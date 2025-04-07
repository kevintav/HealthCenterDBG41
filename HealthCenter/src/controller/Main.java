package controller;
//TODO, JAg tror detta är allt som ska fixas
/*
ADMIN
- See a list of all patients (including their medical number and full name) and their total (sum of all) visit costs.
Patient
- Sign up and register him/herself by giving the basic information
- see his/her basic information and edit the information except for the medical number and registration date, which are fix
Doctor
- Define his/her availabilities for each day of the week and time between 9:00 to 11:00
- The doctor can redefine/change the availabilities only if there is no booked appointment at that time for that week
- see a list of all his/her upcoming appointments.
- see a list of his/her patients and add a medical record for each of them

*/
public class Main {
    public static void main(String[] args) {
        new Controller();
    }
}
