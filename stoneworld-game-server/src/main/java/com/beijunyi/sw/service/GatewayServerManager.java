package com.beijunyi.sw.service;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;

import com.beijunyi.sw.service.model.GatewayServerStatus;
import org.jgroups.Address;

@Named
public class GatewayServerManager {

  private final Map<String, GatewayServerStatus> gateways = new HashMap<>();

  public void addGatewayServer(String name, Address address) {
    if(gateways.containsKey(name))
      throw new IllegalArgumentException();

    gateways.put(name, new GatewayServerStatus(name, address));
  }

  public void removeGatewayServer(String name) {
    gateways.remove(name);
  }

}
