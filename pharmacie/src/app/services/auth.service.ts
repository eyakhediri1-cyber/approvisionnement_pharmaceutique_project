import { Injectable, PLATFORM_ID, Inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';

/**
 * CORRECTIONS apportées :
 * 1. URLs migrates vers Spring Boot (localhost:8080/api)
 * 2. saveSession() stocke maintenant le JWT token (authToken)
 * 3. Suppression de loginGrossiste/signupGrossiste avec mauvais endpoint
 * 4. Ajout de getToken() pour l'intercepteur
 * 5. Correction : succes → status pour cohérence avec le backend Spring
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // ── URLs Spring Boot ────────────────────────────────────────────────────────
  private readonly BASE_URL = 'http://localhost:8080/api';
  private readonly pharmacieUrl   = `${this.BASE_URL}/auth/login`;
  private readonly grossisteUrl   = `${this.BASE_URL}/auth/login-grossiste`;

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  private get isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }

  private storage(key: string): string | null {
    return this.isBrowser ? localStorage.getItem(key) : null;
  }

  // ── Authentification pharmacie ───────────────────────────────────────────
  loginPharmacie(email: string, password: string): Observable<any> {
    return this.http.post(this.pharmacieUrl, { mode: 'login', email, password }).pipe(
      tap((res: any) => {
        if (res.status === 'success') this.saveSession(res);
      })
    );
  }

  signupPharmacie(name: string, email: string, password: string): Observable<any> {
    return this.http.post(this.pharmacieUrl, { mode: 'signup', name, email, password }).pipe(
      tap((res: any) => {
        if (res.status === 'success') this.saveSession(res);
      })
    );
  }

  // ── Authentification grossiste ───────────────────────────────────────────
  loginGrossiste(email: string, password: string): Observable<any> {
    return this.http.post(this.grossisteUrl, { email, password }).pipe(
      tap((res: any) => {
        if (res.status === 'success') this.saveSession(res);
      })
    );
  }

  // ── Gestion session ──────────────────────────────────────────────────────
  private saveSession(res: any): void {
    if (!this.isBrowser) return;
    localStorage.setItem('isLoggedIn', 'true');
    localStorage.setItem('userName',   res.userName   ?? '');
    localStorage.setItem('userId',     String(res.userId ?? ''));
    localStorage.setItem('userRole',   res.role       ?? 'pharmacie');
    // ← CORRECTION MAJEURE : stockage du JWT pour l'intercepteur
    if (res.token) {
      localStorage.setItem('authToken', res.token);
    }
    if (res.nomGrossiste) {
      localStorage.setItem('nomGrossiste', res.nomGrossiste);
    }
  }

  logout(): void {
    if (this.isBrowser) localStorage.clear();
    this.router.navigate(['/login']);
  }

  // ── Getters ─────────────────────────────────────────────────────────────
  isLoggedIn(): boolean  { return this.storage('isLoggedIn') === 'true'; }
  getRole(): string | null     { return this.storage('userRole'); }
  getUserName(): string | null { return this.storage('userName'); }
  getUserId(): string | null   { return this.storage('userId'); }
  getToken(): string | null    { return this.storage('authToken'); }
  getNomGrossiste(): string | null { return this.storage('nomGrossiste'); }
}