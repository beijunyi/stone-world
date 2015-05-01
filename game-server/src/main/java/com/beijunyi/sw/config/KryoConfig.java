package com.beijunyi.sw.config;

import com.beijunyi.sw.message.MessageModelInitializer;
import com.beijunyi.sw.message.MessageSerializer;
import com.esotericsoftware.kryo.Kryo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KryoConfig {

  @Bean
  public MessageSerializer messageSerializer() {
    Kryo kryo = new Kryo();
    MessageModelInitializer.registerModels(kryo);
    return new MessageSerializer(kryo);
  }

}
