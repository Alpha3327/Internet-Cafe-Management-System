package Master.User;
import Transaction.*;
import java.util.*;

public class Customer extends User { // child class dari user
    private final String idCustomer;
    private boolean online;
    private ArrayList<Session> custSessions; // untuk menyimpan riwayat dari akun customer

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

    public void displayCustSessions() { // untuk menampilkan riwayat akun customer
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

    public void addSession(Session session) { // menambahkan sesi ke riwayat akun
        this.custSessions.add(session);
    }

    @Override
    public void displayInfo() { // overide method abstrak dari user
        System.out.println("Nama: " + getName());
        System.out.println("ID Customer: " + idCustomer);
    }
}