package com.beijunyi.sw.service.model;

import org.jgroups.Address;

public class ResourceServerStatus {

  private final Address address;

  public ResourceServerStatus(Address address) {
    this.address = address;
  }

  public Address getAddress() {
    return address;
  }
}
