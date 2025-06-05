package Transaction;
import Master.Computer.*;
import Master.Payment.CashPayment;
import Master.Payment.DigitalPayment;
import Master.Payment.Payment;
import Master.User.*;
import java.util.*;

public class CafeManager {
    ComputerList computers;
    CustomerQueue waitingQueue;
    ArrayList<Session> sessions;
    HistoryStack history;
    private Session currentSession;
    
    public CafeManager(int numRegComputers, int numVipComputers) {
        sessions = new ArrayList<>();
        waitingQueue = new CustomerQueue();
        computers = new ComputerList();
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

    public static int checkInput(String input) {
        try {
            int number = Integer.parseInt(input);
            return number;
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid. Masukkan angka.");
            return -1;
        }
    }

    public void addComputer(Computer computer) {
        boolean status = computers.add(computer);
        if (status) {
            System.out.printf("%s #%d telah ditambahkan.\n", computer.getType(), computer.getNumber());
        } else {
            System.out.printf("Komputer dengan nomor %d sudah ada.\n", computer.getNumber());
        }
    }


    public void removeComputer(int computerNumber) {
        boolean removed = computers.remove(computerNumber);
        if (removed) {
            System.out.printf("Komputer nomor #%d dihapus dari list.\n", computerNumber);
        } else {
            System.out.printf("Komputer dengan nomor #%d tidak dapat ditemukan.\n", computerNumber);
        }
    }

    public void displayComputerStatus() {
        System.out.println("\nList Status Komputer:");
        for (Computer c : computers.toList()) {
            String status = c.isAvailable() ? "Kosong" : "Digunakan oleh " + c.getCurrentUser().getName();
            System.out.printf("%s #%d: %s%n", c.getType(), c.getNumber(), status);
        }
    }

    public boolean historyList() {
        if (sessions.isEmpty()) {
            System.out.println("Riwayat kosong.");
            return false;
        } else {
            history.printHistory();
            return true;
        }
    }

    public void removeCustomer(String idCustomer, ArrayList<Customer> customers) {
        // Cari customer berdasarkan ID
        Customer toRemove = null;
        for (Customer customer : customers) {
            if (idCustomer.equals(customer.getIdCustomer())) {
                toRemove = customer;
                break;
            }
        }
        // Jika ditemukan, hapus dan tampilkan pesan
        if (toRemove != null) {
            customers.remove(toRemove);
            System.out.printf("Customer %s dengan ID: %s telah dihapus dari sistem.\n", toRemove.getName(), idCustomer);
        } else {
            System.out.printf("Customer dengan ID %s tidak ditemukan di dalam sistem.\n", idCustomer);
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
        
        customer.setOnline(true);
        free.occupy(customer);

        currentSession = new Session(customer, free, duration);
        sessions.add(currentSession);

        System.out.printf("Sesi dimulai: %s menggunakan %s #%d selama %d jam.\n", customer.getName(), free.getType(), free.getNumber(), duration);
        return currentSession;
    }

    public Session endSession(Customer customer) {
        // Find the active session for this customer
        Session sessionToEnd = null;
        for (Session session : sessions) {
            if (session.getCustomer().equals(customer) && session.getIsActive()) {
                sessionToEnd = session;
                break;
            }
        }

        if (sessionToEnd == null) {
            System.out.println("Tidak ada sesi aktif untuk " + customer.getName());
            return null;
        } else {
            customer.addSession(sessionToEnd); // Add to customer's personal history
            sessionToEnd.end(); // This should mark session inactive, release computer, set customer offline
            history.push(sessionToEnd); // Add to global history
            sessions.remove(sessionToEnd); // Remove from active sessions list
            if (currentSession != null && currentSession.equals(sessionToEnd)) { // Also clear currentSession if it was this one
                currentSession = null;
            }
            System.out.printf("Sesi untuk %s telah diakhiri.\n", customer.getName());
            return sessionToEnd;
        }
    }

    public boolean custPay(Session sessionToPay, Scanner scanner) {
        if (sessionToPay == null) {
            System.out.println("Error: Tidak ada sesi yang valid untuk pembayaran.");
            return false;
        }
        if (sessionToPay.getComputer() == null) {
            System.out.println("Sesi tidak memiliki komputer.");
            return false;
        }

        int amount = sessionToPay.getComputer().calculatePrice(sessionToPay.getDuration());
        System.out.printf("Total Tagihan: Rp. %d\n", amount);

        Payment payment = null;
        // Loop selama payment yang dipilih tidak valid
        while (payment == null) {
            System.out.println("\nPilih metode pembayaran:");
            System.out.println("1. Cash");
            System.out.println("2. E-Wallet");
            System.out.print("Pilih: ");
            int payChoice;
            do {
                String tempPayChoice = scanner.nextLine();
                payChoice = checkInput(tempPayChoice);
            } while (payChoice < 1 || payChoice > 2);

            switch (payChoice) {
                case 1:
                    payment = new CashPayment();
                    break;
                case 2:
                    String wallet = null;
                    do {
                        System.out.println("Pilih e-wallet (QRIS/Gopay/OVO): ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("QRIS") || input.equalsIgnoreCase("Gopay") || input.equalsIgnoreCase("OVO")) {
                            wallet = input.toUpperCase();
                        } else {
                            System.out.println("e-wallet tidak valid, coba lagi.");
                        }
                    } while (wallet == null);
                    payment = new DigitalPayment(wallet);
                    break;
                default:
                    System.out.println("Input invalid. Coba lagi.");
                    break;
            }
        }

        payment.pay(amount);
        System.out.println("Pembayaran berhasil diproses.");
        return true;
    }

    public void removeLastHistory() {
        if (history.isEmpty()) {
            System.out.println("Riwayat sudah kosong, tidak ada yang bisa dihapus.");
            return;
        }
        System.out.println("Histori berikut telah dihapus:");
        history.pop().printSessionInfo();
    }
}