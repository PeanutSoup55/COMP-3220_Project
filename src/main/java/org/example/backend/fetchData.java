package org.example.backend;

import org.example.Objects.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class fetchData {

    public static List<Patient> GetPatientData(){
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT patientID, illness, symptoms, name, email, password, phone FROM patient";
        try(Connection con = DriverManager.getConnection(tables.url); Statement stmt = con.createStatement(); ResultSet rst = stmt.executeQuery(sql)){
            while (rst.next()){
                Patient patient = new Patient(
                        rst.getInt("patientID"),
                        rst.getString("illness"),
                        rst.getString("symptoms"),
                        rst.getString("name"),
                        rst.getString("email"),
                        rst.getString("password"),
                        rst.getInt("phone")
                );
                patients.add(patient);
            }
        }catch (SQLException e){
            System.err.println("error getting list: " + e.getMessage());
        }
        return patients;
    }
}
