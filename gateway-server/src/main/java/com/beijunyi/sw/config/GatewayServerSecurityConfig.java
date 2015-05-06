package com.beijunyi.sw.config;

import javax.inject.Inject;

import org.jasypt.springsecurity3.authentication.encoding.PasswordEncoder;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
  public final static String LOGIN_URL = "/login";
  public final static String LOGOUT_URL = "/logout";

  @Inject
  private UserDetailsService userDetailsService;

  @Bean
  public InMemoryTokenRepositoryImpl inMemoryTokenRepository() {
    return new InMemoryTokenRepositoryImpl();
  }

  @Bean
  public PasswordEncryptor passwordEncryptor() {
    return new BasicPasswordEncryptor();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    PasswordEncoder passwordEncoder = new PasswordEncoder();
    passwordEncoder.setPasswordEncryptor(passwordEncryptor());
    return passwordEncoder;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .anyRequest().authenticated()
    .and()
      .formLogin()
      .loginProcessingUrl(LOGIN_URL)
      .loginPage(LOGIN_PAGE)
      .failureUrl(LOGIN_PAGE + "#error").permitAll()
    .and()
      .logout()
      .logoutUrl(LOGOUT_URL)
      .logoutSuccessUrl(LOGIN_PAGE)
    .and()
      .authenticationProvider(authenticationProvider())
      .rememberMe()
      .tokenRepository(inMemoryTokenRepository())
      .userDetailsService(userDetailsService);
  }
}
