package net.shtyftu.ubiquode.web.security;

import java.util.Date;
import net.shtyftu.ubiquode.dao.plain.LoginTokenWrapperDao;
import net.shtyftu.ubiquode.model.persist.simple.LoginTokenWrapper;
import net.shtyftu.ubiquode.model.persist.simple.ModelWithId;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * @author shtyftu
 */
public class RedisLoginTokenRepository implements PersistentTokenRepository{

    private final LoginTokenWrapperDao tokenDao;

    public RedisLoginTokenRepository(LoginTokenWrapperDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        final String series = token.getSeries();
        if (tokenDao.getOptional(series).map(LoginTokenWrapper::getToken).isPresent()) {
            throw new IllegalArgumentException(series + " Id already exists");
        }
        tokenDao.save(new LoginTokenWrapper(series, token));
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        final String username = getTokenForSeries(series).getUsername();
        PersistentRememberMeToken newToken = new PersistentRememberMeToken(
                username, series, tokenValue, new Date()
        );
        tokenDao.save(new LoginTokenWrapper(series, newToken));
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return tokenDao.getOptional(seriesId).map(LoginTokenWrapper::getToken).orElse(null);
    }

    @Override
    public void removeUserTokens(String username) {
        tokenDao.getAll().stream()
                .filter(t -> t.getToken() != null && username.equals(t.getToken().getUsername()))
                .map(ModelWithId::getId)
                .map(LoginTokenWrapper::new)
                .forEach(tokenDao::save);
    }

}
