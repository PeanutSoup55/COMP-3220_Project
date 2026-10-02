package org.example.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class sqllite {

    private static final String url = "jdbc:sqlite:identifier.sqlite";

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
                " symptoms " +
                " email TEXT UNIQUE NOT NULL," +
                " password TEXT NOT NULL" +
                ");";
        String symptoms = "CREATE TABLE IF NOT EXISTS symptoms (" +
                " paintype TEXT NOT NULL," +
                " location TEXT NOT NULL," +
                " duration TEXT NOT NULL" +
                ");";

        String receptionist = "CREATE TABLE IF NOT EXISTS receptionist (" +
                " id INTEGER PRIMARY KEY," +
                " name TEXT NOT NULL," +
                " email TEXT UNIQUE NOT NULL," +
                " password TEXT NOT NULL" +
                ");";

        String doctor = "CREATE TABLE IF NOT EXISTS receptionist (" +
                " id INTEGER PRIMARY KEY," +
                " name TEXT NOT NULL," +
                " field TEXT NOT NULL," +
                " email TEXT UNIQUE NOT NULL," +
                " password TEXT NOT NULL" +
                ");";

        String sql = patient + symptoms + receptionist + doctor;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Database and table created successfully.");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

}
