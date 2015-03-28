package com.beijunyi.sw.output;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.config.Settings;
import com.beijunyi.sw.config.custom.CustomAnimation;
import com.beijunyi.sw.config.custom.CustomAnimationSettings;
import com.beijunyi.sw.config.custom.CustomResourcesSettings;
import com.beijunyi.sw.output.models.Animation;
import com.beijunyi.sw.output.models.AnimationFrame;
import com.beijunyi.sw.output.models.Texture;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

@Named
public class CustomAnimationFactory {

  private final Settings settings;
  private final Kryo kryo;

  private final TextureFactory tf;

  private final Map<Integer, CustomAnimation> customAnimationMap = new HashMap<>();

  private final Object[] customAnimationLocks;

  private String customAnimationsOutputPath;

  @Inject
  public CustomAnimationFactory(Settings settings, Kryo kryo, CustomResourcesSettings crs, TextureFactory tf) {
    this.settings = settings;
    this.kryo = kryo;
    this.tf = tf;
    int maxCustomAnimationId = indexCustomAnimationResources(crs);

    customAnimationLocks = new Object[maxCustomAnimationId + 1];
    for(int i = 0; i <= maxCustomAnimationId; i++) {
      customAnimationLocks[i] = new Object();
    }
  }

  @PostConstruct
  public void setOutputPaths() throws IOException {
    String outputPath = settings.getOutputPath();
    customAnimationsOutputPath = FilenameUtils.normalizeNoEndSeparator(FilenameUtils.concat(outputPath, "custom_animations"), true);
    FileUtils.forceMkdir(new File(customAnimationsOutputPath));
  }

  private int indexCustomAnimationResources(CustomResourcesSettings crs) {
    CustomAnimationSettings cas = crs.getAnimations();
    int maxId = -1;

    for(Map.Entry<String, CustomAnimation> additionalAnimationEntry : cas.getAdditions().entrySet()) {
      CustomAnimation animation = additionalAnimationEntry.getValue();
      customAnimationMap.put(animation.getUid(), animation);
      if(animation.getUid() > maxId)
        maxId = animation.getUid();
    }

    return maxId;
  }

  public Animation createCustomAnimation(int id) throws IOException {
    CustomAnimation customAnimation = customAnimationMap.get(id);
    Animation animation = new Animation();
    animation.setDuration(customAnimation.getDuration());
    List<AnimationFrame> frames = new ArrayList<>();
    for(int textureId : customAnimation.getFrames()) {
      AnimationFrame frame = new AnimationFrame();
      Texture texture = tf.getTexture(textureId, false);
      frame.setTexture(texture);
      frame.setAudio((short) 0);
      frames.add(frame);
    }
    animation.setFrames(frames);
    return animation;
  }

  public byte[] getCustomAnimation(int id) throws IOException {
    File customAnimationFile = new File(FilenameUtils.concat(customAnimationsOutputPath, id + ".bin"));
    byte[] customAnimationBytes;
    synchronized(customAnimationLocks[id]) {
      if(customAnimationFile.exists()) {
        try(InputStream is = new GZIPInputStream(new FileInputStream(customAnimationFile))) {
          customAnimationBytes = IOUtils.toByteArray(is);
        }
      } else {
        Animation animation = createCustomAnimation(id);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output out = new Output(baos);
        kryo.writeObject(out, animation);
        out.flush();
        customAnimationBytes = baos.toByteArray();
        try(OutputStream os = new GZIPOutputStream(new FileOutputStream(customAnimationFile))) {
          IOUtils.write(customAnimationBytes, os);
        }
      }
    }
    return customAnimationBytes;
  }
}
