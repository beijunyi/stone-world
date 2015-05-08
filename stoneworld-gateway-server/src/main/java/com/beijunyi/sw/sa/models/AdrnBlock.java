package com.beijunyi.sw.sa.models;

import com.beijunyi.sw.utils.BitConverter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class AdrnBlock implements KryoSerializable {
  private int id;
  private long address;
  private int size;
  private short xOffset;
  private short yOffset;
  private short width;
  private short height;
  private byte east;
  private byte south;
  private byte path;
  private String reference;
  private int map;

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

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public short getxOffset() {
    return xOffset;
  }

  public void setxOffset(short xOffset) {
    this.xOffset = xOffset;
  }

  public short getyOffset() {
    return yOffset;
  }

  public void setyOffset(short yOffset) {
    this.yOffset = yOffset;
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

  public byte getEast() {
    return east;
  }

  public void setEast(byte east) {
    this.east = east;
  }

  public byte getSouth() {
    return south;
  }

  public void setSouth(byte south) {
    this.south = south;
  }

  public byte getPath() {
    return path;
  }

  public void setPath(byte path) {
    this.path = path;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public int getMap() {
    return map;
  }

  public void setMap(int map) {
    this.map = map;
  }

  @Override
  public void write(Kryo kryo, Output output) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void read(Kryo kryo, Input input) {
    setId((int) BitConverter.uint32le(input.readBytes(4)));
    setAddress(BitConverter.uint32le(input.readBytes(4)));
    setSize((int) BitConverter.uint32le(input.readBytes(4)));
    setxOffset((short)BitConverter.int32le(input.readBytes(4)));
    setyOffset((short)BitConverter.int32le(input.readBytes(4)));
    setWidth((short) BitConverter.uint32le(input.readBytes(4)));
    setHeight((short) BitConverter.uint32le(input.readBytes(4)));
    setEast((byte) BitConverter.uint8(input.readByte()));
    setSouth((byte) BitConverter.uint8(input.readByte()));
    setPath((byte) BitConverter.uint8(input.readByte()));
    setReference(new String(input.readBytes(45)));
    setMap((int) BitConverter.uint32le(input.readBytes(4)));
  }
}
