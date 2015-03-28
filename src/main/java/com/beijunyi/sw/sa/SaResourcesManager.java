package com.beijunyi.sw.sa;

import java.io.*;
import java.util.*;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.beijunyi.sw.config.Settings;
import com.beijunyi.sw.sa.models.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.MagicNumberFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Singleton
public class SaResourcesManager {

  private static final Logger log = LoggerFactory.getLogger(SaResourcesManager.class);

  private final Settings settings;
  private final Kryo kryo;

  private final Map<ClientResource, Map<Integer, File>> resources;

  private final Map<Integer, AdrnBlock> idAdrnMap = new HashMap<>();
  private final Map<Integer, Integer> mapIdMap = new HashMap<>();
  private final Map<Integer, SprAdrnBlock> idSprAdrnMap = new HashMap<>();
  private final int maxAdrnId;
  private final int maxSprAdrnId;
  private final List<Long> sprAddresses = new ArrayList<>();
  private final RandomAccessFile realRA;
  private final RandomAccessFile sprRA;
  private final Map<Integer, File> paletFilesMap;

  private final Map<Integer, LS2Map> idLs2MapMap = new HashMap<>();

  @Inject
  public SaResourcesManager(Settings settings, Kryo kryo) throws Exception {
    this.settings = settings;
    this.kryo = kryo;

    resources = locateClientResources();
    try(InputStream is = new FileInputStream(resources.get(ClientResource.ADRN).values().iterator().next())) {
      Adrn adrn = kryo.readObject(new Input(is), Adrn.class);
      maxAdrnId = indexAdrns(adrn);
    }
    try(InputStream is = new FileInputStream(resources.get(ClientResource.SPRADRN).values().iterator().next())) {
      SprAdrn sprAdrn = kryo.readObject(new Input(is), SprAdrn.class);
      maxSprAdrnId = indexSprAdrns(sprAdrn);
    }
    realRA = new RandomAccessFile(resources.get(ClientResource.REAL).values().iterator().next(), "r");
    sprRA = new RandomAccessFile(resources.get(ClientResource.SPR).values().iterator().next(), "r");
    paletFilesMap = resources.get(ClientResource.PALET);

    Collection<File> mapFiles = locateMapFiles();
    for(File mapFile : mapFiles) {
      try(InputStream is = new FileInputStream(mapFile)) {
        LS2Map ls2Map = kryo.readObject(new Input(is), LS2Map.class);
        idLs2MapMap.put(ls2Map.getId(), ls2Map);
      }
    }
  }

  @PreDestroy
  public void closeRAs() throws Exception {
    realRA.close();
    sprRA.close();
  }

  public Map<ClientResource, Map<Integer, File>> getResources() {
    return resources;
  }

  private Map<ClientResource, Map<Integer, File>> locateClientResources() {
    Map<ClientResource, Map<Integer, File>> resources = new HashMap<>();
    File saDataDir = new File(settings.getSaDataPath());
    Collection<File> dataFiles = FileUtils.listFiles(saDataDir, null, true);
    for(File file : dataFiles) {
      for(ClientResource type : ClientResource.values()) {
        String id = type.matches(file.getName());
        if(id != null) {
          Map<Integer, File> fs = resources.get(type);
          if(fs == null) {
            fs = new HashMap<>();
            resources.put(type, fs);
          }
          fs.put(Integer.valueOf(id), file);
          log.info("Found {} at {}.", type, file);
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
    byte[] buf = new byte[length];

    try {
      synchronized(realRA) {
        realRA.seek(address);
        realRA.read(buf);
      }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
    try(InputStream is = new ByteArrayInputStream(buf)) {
      return kryo.readObject(new Input(is), RealBlock.class);
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<SprBlock> getSprBlockSeries(long address, int actions) {
    byte[] buf;
    try {
      int nextIndex = Collections.binarySearch(sprAddresses, address) + 1;
      int length;
      if(nextIndex == sprAddresses.size())
        length = (int) (sprRA.length() - address);
      else
        length = (int) (sprAddresses.get(nextIndex) - address);
      buf = new byte[length];
      synchronized(sprRA) {
        sprRA.seek(address);
        sprRA.read(buf);
      }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
    Input input = new Input(new ByteArrayInputStream(buf));
    List<SprBlock> blocks = new ArrayList<>(actions);
    for(int a = 0; a < actions; a++)
      blocks.add(kryo.readObject(input, SprBlock.class));
    return blocks;
  }

  public Palet getPalet(int id) {
    File paletFile = paletFilesMap.get(id);
    if(paletFile == null)
      return null;
    Palet palet = new Palet();
    try(InputStream is = new FileInputStream(paletFile)) {
      palet.setData(IOUtils.toByteArray(is));
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
    return palet;
  }

  private Collection<File> locateMapFiles() {
    String gmsvDataPath = settings.getGmsvDataPath();
    File mapDir = new File(FilenameUtils.concat(gmsvDataPath, "map"));
    return FileUtils.listFiles(mapDir, new MagicNumberFileFilter("LS2MAP"), TrueFileFilter.TRUE);
  }

  public Map<Integer, LS2Map> getIdLs2MapMap() {
    return idLs2MapMap;
  }
}
