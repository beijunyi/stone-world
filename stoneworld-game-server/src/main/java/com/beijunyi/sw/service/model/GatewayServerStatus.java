package com.beijunyi.sw.service.model;

public class GatewayServerStatus {

  private final String name;
  private final Object address;

  public GatewayServerStatus(String name, Object address) {
    this.name = name;
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public Object getAddress() {
    return address;
  }
}
