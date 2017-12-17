package net.shtyftu.ubiquode.dao.plain;

import com.google.gson.reflect.TypeToken;
import net.shtyftu.ubiquode.dao.RedisClientService;
import net.shtyftu.ubiquode.model.persist.simple.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class AccountDao extends RedisModelWithIdDao<Account> {

    @Autowired
    public AccountDao(RedisClientService clientService) {
        super(
                new TypeToken<Account>() {
                },
                clientService
        );
    }
}
