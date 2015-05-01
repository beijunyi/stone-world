package com.beijunyi.sw.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.config.model.ServerProperties;
import com.beijunyi.sw.message.MessageModel;
import com.beijunyi.sw.message.MessageSerializer;
import com.beijunyi.sw.message.gameserver.GameServerOffline;
import com.beijunyi.sw.message.gameserver.GameServerOnline;
import com.beijunyi.sw.message.resourceserver.ResourceServerOnline;
import org.jgroups.*;

@Named
public class GameMessageReceiver extends ReceiverAdapter {

  private final JChannel channel;
  private final MessageSerializer serializer;

  private final ServerProperties props;

  @Inject
  public GameMessageReceiver(JChannel channel, MessageSerializer serializer, ServerProperties props) {
    this.channel = channel;
    this.serializer = serializer;
    this.props = props;
  }

  @PostConstruct
  public void notifyOnline() throws Exception {
    channel.setReceiver(this);
    channel.send(null, serializer.getByteArray(new GameServerOnline(props.getIp(), props.getPort())));
  }

  @PreDestroy
  public void notifyOffline() throws Exception {
    channel.send(null, serializer.getByteArray(new GameServerOffline()));
  }

  @Override
  public void receive(Message msg) {
    handleRequest(serializer.readMessage(msg.getBuffer()), msg.getSrc());
  }

  public void handleRequest(MessageModel message, Address src) {
    try {
      if(message instanceof ResourceServerOnline) {
        channel.send(src, serializer.getByteArray(new GameServerOnline(props.getIp(), props.getPort())));
      }
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

}
