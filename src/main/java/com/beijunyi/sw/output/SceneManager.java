package com.beijunyi.sw.output;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.beijunyi.sw.config.Settings;
import com.beijunyi.sw.output.models.Scene;
import com.beijunyi.sw.sa.SaResourcesManager;
import com.beijunyi.sw.sa.models.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

@Named
@Singleton
public class SceneManager {

  private static final Pattern NAME_PATTERN = Pattern.compile("^((\\p{L})*)\\|*\u0000*.*$");

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

  public Scene getScene(int id) {
    if(id < 0 || id >= locks.length)
      throw new IllegalArgumentException("Invalid id: " + id);
    Scene scene = scenes.get(id);
    if(scene == null) {
      synchronized(this) {
        // try again
        scene = scenes.get(id);
        if(scene != null)
          return scene;

        Path outputTexturePath = getOutputScenePath(id);
        if(Files.exists(outputTexturePath)) {
          try(InputStream input = Files.newInputStream(outputTexturePath)) {
            scene = kryo.readObject(new Input(input), Scene.class);
          } catch(IOException e) {
            throw new RuntimeException("Could not read " + outputTexturePath, e);
          }
        } else {
          LS2Map map = srm.getLS2Map(id);
          scene = createScene(map);
          try(OutputStream stream = Files.newOutputStream(outputTexturePath)) {
            Output output = new Output(stream);
            kryo.writeObject(output, scene);
            output.flush();
          } catch(IOException e) {
            throw new RuntimeException("Could not write " + outputTexturePath, e);
          }
        }
        scenes.put(id, scene);
      }
    }
    return scene;
  }

  private static Scene createScene(LS2Map map) {
    Scene scene = new Scene();
    String rawName = new String(map.getName(), Charset.forName("gbk"));
    Matcher matcher = NAME_PATTERN.matcher(rawName);
    if(!matcher.matches())
      throw new IllegalArgumentException(rawName);
    scene.setName(matcher.group(1));
    scene.setEast(map.getEast());
    scene.setSouth(map.getSouth());
    scene.setTiles(map.getTiles());
    scene.setObjects(map.getObjects());
    return scene;
  }

}
