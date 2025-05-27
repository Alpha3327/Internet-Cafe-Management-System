package Master;
// RegularComputer.java
public class RegularComputer extends Computer {
    public RegularComputer(int number) {
        super(number);
    }

    @Override
    public String getType() {
        return "Regular Computer";
    }
}
