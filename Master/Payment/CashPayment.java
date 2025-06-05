package Master.Payment;

public class CashPayment implements Payment {
    @Override
    public void pay(int amount) {
        System.out.printf("Pembayaran dengan cash sebesar Rp.%d diterima!\n", amount);
    }
}