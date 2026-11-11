package org.example.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class signup {

    public static String signupPatient(String name, String email, String password, String phone, String illness, String symptoms) {
        if (blank(illness)) return "all fields required";
        String sql = "INSERT INTO patient (name, email, password, phone, illness, symptoms) VALUES (?, ?, ?, ?, ?, ?)";
        return signup(sql, name, email, password, phone, illness.trim(), symptoms == null ? null : symptoms.trim());
    }

    public static String signupDoctor(String name, String email, String password, String phone, String field, String emrgphone) {
        if (blank(field) || blank(emrgphone)) return "all fields required";
        if (email == null || !email.trim().toLowerCase().endsWith("@doctor.ca")) return "invalid doctor email";
        String sql = "INSERT INTO doctor (name, email, password, phone, field, emrgphone) VALUES (?, ?, ?, ?, ?, ?)";
        return signup(sql, name, email, password, phone, field.trim(), emrgphone.trim());
    }

    public static String signupReceptionist(String name, String email, String password, String phone) {
        if (email == null || !email.trim().toLowerCase().endsWith("@clinic.ca")) return "invalid receptionist email";
        String sql = "INSERT INTO receptionist (name, email, password, phone) VALUES (?, ?, ?, ?)";
        return signup(sql, name, email, password, phone);
    }

    private static String signup(String sql, String name, String email, String password, String phone, String... extras) {
        if (blank(name) || blank(email) || blank(password) || blank(phone)) {
            return "all fields required";
        }
        email = email.trim().toLowerCase();
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return "invalid email";
        }

        try (Connection con = tables.connection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, name.trim());
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, phone.trim());
            for (int i = 0; i < extras.length; i++) {
                stmt.setString(5 + i, extras[i]);
            }
            stmt.executeUpdate();
            return "success";
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("UNIQUE")) {
                return "email already registered";
            }
            System.err.println("signup failed: " + e.getMessage());
            return "signup failed";
        }
    }

    private static boolean blank(String s) {
        return s == null || s.trim().isEmpty();
    }
}