package org.example.backend;

import java.sql.*;
import java.time.format.DateTimeFormatter;

public class DatabaseStore {

    public static final DateTimeFormatter SLOT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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

    private static final String TABLE_PATIENT = "CREATE TABLE IF NOT EXISTS patient ("
            + " id INTEGER PRIMARY KEY,"
            + " name TEXT NOT NULL,"
            + " illness TEXT NOT NULL,"
            + " symptoms TEXT,"
            + " email TEXT UNIQUE NOT NULL,"
            + " password TEXT NOT NULL,"
            + " phone INT NOT NULL)";

    private static final String TABLE_RECEPTIONIST = "CREATE TABLE IF NOT EXISTS receptionist ("
            + " id INTEGER PRIMARY KEY,"
            + " name TEXT NOT NULL,"
            + " email TEXT UNIQUE NOT NULL,"
            + " password TEXT NOT NULL,"
            + " phone INT NOT NULL)";

    private static final String TABLE_DOCTOR = "CREATE TABLE IF NOT EXISTS doctor ("
            + " id INTEGER PRIMARY KEY,"
            + " name TEXT NOT NULL,"
            + " field TEXT NOT NULL,"
            + " email TEXT UNIQUE NOT NULL,"
            + " password TEXT NOT NULL,"
            + " phone INT NOT NULL,"
            + " emrgphone INT NOT NULL)";

    private static final String TABLE_APPOINTMENT = "CREATE TABLE IF NOT EXISTS appointment ("
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " p_id INTEGER NOT NULL,"
            + " d_id INTEGER NOT NULL,"
            + " date TEXT NOT NULL,"
            + " location TEXT NOT NULL,"
            + " status TEXT NOT NULL DEFAULT 'SCHEDULED',"
            + " reminder_call INTEGER NOT NULL DEFAULT 0,"
            + " FOREIGN KEY (p_id) REFERENCES patient(id),"
            + " FOREIGN KEY (d_id) REFERENCES doctor(id))";

    private static final String TABLE_AVAILABILITY = "CREATE TABLE IF NOT EXISTS availability ("
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " d_id INTEGER NOT NULL,"
            + " slot TEXT NOT NULL,"
            + " UNIQUE (d_id, slot),"
            + " FOREIGN KEY (d_id) REFERENCES doctor(id))";

    private static final String INDEX_ONE_LIVE_BOOKING =
            "CREATE UNIQUE INDEX IF NOT EXISTS ux_live_doctor_slot ON appointment (d_id, date) "
                    + "WHERE status IN ('SCHEDULED', 'LATE')";

    private static final String SQL_NEXT_OPEN_SLOT =
            "SELECT slot FROM availability WHERE d_id = ? AND slot > ? "
                    + "AND NOT EXISTS (SELECT 1 FROM appointment a WHERE a.d_id = availability.d_id "
                    + "AND a.date = availability.slot AND a.status IN ('SCHEDULED', 'LATE')) "
                    + "ORDER BY slot LIMIT 1";

    public void createTables() {
        try (Connection con = connection(); Statement st = con.createStatement()) {
            st.execute(TABLE_PATIENT);
            st.execute(TABLE_RECEPTIONIST);
            st.execute(TABLE_DOCTOR);
            st.execute(TABLE_APPOINTMENT);
            st.execute(TABLE_AVAILABILITY);
            addColumnIfMissing(con, "appointment", "status", "TEXT NOT NULL DEFAULT 'SCHEDULED'");
            addColumnIfMissing(con, "appointment", "reminder_call", "INTEGER NOT NULL DEFAULT 0");
            try {
                st.execute(INDEX_ONE_LIVE_BOOKING);
            } catch (SQLException e) {
                System.err.println("warning: could not enforce one live booking per doctor slot "
                        + "(duplicates already in the file?): " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    public String findNextOpenSlot(int docId, String afterTime) {
        try (Connection con = connection(); PreparedStatement st = con.prepareStatement(SQL_NEXT_OPEN_SLOT)) {
            st.setInt(1, docId);
            st.setString(2, afterTime);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next() ? rs.getString("slot") : null;
            }
        } catch (SQLException e) {
            System.err.println("findNextOpenSlot failed: " + e.getMessage());
            return null;
        }
    }

    private void addColumnIfMissing(Connection con, String table, String column, String definition) throws SQLException {
        boolean found = false;
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery("PRAGMA table_info(" + table + ")")) {
            while (rs.next()) {
                if (column.equalsIgnoreCase(rs.getString("name"))) {
                    found = true;
                }
            }
        }
        if (!found) {
            try (Statement st = con.createStatement()) {
                st.execute("ALTER TABLE " + table + " ADD COLUMN " + column + " " + definition);
            }
        }
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
