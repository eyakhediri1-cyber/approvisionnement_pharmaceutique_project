/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.jpa.repository.JpaRepository
 *  org.springframework.data.jpa.repository.Query
 *  org.springframework.stereotype.Repository
 */
package com.pharma.approvisionnement.repository;

import com.pharma.approvisionnement.entity.Medicament;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentRepository
extends JpaRepository<Medicament, Long> {
    @Query(value="SELECT m FROM Medicament m WHERE m.quantite <= m.seuilAlerte")
    public List<Medicament> findAlertes();

    public List<Medicament> findByNomContainingIgnoreCase(String var1);
}

