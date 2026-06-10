import { Routes } from '@angular/router';
import { LoginPharmacieComponent } from './login-pharmacie/login-pharmacie.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { StockComponent } from './stock/stock.component';
import { CommandeComponent } from './commande/commande.component';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './services/auth.service';

export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const platformId = inject(PLATFORM_ID);

  if (!isPlatformBrowser(platformId)) return true;

  if (!auth.isLoggedIn()) {
    router.navigate(['/login']);
    return false;
  }
  return true;
};

export const routes: Routes = [
  { path: 'login',         component: LoginPharmacieComponent },
  { path: 'dashboard',     component: DashboardComponent,     canActivate: [authGuard] },
  { path: 'stock',         component: StockComponent,         canActivate: [authGuard] },
  { path: 'commande',      component: CommandeComponent,      canActivate: [authGuard] },
  { path: '',              redirectTo: '/login', pathMatch: 'full' },
  { path: '**',            redirectTo: '/login' }
];