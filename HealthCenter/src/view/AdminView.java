package view;
import controller.Controller;

public class AdminView {
    private MainView mainView;
    private boolean loggedOut = false;
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
        System.out.println("1. Add doctor");
        System.out.println("2. Set doctor specialization");
        System.out.println("3. Delete doctor from register");
        System.out.println("4. Log out");
    }

    public boolean isLoggedOut() {
        return loggedOut;
    }

    public void selectView(int index) {
        switch (index) {
            case 1:
                showMessage("Doctor added");
                break;
            case 2:
                showMessage("Select a doctor:");
                displayAllDoctors();
                break;
            case 3:
                showMessage("Doctor deleted");
                break;
            case 4:
                showMessage("Logging out");
                controller.logOut();
                break;
            default:
                showMessage("Invalid selection");
                break;
        }
    }

    public void setSpec(int id, String spec) {
        controller.setSpec(id, spec);
    }

    public void addInfo() {
        // Future implementation
    }

    public void displayDoctor(int id) {
        // Future implementation
    }

    public void displayAllDoctors() {
        controller.displayAllDoctors();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

}
