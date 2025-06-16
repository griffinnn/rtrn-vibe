package com.rtrn.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String name;
    private String email;
    private boolean premium;

    private List<Item> items = new ArrayList<>();

    public User(Long id, String name, String email, boolean premium) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.premium = premium;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public List<Item> getItems() {
        return items;
    }
}
