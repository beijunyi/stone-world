package com.beijunyi.sw.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import javax.inject.Inject;

import com.beijunyi.sw.AppConstants;
import com.beijunyi.sw.config.custom.CustomResourcesSettings;
import com.beijunyi.sw.message.InternalMessageHandler;
import com.beijunyi.sw.message.InternalMessageReceiver;
import com.esotericsoftware.kryo.Kryo;
import org.codehaus.jackson.map.ObjectMapper;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;
import org.jgroups.JChannel;
import org.springframework.context.annotation.*;

@Configuration
@Import({
          GatewayServerMainPropertiesConfig.class,
          GatewayServerDatabaseConfig.class,
          GatewayServerSecurityConfig.class,
          GatewayServerResourceConfig.class
})
@ComponentScan(basePackages = "com.beijunyi.sw")
public class GatewayServerConfig {

  @Bean
  public Kryo kryo() {
    Kryo kryo = new Kryo();
    kryo.setReferences(false);
    return kryo;
  }

  @Bean
  public PasswordEncryptor passwordEncryptor() {
    return new BasicPasswordEncryptor();
  }

  @Bean
  public CustomResourcesSettings getCustomResourcesSettings() throws IOException {
    ObjectMapper mapper = getObjectMapper();
    try(InputStream is = getClass().getResourceAsStream("/custom_resources.json")) {
      return mapper.readValue(is, CustomResourcesSettings.class);
    }
  }

  @Bean
  public JChannel jChannel() throws Exception {
    JChannel channel = new JChannel();
    channel.connect(System.getProperty("cluster.name", AppConstants.DEFAULT_CLUSTER));
    return channel;
  }

  @Bean
  @Inject
  public InternalMessageReceiver internalMessageReceiver(JChannel channel, Collection<InternalMessageHandler> handlers) {
    InternalMessageReceiver receiver = new InternalMessageReceiver(handlers);
    channel.setReceiver(receiver);
    return receiver;
  }

  @Bean
  public ObjectMapper getObjectMapper() {
    return new ObjectMapper();
  }

}
