package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class MainView {
    private final Controller controller;
    private DoctorView docView;
    private AdminView adminView;
    private PatientView patientView;

    public MainView(Controller controller) {
        this.controller = controller;
        this.docView = new DoctorView(this, controller);
        this.adminView = new AdminView(this, controller);
        this.patientView=new PatientView(this, controller);
    }

    public void showMainMenu() {
        System.out.println("Välj inloggning:");
        System.out.println("....................");
        System.out.println("1. Patient");
        System.out.println("2. Läkare");
        System.out.println("3. Administratör");
        System.out.println("4. visa alla patienter (test), tar från databasen");
        System.out.println("9. Avsluta");
        controller.setView(controller.handleSelection(1, 9));
    }


    public String[] loginView(int type) {
        JPanel panel = new JPanel();
        JLabel userLabel = new JLabel("Ange användarnamn:");
        JLabel passLabel = new JLabel("Ange lösenord:");
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);

        panel.setLayout(new GridLayout(2, 2));
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);

        String title;
        if (type == 3) {
            title = "Admin Inloggning";
        } else if (type == 2) {
            title = "Läkare Inloggning";
        } else {
            title = "Okänd Inloggning";
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
            System.out.println("Användarnamn: " + username);
            System.out.println("Lösenord: " + password);
            */
            return new String[]{username, password};
        } else {
            System.out.println("Inloggning avbruten.");
            return null; // Returnera null om användaren avbryter inloggningen.
        }
    }
    public void showPatientMenu(){
        patientView.showMenu();
    }

    public void showDocMenu() {
        docView.showMenu();
    }
    public void selectDocMenu(int index){
        docView.select(index);
    }

    public void showAdminMenu() {
        adminView.showMenu();
    }

    public void selectAdminMenu(int index){
        adminView.select(index);
    }

    public boolean isLoggedOut(){
    return docView.isLoggedOut();}


}
