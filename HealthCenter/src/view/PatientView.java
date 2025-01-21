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
        System.out.println("1. Book appointment (requires log in)");
        System.out.println("1. See your diagnosis/prescription (requires log in)");
    }

    public void bookAppointment(){
    }

    public void seeDiagnosis(){
    }



}
