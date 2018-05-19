package net.shtyftu.ubiquode.model.persist.simple;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

/**
 * @author shtyftu
 */
public class LoginTokenWrapper extends ModelWithId {

    private PersistentRememberMeToken token;

    public LoginTokenWrapper(String series) {
        super(series);
    }

    public LoginTokenWrapper(String series, PersistentRememberMeToken token) {
        super(series);
        this.token = token;
    }

    public PersistentRememberMeToken getToken() {
        return token;
    }

    public void setToken(PersistentRememberMeToken token) {
        this.token = token;
    }
}
