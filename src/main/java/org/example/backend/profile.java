package org.example.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class profile {

    public static String[] get(int patientId) {
        String sql = "SELECT name, phone, illness, symptoms FROM patient WHERE id = ?";

        try (Connection con = DatabaseStore.connection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, patientId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new String[]{
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("illness"),
                            Objects.toString(rs.getString("symptoms"), "")
                    };
                }
            }
        } catch (SQLException e) {
            System.err.println("profile get failed: " + e.getMessage());
        }
        return new String[]{"", "", "", ""};
    }

    public static boolean update(int patientId, String name, String phone, String illness, String symptoms) {
        if (name.isBlank() || phone.isBlank() || illness.isBlank()) {
            return false;
        }
        String sql = "UPDATE patient SET name = ?, phone = ?, illness = ?, symptoms = ? WHERE id = ?";

        try (Connection con = DatabaseStore.connection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, name.trim());
            stmt.setString(2, phone.trim());
            stmt.setString(3, illness.trim());
            stmt.setString(4, symptoms.trim());
            stmt.setInt(5, patientId);

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("profile update failed: " + e.getMessage());
            return false;
        }
    }
}