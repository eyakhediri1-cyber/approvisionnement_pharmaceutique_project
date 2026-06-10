/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  jakarta.validation.constraints.DecimalMin
 *  jakarta.validation.constraints.Min
 *  jakarta.validation.constraints.NotBlank
 *  jakarta.validation.constraints.NotNull
 */
package com.pharma.approvisionnement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pharma.approvisionnement.entity.Medicament;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class MedicamentDto {
    private Long id;
    @NotBlank(message="Le nom est obligatoire")
    private @NotBlank(message="Le nom est obligatoire") String nom;
    @NotNull
    @Min(value=0L)
    private @NotNull @Min(value=0L) Integer quantite;
    @NotNull
    @Min(value=0L)
    private @NotNull @Min(value=0L) Integer seuilAlerte;
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate datePeremption;
    @NotNull
    @DecimalMin(value="0.0")
    private @NotNull @DecimalMin(value="0.0") Double prix;

    public MedicamentDto(Long id, String nom, Integer quantite, Integer seuilAlerte, LocalDate datePeremption, Double prix) {
        this.id = id;
        this.nom = nom;
        this.quantite = quantite;
        this.seuilAlerte = seuilAlerte;
        this.datePeremption = datePeremption;
        this.prix = prix;
    }

    public MedicamentDto() {
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MedicamentDto)) {
            return false;
        }
        MedicamentDto other = (MedicamentDto)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Integer this$quantite = this.getQuantite();
        Integer other$quantite = other.getQuantite();
        if (this$quantite == null ? other$quantite != null : !((Object)this$quantite).equals(other$quantite)) {
            return false;
        }
        Integer this$seuilAlerte = this.getSeuilAlerte();
        Integer other$seuilAlerte = other.getSeuilAlerte();
        if (this$seuilAlerte == null ? other$seuilAlerte != null : !((Object)this$seuilAlerte).equals(other$seuilAlerte)) {
            return false;
        }
        Double this$prix = this.getPrix();
        Double other$prix = other.getPrix();
        if (this$prix == null ? other$prix != null : !((Object)this$prix).equals(other$prix)) {
            return false;
        }
        String this$nom = this.getNom();
        String other$nom = other.getNom();
        if (this$nom == null ? other$nom != null : !this$nom.equals(other$nom)) {
            return false;
        }
        LocalDate this$datePeremption = this.getDatePeremption();
        LocalDate other$datePeremption = other.getDatePeremption();
        return !(this$datePeremption == null ? other$datePeremption != null : !((Object)this$datePeremption).equals(other$datePeremption));
    }

    public String toString() {
        return "MedicamentDto(id=" + this.getId() + ", nom=" + this.getNom() + ", quantite=" + this.getQuantite() + ", seuilAlerte=" + this.getSeuilAlerte() + ", datePeremption=" + String.valueOf(this.getDatePeremption()) + ", prix=" + this.getPrix() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Integer $quantite = this.getQuantite();
        result = result * 59 + ($quantite == null ? 43 : ((Object)$quantite).hashCode());
        Integer $seuilAlerte = this.getSeuilAlerte();
        result = result * 59 + ($seuilAlerte == null ? 43 : ((Object)$seuilAlerte).hashCode());
        Double $prix = this.getPrix();
        result = result * 59 + ($prix == null ? 43 : ((Object)$prix).hashCode());
        String $nom = this.getNom();
        result = result * 59 + ($nom == null ? 43 : $nom.hashCode());
        LocalDate $datePeremption = this.getDatePeremption();
        result = result * 59 + ($datePeremption == null ? 43 : ((Object)$datePeremption).hashCode());
        return result;
    }

    public static MedicamentDtoBuilder builder() {
        return new MedicamentDtoBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantite() {
        return this.quantite;
    }

    public String getNom() {
        return this.nom;
    }

    public Integer getSeuilAlerte() {
        return this.seuilAlerte;
    }

    public Double getPrix() {
        return this.prix;
    }

    public LocalDate getDatePeremption() {
        return this.datePeremption;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public static MedicamentDto fromEntity(Medicament m) {
        return MedicamentDto.builder().id(m.getId()).nom(m.getNom()).quantite(m.getQuantite()).seuilAlerte(m.getSeuilAlerte()).datePeremption(m.getDatePeremption()).prix(m.getPrix()).build();
    }

    public void setSeuilAlerte(Integer seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setDatePeremption(LocalDate datePeremption) {
        this.datePeremption = datePeremption;
    }

    public Medicament toEntity() {
        return Medicament.builder().nom(this.nom).quantite(this.quantite).seuilAlerte(this.seuilAlerte).datePeremption(this.datePeremption).prix(this.prix).build();
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    protected boolean canEqual(Object other) {
        return other instanceof MedicamentDto;
    }

    public static class MedicamentDtoBuilder {
        private Long id;
        private String nom;
        private Integer quantite;
        private Integer seuilAlerte;
        private LocalDate datePeremption;
        private Double prix;

        MedicamentDtoBuilder() {
        }

        public String toString() {
            return "MedicamentDto.MedicamentDtoBuilder(id=" + this.id + ", nom=" + this.nom + ", quantite=" + this.quantite + ", seuilAlerte=" + this.seuilAlerte + ", datePeremption=" + String.valueOf(this.datePeremption) + ", prix=" + this.prix + ")";
        }

        public MedicamentDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MedicamentDto build() {
            return new MedicamentDto(this.id, this.nom, this.quantite, this.seuilAlerte, this.datePeremption, this.prix);
        }

        public MedicamentDtoBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public MedicamentDtoBuilder quantite(Integer quantite) {
            this.quantite = quantite;
            return this;
        }

        public MedicamentDtoBuilder prix(Double prix) {
            this.prix = prix;
            return this;
        }

        public MedicamentDtoBuilder seuilAlerte(Integer seuilAlerte) {
            this.seuilAlerte = seuilAlerte;
            return this;
        }

        @JsonFormat(pattern="yyyy-MM-dd")
        public MedicamentDtoBuilder datePeremption(LocalDate datePeremption) {
            this.datePeremption = datePeremption;
            return this;
        }
    }
}

