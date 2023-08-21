package com.example.healthcareapp;
public class Booking {
    private String date;
    private String time;
    private String userEmail;
    private String doctorName;

    public Booking() {
        // Empty constructor for Firebase
    }

    public Booking(String date, String time, String doctorName, String userEmail) {
        this.date = date;
        this.time = time;
        this.userEmail = userEmail;
        this.doctorName = doctorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
