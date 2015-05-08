package com.beijunyi.sw.security.model;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "account")
@DynamicUpdate
public final class Account {

  @Id
  @GeneratedValue
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Column(name = "alias", unique = false, nullable = false)
  private String alias;

  @Column(name = "password", nullable = false)
  @JsonIgnore
  private String password;

  @Column(name = "admin", nullable = false)
  private Boolean admin;

  @Column(name = "active", nullable = false)
  private Boolean active;

  public Account(String username, String alias, String password, Boolean admin) {
    this.username = username;
    this.alias = alias;
    this.password = password;
    this.admin = admin;
    this.active = true;
  }

  public Account() {
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
