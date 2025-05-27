import java.util.*;
import Master.*;
import Transaction.CafeManager;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CafeManager manager = new CafeManager(5);

        ArrayList<Admin> admins = new ArrayList<Admin>();
        admins.add(new Admin("Aaron Laurens Misael Wantania", "admin123", "00000133269"));
        admins.add(new Admin("Rafly Ahmad Julyana", "admin123", "00000127818"));
        admins.add(new Admin("Wilsen Gomes", "admin123", "00000127804"));
        admins.add(new Admin("Dani Arifianto", "admin123", "00000133505"));
        admins.add(new Admin("Rachel Nayla Putri", "admin123", "00000133433"));
        admins.add(new Admin("Haris Alfarisi", "admin123", "00000128655"));
        admins.add(new Admin("admin", "a", "1"));

        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(new Customer("abc", "123", "1"));

        boolean exit = false;
        while (!exit) {
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
            // MASUK SEBAGAI ADMIN
                case 1: 
                    Admin loggedInAdmin = null;
                    while (loggedInAdmin == null) {
                        System.out.println("Log in:");
                        System.out.print("ID: ");
                        String inputId = scanner.nextLine();
                        System.out.print("Password: ");
                        String inputPassword = scanner.nextLine();

                        for (Admin admin : admins) {
                            if (admin.getAdminId().equalsIgnoreCase(inputId)
                                    && admin.getPassword().equals(inputPassword)) {
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
                        System.out.println("5. Tambah Customer");
                        System.out.println("6. Hapus Customer");
                        System.out.println("7. Lihat Daftar Customer");
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
                                System.out.print("Masukkan Tipe Komputer: ");
                                String type = scanner.nextLine();
                                System.out.print("Masukkan Nomor Komputer: ");
                                int number = scanner.nextInt();
                                Computer computer = null;

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
                            
                            // 5. Tambah Customer
                            case 5: 
                                System.out.print("Masukkan Nama Customer: ");
                                String name = scanner.nextLine();
                                System.out.print("Masukkan Password Customer: ");
                                String password = scanner.nextLine();
                                String id = String.valueOf(customers.size() + 1);
                                customers.add(new Customer(name, password, id));
                                System.out.println("Customer berhasil ditambahkan.");
                                break;

                            // 6. Hapus Customer
                            case 6:
                                System.out.print("Masukkan ID Customer yang ingin dihapus: ");
                                String deleteCust = scanner.nextLine();
                                manager.removeCustomer(deleteCust);
                                break;

                            // 7. Lihat Daftar Customer
                            case 7:
                                System.out.println("Daftar Customer:");
                                manager.listCustomers();
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

                case 2: 
                    System.out.print("Sudah Punya Akun? (y/n): ");
                    String inputRegis = scanner.nextLine();

                    if (inputRegis.equalsIgnoreCase("y")) {
                        System.out.print("Masukkan Nama: ");
                        String nama = scanner.nextLine();
                        System.out.print("Masukkan Password: ");
                        String password = scanner.nextLine();
                        String id = String.valueOf(customers.size() + 1);
                        customers.add(new Customer(nama, password, id));
                        System.out.println("Akun berhasil dibuat. Silakan login.\n");
                    }

                    Customer loggedInCustomer = null;
                    while (loggedInCustomer == null) {
                        System.out.println("Log in:");
                        System.out.print("Nama: "); 
                        String inputName = scanner.nextLine();
                        System.out.print("Password: ");
                        String inputPassword = scanner.nextLine();

                        for (Customer customer : customers) {
                            if (customer.getName().equalsIgnoreCase(inputName)
                                    && customer.getPassword().equals(inputPassword)) {
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
                        System.out.println("1. Sewa Komputer");
                        System.out.println("2. Tampilkan Status Komputer");
                        System.out.println("3. Lihat Riwayat Sewa");
                        System.out.println("4. Ganti Password");
                        System.out.println("0. Logout");
                        System.out.print("Pilih: ");
                        int choice2 = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice2) {
                            // 1. Sewa Komputer
                            case 1:
                                

                                manager.startSession(loggedInCustomer, );
                                break;

                            // 2. Tampilkan Status Komputer
                            case 2:
                                manager.displayComputerStatus();
                                break;

                            // 3. Lihat Riwayat Sewa
                            case 3:
                                manager.listSessions();
                                break;

                            // 4. Ganti Password
                            case 4:

                                break;
                            
                            case 0:
                                exit1 = false;
                                break;
                            default:
                                System.out.println("Pilihan tidak valid.");
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
