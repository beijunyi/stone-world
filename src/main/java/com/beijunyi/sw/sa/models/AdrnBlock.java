package com.beijunyi.sw.sa.models;

public class AdrnBlock {
  private int id;
  private long address;
  private int size;
  private short xOffset;
  private short yOffset;
  private short width;
  private short height;
  private byte east;
  private byte south;
  private byte path;
  private String reference;
  private int map;

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

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public short getxOffset() {
    return xOffset;
  }

  public void setxOffset(short xOffset) {
    this.xOffset = xOffset;
  }

  public short getyOffset() {
    return yOffset;
  }

  public void setyOffset(short yOffset) {
    this.yOffset = yOffset;
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

  public byte getEast() {
    return east;
  }

  public void setEast(byte east) {
    this.east = east;
  }

  public byte getSouth() {
    return south;
  }

  public void setSouth(byte south) {
    this.south = south;
  }

  public byte getPath() {
    return path;
  }

  public void setPath(byte path) {
    this.path = path;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public int getMap() {
    return map;
  }

  public void setMap(int map) {
    this.map = map;
  }
}
