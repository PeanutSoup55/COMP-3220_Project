package org.example.backend;

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
    }
}
