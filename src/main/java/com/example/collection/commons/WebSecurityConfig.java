package com.example.collection.commons;

import com.example.collection.commons.handlers.WebAccessDeniedHandler;
import com.example.collection.services.SecurityUserService;
import com.mysql.cj.protocol.x.XAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    private final WebAccessDeniedHandler webAccessDeniedHandler;
    private final SecurityUserService securityUserService;

    @Autowired
    public WebSecurityConfig(WebAccessDeniedHandler webAccessDeniedHandler, SecurityUserService securityUserService){
        this.webAccessDeniedHandler  = webAccessDeniedHandler;
        this.securityUserService = securityUserService;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/login","/join","/test/**").permitAll()
                .antMatchers("/v/users").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/v", "/v/**").access("hasRole('ROLE_VIEW')")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/v",true)
                .usernameParameter("email").passwordParameter("password")
                .and()
                .logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().accessDeniedHandler(webAccessDeniedHandler)
                .and()
                .authenticationProvider(authenticationProvider())
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() { //??????????????? ???????????? authenticationProvider??? ????????? ????????????
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(securityUserService); //????????? ?????? ???
        authProvider.setPasswordEncoder(passwordEncoder()); //????????? ?????? ???, ???????????? ????????? ???????????? ?????? ?????????
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //Blowfish ?????? ????????? ?????? ?????? ??????????????? bcrypt
    }

}
