package com.beijunyi.sw.output;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.config.Settings;
import com.beijunyi.sw.output.models.Animation;
import com.beijunyi.sw.output.models.AnimationFrame;
import com.beijunyi.sw.sa.SaResourcesManager;
import com.beijunyi.sw.sa.models.SprAdrnBlock;
import com.beijunyi.sw.sa.models.SprBlock;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

@Named
public class AnimationFactory {

  private final Settings settings;
  private final SaResourcesManager srm;
  private final Kryo kryo;

  private final AudioFactory af;
  private final TextureFactory tf;

  private final Object[] animationLocks;

  private String animationsOutputPath;


  @Inject
  public AnimationFactory(Settings settings, SaResourcesManager srm, Kryo kryo, AudioFactory af, TextureFactory tf) {
    this.settings = settings;
    this.srm = srm;
    this.kryo = kryo;
    this.af = af;
    this.tf = tf;

    animationLocks = new Object[srm.getMaxSprAdrnId() + 1];
    for(int i = 0; i <= srm.getMaxSprAdrnId(); i++) {
      animationLocks[i] = new Object();
    }
  }

  @PostConstruct
  public void setOutputPaths() throws IOException {
    String outputPath = settings.getOutputPath();
    animationsOutputPath = FilenameUtils.normalizeNoEndSeparator(FilenameUtils.concat(outputPath, "animations"), true);
    FileUtils.forceMkdir(new File(animationsOutputPath));
  }

  public Map<Byte, Map<Byte, Animation>> createAnimationMap(int id) throws IOException {
    SprAdrnBlock sprAdrn = srm.getSprAdrnBlock(id);
    List<SprBlock> sprs = srm.getSprBlockSeries(sprAdrn.getAddress(), sprAdrn.getActions());
    Map<Byte, Map<Byte, Animation>> result = new HashMap<>();
    for(SprBlock spr : sprs) {
      Animation animation = new Animation();
      animation.setDuration(spr.getDuration());
      animation.setFrames(new ArrayList<AnimationFrame>(spr.getLength()));
      for(int i = 0; i < spr.getImages().length; i++) {
        AnimationFrame frame = new AnimationFrame();
        frame.setTexture(tf.getTexture(spr.getImages()[i], false));
        int rawAudioId = spr.getImpactAudio()[i];
        int uid = 0;
        if(rawAudioId != 0) {
          Integer mappedUid = af.getAudioIdMap().get(rawAudioId);
          if(mappedUid != null)
            uid = mappedUid;
        }
        frame.setAudio((short)uid);
        animation.getFrames().add(frame);
      }
      Map<Byte, Animation> actionAnimationMap = result.get(spr.getDirection());
      if(actionAnimationMap == null) {
        actionAnimationMap = new HashMap<>();
        result.put(spr.getDirection(), actionAnimationMap);
      }
      actionAnimationMap.put(spr.getAction(), animation);
    }
    return result;
  }

  public byte[] getAnimation(int id, byte direction, byte action) throws IOException {
    String animationDir = getAnimationDir(id);
    File animationFile = new File(getAnimationFile(animationDir, direction, action));
    byte[] animationBytes = null;
    synchronized(animationLocks[id]) {
      if(animationFile.exists()) {
        try(InputStream is = new GZIPInputStream(new FileInputStream(animationFile))) {
          animationBytes = IOUtils.toByteArray(is);
        }
      } else {
        FileUtils.forceMkdir(new File(animationDir));
        Map<Byte, Map<Byte, Animation>> sm = createAnimationMap(id);
        for(Map.Entry<Byte, Map<Byte, Animation>> directionAnimations : sm.entrySet()) {
          for(Map.Entry<Byte, Animation> actionAnimation : directionAnimations.getValue().entrySet()) {
            byte d =  directionAnimations.getKey();
            byte a =  actionAnimation.getKey();
            Animation animation = actionAnimation.getValue();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Output out = new Output(baos);
            kryo.writeObject(out, animation);
            out.flush();
            byte[] buf = baos.toByteArray();
            try(OutputStream os = new GZIPOutputStream(new FileOutputStream(getAnimationFile(animationDir, d, a)))) {
              IOUtils.write(buf, os);
            }
            if(d == direction && a == action)
              animationBytes = buf;
          }
        }
      }
    }

    return animationBytes;
  }

  private String getAnimationDir(int id) {
    return FilenameUtils.normalizeNoEndSeparator(FilenameUtils.concat(animationsOutputPath, Integer.toString(id)), true);
  }

  private String getAnimationFile(String dir, byte direction, byte action) {
    return FilenameUtils.normalizeNoEndSeparator(FilenameUtils.concat(dir, direction + "-" + action + ".bin.gz"), true);
  }

}
