package com.beijunyi.sw.output.models;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Animation implements KryoSerializable {

  private short duration;
  private List<AnimationFrame> frames;

  public int getDuration() {
    return duration;
  }

  public void setDuration(short duration) {
    this.duration = duration;
  }

  public List<AnimationFrame> getFrames() {
    return frames;
  }

  public void setFrames(List<AnimationFrame> frames) {
    this.frames = frames;
  }

  @Override
  public void write(Kryo kryo, Output output) {
    output.writeShort(duration);
    output.writeByte(frames.size());
    for(AnimationFrame frame : frames) {
      kryo.writeObject(output, frame);
    }
  }

  @Override
  public void read(Kryo kryo, Input input) {
    duration = input.readShort();
    byte size = input.readByte();
    frames = new ArrayList<>(size);
    for(byte i = 0; i < size; i++) {
      frames.add(kryo.readObject(input, AnimationFrame.class));
    }
  }
}
