import Master.*;
import Transaction.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CafeManager manager = new CafeManager(5,2);

        ArrayList<Admin> admins = new ArrayList<>();
        admins.add(new Admin("Aaron Laurens Misael Wantania", "admin123", "00000133269"));
        admins.add(new Admin("Rafly Ahmad Julyana", "admin123", "00000127818"));
        admins.add(new Admin("Wilsen Gomes", "admin123", "00000127804"));
        admins.add(new Admin("Dani Arifianto", "admin123", "00000133505"));
        admins.add(new Admin("Rachel Nayla Putri", "admin123", "00000133433"));
        admins.add(new Admin("Haris Alfarisi", "admin123", "00000128655"));
        admins.add(new Admin("admin", "a", "a"));

        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("abc", "1", "1"));

        boolean loggedIn = false;
        User loggedInUser = null;
        System.out.println("Selamat datang di Cafe Komputer!");
        
        while (!loggedIn) {
            System.out.println("Silakan masuk untuk melanjutkan.");
            System.out.print("Masukkan ID: ");
            String inputId = scanner.nextLine();
            System.out.print("Masukkan Password: ");
            String inputPassword = scanner.nextLine();

            for (Admin admin : admins) {
                if (admin.getIdAdmin().equalsIgnoreCase(inputId) && admin.getPassword().equals(inputPassword)) {
                    loggedIn = true;
                    System.out.printf("Selamat datang, %s\n", admin.getName());
                    loggedInUser = admin;
                    break;
                }
            }

            for (Customer customer : customers) {
                if (customer.getIdCustomer().equalsIgnoreCase(inputId) && customer.getPassword().equals(inputPassword)) {
                    loggedIn = true;
                    System.out.printf("Selamat datang, %s\n", customer.getName());
                    loggedInUser = customer;
                    break;
                }
            }

            if (!loggedIn) {
                System.out.println("ID atau Password salah. Coba lagi.\n");
            }
        }

        if (loggedInUser instanceof Admin loggedInAdmin) {
            boolean exit1 = false;
            System.out.printf("\nMasuk sebagai %s\n", loggedInAdmin.getName());
            while (!exit1) {
                System.out.println("\nMenu Admin:");
                System.out.println("1. Tampilkan Status Komputer");
                System.out.println("2. Tambah Komputer");
                System.out.println("3. Hapus Komputer");
                System.out.println("4. Lihat Riwayat Sewa");
                System.out.println("5. Hapus Customer");
                System.out.println("6. Lihat Daftar Customer");
                System.out.println("0. Logout");
                System.out.print("Pilih: ");
                int choice2 = scanner.nextInt();
                scanner.nextLine();

                switch (choice2) {
                    // 1. Tampilkan Status Komputer
                    case 1:
                        manager.displayComputerStatus();
                        break;

                    // 2. Tambah Komputer
                    case 2:
                        String type;
                        Computer computer = null;
                        do {
                            System.out.print("Masukkan Tipe Komputer: ");
                            type = scanner.nextLine();
                            System.out.print("Masukkan Nomor Komputer: ");
                            int number = scanner.nextInt();
                            scanner.nextLine(); // consume newline

                            switch (type.toLowerCase()) {
                                case "regular":
                                    computer = new RegularComputer(number);
                                    break;
                            
                                case "vip":
                                    computer = new VipComputer(number);
                                    break;
                            
                                default:
                                    System.out.println("Tipe komputer tidak valid. Gunakan 'regular' atau 'vip'.");
                                    break;
                            }
                        } while (computer == null);
                        
                        manager.addComputer(computer);
                        break;

                    // 3. Hapus Komputer
                    case 3:
                        System.out.print("Masukkan Nomor Komputer yang ingin dihapus: ");
                        int deleteComputer = scanner.nextInt();
                        manager.removeComputer(deleteComputer);
                        break;
                    
                    // 4. Lihat Riwayat Sewa
                    case 4:
                        manager.listSessions();
                        break;
                    
                    // 5. Hapus Customer
                    case 5:
                        System.out.print("Masukkan ID Customer yang ingin dihapus: ");
                        String deleteCust = scanner.nextLine();
                        manager.removeCustomer(deleteCust, customers);
                        break;

                    // 6. Lihat Daftar Customer
                    case 6:
                        System.out.println("Daftar Customer:");
                        manager.listCustomers(customers);
                        break;

                    // Keluar
                    case 0:
                        System.out.println("Log out...");
                        exit1 = true;
                        scanner.close();
                        break;

                    default:
                        System.out.println("Pilihan tidak valid.");
                        break;
                }
            }
        } else if (loggedInUser instanceof Customer loggedInCustomer) {
            boolean exit1 = false;
            while (!exit1) {
                System.out.println("\nMenu Customer:");
                if (!loggedInCustomer.getOnline()) {
                    System.out.println("1. Sewa Komputer");
                } else {
                    System.out.println("1. Akhiri Sesi");
                }
                System.out.println("2. Tampilkan Status Komputer");
                System.out.println("3. Lihat Riwayat Sewa");
                System.out.println("4. Ganti Password");
                System.out.println("0. Logout");
                System.out.print("Pilih: ");
                int choice2 = scanner.nextInt();
                scanner.nextLine();

                switch (choice2) {
                    // 1. Sewa atau akhiri sesi
                    case 1:
                        if (!loggedInCustomer.getOnline()) {
                            String type;
                            do {
                                System.out.print("Pilih tipe komputer (regular/vip): ");
                                type = scanner.nextLine();
                            } while (!(type.equalsIgnoreCase("vip") || type.equalsIgnoreCase("regular")));

                            System.out.print("Masukkan durasi (jam): ");
                            int duration = scanner.nextInt();
                            scanner.nextLine();

                            manager.startSession(loggedInCustomer, type, duration);
                        } else {
                            Session toBePaySession = manager.endSession(loggedInCustomer);
                            int amount = toBePaySession.getComputer().calculatePrice(toBePaySession.getDuration());
                            System.out.printf("Total: Rp. %d\n", amount);

                            System.out.println("Pilih metode pembayaran:");
                            System.out.println("1. Cash");
                            System.out.println("2. E-Wallet");
                            System.out.print("Pilih: ");
                            int payChoice = scanner.nextInt();
                            scanner.nextLine();

                            Payment payment;
                            do {
                                switch (payChoice) {
                                case 1:
                                    payment = new CashPayment();
                                    break;
                                case 2:
                                    System.out.println("Pilih e-wallet (QRIS/Gopay/OVO): ");
                                    String wallet = scanner.nextLine();
                                    payment = new DigitalPayment(wallet);
                                    break;
                                default:
                                    System.out.println("Input tidak valid.");
                                    return;
                                }
                            } while (payment == null);
                            payment.pay(amount);
                        }
                        break;

                    // 2 Tampilkan status komputer
                    case 2:
                        manager.displayComputerStatus();
                        break;

                    // 3. Lihat riwayat sesi
                    case 3:
                        manager.listSessions();
                        break;

                    // 4. Ganti password
                    case 4:
                        System.out.print("Masukkan Password Baru: ");
                        String newPass = scanner.nextLine();
                        loggedInCustomer.setPassword(newPass);
                        System.out.println("Password berhasil diubah.");
                        break;

                    case 0:
                        System.out.println("Log out...");
                        exit1 = true;
                        scanner.close();
                        break;

                    default:
                        System.out.println("Pilihan tidak valid.");
                        break;
                }
            }
        }
    }
}