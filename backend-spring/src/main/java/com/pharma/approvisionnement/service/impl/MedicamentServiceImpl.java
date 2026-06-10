/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 */
package com.pharma.approvisionnement.service.impl;

import com.pharma.approvisionnement.dto.MedicamentDto;
import com.pharma.approvisionnement.entity.Medicament;
import com.pharma.approvisionnement.exception.ResourceNotFoundException;
import com.pharma.approvisionnement.repository.MedicamentRepository;
import com.pharma.approvisionnement.service.MedicamentService;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MedicamentServiceImpl
implements MedicamentService {
    private static final Logger log = LoggerFactory.getLogger(MedicamentServiceImpl.class);
    private final MedicamentRepository medicamentRepository;

    public MedicamentServiceImpl(MedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }

    @Override
    @Transactional(readOnly=true)
    public List<MedicamentDto> getAllMedicaments() {
        return this.medicamentRepository.findAll().stream().map(MedicamentDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly=true)
    public MedicamentDto getMedicamentById(Long id) {
        Medicament m = (Medicament)this.medicamentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("M\u00e9dicament", id));
        return MedicamentDto.fromEntity(m);
    }

    @Override
    public MedicamentDto ajouterMedicament(MedicamentDto dto) {
        Medicament medicament = dto.toEntity();
        Medicament saved = (Medicament)this.medicamentRepository.save(medicament);
        log.info("M\u00e9dicament ajout\u00e9 : {}", (Object)saved.getNom());
        return MedicamentDto.fromEntity(saved);
    }

    @Override
    public MedicamentDto modifierMedicament(Long id, MedicamentDto dto) {
        Medicament existing = (Medicament)this.medicamentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("M\u00e9dicament", id));
        if (dto.getNom() != null) {
            existing.setNom(dto.getNom());
        }
        if (dto.getQuantite() != null) {
            existing.setQuantite(dto.getQuantite());
        }
        if (dto.getSeuilAlerte() != null) {
            existing.setSeuilAlerte(dto.getSeuilAlerte());
        }
        if (dto.getDatePeremption() != null) {
            existing.setDatePeremption(dto.getDatePeremption());
        }
        if (dto.getPrix() != null) {
            existing.setPrix(dto.getPrix());
        }
        Medicament saved = (Medicament)this.medicamentRepository.save(existing);
        log.info("M\u00e9dicament modifi\u00e9 : {}", (Object)saved.getNom());
        return MedicamentDto.fromEntity(saved);
    }

    @Override
    public void supprimerMedicament(Long id) {
        if (!this.medicamentRepository.existsById(id)) {
            throw new ResourceNotFoundException("M\u00e9dicament", id);
        }
        this.medicamentRepository.deleteById(id);
        log.info("M\u00e9dicament supprim\u00e9 : id={}", (Object)id);
    }

    @Override
    @Transactional(readOnly=true)
    public List<MedicamentDto> getAlertes() {
        return this.medicamentRepository.findAlertes().stream().map(m -> {
            MedicamentDto dto = MedicamentDto.fromEntity(m);
            return dto;
        }).collect(Collectors.toList());
    }
}

