package com.beijunyi.sw.resources.models;

import com.beijunyi.sw.utils.BitConverter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class AnimationFrame implements KryoSerializable {
  private short audio;
  private Texture texture;

  public Texture getTexture() {
    return texture;
  }

  public void setTexture(Texture texture) {
    this.texture = texture;
  }

  public short getAudio() {
    return audio;
  }

  public void setAudio(short audio) {
    this.audio = audio;
  }

  @Override
  public void write(Kryo kryo, Output output) {
    output.writeByte(audio);
    kryo.writeObject(output, texture);
  }

  @Override
  public void read(Kryo kryo, Input input) {
    this.audio = BitConverter.uint8(input.readByte());
    this.texture = kryo.readObject(input, Texture.class);
  }
}
