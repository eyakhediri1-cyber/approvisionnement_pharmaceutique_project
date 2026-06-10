import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MedicamentApi } from '../services/medicament.service';
import { NavbarComponent } from '../navbar/navbar.component';
import { FooterComponent } from '../footer/footer.component';
import { Subscription } from 'rxjs';

/**
 * CORRECTIONS apportées :
 * 1. Intégration complète de la création d'une nouvelle commande (formulaire dynamique & modal).
 * 2. Système complet de recherche en temps réel du grossiste selon le médicament saisi.
 * 3. Mappage robuste pour le support transparent du camelCase (Spring Boot) et snake_case.
 * 4. Filtrage dynamique par statut, par date et par mot-clé (médicament/grossiste).
 * 5. Fenêtre de détails modal de transaction premium avec $event.stopPropagation.
 * 6. Gestion des souscriptions propre dans ngOnDestroy.
 */
@Component({
  selector: 'app-commande',
  standalone: true,
  imports: [
    CommonModule,
    NavbarComponent,
    FooterComponent,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './commande.component.html',
  styleUrls: ['./commande.component.css'],
})
export class CommandeComponent implements OnInit, OnDestroy {

  commandes: any[] = [];
  filteredCommandes: any[] = [];
  loading: boolean = false;
  isLoading: boolean = false;
  errorMessage: string = '';

  // Filtres
  searchTerm: string = '';
  statusFilter: string = '';
  dateFilter: string = '';

  // Modal Nouvelle Commande
  showCreateModal: boolean = false;
  createForm!: FormGroup;
  wholesalers: any[] = [];
  searchingWholesalers: boolean = false;

  // Modal Détail
  selectedCommande: any = null;
  showDetailModal: boolean = false;

  // Alertes
  nomsAlertesStock: string = '';
  nomsAlertesPeremption: string = '';

  private subscription: Subscription = new Subscription();

  constructor(private formBuilder: FormBuilder, private commandeApi: MedicamentApi) {}

  ngOnInit(): void {
    this.initCreateForm();
    this.chargerHistorique();
    this.chargerAlertes();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  initCreateForm() {
    this.createForm = this.formBuilder.group({
      nom: ['', Validators.required],
      grossiste: ['', Validators.required],
      qte: [10, [Validators.required, Validators.min(1)]]
    });

    // Recherche réactive des grossistes dispos pour le médicament saisi
    const nomChanges = this.createForm.get('nom')?.valueChanges.subscribe(nomMed => {
      if (nomMed && nomMed.trim().length >= 2) {
        this.rechercherGrossistes(nomMed);
      } else {
        this.wholesalers = [];
        this.createForm.patchValue({ grossiste: '' });
      }
    });
    if (nomChanges) this.subscription.add(nomChanges);
  }

  chargerHistorique() {
    this.loading = true;
    this.isLoading = true;
    this.errorMessage = '';
    const sub = this.commandeApi.getHistoriqueCommandes().subscribe({
      next: (res: any) => {
        // Mappage transparent camelCase -> snake_case pour le template
        this.commandes = (Array.isArray(res) ? res : []).map(cmd => ({
          ...cmd,
          date_cmd: cmd.dateCmd ?? cmd.date_cmd,
          nom_med: cmd.nomMed ?? cmd.nom_med,
          prix_total: cmd.prixTotal ?? cmd.prix_total
        }));
        this.appliquerFiltres();
        this.loading = false;
        this.isLoading = false;
      },
      error: (err: any) => {
        console.error(err);
        this.errorMessage = 'Erreur lors du chargement de l\'historique des commandes.';
        this.loading = false;
        this.isLoading = false;
      }
    });
    this.subscription.add(sub);
  }

  chargerAlertes() {
    const sub = this.commandeApi.getMedicaments().subscribe({
      next: (meds: any[]) => {
        const alertesStock = meds.filter(
          m => (Number(m.quantite) || 0) <= (Number(m.seuilAlerte ?? m.seuil_alerte) || 5)
        );
        this.nomsAlertesStock = alertesStock.map(m => m.nom).join(', ');

        const dans6Mois = new Date();
        dans6Mois.setMonth(dans6Mois.getMonth() + 6);
        const alertesPeremp = meds.filter(m => {
          const d = m.datePeremption ?? m.date_peremption;
          return d && new Date(d) < dans6Mois;
        });
        this.nomsAlertesPeremption = alertesPeremp.map(m => m.nom).join(', ');
      },
      error: () => {}
    });
    this.subscription.add(sub);
  }

  rechercherGrossistes(nomMed: string) {
    this.searchingWholesalers = true;
    const sub = this.commandeApi.getGrossistesParMed(nomMed).subscribe({
      next: (res: any[]) => {
        this.wholesalers = (res || []).map(g => ({
          ...g,
          nom_grossiste: g.nomGrossiste ?? g.nom_grossiste
        }));
        this.searchingWholesalers = false;
      },
      error: () => {
        this.wholesalers = [];
        this.searchingWholesalers = false;
      }
    });
    this.subscription.add(sub);
  }

  ouvrirNouvelleCommande() {
    this.createForm.reset({ qte: 10 });
    this.wholesalers = [];
    this.showCreateModal = true;
  }

  soumettreCommande() {
    if (this.createForm.invalid) return;
    this.loading = true;
    const val = this.createForm.value;

    const selectedGrossiste = this.wholesalers.find(w => w.nom_grossiste === val.grossiste);
    const prixUnitaire = selectedGrossiste ? selectedGrossiste.prix : 0;

    const payload = {
      nom: val.nom,
      grossiste: val.grossiste,
      qte: val.qte,
      prixTotal: prixUnitaire * val.qte
    };

    const sub = this.commandeApi.passerCommande(payload).subscribe({
      next: (res: any) => {
        this.loading = false;
        if (res && res.succes === false) {
          alert(`Échec : ${res.message}`);
        } else {
          alert('Commande passée avec succès chez le grossiste.');
          this.showCreateModal = false;
          this.chargerHistorique();
        }
      },
      error: (err) => {
        console.error(err);
        alert('Erreur réseau lors de l\'envoi de la commande.');
        this.loading = false;
      }
    });
    this.subscription.add(sub);
  }

  validerReception(cmd: any) {
    if (confirm('Confirmer la réception de cette commande ? Le stock de la pharmacie sera automatiquement incrémenté.')) {
      this.loading = true;
      const sub = this.commandeApi.validerLivraison(cmd.id).subscribe({
        next: (res: any) => {
          if (res && res.succes === false) {
            alert(`Échec : ${res.message}`);
          } else {
            alert('Réception validée avec succès ! Le stock a été mis à jour.');
            this.chargerHistorique();
          }
        },
        error: (err: any) => {
          console.error(err);
          alert('Erreur lors de la validation de la livraison.');
          this.loading = false;
        }
      });
      this.subscription.add(sub);
    }
  }

  // Filtrage des transactions
  appliquerFiltres() {
    let result = [...this.commandes];

    if (this.searchTerm) {
      const term = this.searchTerm.toLowerCase().trim();
      result = result.filter(cmd => 
        (cmd.nom_med || '').toLowerCase().includes(term) || 
        (cmd.grossiste || '').toLowerCase().includes(term)
      );
    }

    if (this.statusFilter) {
      result = result.filter(cmd => 
        (cmd.statut || '').toLowerCase() === this.statusFilter.toLowerCase()
      );
    }

    if (this.dateFilter) {
      const filterDateStr = new Date(this.dateFilter).toDateString();
      result = result.filter(cmd => {
        if (!cmd.date_cmd) return false;
        return new Date(cmd.date_cmd).toDateString() === filterDateStr;
      });
    }

    this.filteredCommandes = result;
  }

  reinitialiserFiltres() {
    this.searchTerm = '';
    this.statusFilter = '';
    this.dateFilter = '';
    this.appliquerFiltres();
  }

  // Détails de la commande
  voirDetail(cmd: any) {
    this.selectedCommande = cmd;
    this.showDetailModal = true;
  }

  fermerDetail() {
    this.selectedCommande = null;
    this.showDetailModal = false;
  }

  peutReceptionner(statut: string): boolean {
    const s = (statut || '').toLowerCase();
    // Seule la commande "en attente" peut être réceptionnée par la pharmacie
    return s.includes('attente') || s === 'en_attente';
  }

  getStatutClass(statut: string): string {
    const s = (statut || '').toLowerCase();
    if (s.includes('attente'))     return 'status-pending';
    if (s.includes('livr'))        return 'status-delivered';
    if (s.includes('annul'))       return 'status-cancelled';
    if (s.includes('confirm'))     return 'status-confirmed';
    if (s.includes('prep') || s.includes('prép')) return 'status-preparing';
    if (s.includes('exped') || s.includes('expéd')) return 'status-shipped';
    return 'status-unknown';
  }
}