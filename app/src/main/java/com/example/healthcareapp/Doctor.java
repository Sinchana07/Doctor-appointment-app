package com.example.healthcareapp;

public class Doctor {
    private String name;
    private String email;



    public Doctor() {
        // Empty constructor for Firebase
    }

    public Doctor(String name, String email) {
        this.name = name;
        this.email = email;

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
}
