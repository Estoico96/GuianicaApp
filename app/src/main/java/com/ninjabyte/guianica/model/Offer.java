package com.ninjabyte.guianica.model;

public class Offer {
    private String offerID;
    private String company;
    private String companyUID;
    private int counter;
    private String logoUrl;
    private String lastBannerUrlOffer;


    public Offer() {
    }

    public String getOfferID() {
        return offerID;
    }

    public String getCompanyUID() {
        return companyUID;
    }

    public String getCompany() {
        return company;
    }

    public int getCounter() {
        return counter;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getLastBannerUrlOffer() {
        return lastBannerUrlOffer;
    }

}
