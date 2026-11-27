package org.example.backend;

import java.sql.*;

public class tables {

    //db url, kinda important
    public static final String url = "jdbc:sqlite:./identifier.sqlite";

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

        String appointment = "CREATE TABLE IF NOT EXISTS appointment (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " p_id INTEGER NOT NULL," +
                " d_id INTEGER NOT NULL," +
                " date TEXT NOT NULL," +
                " location TEXT NOT NULL," +
                " FOREIGN KEY (p_id) REFERENCES patient(id)," +
                " FOREIGN KEY (d_id) REFERENCES doctor(id)" +
                ");";

        String availability = "CREATE TABLE IF NOT EXISTS availability (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " d_id INTEGER NOT NULL," +
                " slot TEXT NOT NULL," +
                " UNIQUE (d_id, slot)," +
                " FOREIGN KEY (d_id) REFERENCES doctor(id)" +
                ");";


        try (Connection conn = connection(); Statement stmt = conn.createStatement()) {
            stmt.execute(patient);
            stmt.execute(receptionist);
            stmt.execute(doctor);
            stmt.execute(appointment);
            stmt.execute(availability);
            System.out.println("Database and table created successfully.");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
        printReceptionistCredentials();

    }

    //just so i dont forget accounts
    public static void printReceptionistCredentials() {
        String query = """
            SELECT 'patient' AS role, email, password FROM patient
            UNION ALL
            SELECT 'receptionist' AS role, email, password FROM receptionist
            UNION ALL
            SELECT 'doctor' AS role, email, password FROM doctor;
            """;

        try (Connection conn = connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean hasRows = false;
            while (rs.next()) {
                hasRows = true;
                String email = rs.getString("email");
                String password = rs.getString("password");
                System.out.println("Email: " + email + " | Password: " + password);
            }
            if (!hasRows) {
                System.out.println("add stuff.");
            }
        } catch (SQLException e) {
            System.err.println("error dude: " + e.getMessage());
        }
    }

}
