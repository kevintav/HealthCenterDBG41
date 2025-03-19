package view;
import controller.Controller;

public class AdminView {
    private MainView mainView;
    private Controller controller;

    public AdminView(MainView mainView, Controller controller) {
        this.mainView = mainView;
        this.controller = controller;
    }

    //TODO
    // Add a list of specialization of its doctors such as dentist, cardiologist, or psychiatrists, and their visit cost;

    //TODO
    // Add the information about the health center’s doctors, including employee number, full name, one specialization for each doctor, and
    // phone. Admin can also delete a doctor from the health center.

    public void showMenu() {
        showMessage("Admin Menu:");
        showMessage("1. Add Doctor");
        showMessage("2. Delete Doctor");
        showMessage("3. Set Doctor Specialization");
        showMessage("4. View All Patients");
        showMessage("5. View All Appointments");
        showMessage("6. View Patient Medical Records");
        showMessage("7. View Total Visit Cost per Patient");
        showMessage("8. Logout");
    }

    public void selectView(int index) {
        switch (index) {
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
                //mainView.viewAllPatients();
                break;
            case 5:
                //mainView.viewAllAppointments();
                break;
            case 6:
                //mainView.viewPatientMedicalRecords();
                break;
            case 7:
                //controller.displayTotalVisitCost();
                break;
            case 8:
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
