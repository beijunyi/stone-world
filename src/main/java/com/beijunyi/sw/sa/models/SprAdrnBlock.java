package com.beijunyi.sw.sa.models;

public class SprAdrnBlock {
  private int id;
  private long address;
  private byte actions;
  private int sound;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public long getAddress() {
    return address;
  }

  public void setAddress(long address) {
    this.address = address;
  }

  public byte getActions() {
    return actions;
  }

  public void setActions(byte actions) {
    this.actions = actions;
  }

  public int getSound() {
    return sound;
  }

  public void setSound(int sound) {
    this.sound = sound;
  }
}
