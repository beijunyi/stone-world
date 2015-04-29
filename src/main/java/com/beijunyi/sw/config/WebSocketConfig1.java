package com.beijunyi.sw.config;

import com.beijunyi.sw.ws.WebSocketEndPoint1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig1 {

  @Bean
  public WebSocketEndPoint1 webSocketEndPoint() {
    return new WebSocketEndPoint1();
  }
}
