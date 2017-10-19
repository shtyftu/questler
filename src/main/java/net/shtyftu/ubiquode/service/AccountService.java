package net.shtyftu.ubiquode.service;

import net.shtyftu.ubiquode.dao.simple.AccountDao;
import net.shtyftu.ubiquode.model.persist.simple.Account;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shtyftu
 */
@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    public boolean register(Account user) {
        if (user != null && StringUtils.isNotBlank(user.getLogin()) && StringUtils.isNotBlank(user.getPassword())
                && accountDao.getByKey(user.getKey()) == null) {
            accountDao.save(user);
            return true;
        }
        return false;
    }

    public boolean validateUser(Account account) {
        final Account savedAccount = accountDao.getByKey(account.getLogin());
        return savedAccount != null && savedAccount.getPassword().equals(account.getPassword());
    }
}
