package Master.User;

public abstract class User { // superclass user yang menerapkan abstrak
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }
    
        public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract void displayInfo(); // metode yang akan diambil oleh customer dan admin dan disesuaikan masing-masing untuk menampilkan info
}