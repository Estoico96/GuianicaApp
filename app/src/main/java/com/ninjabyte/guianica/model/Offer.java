package com.ninjabyte.guianica.model;

public class Offer {
    private String uid;
    private String company;
    private int counter;
    private String logoUrl;
    private String lastBannerUrlOffer;


    public Offer() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLastBannerUrlOffer() {
        return lastBannerUrlOffer;
    }

    public void setLastBannerUrlOffer(String lastBannerUrlOffer) {
        this.lastBannerUrlOffer = lastBannerUrlOffer;
    }


    @Override
    public String toString() {
        return "Offer{" +
                "company='" + company + '\'' +
                ", counter='" + counter + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", lastBannerUrlOffer='" + lastBannerUrlOffer + '\'' +
                '}';
    }
}
