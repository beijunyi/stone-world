package com.beijunyi.sw.output;

import java.awt.image.RenderedImage;
import java.io.*;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.beijunyi.sw.config.Settings;
import com.beijunyi.sw.output.models.Texture;
import com.beijunyi.sw.utils.ImageUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

@Named
@Singleton
public class ResourcesManager {

  private final Settings settings;
  private final ResourcesFactory rf;

  private final AudioFactory af;
  private final AnimationFactory anf;
  private final CustomAnimationFactory caf;
  private final TextureFactory tf;

  private String imagesOutputPath;
  private String palettesOutputPath;

  @Inject
  public ResourcesManager(Settings settings, ResourcesFactory rf, AudioFactory af, AnimationFactory anf, CustomAnimationFactory caf, TextureFactory tf) {
    this.settings = settings;
    this.rf = rf;

    this.af = af;
    this.anf = anf;
    this.caf = caf;
    this.tf = tf;
  }

  @PostConstruct
  public void setOutputPaths() throws IOException {
    String outputPath = settings.getOutputPath();
    imagesOutputPath = FilenameUtils.normalizeNoEndSeparator(FilenameUtils.concat(outputPath, "images"), true);
    FileUtils.forceMkdir(new File(imagesOutputPath));
    palettesOutputPath = FilenameUtils.normalizeNoEndSeparator(FilenameUtils.concat(outputPath, "palettes"), true);
    FileUtils.forceMkdir(new File(palettesOutputPath));
  }

  public byte[] getImage(int id) throws IOException {
    File imageFile = new File(FilenameUtils.concat(imagesOutputPath, id + ".png"));
    byte[] imageBytes;
    if(imageFile.exists()) {
      try(InputStream is = new FileInputStream(imageFile)) {
        imageBytes = IOUtils.toByteArray(is);
      }
    } else {
      Texture texture = tf.getTexture(id, false);
      byte[] palette = getPalette(1);
      RenderedImage image = ImageUtils.createImage(texture.getBitmap(), texture.getWidth(), texture.getHeight(), palette);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(image, "png", baos);
      imageBytes = baos.toByteArray();
      try(OutputStream os = new FileOutputStream(imageFile)) {
        IOUtils.write(imageBytes, os);
      }
    }
    return imageBytes;
  }

  public byte[] getAudio(int id, String format) throws IOException {
    return af.getAudio(id, format);
  }

  public byte[] getTexture(int id) throws IOException {
    return tf.getTextureData(id, true);
  }

  public byte[] getAnimation(int id, byte direction, byte action) throws IOException {
    return anf.getAnimation(id, direction, action);
  }

  public byte[] getCustomAnimation(int id) throws IOException {
    return caf.getCustomAnimation(id);
  }

  public byte[] getPalette(int id) throws IOException {
    File paletteFile = new File(FilenameUtils.concat(palettesOutputPath, id + ".bin"));
    byte[] palette;
    if(paletteFile.exists()) {
      try(InputStream is = new FileInputStream(paletteFile)) {
        palette = IOUtils.toByteArray(is);
      }
    } else {
      palette = rf.createPalette(id);
      try(OutputStream os = new FileOutputStream(paletteFile)) {
        IOUtils.write(palette, os);
      }
    }
    return palette;
  }

}
