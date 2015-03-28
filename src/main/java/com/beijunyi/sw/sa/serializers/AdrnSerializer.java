package com.beijunyi.sw.sa.serializers;


import java.util.ArrayList;

import com.beijunyi.sw.sa.models.Adrn;
import com.beijunyi.sw.sa.models.AdrnBlock;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class AdrnSerializer extends Serializer<Adrn> {
  @Override
  public void write(Kryo kryo, Output output, Adrn object) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Adrn read(Kryo kryo, Input input, Class<Adrn> type) {
    Adrn adrn = new Adrn();
    try {
      int remaining = input.available() / 80;
      adrn.setAdrnBlocks(new ArrayList<AdrnBlock>(remaining));
      while(remaining-- > 0) {
        adrn.getAdrnBlocks().add(kryo.readObject(input, AdrnBlock.class));
      }
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
    return adrn;
  }
}
