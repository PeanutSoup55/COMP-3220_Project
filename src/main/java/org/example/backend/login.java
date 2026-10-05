package org.example.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login {
    public static String login(String email, String password){
        if (email == null || password == null){
            System.out.println("Input a value");
        }
        String trimmed = email.trim().toLowerCase();
        String table;
        if (trimmed.endsWith("@doctor.ca")){
            table = "doctor";
        }else if (trimmed.endsWith("@clinic.ca")){
            table = "receptionist";
        }else {
            table = "patient";
        }
        String sql = "SELECT id FROM " + table + " WHERE email = ? AND password = ?";

        try(Connection con = tables.connection(); PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1, trimmed);
            stmt.setString(2, password);
            ResultSet rst = stmt.executeQuery();
            if (rst.next()){
                return table;
            }
        }catch (SQLException e){
            System.err.println("invalid login: " + e.getMessage());
        }
        return "invalid";
    }
}
