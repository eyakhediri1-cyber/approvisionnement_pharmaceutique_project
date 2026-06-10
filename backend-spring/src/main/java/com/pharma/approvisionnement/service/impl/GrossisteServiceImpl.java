/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 */
package com.pharma.approvisionnement.service.impl;

import com.pharma.approvisionnement.dto.GrossisteSearchResult;
import com.pharma.approvisionnement.entity.Commande;
import com.pharma.approvisionnement.entity.CommandeRecue;
import com.pharma.approvisionnement.entity.PharmacieCliente;
import com.pharma.approvisionnement.entity.StockGrossiste;
import com.pharma.approvisionnement.exception.ResourceNotFoundException;
import com.pharma.approvisionnement.repository.CommandeRecueRepository;
import com.pharma.approvisionnement.repository.CommandeRepository;
import com.pharma.approvisionnement.repository.PharmacieClienteRepository;
import com.pharma.approvisionnement.repository.StockGrossisteRepository;
import com.pharma.approvisionnement.service.GrossisteService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GrossisteServiceImpl
implements GrossisteService {
    private static final Logger log = LoggerFactory.getLogger(GrossisteServiceImpl.class);
    private final StockGrossisteRepository stockGrossisteRepository;
    private final PharmacieClienteRepository pharmacieClienteRepository;
    private final CommandeRecueRepository commandeRecueRepository;
    private final CommandeRepository commandeRepository;

    public GrossisteServiceImpl(StockGrossisteRepository stockGrossisteRepository, PharmacieClienteRepository pharmacieClienteRepository, CommandeRecueRepository commandeRecueRepository, CommandeRepository commandeRepository) {
        this.stockGrossisteRepository = stockGrossisteRepository;
        this.pharmacieClienteRepository = pharmacieClienteRepository;
        this.commandeRecueRepository = commandeRecueRepository;
        this.commandeRepository = commandeRepository;
    }

    @Override
    @Transactional(readOnly=true)
    public List<GrossisteSearchResult> rechercherGrossistesParMed(String nomMed) {
        return this.stockGrossisteRepository.findByNomMedIgnoreCaseAndStockDispoGreaterThan(nomMed).stream().map(s -> GrossisteSearchResult.builder().nomGrossiste(s.getNomGrossiste()).stock(s.getStockDispo()).prix(s.getPrixUnitaire()).medicament(s.getNomMed()).build()).sorted(Comparator.comparingDouble(GrossisteSearchResult::getPrix)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly=true)
    public Map<String, Object> getDashboard(String nomGrossiste) {
        long totalProduits = this.stockGrossisteRepository.findByNomGrossiste(nomGrossiste).size();
        long totalClients = this.pharmacieClienteRepository.findByNomGrossisteOrderByNomPharmacieAsc(nomGrossiste).size();
        long commandesAttente = this.commandeRecueRepository.countByNomGrossisteAndStatut(nomGrossiste, Commande.StatutCommande.EN_ATTENTE);
        List dernieres = this.commandeRecueRepository.findByNomGrossisteOrderByDateCmdDesc(nomGrossiste).stream().limit(5L).collect(Collectors.toList());
        List<StockGrossiste> alertesStock = this.stockGrossisteRepository.findAlertesStock(nomGrossiste);
        List<StockGrossiste> alertesPeremption = this.stockGrossisteRepository.findAlertesPeremption(nomGrossiste, LocalDate.now().plusDays(90L));
        LinkedHashMap<String, Object> dashboard = new LinkedHashMap<String, Object>();
        dashboard.put("grossiste", nomGrossiste);
        dashboard.put("total_produits", totalProduits);
        dashboard.put("total_clients", totalClients);
        dashboard.put("commandes_attente", commandesAttente);
        dashboard.put("commandes", Map.of("commandes", dernieres));
        dashboard.put("alertes_stock", alertesStock);
        dashboard.put("alertes_peremption", alertesPeremption);
        return dashboard;
    }

    @Override
    @Transactional(readOnly=true)
    public List<StockGrossiste> getProduits(String nomGrossiste) {
        return this.stockGrossisteRepository.findByNomGrossiste(nomGrossiste);
    }

    @Override
    @Transactional(readOnly=true)
    public Map<String, Object> getAlertesStock(String nomGrossiste) {
        List<StockGrossiste> produits = this.stockGrossisteRepository.findByNomGrossiste(nomGrossiste);
        List alertesStock = produits.stream().filter(p -> p.getStockDispo() <= p.getSeuilAlerte()).map(p -> Map.of("nom", p.getNomMed(), "message", "R\u00e9approvisionnement n\u00e9cessaire (" + String.valueOf(p.getStockDispo()) + " restants)")).collect(Collectors.toList());
        List alertesPeremption = produits.stream().filter(p -> p.getDatePeremption() != null && p.getDatePeremption().isBefore(LocalDateTime.now().plusMonths(3L).toLocalDate())).map(p -> Map.of("nom", p.getNomMed(), "date", p.getDatePeremption().toString(), "message", "P\u00e9remption proche !")).collect(Collectors.toList());
        return Map.of("produits", produits, "alertes_stock", alertesStock, "alertes_peremption", alertesPeremption);
    }

    @Override
    public StockGrossiste ajouterProduit(String nomGrossiste, StockGrossiste produit) {
        produit.setNomGrossiste(nomGrossiste);
        StockGrossiste saved = (StockGrossiste)this.stockGrossisteRepository.save(produit);
        log.info("Produit ajout\u00e9 chez {} : {}", (Object)nomGrossiste, (Object)produit.getNomMed());
        return saved;
    }

    @Override
    public StockGrossiste modifierProduit(Long id, StockGrossiste update) {
        StockGrossiste existing = (StockGrossiste)this.stockGrossisteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produit grossiste", id));
        if (update.getNomMed() != null) {
            existing.setNomMed(update.getNomMed());
        }
        if (update.getPrixUnitaire() != null) {
            existing.setPrixUnitaire(update.getPrixUnitaire());
        }
        if (update.getStockDispo() != null) {
            existing.setStockDispo(update.getStockDispo());
        }
        if (update.getSeuilAlerte() != null) {
            existing.setSeuilAlerte(update.getSeuilAlerte());
        }
        if (update.getDatePeremption() != null) {
            existing.setDatePeremption(update.getDatePeremption());
        }
        return (StockGrossiste)this.stockGrossisteRepository.save(existing);
    }

    @Override
    public void supprimerProduit(Long id) {
        if (!this.stockGrossisteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produit grossiste", id);
        }
        this.stockGrossisteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly=true)
    public List<PharmacieCliente> getClients(String nomGrossiste) {
        return this.pharmacieClienteRepository.findByNomGrossisteOrderByNomPharmacieAsc(nomGrossiste);
    }

    @Override
    public PharmacieCliente ajouterClient(String nomGrossiste, PharmacieCliente client) {
        client.setNomGrossiste(nomGrossiste);
        PharmacieCliente saved = (PharmacieCliente)this.pharmacieClienteRepository.save(client);
        log.info("Client ajout\u00e9 chez {} : {}", (Object)nomGrossiste, (Object)client.getNomPharmacie());
        return saved;
    }

    @Override
    public PharmacieCliente modifierClient(Long id, PharmacieCliente update) {
        PharmacieCliente existing = (PharmacieCliente)this.pharmacieClienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client pharmacie", id));
        if (update.getNomPharmacie() != null) {
            existing.setNomPharmacie(update.getNomPharmacie());
        }
        if (update.getNomResponsable() != null) {
            existing.setNomResponsable(update.getNomResponsable());
        }
        if (update.getAdresse() != null) {
            existing.setAdresse(update.getAdresse());
        }
        if (update.getTelephone() != null) {
            existing.setTelephone(update.getTelephone());
        }
        if (update.getEmail() != null) {
            existing.setEmail(update.getEmail());
        }
        if (update.getVille() != null) {
            existing.setVille(update.getVille());
        }
        return (PharmacieCliente)this.pharmacieClienteRepository.save(existing);
    }

    @Override
    public void supprimerClient(Long id) {
        if (!this.pharmacieClienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client pharmacie", id);
        }
        this.pharmacieClienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly=true)
    public Map<String, Object> getCommandesRecues(String nomGrossiste) {
        List<CommandeRecue> commandes = this.commandeRecueRepository.findByNomGrossisteOrderByDateCmdDesc(nomGrossiste);
        long nbNonTraitees = this.commandeRecueRepository.countByNomGrossisteAndStatut(nomGrossiste, Commande.StatutCommande.EN_ATTENTE);
        return Map.of("succes", true, "commandes", commandes, "nb_non_traitees", nbNonTraitees);
    }

    @Override
    public Map<String, Object> mettreAJourStatut(Long id, String statut, String nomGrossiste) {
        CommandeRecue commandeRecue = (CommandeRecue)this.commandeRecueRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Commande re\u00e7ue", id));
        Commande.StatutCommande newStatut = Commande.StatutCommande.fromLabel(statut);
        commandeRecue.setStatut(newStatut);
        commandeRecue.setDateTraitement(LocalDateTime.now());
        this.commandeRecueRepository.save(commandeRecue);
        if (commandeRecue.getIdPharmacieCmd() != null) {
            this.commandeRepository.findById(commandeRecue.getIdPharmacieCmd()).ifPresent(cmd -> {
                cmd.setStatut(newStatut);
                this.commandeRepository.save(cmd);
            });
        }
        log.info("Statut commande {} mis \u00e0 jour : {}", (Object)id, (Object)statut);
        return Map.of("succes", true, "message", "Statut mis \u00e0 jour : " + statut);
    }
}

