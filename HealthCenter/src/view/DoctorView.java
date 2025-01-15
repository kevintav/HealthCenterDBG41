package view;

public class DoctorView {
    private MainView mainView;

    public DoctorView(MainView mainView){
        this.mainView=mainView;
    }
    public static String showMenu(){

        String menu1= "1. Availability";
        String menu2= "2. View booked appointments.";
        String menu3= "3. View your patients";

   return String.format("%s %n%s %n%s", menu1,menu2,menu3);}

    public static void setAvailability(){
        //här ska det komma möjlighet att öppna få tillgängliga timmar och välja relevanta tider.
    }
}


