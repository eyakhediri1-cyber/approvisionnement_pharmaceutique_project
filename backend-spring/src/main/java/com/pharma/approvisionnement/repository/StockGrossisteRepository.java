/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.jpa.repository.JpaRepository
 *  org.springframework.data.jpa.repository.Query
 *  org.springframework.data.repository.query.Param
 *  org.springframework.stereotype.Repository
 */
package com.pharma.approvisionnement.repository;

import com.pharma.approvisionnement.entity.StockGrossiste;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockGrossisteRepository
extends JpaRepository<StockGrossiste, Long> {
    @Query(value="SELECT s FROM StockGrossiste s WHERE LOWER(s.nomMed) = LOWER(:nom) AND s.stockDispo > 0")
    public List<StockGrossiste> findByNomMedIgnoreCaseAndStockDispoGreaterThan(@Param(value="nom") String var1);

    public Optional<StockGrossiste> findByNomMedIgnoreCaseAndNomGrossiste(String var1, String var2);

    public List<StockGrossiste> findByNomGrossiste(String var1);

    @Query(value="SELECT s FROM StockGrossiste s WHERE s.nomGrossiste = :ng AND s.stockDispo <= s.seuilAlerte")
    public List<StockGrossiste> findAlertesStock(@Param(value="ng") String var1);

    @Query(value="SELECT s FROM StockGrossiste s WHERE s.nomGrossiste = :ng AND s.datePeremption IS NOT NULL AND s.datePeremption <= :limite")
    public List<StockGrossiste> findAlertesPeremption(@Param(value="ng") String var1, @Param(value="limite") LocalDate var2);
}

