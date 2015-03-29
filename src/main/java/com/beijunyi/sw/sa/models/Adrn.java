package com.beijunyi.sw.sa.models;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Adrn implements KryoSerializable {
  private List<AdrnBlock> adrnBlocks;

  public List<AdrnBlock> getAdrnBlocks() {
    return adrnBlocks;
  }

  public void setAdrnBlocks(List<AdrnBlock> adrnBlocks) {
    this.adrnBlocks = adrnBlocks;
  }

  @Override
  public void write(Kryo kryo, Output output) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void read(Kryo kryo, Input input) {
    try {
      int remaining = input.available() / 80;
      setAdrnBlocks(new ArrayList<AdrnBlock>(remaining));
      while(remaining-- > 0) {
        getAdrnBlocks().add(kryo.readObject(input, AdrnBlock.class));
      }
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }
}
