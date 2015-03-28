package com.beijunyi.sw.sa.models;

public class RealBlock {
  private String magic;
  private byte major;
  private byte minor;
  private short width;
  private short height;
  private int size;
  private byte[] data;

  public String getMagic() {
    return magic;
  }

  public void setMagic(String magic) {
    this.magic = magic;
  }

  public byte getMajor() {
    return major;
  }

  public void setMajor(byte major) {
    this.major = major;
  }

  public byte getMinor() {
    return minor;
  }

  public void setMinor(byte minor) {
    this.minor = minor;
  }

  public short getWidth() {
    return width;
  }

  public void setWidth(short width) {
    this.width = width;
  }

  public short getHeight() {
    return height;
  }

  public void setHeight(short height) {
    this.height = height;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }
}
