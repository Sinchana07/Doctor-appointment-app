package com.example.healthcareapp;

public class DoctorClass {
    private String name;
    private String email;
    private String password;
    private String degree;
    private String experience;
    private String clinicAddress;
    private String clinicTimings;
    //private String docprofileImage;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDegree() {
        return degree;
    }

    public String getExperience() {
        return experience;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public String getClinicTimings() {
        return clinicTimings;
    }

    /*public String getDocprofileImage() {
        return docprofileImage;
    }*/

    public DoctorClass(String name, String email, String password, String degree, String experience, String clinicAddress, String clinicTimings){//, String docprofileImage) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.degree = degree;
        this.experience = experience;
        this.clinicAddress = clinicAddress;
        this.clinicTimings = clinicTimings;
        //this.docprofileImage = docprofileImage;
    }
}
