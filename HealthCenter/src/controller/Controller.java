package controller;

import model.DBhandler;
import view.MainView;

import java.util.Arrays;

public class Controller {
    private MainView mainView;
    private int selectedIndex;
    private DBhandler database;

    public Controller() {
        this.mainView = new MainView(this);
        mainView.showMainMenu();
        this.database=new DBhandler();
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
                mainView.setView(2);    //LÄKARE
                break;
            case 3:
                mainView.setView(3); //ADMIN
                break;
            case 4:
                mainView.setView(4);
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

    public boolean enterDetails(String[] details) {
        //SKICKA TILL DBHANDLER FÖR QUERY
       // boolean login_ok =database.checkDetails(user,password);
        boolean loginOK = false;
        if(details[0].equals("username") || details[1].equals("password")){
            loginOK=true;
        }
    return loginOK;}
}
