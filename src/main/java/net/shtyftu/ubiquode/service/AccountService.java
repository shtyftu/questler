package net.shtyftu.ubiquode.service;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import net.shtyftu.ubiquode.dao.plain.AccountDao;
import net.shtyftu.ubiquode.model.persist.simple.Account;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author shtyftu
 */
@Service
public class AccountService {

    private static final List<SimpleGrantedAuthority> USER_AUTHORITY =
            ImmutableList.of(new SimpleGrantedAuthority("USER"));
    @Autowired
    private AccountDao accountDao;

    public boolean register(Account user) {
        if (user != null && StringUtils.isNotBlank(user.getLogin()) && StringUtils.isNotBlank(user.getPassword())
                && accountDao.getById(user.getId()) == null) {
            accountDao.save(user);
            return true;
        }
        return false;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String login, String password) {
        final Account savedAccount = accountDao.getById(login);
        if (savedAccount == null) {
            if (register(new Account(login, password, login))) {
                return USER_AUTHORITY;
            } else {
                return ImmutableList.of();
            }
        }
        if (savedAccount.getPassword().equals(password)) {
            return USER_AUTHORITY;
        } else {
            return ImmutableList.of();
        }
    }

    public UserDetails getByUsername(String username) {
        final Account user = accountDao.getById(username);
        if (user == null) {
            return null;
        }
        return new User(user.getLogin(), user.getPassword(), USER_AUTHORITY);
    }
}
