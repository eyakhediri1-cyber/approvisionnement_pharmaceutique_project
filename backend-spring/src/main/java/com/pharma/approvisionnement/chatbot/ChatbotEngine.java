/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.stereotype.Component
 */
package com.pharma.approvisionnement.chatbot;

import com.pharma.approvisionnement.entity.Commande;
import com.pharma.approvisionnement.entity.Medicament;
import com.pharma.approvisionnement.entity.StockGrossiste;
import com.pharma.approvisionnement.repository.CommandeRepository;
import com.pharma.approvisionnement.repository.GrossisteClientRepository;
import com.pharma.approvisionnement.repository.MedicamentRepository;
import com.pharma.approvisionnement.repository.StockGrossisteRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChatbotEngine {
    private static final Logger log = LoggerFactory.getLogger(ChatbotEngine.class);
    private final MedicamentRepository medicamentRepository;
    private final CommandeRepository commandeRepository;
    private final StockGrossisteRepository stockGrossisteRepository;
    private final GrossisteClientRepository grossisteClientRepository;
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ChatbotEngine(MedicamentRepository medicamentRepository, CommandeRepository commandeRepository, StockGrossisteRepository stockGrossisteRepository, GrossisteClientRepository grossisteClientRepository) {
        this.medicamentRepository = medicamentRepository;
        this.commandeRepository = commandeRepository;
        this.stockGrossisteRepository = stockGrossisteRepository;
        this.grossisteClientRepository = grossisteClientRepository;
    }

    private String dernieresCommandes() {
        List<Commande> liste = this.commandeRepository.findTop10ByOrderByDateCmdDesc();
        if (liste.isEmpty()) {
            return "Aucune commande enregistr\u00e9e.";
        }
        StringBuilder sb = new StringBuilder("\ud83d\udd50 **10 derni\u00e8res commandes** :\n\n");
        liste.forEach(c -> {
            StringBuilder stringBuilder2 = sb.append("\u2022 **#").append(c.getId()).append("** \u2014 ").append(c.getNomMed()).append(" \u00d7 ").append(c.getQuantite()).append(" \u2014 ").append(c.getStatut().getLabel()).append(" \u2014 ").append(c.getDateCmd().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        });
        return sb.toString();
    }

    private String statutCommande(String msg) {
        Pattern p = Pattern.compile("\\b(\\d+)\\b");
        Matcher m = p.matcher(msg);
        if (m.find()) {
            Long id = Long.parseLong(m.group(1));
            return this.commandeRepository.findById(id).map(c -> "\ud83d\udce6 **Commande #" + String.valueOf(c.getId()) + "** :\n\u2022 M\u00e9dicament : " + c.getNomMed() + "\n\u2022 Quantit\u00e9 : " + String.valueOf(c.getQuantite()) + "\n\u2022 Grossiste : " + c.getGrossiste() + "\n\u2022 Statut : **" + c.getStatut().getLabel() + "**\n\u2022 Date : " + c.getDateCmd().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).orElse("\u274c Commande #" + String.valueOf(id) + " introuvable.");
        }
        return "Veuillez pr\u00e9ciser le num\u00e9ro de commande. Ex : 'statut commande 42'";
    }

    private String resumeCommandes() {
        long total = this.commandeRepository.count();
        long attente = this.commandeRepository.countByStatut(Commande.StatutCommande.EN_ATTENTE);
        long livrees = this.commandeRepository.countByStatut(Commande.StatutCommande.LIVREE);
        return "\ud83d\udcca **R\u00e9sum\u00e9 des commandes** :\n\n\u2022 Total commandes : **" + total + "**\n\u2022 En attente : **" + attente + "**\n\u2022 Livr\u00e9es : **" + livrees + "**\n\n_Tapez 'commandes en attente' pour voir le d\u00e9tail._";
    }

    private String produitsEnRupture() {
        List<Medicament> ruptures = this.medicamentRepository.findAll().stream().filter(m -> m.getQuantite() == 0).collect(Collectors.toList());
        if (ruptures.isEmpty()) {
            return "\u2705 Aucun produit en rupture de stock.";
        }
        StringBuilder sb = new StringBuilder("\ud83d\udea8 **Produits en rupture de stock** (" + ruptures.size() + ") :\n\n");
        ruptures.forEach(m -> {
            StringBuilder stringBuilder2 = sb.append("\u2022 ").append(m.getNom()).append("\n");
        });
        return sb.toString();
    }

    private String alertesStock() {
        List<Medicament> alertes = this.medicamentRepository.findAlertes();
        if (alertes.isEmpty()) {
            return "\u2705 Tous les stocks sont au-dessus des seuils d'alerte.";
        }
        StringBuilder sb = new StringBuilder("\u26a0\ufe0f **Alertes de stock faible** (" + alertes.size() + ") :\n\n");
        alertes.forEach(m -> {
            StringBuilder stringBuilder2 = sb.append("\u2022 **").append(m.getNom()).append("** : ").append(m.getQuantite()).append(" unit\u00e9s (seuil : ").append(m.getSeuilAlerte()).append(")\n");
        });
        return sb.toString();
    }

    private String inventaireGlobal() {
        List<Medicament> all = this.medicamentRepository.findAll();
        long total = all.size();
        long rupture = all.stream().filter(m -> m.getQuantite() == 0).count();
        long faible = all.stream().filter(m -> m.getQuantite() > 0 && m.getQuantite() <= m.getSeuilAlerte()).count();
        double valeur = all.stream().mapToDouble(m -> m.getPrix() * (double)m.getQuantite().intValue()).sum();
        return "\ud83c\udfe5 **Inventaire global du stock** :\n\n\u2022 Total m\u00e9dicaments : **" + total + "**\n\u2022 En rupture (stock = 0) : **" + rupture + "**\n\u2022 Stock faible : **" + faible + "**\n\u2022 Valeur totale : **" + String.format("%.2f DT", valeur) + "**";
    }

    public String analyser(String message) {
        if (message == null || message.isBlank()) {
            return this.reponseDefaut();
        }
        String msg = message.toLowerCase().trim();
        if (this.contient(msg, "commandes en attente", "commandes non trait\u00e9es", "commandes non traitees", "en attente", "non trait\u00e9es", "non traitees")) {
            return this.commandesEnAttente();
        }
        if (this.contient(msg, "commandes du jour", "commandes aujourd'hui", "commandes aujourd hui", "aujourd'hui", "aujourd hui")) {
            return this.commandesDuJour();
        }
        if (this.contient(msg, "derni\u00e8res commandes", "dernieres commandes", "derni\u00e8res", "dernieres")) {
            return this.dernieresCommandes();
        }
        if (this.contient(msg, "statut commande", "commande num\u00e9ro", "commande numero")) {
            return this.statutCommande(msg);
        }
        if (this.contient(msg, "commande", "commandes")) {
            return this.resumeCommandes();
        }
        if (this.contient(msg, "rupture de stock", "rupture stock", "en rupture", "\u00e9puis\u00e9", "epuise", "stock 0", "stock z\u00e9ro", "stock zero")) {
            return this.produitsEnRupture();
        }
        if (this.contient(msg, "stock faible", "alertes stock", "alerte stock", "sous seuil", "stock bas", "stock critique")) {
            return this.alertesStock();
        }
        if (this.contient(msg, "inventaire", "r\u00e9sum\u00e9 stock", "resume stock", "\u00e9tat du stock", "etat du stock", "bilan stock")) {
            return this.inventaireGlobal();
        }
        if (this.contient(msg, "stock de", "stock du", "quantit\u00e9 de", "quantite de", "combien de")) {
            return this.stockDeProduit(msg);
        }
        if (this.contient(msg, "p\u00e9remption", "peremption", "expire", "expir\u00e9", "date limite")) {
            return this.alertesPeremption();
        }
        if (this.contient(msg, "liste des fournisseurs", "fournisseurs actifs", "tous les fournisseurs", "fournisseurs disponibles")) {
            return this.listeFournisseurs();
        }
        if (this.contient(msg, "fournisseur de", "fournisseur du", "qui fournit", "grossiste de", "grossiste du")) {
            return this.fournisseurDeProduit(msg);
        }
        if (this.contient(msg, "contacter", "coordonn\u00e9es", "coordonnees", "contact")) {
            return this.coordonneesFournisseur(msg);
        }
        if (this.contient(msg, "fournisseur", "grossiste", "grossistes")) {
            return this.listeFournisseurs();
        }
        if (this.contient(msg, "aide", "help", "quoi", "que peux", "que puis", "fonctions", "capacit\u00e9s", "capacites")) {
            return this.aide();
        }
        if (this.contient(msg, "bonjour", "salut", "hello", "bonsoir", "coucou")) {
            return this.salutation();
        }
        return this.reponseDefaut();
    }

    private String reponseDefaut() {
        return "\ud83e\udd14 Je n'ai pas compris votre demande.\n\nVous pouvez me poser des questions sur :\n\u2022 Les **commandes** (ex : 'commandes en attente')\n\u2022 Le **stock** (ex : 'alertes stock', 'stock de parac\u00e9tamol')\n\u2022 Les **fournisseurs** (ex : 'liste des fournisseurs')\n\nTapez _'aide'_ pour voir toutes mes capacit\u00e9s.";
    }

    private boolean contient(String msg, String ... keywords) {
        String[] stringArray = keywords;
        int n = keywords.length;
        int n2 = 0;
        while (n2 < n) {
            String kw = stringArray[n2];
            if (msg.contains(kw)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private String commandesEnAttente() {
        List<Commande> liste = this.commandeRepository.findByStatut(Commande.StatutCommande.EN_ATTENTE);
        if (liste.isEmpty()) {
            return "\u2705 Aucune commande en attente pour le moment.";
        }
        StringBuilder sb = new StringBuilder("\ud83d\udccb **Commandes en attente** (" + liste.size() + ") :\n\n");
        liste.stream().limit(10L).forEach(c -> {
            StringBuilder stringBuilder2 = sb.append("\u2022 **#").append(c.getId()).append("** \u2014 ").append(c.getNomMed()).append(" \u00d7 ").append(c.getQuantite()).append(" (").append(c.getGrossiste()).append(")").append(" \u2014 ").append(c.getDateCmd().format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))).append("\n");
        });
        if (liste.size() > 10) {
            sb.append("\n\u2026 et ").append(liste.size() - 10).append(" autres.");
        }
        return sb.toString();
    }

    private String commandesDuJour() {
        List<Commande> liste = this.commandeRepository.findCommandesDuJour();
        if (liste.isEmpty()) {
            return "\ud83d\udcc5 Aucune commande pass\u00e9e aujourd'hui.";
        }
        StringBuilder sb = new StringBuilder("\ud83d\udcc5 **Commandes d'aujourd'hui** (" + liste.size() + ") :\n\n");
        liste.forEach(c -> {
            StringBuilder stringBuilder2 = sb.append("\u2022 **#").append(c.getId()).append("** \u2014 ").append(c.getNomMed()).append(" \u00d7 ").append(c.getQuantite()).append(" \u2014 Statut : ").append(c.getStatut().getLabel()).append("\n");
        });
        return sb.toString();
    }

    private String stockDeProduit(String msg) {
        String nomProduit = this.extraireApres(msg, "stock de", "stock du", "quantit\u00e9 de", "quantite de", "combien de");
        if (nomProduit.isBlank()) {
            return "Veuillez pr\u00e9ciser le nom du m\u00e9dicament. Ex : 'stock de parac\u00e9tamol'";
        }
        List<Medicament> trouv\u00e9s = this.medicamentRepository.findByNomContainingIgnoreCase(nomProduit);
        if (trouv\u00e9s.isEmpty()) {
            return "\u274c M\u00e9dicament '" + nomProduit + "' non trouv\u00e9 dans votre stock.";
        }
        StringBuilder sb = new StringBuilder("\ud83d\udce6 **Stock de '" + nomProduit + "'** :\n\n");
        trouv\u00e9s.forEach(m -> {
            String etat = m.getQuantite() == 0 ? "\ud83d\udd34 Rupture" : (m.getQuantite() <= m.getSeuilAlerte() ? "\ud83d\udfe0 Faible" : "\ud83d\udfe2 OK");
            sb.append("\u2022 **").append(m.getNom()).append("** : ").append(m.getQuantite()).append(" unit\u00e9s ").append(etat).append("\n");
        });
        return sb.toString();
    }

    private String alertesPeremption() {
        LocalDate dans6Mois = LocalDate.now().plusMonths(6L);
        List<Medicament> expir = this.medicamentRepository.findAll().stream().filter(m -> m.getDatePeremption() != null && m.getDatePeremption().isBefore(dans6Mois)).sorted((a, b) -> a.getDatePeremption().compareTo(b.getDatePeremption())).collect(Collectors.toList());
        if (expir.isEmpty()) {
            return "\u2705 Aucune p\u00e9remption prochaine dans les 6 mois.";
        }
        StringBuilder sb = new StringBuilder("\u23f0 **P\u00e9remptions dans les 6 prochains mois** (" + expir.size() + ") :\n\n");
        expir.forEach(m -> {
            StringBuilder stringBuilder2 = sb.append("\u2022 **").append(m.getNom()).append("** \u2014 expire le ").append(m.getDatePeremption().format(DATE_FMT)).append("\n");
        });
        return sb.toString();
    }

    private String listeFournisseurs() {
        List<String> noms = List.of("MedicaLab", "MeDistrib", "PharmRestock");
        return "\ud83c\udfed **Grossistes partenaires disponibles** :\n\n\u2022 **MedicaLab** \u2014 M\u00e9dicaments g\u00e9n\u00e9riques et de marque\n\u2022 **MeDistrib** \u2014 Distribution nationale rapide\n\u2022 **PharmRestock** \u2014 R\u00e9approvisionnement d'urgence\n\n_Pour rechercher un m\u00e9dicament chez un grossiste, allez dans 'Stock' et cliquez sur 'Commander'._";
    }

    private String fournisseurDeProduit(String msg) {
        String nomProduit = this.extraireApres(msg, "fournisseur de", "fournisseur du", "grossiste de", "grossiste du", "qui fournit");
        if (nomProduit.isBlank()) {
            return "Pr\u00e9cisez le m\u00e9dicament. Ex : 'fournisseur de parac\u00e9tamol'";
        }
        List<StockGrossiste> stocks = this.stockGrossisteRepository.findByNomMedIgnoreCaseAndStockDispoGreaterThan(nomProduit);
        if (stocks.isEmpty()) {
            return "\u274c Aucun grossiste ne propose '" + nomProduit + "' en stock actuellement.";
        }
        StringBuilder sb = new StringBuilder("\ud83d\udd0d **Fournisseurs de '" + nomProduit + "'** :\n\n");
        stocks.stream().sorted((a, b) -> Double.compare(a.getPrixUnitaire(), b.getPrixUnitaire())).forEach(s -> {
            StringBuilder stringBuilder2 = sb.append("\u2022 **").append(s.getNomGrossiste()).append("** \u2014 ").append(s.getStockDispo()).append(" unit\u00e9s \u00e0 ").append(String.format("%.2f DT/u", s.getPrixUnitaire())).append("\n");
        });
        return sb.toString();
    }

    private String coordonneesFournisseur(String msg) {
        String msgLower = msg.toLowerCase();
        if (msgLower.contains("medicalab") || msgLower.contains("medical")) {
            return "\ud83d\udcde **MedicaLab** :\n\u2022 T\u00e9l : +216 71 000 001\n\u2022 Email : contact@medicalab.tn\n\u2022 Adresse : Tunis, Zone Industrielle";
        }
        if (msgLower.contains("medistrib") || msgLower.contains("distrib")) {
            return "\ud83d\udcde **MeDistrib** :\n\u2022 T\u00e9l : +216 73 000 002\n\u2022 Email : info@medistrib.tn\n\u2022 Adresse : Sfax, Route de l'A\u00e9roport";
        }
        if (msgLower.contains("pharmrestock") || msgLower.contains("restock")) {
            return "\ud83d\udcde **PharmRestock** :\n\u2022 T\u00e9l : +216 74 000 003\n\u2022 Email : support@pharmrestock.tn\n\u2022 Adresse : Sousse, Centre Commercial";
        }
        return "Pr\u00e9cisez le nom du fournisseur (MedicaLab, MeDistrib ou PharmRestock).";
    }

    private String aide() {
        return "\ud83d\udca1 **Ce que je sais faire** :\n\n**\ud83d\udccb Commandes :**\n\u2022 _'commandes en attente'_\n\u2022 _'commandes du jour'_\n\u2022 _'derni\u00e8res commandes'_\n\u2022 _'statut commande 42'_\n\n**\ud83d\udce6 Stocks :**\n\u2022 _'produits en rupture'_\n\u2022 _'alertes stock'_\n\u2022 _'stock de parac\u00e9tamol'_\n\u2022 _'inventaire'_\n\u2022 _'p\u00e9remptions'_\n\n**\ud83c\udfed Fournisseurs :**\n\u2022 _'liste des fournisseurs'_\n\u2022 _'fournisseur de doliprane'_\n\u2022 _'contacter medicalab'_";
    }

    private String salutation() {
        return "\ud83d\udc4b Bonjour ! Je suis **PharmaBot**, votre assistant de gestion pharmaceutique.\n\nJe peux vous aider avec :\n\u2022 \ud83d\udccb Vos **commandes** (en attente, du jour, historique)\n\u2022 \ud83d\udce6 Votre **stock** (alertes, ruptures, inventaire)\n\u2022 \ud83c\udfed Vos **fournisseurs** (liste, recherche, contacts)\n\nQue puis-je faire pour vous ?";
    }

    private String extraireApres(String msg, String ... prefixes) {
        String[] stringArray = prefixes;
        int n = prefixes.length;
        int n2 = 0;
        while (n2 < n) {
            String prefix = stringArray[n2];
            int idx = msg.indexOf(prefix);
            if (idx >= 0) {
                return msg.substring(idx + prefix.length()).trim();
            }
            ++n2;
        }
        return "";
    }
}

