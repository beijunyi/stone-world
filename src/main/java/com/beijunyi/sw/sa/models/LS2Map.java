package com.beijunyi.sw.sa.models;

public class LS2Map {
  private short east;
  private short south;
  private int id;
  private String name;
  private int[] tiles;
  private int[] objects;

  public short getEast() {
    return east;
  }

  public void setEast(short east) {
    this.east = east;
  }

  public short getSouth() {
    return south;
  }

  public void setSouth(short south) {
    this.south = south;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int[] getTiles() {
    return tiles;
  }

  public void setTiles(int[] tiles) {
    this.tiles = tiles;
  }

  public int[] getObjects() {
    return objects;
  }

  public void setObjects(int[] objects) {
    this.objects = objects;
  }
}
