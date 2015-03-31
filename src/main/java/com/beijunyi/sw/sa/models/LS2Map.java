package com.beijunyi.sw.sa.models;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.beijunyi.sw.utils.BitConverter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.Serializer;
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

  public short getSouth() {
    return south;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int[] getTiles() {
    return tiles;
  }

  public int[] getObjects() {
    return objects;
  }

  @Override
  public void write(Kryo kryo, Output output) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void read(Kryo kryo, Input input) {
    input.skip(6);
    id = BitConverter.uint16be(input.readBytes(2));
    try {
      String rawName = new String(input.readBytes(32), "gbk");
      Matcher matcher = NAME_PATTERN.matcher(rawName);
      if(matcher.matches())
        name = matcher.group(1);
    } catch(UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    east = (short) BitConverter.uint16be(input.readBytes(2));
    south = (short) BitConverter.uint16be(input.readBytes(2));
    int total = east * south;
    tiles = new int[total];
    for(int i = 0; i < total; i++)
      tiles[i] = BitConverter.uint16be(input.readBytes(2));
    objects = new int[east * south];
    for(int i = 0; i < total; i++)
      objects[i] = BitConverter.uint16be(input.readBytes(2));
  }

  public static class MapHeaderSerializer extends Serializer<LS2Map> {

    @Override
    public void write(Kryo kryo, Output output, LS2Map object) {
      throw new UnsupportedOperationException();
    }

    @Override
    public LS2Map read(Kryo kryo, Input input, Class<LS2Map> type) {
      String sig = new String(input.readBytes(6));
      if(!sig.equals("LS2MAP"))
        throw new IllegalArgumentException(sig);
      LS2Map map = new LS2Map();
      map.id = BitConverter.uint16be(input.readBytes(2));
      return map;
    }
  }
}
