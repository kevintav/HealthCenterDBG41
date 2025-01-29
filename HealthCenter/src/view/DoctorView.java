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

        String menu1= "1. Availability";
        String menu2= "2. View booked appointments.";
        String menu3= "3. View your patients";
        String menu4= "4. Log out";
        System.out.printf("%s %n%s %n%s%n%s%n", menu1,menu2,menu3,menu4);
   }

    public static void setAvailability(){
        //här ska det komma möjlighet att öppna få tillgängliga timmar och välja relevanta tider.
    }
    public void select(int index) {
        switch (index){
            case 1:
                System.out.println("set availability");
                mainView.inputAvailability();
                break;
            case 2:
                System.out.println("booked appointments");
                break;
            case 3:
                System.out.println("showed your patients");
                break;
            case 4:
                System.out.println("logging out");
                controller.logOut();
                break;
            default:
                System.out.println("wrong");
                break;

        }
    }

    public boolean isLoggedOut() {
        return loggedOut;
    }
}


