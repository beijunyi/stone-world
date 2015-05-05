package com.beijunyi.sw.sa.models;

import com.beijunyi.sw.utils.BitConverter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class RealBlock implements KryoSerializable {
  private String magic;
  private byte major;
  private byte minor;
  private short width;
  private short height;
  private int size;
  private byte[] data;

  public String getMagic() {
    return magic;
  }

  public void setMagic(String magic) {
    this.magic = magic;
  }

  public byte getMajor() {
    return major;
  }

  public void setMajor(byte major) {
    this.major = major;
  }

  public byte getMinor() {
    return minor;
  }

  public void setMinor(byte minor) {
    this.minor = minor;
  }

  public short getWidth() {
    return width;
  }

  public void setWidth(short width) {
    this.width = width;
  }

  public short getHeight() {
    return height;
  }

  public void setHeight(short height) {
    this.height = height;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

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
    setMagic(new String(input.readBytes(2)));
    setMajor((byte) BitConverter.uint8(input.readByte()));
    setMinor((byte) BitConverter.uint8(input.readByte()));  // 16, 15
    setWidth((short) BitConverter.uint32le(input.readBytes(4)));
    setHeight((short) BitConverter.uint32le(input.readBytes(4)));
    if(major == 1) {
      setSize((int)BitConverter.uint32le(input.readBytes(4)));
      setData(input.readBytes(getSize() - 16));
    } else {
      input.skip(4); // [36, -101, -125, 0]?
      setData(input.readBytes(getWidth() * getHeight()));
    }
  }
}
