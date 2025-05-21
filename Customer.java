import java.util.ArrayList;
import java.util.Scanner;

class Customer extends User implements Login {
    private String id;

    public Customer(String name, String password, String id) {
        super(name, password);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public void displayInfo() {
        System.out.println("Customer Name: " + getName());
        System.out.println("Customer ID: " + id);
    }

    @Override
    public boolean login() {
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan Username Customer: ");
        String user = input.nextLine();
        System.out.print("Masukkan Password Customer: ");
        String pass = input.nextLine();

        input.close();
        return getName().equalsIgnoreCase(user) && getPassword().equals(pass);
    }

    public static boolean login(String name, String password, ArrayList<Customer> customers) {
        for (Customer c : customers) {
            if (c.getName().equalsIgnoreCase(name)
                    && c.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
