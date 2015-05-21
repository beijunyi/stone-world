package com.beijunyi.sw.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.config.model.GameServerMainProperties;
import com.beijunyi.sw.message.InternalMessage;
import com.beijunyi.sw.message.InternalMessageBroker;
import com.beijunyi.sw.message.InternalMessageHandler;
import com.beijunyi.sw.message.gameserver.GameServerOffline;
import com.beijunyi.sw.message.gameserver.GameServerOnline;
import com.beijunyi.sw.message.gatewayserver.GatewayMessageEnum;

@Named
public class GameServerStateManager extends InternalMessageHandler {

  private final GameServerMainProperties props;

  @Inject
  public GameServerStateManager(InternalMessageBroker broker,GameServerMainProperties props) {
    super(broker);
    this.props = props;
  }

  @PostConstruct
  public void init() throws Exception {
    broadcast(new GameServerOnline(props.getName(), props.getIp(), props.getPort()));
  }

  @PreDestroy
  public void dispose() throws Exception {
    broadcast(new GameServerOffline(props.getName()));
  }

  @Override
  protected void handle(InternalMessage msg, Object src) throws Exception {
    if(GatewayMessageEnum.GATEWAY_SERVER_ONLINE.equals(msg.getType())) {
      send(new GameServerOnline(props.getName(), props.getIp(), props.getPort()), src);
    }
  }

}
