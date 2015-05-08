package com.beijunyi.sw.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.beijunyi.sw.dao.QueryRequest;
import com.beijunyi.sw.dao.QueryResult;
import com.beijunyi.sw.security.model.Account;
import com.beijunyi.sw.security.model.ChangeAccountPasswordRequest;
import com.beijunyi.sw.security.model.UpdateAccountRequest;
import org.jasypt.util.password.PasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class AccountManager {

  private static final Logger log = LoggerFactory.getLogger(AccountManager.class);

  @Inject
  private AccountDao ad;
  @Inject
  private PasswordEncryptor pe;


  @Transactional
  public QueryResult<Account> queryAccounts(QueryRequest request) {
    return ad.query(request);
  }

  @Transactional
  public Account getAccount(int id) {
    return ad.get(id);
  }

  @Transactional
  public Account getAccount(String username) {
    return ad.findByUsername(username);
  }

  @Transactional
  public Account createAccount(UpdateAccountRequest request) {
    return ad.create(new Account(request.getUsername(), request.getAlias(), pe.encryptPassword(request.getPassword()), request.getAdmin()));
  }

  @Transactional
  public Account changeAccountPassword(ChangeAccountPasswordRequest request) {
    if(request.getId() == null)
      throw new IllegalArgumentException("id is null");

    Account account = ad.get(request.getId());
    if(account == null)
      throw new RuntimeException("account not found: " + request.getId());

    account.setPassword(pe.encryptPassword(request.getNewPassword()));

    return ad.update(account);
  }
}
