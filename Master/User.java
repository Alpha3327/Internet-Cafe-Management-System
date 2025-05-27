package Master;
import java.util.*;

public abstract class User {
    private String name;
    private String password;

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

     public abstract boolean login();
}