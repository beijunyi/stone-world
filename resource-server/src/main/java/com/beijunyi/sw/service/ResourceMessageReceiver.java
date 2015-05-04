package com.beijunyi.sw.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.message.gameserver.GameServerMessage;
import com.beijunyi.sw.message.resourceserver.ResourceServerOffline;
import com.beijunyi.sw.message.resourceserver.ResourceServerOnline;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

@Named
public class ResourceMessageReceiver extends ReceiverAdapter {

  private final JChannel channel;
  private final GameServerMessageHandler gsmHandler;

  @Inject
  public ResourceMessageReceiver(JChannel channel, GameServerMessageHandler gsmHandler) throws Exception {
    this.channel = channel;
    this.gsmHandler = gsmHandler;
    channel.setReceiver(this);
  }

  @PostConstruct
  public void notifyOnline() throws Exception {
    channel.setReceiver(this);
    channel.send(null, new ResourceServerOnline());
  }

  @PreDestroy
  public void notifyOffline() throws Exception {
    channel.send(null, new ResourceServerOffline());
  }

  @Override
  public void receive(Message msg) {
    Object msgObj = msg.getObject();
    if(msgObj instanceof GameServerMessage) {
      gsmHandler.handle((GameServerMessage) msgObj, msg.getSrc());
    }
  }

}
