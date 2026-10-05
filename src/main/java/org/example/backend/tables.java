package org.example.backend;

import java.sql.*;

public class tables {

    public static final String url = "jdbc:sqlite:identifier.sqlite";

    public static Connection connection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url);
        }catch (SQLException e){
            System.err.println("error: " + e.getMessage());
        }
        return connection;
    }
    public static void createTables(){

        String patient = "CREATE TABLE IF NOT EXISTS patient (" +
                " id INTEGER PRIMARY KEY," +
                " name TEXT NOT NULL," +
                " illness TEXT NOT NULL," +
                " symptoms TEXT," +
                " email TEXT UNIQUE NOT NULL," +
                " password TEXT NOT NULL," +
                " phone INT NOT NULL" +
                ");";

        String receptionist = "CREATE TABLE IF NOT EXISTS receptionist (" +
                " id INTEGER PRIMARY KEY," +
                " name TEXT NOT NULL," +
                " email TEXT UNIQUE NOT NULL," +
                " password TEXT NOT NULL," +
                " phone INT NOT NULL" +
                ");";

        String doctor = "CREATE TABLE IF NOT EXISTS doctor (" +
                " id INTEGER PRIMARY KEY," +
                " name TEXT NOT NULL," +
                " field TEXT NOT NULL," +
                " email TEXT UNIQUE NOT NULL," +
                " password TEXT NOT NULL," +
                " phone INT NOT NULL," +
                " emrgphone INT NOT NULL" +
                ");";


        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            stmt.execute(patient);
            stmt.execute(receptionist);
            stmt.execute(doctor);
            System.out.println("Database and table created successfully.");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }

        String query = "INSERT INTO patient (id, name, illness, symptoms, email, password, phone)\n" +
                "VALUES (1, 'rick', 'Flu', 'Fever, Cough, Fatigue', 'rick@gmail.ca', 'a', 5550199);";

        //put sql querys here because for some reason intellij premium is required to do edits to sqlite. those greedy cucks.
        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()){
            stmt.execute(query);
            System.out.println("query executed");
        }catch (SQLException e){
            System.err.println("fix it moron -> " + e.getMessage());
        }
    }

}
