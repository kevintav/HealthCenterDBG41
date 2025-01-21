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
        // pediatrician (Pe), Oncologist (On), Proctologist (Pr), Orthopedist (Or)


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

    public void addDoctor(int id, String fullName, String spec){
        String sql = "INSERT INTO doctor (id, fullname,spec) VALUES (?, ?, ?)";
        // doctor: [id(PK), name, specialization]
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, fullName);
            stmt.setString(3, spec);

            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " rows added.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setSpec(int id, String spec) {
        String sql = "UPDATE doctor SET spec=? WHERE id=?;";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Bind parametrarna
            stmt.setString(1, spec); // Uppdatera "spec"
            stmt.setInt(2, id);     // Använd det givna id:t

            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " rows updated.");

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

    public void displayAllDoctors() {
        System.out.println("Pediatrician (Pe), Oncologist (On), Proctologist (Pr), Orthopedist (Or)");
        String sql = "SELECT * FROM doctor ORDER BY id ASC";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Id: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("fullName"));
                System.out.println("Specialization: " + rs.getString("spec"));
                System.out.println("---------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
