package com.beijunyi.sw.service.model;

import org.jgroups.Address;

public class GatewayServerStatus {

  private final String name;
  private final Address address;

  public GatewayServerStatus(String name, Address address) {
    this.name = name;
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public Address getAddress() {
    return address;
  }
}
