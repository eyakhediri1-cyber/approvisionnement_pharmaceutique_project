import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule, AsyncPipe } from '@angular/common';
import { Observable, EMPTY } from 'rxjs';
import { MedicamentGrossisteService } from './services/medicamentgrossiste.services';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, CommonModule, AsyncPipe],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'grossiste';
  nbCommandes$!: Observable<number>;

  constructor(
    private svc: MedicamentGrossisteService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.nbCommandes$ = this.svc.getNotificationsCount$();
    } else {
      this.nbCommandes$ = EMPTY;
    }
  }
}