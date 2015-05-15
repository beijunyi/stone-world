package com.beijunyi.sw.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.security.model.Account;
import com.beijunyi.sw.security.model.UpdateAccountRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.transaction.annotation.Transactional;

@Named
public class DatabaseUserDetailsService implements UserDetailsService {

  private static final String DEFAULT_ADMIN_USERNAME = "admin";
  private static final String DEFAULT_ADMIN_PASSWORD = "password";
  private static final String DEFAULT_ADMIN_ALIAS = "Stone World Admin";

  private static final Logger log = LoggerFactory.getLogger(DatabaseUserDetailsService.class);

  private AccountManager accountManager;

  @Inject
  public DatabaseUserDetailsService(AccountManager accountManager) {
    this.accountManager = accountManager;
  }

  @PostConstruct
  public void init() {
    long count = accountManager.countAccounts();
    log.info("Total " + count + " accounts found.");
    if(count == 0) {
      accountManager.createAccount(new UpdateAccountRequest(DEFAULT_ADMIN_USERNAME, DEFAULT_ADMIN_ALIAS, DEFAULT_ADMIN_PASSWORD, true));
      log.info("Admin account " + DEFAULT_ADMIN_USERNAME + " has been created.");
    }
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountManager.getAccount(username);

    if(account == null)
      throw new UsernameNotFoundException("Could not find user " + username);

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(SitePrivilege.ROLE_PLAYER);

    assert account.getAdmin() != null;
    if(account.getAdmin())
      authorities.add(SitePrivilege.ROLE_GAMEMASTER);

    assert account.getUsername() != null;
    assert account.getPassword() != null;
    assert account.getActive() != null;

    return new User(account.getUsername(), account.getPassword(), true, true, true, account.getActive(), Collections.unmodifiableCollection(authorities));
  }

}
