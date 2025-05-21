class DigitalPayment implements Payment {
    private String walletName;

    public DigitalPayment(String walletName) {
        this.walletName = walletName;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Membayar Menggunakan " + walletName + " Sebesar Rp. " + amount + " Berhasil!");
    }
}
