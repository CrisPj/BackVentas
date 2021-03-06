package com.pythonteam.models;

import org.postgresql.geometric.PGpoint;

public class Customer {

    private int id;
    private String name;
    private String phone;
    private String email;
    private PGpoint latlong;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PGpoint getLatlong() {
        return latlong;
    }

    public void setLatlong(PGpoint latlong) {
        this.latlong = latlong;
    }
}
