package org.example.backend;

public class signup {
    public static String signup(String name, String email, String password, String phone){
        if (name == null || email == null || password == null || phone ==  null){
            return "all fields required";
        }

    }
}
