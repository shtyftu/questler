package net.shtyftu.ubiquode.dao.plain;

import com.google.gson.reflect.TypeToken;
import net.shtyftu.ubiquode.dao.RedisClientService;
import net.shtyftu.ubiquode.model.persist.simple.LoginTokenWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class LoginTokenWrapperDao extends RedisModelWithIdDao<LoginTokenWrapper> {

    @Autowired
    public LoginTokenWrapperDao(RedisClientService clientService) {
        super(
                new TypeToken<LoginTokenWrapper>() {
                },
                clientService
        );
    }
}
