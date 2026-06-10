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
 *  jakarta.validation.constraints.Future
 *  jakarta.validation.constraints.Min
 *  jakarta.validation.constraints.NotBlank
 *  jakarta.validation.constraints.NotNull
 *  jakarta.validation.constraints.Size
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
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name="medicaments")
public class Medicament {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    @JsonProperty("id")
    private Long id;
    @NotBlank(message="Le nom du m\u00e9dicament est obligatoire")
    @Size(max=200, message="Le nom ne peut pas d\u00e9passer 200 caract\u00e8res")
    @Column(name="nom", nullable=false)
    @JsonProperty("nom")
    private @NotBlank(message="Le nom du m\u00e9dicament est obligatoire") @Size(max=200, message="Le nom ne peut pas d\u00e9passer 200 caract\u00e8res") String nom;
    @NotNull(message="La quantit\u00e9 est obligatoire")
    @Min(value=0L, message="La quantit\u00e9 ne peut pas \u00eatre n\u00e9gative")
    @Column(name="quantite", nullable=false)
    @JsonProperty("quantite")
    private @NotNull(message="La quantit\u00e9 est obligatoire") @Min(value=0L, message="La quantit\u00e9 ne peut pas \u00eatre n\u00e9gative") Integer quantite;
    @NotNull(message="Le seuil d'alerte est obligatoire")
    @Min(value=0L, message="Le seuil d'alerte ne peut pas \u00eatre n\u00e9gatif")
    @Column(name="seuil_alerte", nullable=false)
    @JsonProperty("seuil_alerte")
    private @NotNull(message="Le seuil d'alerte est obligatoire") @Min(value=0L, message="Le seuil d'alerte ne peut pas \u00eatre n\u00e9gatif") Integer seuilAlerte;
    @NotNull(message="La date de p\u00e9remption est obligatoire")
    @Future(message="La date de p\u00e9remption doit \u00eatre dans le futur")
    @Column(name="date_peremption")
    @JsonProperty("date_peremption")
    private @NotNull(message="La date de p\u00e9remption est obligatoire") @Future(message="La date de p\u00e9remption doit \u00eatre dans le futur") LocalDate datePeremption;
    @NotNull(message="Le prix est obligatoire")
    @DecimalMin(value="0.0", inclusive=true, message="Le prix ne peut pas \u00eatre n\u00e9gatif")
    @Column(name="prix", nullable=false)
    @JsonProperty("prix")
    private @NotNull(message="Le prix est obligatoire") @DecimalMin(value="0.0", inclusive=true, message="Le prix ne peut pas \u00eatre n\u00e9gatif") Double prix;

    public Medicament(Long id, String nom, Integer quantite, Integer seuilAlerte, LocalDate datePeremption, Double prix) {
        this.id = id;
        this.nom = nom;
        this.quantite = quantite;
        this.seuilAlerte = seuilAlerte;
        this.datePeremption = datePeremption;
        this.prix = prix;
    }

    public Medicament() {
        this.seuilAlerte = Medicament.$default$seuilAlerte();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Medicament)) {
            return false;
        }
        Medicament other = (Medicament)o;
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
        return "Medicament(id=" + this.getId() + ", nom=" + this.getNom() + ", quantite=" + this.getQuantite() + ", seuilAlerte=" + this.getSeuilAlerte() + ", datePeremption=" + String.valueOf(this.getDatePeremption()) + ", prix=" + this.getPrix() + ")";
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

    public static MedicamentBuilder builder() {
        return new MedicamentBuilder();
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

    public void setSeuilAlerte(Integer seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public void setDatePeremption(LocalDate datePeremption) {
        this.datePeremption = datePeremption;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Medicament;
    }

    private static Integer $default$seuilAlerte() {
        return 5;
    }

    public static class MedicamentBuilder {
        private Long id;
        private String nom;
        private Integer quantite;
        private boolean seuilAlerte$set;
        private Integer seuilAlerte$value;
        private LocalDate datePeremption;
        private Double prix;

        MedicamentBuilder() {
        }

        public String toString() {
            return "Medicament.MedicamentBuilder(id=" + this.id + ", nom=" + this.nom + ", quantite=" + this.quantite + ", seuilAlerte$value=" + this.seuilAlerte$value + ", datePeremption=" + String.valueOf(this.datePeremption) + ", prix=" + this.prix + ")";
        }

        public MedicamentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Medicament build() {
            Integer seuilAlerte$value = this.seuilAlerte$value;
            if (!this.seuilAlerte$set) {
                seuilAlerte$value = Medicament.$default$seuilAlerte();
            }
            return new Medicament(this.id, this.nom, this.quantite, seuilAlerte$value, this.datePeremption, this.prix);
        }

        public MedicamentBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public MedicamentBuilder quantite(Integer quantite) {
            this.quantite = quantite;
            return this;
        }

        public MedicamentBuilder prix(Double prix) {
            this.prix = prix;
            return this;
        }

        public MedicamentBuilder seuilAlerte(Integer seuilAlerte) {
            this.seuilAlerte$value = seuilAlerte;
            this.seuilAlerte$set = true;
            return this;
        }

        public MedicamentBuilder datePeremption(LocalDate datePeremption) {
            this.datePeremption = datePeremption;
            return this;
        }
    }
}

