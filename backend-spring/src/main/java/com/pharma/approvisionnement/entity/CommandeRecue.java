/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 *  jakarta.persistence.EnumType
 *  jakarta.persistence.Enumerated
 *  jakarta.persistence.GeneratedValue
 *  jakarta.persistence.GenerationType
 *  jakarta.persistence.Id
 *  jakarta.persistence.Table
 *  jakarta.validation.constraints.Min
 *  jakarta.validation.constraints.NotBlank
 *  jakarta.validation.constraints.NotNull
 */
package com.pharma.approvisionnement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pharma.approvisionnement.entity.Commande;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="commandes_recues")
public class CommandeRecue {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @Column(name="id_pharmacie_cmd")
    @JsonProperty("id_pharmacie_cmd")
    private Long idPharmacieCmd;
    @NotBlank
    @Column(name="nom_med", nullable=false)
    @JsonProperty("nom_med")
    private String nomMed;
    @NotNull
    @Min(value=1L)
    @Column(name="quantite", nullable=false)
    @JsonProperty("quantite")
    private @NotNull @Min(value=1L) Integer quantite;
    @Column(name="prix_total")
    @JsonProperty("prix_total")
    private Double prixTotal;
    @Column(name="date_cmd")
    @JsonProperty("date_cmd")
    private LocalDateTime dateCmd;
    @Enumerated(value=EnumType.STRING)
    @Column(name="statut", nullable=false)
    @JsonProperty("statut")
    private Commande.StatutCommande statut;
    @Column(name="date_traitement")
    @JsonProperty("date_traitement")
    private LocalDateTime dateTraitement;
    @Column(name="nom_grossiste", nullable=false)
    @JsonProperty("nom_grossiste")
    private String nomGrossiste;

    public CommandeRecue(Long id, Long idPharmacieCmd, String nomMed, Integer quantite, Double prixTotal, LocalDateTime dateCmd, Commande.StatutCommande statut, LocalDateTime dateTraitement, String nomGrossiste) {
        this.id = id;
        this.idPharmacieCmd = idPharmacieCmd;
        this.nomMed = nomMed;
        this.quantite = quantite;
        this.prixTotal = prixTotal;
        this.dateCmd = dateCmd;
        this.statut = statut;
        this.dateTraitement = dateTraitement;
        this.nomGrossiste = nomGrossiste;
    }

