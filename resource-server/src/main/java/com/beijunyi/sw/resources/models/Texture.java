package com.beijunyi.sw.resources.models;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Texture implements KryoSerializable {

  private short x;
  private short y;
  private short width;
  private short height;
  private byte[] bitmap;

  public short getX() {
    return x;
  }

  public void setX(short x) {
    this.x = x;
  }

  public short getY() {
    return y;
  }

  public void setY(short y) {
    this.y = y;
  }

  public int getWidth() {
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

  public byte[] getBitmap() {
    return bitmap;
  }

  public void setBitmap(byte[] bitmap) {
    this.bitmap = bitmap;
  }

  @Override
  public void write(Kryo kryo, Output output) {
    output.writeShort(x);
    output.writeShort(y);
    output.writeShort(width);
    output.writeShort(height);
    output.writeBytes(bitmap);
  }

  @Override
  public void read(Kryo kryo, Input input) {
    x = input.readShort();
    y = input.readShort();
    width = input.readShort();
    height = input.readShort();
    bitmap = input.readBytes(width * height);
  }
}
