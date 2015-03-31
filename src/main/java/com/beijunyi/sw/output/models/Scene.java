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
