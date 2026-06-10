import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { MedicamentApi } from '../services/medicamentgrossiste.services';

@Component({
  selector: 'app-stock-grossiste',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule, FormsModule],
  templateUrl: './stock-grossiste.component.html',
  styleUrls: ['./stock-grossiste.component.css'],
})
export class StockGrossisteComponent implements OnInit {

  produits: any[] = [];
  filteredProduits: any[] = [];
  productForm!: FormGroup;
  showForm: boolean = false;
  modeEdition: boolean = false;
  produitId: number | null = null;
  searchTerm: string = '';

  nomsAlertesStock: string = '';
  nomsAlertesPeremption: string = '';

  constructor(private formbuilder: FormBuilder, private grossisteApi: MedicamentApi) { }

  ngOnInit(): void {
    this.productForm = this.formbuilder.group({
      nom_med: ['', Validators.required],
      prix_unitaire: [0, [Validators.required, Validators.min(0)]],
      stock_dispo: [0, [Validators.required, Validators.min(0)]],
      seuil_alerte: [5, Validators.required],
      date_peremption: ['', Validators.required]
    });
    this.getAllProduits();
  }

  getAllProduits() {
    this.grossisteApi.getProduits().subscribe((res: any) => {
      const rawData = res.produits ? res.produits : res;
      this.produits = Array.isArray(rawData) ? rawData : [];
      if (this.searchTerm) {
        this.filterProduits();
      } else {
        this.filteredProduits = [...this.produits];
      }
      this.preparerAlertes(res);
    });
  }

  preparerAlertes(res: any) {
    if (res.alertes_stock && Array.isArray(res.alertes_stock)) {
      this.nomsAlertesStock = res.alertes_stock
        .map((a: any) => a.nom_med || a.nom || a.nom_produit)
        .filter((name: any) => name)
        .join(', ');
    } else {
      this.nomsAlertesStock = '';
    }

    if (res.alertes_peremption && Array.isArray(res.alertes_peremption)) {
      this.nomsAlertesPeremption = res.alertes_peremption
        .map((a: any) => a.nom_med || a.nom || a.nom_produit)
        .filter((name: any) => name)
        .join(', ');
    } else {
      this.nomsAlertesPeremption = '';
    }
  }

  onSubmit() {
    if (this.productForm.invalid) return;

    if (this.modeEdition) {
      this.update();
    } else {
      this.ajoutProduit();
    }
  }

  resetForm() {
    this.productForm.reset({ stock_dispo: 0, seuil_alerte: 5, prix_unitaire: 0 });
    this.showForm = false;
    this.modeEdition = false;
    this.produitId = null;
  }

  ajoutProduit() {
    this.grossisteApi.addProduit(this.productForm.value).subscribe({
      next: () => {
        this.resetForm();
        this.getAllProduits();
      },
      error: (err: any) => console.error(err)
    });
  }

  modifierProduit(prod: any) {
    this.produitId = prod.id_med;
    this.modeEdition = true;
    this.productForm.patchValue(prod);
    this.showForm = true;
  }

  update() {
    const data = { id_med: this.produitId, ...this.productForm.value };

    this.grossisteApi.updateProduit(data).subscribe({
      next: (res: any) => {
        console.log("Objet reçu du serveur :", res);
        if (res && (res.succès === true || res.success === true)) {
          this.resetForm();
          this.getAllProduits();
        } else {
          this.resetForm();
          this.getAllProduits();
        }
      },
      error: (err: any) => {
        console.error("Erreur réseau ou crash PHP :", err);
      }
    });
  }

  supprimerProduit(id: number) {
    if (confirm('Supprimer ce produit du catalogue grossiste ?')) {
      this.grossisteApi.deleteProduit(id).subscribe(() => {
        this.getAllProduits();
      });
    }
  }

  filterProduits() {
    const motDeFiltre = this.searchTerm.toLowerCase();
    this.filteredProduits = this.produits.filter(prod =>
      prod.nom_med?.toLowerCase().includes(motDeFiltre)
    );
  }
}