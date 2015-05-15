package com.beijunyi.sw.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.message.gameserver.GameMessage;
import com.beijunyi.sw.message.gatewayserver.GatewayServerOffline;
import com.beijunyi.sw.message.gatewayserver.GatewayServerOnline;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

@Named
public class GatewayMessageReceiver extends ReceiverAdapter {

  private final JChannel channel;
  private final GatewayMessageHandler gmHandler;

  @Inject
  public GatewayMessageReceiver(JChannel channel, GatewayMessageHandler gmHandler) throws Exception {
    this.channel = channel;
    this.gmHandler = gmHandler;
    channel.setReceiver(this);
  }

  @PostConstruct
  public void notifyOnline() throws Exception {
    channel.setReceiver(this);
    channel.send(null, new GatewayServerOnline());
  }

  @PreDestroy
  public void notifyOffline() throws Exception {
    channel.send(null, new GatewayServerOffline());
  }

  @Override
  public void receive(Message msg) {
    Object msgObj = msg.getObject();
    if(msgObj instanceof GameMessage) {
      gmHandler.handle((GameMessage)msgObj, msg.getSrc());
    }
  }

}
