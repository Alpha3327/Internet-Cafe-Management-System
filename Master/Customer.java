package Master;

public class Customer extends User {
    private final String idCustomer;
    private boolean online;

    public Customer(String name, String password, String idCustomer) {
        super(name, password);
        this.idCustomer = idCustomer;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
    this.online = online;
    }

    @Override
    public void displayInfo() {
        System.out.println("Nama: " + getName());
        System.out.println("ID Customer: " + idCustomer);
    }
}