/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.pharma.approvisionnement.dto.CommandeDto
 *  io.swagger.v3.oas.annotations.Operation
 *  io.swagger.v3.oas.annotations.security.SecurityRequirement
 *  io.swagger.v3.oas.annotations.tags.Tag
 *  jakarta.validation.Valid
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.pharma.approvisionnement.controller;

import com.pharma.approvisionnement.dto.CommandeDto;
import com.pharma.approvisionnement.dto.CommandeRequest;
import com.pharma.approvisionnement.dto.GrossisteSearchResult;
import com.pharma.approvisionnement.service.CommandeService;
import com.pharma.approvisionnement.service.GrossisteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/commandes"})
@SecurityRequirement(name="bearerAuth")
@Tag(name="Commandes", description="Gestion des commandes pharmacie")
public class CommandeController {
    private final CommandeService commandeService;
    private final GrossisteService grossisteService;

    public CommandeController(CommandeService commandeService, GrossisteService grossisteService) {
        this.commandeService = commandeService;
        this.grossisteService = grossisteService;
    }

    @GetMapping
    @Operation(summary="Historique des commandes (pharmacie)")
    public ResponseEntity<List<CommandeDto>> getHistoriqueCommandes() {
        return ResponseEntity.ok(this.commandeService.getHistoriqueCommandes());
    }

    @PostMapping
    @Operation(summary="Passer une commande chez un grossiste")
    public ResponseEntity<Map<String, Object>> passerCommande(@Valid @RequestBody CommandeRequest request) {
        return ResponseEntity.ok(this.commandeService.passerCommande(request));
    }

    @PutMapping(value={"/{id}/valider"})
    @Operation(summary="Valider la r\u00e9ception d'une livraison")
    public ResponseEntity<Map<String, Object>> validerLivraison(@PathVariable Long id) {
        return ResponseEntity.ok(this.commandeService.validerLivraison(id));
    }

    @GetMapping(value={"/grossistes/recherche"})
    @Operation(summary="Rechercher des grossistes par m\u00e9dicament")
    public ResponseEntity<List<GrossisteSearchResult>> rechercherGrossistes(@RequestParam(name="nom", defaultValue="") String nom) {
        if (nom.isBlank()) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(this.grossisteService.rechercherGrossistesParMed(nom));
    }
}

