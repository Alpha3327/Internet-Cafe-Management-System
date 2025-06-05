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

        boolean exit = false;
        while (!exit) {
            User loggedInUser = null;
            System.out.println("\n==== Selamat datang di Internet Cafe! ====\n");
            System.out.println("Klik Enter untuk lanjut.");
            System.out.println("Ketik stop untuk memberhentikan program");
            String programConf = scanner.nextLine();
            if (programConf.equalsIgnoreCase("stop")) {
                System.out.println("Terima kasih sudah menggunakan program ini.");
                exit = true;
                scanner.close();
                break;
            }
            
            while (loggedInUser == null) {
                System.out.println("\nSilakan masuk untuk melanjutkan.");
                System.out.print("Masukkan ID atau nama: ");
                String inputIdOrPass = scanner.nextLine();
                System.out.print("Masukkan Password: ");
                String inputPassword = scanner.nextLine();

                for (Admin admin : admins) {
                    if ((admin.getIdAdmin().equalsIgnoreCase(inputIdOrPass) || admin.getName().equalsIgnoreCase(inputIdOrPass)) && admin.getPassword().equals(inputPassword)) {
                        System.out.printf("\nSelamat datang, %s\n", admin.getName());
                        loggedInUser = admin;
                        break;
                    }
                }

                for (Customer customer : customers) {
                    if ((customer.getIdCustomer().equalsIgnoreCase(inputIdOrPass) || customer.getName().equalsIgnoreCase(inputIdOrPass)) && customer.getPassword().equals(inputPassword)) {
                        System.out.printf("\nSelamat datang, %s\n", customer.getName());
                        loggedInUser = customer;
                        break;
                    }
                }

                if (loggedInUser == null) {
                    System.out.println("\nID atau Password salah. Coba lagi.\n");
                    String inputRegis;
                    boolean accountLoop = true;
                    do {
                        System.out.print("Buat akun baru? (y/n): ");
                        inputRegis = scanner.nextLine();

                        switch (inputRegis.toLowerCase()) {
                            case "y":
                                System.out.print("Masukkan Nama: ");
                                String nama = scanner.nextLine();
                                System.out.print("Masukkan Password: ");
                                String password = scanner.nextLine();
                                String id = String.valueOf(customers.size() + 1);

                                customers.add(new Customer(nama, password, id));
                                System.out.println("Akun berhasil terbuat:");
                                System.out.println("ID: " + id);
                                System.out.println("Nama: " + nama);
                                accountLoop = false;
                                break;

                            case "n":
                                System.out.println("Kembali ke menu login");
                                accountLoop = false;
                                break;

                            default:
                                System.out.println("Input invalid!");
                                break;
                        }
                    } while (accountLoop);
                }
            }

            if (loggedInUser instanceof Admin loggedInAdmin) {
                boolean exit1 = false;
                while (!exit1) {
                    System.out.println("\nMenu Admin:");
                    System.out.println("1. Tampilkan Status Komputer");
                    System.out.println("2. Tambah Komputer");
                    System.out.println("3. Hapus Komputer");
                    System.out.println("4. Lihat Riwayat Sewa");
                    System.out.println("5. Hapus Customer");
                    System.out.println("6. Lihat Daftar Customer");
                    System.out.println("7. Informasi Akun");
                    System.out.println("0. Logout");
                    System.out.print("Pilih: ");
                    int choice2;
                    do {
                        String tempChoice2 = scanner.nextLine();
                        choice2 = CafeManager.checkInput(tempChoice2);
                    } while (choice2 < 0);

                    boolean exit2;
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
                                int number;
                                do {
                                    String tempNumber = scanner.nextLine();
                                    number = CafeManager.checkInput(tempNumber);
                                } while (choice2 < 0);

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
                            exit2 = false;
                            do {
                                manager.listSessions();
                                System.out.println("\nMenu:");
                                System.out.println("1. Hapus Sesi terakhir");
                                System.out.println("0. Kembali");

                            } while (!exit2);
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

                        // 7. Informasi Akun
                        case 7:
                            exit2 = false;
                            do {
                                loggedInAdmin.displayInfo();
                                System.out.println("\nMenu:");
                                System.out.println("1. Ganti Nama:");
                                System.out.println("2. Ganti Password:");
                                System.out.println("0. Kembali:");
                                int input = Integer.parseInt(scanner.nextLine());
                                
                                switch (input) {
                                    case 1:
                                        System.out.print("Masukkan Nama Baru: ");
                                        String newName = scanner.nextLine();
                                        loggedInAdmin.setName(newName);
                                        System.out.println("Nama berhasil diubah.");
                                        break;

                                    case 2:
                                        System.out.print("Masukkan Password Baru: ");
                                        String newPass = scanner.nextLine();
                                        loggedInAdmin.setPassword(newPass);
                                        System.out.println("Password berhasil diubah.");
                                        break;

                                    case 0:
                                        System.out.println("Kembali ke menu sebelumnya.");
                                        exit2 = true;
                                        break;

                                    default:
                                        System.out.println("Pilihan tidak valid.");
                                        break;
                                }
                            } while (!exit2);
                            break;
                        // Keluar
                        case 0:
                            System.out.println("Log out...");
                            exit1 = true;
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
                    System.out.println("4. Informasi Akun");
                    System.out.println("0. Logout");
                    System.out.print("Pilih: ");
                    int choice2 = Integer.parseInt(scanner.nextLine());

                    switch (choice2) {
                        // 1. Sewa atau akhiri sesi
                        case 1:
                            if (!loggedInCustomer.getOnline()) {
                                String type;
                                do {
                                    System.out.print("Pilih tipe komputer (regular/vip): ");
                                    type = scanner.nextLine();
                                } while (!(type.equalsIgnoreCase("vip") || type.equalsIgnoreCase("regular")));

                                int duration = 0;
                                boolean validInput = false;
                                while (!validInput) {
                                    try {
                                        System.out.print("Masukkan durasi (jam): ");
                                        duration = Integer.parseInt(scanner.nextLine());
                                        validInput = true;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Input tidak valid. Masukkan angka.");
                                    }
                                }

                                manager.startSession(loggedInCustomer, type, duration);
                            } else {
                                Session toBePaySession = manager.endSession(loggedInCustomer);
                                int amount = toBePaySession.getComputer().calculatePrice(toBePaySession.getDuration());
                                System.out.printf("Total: Rp. %d\n", amount);

                                System.out.println("Pilih metode pembayaran:");
                                System.out.println("1. Cash");
                                System.out.println("2. E-Wallet");
                                System.out.print("Pilih: ");
                                int payChoice;
                                do {
                                    String tempPayChoice = scanner.nextLine();
                                    payChoice = CafeManager.checkInput(tempPayChoice);
                                } while (choice2 < 0);

                                Payment payment;
                                do {
                                    switch (payChoice) {
                                    case 1:
                                        payment = new CashPayment();
                                        break;
                                    case 2:
                                        String wallet = null;
                                        do {
                                            System.out.println("Pilih e-wallet (QRIS/Gopay/OVO): ");
                                            String input = scanner.nextLine();
                                            if (input.equalsIgnoreCase("QRIS")||input.equalsIgnoreCase("Gopay")||input.equalsIgnoreCase("OVO")) {
                                                wallet = input.toUpperCase();
                                            } else {
                                                System.out.println("e-wallet tidak valid, coba lagi.");
                                            }
                                        } while (wallet == null);
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
                            loggedInCustomer.displayCustSessions();
                            break;

                        // 4. Ganti password
                        case 4:
                            boolean exit2 = false;
                            do {
                                loggedInCustomer.displayInfo();
                                System.out.println("\nMenu:");
                                System.out.println("1. Ganti Nama:");
                                System.out.println("2. Ganti Password:");
                                System.out.println("0. Kembali:");
                                int input = Integer.parseInt(scanner.nextLine());
                                
                                switch (input) {
                                    case 1:
                                        System.out.print("Masukkan Nama Baru: ");
                                        String newName = scanner.nextLine();
                                        loggedInCustomer.setName(newName);
                                        System.out.println("Nama berhasil diubah.");
                                        break;

                                    case 2:
                                        System.out.print("Masukkan Password Baru: ");
                                        String newPass = scanner.nextLine();
                                        loggedInCustomer.setPassword(newPass);
                                        System.out.println("Password berhasil diubah.");
                                        break;

                                    case 0:
                                        System.out.println("Kembali ke menu sebelumnya.");
                                        exit2 = true;
                                        break;

                                    default:
                                        System.out.println("Pilihan tidak valid.");
                                        break;
                                }
                            } while (!exit2);
                            break;

                        case 0:
                            System.out.println("Log out...");
                            exit1 = true;
                            break;

                        default:
                            System.out.println("Pilihan tidak valid.");
                            break;
                    }
                }
            }
        }
    }
}