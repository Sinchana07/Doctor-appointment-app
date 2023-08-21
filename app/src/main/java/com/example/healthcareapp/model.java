package com.example.healthcareapp;

public class model {
    String name, email, clinictimings;

    //Data will be sent to recycler view
    model()
    {

    }
    public model(String name, String email, String clinictimings) {
        this.name = name;
        this.email = email;
        this.clinictimings = clinictimings;
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

    public String getClinictimings() {
        return clinictimings;
    }

    public void setClinictimings(String clinictimings) {
        this.clinictimings = clinictimings;
    }
}
