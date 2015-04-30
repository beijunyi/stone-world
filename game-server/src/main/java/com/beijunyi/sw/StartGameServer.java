package com.beijunyi.sw;

import com.beijunyi.sw.config.GameServerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StartGameServer {

  public static void main(String args[]) {
    try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(GameServerConfig.class)) {
      context.start();
      context.registerShutdownHook();
    }
  }

}
