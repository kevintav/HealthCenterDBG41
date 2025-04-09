package view;

import controller.Controller;

import java.time.LocalDate;
import java.util.Scanner;

public class DoctorView {
    private MainView mainView;
    private boolean loggedOut = false;
    private Controller controller;

    public DoctorView(MainView mainView, Controller controller) {
        this.mainView = mainView;
        this.controller = controller;
    }

    public void showMenu() {
        showMessage("1. Availability");
        showMessage("2. View booked appointments.");
        showMessage("3. View your patients");
        showMessage("4. Add Medical Record");
        showMessage("5. Log out");
    }

    public void handleSelection() {
        int choice = mainView.handleSelection(1, 5);
        switch (choice) {
            case 1:
                showMessage("Set your availability");
                mainView.inputAvailability();
                break;
            case 2:
                getAppointments();
                break;
            case 3:
                showMessage("showed your patients");
                break;
            case 4:
                addMedicalRec();
                break;
            case 5:
                controller.logOut();
                loggedOut = true;
                break;
            default:
                showMessage("Fel input");
                break;
        }
    }

    public void getAppointments() {
        String[] appointments = controller.getAppointmentsByDoctor(Integer.parseInt(controller.getLoginInformation()[0]));
        if (appointments.length == 0) showMessage("No appointments.");
        else for (String a : appointments) showMessage(a);
    }

    private void addMedicalRec() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Patient ID: ");
        int patientId = Integer.parseInt(scan.nextLine());
        System.out.print("Diagnosis: ");
        String diagnosis = scan.nextLine();
        System.out.print("Description: ");
        String desc = scan.nextLine();
        System.out.print("Prescription: ");
        String prescription = scan.nextLine();
        controller.addMedicalRecord(patientId, Integer.parseInt(controller.getLoginInformation()[0]), diagnosis, desc, prescription, LocalDate.now());
        showMessage("Medical record added");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public boolean isLoggedOut() {
        return loggedOut;
    }
}
