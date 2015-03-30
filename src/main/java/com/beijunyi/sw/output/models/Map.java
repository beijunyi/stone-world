package com.beijunyi.sw.output.models;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Map implements KryoSerializable {

  private String name;

  @Override
  public void write(Kryo kryo, Output output) {
  }

  @Override
  public void read(Kryo kryo, Input input) {

  }
}
