/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.jpa.repository.JpaRepository
 *  org.springframework.stereotype.Repository
 */
package com.pharma.approvisionnement.repository;

import com.pharma.approvisionnement.entity.Commande;
import com.pharma.approvisionnement.entity.CommandeRecue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRecueRepository
extends JpaRepository<CommandeRecue, Long> {
    public List<CommandeRecue> findByNomGrossisteOrderByDateCmdDesc(String var1);

    public long countByNomGrossisteAndStatut(String var1, Commande.StatutCommande var2);
}

