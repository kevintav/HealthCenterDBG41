package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        /*
        String url = "jdbc:postgresql://pgserver.mau.se/";
        String name = "am2596";
        String password = "mm96dq7m"; // System.getenv("PSQLPWD");
        try (Connection conn = DriverManager.getConnection(url, name, password);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM department");
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        Controller controller = new Controller();
        //System.out.println(System.getenv("PATH"));
    }
}
