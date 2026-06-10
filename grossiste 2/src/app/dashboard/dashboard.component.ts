import { Component, OnInit, OnDestroy, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser, CommonModule } from '@angular/common'; // Assurez-vous que CommonModule est là
import { RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule], // CommonModule active ngIf, ngFor, ngClass et les pipes
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  loading = true;

  // Déclaration de toutes les propriétés utilisées dans le HTML
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
      const userStr = localStorage.getItem('grossiste_user');
      if (userStr) {
        try {
          const user = JSON.parse(userStr);
          this.userName = user.nom || 'Admin';
          const gid = user.grossiste_id || 1; 
          this.chargerDonnees(gid);
        } catch (e) {
          this.chargerDonnees(1);
        }
      } else {
        this.chargerDonnees(1);
      }
    }
  }

  chargerDonnees(grossisteId: number): void {
    this.loading = true;
    const url = `http://localhost:8000/crud-grossistes/crud-grossiste-${grossisteId}/dashboard.php`;

    const sub = this.http.get<any>(url).subscribe({
      next: (res) => {
        this.totalProduits    = res.total_produits    ?? 0;
        this.totalClients     = res.total_clients     ?? 0;
        this.commandesAttente = res.commandes_attente ?? 0;
        
        this.produitsAlerte    = res.alertes_stock      ?? [];
        this.alertesStock      = this.produitsAlerte.length;
        
        this.produitsPeremption = res.alertes_peremption ?? [];
        this.alertesPeremption  = this.produitsPeremption.length;

        const commandes = res.commandes?.commandes ?? [];
        this.dernieresCommandes = Array.isArray(commandes) ? commandes.slice(0, 5) : [];

        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
    this.subs.push(sub);
  }

  // MÉTHODE MANQUANTE : Gestion des classes CSS pour les badges
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

  // GETTER MANQUANT : Pour le message de bienvenue (greeting)
  get greeting(): string {
    const hour = new Date().getHours();
    if (hour < 12) return 'Bonjour';
    if (hour < 18) return 'Bon après-midi';
    return 'Bonsoir';
  }

  ngOnDestroy(): void {
    this.subs.forEach(s => s.unsubscribe());
  }
}