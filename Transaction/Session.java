package Transaction;
import Master.*;

class Session {
    private Customer customer;
    private Computer computer;
    private int duration;

    public Session(Customer customer, Computer computer, int duration) {
        this.customer = customer;
        this.computer = computer;
        this.duration = duration;
        computer.occupy(customer);
    }

    public void endSession() {
        computer.release();
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void printSessionInfo() {
        System.out.println(
            customer.getName() + " Mengunakan Komputer Nomor " + computer.getNumber() + " Untuk " + duration + " Jam.");
    }
}
