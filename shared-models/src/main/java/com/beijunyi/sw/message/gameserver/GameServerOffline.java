package com.beijunyi.sw.message.gameserver;

import com.beijunyi.sw.message.MessageModel;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public final class GameServerOffline implements MessageModel {

  @Override
  public void write(Kryo kryo, Output output) {
  }

  @Override
  public void read(Kryo kryo, Input input) {
  }
}
