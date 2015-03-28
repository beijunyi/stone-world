package com.beijunyi.sw.config;

import com.beijunyi.sw.sa.models.*;
import com.beijunyi.sw.sa.serializers.*;
import com.esotericsoftware.kryo.Kryo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KryoConfig {

  @Bean
  public Kryo getKryo() {
    Kryo kryo = new Kryo();
    kryo.setReferences(false);
    kryo.register(AdrnBlock.class, new AdrnBlockSerializer());
    kryo.register(Adrn.class, new AdrnSerializer());
    kryo.register(SprAdrnBlock.class, new SprAdrnBlockSerializer());
    kryo.register(SprAdrn.class, new SprAdrnSerializer());
    kryo.register(RealBlock.class, new RealBlockSerializer());
    kryo.register(SprBlock.class, new SprBlockSerializer());
    kryo.register(LS2Map.class, new LS2MapSerializer());
    return kryo;
  }

}
