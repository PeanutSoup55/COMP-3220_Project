package org.example.backend;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class appointments {

    public void MakeAppointment(int pid, int did, Date date, String location){
        String sql = "INSERT INTO appointment (p_id, d_id, date, location) VALUES (pid, did, date, location)";

        try(Connection connection = tables.connection(); Statement stmt = connection.createStatement()){
            stmt.execute(sql);
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
}
