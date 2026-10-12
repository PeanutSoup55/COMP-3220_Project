package org.example.Objects;

import java.util.Date;

public class Appointment {
    private Patient patient;
    private Doctor doc;
    private Date date;
    private String location;

    public Appointment(Patient patient, Doctor doc, Date date, String location){
        this.patient = patient;
        this.doc = doc;
        this.date = date;
        this.location = location;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoc() {
        return doc;
    }

    public void setDoc(Doctor doc) {
        this.doc = doc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
