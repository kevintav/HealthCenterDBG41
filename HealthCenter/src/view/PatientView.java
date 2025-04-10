package view;

import controller.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class PatientView {
    private final Scanner scanner = new Scanner(System.in);
    private final MainView mainView;
    private final Controller controller;


    public PatientView(MainView mainView, Controller controller) {
        this.mainView = mainView;
        this.controller = controller;
    }

    public void showMenu() {
        mainView.showMessage("\n-- Patient Menu --");
        mainView.showMessage("1. View & Edit Personal Info");
        mainView.showMessage("2. Search Doctors by Specialization");
        mainView.showMessage("3. View Doctor Availability");
        mainView.showMessage("4. Book Appointment");
        mainView.showMessage("5. View My Medical Records");
        mainView.showMessage("6. Logout");
    }

    public void handleSelection() {
        boolean keepRunning = true;
        while (keepRunning) {
            showMenu();
            int choice = mainView.handleSelection(1, 6);
            switch (choice) {
                case 1 -> viewOrEditPersonalInfo();
                case 2 -> searchDoctors();
                case 3 -> viewDoctorAvailability();
                case 4 -> bookAppointment();
                case 5 -> viewMyMedicalRecords();
                case 6 -> {
                    controller.logOut();
                    keepRunning = false;
                }
            }
        }
    }

    private void viewOrEditPersonalInfo() {
        String[] info = controller.getPatientById(Integer.parseInt(controller.getLoginInformation()[0]));
        for (String s : info) mainView.showMessage(s);

        mainView.showMessage("Do you want to edit phone and address? (Y/N)");
        String input = scanner.nextLine().trim().toUpperCase();
        if (input.equals("Y")) {
            System.out.print("New Phone: ");
            String phone = scanner.nextLine();
            System.out.print("New Address: ");
            String address = scanner.nextLine();
            controller.updatePatientInfo(Integer.parseInt(info[0]), Integer.parseInt(phone), address);
            mainView.showMessage("Information updated.");
        }
    }

    private void searchDoctors() {
        System.out.print("Enter specialization code (e.g., Pe, Or): ");
        String spec = scanner.nextLine();
        controller.showDoctorsBySpec(spec);
    }

    private void viewDoctorAvailability() {
        System.out.print("Enter Doctor ID to view availability: ");
        int docId = Integer.parseInt(scanner.nextLine());
        mainView.printAvailableTimes(controller.getDoctorAvailability(docId));
    }

    private void bookAppointment() {
        System.out.print("Enter Doctor ID: ");
        int docId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        LocalDate date;

        try {
            date = LocalDate.parse(scanner.nextLine());
        } catch (DateTimeParseException e) {
            mainView.showMessage("Invalid date.");
            return;
        }
        System.out.print("Enter appointment time (HH:mm or HH:mm:ss): ");
        String timeInput = scanner.nextLine().trim();
        LocalTime parsedTime;

        try {
            parsedTime = LocalTime.parse(timeInput);  // This will fail if input is invalid
        } catch (DateTimeParseException e) {
            mainView.showMessage("Invalid time format. Please enter as HH:mm or HH:mm:ss.");
            return;
        }
        controller.bookAppointment(Integer.parseInt(controller.getLoginInformation()[0]), docId, date, parsedTime);
    }

    private void viewMyMedicalRecords() {
        String[] records = controller.getMedicalRecordsForLoggedInPatient();
        if (records.length == 0) {
            mainView.showMessage("No medical records found.");
        } else {
            for (String rec : records) mainView.showMessage(rec);
        }
    }
}