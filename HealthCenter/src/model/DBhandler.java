package model;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**     All tables used in this assignment. All methods in this class except help-methods are to send queries to the database.
 * //-- patient: [medicalNbr (PK), f_name, l_name, gender, tel_nr, registryDate]
 *     //-- doctor: [id (PK), name, specialization, password]
 *     //-- tendsto: [medicalNbr (FK till patient), doctorId (FK till doctor), dateAssigned]
 *     //-- medicalRecord: [medicalNbr (FK till patient), doctorId (FK till doctor), diagnosis, description, prescription, visitDate]
 *     //-- appointment: [medicalNbr (FK till patient), doctorId (FK till doctor), appointmentDate, appointmentTime]
 *     //-- availability: [doctorId (FK till doctor), weekDay, time1, time2, time3, time4]
 *     //-- users: [username (PK), password, role]
 */
public class DBhandler {
    private static final String URL = "jdbc:postgresql://pgserver.mau.se/";
    private static final String USER = "am2596";
    private static final String PASSWORD = "mm96dq7m";
    private LocalDate registryDate = LocalDate.now();


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * This method sends a query to the database to add a doctor to the doctor table.
     * @param id is the unique docId that is used to identify a specific doctor
     * @param fullName The doctors name
     * @param spec The doctors' specialisation.
     * @author Christoffer Björnheimer
     */
    public void addDoctor(int id, String fullName, String spec) {
        String sql = "INSERT INTO doctor (id, fullname,spec) VALUES (?, ?, ?)";
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
    public void deleteDoctor(int id) {
        String sql = "DELETE FROM doctor WHERE id=?;";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id); // Ange ID för doktorn som ska tas bort

            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " rows deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean doesDoctorExist(int id) {
        String sql = "SELECT COUNT(*) FROM doctor WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * This method sends a query to the database to set the specialization of a doctor.
     * @param id is the docId to identify which doctor to alter.
     * @param spec a 2-letter specialization.
     * @author Christoffer Björnheimer
     */
    public void setSpecialization(int id, String spec) {
        String sql = "UPDATE doctor SET spec=? WHERE id=?;";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, spec); // Uppdatera "spec"
            stmt.setInt(2, id);     // Använd det givna id:t

            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " rows updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for sending a query to the database to receive a printout of all the doctors.
     * @author Christoffer Björnheimer
     */
    public void getAllDoctors() {
        System.out.println("Pediatrician (Pe), Oncologist (On), Proctologist (Pr), Orthopedist (Or)");
        String sql = "SELECT * FROM doctor ORDER BY id ASC";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String spec = rs.getString("spec");
                System.out.println("docID: "+id+" , "+fullName + " , " + spec);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //TODO
    // PATIENT TABLE'

    /**
     * This method sends a query to update a specific patient in the patient table.
     * @param medicalNbr unique medicalnbr for a patient.
     * @param newPhone new number to be set as phone number in the table.
     * @param newAddress new address to be set as the phone number in the table.
     * @author Christoffer Björnheimer
     */
    public void updatePatientInfo(int medicalNbr, String newPhone, String newAddress) {
        String sql = "UPDATE patient SET tel_nr = ?, address = ? WHERE medicalNbr = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newPhone);
            stmt.setString(2, newAddress);
            stmt.setInt(3, medicalNbr);

            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " rows updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that sends a query that searches for a specfic patient in the patient database. It then prints
     * the patients information out in the console.
     * @param identifier is the medicalnbr that is used in the search.
     * @author Christoffer Björnheimer
     */
    public String[] getCertainPatient(int identifier) {
        String sql = "SELECT * FROM patient WHERE medicalNbr = ?";
        String[] patientInformation = new String[8];
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, identifier); // Binda parameter

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int mNbr = rs.getInt("medicalNbr");
                    String fullName = rs.getString("f_name") + " " + rs.getString("l_name");
                    String gender = rs.getString("gender");
                    String telNbr = rs.getString("tel_nr");
                    String address = rs.getString("address");
                    String birthdate = rs.getString("birthdate");
                    String registryDate = rs.getString("registry");
                    String password = rs.getString("pw");
                    if (rs.getString("pw") == null) {
                        password = "No password set";
                    }

                    patientInformation[0] = String.valueOf(mNbr);
                    patientInformation[1] = fullName;
                    patientInformation[2] = telNbr;
                    patientInformation[3] = address;
                    patientInformation[4] = gender;
                    patientInformation[5] = birthdate;
                    patientInformation[6] = registryDate;
                    patientInformation[7] = password;
                    return patientInformation;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patientInformation;
    }

    /**
     * Method for sending a query to the database that retrieves all patients in the patients table and prints them out
     * in the console.
     * @author Christoffer Björnheimer
     */
    public void getAllPatients() {
        String sql = "SELECT * FROM patient ORDER BY medicalnbr ASC";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("MedicalNbr: " + rs.getInt("medicalNbr"));
                System.out.println("Name: " + rs.getString("f_name") + " " + rs.getString("l_name"));
                System.out.println("tel: 0" + rs.getString("tel_nr"));
                System.out.println("address: " + rs.getString("address"));
                System.out.println("---------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method sends a query to add a new patient into the patient database. It takes in all necessary parameters
     * needed to fill all columns in the table.
     * @param fName First Name
     * @param lName Last Name
     * @param gender Gender (one letter, ex. M/F/X)
     * @param address Address
     * @param telNbr PhoneNumber (max 10 numbers), int
     * @param birthDate Birth Date
     * @param password Password
     * @author Christoffer Björnheimer
     */
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

    //TODO
    // USER TABLE

    /**
     * This method sends a query to the database that checks if a users password and username matches in the database.
     * This is used to be able to log in and alter the tables.
     * @param userId id.
     * @param password password
     * @return boolean which is true if the password and username has a hit in the database. False otherwise.
     * @author Christoffer Björnheimer
     */


    public boolean authenticateUser (int userId, String password, int userType) {
        String query;
        String storedPassword;

        // Välj SQL-fråga baserat på användartyp
        if (userType == 1) {
            // Autentisering för patienter
            query = "SELECT pw FROM patient WHERE medicalnbr = ?";
        } else if (userType == 2) {
            // Autentisering för läkare
            query = "SELECT password FROM doctor WHERE id = ?";
        } else {
            throw new IllegalArgumentException("Ogiltig användartyp: " + userType);
        }

        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, userId); // Sätt ID som int
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                if (userType == 1) {
                    storedPassword = rs.getString("pw");
                } else {
                    storedPassword = rs.getString("password");
                }
                return storedPassword.equals(password); // Kolla om lösenordet stämmer
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Om ingen användare hittades eller lösenordet inte stämmer
    }

    /**
     *
     * @param doctorId
     * @param weekDay
     * @param timeSlot
     * @return
     */
    public boolean isDoctorAvailable(int doctorId, String weekDay, String timeSlot) {
        String query = "SELECT COUNT(*) FROM availability WHERE docId = ? AND weekDay = ? AND (time1 = ? OR time2 = ? OR time3 = ? OR time4 = ?)";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, doctorId);
            pstmt.setString(2, weekDay);
            pstmt.setString(3, timeSlot);
            pstmt.setString(4, timeSlot);
            pstmt.setString(5, timeSlot);
            pstmt.setString(6, timeSlot);

            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;  // Return true if available, false if not
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param docId
     * @param weekDay
     * @param time1
     * @param time2
     * @param time3
     * @param time4
     * @return
     */
    public boolean setAvailability(int docId, String weekDay, String time1, String time2, String time3, String time4) {
        String checkQuery = "SELECT COUNT(*) FROM availability WHERE docId = ? AND weekDay = ?";
        String insertQuery = "INSERT INTO availability (docId, weekDay, time1, time2, time3, time4) VALUES (?, ?, ?, ?, ?, ?)";
        String updateQuery = "UPDATE availability SET time1 = ?, time2 = ?, time3 = ?, time4 = ? WHERE docId = ? AND weekDay = ?";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, docId);
            checkStmt.setString(2, weekDay);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {

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
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param docId
     * @return
     */
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

    /**
     *
     * @param dayNumber
     * @return
     */
    private String getWeekdayName(String dayNumber) {
        switch (dayNumber) {
            case "1":
                return "Monday";
            case "2":
                return "Tuesday";
            case "3":
                return "Wednesday";
            case "4":
                return "Thursday";
            case "5":
                return "Friday";
            case "6":
                return "Saturday";
            case "7":
                return "Sunday";
            default:
                return "Unknown";
        }
    }

    //TODO
    // TENDSTO TABLE

    /**
     *
     * @param medicalNbr
     * @param doctorId
     */
    public void assignDoctorToPatient(int medicalNbr, int doctorId) {
        String sql = "INSERT INTO tendsto (medicalNbr, doctorId) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, medicalNbr);
            stmt.setInt(2, doctorId);

            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " rows added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO
    // MEDICALRECORD TABLE

    /**
     *
     * @param medicalNbr
     * @param doctorId
     * @param diagnosis
     * @param description
     * @param prescription
     * @param visitDate
     */
    public void addMedicalRecord(int medicalNbr, int doctorId, String diagnosis, String description, String prescription, LocalDate visitDate) {
        String sql = "INSERT INTO medicalRecord (medicalNbr, doctorId, diagnosis, description, prescription, visitDate) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, medicalNbr);
            stmt.setInt(2, doctorId);
            stmt.setString(3, diagnosis);
            stmt.setString(4, description);
            stmt.setString(5, prescription);
            stmt.setDate(6, java.sql.Date.valueOf(visitDate));

            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " rows added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String[] getMedicalRecordsForPatient(int medicalNbr) {
        List<String> recordsList = new ArrayList<>();
        String sql = "SELECT * FROM medicalRecord WHERE medicalNbr = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, medicalNbr);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int doctorId = rs.getInt("doctorId");
                String diagnosis = rs.getString("diagnosis");
                String description = rs.getString("description");
                String prescription = rs.getString("prescription");
                LocalDate visitDate = rs.getDate("visitDate").toLocalDate();

                recordsList.add("Läkare ID: " + doctorId);
                recordsList.add("Diagnos: " + diagnosis);
                recordsList.add("Beskrivning: " + description);
                recordsList.add("Recept: " + prescription);
                recordsList.add("Besöksdatum: " + visitDate);
                recordsList.add("---------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recordsList.toArray(new String[0]);
    }




    //TODO
    // APPOINTMENT TABLE

    /**
     *
     * @param medicalNbr
     * @param doctorId
     * @param appointmentDate
     * @param appointmentTime
     */
    public void bookAppointment(int medicalNbr, int doctorId, LocalDate appointmentDate, String appointmentTime) {
        // Ensure booking is only allowed on Fridays
        if (LocalDate.now().getDayOfWeek() != DayOfWeek.FRIDAY) {
            System.out.println("Appointments can only be booked on Fridays.");
            return;
        }

        // Ensure the doctor is available at the requested time
        if (!isDoctorAvailable(doctorId, appointmentDate.getDayOfWeek().toString(), appointmentTime)) {
            System.out.println("Doctor is not available at the selected time.");
            return;
        }

        String sql = "INSERT INTO appointment (medicalNbr, doctorId, appointmentDate, appointmentTime, bookingTime) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, medicalNbr);
                stmt.setInt(2, doctorId);
                stmt.setDate(3, java.sql.Date.valueOf(appointmentDate));
                stmt.setString(4, appointmentTime);
                stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now())); // Record booking time

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    connection.commit(); // Commit transaction
                    System.out.println("Appointment booked successfully.");
                } else {
                    connection.rollback(); // Rollback if insert fails
                    System.out.println("Appointment booking failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getTotalVisitCost() {
        String sql = "SELECT p.medicalNbr, p.f_name, p.l_name, SUM(v.visit_cost) AS total_cost " +
                "FROM patient p " +
                "JOIN appointment a ON p.medicalNbr = a.medicalNbr " +
                "JOIN doctor d ON a.doctorId = d.id " +
                "JOIN visit_costs v ON d.specialization = v.specialization " +
                "GROUP BY p.medicalNbr, p.f_name, p.l_name " +
                "ORDER BY total_cost DESC";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Total Visit Costs Per Patient:");
            while (rs.next()) {
                int medicalNbr = rs.getInt("medicalNbr");
                String fullName = rs.getString("f_name") + " " + rs.getString("l_name");
                double totalCost = rs.getDouble("total_cost");
                System.out.println("Patient: " + fullName + " (ID: " + medicalNbr + ") - Total Cost: $" + totalCost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
