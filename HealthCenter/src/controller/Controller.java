package controller;

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
        Scanner scan = new Scanner(System.in);
        mainView.showMessage("Enter your name");
        String name = scan.nextLine();
        mainView.showMessage("Enter your personal number (10 digits)");
        String something = scan.nextLine();
    }

    public void displayPatient(int id) {
        database.displayPatient(id);
    }
}
