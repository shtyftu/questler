package net.shtyftu.ubiquode.model.persist.simple;

/**
 * @author shtyftu
 **/
public class Account implements PersistEntity<String> {

//    private static final String SALT = "r12j012f!@R@02j";

    private final String username;
    private final String password;
//    private final String passwordHash;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

//    public String getPasswordHash() {
//        return passwordHash;
//    }

    @Override
    public String getKey() {
        return username;
    }
}