package Transcaction;
import Master.*;

class CashPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Membayar Dengan Cash Sebesar Rp. " + amount + " Diterima!");
    }
}
