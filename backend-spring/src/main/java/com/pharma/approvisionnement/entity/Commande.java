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
@Table(name="commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    @JsonProperty("id")
    private Long id;
    @NotBlank(message="Le nom du m\u00e9dicament est obligatoire")
    @Column(name="nom_med", nullable=false)
    @JsonProperty("nom_med")
    private @NotBlank(message="Le nom du m\u00e9dicament est obligatoire") String nomMed;
    @NotBlank(message="Le nom du grossiste est obligatoire")
    @Column(name="grossiste", nullable=false)
    @JsonProperty("grossiste")
    private @NotBlank(message="Le nom du grossiste est obligatoire") String grossiste;
    @NotNull(message="La quantit\u00e9 est obligatoire")
    @Min(value=1L, message="La quantit\u00e9 doit \u00eatre au moins 1")
    @Column(name="quantite", nullable=false)
    @JsonProperty("quantite")
    private @NotNull(message="La quantit\u00e9 est obligatoire") @Min(value=1L, message="La quantit\u00e9 doit \u00eatre au moins 1") Integer quantite;
    @Column(name="prix_total")
    @JsonProperty("prix_total")
    private Double prixTotal;
    @Column(name="date_cmd")
    @JsonProperty("date_cmd")
    private LocalDateTime dateCmd;
    @Enumerated(value=EnumType.STRING)
    @Column(name="statut", nullable=false)
    @JsonProperty("statut")
    private StatutCommande statut;

    public Commande(Long id, String nomMed, String grossiste, Integer quantite, Double prixTotal, LocalDateTime dateCmd, StatutCommande statut) {
        this.id = id;
        this.nomMed = nomMed;
        this.grossiste = grossiste;
        this.quantite = quantite;
        this.prixTotal = prixTotal;
        this.dateCmd = dateCmd;
        this.statut = statut;
    }

    public Commande() {
        this.prixTotal = Commande.$default$prixTotal();
        this.dateCmd = Commande.$default$dateCmd();
        this.statut = Commande.$default$statut();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Commande)) {
            return false;
        }
        Commande other = (Commande)o;
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
        String this$grossiste = this.getGrossiste();
        String other$grossiste = other.getGrossiste();
        if (this$grossiste == null ? other$grossiste != null : !this$grossiste.equals(other$grossiste)) {
            return false;
        }
        LocalDateTime this$dateCmd = this.getDateCmd();
        LocalDateTime other$dateCmd = other.getDateCmd();
        if (this$dateCmd == null ? other$dateCmd != null : !((Object)this$dateCmd).equals(other$dateCmd)) {
            return false;
        }
        StatutCommande this$statut = this.getStatut();
        StatutCommande other$statut = other.getStatut();
        return !(this$statut == null ? other$statut != null : !((Object)((Object)this$statut)).equals((Object)other$statut));
    }

    public String toString() {
        return "Commande(id=" + this.getId() + ", nomMed=" + this.getNomMed() + ", grossiste=" + this.getGrossiste() + ", quantite=" + this.getQuantite() + ", prixTotal=" + this.getPrixTotal() + ", dateCmd=" + String.valueOf(this.getDateCmd()) + ", statut=" + String.valueOf((Object)this.getStatut()) + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Integer $quantite = this.getQuantite();
        result = result * 59 + ($quantite == null ? 43 : ((Object)$quantite).hashCode());
        Double $prixTotal = this.getPrixTotal();
        result = result * 59 + ($prixTotal == null ? 43 : ((Object)$prixTotal).hashCode());
        String $nomMed = this.getNomMed();
        result = result * 59 + ($nomMed == null ? 43 : $nomMed.hashCode());
        String $grossiste = this.getGrossiste();
        result = result * 59 + ($grossiste == null ? 43 : $grossiste.hashCode());
        LocalDateTime $dateCmd = this.getDateCmd();
        result = result * 59 + ($dateCmd == null ? 43 : ((Object)$dateCmd).hashCode());
        StatutCommande $statut = this.getStatut();
        result = result * 59 + ($statut == null ? 43 : ((Object)((Object)$statut)).hashCode());
        return result;
    }

    public static CommandeBuilder builder() {
        return new CommandeBuilder();
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

    public String getGrossiste() {
        return this.grossiste;
    }

    public LocalDateTime getDateCmd() {
        return this.dateCmd;
    }

    public StatutCommande getStatut() {
        return this.statut;
    }

    public Double getPrixTotal() {
        return this.prixTotal;
    }

    public void setStatut(StatutCommande statut) {
        this.statut = statut;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public void setNomMed(String nomMed) {
        this.nomMed = nomMed;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Commande;
    }

    private static Double $default$prixTotal() {
        return 0.0;
    }

    private static LocalDateTime $default$dateCmd() {
        return LocalDateTime.now();
    }

    private static StatutCommande $default$statut() {
        return StatutCommande.EN_ATTENTE;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public void setDateCmd(LocalDateTime dateCmd) {
        this.dateCmd = dateCmd;
    }

    public void setGrossiste(String grossiste) {
        this.grossiste = grossiste;
    }

    public static enum StatutCommande {
        EN_ATTENTE("En attente"),
        CONFIRMEE("Confirm\u00e9e"),
        EN_PREPARATION("En pr\u00e9paration"),
        EXPEDIEE("Exp\u00e9di\u00e9e"),
        LIVREE("Livr\u00e9e"),
        ANNULEE("Annul\u00e9e");

        private final String label;

        private StatutCommande(String label) {
            this.label = label;
        }

        @com.fasterxml.jackson.annotation.JsonValue
        public String getLabel() {
            return this.label;
        }

        public static StatutCommande fromLabel(String label) {
            for (StatutCommande s : StatutCommande.values()) {
                if (!s.label.equalsIgnoreCase(label)) continue;
                return s;
            }
            throw new IllegalArgumentException("Statut invalide : " + label);
        }
    }

    public static class CommandeBuilder {
        private Long id;
        private String nomMed;
        private String grossiste;
        private Integer quantite;
        private boolean prixTotal$set;
        private Double prixTotal$value;
        private boolean dateCmd$set;
        private LocalDateTime dateCmd$value;
        private boolean statut$set;
        private StatutCommande statut$value;

        CommandeBuilder() {
        }

        public String toString() {
            return "Commande.CommandeBuilder(id=" + this.id + ", nomMed=" + this.nomMed + ", grossiste=" + this.grossiste + ", quantite=" + this.quantite + ", prixTotal$value=" + this.prixTotal$value + ", dateCmd$value=" + String.valueOf(this.dateCmd$value) + ", statut$value=" + String.valueOf((Object)this.statut$value) + ")";
        }

        public CommandeBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Commande build() {
            Double prixTotal$value = this.prixTotal$value;
            if (!this.prixTotal$set) {
                prixTotal$value = Commande.$default$prixTotal();
            }
            LocalDateTime dateCmd$value = this.dateCmd$value;
            if (!this.dateCmd$set) {
                dateCmd$value = Commande.$default$dateCmd();
            }
            StatutCommande statut$value = this.statut$value;
            if (!this.statut$set) {
                statut$value = Commande.$default$statut();
            }
            return new Commande(this.id, this.nomMed, this.grossiste, this.quantite, prixTotal$value, dateCmd$value, statut$value);
        }

        public CommandeBuilder statut(StatutCommande statut) {
            this.statut$value = statut;
            this.statut$set = true;
            return this;
        }

        public CommandeBuilder grossiste(String grossiste) {
            this.grossiste = grossiste;
            return this;
        }

        public CommandeBuilder quantite(Integer quantite) {
            this.quantite = quantite;
            return this;
        }

        public CommandeBuilder nomMed(String nomMed) {
            this.nomMed = nomMed;
            return this;
        }

        public CommandeBuilder prixTotal(Double prixTotal) {
            this.prixTotal$value = prixTotal;
            this.prixTotal$set = true;
            return this;
        }

        public CommandeBuilder dateCmd(LocalDateTime dateCmd) {
            this.dateCmd$value = dateCmd;
            this.dateCmd$set = true;
            return this;
        }
    }
}

