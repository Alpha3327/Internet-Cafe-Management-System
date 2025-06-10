package Master.Payment;

public class DigitalPayment implements Payment { // childclass dari payment
    private String walletName;

    public DigitalPayment(String walletName) {
        this.walletName = walletName;
    }

    public String getWalletName() {
        return walletName;
    }

    @Override
    public void pay(int amount) { // overide dari class payment
        System.out.printf("Pembayaran dengan %s sebesar Rp.%d diterima!\n", walletName, amount);
    }
}