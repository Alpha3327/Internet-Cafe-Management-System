package Master;
public class VipComputer extends Computer {
    public VipComputer(int number) {
        super(number);
    }

    @Override
    public String getType() {
        return "VIP Computer";
    }
}
