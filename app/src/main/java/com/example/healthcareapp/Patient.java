package com.example.healthcareapp;

public class Patient {
    private String name;
    private String email;
    private String password;
    private String age;
    private String gender;
    private String address;
    private String phone_num;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNum() {
        return phone_num;
    }
    public Patient(String name, String email, String password, String age, String gender, String address, String phone_num){//, String docprofileImage) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phone_num = phone_num;
        //this.docprofileImage = docprofileImage;
    }
}
