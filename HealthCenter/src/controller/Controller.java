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
                mainView.loginSignUpMenu();
                int signUpOrLogin = mainView.handleSelection(1,2);

                if (signUpOrLogin == 1) {
                    loginInformation = mainView.loginView(1);
                    boolean loginSuccess = checkDetails(loginInformation,1);

                    if (loginSuccess) {
                        mainView.showPatientMenu();
                        int patientChoice = mainView.handleSelection(1, 5);
                        mainView.selectPatientMenu(patientChoice);
                    } else {
                        System.out.println("Invalid login. Returning to main menu");
                    }
                } else if (signUpOrLogin == 2) {
                    mainView.signUpPatient();
                }
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
        if (details.length != 2) {
            System.out.println("Invalid input. Please enter both ID and password.");
            return false;
        }

        int userId;
        try {
            userId = Integer.parseInt(details[0]); // Ensure ID is a number
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter numbers only.");
            return false;
        }

        return database.authenticateUser(userId, details[1], userType);
    }

    public void displayAllPatients(String[] patientArray) {
        mainView.displayAllPatients(patientArray);
    }

    public void displayPatientByID(String[] patientInformation) {
        mainView.showMessageArray(database.getCertainPatient(Integer.parseInt(patientInformation[0])));
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

    public String[] getLoginInformation() {
        return loginInformation;
    }

    public void setLoginInformation(String[] loginInformation) {
        this.loginInformation = loginInformation;
    }

    public void signUpOrLogin(int type) {
        if (type == 1) {

        }
    }

    public void handleBooking(int medicalNbr, int doctorId, LocalDate appointmentDate, String appointmentTime) {
        database.bookAppointment(medicalNbr, doctorId, appointmentDate, appointmentTime);
    }

}
