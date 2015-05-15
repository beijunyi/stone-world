package com.beijunyi.sw.config;

import javax.inject.Inject;

import org.jasypt.springsecurity3.authentication.encoding.PasswordEncoder;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class GatewayServerSecurityConfig extends WebSecurityConfigurerAdapter {

  public final static String LOGIN_PAGE = "/login.html";
  public final static String USERNAME_PARAM = "sw-username";
  public final static String PASSWORD_PARAM = "sw-password";
  public final static String LOGIN_ERROR_SUFFIX = "#error";
  public final static String LOGIN_URL = "/login";
  public final static String LOGOUT_URL = "/logout";

  @Inject
  public void configureGlobal(AuthenticationManagerBuilder amb, UserDetailsService uds, PasswordEncryptor pe) throws Exception {
    PasswordEncoder passwordEncoder = new PasswordEncoder();
    passwordEncoder.setPasswordEncryptor(pe);

    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(uds);
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    amb.authenticationProvider(authenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .authorizeRequests()
      .anyRequest().authenticated()
    .and()
      .formLogin()
      .loginProcessingUrl(LOGIN_URL)
      .loginPage(LOGIN_PAGE)
      .usernameParameter(USERNAME_PARAM)
      .passwordParameter(PASSWORD_PARAM)
      .failureUrl(LOGIN_PAGE + LOGIN_ERROR_SUFFIX).permitAll()
    .and()
      .logout()
      .logoutUrl(LOGOUT_URL)
      .logoutSuccessUrl(LOGIN_PAGE)
    .and()
      .rememberMe()
      .tokenRepository(new InMemoryTokenRepositoryImpl());
  }
}
