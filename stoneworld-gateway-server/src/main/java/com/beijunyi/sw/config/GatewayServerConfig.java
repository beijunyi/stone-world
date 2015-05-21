package com.beijunyi.sw.config;

import java.io.IOException;
import java.io.InputStream;

import com.beijunyi.sw.config.custom.CustomResourcesSettings;
import com.esotericsoftware.kryo.Kryo;
import org.codehaus.jackson.map.ObjectMapper;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.context.annotation.*;

@Configuration
@Import({
          GatewayServerMainConfig.class,
          GatewayServerDatabaseConfig.class,
          GatewayServerMessageConfig.class,
          GatewayServerResourceConfig.class,
          GatewayServerSecurityConfig.class
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
  public ObjectMapper getObjectMapper() {
    return new ObjectMapper();
  }

}
