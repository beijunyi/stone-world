package com.beijunyi.sw.config;

import javax.inject.Inject;

import com.beijunyi.sw.GameServerConstants;
import com.beijunyi.sw.websocket.GameWebSocketService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebMvcConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

  @Inject
  private GameWebSocketService gameWebSocketService;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(gameWebSocketService, GameServerConstants.GAME_SERVER_URL_PATH);
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

}
