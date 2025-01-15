package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBhandler {
    private static final String URL = "jdbc:postgresql://localhost:5432/ap2379";
    private static final String USER = "ap2379";
    private static final String PASSWORD = "iwqfod4v";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addPatient(Patient newPatient){
        //QUERY FÖR ATT LÄGGA TILL PATIENT I PATIENT-TABLE
    }

    public void displayAllPatients(){
        //QUERY FÖR ATT VISA ALLA PATIENTER
    }

    public void displayPatient(String identifier){
        //QUERY FÖR ATT VISA EN PATIENT SOM KAN NÅS MED HJÄLP AV EN IDENTIFIER
    }

    public boolean checkDetails(char[][] details) {
        //QUERY FÖR ATT KOLLA OM KOMBINATIONEN FINNS
    return true;}
}
