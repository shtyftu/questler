package net.shtyftu.ubiquode.model.persist.simple;

import net.shtyftu.ubiquode.model.AModel;

/**
 * @author shtyftu
 **/
public class Account extends AModel implements PersistEntity {

//    private static final String SALT = "r12j012f!@R@02j";

    private final String login;
    private final String password;
    private final String userId;
//    private final String passwordHash;

    public Account(String username, String password, String userId) {
        this.login = username;
        this.password = password;
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

//    public String getPasswordHash() {
//        return passwordHash;
//    }

    @Override
    public String getKey() {
        return login;
    }
}