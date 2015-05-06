package com.beijunyi.sw.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import com.beijunyi.sw.GatewayServerConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayServerMainPropertiesConfig {

  public final static Path MAIN_PROPERTIES = GatewayServerConstants.MODULE_HOME.resolve("main.properties");

  public final static String SERVER_KEY = "server";
  public final static String SERVER_NAME_KEY = SERVER_KEY + ".name";

  private final Properties props = new Properties();

  public GatewayServerMainPropertiesConfig() throws IOException {
    if(!Files.exists(MAIN_PROPERTIES)) {
      loadDefaultSettings();
      loadOverrideSettings();
      Files.createDirectories(MAIN_PROPERTIES.getParent());
      try(OutputStream out = Files.newOutputStream(MAIN_PROPERTIES)) {
        props.store(out, null);
      }
    } else {
      loadDefaultSettings();
      try(InputStream in = Files.newInputStream(MAIN_PROPERTIES)) {
        props.load(in);
      }
      loadOverrideSettings();
    }
  }

  private void loadDefaultSettings() throws UnknownHostException {
    props.setProperty(SERVER_NAME_KEY, InetAddress.getLocalHost().getHostName());
  }

  private void loadOverrideSettings() {
    Properties argCfg = System.getProperties();
    String[] keys = new String[] {
                                   SERVER_NAME_KEY,
    };
    for(String key : keys) {
      if(argCfg.containsKey(key))
        props.setProperty(key, argCfg.getProperty(key));
    }
  }

  @Bean(name = "gateway-server-main-properties")
  public Properties gatewayServerMainProperties() {
    return props;
  }

}
