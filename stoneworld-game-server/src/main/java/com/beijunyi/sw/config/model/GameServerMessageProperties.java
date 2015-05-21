package com.beijunyi.sw.config.model;

import java.util.Properties;

import com.beijunyi.sw.AppConstants;

public class GameServerMessageProperties extends Properties {

  public final static String CLUSTER_PROPERTY_KEY = "cluster";
  public final static String CLUSTER_NAME_PROPERTY_KEY = CLUSTER_PROPERTY_KEY + ".name";

  public GameServerMessageProperties() {
    setProperty(CLUSTER_NAME_PROPERTY_KEY, AppConstants.DEFAULT_CLUSTER_NAME);
  }

  public String getClusterName() {
    return getProperty(CLUSTER_NAME_PROPERTY_KEY);
  }

}
