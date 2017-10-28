package net.shtyftu.ubiquode.web.config;

import net.shtyftu.ubiquode.web.security.AuthFilter;
import net.shtyftu.ubiquode.web.security.EntryPoint;
import net.shtyftu.ubiquode.web.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author shtyftu
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenAuthenticationProvider;
    private final EntryPoint entryPoint;
    private final AuthFilter authFilter;

    @Autowired
    public SecurityConfig(TokenProvider tokenAuthenticationProvider, EntryPoint entryPoint, AuthFilter authFilter) {
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
        this.entryPoint = entryPoint;
        this.authFilter = authFilter;
    }

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenAuthenticationProvider);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(authFilter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/register", "/login").permitAll()
                .antMatchers("/**").hasAuthority("USER")
                .and()
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(tokenAuthenticationProvider)
                .exceptionHandling().authenticationEntryPoint(entryPoint);
    }

}