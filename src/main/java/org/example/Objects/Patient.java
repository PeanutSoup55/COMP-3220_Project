package org.example.Objects;

import org.example.backend.DatabaseStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Patient extends User{

    private static final String UPDATE_PROFILE = "UPDATE patient SET name = ?, phone = ?, illness = ?, symptoms = ? WHERE id = ?";

    private final int patientID;
    private String illness;
    private String symptoms;

    public Patient(int patientID, String illness, String symptoms, String name, String email, String password, int phone){
        super(name, email, password, phone);
        this.patientID = patientID;
        this.illness = illness;
        this.symptoms = symptoms;
    }

    public boolean updateProfile(String name, int phone, String illness, String symptoms) {
        if (name == null || name.isBlank() || illness == null || illness.isBlank() || phone <= 0) {
            return false;
        }
        String newName = name.trim();
        String newIllness = illness.trim();
        String newSymptoms = symptoms == null ? "" : symptoms.trim();

        try (Connection con = DatabaseStore.connection();
             PreparedStatement st = con.prepareStatement(UPDATE_PROFILE)) {
            st.setString(1, newName);
            st.setLong(2, phone);
            st.setString(3, newIllness);
            st.setString(4, newSymptoms);
            st.setInt(5, patientID);
            if (st.executeUpdate() != 1) {
                return false;
            }
        } catch (SQLException e) {
            System.err.println("profile update failed: " + e.getMessage());
            return false;
        }
        setName(newName);
        setPhone(phone);
        this.illness = newIllness;
        this.symptoms = newSymptoms;
        return true;
    }

    public int getPatientID() {
        return patientID;
    }

    public String getIllness() {
        return illness;
    }

    public String getSymptoms() {
        return symptoms;
    }
}
