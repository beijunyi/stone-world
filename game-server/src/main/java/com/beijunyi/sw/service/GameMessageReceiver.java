package com.beijunyi.sw.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.config.model.GameServerProperties;
import com.beijunyi.sw.message.gameserver.GameServerOffline;
import com.beijunyi.sw.message.gameserver.GameServerOnline;
import com.beijunyi.sw.message.resourceserver.ResourceServerOnline;
import org.jgroups.*;

@Named
public class GameMessageReceiver extends ReceiverAdapter {

  private final JChannel channel;

  private final GameServerProperties props;

  @Inject
  public GameMessageReceiver(JChannel channel,  GameServerProperties props) {
    this.channel = channel;
    this.props = props;
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
    handleRequest(msg.getObject(), msg.getSrc());
  }

  public void handleRequest(Object message, Address src) {
    try {
      if(message instanceof ResourceServerOnline) {
        channel.send(src, new GameServerOnline(props.getName(), props.getIp(), props.getPort()));
      }
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

}
