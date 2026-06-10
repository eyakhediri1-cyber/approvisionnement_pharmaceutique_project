/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 *  jakarta.persistence.GeneratedValue
 *  jakarta.persistence.GenerationType
 *  jakarta.persistence.Id
 *  jakarta.persistence.Table
 *  jakarta.validation.constraints.DecimalMin
 *  jakarta.validation.constraints.Min
 *  jakarta.validation.constraints.NotBlank
 *  jakarta.validation.constraints.NotNull
 */
package com.pharma.approvisionnement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name="stock_grossiste")
public class StockGrossiste {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_med")
    @JsonProperty("id_med")
    private Long id;
    @NotBlank(message="Le nom du m\u00e9dicament est obligatoire")
    @Column(name="nom_med", nullable=false)
    @JsonProperty("nom_med")
    private @NotBlank(message="Le nom du m\u00e9dicament est obligatoire") String nomMed;
    @NotNull
    @DecimalMin(value="0.0")
    @Column(name="prix_unitaire", nullable=false)
    @JsonProperty("prix_unitaire")
    private @NotNull @DecimalMin(value="0.0") Double prixUnitaire;
    @NotNull
    @Min(value=0L)
    @Column(name="stock_dispo", nullable=false)
    @JsonProperty("stock_dispo")
    private @NotNull @Min(value=0L) Integer stockDispo;
    @Min(value=0L)
    @Column(name="seuil_alerte")
    @JsonProperty("seuil_alerte")
    private @Min(value=0L) Integer seuilAlerte;
    @Column(name="date_peremption")
    @JsonProperty("date_peremption")
    private LocalDate datePeremption;
    @NotBlank
    @Column(name="nom_grossiste", nullable=false)
    @JsonProperty("nom_grossiste")
    private String nomGrossiste;

    public StockGrossiste(Long id, String nomMed, Double prixUnitaire, Integer stockDispo, Integer seuilAlerte, LocalDate datePeremption, String nomGrossiste) {
        this.id = id;
        this.nomMed = nomMed;
        this.prixUnitaire = prixUnitaire;
        this.stockDispo = stockDispo;
        this.seuilAlerte = seuilAlerte;
        this.datePeremption = datePeremption;
        this.nomGrossiste = nomGrossiste;
    }

