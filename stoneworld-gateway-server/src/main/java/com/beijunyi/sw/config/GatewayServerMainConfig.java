package com.beijunyi.sw.config;

import java.nio.file.Path;

import com.beijunyi.sw.GatewayServerConstants;
import com.beijunyi.sw.config.model.GatewayServerMainProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayServerMainConfig extends AbstractConfig<GatewayServerMainProperties> {

  public final static Path MAIN_PROPERTIES_PATH = GatewayServerConstants.MODULE_HOME.resolve("main.properties");

  @Override
  protected Class<GatewayServerMainProperties> getPropertiesClass() {
    return GatewayServerMainProperties.class;
  }

  @Override
  protected Path getPropertiesPath() {
    return MAIN_PROPERTIES_PATH;
  }
}
