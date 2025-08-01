package Master.Computer;

import Master.User.Customer;

public class VipComputer extends Computer { // childclass dari computer
    private final int RATE_PER_HOUR = 20000; // harga perjam komputer sesuai tipe
    private final String type = "VIP";

    public VipComputer(int number) {
        super(number);
    }
    
    public int getRATE_PER_HOUR() {
        return RATE_PER_HOUR;
    }
    
    @Override
    public String getType() {
        return type;
    }
    
    @Override
    public void occupy(Customer user) {
        setCurrentUser(user);
    }

    @Override
    public void release() {
        System.out.printf("Komputer VIP #%d telah selesai digunakan.\n", getNumber());
        setCurrentUser(null);
    }

    @Override
    public int calculatePrice(int duration) {
        return duration * RATE_PER_HOUR;
    }
}