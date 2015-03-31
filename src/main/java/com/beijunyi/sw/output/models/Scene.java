package com.beijunyi.sw.output.models;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Scene implements KryoSerializable {

  private String name;
  private short east;
  private short south;
  private int[] tiles;
  private int[] objects;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public short getEast() {
    return east;
  }

  public void setEast(short east) {
    this.east = east;
  }

  public short getSouth() {
    return south;
  }

  public void setSouth(short south) {
    this.south = south;
  }

  public int[] getTiles() {
    return tiles;
  }

  public void setTiles(int[] tiles) {
    this.tiles = tiles;
  }

  public int[] getObjects() {
    return objects;
  }

  public void setObjects(int[] objects) {
    this.objects = objects;
  }

  @Override
  public void write(Kryo kryo, Output output) {
    byte[] nameBytes = name.getBytes();
    int nameLength = nameBytes.length;
    if(nameLength > Byte.MAX_VALUE)
      throw new IllegalArgumentException();
    output.writeByte(nameLength);
    output.writeBytes(nameBytes);
    output.writeShort(east);
    output.writeShort(south);
    output.writeInts(tiles);
    output.writeInts(objects);
  }

  @Override
  public void read(Kryo kryo, Input input) {
    int nameLength = input.readByte();
    byte[] nameBytes = new byte[nameLength];
    input.readBytes(nameBytes);
    name = new String(nameBytes);
    east = input.readShort();
    south = input.readShort();
    int total = east * south;
    tiles = input.readInts(total);
    objects = input.readInts(total);
  }
}
