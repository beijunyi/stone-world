package com.beijunyi.sw.sa.models;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Palet implements KryoSerializable {

  private byte[] data;

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  @Override
  public void write(Kryo kryo, Output output) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void read(Kryo kryo, Input input) {
    data = input.readBytes(224 * 3);
  }
}
