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
 *  jakarta.validation.constraints.Email
 *  jakarta.validation.constraints.NotBlank
 */
package com.pharma.approvisionnement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="pharmacie_clientes")
public class PharmacieCliente {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_pharm_client")
    @JsonProperty("id_pharm_client")
    private Long id;
    @NotBlank
    @Column(name="nom_pharmacie", nullable=false)
    @JsonProperty("nom_pharmacie")
    private String nomPharmacie;
    @Column(name="nom_responsable")
    @JsonProperty("nom_responsable")
    private String nomResponsable;
    @Column(name="adress")
    @JsonProperty("adress")
    private String adresse;
    @Column(name="telephone")
    @JsonProperty("telephone")
    private String telephone;
    @Email
    @Column(name="email")
    @JsonProperty("email")
    private String email;
    @Column(name="ville")
    @JsonProperty("ville")
    private String ville;
    @Column(name="nom_grossiste", nullable=false)
    @JsonProperty("nom_grossiste")
    private String nomGrossiste;

    public PharmacieCliente(Long id, String nomPharmacie, String nomResponsable, String adresse, String telephone, String email, String ville, String nomGrossiste) {
        this.id = id;
        this.nomPharmacie = nomPharmacie;
        this.nomResponsable = nomResponsable;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.ville = ville;
        this.nomGrossiste = nomGrossiste;
    }

    public PharmacieCliente() {
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PharmacieCliente)) {
            return false;
        }
        PharmacieCliente other = (PharmacieCliente)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        String this$nomPharmacie = this.getNomPharmacie();
        String other$nomPharmacie = other.getNomPharmacie();
        if (this$nomPharmacie == null ? other$nomPharmacie != null : !this$nomPharmacie.equals(other$nomPharmacie)) {
            return false;
        }
        String this$nomResponsable = this.getNomResponsable();
        String other$nomResponsable = other.getNomResponsable();
        if (this$nomResponsable == null ? other$nomResponsable != null : !this$nomResponsable.equals(other$nomResponsable)) {
            return false;
        }
        String this$adresse = this.getAdresse();
        String other$adresse = other.getAdresse();
        if (this$adresse == null ? other$adresse != null : !this$adresse.equals(other$adresse)) {
            return false;
        }
        String this$telephone = this.getTelephone();
        String other$telephone = other.getTelephone();
        if (this$telephone == null ? other$telephone != null : !this$telephone.equals(other$telephone)) {
            return false;
        }
        String this$email = this.getEmail();
        String other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) {
            return false;
        }
        String this$ville = this.getVille();
        String other$ville = other.getVille();
        if (this$ville == null ? other$ville != null : !this$ville.equals(other$ville)) {
            return false;
        }
        String this$nomGrossiste = this.getNomGrossiste();
        String other$nomGrossiste = other.getNomGrossiste();
        return !(this$nomGrossiste == null ? other$nomGrossiste != null : !this$nomGrossiste.equals(other$nomGrossiste));
    }

    public String toString() {
        return "PharmacieCliente(id=" + this.getId() + ", nomPharmacie=" + this.getNomPharmacie() + ", nomResponsable=" + this.getNomResponsable() + ", adresse=" + this.getAdresse() + ", telephone=" + this.getTelephone() + ", email=" + this.getEmail() + ", ville=" + this.getVille() + ", nomGrossiste=" + this.getNomGrossiste() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        String $nomPharmacie = this.getNomPharmacie();
        result = result * 59 + ($nomPharmacie == null ? 43 : $nomPharmacie.hashCode());
        String $nomResponsable = this.getNomResponsable();
        result = result * 59 + ($nomResponsable == null ? 43 : $nomResponsable.hashCode());
        String $adresse = this.getAdresse();
        result = result * 59 + ($adresse == null ? 43 : $adresse.hashCode());
        String $telephone = this.getTelephone();
        result = result * 59 + ($telephone == null ? 43 : $telephone.hashCode());
        String $email = this.getEmail();
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        String $ville = this.getVille();
        result = result * 59 + ($ville == null ? 43 : $ville.hashCode());
        String $nomGrossiste = this.getNomGrossiste();
        result = result * 59 + ($nomGrossiste == null ? 43 : $nomGrossiste.hashCode());
        return result;
    }

    public static PharmacieClienteBuilder builder() {
        return new PharmacieClienteBuilder();
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

    public String getEmail() {
        return this.email;
    }

    public void setNomGrossiste(String nomGrossiste) {
        this.nomGrossiste = nomGrossiste;
    }

    public String getNomPharmacie() {
        return this.nomPharmacie;
    }

    public void setNomPharmacie(String nomPharmacie) {
        this.nomPharmacie = nomPharmacie;
    }

    public String getNomResponsable() {
        return this.nomResponsable;
    }

    public void setNomResponsable(String nomResponsable) {
        this.nomResponsable = nomResponsable;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVille() {
        return this.ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    protected boolean canEqual(Object other) {
        return other instanceof PharmacieCliente;
    }

    public static class PharmacieClienteBuilder {
        private Long id;
        private String nomPharmacie;
        private String nomResponsable;
        private String adresse;
        private String telephone;
        private String email;
        private String ville;
        private String nomGrossiste;

        PharmacieClienteBuilder() {
        }

        public String toString() {
            return "PharmacieCliente.PharmacieClienteBuilder(id=" + this.id + ", nomPharmacie=" + this.nomPharmacie + ", nomResponsable=" + this.nomResponsable + ", adresse=" + this.adresse + ", telephone=" + this.telephone + ", email=" + this.email + ", ville=" + this.ville + ", nomGrossiste=" + this.nomGrossiste + ")";
        }

        public PharmacieClienteBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PharmacieCliente build() {
            return new PharmacieCliente(this.id, this.nomPharmacie, this.nomResponsable, this.adresse, this.telephone, this.email, this.ville, this.nomGrossiste);
        }

        public PharmacieClienteBuilder nomGrossiste(String nomGrossiste) {
            this.nomGrossiste = nomGrossiste;
            return this;
        }

        public PharmacieClienteBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PharmacieClienteBuilder nomPharmacie(String nomPharmacie) {
            this.nomPharmacie = nomPharmacie;
            return this;
        }

        public PharmacieClienteBuilder nomResponsable(String nomResponsable) {
            this.nomResponsable = nomResponsable;
            return this;
        }

        public PharmacieClienteBuilder adresse(String adresse) {
            this.adresse = adresse;
            return this;
        }

        public PharmacieClienteBuilder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public PharmacieClienteBuilder ville(String ville) {
            this.ville = ville;
            return this;
        }
    }
}

