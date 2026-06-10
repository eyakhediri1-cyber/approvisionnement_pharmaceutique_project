import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { MedicamentGrossisteService } from '../services/medicamentgrossiste.services';
import { FiltreStatutPipe } from '../pipes/filtre-statut.pipe';  // ✅ import du pipe

@Component({
  selector: 'app-liste-commandes',
  standalone: true,
  imports: [CommonModule, RouterModule, FiltreStatutPipe],  // ✅ pipe déclaré ici
  templateUrl: './liste-commandes.component.html',
  styleUrls: ['./liste-commandes.component.css']
})
export class ListeCommandesComponent implements OnInit, OnDestroy {

  commandes: any[] = [];
  loading = false;
  error = '';

  filtreStatut: string = 'tous';
  readonly statuts = ['tous', 'En attente', 'Confirmée', 'En préparation', 'Expédiée', 'Livrée', 'Annulée'];

  readonly statutsSuivants: Record<string, string[]> = {
    'En attente'    : ['Confirmée', 'Annulée'],
    'Confirmée'     : ['En préparation', 'Annulée'],
    'En préparation': ['Expédiée'],
    'Expédiée'      : ['Livrée'],
    'Livrée'        : [],
    'Annulée'       : []
  };

  // ✅ Icônes Bootstrap Icons pour chaque action
  readonly iconesAction: Record<string, string> = {
    'Confirmée'     : 'bi-check-circle',
    'En préparation': 'bi-box-seam',
    'Expédiée'      : 'bi-truck',
    'Livrée'        : 'bi-check2-all',
    'Annulée'       : 'bi-x-circle'
  };

  private sub!: Subscription;

  constructor(private svc: MedicamentGrossisteService) {}

  ngOnInit(): void {
    this.charger();
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  charger(): void {
    this.loading = true;
    this.error   = '';
    this.svc.getCommandesRecues().subscribe({
      next: (res: any) => {
        this.commandes = Array.isArray(res?.commandes) ? res.commandes : [];
        this.loading   = false;
      },
      error: () => {
        this.error   = 'Impossible de charger les commandes.';
        this.loading = false;
      }
    });
  }

  get commandesFiltrees(): any[] {
    if (this.filtreStatut === 'tous') return this.commandes;
    return this.commandes.filter(c => c.statut === this.filtreStatut);
  }

  get nbEnAttente(): number {
    return this.commandes.filter(c => c.statut === 'En attente').length;
  }

  changerStatut(cmd: any, statut: string): void {
  if (!confirm(`Passer la commande #${cmd.id} à "${statut}" ?`)) return;

  this.svc.majStatutCommande(cmd.id, statut).subscribe({
    next: () => {
      this.commandes = this.commandes.map(c =>
        c.id === cmd.id
          ? { ...c, statut, date_traitement: statut !== 'En attente' ? new Date().toISOString() : c.date_traitement }
          : c
      );
    },
    error: () => alert('Erreur lors de la mise à jour du statut.')
  });
}

  statutClass(statut: string): string {
    const map: Record<string, string> = {
      'En attente'    : 'badge-attente',
      'Confirmée'     : 'badge-confirme',
      'En préparation': 'badge-preparation',
      'Expédiée'      : 'badge-expedie',
      'Livrée'        : 'badge-livre',
      'Annulée'       : 'badge-annule'
    };
    return map[statut] ?? 'badge-attente';
  }

  actionsDisponibles(statut: string): string[] {
    return this.statutsSuivants[statut] ?? [];
  }

  // ✅ Méthode manquante — retourne la classe Bootstrap Icon selon l'action
  iconAction(action: string): string {
    return this.iconesAction[action] ?? 'bi-arrow-right-circle';
  }
}