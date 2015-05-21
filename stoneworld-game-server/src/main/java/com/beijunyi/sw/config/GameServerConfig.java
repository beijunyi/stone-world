package com.beijunyi.sw.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
          GameServerMainConfig.class,
          GameServerMessageConfig.class,
          WebMvcConfig.class
})
@ComponentScan(basePackages = "com.beijunyi.sw")
public class GameServerConfig {

}
