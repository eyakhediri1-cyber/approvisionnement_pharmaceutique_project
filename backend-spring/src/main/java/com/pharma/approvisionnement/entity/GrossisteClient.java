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
 *  jakarta.validation.constraints.NotBlank
 */
package com.pharma.approvisionnement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="grossiste_clients")
public class GrossisteClient {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name="nom_grossiste", nullable=false, unique=true)
    private String nomGrossiste;
    @Column(name="base_donnees")
    private String baseDonnees;

    public GrossisteClient(Long id, String nomGrossiste, String baseDonnees) {
        this.id = id;
        this.nomGrossiste = nomGrossiste;
        this.baseDonnees = baseDonnees;
    }

    public GrossisteClient() {
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GrossisteClient)) {
            return false;
        }
        GrossisteClient other = (GrossisteClient)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$nomGrossiste = this.getNomGrossiste();
        String other$nomGrossiste = other.getNomGrossiste();
        if (this$nomGrossiste == null ? other$nomGrossiste != null : !this$nomGrossiste.equals(other$nomGrossiste)) {
            return false;
        }
        String this$baseDonnees = this.getBaseDonnees();
        String other$baseDonnees = other.getBaseDonnees();
        return !(this$baseDonnees == null ? other$baseDonnees != null : !this$baseDonnees.equals(other$baseDonnees));
    }

    public String toString() {
        return "GrossisteClient(id=" + this.getId() + ", nomGrossiste=" + this.getNomGrossiste() + ", baseDonnees=" + this.getBaseDonnees() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $nomGrossiste = this.getNomGrossiste();
        result = result * 59 + ($nomGrossiste == null ? 43 : $nomGrossiste.hashCode());
        String $baseDonnees = this.getBaseDonnees();
        result = result * 59 + ($baseDonnees == null ? 43 : $baseDonnees.hashCode());
        return result;
    }

    public static GrossisteClientBuilder builder() {
        return new GrossisteClientBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomGrossiste() {
        return this.nomGrossiste;
    }

    public void setNomGrossiste(String nomGrossiste) {
        this.nomGrossiste = nomGrossiste;
    }

    protected boolean canEqual(Object other) {
        return other instanceof GrossisteClient;
    }

    public String getBaseDonnees() {
        return this.baseDonnees;
    }

    public void setBaseDonnees(String baseDonnees) {
        this.baseDonnees = baseDonnees;
    }

    public static class GrossisteClientBuilder {
        private Long id;
        private String nomGrossiste;
        private String baseDonnees;

        GrossisteClientBuilder() {
        }

        public String toString() {
            return "GrossisteClient.GrossisteClientBuilder(id=" + this.id + ", nomGrossiste=" + this.nomGrossiste + ", baseDonnees=" + this.baseDonnees + ")";
        }

        public GrossisteClientBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public GrossisteClient build() {
            return new GrossisteClient(this.id, this.nomGrossiste, this.baseDonnees);
        }

        public GrossisteClientBuilder nomGrossiste(String nomGrossiste) {
            this.nomGrossiste = nomGrossiste;
            return this;
        }

        public GrossisteClientBuilder baseDonnees(String baseDonnees) {
            this.baseDonnees = baseDonnees;
            return this;
        }
    }
}

