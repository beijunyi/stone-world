package com.beijunyi.sw.output;

import java.io.*;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.config.Settings;
import com.beijunyi.sw.output.models.Texture;
import com.beijunyi.sw.sa.SaResourcesManager;
import com.beijunyi.sw.sa.models.AdrnBlock;
import com.beijunyi.sw.sa.models.RealBlock;
import com.beijunyi.sw.utils.ImageUtils;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

@Named
public class TextureFactory {

  private final Settings settings;
  private final SaResourcesManager srm;
  private final Kryo kryo;

  private final Object[] textureLocks;
  private String texturesOutputPath;

  @Inject
  public TextureFactory(Settings settings, SaResourcesManager srm, Kryo kryo) {
    this.settings = settings;
    this.srm = srm;
    this.kryo = kryo;

    textureLocks = new Object[srm.getMaxAdrnId() + 1];
    for(int i = 0; i <= srm.getMaxAdrnId(); i++) {
      textureLocks[i] = new Object();
    }
  }

  @PostConstruct
  public void setOutputPaths() throws IOException {
    String outputPath = settings.getOutputPath();
    texturesOutputPath = FilenameUtils.normalizeNoEndSeparator(FilenameUtils.concat(outputPath, "textures"), true);
    FileUtils.forceMkdir(new File(texturesOutputPath));
  }


  private File getTextureFile(int id) {
    return new File(FilenameUtils.concat(texturesOutputPath, id + ".bin"));
  }

  private Texture createTextureFromRawResources(int id) {
    AdrnBlock adrn = srm.getAdrnBlock(id);
    RealBlock real = srm.getRealBlock(adrn.getAddress(), adrn.getSize());
    Texture texture = new Texture();
    texture.setX(adrn.getxOffset());
    texture.setY(adrn.getyOffset());
    texture.setWidth(adrn.getWidth());
    texture.setHeight(adrn.getHeight());
    byte[] buf;
    if(real.getMajor() == 1) {
      buf = new byte[adrn.getWidth() * adrn.getHeight()];
      ImageUtils.decodeRunlength(real.getData(), buf);
    }
    else
      buf = real.getData();
    ImageUtils.flipVertical(buf, adrn.getWidth(), adrn.getHeight());
    texture.setBitmap(buf);
    return texture;
  }

  private byte[] serialize(Texture texture) throws IOException {
    byte[] data;
    try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      Output output = new Output(baos);
      kryo.writeObject(output, texture);
      output.flush();
      data = baos.toByteArray();
    }
    return data;
  }

  private Texture deserialize(byte[] data) throws IOException {
    try(InputStream is = new ByteArrayInputStream(data)) {
      return kryo.readObject(new Input(is), Texture.class);
    }
  }

  private void outputTextureData(int id, byte[] data) throws IOException {
    try(OutputStream os = new FileOutputStream(getTextureFile(id))) {
      IOUtils.write(data, os);
    }
  }

  private byte[] loadTextureDataFromOutput(int id) throws IOException {
    File textureFile = getTextureFile(id);
    if(!textureFile.exists()) {
      return null;
    }
    try(InputStream is = new FileInputStream(textureFile)) {
      return IOUtils.toByteArray(is);
    }
  }

  public Texture getTexture(int id, boolean output) throws IOException {
    Texture texture;
    synchronized(textureLocks[id]) {
      byte[] textureData = loadTextureDataFromOutput(id);
      if(textureData == null) {
        texture = createTextureFromRawResources(id);
        if(output) {
          textureData = serialize(texture);
          outputTextureData(id, textureData);
        }
      } else {
        texture = deserialize(textureData);
      }
    }
    return texture;
  }

  public byte[] getTextureData(int id, boolean output) throws IOException {
    synchronized(textureLocks[id]) {
      byte[] textureData = loadTextureDataFromOutput(id);
      if(textureData == null) {
        Texture texture = createTextureFromRawResources(id);
        textureData = serialize(texture);
        if(output)
          outputTextureData(id, textureData);
      }
      return textureData;
    }
  }
}
