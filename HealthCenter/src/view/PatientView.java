package view;

import controller.Controller;

public class PatientView {
    private MainView mainView;
    private Controller controller;

    public PatientView(MainView mainView, Controller controller) {
        this.controller = controller;
        this.mainView = mainView;
    }

    public void showMenu() {
        showMessage("1. See your information");
        showMessage("2. Book appointment");
        showMessage("3. See your diagnosis/prescription");
        showMessage("4. Go back (Log out)");
    }

    public void handleSelection() {
        int choice = mainView.handleSelection(1, 4);
        switch (choice) {
            case 1:
                controller.displayPatientByID(controller.getLoginInformation());
                break;
            case 2:
                bookAppointment();
                break;
            case 3:
                //SE MEDICAL RECORD //TODO kanske lägga till diagnos?
                controller.viewMedicalRecordsForPatient();
                break;
            case 4:
                showMessage("Returning to main menu");
                break;
            default:
                showMessage("Wrong input");
                break;
        }
    }

    public void bookAppointment() {
        showMessage("Vilken typ av läkare vill du träffa?");
        showMessage("1. Ortoped");
        showMessage("2. Onkolog");
        showMessage("3. Pediatriker");

        int chosen = mainView.handleSelection(1, 3);
        switch (chosen) {
            case 1:
                controller.getAvailability(String.valueOf(1));
                break;
            case 2:
                controller.getAvailability(String.valueOf(2));
                break;
            case 3:
                controller.getAvailability(String.valueOf(3));
                break;
            default:
                break;
        }
    }


    public void showMessage(String message) {
        System.out.println(message);
    }
}
