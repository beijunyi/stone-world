package com.beijunyi.sw.config;

import com.beijunyi.sw.config.model.ServerProperties;
import com.esotericsoftware.kryo.Kryo;
import org.jgroups.JChannel;
import org.springframework.context.annotation.*;

@Configuration
@Import({WebMvcConfig.class})
@ComponentScan(basePackages = "com.beijunyi.sw")
public class GameServerConfig {

  @Bean
  public JChannel jChannel() throws Exception {
    JChannel channel = new JChannel();
    channel.connect(System.getProperty("cluster.name", "stoneworld"));
    return channel;
  }

  @Bean
  public Kryo kryo() {
    Kryo kryo = new Kryo();
    kryo.setReferences(false);
    return kryo;
  }

  @Bean
  public ServerProperties serverProperties() {
    return new ServerProperties(System.getProperty("server.ip"), Integer.valueOf(System.getProperty("server.port")));
  }

}
