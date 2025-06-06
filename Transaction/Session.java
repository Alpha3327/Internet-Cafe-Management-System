package Transaction;
import Master.Computer.*;
import Master.User.*;

public class Session {
    private Customer customer;
    private Computer computer;
    private int duration;
    private boolean isActive;

    public Session(Customer customer, Computer computer, int duration) {
        this.customer = customer;
        this.computer = computer;
        this.duration = duration;
        computer.occupy(customer);
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public boolean getIsActive(){
        return this.isActive;
    }

    public void end() {
        customer.setOnline(false);
        computer.release();
        this.isActive = false;
        printSessionInfo();
    }

    public void printSessionInfo() {
        System.out.printf("%s menggunakan kommputer nomor %d selama %d jam.\n", customer.getName(), computer.getNumber(), duration);
    }
}