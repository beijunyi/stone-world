package com.beijunyi.sw.config.model;

import java.util.Properties;

public class GatewayServerMainProperties extends Properties {

  public final static String SERVER_KEY = "server";
  public final static String SERVER_NAME_KEY = SERVER_KEY + ".name";

  public final static String DEFAULT_SERVER_NAME = "gs";

  public GatewayServerMainProperties() {
    setProperty(SERVER_NAME_KEY, DEFAULT_SERVER_NAME);
  }

  public String getServerName() {
    return getProperty(SERVER_NAME_KEY);
  }

}
