package com.beijunyi.sw.sa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.beijunyi.sw.config.Settings;
import com.beijunyi.sw.sa.models.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Singleton
public class SaResourcesManager {

  private static final Logger log = LoggerFactory.getLogger(SaResourcesManager.class);

  private final Settings settings;
  private final Kryo kryo;

  private final Map<Integer, AdrnBlock> idAdrnMap = new HashMap<>();
  private final Map<Integer, Integer> mapIdMap = new HashMap<>();
  private final Map<Integer, SprAdrnBlock> idSprAdrnMap = new HashMap<>();
  private final int maxAdrnId;
  private final int maxSprAdrnId;
  private final List<Long> sprAddresses = new ArrayList<>();
  private final FileChannel realRA;
  private final FileChannel sprRA;

  private Map<Integer, Path> paletFiles;
  private int maxPaletId;

  private Map<Integer, Path> ls2MapFiles;
  private int maxLS2MapId;

  @Inject
  public SaResourcesManager(Settings settings, Kryo kryo) throws Exception {
    this.settings = settings;
    this.kryo = kryo;

    Path clientDataPath = settings.getSaDataPath();

    Map<ClientResource, Map<Integer, Path>> resources = locateClientResources();
    try(InputStream is = Files.newInputStream(resources.get(ClientResource.ADRN).values().iterator().next())) {
      Adrn adrn = kryo.readObject(new Input(is), Adrn.class);
      maxAdrnId = indexAdrns(adrn);
    }
    try(InputStream is = Files.newInputStream(resources.get(ClientResource.SPRADRN).values().iterator().next())) {
      SprAdrn sprAdrn = kryo.readObject(new Input(is), SprAdrn.class);
      maxSprAdrnId = indexSprAdrns(sprAdrn);
    }
    realRA = FileChannel.open(resources.get(ClientResource.REAL).values().iterator().next());
    sprRA = FileChannel.open(resources.get(ClientResource.SPR).values().iterator().next());

    indexPalets(clientDataPath);

    Path gmsvDataPath = settings.getGmsvDataPath();
    indexLS2Maps(gmsvDataPath, kryo);
  }

  @PreDestroy
  public void closeRAs() throws Exception {
    realRA.close();
    sprRA.close();
  }

  private Map<ClientResource, Map<Integer, Path>> locateClientResources() throws IOException {
    Map<ClientResource, Map<Integer, Path>> resources = new HashMap<>();
    Path saDataDir = settings.getSaDataPath();
    try(DirectoryStream<Path> files = Files.newDirectoryStream(saDataDir)) {
      for(Path file : files) {
        for(ClientResource type : ClientResource.values()) {
          String id = type.matches(file.getFileName().toString());
          if(id != null) {
            Map<Integer, Path> fs = resources.get(type);
            if(fs == null) {
              fs = new HashMap<>();
              resources.put(type, fs);
            }
            fs.put(Integer.valueOf(id), file);
            log.info("Found {} at {}.", type, file);
          }
        }
      }
    }
    return resources;
  }

  private int indexAdrns(Adrn adrn) {
    int maxId = -1;
    for(AdrnBlock block : adrn.getAdrnBlocks()) {
      int id = block.getId();
      idAdrnMap.put(id, block);
      if(id > maxId)
        maxId = id;
      if(block.getMap() != 0)
        mapIdMap.put(block.getMap(), id);
    }
    return maxId;
  }

  public int getMaxAdrnId() {
    return maxAdrnId;
  }

  private int indexSprAdrns(SprAdrn sprAdrn) {
    SortedSet<Long> addressSet = new TreeSet<>();
    int maxId = -1;
    for(SprAdrnBlock block : sprAdrn.getSprAdrnBlocks()) {
      int id = block.getId();
      idSprAdrnMap.put(id, block);
      addressSet.add(block.getAddress());
      if(id > maxId)
        maxId = id;
    }
    sprAddresses.addAll(addressSet);
    return maxId;
  }

  public int getMaxSprAdrnId() {
    return maxSprAdrnId;
  }

