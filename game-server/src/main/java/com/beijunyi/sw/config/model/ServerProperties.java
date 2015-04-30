package com.beijunyi.sw.config.model;

public class ServerProperties {

  private final String ip;
  private final int port;

  public ServerProperties(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }

  public String getIp() {
    return ip;
  }

  public int getPort() {
    return port;
  }
}
