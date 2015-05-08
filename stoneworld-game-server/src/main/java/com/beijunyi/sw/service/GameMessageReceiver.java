package com.beijunyi.sw.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.config.model.GameServerProperties;
import com.beijunyi.sw.message.gameserver.GameServerOffline;
import com.beijunyi.sw.message.gameserver.GameServerOnline;
import com.beijunyi.sw.message.gatewayserver.GatewayMessage;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

@Named
public class GameMessageReceiver extends ReceiverAdapter {

  private final JChannel channel;

  private final GameServerProperties props;
  private final GatewayServerMessageHandler handler;

  @Inject
  public GameMessageReceiver(JChannel channel,  GameServerProperties props, GatewayServerMessageHandler handler) {
    this.channel = channel;
    this.props = props;
    this.handler = handler;
  }

  @PostConstruct
  public void notifyOnline() throws Exception {
    channel.setReceiver(this);
    channel.send(null, new GameServerOnline(props.getName(), props.getIp(), props.getPort()));
  }

  @PreDestroy
  public void notifyOffline() throws Exception {
    channel.send(null, new GameServerOffline(props.getName()));
  }

  @Override
  public void receive(Message msg) {
    Object msgObj = msg.getObject();
    try {
      if(msgObj instanceof GatewayMessage) {
        handler.handle((GatewayMessage)msgObj, msg.getSrc());
      }
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

}
