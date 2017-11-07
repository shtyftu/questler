package net.shtyftu.ubiquode.dao.plain;

import com.google.gson.reflect.TypeToken;
import net.shtyftu.ubiquode.model.persist.simple.Account;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class AccountDao extends RedisModelWithIdDao<Account> {

    public AccountDao() {
        super(new TypeToken<Account>() {
        });
    }
}
