package model;

import java.time.LocalDate;

public class DBhandler {

    private int nbrOfPatients;

    public DBhandler(){

    }

    public void addPatient(Patient newPatient){
        //QUERY FÖR ATT LÄGGA TILL PATIENT I PATIENT-TABLE
        nbrOfPatients++; //för programmets skull?
    }

    public void displayAllPatients(){
        //QUERY FÖR ATT VISA ALLA PATIENTER
    }

    public void displayPatient(String identifier){
        //QUERY FÖR ATT VISA EN PATIENT SOM KAN NÅS MED HJÄLP AV EN IDENTIFIER
    }

    public boolean checkDetails(char[][] details) {
        //QUERY FÖR ATT KOLLA OM KOMBINATIONEN FINNS
    return true;}
}
