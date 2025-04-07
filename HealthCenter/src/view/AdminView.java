package view;

import controller.Controller;

public class AdminView {
    private MainView mainView;
    private Controller controller;

    public AdminView(MainView mainView, Controller controller) {
        this.mainView = mainView;
        this.controller = controller;
    }

    public void showMenu() {
        showMessage("Admin Menu:");
        showMessage("1. Add Doctor");
        showMessage("2. Delete Doctor");
        showMessage("3. Set Doctor Specialization");
        showMessage("4. View All Patients");
        showMessage("5. View all Appointments of a specfic Doctor");
        showMessage("6. View Patient Medical Records");
        showMessage("7. Logout");
    }

    public void handleSelection() {
        int choice = mainView.handleSelection(1, 7);
        switch (choice) {
            case 1:
                mainView.addDoctor();
                break;
            case 2:
                mainView.deleteDoctor();
                break;
            case 3:
                mainView.setSpec();
                break;
            case 4:
                mainView.viewAllPatients();
                break;
            case 5:
                mainView.viewAllAppointments();
                break;
            case 6:
                mainView.viewPatientMedicalRecords();
                break;
            case 7:
                showMessage("Logging out...");
                break;
            default:
                showMessage("Invalid selection.");
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
