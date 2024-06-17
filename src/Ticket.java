import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Ticket {
    private  String seat;
    private int price;
    private Person passenger;
    public Ticket(String seat, int price,Person passenger){
        this.seat=seat;
        this.price=price;
        this.passenger=passenger;
    }
    public String seat(){
        return seat;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public Person getPassenger(){
        return passenger;
    }
    public void setPassenger(Person passenger){
        this.passenger=passenger;
    }
    public void print_ticket(){
        System.out.println("Ticket Information:");
        System.out.println("Seat: " + seat);
        System.out.println("Price: £" + price);

    }
    public void save(String path) {
        String file_name = seat + ".txt";
        try {
            FileWriter my_writer=new FileWriter(file_name);
            my_writer.write("Ticket information:\n");
            my_writer.write("Seat: " + seat + "\n");
            my_writer.write("Price: £" + price + "\n");
            my_writer.write("Passenger Information:\n");
            my_writer.write("Name: " + passenger.getName() + "\n");
            my_writer.write("Surname: " + passenger.getSurname() + "\n");
            my_writer.write("Email: " + passenger.getEmail() + "\n");
            my_writer.close();
        } catch (IOException e) {
            e.printStackTrace(); //this is not necessary but its a starndard to use them.it gives the information abput the error so the debigginh will be easy,it will give the class ,methid,line numver where  the exception occured
            System.out.println("An error occurred while saving.");
        }
    }
}
