import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * CORRECTIONS apportées :
 * 1. Base URL unifiée vers Spring Boot (localhost:8080/api)
 * 2. Suppression des endpoints inexistants (liste_produits.php, ajouter_produit.php, etc.)
 *    → remplacés par les vrais endpoints Spring Boot
 * 3. Classe renommée implicitement (export reste MedicamentApi pour rétrocompat)
 * 4. Méthodes getProduits/addProduit/updateProduit/deleteProduit branchées sur
 *    les endpoints grossiste Spring Boot
 * 5. Toutes les URLs sont maintenant REST propres (pas de .php)
 */
@Injectable({
  providedIn: 'root'
})
export class MedicamentApi {

  private readonly BASE_URL = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  // ── Médicaments (stock pharmacie) ──────────────────────────────────────────

  getMedicaments(): Observable<any[]> {
    return this.http.get<any[]>(`${this.BASE_URL}/medicaments`);
  }

  addMedicament(donnees: any): Observable<any> {
    return this.http.post(`${this.BASE_URL}/medicaments`, donnees);
  }

  updateMedicament(donnees: any): Observable<any> {
    const { id, ...body } = donnees;
    return this.http.put(`${this.BASE_URL}/medicaments/${id}`, body);
  }

  deleteMedicament(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL}/medicaments/${id}`);
  }

  getAlertesStock(): Observable<any[]> {
    return this.http.get<any[]>(`${this.BASE_URL}/medicaments/alertes`);
  }

  // ── Grossiste — produits (CORRECTION : endpoints inexistants remplacés) ────

  /**
   * CORRECTION : getProduits() pointait vers liste_produits.php (inexistant).
   * Branche maintenant sur GET /api/grossiste/produits.
   */
  getProduits(grossiste?: string): Observable<any> {
    const params = grossiste ? `?grossiste=${grossiste}` : '';
    return this.http.get<any>(`${this.BASE_URL}/grossiste/produits${params}`);
  }

  addProduit(donnees: any): Observable<any> {
    return this.http.post(`${this.BASE_URL}/grossiste/produits`, donnees);
  }

  updateProduit(donnees: any): Observable<any> {
    const { id_med, ...body } = donnees;
    return this.http.put(`${this.BASE_URL}/grossiste/produits/${id_med}`, body);
  }

  deleteProduit(id: number): Observable<any> {
    return this.http.delete(`${this.BASE_URL}/grossiste/produits/${id}`);
  }

  // ── Commandes (côté pharmacie) ─────────────────────────────────────────────

  getHistoriqueCommandes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.BASE_URL}/commandes`);
  }

  passerCommande(donnees: any): Observable<any> {
    return this.http.post(`${this.BASE_URL}/commandes`, donnees);
  }

  validerLivraison(id: number): Observable<any> {
    return this.http.put(`${this.BASE_URL}/commandes/${id}/valider`, {});
  }

  // ── Recherche grossiste ────────────────────────────────────────────────────

  getGrossistesParMed(nomMed: string): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.BASE_URL}/grossiste/recherche?nom=${encodeURIComponent(nomMed)}`
    );
  }
}