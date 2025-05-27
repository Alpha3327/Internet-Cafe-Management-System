package Transaction;
import Master.*;
import java.util.*;

public class CafeManager {
    ComputerCircularList computers;
    CustomerQueue waitingQueue;
    ArrayList<Session> sessions;
    HistoryStack history;
    private Session currentSession;



    public CafeManager(int numRegComputers, int numVipComputers) {
        sessions = new ArrayList<>();
        waitingQueue = new CustomerQueue();
        computers = new ComputerCircularList();
        history = new HistoryStack();

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

    public void removeCustomer(String idCustomer, ArrayList<Customer> customers) {
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
    
    public void listCustomers(ArrayList<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("Tidak ada customer terdaftar.");
        } else {
            System.out.println("Daftar Customer");
            for (Customer c : customers) {
                System.out.println("- " + c.getName() + " (ID: " + c.getIdCustomer() + ")");
            }
        }
    }

    private Computer findFreeByType(String type) {
    for (Computer c : computers.toList()) {
        if (c.isAvailable() && c.getType().equalsIgnoreCase(type)) {
            return c;
        }
    }
    return null;
    }

    public Session startSession(Customer customer, Computer computer, int duration) {
    customer.setOnline(true);
    computer.occupy(customer);

    currentSession = new Session(customer, computer, duration);
    sessions.add(currentSession);

    System.out.printf("Sesi dimulai: %s menggunakan %s #%d selama %d jam.", customer.getName(), computer.getType(), computer.getNumber(), duration);
    return currentSession;
}

    public Session startSession(Customer customer, String type, int duration) {
    if (customer.getOnline()) {
        System.out.println(customer.getName() + " sudah punya sesi aktif.");
        return null;
    }
    Computer free = findFreeByType(type);
    if (free == null) {
        System.out.println("Tidak ada komputer " + type + " yang tersedia. Masuk antrian.");
        waitingQueue.enqueue(customer);
        return null;
    }
    // ada komputer → delegasi ke overload existing (Customer, Computer, int)
    return startSession(customer, free, duration);
}

    public Session startSession(Customer customer, int duration) {
    Computer free = computers.getNextAvailable();
    if (free == null) {
        System.out.println("All computers are currently occupied. Adding to queue.");
        waitingQueue.enqueue(customer);
        return null;
    }
    return startSession(customer, free, duration);
}

    public void endSession(Session session) {
        session.endSession();
        sessions.add(session);

        if (!waitingQueue.isEmpty()) {
            Customer next = waitingQueue.dequeue();
            System.out.println("Assigning next customer: " + next.getName());
            Computer freeComputer = computers.getNextAvailable();
            int defaultDuration = 1; // or set this to a suitable default or get from next
            startSession(next, freeComputer, defaultDuration);
        }
    }

    public void endSession(Customer customer) {
        if (currentSession == null || !currentSession.getCustomer().equals(customer)) {
            System.out.println("Tidak ada sesi aktif untuk " + customer.getName());
            return;
        }
        Session session = currentSession;
        session.endSession();
        history.push(session);
        currentSession = null;
    }

    public int queueSize() {
        return waitingQueue.size();
    }
}
