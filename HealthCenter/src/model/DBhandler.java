package model;

import java.sql.*;
import java.time.LocalDate;

public class DBhandler {
    private static final String URL = "jdbc:postgresql://pgserver.mau.se/";
    private static final String USER = "am2596";
    private static final String PASSWORD = "mm96dq7m";
    private LocalDate registryDate=LocalDate.now();


    //todo
    // patient: [medicalNbr (PK), f_name, l_name, gender, tel_nr, registryDate]
    // doctor: [id(PK), name, specialization]
    // tendsto: [medicalNbr (FK till patient), doctor(FK till doctor)]

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

    public void addPatient(Patient newPatient) { //BORDE KANSKE TA IN VÄRDENA ISTÄLLET FÖR ATT DET SKAPAS EN PATIENTKLASS?
        String sql = "INSERT INTO patient (medicalNbr,f_name, l_name, gender, tel_nr,registryDate) VALUES (?, ?, ?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, newPatient.getMedicalNbr());
            stmt.setString(2, newPatient.getF_name());
            stmt.setString(3, newPatient.getL_name());
            stmt.setString(4, newPatient.getGender());
            stmt.setInt(5,newPatient.getTel_nr());
            stmt.setDate(6, Date.valueOf(registryDate));

            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " rows added.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void displayAllPatients() {

        String sql = "SELECT * FROM patient ORDER BY medicalnbr ASC";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("MedicalNbr: " + rs.getInt("medicalNbr"));
                System.out.println("Name: " + rs.getString("f_name")+ " "+ rs.getString("l_name") );
                System.out.println("tel: " + rs.getString("tel_nr"));
                System.out.println("address: " + rs.getString("address"));
                System.out.println("---------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void displayPatient(String identifier){
        //QUERY FÖR ATT VISA EN PATIENT SOM KAN NÅS MED HJÄLP AV EN IDENTIFIER
    }

    public boolean checkDetails(char[][] details) {
        //QUERY FÖR ATT KOLLA OM KOMBINATIONEN FINNS
    return true;}
}
