import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MedicamentApi } from '../services/medicamentgrossiste.services';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-gestion-clients',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterLink, RouterLinkActive, RouterOutlet],
  templateUrl: './gestion-clients.component.html',
  styleUrl: './gestion-clients.component.css'
})
export class GestionClientsComponent implements OnInit {
  clients: any[] = [];
  filteredClients: any[] = [];

  clientForm: FormGroup;
  showForm: boolean = false;
  modeEdition: boolean = false;
  currentClientId: number | null = null;

  searchTerm: string = '';

  constructor(
    private fb: FormBuilder,
    private api: MedicamentApi
  ) {
    this.clientForm = this.fb.group({
      nom_pharmacie:   ['', [Validators.required, Validators.minLength(3)]],
      nom_responsable: ['', [Validators.required]],
      adress:          ['', [Validators.required]],
      telephone:       ['', [Validators.required, Validators.pattern('^[0-9+ ]*$')]],
      email:           ['', [Validators.required, Validators.email]],
      ville:           ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    this.api.getClients().subscribe({
      next: (res: any) => {
        const rawData = Array.isArray(res) ? res : (res?.clients || []);
        this.clients = rawData;
        this.filteredClients = [...this.clients];
      },
      error: (err: any) => {
        console.error('Erreur de chargement:', err);
      }
    });
  }

  filterClients(): void {
    const term = this.searchTerm.toLowerCase().trim();
    if (!term) {
      this.filteredClients = [...this.clients];
      return;
    }
    this.filteredClients = this.clients.filter(c =>
      c.nom_pharmacie?.toLowerCase().includes(term) ||
      c.ville?.toLowerCase().includes(term) ||
      c.nom_responsable?.toLowerCase().includes(term)
    );
  }

  onSubmit(): void {
    if (this.clientForm.invalid) return;

    const clientData = this.clientForm.value;

    if (this.modeEdition && this.currentClientId) {
      // ✅ Fusionner l'id dans l'objet (updateClient n'accepte qu'1 argument)
      this.api.updateClient({ id: this.currentClientId, ...clientData }).subscribe({
        next: () => {
          this.resetForm();
          this.loadClients();
        },
        error: (err: any) => alert('Erreur lors de la mise à jour')
      });
    } else {
      this.api.addClient(clientData).subscribe({
        next: () => {
          this.resetForm();
          this.loadClients();
        },
        error: (err: any) => alert('Erreur lors de l\'inscription')
      });
    }
  }

  modifierClient(client: any): void {
    this.modeEdition = true;
    this.showForm = true;
    this.currentClientId = client.id_pharm_client || client.id_client || client.id;
    this.clientForm.patchValue(client);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  supprimerClient(id: number): void {
    if (!id) {
      alert('Impossible de supprimer : ID introuvable');
      return;
    }
    if (confirm('Voulez-vous vraiment retirer cette pharmacie de votre liste ?')) {
      this.api.deleteClient(id).subscribe({
        next: (res: any) => {
          if (res.success) {
            this.loadClients();
          } else {
            alert('Erreur lors de la suppression en base de données');
          }
        },
        error: (err: any) => {
          console.error('Erreur API suppression:', err);
          alert('Erreur de connexion au serveur');
        }
      });
    }
  }

  resetForm(): void {
    this.clientForm.reset();
    this.showForm = false;
    this.modeEdition = false;
    this.currentClientId = null;
  }
}