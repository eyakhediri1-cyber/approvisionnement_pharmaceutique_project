import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MedicamentApi } from '../services/medicament.service';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { FooterComponent } from '../footer/footer.component';

/**
 * CORRECTIONS apportées :
 * 1. Mappage bidirectionnel robuste pour supporter la différence entre camelCase (Spring Boot) et snake_case (HTML/MySQL).
 * 2. Gestion propre des états de chargement (loading) et des messages d'erreur utilisateurs (errorMessage).
 * 3. Implémentation complète de la pagination côté client demandée dans le sujet.
 * 4. Correction de l'alignement des propriétés grossistes (nomGrossiste -> nom_grossiste).
 */
@Component({
  selector: 'app-stock',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NavbarComponent,
    FooterComponent,
    FormsModule,
    RouterModule
  ],
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.css'],
})
export class StockComponent implements OnInit {
  medicaments: any[] = [];
  filteredMedicaments: any[] = [];
  paginatedMedicaments: any[] = [];
  grossistes: any[] = [];
  form!: FormGroup;
  showForm: boolean = false;
  medicamentId: number | null = null;
  searchTerm: string = '';
  isModalOpen: boolean = false;
  loading: boolean = false;
  isLoading: boolean = false;
  selectedMedNom: string = '';
  errorMessage: string = '';

  // Variables pour la pagination
  currentPage: number = 1;
  itemsPerPage: number = 8;
  totalPages: number = 1;

  constructor(private formbuilder: FormBuilder, private medicamentApi: MedicamentApi) {}

  ngOnInit(): void {
    this.initForm();
    this.getAllMedicaments();
  }

  initForm() {
    this.form = this.formbuilder.group({
      nom:             ['', Validators.required],
      quantite:        [0, [Validators.required, Validators.min(0)]],
      seuil_alerte:    [3, [Validators.required, Validators.min(1)]],
      date_peremption: ['', Validators.required],
      prix:            [0, [Validators.required, Validators.min(0)]]
    });
  }

  getAllMedicaments() {
    this.loading = true;
    this.isLoading = true;
    this.errorMessage = '';
    this.medicamentApi.getMedicaments().subscribe({
      next: (res: any[]) => {
        this.medicaments = (res || []).map(med => ({
          ...med,
          seuil_alerte: med.seuilAlerte ?? med.seuil_alerte ?? 5,
          date_peremption: med.datePeremption ?? med.date_peremption
        }));
        this.filterMedicaments();
        this.loading = false;
        this.isLoading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Impossible de charger l\'inventaire de médicaments.';
        this.loading = false;
        this.isLoading = false;
      }
    });
  }

  toggleForm() {
    this.showForm = !this.showForm;
    if (!this.showForm) this.resetForm();
  }

  submit() {
    if (this.form.invalid) return;
    this.loading = true;
    this.errorMessage = '';
    const formValue = this.form.value;

    // Mappage robuste vers le format attendu par le DTO Spring Boot (camelCase)
    const payload = {
      nom:             formValue.nom,
      quantite:        formValue.quantite,
      seuilAlerte:    formValue.seuil_alerte,
      datePeremption: formValue.date_peremption,
      prix:            formValue.prix
    };

    if (this.medicamentId) {
      const dataWithId = { id: this.medicamentId, ...payload };
      this.medicamentApi.updateMedicament(dataWithId).subscribe({
        next: () => {
          this.getAllMedicaments();
          this.resetForm();
          this.showForm = false;
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Erreur lors de la modification du médicament.';
          this.loading = false;
        }
      });
    } else {
      this.medicamentApi.addMedicament(payload).subscribe({
        next: () => {
          this.resetForm();
          this.getAllMedicaments();
          this.showForm = false;
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Erreur lors de l\'ajout du médicament.';
          this.loading = false;
        }
      });
    }
  }

  remplirForm(med: any) {
    this.medicamentId = med.id;
    this.form.patchValue({
      nom:             med.nom,
      quantite:        med.quantite,
      seuil_alerte:    med.seuil_alerte ?? med.seuilAlerte,
      date_peremption: med.date_peremption ?? med.datePeremption,
      prix:            med.prix
    });
    this.showForm = true;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  delete(id: number) {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce médicament du stock ?')) {
      this.loading = true;
      this.errorMessage = '';
      this.medicamentApi.deleteMedicament(id).subscribe({
        next: () => {
          this.medicaments = this.medicaments.filter(m => m.id !== id);
          this.filterMedicaments();
          this.loading = false;
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Erreur lors de la suppression du médicament.';
          this.loading = false;
        }
      });
    }
  }

  resetForm() {
    this.form.reset({ quantite: 0, seuil_alerte: 3, prix: 0 });
    this.medicamentId = null;
  }

  filterMedicaments() {
    const motdefiltre = this.searchTerm.toLowerCase().trim();
    this.filteredMedicaments = !motdefiltre
      ? [...this.medicaments]
      : this.medicaments.filter(med => med.nom.toLowerCase().includes(motdefiltre));
    
    this.currentPage = 1;
    this.updatePagination();
  }

  // Fonctions de pagination
  updatePagination() {
    this.totalPages = Math.ceil(this.filteredMedicaments.length / this.itemsPerPage) || 1;
    const start = (this.currentPage - 1) * this.itemsPerPage;
    this.paginatedMedicaments = this.filteredMedicaments.slice(start, start + this.itemsPerPage);
  }

  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.updatePagination();
    }
  }

  getPagesArray(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i + 1);
  }

  ouvrirSourcing(med: any) {
    this.selectedMedNom = med.nom;
    this.isModalOpen = true;
    this.loading = true;
    this.errorMessage = '';
    this.medicamentApi.getGrossistesParMed(med.nom).subscribe({
      next: (res: any) => {
        this.grossistes = (res || []).map((g: any) => ({
          ...g,
          nom_grossiste: g.nomGrossiste ?? g.nom_grossiste ?? 'Grossiste Inconnu',
          quantiteSouhaitee: 10
        }));
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Impossible de contacter les services grossistes.';
        this.loading = false;
      }
    });
  }

  commander(grossiste: any) {
    if (grossiste.quantiteSouhaitee <= 0 || grossiste.quantiteSouhaitee > grossiste.stock) {
      alert(`La quantité doit être comprise entre 1 et le stock disponible (${grossiste.stock} unités).`);
      return;
    }
    const data = {
      nom:       this.selectedMedNom,
      grossiste: grossiste.nomGrossiste ?? grossiste.nom_grossiste,
      qte:       grossiste.quantiteSouhaitee
    };
    this.loading = true;
    this.medicamentApi.passerCommande(data).subscribe({
      next: (res: any) => {
        this.loading = false;
        if (res && res.succes === false) {
          alert(`Échec : ${res.message}`);
        } else {
          alert(`Commande de ${grossiste.quantiteSouhaitee} unités de ${this.selectedMedNom} passée avec succès chez ${data.grossiste} !`);
          this.isModalOpen = false;
          this.getAllMedicaments();
        }
      },
      error: (err) => {
        console.error(err);
        alert('Erreur réseau lors du passage de la commande.');
        this.loading = false;
      }
    });
  }
}