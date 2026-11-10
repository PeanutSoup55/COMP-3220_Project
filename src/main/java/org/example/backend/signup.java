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

    private static String register(String sql, String name, String email, String password, String phone, String... extras) {
        if (blank(name) || blank(email) || blank(password) || blank(phone)) {
            return "all fields required";
        }
    }

    private static boolean blank(String s) {
        return s == null || s.trim().isEmpty();
    }
}