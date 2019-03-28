package com.ninjabyte.guianica.model;

public class Notice {
    private String title;
    private String urlImage;
    private String by;
    private String type;
    private String url;

    public Notice() {
    }

    public String getTitle() {
        return title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getBy() {
        return by;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "title='" + title + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", by='" + by + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
