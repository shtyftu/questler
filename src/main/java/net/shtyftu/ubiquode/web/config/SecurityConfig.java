package net.shtyftu.ubiquode.web.config;

import net.shtyftu.ubiquode.dao.plain.LoginTokenWrapperDao;
import net.shtyftu.ubiquode.web.security.RedisLoginTokenRepository;
import net.shtyftu.ubiquode.web.security.AuthTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * @author shtyftu
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthTokenProvider authTokenProvider;
    private final LoginTokenWrapperDao tokenDao;

    @Autowired
    public SecurityConfig(AuthTokenProvider authTokenProvider, LoginTokenWrapperDao tokenDao) {
        this.authTokenProvider = authTokenProvider;
        this.tokenDao = tokenDao;
    }

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authTokenProvider);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/**").hasAuthority("USER")
                .and()

                .formLogin()
                .loginPage("/login-page")
                .failureUrl("/login-page?error")
                .loginProcessingUrl("/login-check")
                .defaultSuccessUrl("/quest/list", true)
                .usernameParameter("login")
                .passwordParameter("password")
                .permitAll()
                .and()

                .authenticationProvider(authTokenProvider)

                .rememberMe()
                .rememberMeCookieName("questler-remember-me")
                .tokenValiditySeconds(30 * 24 * 60 * 60) // expired time = 30 day
                .tokenRepository(persistentTokenRepository())
        ;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new RedisLoginTokenRepository(tokenDao);
    }

}