/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.jpa.repository.JpaRepository
 *  org.springframework.stereotype.Repository
 */
package com.pharma.approvisionnement.repository;

import com.pharma.approvisionnement.entity.GrossisteClient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrossisteClientRepository
extends JpaRepository<GrossisteClient, Long> {
    public Optional<GrossisteClient> findByNomGrossiste(String var1);
}