    public CommandeRecue() {
        this.prixTotal = CommandeRecue.$default$prixTotal();
        this.dateCmd = CommandeRecue.$default$dateCmd();
        this.statut = CommandeRecue.$default$statut();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CommandeRecue)) {
            return false;
        }
        CommandeRecue other = (CommandeRecue)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Long this$idPharmacieCmd = this.getIdPharmacieCmd();
        Long other$idPharmacieCmd = other.getIdPharmacieCmd();
        if (this$idPharmacieCmd == null ? other$idPharmacieCmd != null : !((Object)this$idPharmacieCmd).equals(other$idPharmacieCmd)) {
            return false;
        }
        Integer this$quantite = this.getQuantite();
        Integer other$quantite = other.getQuantite();
        if (this$quantite == null ? other$quantite != null : !((Object)this$quantite).equals(other$quantite)) {
            return false;
        }
        Double this$prixTotal = this.getPrixTotal();
        Double other$prixTotal = other.getPrixTotal();
        if (this$prixTotal == null ? other$prixTotal != null : !((Object)this$prixTotal).equals(other$prixTotal)) {
            return false;
        }
        String this$nomMed = this.getNomMed();
        String other$nomMed = other.getNomMed();
        if (this$nomMed == null ? other$nomMed != null : !this$nomMed.equals(other$nomMed)) {
            return false;
        }
        LocalDateTime this$dateCmd = this.getDateCmd();
        LocalDateTime other$dateCmd = other.getDateCmd();
        if (this$dateCmd == null ? other$dateCmd != null : !((Object)this$dateCmd).equals(other$dateCmd)) {
            return false;
        }
        Commande.StatutCommande this$statut = this.getStatut();
        Commande.StatutCommande other$statut = other.getStatut();
        if (this$statut == null ? other$statut != null : !((Object)((Object)this$statut)).equals((Object)other$statut)) {
            return false;
        }
        LocalDateTime this$dateTraitement = this.getDateTraitement();
        LocalDateTime other$dateTraitement = other.getDateTraitement();
        if (this$dateTraitement == null ? other$dateTraitement != null : !((Object)this$dateTraitement).equals(other$dateTraitement)) {
            return false;
        }
        String this$nomGrossiste = this.getNomGrossiste();
        String other$nomGrossiste = other.getNomGrossiste();
        return !(this$nomGrossiste == null ? other$nomGrossiste != null : !this$nomGrossiste.equals(other$nomGrossiste));
    }

    public String toString() {
        return "CommandeRecue(id=" + this.getId() + ", idPharmacieCmd=" + this.getIdPharmacieCmd() + ", nomMed=" + this.getNomMed() + ", quantite=" + this.getQuantite() + ", prixTotal=" + this.getPrixTotal() + ", dateCmd=" + String.valueOf(this.getDateCmd()) + ", statut=" + String.valueOf((Object)this.getStatut()) + ", dateTraitement=" + String.valueOf(this.getDateTraitement()) + ", nomGrossiste=" + this.getNomGrossiste() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Long $idPharmacieCmd = this.getIdPharmacieCmd();
        result = result * 59 + ($idPharmacieCmd == null ? 43 : ((Object)$idPharmacieCmd).hashCode());
        Integer $quantite = this.getQuantite();
        result = result * 59 + ($quantite == null ? 43 : ((Object)$quantite).hashCode());
        Double $prixTotal = this.getPrixTotal();
        result = result * 59 + ($prixTotal == null ? 43 : ((Object)$prixTotal).hashCode());
        String $nomMed = this.getNomMed();
        result = result * 59 + ($nomMed == null ? 43 : $nomMed.hashCode());
        LocalDateTime $dateCmd = this.getDateCmd();
        result = result * 59 + ($dateCmd == null ? 43 : ((Object)$dateCmd).hashCode());
        Commande.StatutCommande $statut = this.getStatut();
        result = result * 59 + ($statut == null ? 43 : ((Object)((Object)$statut)).hashCode());
        LocalDateTime $dateTraitement = this.getDateTraitement();
        result = result * 59 + ($dateTraitement == null ? 43 : ((Object)$dateTraitement).hashCode());
        String $nomGrossiste = this.getNomGrossiste();
        result = result * 59 + ($nomGrossiste == null ? 43 : $nomGrossiste.hashCode());
        return result;
    }

    public static CommandeRecueBuilder builder() {
        return new CommandeRecueBuilder();
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

    public Integer getQuantite() {
        return this.quantite;
    }

    public LocalDateTime getDateCmd() {
        return this.dateCmd;
    }

    public Commande.StatutCommande getStatut() {
        return this.statut;
    }

    public String getNomGrossiste() {
        return this.nomGrossiste;
    }

    public Double getPrixTotal() {
        return this.prixTotal;
    }

    public void setStatut(Commande.StatutCommande statut) {
        this.statut = statut;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public void setNomGrossiste(String nomGrossiste) {
        this.nomGrossiste = nomGrossiste;
    }

    public void setNomMed(String nomMed) {
        this.nomMed = nomMed;
    }

    public void setDateTraitement(LocalDateTime dateTraitement) {
        this.dateTraitement = dateTraitement;
    }

    public Long getIdPharmacieCmd() {
        return this.idPharmacieCmd;
    }

    protected boolean canEqual(Object other) {
        return other instanceof CommandeRecue;
    }

    public LocalDateTime getDateTraitement() {
        return this.dateTraitement;
    }

    private static Double $default$prixTotal() {
        return 0.0;
    }

    private static LocalDateTime $default$dateCmd() {
        return LocalDateTime.now();
    }

    private static Commande.StatutCommande $default$statut() {
        return Commande.StatutCommande.EN_ATTENTE;
    }

    public void setIdPharmacieCmd(Long idPharmacieCmd) {
        this.idPharmacieCmd = idPharmacieCmd;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public void setDateCmd(LocalDateTime dateCmd) {
        this.dateCmd = dateCmd;
    }

    public static class CommandeRecueBuilder {
        private Long id;
        private Long idPharmacieCmd;
        private String nomMed;
        private Integer quantite;
        private boolean prixTotal$set;
        private Double prixTotal$value;
        private boolean dateCmd$set;
        private LocalDateTime dateCmd$value;
        private boolean statut$set;
        private Commande.StatutCommande statut$value;
        private LocalDateTime dateTraitement;
        private String nomGrossiste;

        CommandeRecueBuilder() {
        }

        public String toString() {
            return "CommandeRecue.CommandeRecueBuilder(id=" + this.id + ", idPharmacieCmd=" + this.idPharmacieCmd + ", nomMed=" + this.nomMed + ", quantite=" + this.quantite + ", prixTotal$value=" + this.prixTotal$value + ", dateCmd$value=" + String.valueOf(this.dateCmd$value) + ", statut$value=" + String.valueOf((Object)this.statut$value) + ", dateTraitement=" + String.valueOf(this.dateTraitement) + ", nomGrossiste=" + this.nomGrossiste + ")";
        }

        public CommandeRecueBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CommandeRecue build() {
            Double prixTotal$value = this.prixTotal$value;
            if (!this.prixTotal$set) {
                prixTotal$value = CommandeRecue.$default$prixTotal();
            }
            LocalDateTime dateCmd$value = this.dateCmd$value;
            if (!this.dateCmd$set) {
                dateCmd$value = CommandeRecue.$default$dateCmd();
            }
            Commande.StatutCommande statut$value = this.statut$value;
            if (!this.statut$set) {
                statut$value = CommandeRecue.$default$statut();
            }
            return new CommandeRecue(this.id, this.idPharmacieCmd, this.nomMed, this.quantite, prixTotal$value, dateCmd$value, statut$value, this.dateTraitement, this.nomGrossiste);
        }

        public CommandeRecueBuilder statut(Commande.StatutCommande statut) {
            this.statut$value = statut;
            this.statut$set = true;
            return this;
        }

        public CommandeRecueBuilder nomGrossiste(String nomGrossiste) {
            this.nomGrossiste = nomGrossiste;
            return this;
        }

        public CommandeRecueBuilder quantite(Integer quantite) {
            this.quantite = quantite;
            return this;
        }

        public CommandeRecueBuilder nomMed(String nomMed) {
            this.nomMed = nomMed;
            return this;
        }

        public CommandeRecueBuilder prixTotal(Double prixTotal) {
            this.prixTotal$value = prixTotal;
            this.prixTotal$set = true;
            return this;
        }

        public CommandeRecueBuilder dateCmd(LocalDateTime dateCmd) {
            this.dateCmd$value = dateCmd;
            this.dateCmd$set = true;
            return this;
        }

        public CommandeRecueBuilder idPharmacieCmd(Long idPharmacieCmd) {
            this.idPharmacieCmd = idPharmacieCmd;
            return this;
        }

        public CommandeRecueBuilder dateTraitement(LocalDateTime dateTraitement) {
            this.dateTraitement = dateTraitement;
            return this;
        }
    }
}

