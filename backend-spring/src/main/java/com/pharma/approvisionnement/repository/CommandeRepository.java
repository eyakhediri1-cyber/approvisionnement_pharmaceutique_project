/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.jpa.repository.JpaRepository
 *  org.springframework.data.jpa.repository.Query
 *  org.springframework.stereotype.Repository
 */
package com.pharma.approvisionnement.repository;

import com.pharma.approvisionnement.entity.Commande;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository
extends JpaRepository<Commande, Long> {
    public List<Commande> findByStatut(Commande.StatutCommande var1);

    @Query(value="SELECT c FROM Commande c WHERE DATE(c.dateCmd) = CURRENT_DATE ORDER BY c.dateCmd DESC")
    public List<Commande> findCommandesDuJour();

    public List<Commande> findTop10ByOrderByDateCmdDesc();

    public long countByStatut(Commande.StatutCommande var1);

    public List<Commande> findAllByOrderByDateCmdDesc();
}

