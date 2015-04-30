package com.beijunyi.sw.config;

import org.jgroups.JChannel;
import org.springframework.context.annotation.*;

@Configuration
@Import({})
@ComponentScan(basePackages = "com.beijunyi.sw")
public class GameServerConfig {

  @Bean
  public JChannel jChannel() throws Exception {
    JChannel channel = new JChannel();
    channel.connect(System.getProperty("cluster.name", "stoneworld"));
    return channel;
  }

}
