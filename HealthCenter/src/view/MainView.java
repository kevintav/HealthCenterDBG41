package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {
    private Scanner scanner = new Scanner(System.in);
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
        while (true) {
            JPanel panel = new JPanel();
            JLabel userLabel = new JLabel("Enter ID:");
            JLabel passLabel = new JLabel("Enter password:");
            JTextField usernameField = new JTextField(10);
            JPasswordField passwordField = new JPasswordField(10);

            panel.setLayout(new GridLayout(2, 2));
            panel.add(userLabel);
            panel.add(usernameField);
            panel.add(passLabel);
            panel.add(passwordField);

            String title;
            if (type == 1) {
                title = "Patient Login";
            } else if (type == 2) {
                title = "Doctor Login";
            } else {
                title = "Unknown Login";
            }

            int result = JOptionPane.showConfirmDialog(null, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());


                try {
                    int userId = Integer.parseInt(username);

                    return new String[]{username, password};
                } catch (NumberFormatException e) {

                    JOptionPane.showMessageDialog(null, "Användarnamn måste vara ett heltal (ID).", "Ogiltigt ID", JOptionPane.ERROR_MESSAGE);

                }
            } else {
                System.out.println("Login canceled.");
                return null; // Return null if the user cancels the login.
            }
        }
    }
    public String adminLogin() {
        JPanel panel = new JPanel();
        JLabel passLabel = new JLabel("Enter password:");

        JPasswordField passwordField = new JPasswordField(10);

        panel.setLayout(new GridLayout(2, 2));
        panel.add(passLabel);
        panel.add(passwordField);


        int result = JOptionPane.showConfirmDialog(null, panel, "Admin Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {

            return new String(passwordField.getPassword());
        } else {
            System.out.println("Login canceled.");
            return null; // Return null if the user cancels the login.
        }
    }

    public int handleSelection(int min, int max) {
        int selection = scanner.nextInt();

        if (selection < min || selection > max) {
            System.out.println("Invalid menu choice");
            return -1;
        }
        return selection;
    }

    public void signUpPatient() {
        boolean notDone = true;
        while (notDone) {
            showMessage("Enter your first name:");
            String f_name = scanner.nextLine();
            showMessage("Enter your last name:");
            String l_name = scanner.nextLine();
            showMessage("Specify gender (F/M/X):");
            String gender = scanner.nextLine();
            showMessage("Enter your address:");
            String address = scanner.nextLine();
            showMessage("Enter your phone number:");
            int tel_nbr = scanner.nextInt();
            showMessage("Select a password:");
            String password = scanner.nextLine();
            showMessage("Enter your birthdate (YYYY-MM-DD):");

            String birthDateStr = scanner.next();
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

    public void setSpec() {
        showMessage("Select a doctor:");
        controller.displayAllDoctors();
        int docId = -1;

        while (true) {
            System.out.print("Ange docID: ");
            if (scanner.hasNextInt()) {
                docId = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Fel: docID måste vara ett heltal. Försök igen.");
                scanner.nextLine();
            }
        }

        String spec;

        while (true) {
            System.out.println("Ange specialkunskap");
            System.out.println("(Pediatrician (Pe), Oncologist (On), Proctologist (Pr), Orthopedist (Or))");
            spec = scanner.nextLine().trim();

            if (spec.matches("[a-zA-Z]{2}")) {
                break;
            } else {
                System.out.println("Fel: Specialkunskap måste vara exakt två bokstäver. Försök igen.");
            }
        }
        controller.setSpec(docId, spec);
    }

    public void deleteDoctor() {
        Scanner scanner = new Scanner(System.in);
        showMessage("Välj en läkare att ta bort:");
        controller.displayAllDoctors();

        int id = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Ange ID för läkaren som ska tas bort: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                validInput = true;
            } else {
                System.out.println("Felaktig inmatning! Ange ett giltigt heltal.");
                scanner.nextLine();
            }
        }

        if (!controller.doesDoctorExist(id)) {
            showMessage("Ingen läkare med ID " + id + " hittades.");
            return;
        }

        System.out.print("Vill du verkligen ta bort denna läkare? (Y/N): ");
        String confirm = scanner.nextLine().trim().toUpperCase();

        if (confirm.equals("Y")) {
            controller.deleteDoctor(id);
            showMessage("Läkaren med ID " + id + " har tagits bort.");
        } else {
            showMessage("Borttagning avbruten.");
        }
    }


    //TODO
    public void configureCosts() {
    }

    public void addDoctor() {
        System.out.println("Ange information för den nya läkaren:");
        System.out.print("Ange namn: ");
        scanner.nextLine();

        String fullName= scanner.nextLine();




        System.out.print("Skriv in ID för läkaren: ");
        int id = -1;


        while (true) {
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Felaktig inmatning. Ange ett giltigt heltal för ID.");
                scanner.nextLine();
            }
        }

        if (controller.doesDoctorExist(id)) {
            System.out.println("Läkaren med ID " + id + " finns redan.");
        } else {

            System.out.print("Ange läkarspecialitet (t.ex. Pe för Pediatrician): ");
            String specialty = scanner.nextLine();


            controller.addDoctor(fullName, id, specialty);
            System.out.println("Läkaren " + fullName + " med ID " + id + " har lagts till.");
        }
    }
}