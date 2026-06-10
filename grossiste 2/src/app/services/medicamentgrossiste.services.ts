import { Injectable, PLATFORM_ID, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, interval, switchMap, startWith, distinctUntilChanged, map, shareReplay } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class MedicamentGrossisteService {

  private readonly BASE_URL = 'http://localhost:8082/api/grossiste';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  // ── Stock ──────────────────────────────────────────────────────
  getProduits(): Observable<any> {
    return this.http.get<any>(`${this.BASE_URL}/produits`);
  }

  addProduit(donnees: any): Observable<any> {
    return this.http.post(`${this.BASE_URL}/produits`, donnees);
  }

  updateProduit(donnees: any): Observable<any> {
    const { id_med, ...body } = donnees;
    return this.http.put(`${this.BASE_URL}/produits/${id_med}`, body);
  }

  deleteProduit(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL}/produits/${id}`);
  }

  getAlertesStock(): Observable<any> {
    return this.http.get<any>(`${this.BASE_URL}/alertes`);
  }

  // ── Clients ────────────────────────────────────────────────────
  getClients(): Observable<any[]> {
    return this.http.get<any>(`${this.BASE_URL}/clients`).pipe(
      map((res: any) => res?.clients || [])
    );
  }

  addClient(donnees: any): Observable<any> {
    return this.http.post(`${this.BASE_URL}/clients`, donnees);
  }

  updateClient(donnees: any): Observable<any> {
    const { id, ...body } = donnees;
    return this.http.put(`${this.BASE_URL}/clients/${id}`, body);
  }

  deleteClient(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL}/clients/${id}`);
  }

  // ── Commandes reçues ───────────────────────────────────────────
  getCommandesRecues(): Observable<any> {
    return this.http.get<any>(`${this.BASE_URL}/commandes`);
  }

  majStatutCommande(id: number, statut: string): Observable<any> {
    return this.http.put(`${this.BASE_URL}/commandes/${id}/statut`, { statut });
  }

  getNotificationsCount$(): Observable<number> {
    return interval(15000).pipe(
      startWith(0),
      switchMap(() => this.getCommandesRecues()),
      map((res: any) => res?.nb_non_traitees ?? 0),
      distinctUntilChanged(),
      shareReplay(1)
    );
  }

  // ── Auth ───────────────────────────────────────────────────────
  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(`http://localhost:8082/api/auth/login-grossiste`, credentials);
  }

  logout(): void {}
}

export { MedicamentGrossisteService as MedicamentApi };