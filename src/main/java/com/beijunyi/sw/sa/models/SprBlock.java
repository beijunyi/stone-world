package com.beijunyi.sw.sa.models;

import com.beijunyi.sw.utils.BitConverter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SprBlock implements KryoSerializable{
  private byte direction;
  private byte action;
  private short duration;
  private byte length;
  private int[] images;
  private short[] unknown;
  private short[] impactAudio;
  private short[] dodgeAudio;

  public byte getDirection() {
    return direction;
  }

  public void setDirection(byte direction) {
    this.direction = direction;
  }

  public byte getAction() {
    return action;
  }

  public void setAction(byte action) {
    this.action = action;
  }

  public short getDuration() {
    return duration;
  }

  public void setDuration(short duration) {
    this.duration = duration;
  }

  public byte getLength() {
    return length;
  }

  public void setLength(byte length) {
    this.length = length;
  }

  public int[] getImages() {
    return images;
  }

  public void setImages(int[] images) {
    this.images = images;
  }

  public short[] getUnknown() {
    return unknown;
  }

  public void setUnknown(short[] unknown) {
    this.unknown = unknown;
  }

  public short[] getImpactAudio() {
    return impactAudio;
  }

  public void setImpactAudio(short[] impactAudio) {
    this.impactAudio = impactAudio;
  }

  public short[] getDodgeAudio() {
    return dodgeAudio;
  }

  public void setDodgeAudio(short[] dodgeAudio) {
    this.dodgeAudio = dodgeAudio;
  }

  @Override
  public void write(Kryo kryo, Output output) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void read(Kryo kryo, Input input) {
    setDirection((byte)BitConverter.uint16le(input.readBytes(2)));
    setAction((byte)BitConverter.uint16le(input.readBytes(2)));
    setDuration((short)BitConverter.uint32le(input.readBytes(4)));
    setLength((byte)BitConverter.uint32le(input.readBytes(4)));
    setImages(new int[length]);
    setUnknown(new short[length]);
    setImpactAudio(new short[length]);
    setDodgeAudio(new short[length]);
    for(int i = 0; i < getLength(); i++) {
      getImages()[i] = (int) BitConverter.uint32le(input.readBytes(4));
      getUnknown()[i] = (short) BitConverter.uint32le(input.readBytes(4));
      getImpactAudio()[i] = BitConverter.uint8(input.readByte());
      getDodgeAudio()[i] = BitConverter.uint8(input.readByte());
    }
  }
}
