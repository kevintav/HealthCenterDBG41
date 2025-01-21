package view;

public class DoctorView {
    private MainView mainView;
    private boolean loggedOut=false;

    public DoctorView(MainView mainView){
        this.mainView=mainView;
    }

    public void showMenu(){

        String menu1= "1. Availability";
        String menu2= "2. View booked appointments.";
        String menu3= "3. View your patients";
        String menu4= "4. Log out";
        System.out.printf("%s %n%s %n%s%n%s%n", menu1,menu2,menu3,menu4);
   }

    public static void setAvailability(){
        //här ska det komma möjlighet att öppna få tillgängliga timmar och välja relevanta tider.
    }
    public void select(int index) {
        switch (index){
            case 1:
                System.out.println("set availability");
                setAvailability();
                break;
            case 2:
                System.out.println("booked appointsments");
                break;
            case 3:
                System.out.println("showed your patients");
                break;
            case 4:
                System.out.println("logging out");
                loggedOut =true;
                break;
            default:
                System.out.println("wrong");
                break;

        }
    }

    public boolean isLoggedOut() {
        return loggedOut;
    }
}


