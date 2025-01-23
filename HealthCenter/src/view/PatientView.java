package view;

import controller.Controller;

import java.time.LocalDate;

public class PatientView {
    private MainView mainView;
    private Controller controller;
    public PatientView(MainView mainView, Controller controller){
        this.controller=controller;
        this.mainView = mainView;
    }
    public void showMenu() {
        System.out.println("1. Sign up");
        System.out.println("2. See your information (requires log in)");
        System.out.println("3. Book appointment (requires log in)");
        System.out.println("4. See your diagnosis/prescription (requires log in)");
        System.out.println("5. Go back");
    }

    public void bookAppointment(){
    }

    public void seeDiagnosis(){
    }


    public void select(int index) {
        switch (index){
            case 1:
                signUpMenu();
                break;
            case 2:
                System.out.println("information /log in");
                break;
            case 3:
                System.out.println("book /log in");
                break;
            case 4:
                System.out.println("diagnosis/ log in");
            case 5:
                System.out.println("Returning to main menu");
                controller.logOut();
                break;
            default:
                System.out.println("wrong");
                break;

        }
    }

    private void signUpMenu() {
        controller.signUp();
    }
}
