package com.beijunyi.sw.config;

import com.beijunyi.sw.model.ModelInitializer;
import com.esotericsoftware.kryo.Kryo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KryoConfig {

  @Bean(name = "KryoRef")
  public Kryo KryoRef() {
    Kryo kryo = new Kryo();
    ModelInitializer.registerModels(kryo);
    return kryo;
  }

}
