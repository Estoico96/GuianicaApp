package com.ninjabyte.guianica.model;

public class Company {
   private String name;
   private String logoUrl;


    public Company() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }
}
