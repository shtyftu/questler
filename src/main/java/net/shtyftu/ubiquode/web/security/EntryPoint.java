package net.shtyftu.ubiquode.web.security;

import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class EntryPoint extends LoginUrlAuthenticationEntryPoint {

    public EntryPoint() {
        super("/login");
    }
}
