import { Component, OnInit, ViewChild, ElementRef, AfterViewChecked } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface Message {
  role: 'user' | 'assistant';
  content: string;
  timestamp: Date;
}

/**
 * CORRECTIONS apportées :
 * 1. URL corrigée vers Spring Boot : POST /api/chatbot/message
 * 2. Payload corrigé : { message, sessionId } au lieu de { messages: [] }
 * 3. Réponse Spring Boot : { response: string, timestamp: string }
 * 4. sessionId généré automatiquement
 * 5. Logique async → Observable RxJS propre
 */
@Component({
  selector: 'app-chatbot',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.css']
})
export class ChatbotComponent implements OnInit, AfterViewChecked {
  @ViewChild('messagesContainer') messagesContainer!: ElementRef;

  isOpen   = false;
  isLoading = false;
  userInput = '';
  messages: Message[] = [];
  hasNewMessage = false;

  // ← CORRECTION : URL Spring Boot correcte
  private readonly apiUrl = 'http://localhost:8080/api/chatbot/message';

  // Session ID unique par ouverture du navigateur
  private readonly sessionId = `session-${Date.now()}-${Math.random().toString(36).slice(2)}`;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.messages.push({
      role: 'assistant',
      content: '👋 Bonjour ! Je suis **PharmaBot**, votre assistant pharmaceutique.\n\n' +
               'Je peux vous aider avec :\n' +
               '• 📋 Vos **commandes** (en attente, du jour)\n' +
               '• 📦 Votre **stock** (alertes, ruptures, inventaire)\n' +
               '• 🏭 Vos **fournisseurs**\n\n' +
               'Tapez _"aide"_ pour voir toutes mes capacités.',
      timestamp: new Date()
    });
  }

  ngAfterViewChecked(): void {
    this.scrollToBottom();
  }

  toggleChat(): void {
    this.isOpen = !this.isOpen;
    if (this.isOpen) this.hasNewMessage = false;
  }

  sendMessage(): void {
    const trimmed = this.userInput.trim();
    if (!trimmed || this.isLoading) return;

    // Ajouter le message utilisateur
    this.messages.push({
      role: 'user',
      content: trimmed,
      timestamp: new Date()
    });

    this.userInput  = '';
    this.isLoading  = true;

    // ← CORRECTION : payload Spring Boot { message, sessionId }
    this.http.post<{ response: string; timestamp: string }>(
      this.apiUrl,
      { message: trimmed, sessionId: this.sessionId }
    ).subscribe({
      next: (res) => {
        this.messages.push({
          role: 'assistant',
          content: res.response,
          timestamp: new Date()
        });
        this.isLoading = false;
        if (!this.isOpen) this.hasNewMessage = true;
      },
      error: (err) => {
        const msg = err.status === 401
          ? '🔒 Session expirée. Veuillez vous reconnecter.'
          : '❌ Une erreur est survenue. Vérifiez votre connexion.';
        this.messages.push({
          role: 'assistant',
          content: msg,
          timestamp: new Date()
        });
        this.isLoading = false;
      }
    });
  }

  onKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      this.sendMessage();
    }
  }

  formatTime(date: Date): string {
    return date.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });
  }

  formatMessage(content: string): string {
    if (!content) return '';
    return content.replace(/\n/g, '<br>');
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