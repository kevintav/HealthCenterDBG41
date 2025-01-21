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
        this.database=new DBhandler();
        mainView.showMainMenu();

    }

    public void setView(int index) {
        switch (index) {
            case 1:
                mainView.showPatientMenu();
                displayPatient(669); //hämtar specifik patient

                break;
            case 2:
                String[] doctorLogin = mainView.loginView(2);
                if (checkDetails(doctorLogin)) {
                    System.out.println("inloggning lyckades");
                    loginStatus=true;
                    while (!loginStatus){
                        mainView.showDocMenu();
                        mainView.selectDocMenu(handleSelection(1, 9));
                        mainView.isLoggedOut();
                    }
                    break;
                } else {
                    System.out.println("felaktigt inloggsförsök");
                }
                break;
            case 3:
                String[] adminLogin = mainView.loginView(2);
                if (checkDetails(adminLogin)) {
                    System.out.println("inloggning lyckades");
                    boolean loggedOut=false;
                    while (!loggedOut){
                        mainView.showAdminMenu();
                        mainView.selectAdminMenu(handleSelection(1, 9));
                        loggedOut= mainView.isLoggedOut();
                    }
                    break;
                } else {
                    System.out.println("felaktigt inloggningsförsök");
                }
                break;
            case 4:
                displayAllPatients();
                break;
            case 9:
                System.out.println("Avslutar");
                System.exit(0);
            default:
                System.out.println("Fel input");
        }
        mainView.showMainMenu();
    }

    public void logOut(){
        loginStatus=false;
    }

    public int handleSelection(int min, int max) {
        Scanner scan = new Scanner(System.in);
        int selection = scan.nextInt();

        if (selection < min || selection > max) {
            System.out.println("felaktigt menyval");
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
        //SKICKA TILL DBHANDLER FÖR QUERY
        //boolean login_ok =database.checkDetails(user,password);
       //return details[0].equals(" ") && details[1].equals(" ");
        return true;
    }

    public void displayAllPatients() {
        database.displayAllPatients();
    }

    public void signUp() {
    }


    public void displayPatient(int id){
        database.displayPatient(id);
    }
}
