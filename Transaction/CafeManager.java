package Transaction;
import java.util.*;
import Master.*;

public class CafeManager {
    private ComputerCircularList computers;
    private CustomerQueue waitingQueue;
    private List<Session> sessions;
    private double ratePerHour;

    public CafeManager(int numComputers) {
        computers = new ComputerCircularList();
        for (int i = 1; i <= numComputers; i++) {
            computers.add(new RegularComputer(i));
        }
        waitingQueue = new CustomerQueue();
        sessions = new ArrayList<>();
    }

    public void addComputer(Computer comp) {
        computers.add(comp);
        System.out.println(comp.getType() + " #" + comp.getNumber() + " added.");
    }

    public void displayComputerStatus() {
        System.out.println("=== Computer Status ===");
        for (Computer c : computers.toList()) {
            String status = c.isAvailable()
                ? "Available"
                : "Occupied by " + c.getCurrentUser().getName();
            System.out.printf("%s #%d: %s%n",
                c.getType(), c.getNumber(), status);
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

    public Session startSession(Customer customer, Computer computer, int duration) {
        Computer free = computers.getNextAvailable();
        if (free == null) {
            System.out.println("All computers are currently occupied. Adding to queue.");
            waitingQueue.enqueue(customer);
            return null;
        }
        free.occupy(customer);
        Session session = new Session(customer, computer, duration);
        System.out.println("Session started for " + customer.getName() +
                           " on " + free.getType() + " #" + free.getNumber());
        return session;
    }

    /**
     * Akhiri sesi, release komputer, catat riwayat, lalu assign ke customer berikutnya.
     */
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

    /**
     * Dapatkan jumlah customer yang sedang menunggu.
     */
    public int queueSize() {
        return waitingQueue.size();
    }
}
