package Master.Computer;

import Master.User.Customer;

public abstract class Computer { // supeerclass computer yang menerapkan abstrak
    private int number;
    private Customer currentUser;

    public Computer(int number) {
    this.number = number;
    this.currentUser = null;
    }

    public Customer getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Customer currentUser) {
        this.currentUser = currentUser;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isAvailable() {
        return currentUser == null;
    }
    // method method yang akan dioverride oleh childclass

    public abstract String getType();

    public abstract void occupy(Customer customer); // ketika customer mulai menggunakan komputer

    public abstract void release(); // ketika customer selesai menggunakan komputer

    public abstract int calculatePrice(int duration); // method untuk menghitung
}