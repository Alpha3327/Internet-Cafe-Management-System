package Master;
public abstract class Computer {
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

    public abstract String getType();

    public abstract void occupy(Customer customer);

    public abstract void release();
}
