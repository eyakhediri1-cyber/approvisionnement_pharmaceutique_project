package com.pharma.approvisionnement.controller;

import com.pharma.approvisionnement.dto.GrossisteSearchResult;
import com.pharma.approvisionnement.entity.PharmacieCliente;
import com.pharma.approvisionnement.entity.StockGrossiste;
import com.pharma.approvisionnement.service.GrossisteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grossiste")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Grossiste", description = "Gestion du stock et des clients grossiste")
public class GrossisteController {

    private final GrossisteService grossisteService;

    /** Injecté depuis application-{profil}.properties → app.default-grossiste */
    @Value("${app.default-grossiste:MedicaLab}")
    private String defaultGrossiste;

    public GrossisteController(GrossisteService grossisteService) {
        this.grossisteService = grossisteService;
    }

    /**
     * Résout le nom du grossiste :
     *  1. Paramètre de requête (si fourni et non vide)
     *  2. Claim JWT « nomGrossiste » (si présent)
     *  3. Valeur par défaut du profil Spring actif
     */
    private String resolveGrossiste(String paramGrossiste, Authentication auth) {
        if (paramGrossiste != null && !paramGrossiste.isBlank()) {
            return paramGrossiste;
        }
        return defaultGrossiste;
    }

    // ── Dashboard ─────────────────────────────────────────────────────────────

    @GetMapping("/dashboard")
    @Operation(summary = "Dashboard statistiques grossiste")
    public ResponseEntity<Map<String, Object>> getDashboard(
            @RequestParam(required = false) String grossiste,
            Authentication auth) {
        return ResponseEntity.ok(grossisteService.getDashboard(resolveGrossiste(grossiste, auth)));
    }

    // ── Produits (stock) ──────────────────────────────────────────────────────

    @GetMapping("/produits")
    @Operation(summary = "Liste des produits grossiste avec alertes")
    public ResponseEntity<Map<String, Object>> getProduits(
            @RequestParam(required = false) String grossiste,
            Authentication auth) {
        String nom = resolveGrossiste(grossiste, auth);
        List<StockGrossiste> produits = grossisteService.getProduits(nom);
        Map<String, Object> alertes = grossisteService.getAlertesStock(nom);
        return ResponseEntity.ok(Map.of(
                "produits", produits,
                "alertes_stock", alertes.get("alertes_stock"),
                "alertes_peremption", alertes.get("alertes_peremption")
        ));
    }

    @PostMapping("/produits")
    @Operation(summary = "Ajouter un produit au stock grossiste")
    public ResponseEntity<Map<String, Object>> ajouterProduit(
            @RequestBody StockGrossiste produit,
            @RequestParam(required = false) String grossiste,
            Authentication auth) {
        StockGrossiste saved = grossisteService.ajouterProduit(resolveGrossiste(grossiste, auth), produit);
        return ResponseEntity.ok(Map.of("succès", true, "message", "Produit ajouté !", "produit", saved));
    }

    @PutMapping("/produits/{id}")
    @Operation(summary = "Modifier un produit grossiste")
    public ResponseEntity<StockGrossiste> modifierProduit(
            @PathVariable Long id,
            @RequestBody StockGrossiste update) {
        return ResponseEntity.ok(grossisteService.modifierProduit(id, update));
    }

    @DeleteMapping("/produits/{id}")
    @Operation(summary = "Supprimer un produit grossiste")
    public ResponseEntity<Map<String, Object>> supprimerProduit(@PathVariable Long id) {
        grossisteService.supprimerProduit(id);
        return ResponseEntity.ok(Map.of("succès", true));
    }

    // ── Clients (pharmacies) ──────────────────────────────────────────────────

    @GetMapping("/clients")
    @Operation(summary = "Liste des pharmacies clientes")
    public ResponseEntity<Map<String, Object>> getClients(
            @RequestParam(required = false) String grossiste,
            Authentication auth) {
        List<PharmacieCliente> clients = grossisteService.getClients(resolveGrossiste(grossiste, auth));
        return ResponseEntity.ok(Map.of("success", true, "clients", clients, "total", clients.size()));
    }

    @PostMapping("/clients")
    @Operation(summary = "Ajouter une pharmacie cliente")
    public ResponseEntity<Map<String, Object>> ajouterClient(
            @RequestBody PharmacieCliente client,
            @RequestParam(required = false) String grossiste,
            Authentication auth) {
        PharmacieCliente saved = grossisteService.ajouterClient(resolveGrossiste(grossiste, auth), client);
        return ResponseEntity.ok(Map.of("success", true, "message", "Client inscrit avec succès", "client", saved));
    }

    @PutMapping("/clients/{id}")
    @Operation(summary = "Modifier une pharmacie cliente")
    public ResponseEntity<PharmacieCliente> modifierClient(
            @PathVariable Long id,
            @RequestBody PharmacieCliente update) {
        return ResponseEntity.ok(grossisteService.modifierClient(id, update));
    }

    @DeleteMapping("/clients/{id}")
    @Operation(summary = "Supprimer une pharmacie cliente")
    public ResponseEntity<Map<String, Object>> supprimerClient(@PathVariable Long id) {
        grossisteService.supprimerClient(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    // ── Commandes reçues ──────────────────────────────────────────────────────

    @GetMapping("/commandes")
    @Operation(summary = "Commandes reçues par le grossiste")
    public ResponseEntity<Map<String, Object>> getCommandesRecues(
            @RequestParam(required = false) String grossiste,
            Authentication auth) {
        return ResponseEntity.ok(grossisteService.getCommandesRecues(resolveGrossiste(grossiste, auth)));
    }

    @PutMapping("/commandes/{id}/statut")
    @Operation(summary = "Mettre à jour le statut d'une commande reçue")
    public ResponseEntity<Map<String, Object>> mettreAJourStatut(
            @PathVariable Long id,
            @RequestBody Map<String, String> body,
            @RequestParam(required = false) String grossiste,
            Authentication auth) {
        return ResponseEntity.ok(
                grossisteService.mettreAJourStatut(id, body.get("statut"), resolveGrossiste(grossiste, auth))
        );
    }

    // ── Alertes ───────────────────────────────────────────────────────────────

    @GetMapping("/alertes")
    @Operation(summary = "Alertes stock et péremption grossiste")
    public ResponseEntity<Map<String, Object>> getAlertes(
            @RequestParam(required = false) String grossiste,
            Authentication auth) {
        return ResponseEntity.ok(grossisteService.getAlertesStock(resolveGrossiste(grossiste, auth)));
    }

    // ── Recherche inter-grossistes ────────────────────────────────────────────

    @GetMapping("/recherche")
    @Operation(summary = "Rechercher un médicament chez tous les grossistes")
    public ResponseEntity<List<GrossisteSearchResult>> rechercherParMed(
            @RequestParam(name = "nom", defaultValue = "") String nom) {
        if (nom.isBlank()) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(grossisteService.rechercherGrossistesParMed(nom));
    }
}
