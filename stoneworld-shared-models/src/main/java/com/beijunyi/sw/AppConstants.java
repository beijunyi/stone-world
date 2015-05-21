package com.beijunyi.sw;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppConstants {

  public final static String APP_NAME = "Stone World";
  public final static Path APP_HOME = Paths.get(System.getProperty("user.home")).resolve(APP_NAME);

  public final static String DEFAULT_CLUSTER_NAME = APP_NAME;

}
