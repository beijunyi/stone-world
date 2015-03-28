package com.beijunyi.sw.sa.serializers;


import java.util.ArrayList;

import com.beijunyi.sw.sa.models.SprAdrn;
import com.beijunyi.sw.sa.models.SprAdrnBlock;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SprAdrnSerializer extends Serializer<SprAdrn> {
  @Override
  public void write(Kryo kryo, Output output, SprAdrn object) {
    throw new UnsupportedOperationException();
  }

  @Override
  public SprAdrn read(Kryo kryo, Input input, Class<SprAdrn> type) {
    SprAdrn sprAdrn = new SprAdrn();
    try {
      int remaining = input.available() / 12;
      sprAdrn.setSprAdrnBlocks(new ArrayList<SprAdrnBlock>(remaining));
      while(remaining-- > 0) {
        sprAdrn.getSprAdrnBlocks().add(kryo.readObject(input, SprAdrnBlock.class));
      }
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
    return sprAdrn;
  }
}
