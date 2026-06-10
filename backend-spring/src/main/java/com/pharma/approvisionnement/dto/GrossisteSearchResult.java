/*
 * Decompiled with CFR 0.152.
 */
package com.pharma.approvisionnement.dto;

public class GrossisteSearchResult {
    private String nomGrossiste;
    private Integer stock;
    private Double prix;
    private String medicament;

    public GrossisteSearchResult(String nomGrossiste, Integer stock, Double prix, String medicament) {
        this.nomGrossiste = nomGrossiste;
        this.stock = stock;
        this.prix = prix;
        this.medicament = medicament;
    }

    public GrossisteSearchResult() {
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GrossisteSearchResult)) {
            return false;
        }
        GrossisteSearchResult other = (GrossisteSearchResult)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Integer this$stock = this.getStock();
        Integer other$stock = other.getStock();
        if (this$stock == null ? other$stock != null : !((Object)this$stock).equals(other$stock)) {
            return false;
        }
        Double this$prix = this.getPrix();
        Double other$prix = other.getPrix();
        if (this$prix == null ? other$prix != null : !((Object)this$prix).equals(other$prix)) {
            return false;
        }
        String this$nomGrossiste = this.getNomGrossiste();
        String other$nomGrossiste = other.getNomGrossiste();
        if (this$nomGrossiste == null ? other$nomGrossiste != null : !this$nomGrossiste.equals(other$nomGrossiste)) {
            return false;
        }
        String this$medicament = this.getMedicament();
        String other$medicament = other.getMedicament();
        return !(this$medicament == null ? other$medicament != null : !this$medicament.equals(other$medicament));
    }

    public String toString() {
        return "GrossisteSearchResult(nomGrossiste=" + this.getNomGrossiste() + ", stock=" + this.getStock() + ", prix=" + this.getPrix() + ", medicament=" + this.getMedicament() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Integer $stock = this.getStock();
        result = result * 59 + ($stock == null ? 43 : ((Object)$stock).hashCode());
        Double $prix = this.getPrix();
        result = result * 59 + ($prix == null ? 43 : ((Object)$prix).hashCode());
        String $nomGrossiste = this.getNomGrossiste();
        result = result * 59 + ($nomGrossiste == null ? 43 : $nomGrossiste.hashCode());
        String $medicament = this.getMedicament();
        result = result * 59 + ($medicament == null ? 43 : $medicament.hashCode());
        return result;
    }

    public static GrossisteSearchResultBuilder builder() {
        return new GrossisteSearchResultBuilder();
    }

    public Double getPrix() {
        return this.prix;
    }

    public String getNomGrossiste() {
        return this.nomGrossiste;
    }

    public void setNomGrossiste(String nomGrossiste) {
        this.nomGrossiste = nomGrossiste;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    protected boolean canEqual(Object other) {
        return other instanceof GrossisteSearchResult;
    }

    public String getMedicament() {
        return this.medicament;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setMedicament(String medicament) {
        this.medicament = medicament;
    }

    public static class GrossisteSearchResultBuilder {
        private String nomGrossiste;
        private Integer stock;
        private Double prix;
        private String medicament;

        GrossisteSearchResultBuilder() {
        }

        public String toString() {
            return "GrossisteSearchResult.GrossisteSearchResultBuilder(nomGrossiste=" + this.nomGrossiste + ", stock=" + this.stock + ", prix=" + this.prix + ", medicament=" + this.medicament + ")";
        }

        public GrossisteSearchResult build() {
            return new GrossisteSearchResult(this.nomGrossiste, this.stock, this.prix, this.medicament);
        }

        public GrossisteSearchResultBuilder nomGrossiste(String nomGrossiste) {
            this.nomGrossiste = nomGrossiste;
            return this;
        }

        public GrossisteSearchResultBuilder medicament(String medicament) {
            this.medicament = medicament;
            return this;
        }

        public GrossisteSearchResultBuilder stock(Integer stock) {
            this.stock = stock;
            return this;
        }

        public GrossisteSearchResultBuilder prix(Double prix) {
            this.prix = prix;
            return this;
        }
    }
}

