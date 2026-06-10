import { Component, OnInit, OnDestroy, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser, CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { MedicamentApi } from '../services/medicamentgrossiste.services';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  loading = true;

  totalProduits     = 0;
  totalClients      = 0;
  commandesAttente  = 0;
  alertesStock      = 0;
  alertesPeremption = 0;

  dernieresCommandes: any[] = [];
  produitsAlerte: any[]     = [];
  produitsPeremption: any[] = [];

  userName = 'Admin';
  today    = new Date();

  private subs: Subscription[] = [];
  private loaded = { produits: false, clients: false, commandes: false };

  constructor(
    private api: MedicamentApi,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      const user = localStorage.getItem('grossiste_user');
      if (user) {
        try {
          const parsed = JSON.parse(user);
          this.userName = parsed.nom || parsed.name || parsed.email || 'Admin';
        } catch {}
      }
    }
    this.chargerDonnees();
  }

  ngOnDestroy(): void {
    this.subs.forEach(s => s.unsubscribe());
  }

  private checkAllLoaded(): void {
    if (this.loaded.produits && this.loaded.clients && this.loaded.commandes) {
      this.loading = false;
    }
  }

  chargerDonnees(): void {
    this.loading = true;
    this.loaded = { produits: false, clients: false, commandes: false };

    // Sécurité : si le backend ne répond pas après 5s, on affiche quand même l'UI
    const timeout = setTimeout(() => { this.loading = false; }, 5000);

    const s1 = this.api.getProduits().subscribe({
      next: (res: any) => {
        const produits = Array.isArray(res?.produits) ? res.produits : (Array.isArray(res) ? res : []);
        this.totalProduits = produits.length;
        if (res?.alertes_stock) {
          this.produitsAlerte = res.alertes_stock;
          this.alertesStock   = res.alertes_stock.length;
        }
        if (res?.alertes_peremption) {
          this.produitsPeremption = res.alertes_peremption;
          this.alertesPeremption  = res.alertes_peremption.length;
        }
        this.loaded.produits = true;
        this.checkAllLoaded();
      },
      error: () => {
        this.loaded.produits = true;
        this.checkAllLoaded();
      }
    });

    const s2 = this.api.getClients().subscribe({
      next: (res: any) => {
        const clients = Array.isArray(res) ? res : (Array.isArray(res?.clients) ? res.clients : []);
        this.totalClients = clients.length;
        this.loaded.clients = true;
        this.checkAllLoaded();
      },
      error: () => {
        this.loaded.clients = true;
        this.checkAllLoaded();
      }
    });

    const s3 = this.api.getCommandesRecues().subscribe({
      next: (res: any) => {
        const all = Array.isArray(res?.commandes) ? res.commandes : [];
        this.commandesAttente   = all.filter((c: any) => c.statut === 'En attente').length;
        this.dernieresCommandes = all.slice(0, 5);
        this.loaded.commandes = true;
        clearTimeout(timeout);
        this.checkAllLoaded();
      },
      error: () => {
        this.loaded.commandes = true;
        clearTimeout(timeout);
        this.checkAllLoaded();
      }
    });

    this.subs.push(s1, s2, s3);
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

  get currentHour(): number { return new Date().getHours(); }

  get greeting(): string {
    if (this.currentHour < 12) return 'Bonjour';
    if (this.currentHour < 18) return 'Bon après-midi';
    return 'Bonsoir';
  }
}