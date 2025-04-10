package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MainView {
    private final Scanner scanner = new Scanner(System.in);
    private final Controller controller;
    private final DoctorView doctorView;
    private final AdminView adminView;
    private final PatientView patientView;

    public MainView(Controller controller) {
        this.controller = controller;
        this.doctorView = new DoctorView(this, controller);
        this.adminView = new AdminView(this, controller);
        this.patientView = new PatientView(this, controller);
    }

    public void showMainMenu() {
        showMessage("\nWelcome to the Health Center System");
        showMessage("1. Patient Login/Sign Up");
        showMessage("2. Doctor Login");
        showMessage("3. Admin Login");
        showMessage("4. Exit");
        controller.handleMainMenuSelection(handleSelection(1, 4));
    }

    public void loginSignUpMenu() {
        showMessage("1. Login");
        showMessage("2. Sign Up");
    }

    public String[] loginView(int userType) {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("ID:"));
        JTextField idField = new JTextField(10);
        panel.add(idField);
        panel.add(new JLabel("Password:"));
        JPasswordField pwField = new JPasswordField(10);
        panel.add(pwField);

        String title = (userType == 1) ? "Patient Login" : "Doctor Login";
        int result = JOptionPane.showConfirmDialog(null, panel, title, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Integer.parseInt(idField.getText()); // Validate int input
                return new String[]{idField.getText(), new String(pwField.getPassword())};
            } catch (NumberFormatException e) {
                showMessage("ID must be a number.");
            }
        }
        return null;
    }

    public String adminLogin() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JLabel("Admin Password:"));
        JPasswordField pwField = new JPasswordField(10);
        panel.add(pwField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Admin Login", JOptionPane.OK_CANCEL_OPTION);
        return (result == JOptionPane.OK_OPTION) ? new String(pwField.getPassword()) : null;
    }

    public void signUpPatient() {
        showMessage("-- Patient Registration --");
        System.out.print("First name: ");
        String fName = scanner.nextLine();
        System.out.print("Last name: ");
        String lName = scanner.nextLine();
        System.out.print("Gender (M/F/X): ");
        String gender = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone number (digits only): ");
        int phone = Integer.parseInt(scanner.nextLine());
        System.out.print("Password: ");
        String pw = scanner.nextLine();

        LocalDate birthDate = null;
        while (birthDate == null) {
            System.out.print("Birthdate (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();
            try {
                birthDate = LocalDate.parse(dateInput);
            } catch (DateTimeParseException e) {
                showMessage("Invalid date format. Try again.");
            }
        }
        controller.addPatient(fName, lName, gender, address, phone, birthDate, pw);
        int id = controller.fetchPatientId(fName, lName, phone, birthDate);
        if (id > 0) {
            showMessage("You have registered succesfully. Your new ID is: "+id);
        } else {
            showMessage("Registration failed");
        }
    }

    public void inputAvailability() {
        System.out.print("Enter your doctor ID: ");
        int docId = Integer.parseInt(scanner.nextLine());
        System.out.print("Weekday (1-7): ");
        String weekday = scanner.nextLine();
        System.out.print("09:00 (F/B): ");
        String t1 = scanner.nextLine();
        System.out.print("09:30 (F/B): ");
        String t2 = scanner.nextLine();
        System.out.print("10:00 (F/B): ");
        String t3 = scanner.nextLine();
        System.out.print("10:30 (F/B): ");
        String t4 = scanner.nextLine();

        controller.setDoctorAvailability(docId, weekday, t1, t2, t3, t4);
        showMessage("Availability updated.");
    }

    public void printAvailableTimes(String[][] schedule) {
        showMessage("\n+------------+--------+--------+--------+--------+");
        showMessage("| Weekday    | 09:00 | 09:30 | 10:00 | 10:30 |");
        showMessage("+------------+--------+--------+--------+--------+");

        for (String[] row : schedule) {
            if (row[0] != null) {
                System.out.printf("| %-10s | %-6s | %-6s | %-6s | %-6s |%n",
                        row[0], formatTime(row[1]), formatTime(row[2]), formatTime(row[3]), formatTime(row[4]));
            }
        }
        showMessage("+------------+--------+--------+--------+--------+\n");
    }

    private String formatTime(String slot) {
        return (slot == null || slot.equals("B")) ? "-" : "F";
    }

    public int handleSelection(int min, int max) {
        int choice;
        while (true) {
            try {
                System.out.print("Select option: ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) return choice;
                else showMessage("Choice must be between " + min + " and " + max);
            } catch (NumberFormatException e) {
                showMessage("Invalid input. Enter a number.");
            }
        }
    }

    public void showPatientMenu() {
        patientView.showMenu();
        patientView.handleSelection();
    }

    public void showDocMenu() {
        doctorView.showMenu();
        doctorView.handleSelection();
    }

    public void showAdminMenu() {
        adminView.showMenu();
        adminView.handleSelection();
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public DoctorView getDoctorView() {
        return doctorView;
    }

    public AdminView getAdminView() {
        return adminView;
    }
}