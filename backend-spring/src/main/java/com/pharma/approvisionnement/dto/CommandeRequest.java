/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.validation.constraints.Min
 *  jakarta.validation.constraints.NotBlank
 *  jakarta.validation.constraints.NotNull
 */
package com.pharma.approvisionnement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommandeRequest {
    @NotBlank(message="Le nom du m\u00e9dicament est obligatoire")
    private @NotBlank(message="Le nom du m\u00e9dicament est obligatoire") String nom;
    @NotBlank(message="Le grossiste est obligatoire")
    private @NotBlank(message="Le grossiste est obligatoire") String grossiste;
    @NotNull(message="La quantit\u00e9 est obligatoire")
    @Min(value=1L, message="La quantit\u00e9 doit \u00eatre au moins 1")
    private @NotNull(message="La quantit\u00e9 est obligatoire") @Min(value=1L, message="La quantit\u00e9 doit \u00eatre au moins 1") Integer qte;
    private Double prixTotal;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CommandeRequest)) {
            return false;
        }
        CommandeRequest other = (CommandeRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$qte = this.getQte();
        Integer other$qte = other.getQte();
        if (this$qte == null ? other$qte != null : !((Object)this$qte).equals(other$qte)) {
            return false;
        }
        Double this$prixTotal = this.getPrixTotal();
        Double other$prixTotal = other.getPrixTotal();
        if (this$prixTotal == null ? other$prixTotal != null : !((Object)this$prixTotal).equals(other$prixTotal)) {
            return false;
        }
        String this$nom = this.getNom();
        String other$nom = other.getNom();
        if (this$nom == null ? other$nom != null : !this$nom.equals(other$nom)) {
            return false;
        }
        String this$grossiste = this.getGrossiste();
        String other$grossiste = other.getGrossiste();
        return !(this$grossiste == null ? other$grossiste != null : !this$grossiste.equals(other$grossiste));
    }

    public String toString() {
        return "CommandeRequest(nom=" + this.getNom() + ", grossiste=" + this.getGrossiste() + ", qte=" + this.getQte() + ", prixTotal=" + this.getPrixTotal() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $qte = this.getQte();
        result = result * 59 + ($qte == null ? 43 : ((Object)$qte).hashCode());
        Double $prixTotal = this.getPrixTotal();
        result = result * 59 + ($prixTotal == null ? 43 : ((Object)$prixTotal).hashCode());
        String $nom = this.getNom();
        result = result * 59 + ($nom == null ? 43 : $nom.hashCode());
        String $grossiste = this.getGrossiste();
        result = result * 59 + ($grossiste == null ? 43 : $grossiste.hashCode());
        return result;
    }

    public String getGrossiste() {
        return this.grossiste;
    }

    public String getNom() {
        return this.nom;
    }

    public Integer getQte() {
        return this.qte;
    }

    public Double getPrixTotal() {
        return this.prixTotal;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    protected boolean canEqual(Object other) {
        return other instanceof CommandeRequest;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public void setGrossiste(String grossiste) {
        this.grossiste = grossiste;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }
}

