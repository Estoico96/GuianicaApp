package com.ninjabyte.guianica.model;

public class About {
    private String title;
    private String description;

    public About(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
