/*
 * Decompiled with CFR 0.152.
 */
package com.pharma.approvisionnement.service;

import com.pharma.approvisionnement.dto.GrossisteSearchResult;
import com.pharma.approvisionnement.entity.PharmacieCliente;
import com.pharma.approvisionnement.entity.StockGrossiste;
import java.util.List;
import java.util.Map;

public interface GrossisteService {
    public List<GrossisteSearchResult> rechercherGrossistesParMed(String var1);

    public Map<String, Object> getDashboard(String var1);

    public List<StockGrossiste> getProduits(String var1);

    public Map<String, Object> getAlertesStock(String var1);

    public StockGrossiste ajouterProduit(String var1, StockGrossiste var2);

    public StockGrossiste modifierProduit(Long var1, StockGrossiste var2);

    public void supprimerProduit(Long var1);

    public List<PharmacieCliente> getClients(String var1);

    public PharmacieCliente ajouterClient(String var1, PharmacieCliente var2);

    public PharmacieCliente modifierClient(Long var1, PharmacieCliente var2);

    public void supprimerClient(Long var1);

    public Map<String, Object> getCommandesRecues(String var1);

    public Map<String, Object> mettreAJourStatut(Long var1, String var2, String var3);
}

