package net.shtyftu.ubiquode.web.security;

import java.util.Collection;
import net.shtyftu.ubiquode.service.AccountService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * @author shtyftu
 */
@Component
public final class TokenProvider implements AuthenticationProvider {

    @Autowired
    private AccountService service;

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(final Authentication authentication) {
        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            throw new AuthenticationServiceException(
                    "expecting a UsernamePasswordAuthenticationToken, got " + authentication);
        }

        try {
            final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            final String principal = (String) token.getPrincipal();
            final String credentials = (String) token.getCredentials();
            final Collection<? extends GrantedAuthority> authorities = service.getAuthorities(principal, credentials);

            if (CollectionUtils.isEmpty(authorities)) {
                throw new BadCredentialsException("error");
            }
            return new UsernamePasswordAuthenticationToken(principal, credentials, authorities);

        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            // stop non-AuthenticationExceptions. otherwise full stacktraces returned to the requester
            throw new AuthenticationServiceException("Internal error occurred");
        }
    }
}