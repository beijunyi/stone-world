package com.beijunyi.sw.output.models;

public class Palette {
  private final byte[] rgb;

  public Palette(byte[] rgb) {
    this.rgb = rgb;
  }

  public byte[] getRgb() {
    return rgb;
  }
}
