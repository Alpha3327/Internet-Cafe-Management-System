public class Computer {
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

    public void setCurrentUser(Customer user) {
        this.currentUser = user;
    }

    public Customer getCurrentUser() {
        return currentUser;
    }

    public void occupy() {
        System.out.println("Komputer " + number + " mulai digunakan.");
    }

    public void release() {
        System.out.println("Komputer " + number + " telah selesai digunakan.");
        this.currentUser = null; 
    }
}
