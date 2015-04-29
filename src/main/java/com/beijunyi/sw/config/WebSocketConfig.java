package com.beijunyi.sw.config;

import com.beijunyi.sw.ws.WebSocketEndPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig {

  @Bean
  public WebSocketEndPoint webSocketEndPoint() {
    return new WebSocketEndPoint();
  }
}
