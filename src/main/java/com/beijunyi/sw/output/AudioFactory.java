package com.beijunyi.sw.output;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.config.Settings;
import com.beijunyi.sw.config.custom.*;
import com.beijunyi.sw.sa.ClientResource;
import com.beijunyi.sw.sa.SaResourcesManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

@Named
public class AudioFactory {

  private final Settings settings;

  private final Map<Integer, Integer> audioIdMap = new HashMap<>();
  private final Map<Integer, File> audioFileMap = new HashMap<>();

  private final Object[] audioLocks;
  private String audiosOutputPath;

  @Inject
  public AudioFactory(Settings settings, SaResourcesManager srm, CustomResourcesSettings crs) {
    this.settings = settings;

    int maxAudioId = indexCustomAudioResources(srm.getResources(), crs);

    audioLocks = new Object[maxAudioId + 1];
    for(int i = 0; i <= maxAudioId; i++) {
      audioLocks[i] = new Object();
    }
  }

  @PostConstruct
  public void setOutputPaths() throws IOException {
    String outputPath = settings.getOutputPath();
    audiosOutputPath = FilenameUtils.normalizeNoEndSeparator(FilenameUtils.concat(outputPath, "audios"), true);
    FileUtils.forceMkdir(new File(audiosOutputPath));
  }

  private int indexCustomAudioResources(Map<ClientResource, Map<Integer, File>> resources, CustomResourcesSettings crs) {
    CustomAudioSettings audioSettings = crs.getAudios();
    int maxId = -1;
    for(Map.Entry<String, AudioMapping> settingEntry : audioSettings.getMappings().entrySet()) {
      ClientResource resourcesType = ClientResource.valueOf("AUDIO_" + settingEntry.getKey().toUpperCase());
      Map<Integer, File> audioFiles = resources.get(resourcesType);

      AudioMapping audioMapping = settingEntry.getValue();
      Map<Integer, AudioSpecialMapping> specialMappings = audioMapping.getSpecials();
      if(specialMappings != null) {
        for(Map.Entry<Integer, AudioSpecialMapping> audioSpecialMappingEntry : specialMappings.entrySet()) {
          int id = audioSpecialMappingEntry.getKey();
          File audioFile = audioFiles.get(id);
          AudioSpecialMapping mapping = audioSpecialMappingEntry.getValue();
          int uid = mapping.getUid();
          audioFileMap.put(uid, audioFile);
          for(int rawId : mapping.getRaw()) {
            audioIdMap.put(rawId, uid);
          }
          if(uid > maxId)
            maxId = uid;
        }
      }

      AudioDefaultMapping audioDefaultMapping = audioMapping.getDefaults();
      if(audioDefaultMapping != null) {
        for(Map.Entry<Integer, File> audioFileEntry : audioFiles.entrySet()) {
          File file = audioFileEntry.getValue();
          if(!audioFileMap.values().contains(file)) {
            int id = audioFileEntry.getKey();
            int uid = id + audioDefaultMapping.getUid();
            int rawId = id + audioDefaultMapping.getRaw();
            audioFileMap.put(uid, file);
            audioIdMap.put(rawId, uid);
            if(uid > maxId)
              maxId = uid;
          }
        }
      }
    }
    return maxId;
  }

  public byte[] getAudio(int id, String format) throws IOException {
    File audioFile = new File(FilenameUtils.concat(audiosOutputPath, id + "." + format));
    byte[] audioBytes;
    synchronized(audioLocks[id]) {
      if(audioFile.exists()) {
        try(InputStream is = new FileInputStream(audioFile)) {
          audioBytes = IOUtils.toByteArray(is);
        }
      } else {
        switch(format) {
          case("wav"):
            try(InputStream is = new FileInputStream(audioFileMap.get(id))) {
              audioBytes = IOUtils.toByteArray(is);
              FileUtils.writeByteArrayToFile(audioFile, audioBytes);
            }
            break;
          default:
            throw new UnsupportedOperationException("Cannot generate " + id + "." + format);
        }
      }
    }

    return audioBytes;
  }

  public Map<Integer, Integer> getAudioIdMap() {
    return audioIdMap;
  }
}
