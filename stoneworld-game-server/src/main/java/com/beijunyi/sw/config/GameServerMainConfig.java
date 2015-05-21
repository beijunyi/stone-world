package com.beijunyi.sw.config;

import java.nio.file.Path;

import com.beijunyi.sw.GameServerConstants;
import com.beijunyi.sw.config.model.GameServerMainProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameServerMainConfig extends AbstractConfig<GameServerMainProperties> {

  public final static Path MAIN_PROPERTIES_PATH = GameServerConstants.MODULE_HOME.resolve("main.properties");

  @Bean
  public GameServerMainProperties gameServerMainProperties() {
    return props;
  }

  @Override
  protected Class<GameServerMainProperties> getPropertiesClass() {
    return GameServerMainProperties.class;
  }

  @Override
  protected Path getPropertiesPath() {
    return MAIN_PROPERTIES_PATH;
  }
}
