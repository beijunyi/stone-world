package com.beijunyi.sw.sa.serializers;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.beijunyi.sw.sa.models.LS2Map;
import com.beijunyi.sw.utils.BitConverter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class LS2MapSerializer extends Serializer<LS2Map> {

  private static final Pattern NAME_PATTERN = Pattern.compile("^((\\p{L})*)\\|*\u0000*.*$");

  @Override
  public void write(Kryo kryo, Output output, LS2Map object) {
    throw new UnsupportedOperationException();
  }

  @Override
  public LS2Map read(Kryo kryo, Input input, Class<LS2Map> type) {
    LS2Map ls2Map = new LS2Map();
    input.skip(6);
    ls2Map.setId(BitConverter.uint16be(input.readBytes(2)));
    try {
      Matcher matcher = NAME_PATTERN.matcher(new String(input.readBytes(32), "gbk"));
      if(matcher.matches())
        ls2Map.setName(matcher.group(1));
    } catch(UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    int east = BitConverter.uint16be(input.readBytes(2));
    int south = BitConverter.uint16be(input.readBytes(2));
    ls2Map.setEast((short) east);
    ls2Map.setSouth((short) south);
    int total = east * south;
    int[] tiles = new int[total];
    for(int i = 0; i < total; i++)
      tiles[i] = BitConverter.uint16be(input.readBytes(2));
    ls2Map.setTiles(tiles);
    int[] objects = new int[east * south];
    for(int i = 0; i < total; i++)
      objects[i] = BitConverter.uint16be(input.readBytes(2));
    ls2Map.setObjects(objects);
    return ls2Map;
  }
}
