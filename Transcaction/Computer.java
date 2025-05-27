package Transcaction;
import Master.*;

public abstract class Computer {
    private int number;
    private Customer currentUser;

    public Computer(int number) {
        this.number = number;
        this.currentUser = null;
    }

    public int getNumber() {
        return number;
    }

    public boolean isAvailable() {
        return currentUser == null;
    }

    public void occupy(Customer user) {
        this.currentUser = user;
        System.out.println(getType() + " #" + number + " mulai digunakan oleh " 
                           + user.getName() + ".");
    }

    public void release() {
        System.out.println(getType() + " #" + number + " telah selesai digunakan.");
        this.currentUser = null; 
    }

    public Customer getCurrentUser() {
        return currentUser;
    }

    /** 
     * Tipe komputer, di-override oleh subclass 
     */
    public abstract String getType();
}
