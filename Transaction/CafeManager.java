package Transaction;

import Master.Computer.*;
import Master.Payment.CashPayment;
import Master.Payment.DigitalPayment;
import Master.Payment.Payment;
import Master.User.*;
import java.util.*;

public class CafeManager { // class yang mengatur segala fitur yang ada di main class
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

    public static int checkInput(String input) { // untuk memastikan input berupa angka ketika sistem membutuhkan int
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid. Masukkan angka.");
            return -1;
        }
    }

    public void addComputer(Computer computer) { // untuk menambahkan komputer ke dalam list komputer aktif
        if (computers.add(computer)) {
            System.out.printf("%s #%d telah ditambahkan.\n", computer.getType(), computer.getNumber());
        } else {
            System.out.printf("Komputer dengan nomor %d sudah ada.\n", computer.getNumber());
        }
    }

    public void removeComputer(int computerNumber) { // untuk menghapus komputer dari list komputer aktif
        if (computers.remove(computerNumber)) {
            System.out.printf("Komputer nomor #%d dihapus dari list.\n", computerNumber);
        } else {
            System.out.printf("Komputer dengan nomor #%d tidak dapat ditemukan.\n", computerNumber);
        }
    }

    public void displayComputerStatus() { // untuk menampilkan list dan status komputer 
        System.out.println("\nList Status Komputer:");
        for (Computer c : computers.toList()) {
            String status = c.isAvailable() ? "Kosong" : "Digunakan oleh " + c.getCurrentUser().getName();
            System.out.printf("%s #%d: %s%n", c.getType(), c.getNumber(), status);
        }
    }
    
    public void requestSession(Customer customer, String type, int duration) { // method yang dijalankan ketika customer melakukan penyewaan, yang akan mencari komputer dengan tipe sesuai dan sedang kosong, dan akan membuat request dan diteruskan ke method selanjutnya
        if (customer.getOnline()) {
            System.out.println(customer.getName() + " sudah punya sesi aktif.");
            return;
        }

        Computer freeComputer = findFreeByType(type);
        if (freeComputer == null) { // jika komputer tidak ada yang kosong maka akan masuk ke dalam antrian
            System.out.println("Tidak ada komputer " + type + " yang tersedia. Anda ditambahkan ke dalam antrian.");
            waitingQueue.enqueue(new CustomerQueue.QueueRequest(customer, type, duration));
        } else {
            startSession(customer, freeComputer, duration);
        }
    }

    private void startSession(Customer customer, Computer computer, int duration) { // setelah komputer ditemukan maka akan dimulai sesi penyewaan
        customer.setOnline(true);
        computer.occupy(customer);

        Session newSession = new Session(customer, computer, duration);
        sessions.add(newSession);

        System.out.printf("Sesi dimulai: %s menggunakan komputer %s #%d selama %d jam.\n", customer.getName(), computer.getType(), computer.getNumber(), duration);
    }

    public Session endSession(Customer customer) { // method yang dijalankan ketika customer memilih untuk mengakhiri sesi
        Session sessionToEnd = null;
        int sessionIndex = -1;

        // Mencari sesi berdasarkan objek customer yang sama
        int index = 0;
        for (Session session : sessions) {
            // Membandingkan referensi objek Customer secara langsung dan memastikan sesi aktif
            if (session.getCustomer() == customer && session.getIsActive()) {
                sessionToEnd = session;
                sessionIndex = index;
                System.out.println("ketemu");
                break;
            }
            index++;
        }
        if (sessionToEnd == null) { // jika tidak ada
            System.out.println("Tidak ada sesi aktif yang bisa diakhiri untuk " + customer.getName());
            return null;
        }
        Computer freedComputer = sessionToEnd.getComputer();
        customer.addSession(sessionToEnd); // menambahkan sesi ke dalam riwayat akun customer
        sessionToEnd.end(); // mengakhiri sesi
        history.push(sessionToEnd); // menambahkan sesi ke dalam riwayat keseluruhan
        
        // Hapus sesi dari daftar aktif menggunakan index yang sudah ditemukan
        if (sessionIndex != -1) {
            sessions.remove(sessionIndex);
        }
        System.out.printf("Sesi untuk %s telah diakhiri.\n", customer.getName());
        // Setelah sesi berakhir dan dihapus maka akan mengirim request untuk mencari apakah ada antrian
        processQueue(freedComputer);
        return sessionToEnd;
    }

    private void processQueue(Computer freedComputer) { // memeriksa apakah ada antrian
        CustomerQueue.QueueRequest nextInLine = waitingQueue.dequeueFor(freedComputer.getType());
        if (nextInLine != null) { // jika ada customer dengan tipe komputers yang sesuai dalam antrian
            // Memulai sesi baru untuk customer dari antrian
            startSession(nextInLine.getCustomer(), freedComputer, nextInLine.getDuration());
        }
    }

    private Computer findFreeByType(String type) { // method untuk mencari komputer yang kosong sesuai tipe yang diinginkan
        for (Computer computer : computers.toList()) {
            if (computer.isAvailable() && computer.getType().equalsIgnoreCase(type)) {
                return computer;
            }
        }
        return null;
    }

    public boolean historyList() { // untuk menampilkan seluruh riwayat
        if (history.isEmpty()) {
            System.out.println("Riwayat kosong.");
            return false;
        }
        history.printHistory();
        return true;
    }
    
    public void removeLastHistory() { // untuk menghapus riwayat terakhir yang masuk(stack)
        if (history.isEmpty()) { // jika list kosong
            System.out.println("Riwayat sudah kosong, tidak ada yang bisa dihapus.");
            return;
        }
        System.out.println("Histori berikut telah dihapus:");
        history.pop().printSessionInfo();
    }

    public boolean listCustomers(ArrayList<Customer> customers) { // untuk memperlihatkan seluruh customer yang terdaftar
        if (customers.isEmpty()) { // jika list kosong
            System.out.println("Tidak ada customer terdaftar.");
            return false;
        } else {
            System.out.println("Daftar Customer:");
            for (Customer customer : customers) {
                System.out.printf("ID: %s - Nama: %s\n", customer.getIdCustomer(), customer.getName());
            }
            return true;
        }
    }

    public void removeCustomer(String idCustomer, ArrayList<Customer> customers) { // untuk menghapus customer dari list
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
        } else { // jika tidak ditemukan
            System.out.printf("Customer dengan ID %s tidak ditemukan di dalam sistem.\n", idCustomer);
        }
    }

    public boolean custPay(Session sessionToPay, Scanner scanner) { // method yang dijalankan ketika customer memilih untuk membayar
        if (sessionToPay == null || sessionToPay.getComputer() == null) { // jika sesi ternyata tidak ada
            System.out.println("Sesi tidak valid untuk pembayaran.");
            return false;
        }

        // menghitung jumlah yang perlu dibayar oleh pengguna
        int amount = sessionToPay.getComputer().calculatePrice(sessionToPay.getDuration());
        System.out.printf("Total Tagihan: Rp. %d\n", amount);

        Payment payment = null;
        while (payment == null) { // pemilihan metode pembayaran
            System.out.println("\nPilih metode pembayaran:\n1. Cash\n2. E-Wallet");
            System.out.print("Pilih: ");
            int payChoice = checkInput(scanner.nextLine());

            switch (payChoice) {
                case 1: // jika memilih cash, maka langsung ke proses pembayaran
                    payment = new CashPayment();
                    break;
                case 2: // jika memilih pembayaran digital maka akan memilih jenisnya
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
        return true;
    }
}
