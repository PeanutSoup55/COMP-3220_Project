package org.example.Objects;

public class Patient extends User{
    private int patientID;
    private String illness;
    private String symptoms;

    public Patient(int patientID, String illness, String symptoms, String name, String email, String password, int phone){
        super(name, email, password, phone);
        this.patientID = patientID;
        this.illness = illness;
        this.symptoms = symptoms;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
}
