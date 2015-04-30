package com.beijunyi.sw.model;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class GameServerReady implements KryoSerializable {

  private String ip;
  private int port;

  public GameServerReady(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }

  public GameServerReady() {
  }

  public String getIp() {
    return ip;
  }

  public int getPort() {
    return port;
  }

  @Override
  public void write(Kryo kryo, Output output) {
    output.writeString(ip);
    output.writeInt(port);
  }

  @Override
  public void read(Kryo kryo, Input input) {
    ip = input.readString();
    port = input.readInt();
  }
}
