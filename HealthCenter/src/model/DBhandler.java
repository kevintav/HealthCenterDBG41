package model;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBhandler {
    private static final String URL = "jdbc:postgresql://pgserver.mau.se/";
    private static final String USER = "am2596";
    private static final String PASSWORD = "mm96dq7m";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ---------------------- PATIENT ----------------------

    public void addPatient(String fName, String lName, String gender, String address, int telNbr, LocalDate birthDate, String password) {
        String sql = "INSERT INTO patient (f_name, l_name, gender, address, tel_nr, birthdate, registry, pw) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, fName);
            stmt.setString(2, lName);
            stmt.setString(3, gender);
            stmt.setString(4, address);
            stmt.setInt(5, telNbr);
            stmt.setDate(6, Date.valueOf(birthDate));
            stmt.setDate(7, Date.valueOf(LocalDate.now()));
            stmt.setString(8, password);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePatientInfo(int medicalNbr, int newPhone, String newAddress) {
        String sql = "UPDATE patient SET tel_nr = ?, address = ? WHERE medicalNbr = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newPhone);
            stmt.setString(2, newAddress);
            stmt.setInt(3, medicalNbr);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] getCertainPatient(int id) {
        String[] info = new String[8];
        String sql = "SELECT * FROM patient WHERE medicalNbr = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                info[0] = String.valueOf(rs.getInt("medicalNbr"));
                info[1] = rs.getString("f_name") + " " + rs.getString("l_name");
                info[2] = rs.getString("tel_nr");
                info[3] = rs.getString("address");
                info[4] = rs.getString("gender");
                info[5] = rs.getString("birthdate");
                info[6] = rs.getString("registry");
                info[7] = rs.getString("pw");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;
    }

    public void getAllPatients() {
        String sql = "SELECT * FROM patient ORDER BY medicalNbr";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("medicalNbr") + ", Name: " + rs.getString("f_name") + " " + rs.getString("l_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------------- DOCTOR ----------------------

    public void addDoctor(int id, String fullName, String spec) {
        String sql = "INSERT INTO doctor (id, fullname, spec) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, fullName);
            stmt.setString(3, spec);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDoctor(int id) {
        String sql = "DELETE FROM doctor WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean doesDoctorExist(int id) {
        String sql = "SELECT COUNT(*) FROM doctor WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setSpecialization(int id, String spec) {
        String sql = "UPDATE doctor SET spec = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, spec);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllDoctors() {
        String sql = "SELECT * FROM doctor ORDER BY id";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("fullname") + ", Spec: " + rs.getString("spec"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------------- AUTH ----------------------

    public boolean authenticateUser(int id, String password, int type) {
        String sql = (type == 1) ? "SELECT pw FROM patient WHERE medicalNbr = ?" : "SELECT password FROM doctor WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1).equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------------- AVAILABILITY ----------------------

    public boolean setAvailability(int docId, String day, String t1, String t2, String t3, String t4) {
        String check = "SELECT COUNT(*) FROM availability WHERE docId = ? AND weekDay = ?";
        String insert = "INSERT INTO availability (docId, weekDay, time1, time2, time3, time4) VALUES (?, ?, ?, ?, ?, ?)";
        String update = "UPDATE availability SET time1=?, time2=?, time3=?, time4=? WHERE docId=? AND weekDay=?";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(check)) {

            checkStmt.setInt(1, docId);
            checkStmt.setString(2, day);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                try (PreparedStatement updateStmt = conn.prepareStatement(update)) {
                    updateStmt.setString(1, t1);
                    updateStmt.setString(2, t2);
                    updateStmt.setString(3, t3);
                    updateStmt.setString(4, t4);
                    updateStmt.setInt(5, docId);
                    updateStmt.setString(6, day);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insert)) {
                    insertStmt.setInt(1, docId);
                    insertStmt.setString(2, day);
                    insertStmt.setString(3, t1);
                    insertStmt.setString(4, t2);
                    insertStmt.setString(5, t3);
                    insertStmt.setString(6, t4);
                    insertStmt.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[][] getAvailability(int docId) {
        String[][] result = new String[7][5];
        String sql = "SELECT weekDay, time1, time2, time3, time4 FROM availability WHERE docId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, docId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idx = Integer.parseInt(rs.getString("weekDay")) - 1;
                result[idx][0] = getWeekdayName(rs.getString("weekDay"));
                result[idx][1] = rs.getString("time1");
                result[idx][2] = rs.getString("time2");
                result[idx][3] = rs.getString("time3");
                result[idx][4] = rs.getString("time4");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getWeekdayName(String dayNum) {
        return switch (dayNum) {
            case "1" -> "Monday";
            case "2" -> "Tuesday";
            case "3" -> "Wednesday";
            case "4" -> "Thursday";
            case "5" -> "Friday";
            case "6" -> "Saturday";
            case "7" -> "Sunday";
            default -> "Unknown";
        };
    }

    // ---------------------- APPOINTMENTS ----------------------

    public void bookAppointment(int medNbr, int docId, LocalDate date, String time) {
        if (!LocalDate.now().getDayOfWeek().name().equals("FRIDAY")) {
            System.out.println("Appointments can only be booked on Fridays.");
            return;
        }

        String sql = "INSERT INTO appointment (medicalNbr, doctorId, appointmentDate, appointmentTime, bookingTime) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, medNbr);
            stmt.setInt(2, docId);
            stmt.setDate(3, Date.valueOf(date));
            stmt.setString(4, time);
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            System.out.println("Appointment booked.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] getAppointmentsByDoctor(int docId) {
        List<String> results = new ArrayList<>();
        String sql = "SELECT a.medicalNbr, a.appointmentDate, a.appointmentTime, p.f_name, p.l_name FROM appointment a JOIN patient p ON a.medicalNbr = p.medicalNbr WHERE a.doctorId = ? ORDER BY a.appointmentDate, a.appointmentTime";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, docId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add("Patient: " + rs.getString("f_name") + " " + rs.getString("l_name") +
                        " (ID: " + rs.getInt("medicalNbr") + ") - " + rs.getDate("appointmentDate") + " at " + rs.getString("appointmentTime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results.toArray(new String[0]);
    }

    // ---------------------- PATIENTS FOR DOCTOR ----------------------

    public String[] getPatientsOfDoctor(int docId) {
        List<String> patients = new ArrayList<>();
        String sql = "SELECT DISTINCT p.medicalNbr, p.f_name, p.l_name FROM patient p JOIN appointment a ON p.medicalNbr = a.medicalNbr WHERE a.doctorId = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, docId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                patients.add("Patient ID: " + rs.getInt("medicalNbr") + " - " + rs.getString("f_name") + " " + rs.getString("l_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients.toArray(new String[0]);
    }

    // ---------------------- MEDICAL RECORD ----------------------

    public void addMedicalRecord(int medNbr, int docId, String diagnosis, String desc, String prescription, LocalDate visitDate) {
        String sql = "INSERT INTO medicalRecord (medicalNbr, doctorId, diagnosis, description, prescription, visitDate) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, medNbr);
            stmt.setInt(2, docId);
            stmt.setString(3, diagnosis);
            stmt.setString(4, desc);
            stmt.setString(5, prescription);
            stmt.setDate(6, Date.valueOf(visitDate));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String[] getMedicalRecordsForPatient(int medicalNbr) {
        List<String> records = new ArrayList<>();
        String sql = "SELECT * FROM medicalRecord WHERE medicalNbr = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, medicalNbr);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                records.add("Doctor ID: " + rs.getInt("doctorId") +
                        ", Diagnosis: " + rs.getString("diagnosis") +
                        ", Description: " + rs.getString("description") +
                        ", Prescription: " + rs.getString("prescription") +
                        ", Visit Date: " + rs.getDate("visitDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records.toArray(new String[0]);
    }

    // ---------------------- VISIT COSTS ----------------------

    public void setVisitCost(String spec, int cost) {
        String sql = "UPDATE visit_costs SET cost = ? WHERE specialization = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cost);
            stmt.setString(2, spec);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getTotalVisitCost() {
        String sql = "SELECT p.medicalNbr, p.f_name, p.l_name, SUM(v.cost) AS total_cost " +
                "FROM patient p " +
                "JOIN appointment a ON p.medicalNbr = a.medicalNbr " +
                "JOIN doctor d ON a.doctorId = d.id " +
                "JOIN visit_costs v ON d.spec = v.specialization " +
                "GROUP BY p.medicalNbr, p.f_name, p.l_name " +
                "ORDER BY total_cost DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Patient: " + rs.getString("f_name") + " " + rs.getString("l_name") +
                        " (ID: " + rs.getInt("medicalNbr") + ") - Total Cost: " + rs.getDouble("total_cost") + " SEK");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}