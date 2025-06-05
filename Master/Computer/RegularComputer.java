package Master.Computer;

import Master.User.Customer;

public class RegularComputer extends Computer {
    private final int RATE_PER_HOUR = 10000;
    private final String type = "Regular";

    public RegularComputer(int number) {
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
        System.out.printf("Komputer regular #%d telah selesai digunakan.\n", getNumber());
        setCurrentUser(null);
    }

    @Override
    public int calculatePrice(int duration) {
        return duration * RATE_PER_HOUR;
    }
}