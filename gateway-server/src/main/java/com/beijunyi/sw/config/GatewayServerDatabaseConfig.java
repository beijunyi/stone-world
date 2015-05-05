package com.beijunyi.sw.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Driver;
import java.util.Properties;
import javax.sql.DataSource;

import com.beijunyi.sw.GatewayServerConstants;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.H2Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class GatewayServerDatabaseConfig {

  public final static Path DATABASE_PROPERTIES = GatewayServerConstants.MODULE_HOME.resolve("database.properties");
  public final static Path DATABASE_LOCATION = GatewayServerConstants.MODULE_HOME.resolve("h2");

  private final Properties cfg = new Properties();

  public GatewayServerDatabaseConfig() throws IOException {
    if(!Files.exists(DATABASE_PROPERTIES)) {
      loadDefaultSettings(cfg);
      loadOverrideSettings(cfg);
      Files.createDirectories(DATABASE_PROPERTIES.getParent());
      try(OutputStream out = Files.newOutputStream(DATABASE_PROPERTIES)) {
        cfg.store(out, null);
      }
    } else {
      loadDefaultSettings(cfg);
      try(InputStream in = Files.newInputStream(DATABASE_PROPERTIES)) {
        cfg.load(in);
      }
      loadOverrideSettings(cfg);
    }
  }

  private void loadDefaultSettings(Properties cfg) {
    cfg.setProperty(AvailableSettings.DRIVER, org.h2.Driver.class.getCanonicalName());
    cfg.setProperty(AvailableSettings.URL, "jdbc:h2:file:" + DATABASE_LOCATION);
    cfg.setProperty(AvailableSettings.USER, GatewayServerConstants.MODULE_NAME);
    cfg.setProperty(AvailableSettings.PASS, "");
    cfg.setProperty(AvailableSettings.DIALECT, H2Dialect.class.getCanonicalName());
    cfg.setProperty(AvailableSettings.SHOW_SQL, Boolean.toString(true));
    cfg.setProperty(AvailableSettings.HBM2DDL_AUTO, "update");
  }

  private void loadOverrideSettings(Properties cfg) {
    Properties argCfg = System.getProperties();
    String[] keys = new String[] {
                                   AvailableSettings.DRIVER,
                                   AvailableSettings.URL,
                                   AvailableSettings.USER,
                                   AvailableSettings.PASS,
                                   AvailableSettings.DIALECT,
                                   AvailableSettings.SHOW_SQL,
                                   AvailableSettings.HBM2DDL_AUTO
    };
    for(String key : keys) {
      if(argCfg.containsKey(key))
        cfg.setProperty(key, argCfg.getProperty(key));
    }
  }

  @Bean
  public SessionFactory sessionFactory() throws Exception {
    DataSource ds = new SimpleDriverDataSource((Driver) Class.forName(cfg.getProperty(AvailableSettings.DRIVER)).newInstance(), cfg.getProperty(AvailableSettings.URL));
    LocalSessionFactoryBuilder sfb = new LocalSessionFactoryBuilder(ds);
    sfb.scanPackages("com.beijunyi.sw.security.model.*");
    sfb.addProperties(cfg);
    return sfb.buildSessionFactory();
  }

  @Bean
  public HibernateTransactionManager transactionManager() throws Exception {
    return new HibernateTransactionManager(sessionFactory());
  }


}
