package com.beijunyi.sw.message;

import com.beijunyi.sw.message.gameserver.GameServerOffline;
import com.beijunyi.sw.message.gameserver.GameServerOnline;
import com.beijunyi.sw.message.resourceserver.ResourceServerOffline;
import com.beijunyi.sw.message.resourceserver.ResourceServerOnline;
import com.esotericsoftware.kryo.Kryo;

public final class MessageModelInitializer {

  public static void registerModels(Kryo kryo) {
    kryo.register(GameServerOnline.class, 100);
    kryo.register(GameServerOffline.class, 199);

    kryo.register(ResourceServerOnline.class, 200);
    kryo.register(ResourceServerOffline.class, 299);
  }

}
