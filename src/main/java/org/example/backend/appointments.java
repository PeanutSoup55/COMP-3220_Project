package org.example.backend;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class appointments {

    private static final DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static String now(){
        return LocalDateTime.now().format(time);
    }

    public static boolean book(int pid, int did, String slot){
        String sql = "INSERT INTO appointment (p_id, d_id, date, location) "
                + "SELECT ?, ?, ?, 'Clinic' "
                + "WHERE EXISTS (SELECT 1 FROM availability WHERE d_id = ? AND slot = ?) "
                + "AND NOT EXISTS (SELECT 1 FROM appointment WHERE d_id = ? AND date = ?)";

        try(Connection connection = tables.connection(); PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, pid);
            stmt.setInt(2, did);
            stmt.setString(3, slot);
            stmt.setInt(4, did);
            stmt.setString(5, slot);
            stmt.setInt(6, did);
            stmt.setString(7, slot);
            return stmt.executeUpdate() == 1;
        }catch (SQLException e){
            System.err.println("well that didnt work: " + e.getMessage());
            return false;
        }
    }

    public static boolean cancelOwn(int aid, int pid){
        String sql = "DELETE FROM appointment WHERE id = ? AND p_id = ? AND date > ?";

        try (Connection con = tables.connection(); PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setInt(1, aid);
            stmt.setInt(2, pid);
            stmt.setString(3, now());
            return stmt.executeUpdate() == 1;
        }catch (SQLException e){
            System.err.println("that also didnt work: " + e.getMessage());
            return false;
        }
    }

    public static boolean cancelAny(int aid){
        String sql = "DELETE FROM appointment WHERE id = ?";
        try (Connection con = tables.connection(); PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setInt(1, aid);
            return stmt.executeUpdate() == 1;
        }catch (SQLException e){
            System.err.println("u kinda suck at this");
            return false;
        }
    }

    public static List<String[]> forPatient(int pid){
        List<String[]> rows = new ArrayList<>();
        String sql = "SELECT a.id, a.date, d.name AS doctor_name, d.field, a.location "
                + "FROM appointment a JOIN doctor d ON d.id = a.d_id "
                + "WHERE a.p_id = ? ORDER BY a.date";
        try(Connection con = tables.connection(); PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setInt(1, pid);
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    rows.add(new String[]{
                            rs.getString("id"),
                            rs.getString("date"),
                            rs.getString("name"),
                            rs.getString("field"),
                            rs.getString("location")
                    });
                }
            }
        }catch (SQLException e){
            System.err.println("like really suck at this: " + e.getMessage());
        }
        return rows;
    }

    public static List<String[]> fields() {
        List<String[]> rows = new ArrayList<>();
        String sql = "SELECT DISTINCT field FROM doctor ORDER BY field";

        try (Connection con = tables.connection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                rows.add(new String[]{rs.getString("field")});
            }
        } catch (SQLException e) {
            System.err.println("fields failed: " + e.getMessage());
        }
        return rows;
    }

    public static List<String[]> doctors(String field) {
        List<String[]> rows = new ArrayList<>();
        boolean filtered = field != null && !field.isEmpty();
        String sql = filtered
                ? "SELECT id, name, field FROM doctor WHERE field = ? ORDER BY name"
                : "SELECT id, name, field FROM doctor ORDER BY name";

        try (Connection con = tables.connection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (filtered) {
                stmt.setString(1, field);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rows.add(new String[]{
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("field")
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("doctors failed: " + e.getMessage());
        }
        return rows;
    }
}
