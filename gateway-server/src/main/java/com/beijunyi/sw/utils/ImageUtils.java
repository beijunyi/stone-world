package com.beijunyi.sw.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {

  public static BufferedImage createImage(byte[] bitmap, int width, int height, byte[] palette) {
    int[] colors = new int[256];
    for(int i = 0; i < 256; i++) {
      int r = BitConverter.uint8(palette[i * 4]);
      int g = BitConverter.uint8(palette[i * 4 + 1]);
      int b = BitConverter.uint8(palette[i * 4 + 2]);
      colors[i] = new Color(r, g, b).getRGB();
    }

    BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    int x = 0;
    int y = 0;
    int pos = 0;
    int bitmapLength = bitmap.length;
    int pix;
    while(pos < bitmapLength) {
      pix = BitConverter.uint8(bitmap[pos]);
      if(pix != 0)
        ret.setRGB(x, y, colors[pix]);
      x++;
      pos++;
      if(x == width) {
        x = 0;
        y++;
      }
    }
    return ret;
  }

}
