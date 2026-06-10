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
  messageSucces: string = '';
  messageErreur: string = '';

  constructor(
    private fb: FormBuilder,
    private api: MedicamentApi
  ) {
    this.clientForm = this.fb.group({
      nom_pharmacie:  ['', [Validators.required, Validators.minLength(3)]],
      nom_responsable:['', [Validators.required]],
      adress:         ['', [Validators.required]],
      telephone:      ['', [Validators.required, Validators.pattern('^[0-9+ ]*$')]],
      email:          ['', [Validators.required, Validators.email]],
      ville:          ['', [Validators.required]]
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
        this.afficherErreur('Impossible de charger les clients.');
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
      // ── Modification ──────────────────────────────────────────
      this.api.updateClient({ id: this.currentClientId, ...clientData }).subscribe({
        next: (res: any) => {
          if (res?.success) {
            this.afficherSucces('Pharmacie mise à jour avec succès.');
            this.resetForm();
            this.loadClients();
          } else {
            this.afficherErreur(res?.erreur || 'Erreur lors de la mise à jour.');
          }
        },
        error: (err: any) => {
          console.error('Erreur mise à jour:', err);
          this.afficherErreur('Erreur de connexion au serveur.');
        }
      });

    } else {
      // ── Ajout ─────────────────────────────────────────────────
      this.api.addClient(clientData).subscribe({
        next: (res: any) => {
          if (res?.success) {
            this.afficherSucces('Pharmacie inscrite avec succès.');
            this.resetForm();
            this.loadClients();
          } else {
            this.afficherErreur(res?.erreur || 'Erreur lors de l\'inscription.');
          }
        },
        error: (err: any) => {
          console.error('Erreur ajout:', err);
          this.afficherErreur('Erreur de connexion au serveur.');
        }
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
      this.afficherErreur('Impossible de supprimer : ID introuvable.');
      return;
    }

    if (confirm('Voulez-vous vraiment retirer cette pharmacie de votre liste ?')) {
      this.api.deleteClient(id).subscribe({
        next: (res: any) => {
          if (res?.success) {
            this.afficherSucces('Pharmacie supprimée avec succès.');
            this.loadClients();
          } else {
            this.afficherErreur(res?.erreur || 'Erreur lors de la suppression.');
          }
        },
        error: (err: any) => {
          console.error('Erreur suppression:', err);
          this.afficherErreur('Erreur de connexion au serveur.');
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

  private afficherSucces(msg: string): void {
    this.messageSucces = msg;
    this.messageErreur = '';
    setTimeout(() => this.messageSucces = '', 3000);
  }

  private afficherErreur(msg: string): void {
    this.messageErreur = msg;
    this.messageSucces = '';
    setTimeout(() => this.messageErreur = '', 4000);
  }
}