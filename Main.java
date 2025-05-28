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
        customers.add(new Customer("abc", "a", "a"));
        
        while (true) {
            System.out.println("\nMasuk sebagai:");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            int choice1 = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            boolean exit1 = false;

            switch (choice1) {
                case 1: // MASUK SEBAGAI ADMIN
                    Admin loggedInAdmin = null;
                    while (loggedInAdmin == null) {
                        System.out.println("Log in:");
                        System.out.print("ID: ");
                        String inputId = scanner.nextLine();
                        System.out.print("Password: ");
                        String inputPassword = scanner.nextLine();

                        for (Admin admin : admins) {
                            if (admin.getAdminId().equalsIgnoreCase(inputId) && admin.getPassword().equals(inputPassword)) {
                                loggedInAdmin = admin;
                                break;
                            }
                        }

                        if (loggedInAdmin == null) {
                            System.out.println("ID atau Password salah. Coba lagi.\n");
                        }
                    }

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
                                break;

                            default:
                                System.out.println("Pilihan tidak valid.");
                                break;
                        }
                    }
                    break;

                case 2: // MASUK SEBAGAI CUSTOMER
                    String inputRegis;
                    do {
                        System.out.print("Sudah Punya Akun? (y/n): ");
                        inputRegis = scanner.nextLine();
                        if (inputRegis.equalsIgnoreCase("n")) {
                            System.out.print("Masukkan Nama: ");
                            String nama = scanner.nextLine();
                            System.out.print("Masukkan Password: ");
                            String password = scanner.nextLine();
                            String id = String.valueOf(customers.size() + 1);
                            customers.add(new Customer(nama, password, id));
                            System.out.println("Akun berhasil dibuat. Silakan login.\n");
                        }
                    } while (!(inputRegis.equalsIgnoreCase("y")||inputRegis.equalsIgnoreCase("n")));

                    Customer loggedInCustomer = null;
                    while (loggedInCustomer == null) {
                        System.out.println("Log in:");
                        System.out.print("ID: ");
                        String inputId = scanner.nextLine();
                        System.out.print("Password: ");
                        String inputPassword = scanner.nextLine();

                        for (Customer customer : customers) {
                            if (customer.getIdCustomer().equalsIgnoreCase(inputId) && customer.getPassword().equals(inputPassword)) {
                                loggedInCustomer = customer;
                                break;
                            }
                        }

                        if (loggedInCustomer == null) {
                            System.out.println("Nama atau Password salah. Coba lagi.\n");
                        }
                    }

                    System.out.printf("\nMasuk sebagai %s\n", loggedInCustomer.getName());

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
                                    manager.endSession(loggedInCustomer);
                                }
                                break;

                            case 2:
                                manager.displayComputerStatus();
                                break;

                            case 3:
                                manager.listSessions();
                                break;

                            case 4:
                                System.out.print("Masukkan Password Baru: ");
                                String newPass = scanner.nextLine();
                                loggedInCustomer.setPassword(newPass);
                                System.out.println("Password berhasil diubah.");
                                break;

                            case 0:
                                exit1 = true;
                                break;

                            default:
                                System.out.println("Pilihan tidak valid.");
                                break;
                        }
                    }
                    break;

                case 0:
                    System.out.println("Keluar dari program...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Input tidak valid.\n");
            }
        }
    }
}
