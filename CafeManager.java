import java.util.*;

class CafeManager {
    private List<Computer> computers;

    public CafeManager(int numberOfComputers) {
        computers = new ArrayList<>();
        for (int i = 1; i <= numberOfComputers; i++) {
            computers.add(new Computer(i));
        }
    }

    public void startSessionInteractive(Scanner scanner, Customer customer) {
        int compNumber;
        Computer selectedComputer = null;

        // Pilih komputer
        while (selectedComputer == null) {
            System.out.print("Pilih nomor komputer (1 - " + computers.size() + "): ");
            compNumber = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (compNumber < 1 || compNumber > computers.size()) {
                System.out.println("Nomor komputer tidak valid.");
                continue;
            }

            Computer temp = computers.get(compNumber - 1);
            if (temp.isAvailable()) {
                selectedComputer = temp;
                selectedComputer.setCurrentUser(customer); // set user setelah valid
            } else {
                System.out.println("Komputer sedang dipakai. Silakan pilih komputer lain.");
            }
        }

        // Durasi awal
        System.out.print("Berapa Lama Kamu Ingin Bermain (jam)? ");
        double hours = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        int duration = (int) hours;

        Session session = new Session(customer, selectedComputer, duration);
        session.printSessionInfo();

        // Loop konfirmasi akhir sesi
        boolean sesiBerakhir = false;
        while (!sesiBerakhir) {
            System.out.print("Ingin Mengakhiri Sesi? (y/n): ");
            String konfirmasi = scanner.nextLine().trim().toLowerCase();

            switch (konfirmasi) {
                case "y":
                    System.out.print("Masukkan Total Waktu Penggunaan (jam): ");
                    double waktuAkhir = scanner.nextDouble();
                    scanner.nextLine(); // consume newline

                    session.setDuration((int) waktuAkhir);
                    double biayaAkhir = calculatePrice(waktuAkhir);

                    Payment payment = pilihMetodePembayaran(scanner);
                    payment.pay(biayaAkhir);

                    System.out.println("\nTerima Kasih Telah Menggunakan Komputer Kami!\n");
                    sesiBerakhir = true;
                    break;

                case "n":
                    System.out.println("Sesi Masih Akan Berlanjut.\n");
                    break;

                default:
                    System.out.println("Pilihan tidak valid. Masukkan 'y' atau 'n'.\n");
                    break;
            }
        }

        // Reset komputer
        selectedComputer.setCurrentUser(null);
    }

    private double calculatePrice(double hours) {
        return hours * 5000;
    }

    private Payment pilihMetodePembayaran(Scanner scanner) {
        System.out.println("\nPilih Metode Pembayaran: \n1. Cash \n2. E-Wallet");
        System.out.print("Pilih: ");
        int metode = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (metode == 1) {
            return new CashPayment();
        } else if (metode == 2) {
            System.out.print("Nama Platform E-Wallet: ");
            String wallet = scanner.nextLine();
            return new DigitalPayment(wallet);
        } else {
            System.out.println("Platform tidak ditemukan. Menggunakan Cash sebagai default.");
            return new CashPayment();
        }
    }

    public void displayComputerStatus() {
        System.out.println("\n--- Status Komputer ---");
        for (Computer computer : computers) {
            String status = computer.isAvailable()
                ? "Tersedia"
                : "Dipakai oleh " + computer.getCurrentUser().getName();
            System.out.printf("Komputer %d: %s\n", computer.getNumber(), status);
        }
        System.out.println("------------------------\n");
    }
}