    public StockGrossiste() {
        this.stockDispo = StockGrossiste.$default$stockDispo();
        this.seuilAlerte = StockGrossiste.$default$seuilAlerte();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StockGrossiste)) {
            return false;
        }
        StockGrossiste other = (StockGrossiste)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Double this$prixUnitaire = this.getPrixUnitaire();
        Double other$prixUnitaire = other.getPrixUnitaire();
        if (this$prixUnitaire == null ? other$prixUnitaire != null : !((Object)this$prixUnitaire).equals(other$prixUnitaire)) {
            return false;
        }
        Integer this$stockDispo = this.getStockDispo();
        Integer other$stockDispo = other.getStockDispo();
        if (this$stockDispo == null ? other$stockDispo != null : !((Object)this$stockDispo).equals(other$stockDispo)) {
            return false;
        }
        Integer this$seuilAlerte = this.getSeuilAlerte();
        Integer other$seuilAlerte = other.getSeuilAlerte();
        if (this$seuilAlerte == null ? other$seuilAlerte != null : !((Object)this$seuilAlerte).equals(other$seuilAlerte)) {
            return false;
        }
        String this$nomMed = this.getNomMed();
        String other$nomMed = other.getNomMed();
        if (this$nomMed == null ? other$nomMed != null : !this$nomMed.equals(other$nomMed)) {
            return false;
        }
        LocalDate this$datePeremption = this.getDatePeremption();
        LocalDate other$datePeremption = other.getDatePeremption();
        if (this$datePeremption == null ? other$datePeremption != null : !((Object)this$datePeremption).equals(other$datePeremption)) {
            return false;
        }
        String this$nomGrossiste = this.getNomGrossiste();
        String other$nomGrossiste = other.getNomGrossiste();
        return !(this$nomGrossiste == null ? other$nomGrossiste != null : !this$nomGrossiste.equals(other$nomGrossiste));
    }

    public String toString() {
        return "StockGrossiste(id=" + this.getId() + ", nomMed=" + this.getNomMed() + ", prixUnitaire=" + this.getPrixUnitaire() + ", stockDispo=" + this.getStockDispo() + ", seuilAlerte=" + this.getSeuilAlerte() + ", datePeremption=" + String.valueOf(this.getDatePeremption()) + ", nomGrossiste=" + this.getNomGrossiste() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Double $prixUnitaire = this.getPrixUnitaire();
        result = result * 59 + ($prixUnitaire == null ? 43 : ((Object)$prixUnitaire).hashCode());
        Integer $stockDispo = this.getStockDispo();
        result = result * 59 + ($stockDispo == null ? 43 : ((Object)$stockDispo).hashCode());
        Integer $seuilAlerte = this.getSeuilAlerte();
        result = result * 59 + ($seuilAlerte == null ? 43 : ((Object)$seuilAlerte).hashCode());
        String $nomMed = this.getNomMed();
        result = result * 59 + ($nomMed == null ? 43 : $nomMed.hashCode());
        LocalDate $datePeremption = this.getDatePeremption();
        result = result * 59 + ($datePeremption == null ? 43 : ((Object)$datePeremption).hashCode());
        String $nomGrossiste = this.getNomGrossiste();
        result = result * 59 + ($nomGrossiste == null ? 43 : $nomGrossiste.hashCode());
        return result;
    }

    public static StockGrossisteBuilder builder() {
        return new StockGrossisteBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomMed() {
        return this.nomMed;
    }

    public Integer getSeuilAlerte() {
        return this.seuilAlerte;
    }

    public LocalDate getDatePeremption() {
        return this.datePeremption;
    }

    public Double getPrixUnitaire() {
        return this.prixUnitaire;
    }

    public String getNomGrossiste() {
        return this.nomGrossiste;
    }

    public Integer getStockDispo() {
        return this.stockDispo;
    }

    public void setStockDispo(Integer stockDispo) {
        this.stockDispo = stockDispo;
    }

    public void setNomGrossiste(String nomGrossiste) {
        this.nomGrossiste = nomGrossiste;
    }

    public void setNomMed(String nomMed) {
        this.nomMed = nomMed;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public void setSeuilAlerte(Integer seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public void setDatePeremption(LocalDate datePeremption) {
        this.datePeremption = datePeremption;
    }

    protected boolean canEqual(Object other) {
        return other instanceof StockGrossiste;
    }

    private static Integer $default$seuilAlerte() {
        return 5;
    }

    private static Integer $default$stockDispo() {
        return 0;
    }

    public static class StockGrossisteBuilder {
        private Long id;
        private String nomMed;
        private Double prixUnitaire;
        private boolean stockDispo$set;
        private Integer stockDispo$value;
        private boolean seuilAlerte$set;
        private Integer seuilAlerte$value;
        private LocalDate datePeremption;
        private String nomGrossiste;

        StockGrossisteBuilder() {
        }

        public String toString() {
            return "StockGrossiste.StockGrossisteBuilder(id=" + this.id + ", nomMed=" + this.nomMed + ", prixUnitaire=" + this.prixUnitaire + ", stockDispo$value=" + this.stockDispo$value + ", seuilAlerte$value=" + this.seuilAlerte$value + ", datePeremption=" + String.valueOf(this.datePeremption) + ", nomGrossiste=" + this.nomGrossiste + ")";
        }

        public StockGrossisteBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public StockGrossiste build() {
            Integer stockDispo$value = this.stockDispo$value;
            if (!this.stockDispo$set) {
                stockDispo$value = StockGrossiste.$default$stockDispo();
            }
            Integer seuilAlerte$value = this.seuilAlerte$value;
            if (!this.seuilAlerte$set) {
                seuilAlerte$value = StockGrossiste.$default$seuilAlerte();
            }
            return new StockGrossiste(this.id, this.nomMed, this.prixUnitaire, stockDispo$value, seuilAlerte$value, this.datePeremption, this.nomGrossiste);
        }

        public StockGrossisteBuilder nomGrossiste(String nomGrossiste) {
            this.nomGrossiste = nomGrossiste;
            return this;
        }

        public StockGrossisteBuilder nomMed(String nomMed) {
            this.nomMed = nomMed;
            return this;
        }

        public StockGrossisteBuilder seuilAlerte(Integer seuilAlerte) {
            this.seuilAlerte$value = seuilAlerte;
            this.seuilAlerte$set = true;
            return this;
        }

        public StockGrossisteBuilder datePeremption(LocalDate datePeremption) {
            this.datePeremption = datePeremption;
            return this;
        }

        public StockGrossisteBuilder prixUnitaire(Double prixUnitaire) {
            this.prixUnitaire = prixUnitaire;
            return this;
        }

        public StockGrossisteBuilder stockDispo(Integer stockDispo) {
            this.stockDispo$value = stockDispo;
            this.stockDispo$set = true;
            return this;
        }
    }
}

