/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.v3.oas.annotations.Operation
 *  io.swagger.v3.oas.annotations.security.SecurityRequirement
 *  io.swagger.v3.oas.annotations.tags.Tag
 *  jakarta.validation.Valid
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.HttpStatusCode
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.pharma.approvisionnement.controller;

import com.pharma.approvisionnement.dto.MedicamentDto;
import com.pharma.approvisionnement.service.MedicamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/medicaments"})
@SecurityRequirement(name="bearerAuth")
@Tag(name="M\u00e9dicaments", description="Gestion du stock de m\u00e9dicaments (pharmacie)")
public class MedicamentController {
    private final MedicamentService medicamentService;

    public MedicamentController(MedicamentService medicamentService) {
        this.medicamentService = medicamentService;
    }

    @GetMapping
    @Operation(summary="Liste tous les m\u00e9dicaments")
    public ResponseEntity<List<MedicamentDto>> getAllMedicaments() {
        return ResponseEntity.ok(this.medicamentService.getAllMedicaments());
    }

    @GetMapping(value={"/{id}"})
    @Operation(summary="Détail d'un médicament par ID")
    public ResponseEntity<MedicamentDto> getMedicamentById(@PathVariable Long id) {
        return ResponseEntity.ok(this.medicamentService.getMedicamentById(id));
    }

    @PostMapping
    @Operation(summary="Ajouter un médicament au stock")
    public ResponseEntity<Map<String, Object>> ajouterMedicament(@Valid @RequestBody MedicamentDto dto) {
        MedicamentDto saved = this.medicamentService.ajouterMedicament(dto);
        return ResponseEntity.status((HttpStatusCode)HttpStatus.CREATED).body(Map.of("succés", true, "medicament", saved));
    }

    @PutMapping(value={"/{id}"})
    @Operation(summary="Modifier un médicament")
    public ResponseEntity<Map<String, Object>> modifierMedicament(@PathVariable Long id, @RequestBody MedicamentDto dto) {
        MedicamentDto updated = this.medicamentService.modifierMedicament(id, dto);
        return ResponseEntity.ok(Map.of("succés", true, "medicament", updated));
    }

    @DeleteMapping(value={"/{id}"})
    @Operation(summary="Supprimer un médicament")
    public ResponseEntity<Map<String, Object>> supprimerMedicament(@PathVariable Long id) {
        this.medicamentService.supprimerMedicament(id);
        return ResponseEntity.ok(Map.of("succes", true));
    }

    @GetMapping(value={"/alertes"})
    @Operation(summary="Alertes de stock faible")
    public ResponseEntity<List<Map<String, Object>>> getAlertes() {
        List<MedicamentDto> alertes = this.medicamentService.getAlertes();
        List<Map<String, Object>> result = alertes.stream().map(m -> Map.of("nom", (Object)m.getNom(), "quantite", m.getQuantite(), "seuil_alerte", m.getSeuilAlerte(), "message", "Stock faible : quantité actuelle (" + m.getQuantite() + ") ≤ seuil d'alerte (" + m.getSeuilAlerte() + ")")).toList();
        return ResponseEntity.ok(result);
    }
}

