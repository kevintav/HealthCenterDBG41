package model;

import java.time.LocalDate;

public class Patient {
    private int medicalNbr;
    private String f_name;
    private String l_name;
    private String gender;
    private String address;
    private int tel_nr;
    private int birthDate;
    private LocalDate registryDate;

    public Patient(int medicalNbr, String f_name, String l_name, String gender, String address, int tel_nr, int birthDate) {
        this.medicalNbr = medicalNbr;
        this.f_name = f_name;
        this.l_name = l_name;
        this.gender = gender;
        this.address = address;
        this.tel_nr = tel_nr;
        this.birthDate = birthDate;
        this.registryDate=LocalDate.now();
    }

    public String getRegistryDate() {
        return String.valueOf(registryDate);
    }

    public int getMedicalNbr() {
        return medicalNbr;
    }

    public void setMedicalNbr(int medicalNbr) {
        this.medicalNbr = medicalNbr;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTel_nr() {
        return tel_nr;
    }

    public void setTel_nr(int tel_nr) {
        this.tel_nr = tel_nr;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    public void bookAppointment(){
    }
}
