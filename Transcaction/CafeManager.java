package Transcaction;
import java.util.*;
import Master.*;

public class CafeManager {
    private ComputerCircularList computers;
    private CustomerQueue waitingQueue;
    private List<Session> sessions;

    public CafeManager(List<Computer> initialComputers) {
        computers = new ComputerCircularList();
        for (Computer c : initialComputers) {
            computers.add(c);
        }
        waitingQueue = new CustomerQueue();
        sessions = new ArrayList<>();
    }

    public void displayComputerStatus() {
        System.out.println("=== Computer Status ===");
        for (Computer c : computers.toList()) {
            String status = (!c.isAvailable())
                ? "Occupied by " + c.getCurrentUser().getName() : "Available";
            System.out.printf("PC %d: %s%n", c.getNumber(), status);
        }
    }

    public void addComputer(Computer comp) {
        computers.add(comp);
        System.out.println("Computer " + comp.getNumber() + " added.");
    }


    public void removeComputer(int compId) {
        boolean removed = computers.remove(compId);
        if (removed) {
            System.out.println("Computer " + compId + " removed.");
        } else {
            System.out.println("Computer with ID " + compId + " not found.");
        }
    }

    public void listSessions() {
        if (sessions.isEmpty()) {
            System.out.println("No sessions recorded.");
        } else {
            System.out.println("=== Session History ===");
            for (Session s : sessions) {
                System.out.println(s);
            }
        }
    }

    public Session startSession(Customer customer, int duration) {
        Computer free = computers.getNextAvailable();
        if (free == null) {
            System.out.println("All computers are currently occupied. Adding to queue.");
            waitingQueue.enqueue(customer);
            return null;
        }
        free.occupy(customer);
        Session session = new Session(customer, free, duration);
        System.out.println("Session started for " + customer.getName() +
                           " on PC " + free.getNumber());
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
            startSession(next, 1);
        }
    }

    /**
     * Dapatkan jumlah customer yang sedang menunggu.
     */
    public int queueSize() {
        return waitingQueue.size();
    }
}
