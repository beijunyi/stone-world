package com.beijunyi.sw.sa.models;

public class SprBlock {
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
}
