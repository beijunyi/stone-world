package com.beijunyi.sw.sa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.beijunyi.sw.config.GatewayServerResourceConfig;
import com.beijunyi.sw.sa.models.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Singleton
public class SaResourcesManager {

  private static final Logger log = LoggerFactory.getLogger(SaResourcesManager.class);

  private final Kryo kryo;
  private final Properties props;

  private Map<Integer, AdrnBlock> idAdrnMap;
  private Map<Integer, Integer> mapIdMap;
  private Map<Integer, SprAdrnBlock> idSprAdrnMap;
  private int maxAdrnId = -1;
  private int maxSprAdrnId = -1;
  private List<Long> sprAddresses;
  private FileChannel realChannel;
  private FileChannel sprChannel;

  private Map<Integer, Path> paletFiles;
  private int maxPaletId = -1;

  private Map<Integer, Path> ls2MapFiles;
  private int maxLS2MapId;

  @Inject
  public SaResourcesManager(@Named("resource-properties") Properties props, Kryo kryo) {
    this.props = props;
    this.kryo = kryo;
  }

  @PostConstruct
  public void prepareResources() throws IOException {
    Path saDataDir = Paths.get(props.getProperty(GatewayServerResourceConfig.SA_DATA_PROPERTY_KEY));
    if(Files.exists(saDataDir)) {
      Map<ClientResource, Map<Integer, Path>> resources = new HashMap<>();
      locateClientResources(saDataDir, resources);
      indexAdrns(resources);
      indexSprAdrns(resources);
      openReal(resources);
      openSpr(resources);
      indexPalets(resources);
    }

    Path gmsvDataDir = Paths.get(props.getProperty(GatewayServerResourceConfig.GMSV_DATA_PROPERTY_KEY));
    indexLS2Maps(gmsvDataDir, kryo);
  }

  @PreDestroy
  public void closeRAs() throws Exception {
    realChannel.close();
    sprChannel.close();
  }

  private void locateClientResources(Path dir, Map<ClientResource, Map<Integer, Path>> resources) throws IOException {
    try(DirectoryStream<Path> files = Files.newDirectoryStream(dir)) {
      for(Path file : files) {
        if(Files.isDirectory(file))
          locateClientResources(file, resources);
        else {
          for(ClientResource type : ClientResource.values()) {
            String id = type.matches(file.getFileName().toString());
            if(id != null) {
              Map<Integer, Path> fs = resources.get(type);
              if(fs == null) {
                fs = new HashMap<>();
                resources.put(type, fs);
              }
              fs.put(Integer.valueOf(id), file);
              log.debug("Found " + type + " at " + file);
            }
          }
        }
      }
    }
  }

  private static Path findUniqueClientResource(Map<ClientResource, Map<Integer, Path>> resources, ClientResource type) {
    Map<Integer, Path> pathMap = resources.get(type);
    if(pathMap.isEmpty()) {
      log.info("No " + type + " file is found");
      return null;
    }
    List<Path> paths = new ArrayList<>(pathMap.values());
    if(paths.size() != 1) {
      StringBuilder sb = new StringBuilder("Found multiple " + type + " files: ");
      Collections.sort(paths);
      for(Path path : paths) {
        sb.append("\n\t").append(path);
      }
      log.warn(sb.toString());
      Collections.reverse(paths);
    }
    return paths.get(0);
  }

  private void indexAdrns(Map<ClientResource, Map<Integer, Path>> resources) throws IOException {
    Path adrnPath = findUniqueClientResource(resources, ClientResource.ADRN);
    if(adrnPath != null) {
      log.info("Indexing " + adrnPath);
      try(InputStream is = Files.newInputStream(adrnPath)) {
        Adrn adrn = kryo.readObject(new Input(is), Adrn.class);
        idAdrnMap = new HashMap<>();
        mapIdMap = new HashMap<>();
        for(AdrnBlock block : adrn.getAdrnBlocks()) {
          int id = block.getId();
          idAdrnMap.put(id, block);
          if(id > maxAdrnId)
            maxAdrnId = id;
          if(block.getMap() != 0)
            mapIdMap.put(block.getMap(), id);
        }
      }
      log.info(Integer.toString(idAdrnMap.size()) + ClientResource.ADRN +  " objects are found, max ID is " + maxAdrnId);
    }
  }

  private void indexSprAdrns(Map<ClientResource, Map<Integer, Path>> resources) throws IOException {
    Path spradrnPath = findUniqueClientResource(resources, ClientResource.SPRADRN);
    if(spradrnPath != null) {
      log.info("Indexing " + spradrnPath);
      try(InputStream is = Files.newInputStream(spradrnPath)) {
        SprAdrn sprAdrn = kryo.readObject(new Input(is), SprAdrn.class);
        SortedSet<Long> addressSet = new TreeSet<>();
        idSprAdrnMap = new HashMap<>();
        for(SprAdrnBlock block : sprAdrn.getSprAdrnBlocks()) {
          int id = block.getId();
          idSprAdrnMap.put(id, block);
          addressSet.add(block.getAddress());
          if(id > maxSprAdrnId)
            maxSprAdrnId = id;
        }
        sprAddresses = new ArrayList<>(addressSet);
      }
      log.info(Integer.toString(idSprAdrnMap.size()) + ClientResource.SPRADRN + " objects are found, max ID is " + maxSprAdrnId);
    }
  }

  private void openReal(Map<ClientResource, Map<Integer, Path>> resources) throws IOException {
    Path realPath = findUniqueClientResource(resources, ClientResource.REAL);
    if(realPath != null) {
      log.info("Opening " + realPath);
      realChannel = FileChannel.open(realPath);
      log.info(ClientResource.REAL + " objects are ready, size is " + realChannel.size());
    }
  }

  private void openSpr(Map<ClientResource, Map<Integer, Path>> resources) throws IOException {
    Path sprPath = findUniqueClientResource(resources, ClientResource.SPR);
    if(sprPath != null) {
      log.info("Opening " + sprPath);
      sprChannel = FileChannel.open(sprPath);
      log.info(ClientResource.SPR + " objects are ready, size is " + sprChannel.size());
    }
  }


  private void indexPalets(Map<ClientResource, Map<Integer, Path>> resources) throws IOException {
    Map<Integer, Path> pathMap = resources.get(ClientResource.PALET);
    if(pathMap.isEmpty()) {
      log.info("No " + ClientResource.PALET + " file is found");
      return;
    }
    log.info("Indexing " + ClientResource.PALET + " files");
    paletFiles = new HashMap<>();
    int count = 0;
    for(Map.Entry<Integer, Path> entry : pathMap.entrySet()) {
      int id = entry.getKey();
      paletFiles.put(id, entry.getValue());
      if(id > maxPaletId)
        maxPaletId = id;
      count++;
    }
    log.info(Integer.toString(count) + ClientResource.PALET + " objects are found, max ID is " + maxPaletId);
  }

  public int getMaxAdrnId() {
    return maxAdrnId;
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
      synchronized(realChannel) {
        realChannel.position(address);
        realChannel.read(buf);
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
        length = (int) (sprChannel.size() - address);
      else
        length = (int) (sprAddresses.get(nextIndex) - address);
      buf = ByteBuffer.allocate(length);
      synchronized(sprChannel) {
        sprChannel.position(address);
        sprChannel.read(buf);
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
