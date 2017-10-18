package net.shtyftu.ubiquode.dao.simple;

import net.shtyftu.ubiquode.model.persist.simple.User;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class UserDao extends HashMapDao<User, String> {

}
