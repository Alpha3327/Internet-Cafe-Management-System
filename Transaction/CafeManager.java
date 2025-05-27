package Transaction;
import Master.*;
import java.util.*;

public class CafeManager {
    private ComputerCircularList computers;
    private List<Customer> customers;
    private CustomerQueue waitingQueue;
    private List<Session> sessions;

    public CafeManager(int numRegComputers, int numVipComputers) {
        customers    = new ArrayList<>();
        sessions     = new ArrayList<>();
        waitingQueue = new CustomerQueue();
        computers    = new ComputerCircularList();
        int numComputers = 0;
        for (int i = 1; i <= numRegComputers; i++) {
            computers.add(new RegularComputer(i));
            numComputers++;
        }
        for (int i = numComputers + 1; i <= numComputers + numVipComputers; i++) {
            computers.add(new VipComputer(i));
        }
    }

    public void addComputer(Computer computer) {
        computers.add(computer);
        System.out.println(computer.getType() + " #" + computer.getNumber() + " added.");
    }

    public void displayComputerStatus() {
        System.out.println("=== Computer Status ===");
        for (Computer c : computers.toList()) {
            String status = c.isAvailable() ? "Available" : "Occupied by " + c.getCurrentUser().getName();
            System.out.printf("%s #%d: %s%n", c.getType(), c.getNumber(), status);
        }
    }

    public void removeComputer(int compNumber) {
        boolean removed = computers.remove(compNumber);
        if (removed) {
            System.out.println("Computer #" + compNumber + " removed.");
        } else {
            System.out.println("Computer with number " + compNumber + " not found.");
        }
    }

    public void listSessions() {
        if (sessions.isEmpty()) {
            System.out.println("No sessions recorded.");
        } else {
            System.out.println("=== Session History ===");
            for (Session s : sessions) {
                s.printSessionInfo();
            }
        }
    }

    public void removeCustomer(String idCustomer) {
        // Cari customer berdasarkan ID
        Customer toRemove = null;
        for (Customer c : customers) {
            if (idCustomer.equals(c.getIdCustomer())) {
                toRemove = c;
                break;
            }
        }
        // Jika ditemukan, hapus dan tampilkan pesan
        if (toRemove != null) {
            customers.remove(toRemove);
            System.out.println("Customer " + toRemove.getName() + " (ID: " + idCustomer + ") telah dihapus dari sistem.");
        } else {
            System.out.println("Customer dengan ID " + idCustomer + " tidak ditemukan di sistem.");
        }
    }
    
    public void listCustomers() {
        if (customers.isEmpty()) {
            System.out.println("Tidak ada customer terdaftar.");
        } else {
            System.out.println("Daftar Customer");
            for (Customer c : customers) {
                System.out.println("- " + c.getName() + " (ID: " + c.getIdCustomer() + ")");
            }
        }
    }

    public Session startSession(Customer customer, Computer computer, int duration) {
        Computer free = computers.getNextAvailable();
        if (free == null) {
            System.out.println("All computers are currently occupied. Adding to queue.");
            waitingQueue.enqueue(customer);
            return null;
        }
        free.occupy(customer);
        Session session = new Session(customer, computer, duration);
        System.out.println("Session started for " + customer.getName() + " on " + free.getType() + " #" + free.getNumber());
        return session;
    }

    public void endSession(Session session) {
        session.endSession();           // me-release komputer & print info
        sessions.add(session);          // simpan riwayat

        if (!waitingQueue.isEmpty()) {
            Customer next = waitingQueue.dequeue();
            System.out.println("Assigning next customer: " + next.getName());
            Computer freeComputer = computers.getNextAvailable();
            int defaultDuration = 1; // or set this to a suitable default or get from next
            startSession(next, freeComputer, defaultDuration);
        }
    }

    public int queueSize() {
        return waitingQueue.size();
    }
}