  public Map<Integer, AdrnBlock> getIdAdrnMap() {
    return idAdrnMap;
  }

  public Map<Integer, Integer> getMapIdMap() {
    return mapIdMap;
  }

  public Map<Integer, SprAdrnBlock> getIdSprAdrnMap() {
    return idSprAdrnMap;
  }

  public AdrnBlock getAdrnBlock(int adrnId) {
    return idAdrnMap.get(adrnId);
  }

  public SprAdrnBlock getSprAdrnBlock(int sprAdrnId) {
    return idSprAdrnMap.get(sprAdrnId);
  }

  public RealBlock getRealBlock(long address, int length) {
    ByteBuffer buf = ByteBuffer.allocate(length);

    try {
      synchronized(realRA) {
        realRA.position(address);
        realRA.read(buf);
      }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
    try(InputStream is = new ByteArrayInputStream(buf.array())) {
      return kryo.readObject(new Input(is), RealBlock.class);
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<SprBlock> getSprBlockSeries(long address, int actions) {
    ByteBuffer buf;
    try {
      int nextIndex = Collections.binarySearch(sprAddresses, address) + 1;
      int length;
      if(nextIndex == sprAddresses.size())
        length = (int) (sprRA.size() - address);
      else
        length = (int) (sprAddresses.get(nextIndex) - address);
      buf = ByteBuffer.allocate(length);
      synchronized(sprRA) {
        sprRA.position(address);
        sprRA.read(buf);
      }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
    Input input = new Input(buf.array());
    List<SprBlock> blocks = new ArrayList<>(actions);
    for(int a = 0; a < actions; a++)
      blocks.add(kryo.readObject(input, SprBlock.class));
    return blocks;
  }

  private void indexPalets(Path clientDataPath) throws IOException {
    paletFiles = new HashMap<>();
    Path palsDir = clientDataPath.resolve("pal");
    if(Files.exists(palsDir)) {
      try(DirectoryStream<Path> pals = Files.newDirectoryStream(palsDir)) {
        for(Path pal : pals) {
          String label = ClientResource.PALET.matches(pal.getFileName().toString());
          if(label == null)
            continue;
          int id = Integer.valueOf(label);
          paletFiles.put(id, pal);
          if(id > maxPaletId)
            maxPaletId = id;
        }
      }
    }
  }

  public int getMaxPaletId() {
    return maxPaletId;
  }

  public Palet getPalet(int id) {
    Path file = paletFiles.get(id);
    if(file == null)
      throw new IllegalArgumentException("Cannot find Palet: " + id);
    try(InputStream stream = Files.newInputStream(file)) {
      Input input = new Input(stream);
      return kryo.readObject(input, Palet.class);
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void indexLS2Maps(Path gmsvDataPath, final Kryo kryo) throws IOException {
    Path mapDir = gmsvDataPath.resolve("map");
    final LS2Map.MapHeaderSerializer headerSerializer = new LS2Map.MapHeaderSerializer();
    ls2MapFiles = new HashMap<>();
    Files.walkFileTree(mapDir, new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        try(InputStream stream = Files.newInputStream(file)) {
          try {
            LS2Map ls2Map = kryo.readObject(new Input(stream), LS2Map.class, headerSerializer);
            int id = ls2Map.getId();
            ls2MapFiles.put(id, file);
            if(id > maxLS2MapId)
              maxLS2MapId = id;
          } catch(IllegalArgumentException e) {
            log.debug("Invalid map file: " + file, e);
          }
        }
        return FileVisitResult.CONTINUE;
      }
    });
  }

  public int getMaxLS2MapId() {
    return maxLS2MapId;
  }

  public LS2Map getLS2Map(int id) {
    Path file = ls2MapFiles.get(id);
    if(file == null)
      throw new IllegalArgumentException("Cannot find LS2MAP: " + id);
    try(InputStream stream = Files.newInputStream(file)) {
      Input input = new Input(stream);
      return kryo.readObject(input, LS2Map.class);
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

}
