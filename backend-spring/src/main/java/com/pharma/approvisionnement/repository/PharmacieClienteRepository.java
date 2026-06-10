/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.jpa.repository.JpaRepository
 *  org.springframework.stereotype.Repository
 */
package com.pharma.approvisionnement.repository;

import com.pharma.approvisionnement.entity.PharmacieCliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacieClienteRepository
extends JpaRepository<PharmacieCliente, Long> {
    public List<PharmacieCliente> findByNomGrossisteOrderByNomPharmacieAsc(String var1);
}

