package com.beijunyi.sw.config;

import java.nio.file.Path;

import com.beijunyi.sw.GameServerConstants;
import com.beijunyi.sw.config.model.GameServerMessageProperties;
import com.beijunyi.sw.message.InternalMessageBroker;
import org.jgroups.JChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameServerMessageConfig extends AbstractConfig<GameServerMessageProperties> {

  public final static Path MESSAGE_PROPERTIES_PATH = GameServerConstants.MODULE_HOME.resolve("message.properties");

  @Bean
  protected InternalMessageBroker internalMessageBroker() throws Exception {
    JChannel channel = new JChannel();
    channel.connect(props.getClusterName());
    InternalMessageBroker broker = new InternalMessageBroker(channel);
    channel.setReceiver(broker);
    return broker;
  }

  @Override
  protected Class<GameServerMessageProperties> getPropertiesClass() {
    return GameServerMessageProperties.class;
  }

  @Override
  protected Path getPropertiesPath() {
    return MESSAGE_PROPERTIES_PATH;
  }
}
