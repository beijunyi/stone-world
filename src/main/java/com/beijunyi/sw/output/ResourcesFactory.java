package com.beijunyi.sw.output;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.beijunyi.sw.config.custom.*;
import com.beijunyi.sw.sa.SaResourcesManager;
import com.beijunyi.sw.utils.ImageUtils;

@Named
@Singleton
public class ResourcesFactory {

  private final SaResourcesManager srm;


  @Inject
  public ResourcesFactory(SaResourcesManager srm) {
    this.srm = srm;
  }


  public byte[] createPalette(int id) {
    byte[] raw = srm.getPalet(id).getData();
    byte[] palette = new byte[256 * 4];
    System.arraycopy(ImageUtils.PALETTE_BEGIN, 0, palette, 0      , 16 * 4);
    System.arraycopy(ImageUtils.PALETTE_END  , 0, palette, 240 * 4, 16 * 4);
    int i = 0;
    while(i < 224) {
      palette[i * 4 + 64] = raw[i * 3 + 2]; // r
      palette[i * 4 + 65] = raw[i * 3 + 1]; // g
      palette[i * 4 + 66] = raw[i * 3    ]; // b
      palette[i * 4 + 67] = (byte) 255;     // a
      i++;
    }
    return palette;
  }

}
