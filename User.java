public abstract class User {
    protected String name;
    protected String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public abstract void displayInfo();

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}