package com.beijunyi.sw.config;

import java.nio.file.Path;

import com.beijunyi.sw.GatewayServerConstants;
import com.beijunyi.sw.config.model.GatewayServerResourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayServerResourceConfig extends AbstractConfig<GatewayServerResourceProperties> {

  public final static Path RESOURCE_PROPERTIES_PATH = GatewayServerConstants.MODULE_HOME.resolve("resource.properties");

  @Bean
  public GatewayServerResourceProperties gatewayServerResourceProperties() {
    return props;
  }

  @Override
  protected Class<GatewayServerResourceProperties> getPropertiesClass() {
    return GatewayServerResourceProperties.class;
  }

  @Override
  protected Path getPropertiesPath() {
    return RESOURCE_PROPERTIES_PATH;
  }
}
