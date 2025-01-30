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
        showMessage("1. Sign up");
        showMessage("2. See your information (requires log in)");
        showMessage("3. Book appointment (requires log in)");
        showMessage("4. See your diagnosis/prescription (requires log in)");
        showMessage("5. Go back");
    }

    public void bookAppointment() {
    }

    public void seeDiagnosis() {
    }

    public void select(int index) {
        switch (index) {
            case 1:
                mainView.signUpPatient();
                break;
            case 2:
                if (!controller.isLoginStatus()) {
                    mainView.loginView(1);
                }
                mainView.getPatientID();
                break;
            case 3:
                showMessage("book /log in");
                if (!controller.isLoginStatus()) {
                    mainView.loginView(1);
                }
                break;
            case 4:
                showMessage("diagnosis /log in");
                if (!controller.isLoginStatus()) {
                    mainView.loginView(1);
                }
            case 5:
                showMessage("Returning to main menu");
                controller.logOut();
                controller.setView(mainView.handleSelection(1,9));
                break;
            default:
                showMessage("Wrong input");
                break;

        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
