import { Routes } from '@angular/router';
import { StockGrossisteComponent }   from './stock-grossiste/stock-grossiste.component';
import { GestionClientsComponent }   from './gestion-clients/gestion-clients.component';
import { ListeCommandesComponent }   from './liste-commandes/liste-commandes.component';
import { DashboardComponent }        from './dashboard/dashboard.component';

export const routes: Routes = [
  { path: '',           redirectTo: '/stock', pathMatch: 'full' },
  { path: 'stock',      component: StockGrossisteComponent },
  { path: 'commandes',  component: ListeCommandesComponent },   // ✅ Nouvelle route
  { path: 'gestion-clients', component: GestionClientsComponent },
  { path: 'dashboard',  component: DashboardComponent },
];