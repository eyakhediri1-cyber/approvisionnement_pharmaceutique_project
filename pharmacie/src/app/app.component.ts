import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ChatbotComponent } from './chatbot/chatbot.component';

/**
 * CORRECTIONS :
 * 1. Import StockComponent supprimé (inutile dans le root component)
 * 2. ChatbotComponent importé pour l'overlay global
 */
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ChatbotComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'pharmacie';
}
