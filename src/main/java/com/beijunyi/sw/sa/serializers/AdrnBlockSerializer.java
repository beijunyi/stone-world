package com.beijunyi.sw.sa.serializers;

import com.beijunyi.sw.sa.models.AdrnBlock;
import com.beijunyi.sw.utils.BitConverter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class AdrnBlockSerializer extends Serializer<AdrnBlock> {

  @Override
  public void write(Kryo kryo, Output output, AdrnBlock object) {
    throw new UnsupportedOperationException();
  }

  @Override
  public AdrnBlock read(Kryo kryo, Input input, Class<AdrnBlock> type) {
    AdrnBlock adrnBlock = new AdrnBlock();
    adrnBlock.setId((int) BitConverter.uint32le(input.readBytes(4)));
    adrnBlock.setAddress(BitConverter.uint32le(input.readBytes(4)));
    adrnBlock.setSize((int) BitConverter.uint32le(input.readBytes(4)));
    adrnBlock.setxOffset((short)BitConverter.int32le(input.readBytes(4)));
    adrnBlock.setyOffset((short)BitConverter.int32le(input.readBytes(4)));
    adrnBlock.setWidth((short) BitConverter.uint32le(input.readBytes(4)));
    adrnBlock.setHeight((short) BitConverter.uint32le(input.readBytes(4)));
    adrnBlock.setEast((byte) BitConverter.uint8(input.readByte()));
    adrnBlock.setSouth((byte) BitConverter.uint8(input.readByte()));
    adrnBlock.setPath((byte) BitConverter.uint8(input.readByte()));
    adrnBlock.setReference(new String(input.readBytes(45)));
    adrnBlock.setMap((int) BitConverter.uint32le(input.readBytes(4)));
    return adrnBlock;
  }
}
