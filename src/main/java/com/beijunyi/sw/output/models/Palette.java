package com.beijunyi.sw.output.models;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Palette implements KryoSerializable {

  private byte[] rgba;

  public Palette() {
    rgba = new byte[256 * 4];
  }

  public byte[] getRgba() {
    return rgba;
  }

  public void setRgba(byte[] rgba) {
    this.rgba = rgba;
  }

  @Override
  public void write(Kryo kryo, Output output) {
    output.write(rgba);
  }

  @Override
  public void read(Kryo kryo, Input input) {
    input.readBytes(rgba);
  }
}
