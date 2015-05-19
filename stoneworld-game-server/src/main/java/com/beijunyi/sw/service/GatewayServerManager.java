package com.beijunyi.sw.service;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;

import com.beijunyi.sw.message.InternalMessage;
import com.beijunyi.sw.message.InternalMessageHandler;
import com.beijunyi.sw.message.gatewayserver.GatewayMessageEnum;
import com.beijunyi.sw.message.gatewayserver.GatewayServerOffline;
import com.beijunyi.sw.message.gatewayserver.GatewayServerOnline;
import com.beijunyi.sw.service.model.GatewayServerStatus;
import org.jgroups.Address;

@Named
public class GatewayServerManager implements InternalMessageHandler {

  private final Map<String, GatewayServerStatus> gateways = new HashMap<>();

  private void addGatewayServer(String name, Address address) {
    if(gateways.containsKey(name))
      throw new IllegalArgumentException();

    gateways.put(name, new GatewayServerStatus(name, address));
  }

  private void removeGatewayServer(String name) {
    gateways.remove(name);
  }

  @Override
  public void handle(InternalMessage msg, Address src) throws Exception {
    if(GatewayMessageEnum.GATEWAY_SERVER_ONLINE.equals(msg.getType())) {
      GatewayServerOnline gwOnline = (GatewayServerOnline) msg;
      addGatewayServer(gwOnline.getName(), src);
    } else if(GatewayMessageEnum.GATEWAY_SERVER_OFFLINE.equals(msg.getType())) {
      GatewayServerOffline gwOffline = (GatewayServerOffline) msg;
      removeGatewayServer(gwOffline.getName());
    }
  }
}
