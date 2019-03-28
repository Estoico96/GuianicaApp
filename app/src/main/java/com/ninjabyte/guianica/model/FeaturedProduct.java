package com.ninjabyte.guianica.model;

public class FeaturedProduct {

    private String description;
    private String name;
    private String image;
    private String price;

    private FeaturedProduct() {
    }


    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "FeaturedProduct{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
