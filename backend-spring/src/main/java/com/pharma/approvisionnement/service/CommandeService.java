/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.pharma.approvisionnement.dto.CommandeDto
 */
package com.pharma.approvisionnement.service;

import com.pharma.approvisionnement.dto.CommandeDto;
import com.pharma.approvisionnement.dto.CommandeRequest;
import java.util.List;
import java.util.Map;

public interface CommandeService {
    public List<CommandeDto> getHistoriqueCommandes();

    public Map<String, Object> passerCommande(CommandeRequest var1);

    public Map<String, Object> validerLivraison(Long var1);
}

