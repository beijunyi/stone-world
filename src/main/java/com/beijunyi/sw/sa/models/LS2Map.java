package com.beijunyi.sw.sa.models;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.beijunyi.sw.utils.BitConverter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class LS2Map implements KryoSerializable {

  private static final Pattern NAME_PATTERN = Pattern.compile("^((\\p{L})*)\\|*\u0000*.*$");

  private short east;
  private short south;
  private int id;
  private String name;
  private int[] tiles;
  private int[] objects;

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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
    throw new UnsupportedOperationException();
  }

  @Override
  public void read(Kryo kryo, Input input) {
    String type = new String(input.readBytes(6));
    if(!type.equals("LS2MAP"))
      throw new IllegalArgumentException(type);
    setId(BitConverter.uint16be(input.readBytes(2)));
    try {
      String rawName = new String(input.readBytes(32), "gbk");
      Matcher matcher = NAME_PATTERN.matcher(rawName);
      if(matcher.matches())
        setName(matcher.group(1));
    } catch(UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    int east = BitConverter.uint16be(input.readBytes(2));
    int south = BitConverter.uint16be(input.readBytes(2));
    setEast((short)east);
    setSouth((short)south);
    int total = east * south;
    int[] tiles = new int[total];
    for(int i = 0; i < total; i++)
      tiles[i] = BitConverter.uint16be(input.readBytes(2));
    setTiles(tiles);
    int[] objects = new int[east * south];
    for(int i = 0; i < total; i++)
      objects[i] = BitConverter.uint16be(input.readBytes(2));
    setObjects(objects);
  }
}
