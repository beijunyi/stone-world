package com.beijunyi.sw.sa.models;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SprAdrn implements KryoSerializable {
  private List<SprAdrnBlock> sprAdrnBlocks;

  public List<SprAdrnBlock> getSprAdrnBlocks() {
    return sprAdrnBlocks;
  }

  public void setSprAdrnBlocks(List<SprAdrnBlock> sprAdrnBlocks) {
    this.sprAdrnBlocks = sprAdrnBlocks;
  }

  @Override
  public void write(Kryo kryo, Output output) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void read(Kryo kryo, Input input) {
    try {
      int remaining = input.available() / 12;
      setSprAdrnBlocks(new ArrayList<SprAdrnBlock>(remaining));
      while(remaining-- > 0) {
        getSprAdrnBlocks().add(kryo.readObject(input, SprAdrnBlock.class));
      }
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }
}
