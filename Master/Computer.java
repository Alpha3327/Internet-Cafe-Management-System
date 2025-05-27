package Master;
public abstract class Computer {
    private int number;
    private Customer currentUser;
    private int ratePerHour;
    private String type;

    public Computer(int number) {
        this.number = number;
        this.currentUser = null;
    }

    public Customer getCurrentUser() {
        return currentUser;
    }

    public int getRatePerHour() {
        return ratePerHour;
    }

    public int getNumber() {
        return number;
    }

    public boolean isAvailable() {
        return currentUser == null;
    }
    
    public String getType() {
        return type;
    }

    public void occupy(Customer user) {
        this.currentUser = user;
        System.out.println(getType() + " #" + number + " mulai digunakan oleh " + user.getName() + ".");
    }


    public void release() {
        System.out.println(getType() + " #" + number + " telah selesai digunakan.");
        this.currentUser = null; 
    }
}
