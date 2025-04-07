package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        showMessage("Select login type:");
        showMessage("....................");
        showMessage("1. Patient");
        showMessage("2. Doctor");
        showMessage("3. Administrator");
        showMessage("4. Visa tillgänglihet för läkare 1. ");
        showMessage("9. Exit");
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

            String title = (type == 1) ? "Patient Login" : "Doctor Login";

            int result = JOptionPane.showConfirmDialog(null, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    controller.logIn();
                    int userId = Integer.parseInt(username);
                    return new String[]{username, password};
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Användarnamn måste vara ett heltal (ID).", "Ogiltigt ID", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                showMessage("Login canceled.");
                return null; // Return null if the user cancels the login.
            }
        }
    }

    public String adminLogin() {
        showMessage("Opening separate window");
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
            showMessage("Login canceled.");
            return null; // Return null if the user cancels the login.
        }
    }

    public int handleSelection(int min, int max) {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Invalid selection. Please choose between " + min + " and " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number");
            }
        }
    }

    public void loginSignUpMenu() {
        showMessage("1. Login");
        showMessage("2. Sign Up");
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
            scanner.nextLine();
            showMessage("Select a password:");
            String password = scanner.nextLine();
            showMessage("Enter your birthdate (YYYY-MM-DD):");

            String birthDateStr = scanner.next();
            LocalDate birthDate = null;
            try {
                birthDate = LocalDate.parse(birthDateStr);
            } catch (DateTimeParseException e) {
                showMessage("Invalid date format. Please use YYYY-MM-DD.");
                scanner.nextLine();
                continue;
            }
            notDone = false;
            controller.HandlePatient(f_name, l_name, gender, address, tel_nbr, birthDate, password);
            showMessage("Patient registrerad");
            showMainMenu();
        }
    }

    public void displayAllPatients(String[] patientArray) {
        for (String patient : patientArray) {
            showMessage(patient);
        }
    }

    //HAR ÄNDRAT ALLA DESSA FÖR ATT VARA ENKLARE ATT HANTERA
    public void showPatientMenu() {
        patientView.showMenu();
        patientView.handleSelection();
    }
    //HAR ÄNDRAT ALLA DESSA FÖR ATT VARA ENKLARE ATT HANTERA
    public void showDocMenu() {
        docView.showMenu();
        docView.handleSelection();
    }
    //HAR ÄNDRAT ALLA DESSA FÖR ATT VARA ENKLARE ATT HANTERA
    public void showAdminMenu() {
        adminView.showMenu();
        adminView.handleSelection();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void inputAvailability() {
        showMessage("Ange ditt docID: ");
        int docId = scanner.nextInt();
        scanner.nextLine();
        showMessage("Ange veckodag: (1 för Mon / 2 för Tis osv) ");
        String weekDay = scanner.nextLine();
        showMessage("Ange 09:00 (F för tillgänglig, B för uppbokad.): ");
        String time1 = scanner.nextLine().trim();
        showMessage("Ange 09:30 ");
        String time2 = scanner.nextLine().trim();
        showMessage("Ange 10:00 ");
        String time3 = scanner.nextLine().trim();
        showMessage("Ange 10:30 ");
        String time4 = scanner.nextLine().trim();
        controller.setAvailability(docId, weekDay, time1, time2, time3, time4);
    }

    private String formatTime(String time) {
        if (time.equals("F")) return "Ledigt";
        if (time.equals("B")) return "-";
        return time;
    }

    public void printAvailableTimes(String[][] availabilityArray) {
        showMessage("+------------+---------+---------+---------+---------+");
        showMessage("| Weekday    |  09:00  |  09:30  |  10:00  |  10:30  |");
        showMessage("+------------+---------+---------+---------+---------+");

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
            showMessage("|   Inga lediga tider tillgängliga                |");
        }
        showMessage("+------------+---------+---------+---------+---------+");
    }

    public void setSpec() {
        showMessage("Select a doctor:");
        controller.displayAllDoctors();
        int docId = -1;

        while (true) {
            showMessage("Ange docID: ");
            if (scanner.hasNextInt()) {
                docId = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                showMessage("Fel: docID måste vara ett heltal. Försök igen.");
                scanner.nextLine();
            }
        }

        String spec;

        while (true) {
            showMessage("Ange specialkunskap");
            showMessage("(Pediatrician (Pe), Oncologist (On), Proctologist (Pr), Orthopedist (Or))");
            spec = scanner.nextLine().trim();

            if (spec.matches("[a-zA-Z]{2}")) {
                break;
            } else {
                showMessage("Fel: Specialkunskap måste vara exakt två bokstäver. Försök igen.");
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
                showMessage("Felaktig inmatning! Ange ett giltigt heltal.");
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

    public void configureCosts() {
        while (true) {
            showMessage("Konfigurera kostnader:");
            showMessage("1. Pediatrician (Pe)");
            showMessage("2. Oncologist (On)");
            showMessage("3. Proctologist (Pr)");
            showMessage("4. Orthopedist (Or)");
            showMessage("5. Tillbaka till huvudmenyn");

            int choice = handleSelection(1, 5);
            if (choice == 5) return;

            showMessage("Ange ny kostnad i SEK:");
            if (scanner.hasNextInt()) {
                int cost = scanner.nextInt();
                scanner.nextLine();

                String code = switch (choice) {
                    case 1 -> "Pe";
                    case 2 -> "On";
                    case 3 -> "Pr";
                    case 4 -> "Or";
                    default -> "";
                };
                //TODO controller.setCost(code, cost);
                //
                showMessage("Kostnad uppdaterad för " + code);
            } else {
                showMessage("Felaktig inmatning. Försök igen.");
                scanner.nextLine();
            }
        }
    }


    public void addDoctor() {
        showMessage("Ange information för den nya läkaren:");
        showMessage("Ange namn: ");
        scanner.nextLine();

        String fullName = scanner.nextLine();

        System.out.print("Skriv in ID för läkaren: ");
        int id = -1;

        while (true) {
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                showMessage("Felaktig inmatning. Ange ett giltigt heltal för ID.");
                scanner.nextLine();
            }
        }
        if (controller.doesDoctorExist(id)) {
            showMessage("Läkaren med ID " + id + " finns redan.");
        } else {
            showMessage("Ange läkarspecialitet (t.ex. Pe för Pediatrician): ");
            String specialty = scanner.nextLine();

            controller.addDoctor(fullName, id, specialty);
            showMessage("Läkaren " + fullName + " med ID " + id + " har lagts till.");
        }
    }

    public void showMessageArray(String[] array) {
        for (String info : array) {
            showMessage(info);
        }
    }

    public void viewAllPatients() {
        controller.viewAllPatiens();
    }

    public void viewAllAppointments() {
        showMessage("Alla tillgängliga läkare:");
        controller.displayAllDoctors();

        showMessage("Ange ID på läkare vars tider du vill se:");
        int docId = -1;

        while (true) {
            if (scanner.hasNextInt()) {
                docId = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                showMessage("Felaktig inmatning. Ange ett giltigt heltal.");
                scanner.nextLine();
            }
        }

        String[] appointments = controller.getAppointmentsByDoctor(docId);
        if (appointments != null && appointments.length > 0) {
            showMessage("Bokade tider för läkare med ID: " + docId);
            showMessageArray(appointments);
        } else {
            showMessage("Inga bokningar hittades för denna läkare.");
        }
    }


    public void viewPatientMedicalRecords() {
        showMessage("Lista över patienter:");
        controller.viewAllPatiens();

        showMessage("Ange patientens ID för att visa journal:");
        int patientId = -1;

        while (true) {
            if (scanner.hasNextInt()) {
                patientId = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                showMessage("Felaktig inmatning. Ange ett giltigt heltal.");
                scanner.nextLine();
            }
        }

            String[] records = controller.viewMedicalRecordsForPatient(Integer.parseInt(String.valueOf(patientId)));
        if (records != null && records.length > 0) {
            showMessage("Medicinsk journal för patient " + patientId + ":");
            showMessageArray(records);
            //TODO HÄR SKA VI LÄGGA TILL EN KALKYL PÅ TOTALCOST FÖR JUST DEN HÄR PATIENTEN OCKSÅ
        } else {
            showMessage("Ingen journal hittades för den patienten.");
        }
    }

}
