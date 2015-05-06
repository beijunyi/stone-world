package com.beijunyi.sw.service;

import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.config.model.GameServerProperties;
import com.beijunyi.sw.message.gameserver.GameServerOnline;
import com.beijunyi.sw.message.gatewayserver.GatewayServerMessage;
import com.beijunyi.sw.message.gatewayserver.GatewayServerOffline;
import com.beijunyi.sw.message.gatewayserver.GatewayServerOnline;
import org.jgroups.Address;
import org.jgroups.JChannel;

@Named
public class GatewayServerMessageHandler {

  private final JChannel channel;
  private final GameServerProperties props;
  private final GatewayServerManager gatewayManager;

  @Inject
  public GatewayServerMessageHandler(JChannel channel, GameServerProperties props, GatewayServerManager gatewayManager) {
    this.channel = channel;
    this.props = props;
    this.gatewayManager = gatewayManager;
  }

  public void handle(GatewayServerMessage message, Address src) throws Exception {
    switch(message.getType()) {
      case GATEWAY_SERVER_ONLINE:
        channel.send(src, new GameServerOnline(props.getName(), props.getIp(), props.getPort()));
        gatewayManager.addGatewayServer(((GatewayServerOnline)message).getName(), src);
        break;
      case GATEWAY_SERVER_OFFLINE:
        gatewayManager.removeGatewayServer(((GatewayServerOffline)message).getName());
        break;
    }
  }

}
