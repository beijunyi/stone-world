package com.beijunyi.sw.service.model;

public class GameServerStatus {

  private final String name;
  private final String ip;
  private final int port;

  public GameServerStatus(String name, String ip, int port) {
    this.name = name;
    this.ip = ip;
    this.port = port;
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

}
