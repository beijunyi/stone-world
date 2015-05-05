package com.beijunyi.sw.security;

import org.springframework.security.core.GrantedAuthority;

public enum SitePrivilege implements GrantedAuthority {
  ROLE_PLAYER,
  ROLE_GAMEMASTER;

  @Override
  public String getAuthority() {
    return name();
  }

}
