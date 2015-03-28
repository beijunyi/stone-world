package com.beijunyi.sw.sa.serializers;

import com.beijunyi.sw.sa.models.RealBlock;
import com.beijunyi.sw.utils.BitConverter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class RealBlockSerializer extends Serializer<RealBlock> {
  @Override
  public void write(Kryo kryo, Output output, RealBlock object) {
    throw new UnsupportedOperationException();
  }

  @Override
  public RealBlock read(Kryo kryo, Input input, Class<RealBlock> type) {
    RealBlock realBlock = new RealBlock();
    realBlock.setMagic(new String(input.readBytes(2)));
    realBlock.setMajor((byte) BitConverter.uint8(input.readByte()));
    realBlock.setMinor((byte) BitConverter.uint8(input.readByte()));  // 16, 15
    realBlock.setWidth((short) BitConverter.uint32le(input.readBytes(4)));
    realBlock.setHeight((short) BitConverter.uint32le(input.readBytes(4)));
    if(realBlock.getMajor() == 1) {
      realBlock.setSize((int)BitConverter.uint32le(input.readBytes(4)));
      realBlock.setData(input.readBytes(realBlock.getSize() - 16));
    } else {
      input.skip(4); // [36, -101, -125, 0]?
      realBlock.setData(input.readBytes(realBlock.getWidth() * realBlock.getHeight()));
    }

    return realBlock;
  }
}
