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
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid. Masukkan angka.");
            return -1;
        }
    }

    public void addComputer(Computer computer) {
        if (computers.add(computer)) {
            System.out.printf("%s #%d telah ditambahkan.\n", computer.getType(), computer.getNumber());
        } else {
            System.out.printf("Komputer dengan nomor %d sudah ada.\n", computer.getNumber());
        }
    }

    public void removeComputer(int computerNumber) {
        if (computers.remove(computerNumber)) {
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
    
    // Metode ini sekarang menjadi dispatcher: memulai sesi atau menambahkan ke antrian.
    public void startSession(Customer customer, String type, int duration) {
        if (customer.getOnline()) {
            System.out.println(customer.getName() + " sudah punya sesi aktif.");
            return;
        }

        Computer freeComputer = findFreeByType(type);
        if (freeComputer == null) {
            System.out.println("Tidak ada komputer " + type + " yang tersedia. Anda ditambahkan ke dalam antrian.");
            waitingQueue.enqueue(new CustomerQueue.QueueRequest(customer, type, duration));
        } else {
            // Komputer tersedia, langsung mulai sesi.
            startSession(customer, freeComputer, duration);
        }
    }

    // Metode private untuk memulai sesi pada komputer yang *spesifik*.
    private void startSession(Customer customer, Computer computer, int duration) {
        customer.setOnline(true);
        computer.occupy(customer);

        Session newSession = new Session(customer, computer, duration);
        sessions.add(newSession);

        System.out.printf("Sesi dimulai: %s menggunakan %s #%d selama %d jam.\n", customer.getName(), computer.getType(), computer.getNumber(), duration);
    }

    public Session endSession(Customer customer) {
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
        }

        Computer freedComputer = sessionToEnd.getComputer();

        customer.addSession(sessionToEnd);
        sessionToEnd.end(); // Melepaskan komputer dan mengubah status customer.
        history.push(sessionToEnd);
        sessions.remove(sessionToEnd); // Hapus dari daftar sesi aktif.
        
        System.out.printf("Sesi untuk %s telah diakhiri.\n", customer.getName());

        // LOGIKA BARU: Periksa antrian untuk komputer yang baru saja bebas.
        processQueue(freedComputer);

        return sessionToEnd;
    }
    
    private void processQueue(Computer freedComputer) {
        System.out.println("Mengecek antrian untuk komputer " + freedComputer.getType() + " yang baru tersedia...");
        
        // Cari pelanggan berikutnya di antrian yang menunggu tipe komputer ini.
        CustomerQueue.QueueRequest nextInLine = waitingQueue.dequeueFor(freedComputer.getType());

        if (nextInLine != null) {
            System.out.println("Pelanggan ditemukan di antrian: " + nextInLine.getCustomer().getName());
            // Pelanggan ditemukan, mulai sesi baru untuk mereka.
            startSession(nextInLine.getCustomer(), freedComputer, nextInLine.getDuration());
        } else {
            System.out.println("Tidak ada pelanggan di antrian yang menunggu komputer tipe " + freedComputer.getType() + ".");
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

    public boolean historyList() {
        if (history.isEmpty()) {
            System.out.println("Riwayat kosong.");
            return false;
        }
        history.printHistory();
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

    public void removeCustomer(String idCustomer, ArrayList<Customer> customers) {
        Customer toRemove = null;
        for (Customer customer : customers) {
            if (idCustomer.equals(customer.getIdCustomer())) {
                toRemove = customer;
                break;
            }
        }
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
    
    public boolean custPay(Session sessionToPay, Scanner scanner) {
        if (sessionToPay == null || sessionToPay.getComputer() == null) {
            System.out.println("Sesi tidak valid untuk pembayaran.");
            return false;
        }

        int amount = sessionToPay.getComputer().calculatePrice(sessionToPay.getDuration());
        System.out.printf("Total Tagihan: Rp. %d\n", amount);

        Payment payment = null;
        while (payment == null) {
            System.out.println("\nPilih metode pembayaran:\n1. Cash\n2. E-Wallet");
            System.out.print("Pilih: ");
            int payChoice = checkInput(scanner.nextLine());

            switch (payChoice) {
                case 1:
                    payment = new CashPayment();
                    break;
                case 2:
                    String wallet = null;
                    do {
                        System.out.print("Pilih e-wallet (QRIS/Gopay/OVO): ");
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
                    System.out.println("Pilihan tidak valid. Coba lagi.");
                    break;
            }
        }

        payment.pay(amount);
        System.out.println("Pembayaran berhasil diproses.");
        return true;
    }
}
