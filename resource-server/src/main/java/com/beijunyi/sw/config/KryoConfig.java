package com.beijunyi.sw.config;

import com.beijunyi.sw.message.MessageSerializer;
import com.esotericsoftware.kryo.Kryo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KryoConfig {

  @Bean
  public Kryo kryo() {
    Kryo kryo = new Kryo();
    kryo.setReferences(false);
    return kryo;
  }

  @Bean
  public MessageSerializer messageSerializer() {
    return new MessageSerializer(kryo());
  }

}
