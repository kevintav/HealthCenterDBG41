package view;

import controller.Controller;

public class DoctorView {
    private MainView mainView;
    private boolean loggedOut=false;
    private Controller controller;

    public DoctorView(MainView mainView, Controller controller){
        this.mainView=mainView;
        this.controller=controller;
    }

    public void showMenu(){
        showMessage("1. Availability");
        showMessage("2. View booked appointments.");
        showMessage("3. View your patients");
        showMessage("4. Log out");
   }

    public static void setAvailability(){
        //här ska det komma möjlighet att öppna få tillgängliga timmar och välja relevanta tider.
    }
    public void select(int index) {
        switch (index){
            case 1:
                showMessage("Set your availability");
                mainView.inputAvailability();
                break;
            case 2:
                showMessage("booked appointments");
                break;
            case 3:
                showMessage("showed your patients");
                break;
            case 4:
                showMessage("logging out");
                controller.logOut();
                break;
            default:
                showMessage("Fel input");
                break;

        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public boolean isLoggedOut() {
        return loggedOut;
    }
}


