package com.beijunyi.sw.config;

import java.nio.file.Path;

import com.beijunyi.sw.GatewayServerConstants;
import com.beijunyi.sw.config.model.GatewayServerMessageProperties;
import com.beijunyi.sw.message.InternalMessageBroker;
import org.jgroups.JChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayServerMessageConfig extends AbstractConfig<GatewayServerMessageProperties> {

  public final static Path MESSAGE_PROPERTIES_PATH = GatewayServerConstants.MODULE_HOME.resolve("message.properties");

  @Bean
  public InternalMessageBroker internalMessageBroker() throws Exception {
    JChannel channel = new JChannel();
    channel.connect(props.getClusterName());
    InternalMessageBroker broker = new InternalMessageBroker(channel);
    channel.setReceiver(broker);
    return broker;
  }

  @Override
  protected Class<GatewayServerMessageProperties> getPropertiesClass() {
    return GatewayServerMessageProperties.class;
  }

  @Override
  protected Path getPropertiesPath() {
    return MESSAGE_PROPERTIES_PATH;
  }
}
