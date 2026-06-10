/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.pharma.approvisionnement.dto.CommandeDto
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 */
package com.pharma.approvisionnement.service.impl;

import com.pharma.approvisionnement.dto.CommandeDto;
import com.pharma.approvisionnement.dto.CommandeRequest;
import com.pharma.approvisionnement.entity.Commande;
import com.pharma.approvisionnement.entity.CommandeRecue;
import com.pharma.approvisionnement.entity.StockGrossiste;
import com.pharma.approvisionnement.exception.BusinessException;
import com.pharma.approvisionnement.exception.ResourceNotFoundException;
import com.pharma.approvisionnement.repository.CommandeRecueRepository;
import com.pharma.approvisionnement.repository.CommandeRepository;
import com.pharma.approvisionnement.repository.MedicamentRepository;
import com.pharma.approvisionnement.repository.StockGrossisteRepository;
import com.pharma.approvisionnement.service.CommandeService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommandeServiceImpl
implements CommandeService {
    private static final Logger log = LoggerFactory.getLogger(CommandeServiceImpl.class);
    private final CommandeRepository commandeRepository;
    private final StockGrossisteRepository stockGrossisteRepository;
    private final MedicamentRepository medicamentRepository;
    private final CommandeRecueRepository commandeRecueRepository;

    public CommandeServiceImpl(CommandeRepository commandeRepository, StockGrossisteRepository stockGrossisteRepository, MedicamentRepository medicamentRepository, CommandeRecueRepository commandeRecueRepository) {
        this.commandeRepository = commandeRepository;
        this.stockGrossisteRepository = stockGrossisteRepository;
        this.medicamentRepository = medicamentRepository;
        this.commandeRecueRepository = commandeRecueRepository;
    }

    @Override
    @Transactional(readOnly=true)
    public List<CommandeDto> getHistoriqueCommandes() {
        return this.commandeRepository.findAllByOrderByDateCmdDesc().stream().map(CommandeDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> passerCommande(CommandeRequest request) {
        String nomMed = request.getNom();
        String grossiste = request.getGrossiste();
        int quantite = request.getQte();
        double prixTotal = request.getPrixTotal() != null ? request.getPrixTotal() : 0.0;
        StockGrossiste stockGrossiste = this.stockGrossisteRepository.findByNomMedIgnoreCaseAndNomGrossiste(nomMed, grossiste).orElseThrow(() -> new BusinessException("Le m\u00e9dicament '" + nomMed + "' est introuvable chez " + grossiste));
        if (stockGrossiste.getStockDispo() < quantite) {
            return Map.of("succes", false, "message", "Le grossiste n'a pas assez de stock (" + String.valueOf(stockGrossiste.getStockDispo()) + " disponible)");
        }
        // La déduction du stock est désormais gérée par le trigger MySQL sync_to_grossiste
        // pour garantir la synchronisation en temps réel avec la vraie base de données du grossiste.
        Commande commande = Commande.builder().nomMed(nomMed).grossiste(grossiste).quantite(quantite).prixTotal(prixTotal).dateCmd(LocalDateTime.now()).statut(Commande.StatutCommande.EN_ATTENTE).build();
        Commande savedCommande = (Commande)this.commandeRepository.save(commande);
        CommandeRecue commandeRecue = CommandeRecue.builder().idPharmacieCmd(savedCommande.getId()).nomMed(nomMed).quantite(quantite).prixTotal(prixTotal).dateCmd(LocalDateTime.now()).statut(Commande.StatutCommande.EN_ATTENTE).nomGrossiste(grossiste).build();
        this.commandeRecueRepository.save(commandeRecue);
        log.info("Commande pass\u00e9e : {} x{} chez {}", new Object[]{nomMed, quantite, grossiste});
        return Map.of("succes", true, "message", "Commande envoy\u00e9e au grossiste. En attente de livraison.");
    }

    @Override
    public Map<String, Object> validerLivraison(Long id) {
        Commande commande = (Commande)this.commandeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commande", id));
        if (commande.getStatut() == Commande.StatutCommande.LIVREE) {
            return Map.of("succes", false, "message", "Cette commande a d\u00e9j\u00e0 \u00e9t\u00e9 r\u00e9ceptionn\u00e9e.");
        }
        this.medicamentRepository.findByNomContainingIgnoreCase(commande.getNomMed()).stream().findFirst().ifPresent(med -> {
            med.setQuantite(med.getQuantite() + commande.getQuantite());
            this.medicamentRepository.save(med);
            log.info("Stock mis \u00e0 jour : {} +{}", (Object)med.getNom(), (Object)commande.getQuantite());
        });
        commande.setStatut(Commande.StatutCommande.LIVREE);
        this.commandeRepository.save(commande);
        return Map.of("succes", true, "message", "R\u00e9ception confirm\u00e9e ! Le stock de " + commande.getNomMed() + " a \u00e9t\u00e9 mis \u00e0 jour.");
    }
}

