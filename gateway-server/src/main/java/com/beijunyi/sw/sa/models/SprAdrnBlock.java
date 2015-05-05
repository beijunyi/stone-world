package com.beijunyi.sw.sa.models;

import com.beijunyi.sw.utils.BitConverter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SprAdrnBlock implements KryoSerializable {
  private int id;
  private long address;
  private byte actions;
  private int sound;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public long getAddress() {
    return address;
  }

  public void setAddress(long address) {
    this.address = address;
  }

  public byte getActions() {
    return actions;
  }

  public void setActions(byte actions) {
    this.actions = actions;
  }

  public int getSound() {
    return sound;
  }

  public void setSound(int sound) {
    this.sound = sound;
  }

  @Override
  public void write(Kryo kryo, Output output) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void read(Kryo kryo, Input input) {
    setId((int) BitConverter.uint32le(input.readBytes(4)));
    setAddress(BitConverter.uint32le(input.readBytes(4)));
    setActions((byte) BitConverter.uint16le(input.readBytes(2)));
    setSound(BitConverter.uint16le(input.readBytes(2)));
  }
}
