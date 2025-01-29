package controller;

import java.time.LocalDate;
import java.util.List;

import model.DBhandler;
import view.MainView;

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
                mainView.selectPatientMenu(mainView.handleSelection(1, 5));
                break;
            case 2:
                String[] doctorLogin = mainView.loginView(2);
                if (checkDetails(doctorLogin)) {
                    System.out.println("Login successful");
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
                String[] adminLogin = mainView.loginView(3);
                if (checkDetails(adminLogin)) {
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

    public void displayAllDoctors() {
        database.getAllDoctors();
    }

    public void setSpec(int id, String spec) {
        database.setSpecialization(id, spec);
    }

    public boolean checkDetails(String[] details) {
        //TODO kontrollerar id och lösenord, men behöver fixas på något bra sätt:
        // SEND TO DBHANDLER FOR QUERY
        // boolean login_ok = database.checkDetails(user, password);
        // return details[0].equals(" ") && details[1].equals(" ");
        return true;
    }

    public void displayAllPatients(String[] patientArray) {
        mainView.displayAllPatients(patientArray);
    }

    public void displayPatientByID(int id) {
        database.getAllPatient(id);
    }

    public void getAvailability(String docId){
    }

    public void setAvailability(int docId, String weekDay, String time1, String time2, String time3, String time4){
        database.setAvailability(docId, weekDay, time1, time2, time3, time4);
    }




    // Formatering av tiderna


    // Metod för att konvertera veckodagsnummer till namn







}
