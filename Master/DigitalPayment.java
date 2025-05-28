package Master;

public class DigitalPayment implements Payment {
    private String walletName;

    public DigitalPayment(String walletName) {
        this.walletName = walletName;
    }

    public String getWalletName() {
        return walletName;
    }

    @Override
    public void pay(int amount) {
        System.out.printf("Membayar menggunakan %s sebesar Rp. %d berhasil.\n", walletName, amount);
    }
}