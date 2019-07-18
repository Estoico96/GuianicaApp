package com.ninjabyte.guianica.model;

import android.graphics.drawable.Drawable;

public class About {
    private Drawable image;
    private String title;
    private String description;

    public About(Drawable image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public Drawable getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
