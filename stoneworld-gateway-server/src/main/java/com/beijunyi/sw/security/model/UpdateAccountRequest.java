package com.beijunyi.sw.security.model;

public class UpdateAccountRequest {

  private Integer id;
  private String username;
  private String alias;
  private String password;
  private Boolean admin;
  private Boolean active;

  public UpdateAccountRequest(String username, String alias, String password, Boolean admin) {
    this.username = username;
    this.alias = alias;
    this.password = password;
    this.admin = admin;
  }

  public UpdateAccountRequest() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getAdmin() {
    return admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

}
