package com.beijunyi.sw.resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.config.custom.Settings;
import com.beijunyi.sw.resources.models.Texture;
import com.beijunyi.sw.sa.SaResourcesManager;
import com.beijunyi.sw.sa.models.AdrnBlock;
import com.beijunyi.sw.sa.models.RealBlock;
import com.beijunyi.sw.utils.BitConverter;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

@Named
public class TextureManager {

  private final SaResourcesManager srm;
  private final Kryo kryo;
  private final Path texturesDir;
  private final Object[] locks;

  private Map<Integer, byte[]> textures = new HashMap<>();

  @Inject
  public TextureManager(Settings settings, SaResourcesManager srm, Kryo kryo) throws IOException {
    this.srm = srm;
    this.kryo = kryo;
    texturesDir = settings.getOutputPath().resolve("textures");
    Files.createDirectories(texturesDir);
    locks = new Object[srm.getMaxAdrnId() + 1];
    for(int i = 0; i < locks.length; i++)
      locks[i] = new Object();
  }

  private Path getOutputTexturePath(int id) {
    return texturesDir.resolve(id + ".bin");
  }

  public byte[] getTextureData(int id) {
    if(id < 0 || id >= locks.length)
      throw new IllegalArgumentException("Invalid id: " + id);
    byte[] data = textures.get(id);
    if(data == null) {
      synchronized(this) {
        // try again
        data = textures.get(id);
        if(data != null)
          return data;

        Path outputTexturePath = getOutputTexturePath(id);
        if(Files.exists(outputTexturePath)) {
          try {
            data = Files.readAllBytes(outputTexturePath);
          } catch(IOException e) {
            throw new RuntimeException("Could not read " + outputTexturePath, e);
          }
        } else {
          AdrnBlock adrn = srm.getAdrnBlock(id);
          RealBlock real = srm.getRealBlock(adrn.getAddress(), adrn.getSize());
          Texture texture = createTexture(adrn, real);
          ByteArrayOutputStream stream = new ByteArrayOutputStream();
          Output output = new Output(stream);
          kryo.writeObject(output, texture);
          output.flush();
          data = stream.toByteArray();
          try {
            Files.write(outputTexturePath, data);
          } catch(IOException e) {
            throw new RuntimeException("Could not write " + outputTexturePath, e);
          }
        }
        textures.put(id, data);
      }
    }
    return data;
  }

  private static Texture createTexture(AdrnBlock adrn, RealBlock real) {
    Texture texture = new Texture();
    texture.setX(adrn.getxOffset());
    texture.setY(adrn.getyOffset());
    texture.setWidth(adrn.getWidth());
    texture.setHeight(adrn.getHeight());
    byte[] buf;
    if(real.getMajor() == 1) {
      buf = new byte[adrn.getWidth() * adrn.getHeight()];
      decodeRunLength(real.getData(), buf);
    }
    else
      buf = real.getData();
    flipVertical(buf, adrn.getWidth(), adrn.getHeight());
    texture.setBitmap(buf);
    return texture;
  }

  private static void decodeRunLength(byte[] rl, byte[] buf) {
    int length = rl.length;
    int readPos = 0;
    int writePos = 0;
    while(readPos < length) {
      short head = BitConverter.uint8(rl[readPos++]);
      byte value = 0;
      boolean copy;
      short x, y, z;
      if(head >= 224) {
        copy = false;
        value = 0;
        x = (short) (head - 224);
        y = BitConverter.uint8(rl[readPos++]);
        z = BitConverter.uint8(rl[readPos++]);
      } else if(head >= 208) {
        copy = false;
        value = 0;
        x = 0;
        y = (short) (head - 208);
        z = BitConverter.uint8(rl[readPos++]);
      } else if(head >= 192) {
        copy = false;
        value = 0;
        x = 0;
        y = 0;
        z = (short) (head - 192);
      } else if(head >= 160) {
        copy = false;
        value = rl[readPos++];
        x = (short) (head - 160);
        y = BitConverter.uint8(rl[readPos++]);
        z = BitConverter.uint8(rl[readPos++]);
      } else if(head >= 144) {
        copy = false;
        value = rl[readPos++];
        x = 0;
        y = (short) (head - 144);
        z = BitConverter.uint8(rl[readPos++]);
      } else if(head >= 128) {
        copy = false;
        value = rl[readPos++];
        x = 0;
        y = 0;
        z = (short) (head - 128);
      } else if(head >= 32) {
        copy = true;
        x = (short) (head - 32);
        y = BitConverter.uint8(rl[readPos++]);
        z = BitConverter.uint8(rl[readPos++]);
      } else if(head >= 16) {
        copy = true;
        x = 0;
        y = (short) (head - 16);
        z = BitConverter.uint8(rl[readPos++]);
      } else {
        copy = true;
        x = 0;
        y = 0;
        z = head;
      }
      int total = x * 65536 + y * 256 + z;
      int canWrite = buf.length - writePos;
      if(total > canWrite) {
        total = canWrite;
      }
      if(copy) {
        int canRead = length - readPos;
        if(total > canRead) {
          total = canRead;
        }
        for(int i = 0; i < total; i++) {
          value = rl[readPos++];
          buf[writePos++] = value;
        }
      } else {
        for(int i = 0; i < total; i++) {
          buf[writePos++] = value;
        }
      }
    }
  }

  private static void flipVertical(byte[] data, int width, int height) {
    byte[] buf = new byte[width];
    for(int i = 0; i < height / 2; i++) {
      System.arraycopy(data, i * width, buf, 0, width);
      System.arraycopy(data, (height - i - 1) * width, data, i * width, width);
      System.arraycopy(buf, 0, data, (height - i - 1) * width, width);
    }
  }

}
