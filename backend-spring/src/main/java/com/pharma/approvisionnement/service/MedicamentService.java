/*
 * Decompiled with CFR 0.152.
 */
package com.pharma.approvisionnement.service;

import com.pharma.approvisionnement.dto.MedicamentDto;
import java.util.List;

public interface MedicamentService {
    public List<MedicamentDto> getAllMedicaments();

    public MedicamentDto getMedicamentById(Long var1);

    public MedicamentDto ajouterMedicament(MedicamentDto var1);

    public MedicamentDto modifierMedicament(Long var1, MedicamentDto var2);

    public void supprimerMedicament(Long var1);

    public List<MedicamentDto> getAlertes();
}

