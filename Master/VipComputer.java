package Master;

public class VipComputer extends Computer {
    private final int RATE_PER_HOUR = 20000;
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
        System.out.printf("VIP Computer #%d telah selesai digunakan.\n", getNumber());
        setCurrentUser(null);
    }
}