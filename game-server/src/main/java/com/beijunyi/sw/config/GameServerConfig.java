package com.beijunyi.sw.config;

import com.beijunyi.sw.AppConstants;
import com.beijunyi.sw.config.model.GameServerProperties;
import org.jgroups.JChannel;
import org.springframework.context.annotation.*;

@Configuration
@Import({WebMvcConfig.class})
@ComponentScan(basePackages = "com.beijunyi.sw")
public class GameServerConfig {

  @Bean
  public JChannel jChannel() throws Exception {
    JChannel channel = new JChannel();
    channel.connect(System.getProperty("cluster.name", AppConstants.DEFAULT_CLUSTER));
    return channel;
  }

  @Bean
  public GameServerProperties serverProperties() {
    String ip = System.getProperty("server.ip");
    int port = Integer.valueOf(System.getProperty("server.port"));
    String name = System.getProperty("server.name", ip + ":" + port);
    return new GameServerProperties(name, ip, port);
  }

}
