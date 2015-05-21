package com.beijunyi.sw.config.model;

import java.util.Properties;

public class GameServerMainProperties extends Properties {

  public final static String SERVER_KEY = "server";
  public final static String SERVER_NAME_KEY = SERVER_KEY + ".name";
  public final static String SERVER_IP_KEY = SERVER_KEY + ".ip";
  public final static String SERVER_PORT_KEY = SERVER_KEY + ".port";

  public final static String DEFAULT_SERVER_NAME = "gs";
  public final static String DEFAULT_SERVER_IP = "127.0.0.1";
  public final static String DEFAULT_SERVER_PORT = "8889";

  public GameServerMainProperties() {
    setProperty(SERVER_NAME_KEY, DEFAULT_SERVER_NAME);
    setProperty(SERVER_IP_KEY, DEFAULT_SERVER_IP);
    setProperty(SERVER_PORT_KEY, DEFAULT_SERVER_PORT);
  }

  public String getName() {
    return getProperty(SERVER_NAME_KEY);
  }

  public String getIp() {
    return getProperty(SERVER_IP_KEY);
  }

  public int getPort() {
    return Integer.valueOf(getProperty(SERVER_PORT_KEY));
  }
}
