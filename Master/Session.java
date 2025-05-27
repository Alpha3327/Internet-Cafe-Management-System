package Master;

public class Session {
    private Customer customer;
    private Computer computer;
    private int duration;

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

    public void endSession() {
        customer.setOnline(false);
        computer.release();
        printSessionInfo();
    }

    public void printSessionInfo() {
        System.out.println(
            customer.getName() + " menggunakan Komputer Nomor " + computer.getNumber() + " selama " + duration + " jam.");
    }
}
