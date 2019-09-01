package com.ninjabyte.guianica.model;

public class User {
    public String uid;
    public String name;
    public String photoUrl;
    public String email;

    public User() {
    }

    public User(String uid, String name, String photoUrl, String email) {
        this.uid = uid;
        this.name = name;
        this.photoUrl = photoUrl;
        this.email = email;
    }
}
