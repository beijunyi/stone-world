package com.beijunyi.sw.service.model;

import org.jgroups.Address;

public class GatewayServerStatus {

  private final Address address;

  public GatewayServerStatus(Address address) {
    this.address = address;
  }

  public Address getAddress() {
    return address;
  }
}
