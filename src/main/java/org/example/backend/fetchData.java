package org.example.backend;

import org.example.Objects.Appointment;
import org.example.Objects.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class fetchData {


    //static because data doesnt change
    //returns a list of Patients from objects folder
    public static List<Patient> GetPatientData(){
        //make new arraylsit
        List<Patient> patients = new ArrayList<>();

        //just get all patient data
        String sql = "SELECT id, illness, symptoms, name, email, password, phone FROM patient";

        //statement is for query delivery and resultset is the object taht holds the data from the query before sendoff
        try(Connection con = tables.connection(); Statement stmt = con.createStatement(); ResultSet rst = stmt.executeQuery(sql)){

            //while theres a next patient run
            while (rst.next()){
                Patient patient = new Patient(
                        rst.getInt("id"),
                        rst.getString("illness"),
                        rst.getString("symptoms"),
                        rst.getString("name"),
                        rst.getString("email"),
                        rst.getString("password"),
                        rst.getInt("phone")
                );

                //add to list
                patients.add(patient);
            }
        }catch (SQLException e){
            System.err.println("error getting list: " + e.getMessage());
        }

        //return list
        return patients;
    }

    public static List<Appointment> GetAptData(){

    }
}
