package com.beijunyi.sw.resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import com.beijunyi.sw.config.model.GatewayServerResourceProperties;
import com.beijunyi.sw.resources.models.Scene;
import com.beijunyi.sw.sa.SaResourcesManager;
import com.beijunyi.sw.sa.models.LS2Map;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

@Named
@Singleton
public class SceneManager {

  private static final Pattern NAME_PATTERN = Pattern.compile("^((\\p{L})*)\\|*\u0000*.*$");

  private final SaResourcesManager srm;
  private final Kryo kryo;
  private final Path scenesDir;
  private final Object[] locks;

  private Map<Integer, byte[]> scenes = new HashMap<>();

  @Inject
  public SceneManager(GatewayServerResourceProperties props, SaResourcesManager srm, Kryo kryo) throws IOException {
    this.srm = srm;
    this.kryo = kryo;
    scenesDir = props.getCacheLocation().resolve("scenes");
    Files.createDirectories(scenesDir);
    locks = new Object[srm.getMaxLS2MapId() + 1];
    for(int i = 0; i < locks.length; i++)
      locks[i] = new Object();
  }

  private Path getOutputScenePath(int id) {
    return scenesDir.resolve(id + ".bin");
  }

  public byte[] getSceneData(int id) {
    if(id < 0 || id >= locks.length)
      throw new IllegalArgumentException("Invalid id: " + id);
    byte[] data = scenes.get(id);
    if(data == null) {
      synchronized(this) {
        // try again
        data = scenes.get(id);
        if(data != null)
          return data;

        Path outputTexturePath = getOutputScenePath(id);
        if(Files.exists(outputTexturePath)) {
          try {
            data = Files.readAllBytes(outputTexturePath);
          } catch(IOException e) {
            throw new RuntimeException("Could not read " + outputTexturePath, e);
          }
        } else {
          Scene scene = createScene(srm.getLS2Map(id));
          ByteArrayOutputStream stream = new ByteArrayOutputStream();
          Output output = new Output(stream);
          kryo.writeObject(output, scene);
          output.flush();
          data = stream.toByteArray();
          try {
            Files.write(outputTexturePath, data);
          } catch(IOException e) {
            throw new RuntimeException("Could not write " + outputTexturePath, e);
          }
        }
        scenes.put(id, data);
      }
    }
    return data;
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
