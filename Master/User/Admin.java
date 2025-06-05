package Master.User;

public class Admin extends User {
    private final String idAdmin;

    public Admin(String name, String password, String adminId) {
        super(name, password);
        this.idAdmin = adminId;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    @Override
    public void displayInfo() {
        System.out.println("Nama: " + getName());
        System.out.println("ID Customer: " + idAdmin);
    }
}