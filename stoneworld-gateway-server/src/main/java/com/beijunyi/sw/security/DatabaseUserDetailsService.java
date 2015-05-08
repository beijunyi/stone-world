package com.beijunyi.sw.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.security.model.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.transaction.annotation.Transactional;

@Named
public class DatabaseUserDetailsService implements UserDetailsService {


  private AccountManager accountManager;

  @Inject
  public DatabaseUserDetailsService(AccountManager accountManager) {
    this.accountManager = accountManager;
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
