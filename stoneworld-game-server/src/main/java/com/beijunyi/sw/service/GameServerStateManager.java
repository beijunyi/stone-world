package com.beijunyi.sw.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.config.model.GameServerProperties;
import com.beijunyi.sw.message.InternalMessage;
import com.beijunyi.sw.message.InternalMessageHandler;
import com.beijunyi.sw.message.gameserver.GameServerOffline;
import com.beijunyi.sw.message.gameserver.GameServerOnline;
import com.beijunyi.sw.message.gatewayserver.GatewayMessageEnum;
import org.jgroups.Address;
import org.jgroups.JChannel;

@Named
public class GameServerStateManager implements InternalMessageHandler {

  @Inject
  private JChannel channel;
  @Inject
  private GameServerProperties props;

  @PostConstruct
  public void init() throws Exception {
    channel.send(null, new GameServerOnline(props.getName(), props.getIp(), props.getPort()));
  }

  @PreDestroy
  public void dispose() throws Exception {
    channel.send(null, new GameServerOffline(props.getName()));
  }

  @Override
  public void handle(InternalMessage msg, Address src) throws Exception {
    if(GatewayMessageEnum.GATEWAY_SERVER_ONLINE.equals(msg.getType())) {
      channel.send(src, new GameServerOnline(props.getName(), props.getIp(), props.getPort()));
    }
  }

}
