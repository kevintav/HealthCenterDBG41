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
        System.out.println("1. Handle costs");
        System.out.println("2. Set doctor specialization");
        System.out.println("3. Add doctor to register");
        System.out.println("4. Delete doctor from register");
        System.out.println("5. Log out");
    }

    public void selectView(int index) {
        switch (index) {
            case 1:
                mainView.configureCosts();
                break;
            case 2:
                mainView.setSpec();
                break;
            case 3:
                mainView.addDoctor();
                break;
            case 4:
                mainView.deleteDoctor();
                break;
            case 5:
                showMessage("Logging out");
                controller.logOut();
                break;
            default:
                showMessage("Invalid selection");
                break;
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

}
