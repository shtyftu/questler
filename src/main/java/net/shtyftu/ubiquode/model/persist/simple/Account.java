package net.shtyftu.ubiquode.model.persist.simple;

import net.shtyftu.ubiquode.model.AModel;
/**
 * @author shtyftu
 **/
public class Account extends ModelWithId{

//    private static final String SALT = "r12j012f!@R@02j";

    private final String password;
    private final String userId;
//    private final String passwordHash;

    public Account(String login, String password, String userId) {
        super(login);
        this.password = password;
        this.userId = userId;
    }

    public String getLogin() {
        return getId();
    }

    public String getPassword() {
        return password;
    }

//    public String getPasswordHash() {
//        return passwordHash;
//    }

}