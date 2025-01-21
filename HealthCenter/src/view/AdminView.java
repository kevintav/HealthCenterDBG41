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

    public void showMenu() {
        System.out.println("1. Add doctor");
        System.out.println("2. Set doctor specialization");
        System.out.println("3. Delete doctor from register");
        System.out.println("4. Log out");
    }

    public boolean isLoggedOut() {
        return loggedOut;
    }

    public void select(int index) {
        switch (index) {
            case 1:
                addDoctor();
                break;
            case 2:
                System.out.println("Select a doctor:");
                displayAllDoctors();
                break;
            case 3:
                deleteDoctor();
                break;
            case 4:
                System.out.println("Logging out");
                controller.logOut();
                break;
            default:
                System.out.println("Invalid selection");
                break;
        }
    }

    private void addDoctor() {
        System.out.println("Doctor added");
    }

    public void deleteDoctor() {
        System.out.println("Doctor deleted");
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
}
