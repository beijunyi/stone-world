package com.beijunyi.sw.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class MessageSerializer {

  private final Kryo kryo;

  public MessageSerializer(Kryo kryo) {
    this.kryo = kryo;
  }

  public byte[] getByteArray(MessageModel model) {
    try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      kryo.writeObject(new Output(out), model);
      return out.toByteArray();
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

  public MessageModel readMessage(byte[] bytes) {
    try(Input input = new Input(bytes)) {
      return kryo.readObject(input, MessageModel.class);
    }
  }
}
