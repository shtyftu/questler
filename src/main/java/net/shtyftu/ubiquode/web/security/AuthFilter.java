package net.shtyftu.ubiquode.web.security;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public class AuthFilter extends DefaultLoginPageGeneratingFilter {

    public AuthFilter() {
        super(
                new UsernamePasswordAuthenticationFilter() {{
                    setUsernameParameter("login");
                    setPasswordParameter("password");
                }}
        );
        setAuthenticationUrl("/login");
    }
}
