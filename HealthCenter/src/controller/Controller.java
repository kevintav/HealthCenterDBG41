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
}
