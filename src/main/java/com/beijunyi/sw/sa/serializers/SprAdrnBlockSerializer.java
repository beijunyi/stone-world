package com.beijunyi.sw.sa.serializers;

import com.beijunyi.sw.sa.models.SprAdrnBlock;
import com.beijunyi.sw.utils.BitConverter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SprAdrnBlockSerializer extends Serializer<SprAdrnBlock> {
  @Override
  public void write(Kryo kryo, Output output, SprAdrnBlock object) {
    throw new UnsupportedOperationException();
  }

  @Override
  public SprAdrnBlock read(Kryo kryo, Input input, Class<SprAdrnBlock> type) {
    SprAdrnBlock sprAdrnBlock = new SprAdrnBlock();
    sprAdrnBlock.setId((int) BitConverter.uint32le(input.readBytes(4)));
    sprAdrnBlock.setAddress(BitConverter.uint32le(input.readBytes(4)));
    sprAdrnBlock.setActions((byte) BitConverter.uint16le(input.readBytes(2)));
    sprAdrnBlock.setSound(BitConverter.uint16le(input.readBytes(2)));
    return sprAdrnBlock;
  }
}
