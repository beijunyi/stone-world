package com.beijunyi.sw.config.model;

import java.nio.file.Path;
import java.util.Properties;

import com.beijunyi.sw.GatewayServerConstants;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.H2Dialect;

public class GatewayServerDatabaseProperties extends Properties {

  public final static Path DATABASE_LOCATION = GatewayServerConstants.MODULE_HOME.resolve("h2");

  public GatewayServerDatabaseProperties() {
    setProperty(AvailableSettings.DRIVER, org.h2.Driver.class.getCanonicalName());
    setProperty(AvailableSettings.URL, "jdbc:h2:file:" + DATABASE_LOCATION);
    setProperty(AvailableSettings.USER, GatewayServerConstants.MODULE_NAME);
    setProperty(AvailableSettings.PASS, "");
    setProperty(AvailableSettings.DIALECT, H2Dialect.class.getCanonicalName());
    setProperty(AvailableSettings.SHOW_SQL, Boolean.toString(true));
    setProperty(AvailableSettings.HBM2DDL_AUTO, "update");
  }

}
