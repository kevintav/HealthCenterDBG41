package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class MainView {
    private Controller controller;
    private DoctorView docView;

    public MainView(Controller controller){
        this.controller=controller;
        System.out.println("test");
    }

    public void showMainMenu(){
        System.out.println("Välj inloggning:");
        System.out.println("....................");
        System.out.println("1. Patient");
        System.out.println("2. Läkare");
        System.out.println("3. Administratör");
        System.out.println("9. Avsluta");
        controller.select(handleSelection(1,9));
    }

    public int handleSelection(int min, int max){
        Scanner scan = new Scanner(System.in);
        int selection = scan.nextInt();

        if(selection<min || selection>max ){
            System.out.println("felaktigt menyval");
            return -1;
        }
        return selection;}

    public void showMessage(String message){
        System.out.println(message);
    }

    public void setView(int index) {
        switch (index){
            case 1:
                System.out.println(PatientView.showMenu());
                break;
            case 2:
                String[] doctorLogin=loginView(2);
                if(controller.checkDetails(doctorLogin)){
                    System.out.println(DoctorView.showMenu());
                    break;
                } else {
                    System.out.println("felaktigt inloggsförsök");
                }
                break;
            case 3:
                String[] adminLogin=loginView(2);
                if(controller.checkDetails(adminLogin)){
                    System.out.println(AdminView.showMenu());
                    break;
                } else {
                    System.out.println("felaktigt inloggsförsök");
                }
                break;
            case 9:
                System.out.println("Avslutar");
                System.exit(0);
            default:
                System.out.println("Fel input");
        }
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

            System.out.println("Användarnamn: " + username);
            System.out.println("Lösenord: " + password);

            return new String[]{username, password};
        } else {
            System.out.println("Inloggning avbruten.");
            return null; // Returnera null om användaren avbryter inloggningen.
        }
    }
}
