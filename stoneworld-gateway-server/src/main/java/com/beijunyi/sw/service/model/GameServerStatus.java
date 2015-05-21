package com.beijunyi.sw.service.model;

public class GameServerStatus {

  private final String name;
  private final String ip;
  private final int port;
  private final Object address;

  public GameServerStatus(String name, String ip, int port, Object address) {
    this.name = name;
    this.ip = ip;
    this.port = port;
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public String getIp() {
    return ip;
  }

  public int getPort() {
    return port;
  }

  public Object getAddress() {
    return address;
  }
}
