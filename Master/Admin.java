package Master;
import java.util.Scanner;
import java.util.ArrayList;

public class Admin extends User {
    private String adminId;

    public Admin(String name, String password, String adminId) {
        super(name, password);
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }

    @Override
    public void displayInfo() {
        System.out.println("Nama Admin: " + getName() + "\nID Admin: " + adminId);
    }

    @Override
    public boolean login() {
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan Username Admin: ");
        String user = input.nextLine();
        System.out.print("Masukkan Password Admin: ");
        String pass = input.nextLine();

        input.close();
        return getName().equalsIgnoreCase(user) && getPassword().equals(pass);
    }

    public static boolean login(String user, String password, ArrayList<Admin> admins) {
        for (Admin admin : admins) {
            if (admin.getName().equalsIgnoreCase(user) &&
                admin.getPassword().equals(password)) {
                return true;
            }
        }
        return false;       
    }
}