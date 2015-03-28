package com.beijunyi.sw.sa.serializers;

import com.beijunyi.sw.sa.models.SprBlock;
import com.beijunyi.sw.utils.BitConverter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SprBlockSerializer extends Serializer<SprBlock> {
  @Override
  public void write(Kryo kryo, Output output, SprBlock object) {
    throw new UnsupportedOperationException();
  }

  @Override
  public SprBlock read(Kryo kryo, Input input, Class<SprBlock> type) {
    SprBlock sprBlock = new SprBlock();
    sprBlock.setDirection((byte) BitConverter.uint16le(input.readBytes(2)));
    sprBlock.setAction((byte) BitConverter.uint16le(input.readBytes(2)));
    sprBlock.setDuration((short) BitConverter.uint32le(input.readBytes(4)));
    sprBlock.setLength((byte) BitConverter.uint32le(input.readBytes(4)));
    sprBlock.setImages(new int[sprBlock.getLength()]);
    sprBlock.setUnknown(new short[sprBlock.getLength()]);
    sprBlock.setImpactAudio(new short[sprBlock.getLength()]);
    sprBlock.setDodgeAudio(new short[sprBlock.getLength()]);
    for(int i = 0; i < sprBlock.getLength(); i++) {
      sprBlock.getImages()[i] = (int) BitConverter.uint32le(input.readBytes(4));
      sprBlock.getUnknown()[i] = (short) BitConverter.uint32le(input.readBytes(4));
      sprBlock.getImpactAudio()[i] = BitConverter.uint8(input.readByte());
      sprBlock.getDodgeAudio()[i] = BitConverter.uint8(input.readByte());
    }
    return sprBlock;
  }
}
