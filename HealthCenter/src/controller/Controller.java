package controller;

import java.time.LocalDate;

import model.DBhandler;
import view.MainView;

public class Controller {
    private MainView mainView;
    private DBhandler database;
    private boolean loginStatus;
    private String adminPassword = "Admin";
    private String[] loginInformation;

    public Controller() {
        this.mainView = new MainView(this);
        this.database = new DBhandler();
        mainView.showMainMenu();
    }

    public void setView(int index) {
        switch (index) {
            case 1:
                mainView.showPatientMenu();
                mainView.selectPatientMenu(mainView.handleSelection(1,5));
                
                loginInformation = mainView.loginView(1);
                checkDetails(loginInformation,1);
                break;
            case 2:
                loginInformation = mainView.loginView(2);
                if (checkDetails(loginInformation, 2)) {
                    System.out.println("Login successful /doctor");
                    loginStatus = true;
                    while (loginStatus) {
                        mainView.showDocMenu();
                        mainView.selectDocMenu(mainView.handleSelection(1, 9));
                        mainView.isLoggedOut();
                    }
                    break;
                } else {
                    System.out.println("Invalid login attempt");
                }
                break;
            case 3:
                String password = mainView.adminLogin();
                if (password.equals(adminPassword)) {
                    System.out.println("Login successful");
                    loginStatus = true;
                    while (loginStatus) {
                        mainView.showAdminMenu();
                        mainView.selectAdminMenu(mainView.handleSelection(1, 9));
                        mainView.isLoggedOut();
                    }
                    break;
                } else {
                    System.out.println("Invalid login attempt");
                }
                break;
            case 4:
                //skriver ut en läkares tider genom att skicka in docId.
                mainView.printAvailableTimes(database.getAvailability(1));
                break;
            case 9:
                System.out.println("Exiting");
                System.exit(0);
            default:
                System.out.println("Invalid input");
        }
        mainView.showMainMenu();
    }

    public void HandlePatient(String f_name, String l_name, String gender, String address, int tel_nr, LocalDate birthDate, String password) {
        database.addPatient(f_name, l_name, gender, address, tel_nr, birthDate, password);
    }

    public void logOut() {
        loginStatus = false;
    }

    public void logIn() {
        loginStatus = true;
    }

    public void setSpec(int id, String spec) {
        database.setSpecialization(id, spec);
    }

    public boolean checkDetails(String[] details, int userType) {
        String username = details[0];
        String password = details[1];
        if (details.length != 2) {
            throw new IllegalArgumentException("Details array must contain 2 elements: ID and password.");
        }

        int userId;
        try {
            userId = Integer.parseInt(details[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ogiltigt ID-format: " + details[0]);
        }
        return database.authenticateUser(userId, details[1], userType);
    }

    public void displayAllPatients(String[] patientArray) {
        mainView.displayAllPatients(patientArray);
    }

    public void displayPatientByID(int id) {
        mainView.showMessageArray(database.getCertainPatient(id));
    }

    public void getAvailability(String docId) {
    }

    public void setAvailability(int docId, String weekDay, String time1, String time2, String time3, String time4) {
        database.setAvailability(docId, weekDay, time1, time2, time3, time4);
    }

    public boolean doesDoctorExist(int id) {
        return database.doesDoctorExist(id);
    }

    public void displayAllDoctors() {
        database.getAllDoctors();
    }

    public void deleteDoctor(int id) {
        database.deleteDoctor(id);
    }

    public void addDoctor(String fullName, int id, String specialty) {
        database.addDoctor(id, fullName, specialty);
    }

    public boolean isLoginStatus() {
        return loginStatus;
    }
}
