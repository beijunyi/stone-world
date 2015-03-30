package com.beijunyi.sw.output;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.beijunyi.sw.config.Settings;
import com.beijunyi.sw.output.models.Palette;
import com.beijunyi.sw.sa.SaResourcesManager;
import com.beijunyi.sw.sa.models.Palet;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

@Named
@Singleton
public class PaletteManager {

  private static byte[] PALETTE_BEGIN =
    new byte[] {
                 (byte)   0, (byte)   0, (byte)   0, (byte) 0,   // ok
                 (byte) 132, (byte)   4, (byte)   0, (byte) 255, // changed from (byte) 128, (byte)   0, (byte)   0, (byte) 255
                 (byte)   0, (byte) 134, (byte)   0, (byte) 255, // changed from (byte)   0, (byte) 128, (byte)   0, (byte) 255
                 (byte) 132, (byte) 134, (byte)   0, (byte) 255, // changed from (byte) 128, (byte) 128, (byte)   0, (byte) 255
                 (byte)   0, (byte)   4, (byte) 132, (byte) 255, // changed from (byte)   0, (byte)   0, (byte) 128, (byte) 255
                 (byte) 132, (byte)   4, (byte) 132, (byte) 255, // changed from (byte) 128, (byte)   0, (byte) 128, (byte) 255
                 (byte)   0, (byte) 134, (byte) 132, (byte) 255, // changed from (byte)   0, (byte) 128, (byte) 128, (byte) 255
                 (byte) 192, (byte) 192, (byte) 192, (byte) 255, // changed from (byte)   0, (byte) 192, (byte) 192, (byte) 255
                 (byte) 198, (byte) 223, (byte) 198, (byte) 255, // changed from (byte) 192, (byte) 202, (byte) 192, (byte) 255
                 (byte) 165, (byte) 207, (byte) 247, (byte) 255, // changed from (byte) 166, (byte) 202, (byte) 240, (byte) 255
                 (byte) 222, (byte)   4, (byte)   0, (byte) 255, // changed from (byte) 222, (byte)   0, (byte)   0, (byte) 255
                 (byte) 255, (byte)  93, (byte)   0, (byte) 255, // changed from (byte) 255, (byte)  95, (byte)   0, (byte) 255
                 (byte) 255, (byte) 255, (byte) 165, (byte) 255, // changed from (byte) 255, (byte) 255, (byte) 160, (byte) 255
                 (byte)   0, (byte)  93, (byte) 214, (byte) 255, // changed from (byte)   0, (byte)  95, (byte) 210, (byte) 255
                 (byte)  82, (byte) 215, (byte) 255, (byte) 255, // changed from (byte)  80, (byte) 210, (byte) 255, (byte) 255
                 (byte)  41, (byte) 231, (byte)  41, (byte) 255  // changed from (byte)  40, (byte) 225, (byte)  40, (byte) 255
    };

  private static byte[] PALETTE_END =
    new byte[] {
                 (byte) 247, (byte) 199, (byte) 148, (byte) 255, // changed from (byte) 245, (byte) 195, (byte) 150, (byte) 255
                 (byte) 231, (byte) 166, (byte)  90, (byte) 255, // changed from (byte)  30, (byte) 160, (byte)  95, (byte) 255
                 (byte) 198, (byte) 125, (byte)  66, (byte) 255, // changed from (byte) 195, (byte) 125, (byte)  70, (byte) 255
                 (byte) 156, (byte)  85, (byte)  24, (byte) 255, // changed from (byte) 155, (byte)  85, (byte)  30, (byte) 255
                 (byte)  66, (byte)  69, (byte)  49, (byte) 255, // changed from (byte)  70, (byte)  65, (byte)  55, (byte) 255
                 (byte)  41, (byte)  36, (byte)  24, (byte) 255, // changed from (byte)  40, (byte)  35, (byte)  30, (byte) 255
                 (byte) 255, (byte) 255, (byte) 247, (byte) 255, // changed from (byte) 255, (byte) 251, (byte) 240, (byte) 255
                 (byte) 165, (byte) 166, (byte) 165, (byte) 255, // changed from (byte)  58, (byte) 110, (byte) 165, (byte) 255
                 (byte) 132, (byte) 134, (byte) 132, (byte) 255, // changed from (byte) 128, (byte) 128, (byte) 128, (byte) 255
                 (byte) 255, (byte)   4, (byte)   0, (byte) 255, // changed from (byte) 255, (byte)   0, (byte)   0, (byte) 255
                 (byte)   0, (byte) 255, (byte)   0, (byte) 255, // ok
                 (byte) 255, (byte) 255, (byte)   0, (byte) 255, // ok
                 (byte)   0, (byte)   4, (byte) 255, (byte) 255, // changed from (byte)   0, (byte)   0, (byte) 255, (byte) 255
                 (byte) 255, (byte)   4, (byte) 255, (byte) 255, // changed from (byte) 255, (byte) 128, (byte) 255, (byte) 255
                 (byte)   0, (byte) 255, (byte) 255, (byte) 255, // ok
                 (byte) 255, (byte) 255, (byte) 255, (byte) 255  // changed from (byte) 255, (byte) 225, (byte) 255, (byte) 255
    };

  private final SaResourcesManager srm;
  private final Kryo kryo;
  private final Path paletteDir;

  private Map<Integer, Palette> palettes = new HashMap<>();

  @Inject
  public PaletteManager(SaResourcesManager srm, Kryo kryo, Settings settings) throws IOException {
    this.srm = srm;
    this.kryo = kryo;
    paletteDir = settings.getOutputPath().resolve("palettes");
    Files.createDirectories(paletteDir);
  }

  private Path getOutputPalettePath(int id) {
    return paletteDir.resolve(id + ".bin");
  }

  public Palette getPalette(int id) {
    Palette palette = palettes.get(id);
    if(palette == null) {
      synchronized(this) {
        // try again
        palette = palettes.get(id);
        if(palette != null)
          return palette;

        Path outputPalettePath = getOutputPalettePath(id);
        if(Files.exists(outputPalettePath)) {
          try(InputStream input = Files.newInputStream(outputPalettePath)) {
            palette = kryo.readObject(new Input(input), Palette.class);
          } catch(IOException e) {
            throw new RuntimeException("Could not read " + outputPalettePath, e);
          }
        } else {
          palette = createPalette(srm.getPalet(id));
          try(OutputStream output = Files.newOutputStream(outputPalettePath)) {
            kryo.writeObject(new Output(output), palette);
          } catch(IOException e) {
            throw new RuntimeException("Could not write " + outputPalettePath, e);
          }
        }
        palettes.put(id, palette);
      }
    }
    return palette;
  }

  private static Palette createPalette(Palet palet) {
    byte[] raw = palet.getData();
    byte[] rgba = new byte[256 * 4];
    System.arraycopy(PALETTE_BEGIN, 0, rgba, 0      , 16 * 4);
    System.arraycopy(PALETTE_END  , 0, rgba, 240 * 4, 16 * 4);
    int i = 0;
    while(i < 224) {
      rgba[i * 4 + 64] = raw[i * 3 + 2]; // r
      rgba[i * 4 + 65] = raw[i * 3 + 1]; // g
      rgba[i * 4 + 66] = raw[i * 3    ]; // b
      rgba[i * 4 + 67] = (byte) 255;     // a
      i++;
    }
    Palette palette = new Palette();
    palette.setRgba(rgba);
    return palette;
  }

}
