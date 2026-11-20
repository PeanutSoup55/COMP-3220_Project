package org.example.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class appointments {

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

    }
}
