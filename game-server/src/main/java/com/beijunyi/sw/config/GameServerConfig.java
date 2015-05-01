package com.beijunyi.sw.config;

import com.beijunyi.sw.AppConstants;
import com.beijunyi.sw.config.model.ServerProperties;
import org.jgroups.JChannel;
import org.springframework.context.annotation.*;

@Configuration
@Import({KryoConfig.class, WebMvcConfig.class})
@ComponentScan(basePackages = "com.beijunyi.sw")
public class GameServerConfig {

  @Bean
  public JChannel jChannel() throws Exception {
    JChannel channel = new JChannel();
    channel.connect(System.getProperty("cluster.name", AppConstants.DEFAULT_CLUSTER));
    return channel;
  }

  @Bean
  public ServerProperties serverProperties() {
    return new ServerProperties(System.getProperty("server.ip"), Integer.valueOf(System.getProperty("server.port")));
  }

}
