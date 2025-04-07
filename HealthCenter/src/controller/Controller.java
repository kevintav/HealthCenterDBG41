package controller;

import java.time.LocalDate;
import java.util.Arrays;

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
                int signUpOrLogin = mainView.handleSelection(1, 2);

                if (signUpOrLogin == 1) {
                    loginInformation = mainView.loginView(1);
                    boolean loginSuccess = checkDetails(loginInformation, 1);

                    if (loginSuccess) {
                        mainView.showPatientMenu();
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
                    }
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
                    }
                } else {
                    System.out.println("Invalid login attempt");
                }
                break;
            case 4:
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
        try {
            int id = Integer.parseInt(details[0]);
            String password = details[1];
            return database.authenticateUser(id, password, userType);
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
            return false;
        }
    }

    public void displayAllPatients(String[] patientArray) {
        mainView.displayAllPatients(patientArray);
    }

    public void displayPatientByID(String[] patientInformation) {
        mainView.showMessageArray(database.getCertainPatient(Integer.parseInt(patientInformation[0])));
    }

    public void getAvailability(String docId) {
        mainView.printAvailableTimes(database.getAvailability(Integer.parseInt(docId)));
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

    public void handleBooking(int medicalNbr, int doctorId, LocalDate appointmentDate, String appointmentTime) {
        database.bookAppointment(medicalNbr, doctorId, appointmentDate, appointmentTime);
    }

    public void viewAllPatiens() {
        database.getAllPatients();
    }

    public void viewMedicalRecordsForPatient() {
        String[] medicalRecords = DBhandler.getMedicalRecordsForPatient(Integer.parseInt(loginInformation[0]));

        if (medicalRecords.length == 0) {
            System.out.println("Inga medicinska journaler hittades för det angivna numret.");
        } else {
            System.out.println("Medicinska journaler för nummer " + loginInformation[0] + ":");
            for (String record : medicalRecords) {
                System.out.println(record);
            }
        }
    }
    public String[] viewMedicalRecordsForPatient(int medicalID) {
return DBhandler.getMedicalRecordsForPatient(medicalID);
    }

    public String[] getAppointmentsByDoctor(int docId) {
        //TODO FORMATTERING AV SVARET
        System.out.println(Arrays.deepToString(database.getAvailability(docId)));
    return null;}
}
