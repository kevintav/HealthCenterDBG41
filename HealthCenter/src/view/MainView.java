package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        this.patientView = new PatientView(this, controller);
    }

    public void showMainMenu() {
        System.out.println("Select login type:");
        System.out.println("....................");
        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.println("3. Administrator");
        System.out.println("4. Visa tillgänglihet för läkare 1. ");
        System.out.println("9. Exit");
        controller.setView(handleSelection(1, 9));
    }

    public String[] loginView(int type) {
        JPanel panel = new JPanel();
        JLabel userLabel = new JLabel("Enter doctor id:");
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

        int result = JOptionPane.showConfirmDialog(null, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            return new String[]{username, password};
        } else {
            System.out.println("Login canceled.");
            return null; // Return null if the user cancels the login.
        }
    }

    public int handleSelection(int min, int max) {
        Scanner scan = new Scanner(System.in);
        int selection = scan.nextInt();

        if (selection < min || selection > max) {
            System.out.println("Invalid menu choice");
            return -1;
        }
        return selection;
    }

    public void signUpPatient() {
        //TODO, fungerar inte som det ska längre
        boolean notDone = true;
        while (notDone) {
            Scanner scan = new Scanner(System.in);
            showMessage("Enter your first name:");
            String f_name = scan.nextLine();
            showMessage("Enter your last name:");
            String l_name = scan.nextLine();
            showMessage("Specify gender (F/M/X):");
            String gender = scan.nextLine();
            showMessage("Enter your address:");
            String address = scan.nextLine();
            showMessage("Enter your phone number:");
            int tel_nbr = scan.nextInt();
            showMessage("Select a password:");
            String password = scan.nextLine();
            showMessage("Enter your birthdate (YYYY-MM-DD):");

            String birthDateStr = scan.next();
            LocalDate birthDate = null;
            try {
                birthDate = LocalDate.parse(birthDateStr);
            } catch (DateTimeParseException e) {
                showMessage("Invalid date format. Please use YYYY-MM-DD.");
                continue;
            }
            notDone = false;
            controller.HandlePatient(f_name, l_name, gender, address, tel_nbr, birthDate, password);
        }
    }

    public void displayAllPatients(String[] patientArray) {
        for (String patient : patientArray) {
            showMessage(patient);
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
        adminView.selectView(index);
    }

    public boolean isLoggedOut() {
        return docView.isLoggedOut();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void inputAvailability() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ange ditt docID: ");
        int docId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ange veckodag: (1 för Mon / 2 för Tis osv) ");
        String weekDay = scanner.nextLine();
        System.out.print("Ange 09:00 (F för tillgänglig, B för uppbokad.): ");
        String time1 = scanner.nextLine().trim();
        System.out.print("Ange 09:30 ");
        String time2 = scanner.nextLine().trim();
        System.out.print("Ange 10:00 ");
        String time3 = scanner.nextLine().trim();
        System.out.print("Ange 10:30 ");
        String time4 = scanner.nextLine().trim();
        controller.setAvailability(docId, weekDay, time1, time2, time3, time4);


    }

    private String formatTime(String time) {
        if (time.equals("F")) return "Ledigt";
        if (time.equals("B")) return "-";
        return time;
    }

    public void printAvailableTimes(String[][] availabilityArray) {
        System.out.println("+------------+---------+---------+---------+---------+");
        System.out.println("| Weekday    |  09:00  |  09:30  |  10:00  |  10:30  |");
        System.out.println("+------------+---------+---------+---------+---------+");

        boolean hasAvailableTimes = false;


        for (int i = 0; i < 7; i++) {

            if (availabilityArray[i][0] != null) {
                String weekDay = availabilityArray[i][0];
                String time1 = formatTime(availabilityArray[i][1]);
                String time2 = formatTime(availabilityArray[i][2]);
                String time3 = formatTime(availabilityArray[i][3]);
                String time4 = formatTime(availabilityArray[i][4]);

                if (time1.equals("Ledigt") || time2.equals("Ledigt") || time3.equals("Ledigt") || time4.equals("Ledigt")) {
                    hasAvailableTimes = true;
                    System.out.printf("| %-10s | %-7s | %-7s | %-7s | %-7s |\n", weekDay, time1, time2, time3, time4);
                } else {
                    System.out.printf("| %-10s | %-7s | %-7s | %-7s | %-7s |\n", weekDay, "-", "-", "-", "-");
                }
            }
        }

        if (!hasAvailableTimes) {
            System.out.println("|   Inga lediga tider tillgängliga                |");
        }

        System.out.println("+------------+---------+---------+---------+---------+");
    }

}
