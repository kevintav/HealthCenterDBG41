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
                printAvailableTimes(database.getAvailability(1));
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

    // Formatering av tiderna
    private String formatTime(String time) {
        if (time.equals("F")) return "Ledigt";
        if (time.equals("B")) return "-";
        return time;
    }

    // Metod för att konvertera veckodagsnummer till namn







}
