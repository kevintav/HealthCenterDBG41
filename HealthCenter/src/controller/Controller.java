package controller;

import model.DBhandler;
import view.MainView;

import java.time.LocalDate;

public class Controller {
    private final MainView mainView;
    private final DBhandler database;
    private boolean isLoggedIn;
    private String[] loginInfo;

    private final String ADMIN_PASSWORD = "Admin";

    public Controller() {
        this.database = new DBhandler();
        this.mainView = new MainView(this);
        mainView.showMainMenu();
    }

    public void handleMainMenuSelection(int option) {
        switch (option) {
            case 1 -> handlePatientAccess();
            case 2 -> handleDoctorAccess();
            case 3 -> handleAdminAccess();
            case 4 -> exitApplication();
            default -> mainView.showMessage("Invalid input. Try again.");
        }
        mainView.showMainMenu();
    }

    private void handlePatientAccess() {
        mainView.loginSignUpMenu();
        int choice = mainView.handleSelection(1, 2);

        if (choice == 1) {
            loginInfo = mainView.loginView(1);
            if (loginInfo != null && database.authenticateUser(Integer.parseInt(loginInfo[0]), loginInfo[1], 1)) {
                isLoggedIn = true;
                mainView.showPatientMenu();
            } else {
                mainView.showMessage("Login failed.");
            }
        } else {
            mainView.signUpPatient();
        }
    }

    private void handleDoctorAccess() {
        loginInfo = mainView.loginView(2);
        if (loginInfo != null && database.authenticateUser(Integer.parseInt(loginInfo[0]), loginInfo[1], 2)) {
            isLoggedIn = true;
            while (isLoggedIn) {
                mainView.showDocMenu();
                if (mainView.getDoctorView().isLoggedOut()) {
                    isLoggedIn = false;
                }
            }
        } else {
            mainView.showMessage("Login failed.");
        }
    }

    private void handleAdminAccess() {
        String password = mainView.adminLogin();
        if (password != null && password.equals(ADMIN_PASSWORD)) {
            isLoggedIn = true;
            while (isLoggedIn) {
                mainView.showAdminMenu();
                if (mainView.getAdminView().isLoggedOut()) {
                    isLoggedIn = false;
                }
            }
        } else {
            mainView.showMessage("Invalid admin password.");
        }
    }

    public void logOut() {
        isLoggedIn = false;
    }

    public void exitApplication() {
        mainView.showMessage("Shutting down...");
        System.exit(0);
    }

    // ===== Getters and Logic Delegation =====

    public void addPatient(String f, String l, String gender, String address, int phone, LocalDate dob, String pw) {
        database.addPatient(f, l, gender, address, phone, dob, pw);
    }

    public boolean doctorExists(int id) {
        return database.doesDoctorExist(id);
    }

    public void addDoctor(String name, int id, String spec) {
        database.addDoctor(id, name, spec);
    }

    public void deleteDoctor(int id) {
        database.deleteDoctor(id);
    }

    public void updateDoctorSpec(int id, String spec) {
        database.setSpecialization(id, spec);
    }

    public void updateVisitCost(String spec, int cost) {
        database.setVisitCost(spec, cost);
    }

    public void showAllDoctors() {
        database.getAllDoctors();
    }

    public void showAllPatients() {
        database.getAllPatients();
    }

    public String[] getPatientById(int id) {
        return database.getCertainPatient(id);
    }

    public void updatePatientInfo(int medicalNbr, int newPhone, String newAddress) {
        database.updatePatientInfo(medicalNbr, newPhone, newAddress);
    }

    public void setDoctorAvailability(int docId, String day, String t1, String t2, String t3, String t4) {
        database.setAvailability(docId, day, t1, t2, t3, t4);
    }

    public String[][] getDoctorAvailability(int docId) {
        return database.getAvailability(docId);
    }

    public void bookAppointment(int medicalNbr, int docId, LocalDate date, String time) {
        database.bookAppointment(medicalNbr, docId, date, time);
    }

    public String[] getAppointmentsByDoctor(int docId) {
        return database.getAppointmentsByDoctor(docId);
    }

    public String[] getPatientsOfLoggedInDoctor() {
        return database.getPatientsOfDoctor(Integer.parseInt(loginInfo[0]));
    }

    public String[] getMedicalRecordsForLoggedInPatient() {
        return DBhandler.getMedicalRecordsForPatient(Integer.parseInt(loginInfo[0]));
    }

    public String[] getMedicalRecordsForPatient(int patientId) {
        return DBhandler.getMedicalRecordsForPatient(patientId);
    }

    public void addMedicalRecord(int medNbr, int docId, String diagnosis, String desc, String prescription, LocalDate visitDate) {
        database.addMedicalRecord(medNbr, docId, diagnosis, desc, prescription, visitDate);
    }

    public void showTotalVisitCosts() {
        database.getTotalVisitCost();
    }

    public String[] getLoginInformation() {
        return loginInfo;
    }

    public void setLoginInformation(String[] loginInformation) {
        this.loginInfo = loginInformation;
    }
}
