package com.beijunyi.sw.config.model;

public class GameServerProperties {

  private final String name;
  private final String ip;
  private final int port;

  public GameServerProperties(String name, String ip, int port) {
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
