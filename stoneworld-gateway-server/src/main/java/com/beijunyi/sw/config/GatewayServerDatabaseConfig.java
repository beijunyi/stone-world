package com.beijunyi.sw.config;

import java.nio.file.Path;
import java.sql.Driver;
import javax.sql.DataSource;

import com.beijunyi.sw.GatewayServerConstants;
import com.beijunyi.sw.config.model.GatewayServerDatabaseProperties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class GatewayServerDatabaseConfig extends AbstractConfig<GatewayServerDatabaseProperties> {

  public final static Path DATABASE_PROPERTIES_PATH = GatewayServerConstants.MODULE_HOME.resolve("database.properties");

  @Bean
  public SessionFactory sessionFactory() throws Exception {
    DataSource ds = new SimpleDriverDataSource((Driver) Class.forName(props.getProperty(AvailableSettings.DRIVER)).newInstance(), props.getProperty(AvailableSettings.URL));
    LocalSessionFactoryBuilder sfb = new LocalSessionFactoryBuilder(ds);
    sfb.scanPackages("com.beijunyi.sw.security.model.**");
    sfb.addProperties(props);
    return sfb.buildSessionFactory();
  }

  @Bean
  public HibernateTransactionManager transactionManager() throws Exception {
    return new HibernateTransactionManager(sessionFactory());
  }

  @Override
  protected Class<GatewayServerDatabaseProperties> getPropertiesClass() {
    return GatewayServerDatabaseProperties.class;
  }

  @Override
  protected Path getPropertiesPath() {
    return DATABASE_PROPERTIES_PATH;
  }
}
