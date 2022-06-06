package com.example.contacts;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbFunctions {
    public Connection connect_to_db(String dbname, String username, String password){
        Connection conn;

        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, username, password);
            System.out.println("Connection Success!");
            return conn;
        }catch (Exception e){
            System.out.println("Connection Fail!");
            return null;
        }
    }
}
