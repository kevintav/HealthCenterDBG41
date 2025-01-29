package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    return null;}


    public void getAllPatient(int identifier) {
        String sql = "SELECT * FROM patient WHERE medicalNbr = ?";
        List<String[]> patientInfoList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, identifier); // Binda parameter

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int mNbr = rs.getInt("medicalNbr");
                    String fullName = rs.getString("f_name")+" "+rs.getString("l_name");
                    String telNbr = rs.getString("tel_nr");
                    String address = rs.getString("address");

                    patientInfoList.add(new String[]{String.valueOf(mNbr), fullName, telNbr, address});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean checkDetails(char[][] details) {
        //QUERY FÖR ATT KOLLA OM KOMBINATIONEN FINNS
    return true;}

    public void getAllDoctors() {
        System.out.println("Pediatrician (Pe), Oncologist (On), Proctologist (Pr), Orthopedist (Or)");
        String sql = "SELECT * FROM doctor ORDER BY id ASC";
        List<String[]> doctorInfoList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String spec = rs.getString("spec");
                String password = rs.getString("password");
                System.out.println(fullName+" , "+ spec );
                doctorInfoList.add(new String[]{String.valueOf(id), fullName, spec, password});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean setAvailability(int docId, String weekDay, String time1, String time2, String time3, String time4) {
        String checkQuery = "SELECT COUNT(*) FROM availability WHERE docId = ? AND weekDay = ?";
        String insertQuery = "INSERT INTO availability (docId, weekDay, time1, time2, time3, time4) VALUES (?, ?, ?, ?, ?, ?)";
        String updateQuery = "UPDATE availability SET time1 = ?, time2 = ?, time3 = ?, time4 = ? WHERE docId = ? AND weekDay = ?";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            // Kolla om en post redan finns för docId och weekDay
            checkStmt.setInt(1, docId);
            checkStmt.setString(2, weekDay);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                // Uppdatera befintlig rad
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, time1);
                    updateStmt.setString(2, time2);
                    updateStmt.setString(3, time3);
                    updateStmt.setString(4, time4);
                    updateStmt.setInt(5, docId);
                    updateStmt.setString(6, weekDay);
                    updateStmt.executeUpdate();
                }
            } else {
                // Lägg till ny rad
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, docId);
                    insertStmt.setString(2, weekDay);
                    insertStmt.setString(3, time1);
                    insertStmt.setString(4, time2);
                    insertStmt.setString(5, time3);
                    insertStmt.setString(6, time4);
                    insertStmt.executeUpdate();
                }
            }
            return true; // Operation lyckades
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Något gick fel
        }
    }



    public String[][] getAvailability(int docId) {
        String[][] availabilityArray = new String[7][5]; // 7 dagar, 5 kolumner (1 för veckodag och 4 för tider)
        String query = "SELECT weekDay, time1, time2, time3, time4 FROM availability WHERE docId = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, docId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String weekDayNum = rs.getString("weekDay");
                String time1 = rs.getString("time1");
                String time2 = rs.getString("time2");
                String time3 = rs.getString("time3");
                String time4 = rs.getString("time4");

                // Konvertera vecka till index 0-6 (Måndag till Söndag)
                int weekDayIndex = Integer.parseInt(weekDayNum) - 1;

                // Fyll i arrayen med datan
                availabilityArray[weekDayIndex][0] = getWeekdayName(weekDayNum); // Sätt veckodagen
                availabilityArray[weekDayIndex][1] = time1 != null ? time1 : "-";
                availabilityArray[weekDayIndex][2] = time2 != null ? time2 : "-";
                availabilityArray[weekDayIndex][3] = time3 != null ? time3 : "-";
                availabilityArray[weekDayIndex][4] = time4 != null ? time4 : "-";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availabilityArray;
    }

    private String getWeekdayName(String dayNumber) {
        switch (dayNumber) {
            case "1": return "Monday";
            case "2": return "Tuesday";
            case "3": return "Wednesday";
            case "4": return "Thursday";
            case "5": return "Friday";
            case "6": return "Saturday";
            case "7": return "Sunday";
            default: return "Unknown";
        }
    }


    public void addPatient(String fName, String lName, String gender, String address, int telNbr, LocalDate birthDate, String password) {
        String sql = "INSERT INTO patient (f_name, l_name, gender, address, tel_nr, birthdate, registry) VALUES (?,?,?,?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, fName);
            stmt.setString(2, lName);
            stmt.setString(3, gender);
            stmt.setString(4, address);
            stmt.setInt(5, telNbr);
            stmt.setDate(6, java.sql.Date.valueOf(birthDate)); // Konvertera LocalDate till sql.Date
            stmt.setString(7, password);

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
