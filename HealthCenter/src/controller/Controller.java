package controller;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import model.DBhandler;
import model.Patient;
import view.MainView;

import java.util.Scanner;

public class Controller {
    private MainView mainView;
    private DBhandler database;
    private boolean loginStatus;

    public Controller() {
        this.mainView = new MainView(this);
        this.database = new DBhandler();
        mainView.showMainMenu();
    }

    public void setView(int index) {
        switch (index) {
            case 1:
                mainView.showPatientMenu();
                mainView.selectPatientMenu(handleSelection(1, 5));
                break;
            case 2:
                String[] doctorLogin = mainView.loginView(2);
                if (checkDetails(doctorLogin)) {
                    System.out.println("Login successful");
                    loginStatus = true;
                    while (loginStatus) {
                        mainView.showDocMenu();
                        mainView.selectDocMenu(handleSelection(1, 9));
                        mainView.isLoggedOut();
                    }
                    break;
                } else {
                    System.out.println("Invalid login attempt");
                }
                break;
            case 3:
                String[] adminLogin = mainView.loginView(3);
                if (checkDetails(adminLogin)) {
                    System.out.println("Login successful");
                    loginStatus = true;
                    while (loginStatus) {
                        mainView.showAdminMenu();
                        mainView.selectAdminMenu(handleSelection(1, 9));
                        mainView.isLoggedOut();
                    }
                    break;
                } else {
                    System.out.println("Invalid login attempt");
                }
                break;
            case 4:
                displayAllPatients();
                break;
            case 9:
                System.out.println("Exiting");
                System.exit(0);
            default:
                System.out.println("Invalid input");
        }
        mainView.showMainMenu();
    }

    public void logOut() {
        loginStatus = false;
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

    public void displayAllDoctors() {
        database.displayAllDoctors();
    }

    public void setSpec(int id, String spec) {
        database.setSpec(id, spec);
    }

    public boolean checkDetails(String[] details) {
        // SEND TO DBHANDLER FOR QUERY
        // boolean login_ok = database.checkDetails(user, password);
        // return details[0].equals(" ") && details[1].equals(" ");
        return true;
    }

    public void displayAllPatients() {
        database.displayAllPatients();
    }

    public void signUp() {
        // TODO: Add functionality here
        boolean notDone = true;
        while (notDone) {
            Scanner scan = new Scanner(System.in);
            mainView.showMessage("Enter your first name:");
            String f_name = scan.nextLine();
            mainView.showMessage("Enter your last name:");
            String l_name = scan.nextLine();
            mainView.showMessage("Specify gender (F/M/X):");
            String gender = scan.nextLine();
            mainView.showMessage("Enter your address:");
            String address = scan.nextLine();
            mainView.showMessage("Enter your phone number:");
            int tel_nbr = scan.nextInt();
            mainView.showMessage("Enter your birthdate (YYYY-MM-DD):");

            String birthDateStr = scan.next(); // Läser födelsedatum som sträng

            // Försök att konvertera strängen till LocalDate och fånga eventuella fel
            LocalDate birthDate = null;
            try {
                birthDate = LocalDate.parse(birthDateStr);
            } catch (DateTimeParseException e) {
                mainView.showMessage("Invalid date format. Please use YYYY-MM-DD.");
                continue; // Be användaren att försöka igen
            }

            // Om konverteringen lyckas, fortsätt
            notDone = false;
            database.addPatient(f_name, l_name, gender, address, tel_nbr, birthDate);
        }
    }

    public void displayPatient(int id) {
        database.displayPatient(id);
    }
}
