package com.beijunyi.sw.config.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.beijunyi.sw.GatewayServerConstants;

public class GatewayServerResourceProperties extends Properties {

  public final static String SA_PROPERTY_KEY = "sa";
  public final static String SA_DATA_PROPERTY_KEY = SA_PROPERTY_KEY + ".data";
  public final static String GMSV_PROPERTY_KEY = "gmsv";
  public final static String GMSV_DATA_PROPERTY_KEY = GMSV_PROPERTY_KEY + ".data";
  public final static String CACHE_PROPERTY_KEY = "cache";

  public final static Path DEFAULT_RESOURCE_LOCATION = GatewayServerConstants.MODULE_HOME.resolve("resources");
  public final static Path DEFAULT_SA_DATA_LOCATION = DEFAULT_RESOURCE_LOCATION.resolve("sa/data");
  public final static Path DEFAULT_GMSV_DATA_LOCATION = DEFAULT_RESOURCE_LOCATION.resolve("gmsv/data");
  public final static Path DEFAULT_CACHE_LOCATION = DEFAULT_RESOURCE_LOCATION.resolve("cache");

  public GatewayServerResourceProperties() {
    setProperty(SA_DATA_PROPERTY_KEY, DEFAULT_SA_DATA_LOCATION.toString());
    setProperty(GMSV_DATA_PROPERTY_KEY, DEFAULT_GMSV_DATA_LOCATION.toString());
    setProperty(CACHE_PROPERTY_KEY, DEFAULT_CACHE_LOCATION.toString());
  }

  public Path getSaDataLocation() {
    return Paths.get(getProperty(SA_DATA_PROPERTY_KEY));
  }

  public Path getGmsvDataLocation() {
    return Paths.get(getProperty(GMSV_DATA_PROPERTY_KEY));
  }

  public Path getCacheLocation() {
    return Paths.get(getProperty(CACHE_PROPERTY_KEY));
  }

}
