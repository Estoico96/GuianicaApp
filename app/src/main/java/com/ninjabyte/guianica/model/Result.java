package com.ninjabyte.guianica.model;

import java.util.ArrayList;

public class Result {
    private String uid;
    private String company;
    private String logoUrl;
    private String specialty;
    private ArrayList<String> schedule;
    private boolean offer;
    private Delivery delivery;
    private int rating;


    public Result() {
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSpecialty() {
        return specialty;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public ArrayList<String> getSchedule() {
        return schedule;
    }

    public int getRating() {
        return rating;
    }

    public void setSchedule(ArrayList<String> schedule) {
        this.schedule = schedule;
    }

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}
