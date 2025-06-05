package Master.User;
import Transaction.*;
import java.util.*;

public class Customer extends User {
    private final String idCustomer;
    private boolean online;
    private ArrayList<Session> custSessions;

    public Customer(String name, String password, String idCustomer) {
        super(name, password);
        this.idCustomer = idCustomer;
        custSessions = new ArrayList<>();
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

    public void displayCustSessions() {
        int index = 0;
        if (custSessions.isEmpty()) {
            System.out.println("Anda belum mempunyai riwayat.");
        } else {
            for (Session session : custSessions) {
            System.out.printf("%d. Komputer %d dengan durasi %d\n", index, session.getComputer().getNumber(), session.getDuration());
            index++;
            }
        }
    }

    public void addSession(Session session) {
        this.custSessions.add(session);
    }

    @Override
    public void displayInfo() {
        System.out.println("Nama: " + getName());
        System.out.println("ID Customer: " + idCustomer);
    }
}