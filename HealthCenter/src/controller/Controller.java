package controller;

import model.DBhandler;
import model.Patient;
import view.MainView;

public class Controller {
    private MainView mainView;
    private int selectedIndex;
    private DBhandler database;

    public Controller() {
        this.mainView = new MainView(this);
        this.database=new DBhandler();

        mainView.showMainMenu();

    }

    public void select(int i) {
        this.selectedIndex=i;
        showMenu(selectedIndex);
    }

    public void showMenu(int selectedIndex){
        switch (selectedIndex){
            case 1:
                mainView.setView(1); //PATIENT
                break;
            case 2:
                mainView.setView(2); //LÄKARE
                break;
            case 3:
                mainView.setView(3); //ADMIN
                break;
            case 4:
                Patient newPatient = new Patient(123, "Stefan", "Flöjt", "M", "Stefanvägen 3", 0351, 1230 );
                //database.addPatient(newPatient);

                // Testa att visa alla patienter
                database.displayAllPatients();
                //mainView.setView(4);
                break;
            case 5:
                mainView.setView(5);
                break;
            case 9:
                mainView.setView(9);
            default:
                mainView.showMessage("felaktigt menyval");
                mainView.showMainMenu(); // Återgå till huvudmenyn vid ogiltigt val
        }
    }

    public boolean checkDetails(String[] details) {
        //SKICKA TILL DBHANDLER FÖR QUERY
        //boolean login_ok =database.checkDetails(user,password);
        return details[0].equals("username") || details[1].equals("password");
    }
}
