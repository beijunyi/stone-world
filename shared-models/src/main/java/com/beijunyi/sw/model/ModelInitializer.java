package com.beijunyi.sw.model;

import com.beijunyi.sw.model.gameserver.GameServerOffline;
import com.beijunyi.sw.model.gameserver.GameServerOnline;
import com.beijunyi.sw.model.resourceserver.ResourceServerOffline;
import com.beijunyi.sw.model.resourceserver.ResourceServerOnline;
import com.esotericsoftware.kryo.Kryo;

public final class ModelInitializer {

  public static void registerModels(Kryo kryo) {
    kryo.register(GameServerOnline.class, 100);
    kryo.register(GameServerOffline.class, 199);

    kryo.register(ResourceServerOnline.class, 200);
    kryo.register(ResourceServerOffline.class, 299);
  }

}
