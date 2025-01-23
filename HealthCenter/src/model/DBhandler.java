package model;

import java.sql.*;
import java.time.LocalDate;

public class DBhandler {
    private static final String URL = "jdbc:postgresql://pgserver.mau.se/";
    private static final String USER = "am2596";
    private static final String PASSWORD = "mm96dq7m";
    private LocalDate registryDate=LocalDate.now();


    //TODO
    // patient: [medicalNbr (PK), f_name, l_name, gender, tel_nr, registryDate]
    // doctor: [id(PK), name, specialization]
    // tendsto: [medicalNbr (FK till patient), doctor(FK till doctor)]

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
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

    public void setSpecialization(int id, String spec) {
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

    public String[][] getAllPatients() {
        String sql = "SELECT * FROM patient ORDER BY medicalnbr ASC";
        String[][] patientsInformation;
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("MedicalNbr: " + rs.getInt("medicalNbr"));
                System.out.println("Name: " + rs.getString("f_name")+ " "+ rs.getString("l_name") );
                System.out.println("tel: 0" + rs.getString("tel_nr"));
                System.out.println("address: " + rs.getString("address"));
                System.out.println("---------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void displayPatient(int identifier) {
        String sql = "SELECT * FROM patient WHERE medicalNbr = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, identifier); // Binda parameter

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("MedicalNbr: " + rs.getInt("medicalNbr"));
                    System.out.println("Name: " + rs.getString("f_name") + " " + rs.getString("l_name"));
                    System.out.println("Tel: 0" + rs.getString("tel_nr"));
                    System.out.println("Address: " + rs.getString("address"));
                    System.out.println("---------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void addPatient(String fName, String lName, String gender, String address, int telNbr, LocalDate birthDate, String password) {
        String sql = "INSERT INTO patient (f_name, l_name, gender, address, tel_nr, birthdate, registry) VALUES (?, ?, ?,?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, fName);
            stmt.setString(2, lName);
            stmt.setString(3, gender);
            stmt.setString(4, address);
            stmt.setInt(5, telNbr);
            stmt.setDate(6, java.sql.Date.valueOf(birthDate)); // Konvertera LocalDate till sql.Date

            // Lägg till registretdatum (idag)
            String registryDate = java.time.LocalDate.now().toString();
            stmt.setDate(7, java.sql.Date.valueOf(registryDate));

            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " rows added.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
