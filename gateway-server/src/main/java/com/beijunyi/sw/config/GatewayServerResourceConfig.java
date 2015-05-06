package com.beijunyi.sw.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import com.beijunyi.sw.GatewayServerConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayServerResourceConfig {

  public final static Path RESOURCE_PROPERTIES = GatewayServerConstants.MODULE_HOME.resolve("resource.properties");

  public final static String SA_PROPERTY_KEY = "sa";
  public final static String SA_DATA_PROPERTY_KEY = SA_PROPERTY_KEY + ".data";
  public final static String GMSV_PROPERTY_KEY = "gmsv";
  public final static String GMSV_DATA_PROPERTY_KEY = GMSV_PROPERTY_KEY + ".data";
  public final static String CACHE_PROPERTY_KEY = "cache";

  public final static Path DEFAULT_RESOURCE_LOCATION = GatewayServerConstants.MODULE_HOME.resolve("resources");
  public final static Path DEFAULT_SA_DATA_LOCATION = DEFAULT_RESOURCE_LOCATION.resolve("sa/data");
  public final static Path DEFAULT_GMSV_DATA_LOCATION = DEFAULT_RESOURCE_LOCATION.resolve("gmsv/data");
  public final static Path DEFAULT_CACHE_LOCATION = DEFAULT_RESOURCE_LOCATION.resolve("cache");

  private final Properties props = new Properties();

  public GatewayServerResourceConfig() throws IOException {
    if(!Files.exists(RESOURCE_PROPERTIES)) {
      loadDefaultSettings();
      loadOverrideSettings();
      Files.createDirectories(RESOURCE_PROPERTIES.getParent());
      try(OutputStream out = Files.newOutputStream(RESOURCE_PROPERTIES)) {
        props.store(out, null);
      }
    } else {
      loadDefaultSettings();
      try(InputStream in = Files.newInputStream(RESOURCE_PROPERTIES)) {
        props.load(in);
      }
      loadOverrideSettings();
    }
  }

  private void loadDefaultSettings() {
    props.setProperty(SA_DATA_PROPERTY_KEY, DEFAULT_SA_DATA_LOCATION.toString());
    props.setProperty(GMSV_DATA_PROPERTY_KEY, DEFAULT_GMSV_DATA_LOCATION.toString());
    props.setProperty(CACHE_PROPERTY_KEY, DEFAULT_CACHE_LOCATION.toString());
  }

  private void loadOverrideSettings() {
    Properties argCfg = System.getProperties();
    String[] keys = new String[] {
                                   SA_DATA_PROPERTY_KEY,
                                   GMSV_DATA_PROPERTY_KEY,
                                   CACHE_PROPERTY_KEY
    };
    for(String key : keys) {
      if(argCfg.containsKey(key))
        props.setProperty(key, argCfg.getProperty(key));
    }
  }

  @Bean(name = "resource-properties")
  public Properties resourceProperties() {
    return props;
  }

}
