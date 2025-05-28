package Master;

public class CashPayment implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("Membayar Dengan Cash Sebesar Rp. " + amount + " Diterima!");
    }
}