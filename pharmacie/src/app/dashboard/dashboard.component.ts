import { Component, OnInit, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { MedicamentApi } from '../services/medicament.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../navbar/navbar.component';
import { FooterComponent } from '../footer/footer.component';
import { HttpClient } from '@angular/common/http';
import { Subscription } from 'rxjs';

/**
 * CORRECTIONS apportées :
 * 1. Intégration complète et propre du chatbot avec ses variables et méthodes pour le template.
 * 2. Utilisation de l'intercepteur JWT existant pour les requêtes HTTP.
 * 3. Gestion rigoureuse des souscriptions avec désabonnement propre dans ngOnDestroy.
 * 4. Mappage robuste pour le support transparent du camelCase (Spring Boot) et snake_case.
 */
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, NavbarComponent, FooterComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  userName: string = '';

  stats = {
    totalMedicaments: 0,
    alertes: 0,
    totalCommandes: 0,
    valeurTotale: 0,
    commandesEnAttente: 0,
    ruptureStock: 0,
    fournisseursActifs: 3
  };

  peremptionProche: any[] = [];
  produitsCritiques: any[] = [];
  loading: boolean = true;
  isLoading: boolean = true;

  // Propriétés pour le chatbot du template
  chatbotOpen: boolean = false;
  chatLoading: boolean = false;
  chatInput: string = '';
  chatMessages: any[] = [];
  suggestions: string[] = [
    'Quels médicaments sont en rupture ?',
    'Quelle est la valeur totale du stock ?',
    'Y a-t-il des commandes en attente ?'
  ];

  @ViewChild('messagesContainer') messagesContainer!: ElementRef;

  private readonly sessionId = `session-${Date.now()}-${Math.random().toString(36).slice(2)}`;
  private subscription: Subscription = new Subscription();

  constructor(private api: MedicamentApi, private http: HttpClient) {}

  ngOnInit(): void {
    this.userName = (typeof window !== 'undefined'
      ? localStorage.getItem('userName') : null) || 'Utilisateur';

    // Initialisation du premier message PharmaBot
    this.chatMessages.push({
      role: 'assistant',
      content: `👋 Bonjour <strong>${this.userName}</strong> ! Je suis <strong>PharmaBot</strong>, votre assistant pharmaceutique.<br><br>Comment puis-je vous aider aujourd'hui ?`
    });

    this.chargerDonneesDashboard();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  chargerDonneesDashboard(): void {
    this.loading = true;
    this.isLoading = true;
    
    const medsSub = this.api.getMedicaments().subscribe({
      next: (meds: any[]) => {
        if (!meds || !Array.isArray(meds)) {
          this.loading = false;
          this.isLoading = false;
          return;
        }

        this.stats.totalMedicaments = meds.length;

        // Normalisation pour supporter indifféremment camelCase (Spring Boot) et snake_case
        const normalizedMeds = meds.map(m => ({
          ...m,
          seuil_alerte: m.seuilAlerte ?? m.seuil_alerte ?? 5,
          date_peremption: m.datePeremption ?? m.date_peremption
        }));

        this.produitsCritiques = normalizedMeds
          .filter(m => (Number(m.quantite) || 0) <= (Number(m.seuil_alerte) || 5))
          .sort((a, b) => Number(a.quantite) - Number(b.quantite))
          .slice(0, 5);

        this.stats.alertes = normalizedMeds.filter(
          m => (Number(m.quantite) || 0) <= (Number(m.seuil_alerte) || 5)
        ).length;

        this.stats.ruptureStock = normalizedMeds.filter(
          m => (Number(m.quantite) || 0) === 0
        ).length;

        this.stats.valeurTotale = meds.reduce(
          (acc, m) => acc + (Number(m.prix) || 0) * (Number(m.quantite) || 0), 0
        );

        const aujourdhui = new Date();
        this.peremptionProche = normalizedMeds
          .filter(m => {
            if (!m.date_peremption) return false;
            const dateM = new Date(m.date_peremption);
            const diffMois = (dateM.getFullYear() - aujourdhui.getFullYear()) * 12
              + (dateM.getMonth() - aujourdhui.getMonth());
            return diffMois >= 0 && diffMois <= 6;
          })
          .sort((a, b) =>
            new Date(a.date_peremption).getTime() - new Date(b.date_peremption).getTime()
          )
          .slice(0, 5);

        this.loading = false;
        this.isLoading = false;
      },
      error: () => {
        this.loading = false;
        this.isLoading = false;
      }
    });
    this.subscription.add(medsSub);

    const cmdSub = this.api.getHistoriqueCommandes().subscribe({
      next: (cmds: any[]) => {
        this.stats.totalCommandes = cmds?.length || 0;
        this.stats.commandesEnAttente = (cmds || []).filter(
          c => c.statut === 'EN_ATTENTE' || c.statut === 'En attente'
        ).length;

        const uniqueSuppliers = new Set((cmds || []).map(c => c.grossiste).filter(Boolean));
        this.stats.fournisseursActifs = uniqueSuppliers.size > 0 ? uniqueSuppliers.size : 3;
      },
      error: () => {}
    });
    this.subscription.add(cmdSub);
  }

  // Méthodes du Chatbot
  toggleChatbot(): void {
    this.chatbotOpen = !this.chatbotOpen;
    if (this.chatbotOpen) {
      setTimeout(() => this.scrollToBottom(), 50);
    }
  }

  sendSuggestion(suggestion: string): void {
    this.chatInput = suggestion;
    this.sendMessage();
  }

  sendMessage(): void {
    const messageText = this.chatInput.trim();
    if (!messageText || this.chatLoading) return;

    this.chatMessages.push({
      role: 'user',
      content: messageText
    });

    this.chatInput = '';
    this.chatLoading = true;
    setTimeout(() => this.scrollToBottom(), 50);

    const chatbotSub = this.http.post<any>('http://localhost:8080/api/chatbot/message', {
      message: messageText,
      sessionId: this.sessionId
    }).subscribe({
      next: (res) => {
        this.chatMessages.push({
          role: 'assistant',
          content: res.response || 'Désolé, je ne parviens pas à répondre pour le moment.'
        });
        this.chatLoading = false;
        setTimeout(() => this.scrollToBottom(), 50);
      },
      error: () => {
        this.chatMessages.push({
          role: 'assistant',
          content: '❌ Impossible de contacter l\'assistant IA. Veuillez vérifier votre connexion.'
        });
        this.chatLoading = false;
        setTimeout(() => this.scrollToBottom(), 50);
      }
    });
    this.subscription.add(chatbotSub);
  }

  private scrollToBottom(): void {
    try {
      if (this.messagesContainer) {
        this.messagesContainer.nativeElement.scrollTop =
          this.messagesContainer.nativeElement.scrollHeight;
      }
    } catch {}
  }
}