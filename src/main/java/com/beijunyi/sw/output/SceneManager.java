package com.beijunyi.sw.output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.beijunyi.sw.config.Settings;
import com.beijunyi.sw.output.models.Scene;
import com.beijunyi.sw.sa.SaResourcesManager;
import com.esotericsoftware.kryo.Kryo;

@Named
@Singleton
public class SceneManager {

  private final SaResourcesManager srm;
  private final Kryo kryo;
  private final Path scenesDir;
  private final Object[] locks;

  private Map<Integer, Scene> scenes = new HashMap<>();

  @Inject
  public SceneManager(Settings settings, SaResourcesManager srm, Kryo kryo) throws IOException {
    this.srm = srm;
    this.kryo = kryo;
    scenesDir = settings.getOutputPath().resolve("scenes");
    Files.createDirectories(scenesDir);
    locks = new Object[srm.getMaxLS2MapId() + 1];
  }

  private Path getOutputScenePath(int id) {
    return scenesDir.resolve(id + ".bin");
  }

}
