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

    @Autowired
    private TokenProvider tokenAuthenticationProvider;

//    @Autowired
//    private Filter tokenFilter;
//
    @Autowired
    private EntryPoint entryPoint;

    @Autowired
    private AuthFilter authFilter;

    @Bean(name="authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth)  throws Exception {
        auth.authenticationProvider(tokenAuthenticationProvider);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean () {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(authFilter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//        http
//                .authorizeRequests()
                .antMatchers("/css/**", "/register", "/login").permitAll()
                .antMatchers("/quest/**").hasAuthority("USER")
//
                .and()
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(tokenAuthenticationProvider)
//                .antMatcher("/authenticate/**")
                .exceptionHandling().authenticationEntryPoint(entryPoint)
//                .and()
//                .logout().logoutSuccessUrl(oeSettings.getUrl())
//                .and()
//                .formLogin()
//                .loginPage("/loginPage").failureUrl("/loginPager");
                ;
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }
}