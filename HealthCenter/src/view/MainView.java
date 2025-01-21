package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainView {
    private final Controller controller;
    private DoctorView docView;
    private AdminView adminView;
    private PatientView patientView;

    public MainView(Controller controller) {
        this.controller = controller;
        this.docView = new DoctorView(this, controller);
        this.adminView = new AdminView(this, controller);
        this.patientView = new PatientView(this, controller);
    }

    public void showMainMenu() {
        System.out.println("Select login type:");
        System.out.println("....................");
        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.println("3. Administrator");
        System.out.println("4. Show all patients (test), fetch from database");
        System.out.println("9. Exit");
        controller.setView(controller.handleSelection(1, 9));
    }

    public String[] loginView(int type) {
        JPanel panel = new JPanel();
        JLabel userLabel = new JLabel("Enter username:");
        JLabel passLabel = new JLabel("Enter password:");
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);

        panel.setLayout(new GridLayout(2, 2));
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);

        String title;
        if (type == 3) {
            title = "Admin Login";
        } else if (type == 2) {
            title = "Doctor Login";
        } else {
            title = "Unknown Login";
        }

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            /*
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            */
            return new String[]{username, password};
        } else {
            System.out.println("Login canceled.");
            return null; // Return null if the user cancels the login.
        }
    }

    public void showPatientMenu() {
        patientView.showMenu();
    }

    public void selectPatientMenu(int index) {
        patientView.select(index);
    }

    public void showDocMenu() {
        docView.showMenu();
    }

    public void selectDocMenu(int index) {
        docView.select(index);
    }

    public void showAdminMenu() {
        adminView.showMenu();
    }

    public void selectAdminMenu(int index) {
        adminView.select(index);
    }

    public boolean isLoggedOut() {
        return docView.isLoggedOut();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
