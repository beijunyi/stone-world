package com.beijunyi.sw.security;

import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.dao.AbstractDao;
import com.beijunyi.sw.security.model.Account;
import org.hibernate.SessionFactory;

@Named
public class AccountDao extends AbstractDao<Account> {

  @Inject
  public AccountDao(SessionFactory sf) {
    super(sf);
  }

  @Override
  protected Class<Account> getPersistentClass() {
    return Account.class;
  }

  public Account findByUsername(String username) {
    return getBy("username", username);
  }
}
