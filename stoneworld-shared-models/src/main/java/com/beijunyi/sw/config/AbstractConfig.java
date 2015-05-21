package com.beijunyi.sw.config;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public abstract class AbstractConfig<T extends Properties> {

  protected final T props;
  protected final Path path;

  public AbstractConfig() {
    try {
      props = getPropertiesClass().newInstance();
      path = getPropertiesPath();

      if(!Files.exists(path)) {
        loadOverrideSettings();
        Files.createDirectories(path.getParent());
        try(OutputStream out = Files.newOutputStream(path)) {
          props.store(out, getComments());
        }
      } else {
        try(InputStream in = Files.newInputStream(path)) {
          props.load(in);
        }
        loadOverrideSettings();
      }
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected String getComments() {
    return getClass().getName();
  }

  protected void loadOverrideSettings() {
    Properties vmParams = System.getProperties();
    for(Object key : props.keySet()) {
      String keyStr = (String) key;
      if(vmParams.containsKey(keyStr))
        props.setProperty(keyStr, vmParams.getProperty(keyStr));
    }
  }

  protected abstract Class<T> getPropertiesClass();

  protected abstract Path getPropertiesPath();

}
