import { Component, OnInit, OnDestroy, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser, CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Subscription } from 'rxjs';

const DASHBOARD_URL = 'http://localhost:8000/crud-grossistes/crud-grossiste-3/dashboard.php';

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

  constructor(
    private http: HttpClient,
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

  chargerDonnees(): void {
    this.loading = true;

    const timeout = setTimeout(() => { this.loading = false; }, 5000);

    const sub = this.http.get<any>(DASHBOARD_URL).subscribe({
      next: (res) => {
        clearTimeout(timeout);

        this.totalProduits    = res.total_produits    ?? 0;
        this.totalClients     = res.total_clients     ?? 0;
        this.commandesAttente = res.commandes_attente ?? 0;

        this.produitsAlerte    = Array.isArray(res.alertes_stock)      ? res.alertes_stock      : [];
        this.alertesStock      = this.produitsAlerte.length;

        this.produitsPeremption = Array.isArray(res.alertes_peremption) ? res.alertes_peremption : [];
        this.alertesPeremption  = this.produitsPeremption.length;

        const commandes = res.commandes?.commandes ?? res.dernieres_commandes ?? [];
        this.dernieresCommandes = Array.isArray(commandes) ? commandes.slice(0, 5) : [];

        this.loading = false;
      },
      error: () => {
        clearTimeout(timeout);
        this.loading = false;
      }
    });

    this.subs.push(sub);
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