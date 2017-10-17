package net.shtyftu.ubiquode.model.persist.simple;

/**
 * @author shtyftu
 **/
public class Account {

    private static final String SALT = "r12j012f!@R@02j";

    private final String username;
    private final String password;
    private final String passwordHash;

    public Account(String username, String password, String passwordHash) {
        this.username = username;
        this.password = password;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}