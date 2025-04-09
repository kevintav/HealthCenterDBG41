package view;

import controller.Controller;

import java.util.Scanner;

public class AdminView {
    private final MainView mainView;
    private final Controller controller;
    private final Scanner scanner = new Scanner(System.in);
    private boolean loggedOut = false;

    public AdminView(MainView mainView, Controller controller) {
        this.mainView = mainView;
        this.controller = controller;
    }

    public void showMenu() {
        mainView.showMessage("\n-- Admin Menu --");
        mainView.showMessage("1. Add Doctor");
        mainView.showMessage("2. Delete Doctor");
        mainView.showMessage("3. Set Doctor Specialization");
        mainView.showMessage("4. View All Patients");
        mainView.showMessage("5. View Appointments by Doctor");
        mainView.showMessage("6. View Patient Medical Records");
        mainView.showMessage("7. Configure Visit Costs");
        mainView.showMessage("8. View Total Visit Costs per Patient");
        mainView.showMessage("9. Logout");
    }

    public void handleSelection() {
        int choice = mainView.handleSelection(1, 9);
        switch (choice) {
            case 1 -> addDoctor();
            case 2 -> deleteDoctor();
            case 3 -> setDoctorSpecialization();
            case 4 -> controller.showAllPatients();
            case 5 -> viewAppointmentsByDoctor();
            case 6 -> viewMedicalRecords();
            case 7 -> configureVisitCosts();
            case 8 -> controller.showTotalVisitCosts();
            case 9 -> {
                controller.logOut();
                loggedOut = true;
            }
        }
    }

    private void addDoctor() {
        System.out.print("Doctor Name: ");
        String name = scanner.nextLine();
        System.out.print("Doctor ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Specialization (e.g., Pe, Or): ");
        String spec = scanner.nextLine();
        System.out.print("Doctor Phone: ");
        int phone = scanner.nextInt();
        controller.addDoctor(name, id, spec, phone); // modify method
        mainView.showMessage("Doctor added.");
    }

    private void deleteDoctor() {
        System.out.print("Enter Doctor ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        controller.deleteDoctor(id);
        mainView.showMessage("Doctor deleted.");
    }

    private void setDoctorSpecialization() {
        System.out.print("Enter Doctor ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New Specialization: ");
        String spec = scanner.nextLine();
        controller.updateDoctorSpec(id, spec);
        mainView.showMessage("Specialization updated.");
    }

    private void viewAppointmentsByDoctor() {
        System.out.print("Enter Doctor ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        String[] appointments = controller.getAppointmentsByDoctor(id);
        if (appointments.length == 0) mainView.showMessage("No appointments.");
        else for (String a : appointments) mainView.showMessage(a);
    }

    private void viewMedicalRecords() {
        System.out.print("Enter Patient ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        String[] records = controller.getMedicalRecordsForPatient(id);
        if (records.length == 0) mainView.showMessage("No medical records.");
        else for (String r : records) mainView.showMessage(r);
    }

    private void configureVisitCosts() {
        System.out.print("Enter Specialization Code: ");
        String spec = scanner.nextLine();
        System.out.print("Enter New Cost (SEK): ");
        int cost = Integer.parseInt(scanner.nextLine());
        controller.updateVisitCost(spec, cost);
        mainView.showMessage("Visit cost updated.");
    }

    public boolean isLoggedOut() {
        return loggedOut;
    }
}