package com.beijunyi.sw.service;

import java.util.Collection;
import java.util.LinkedList;
import javax.inject.Named;

import com.beijunyi.sw.service.model.GatewayServerStatus;
import org.jgroups.Address;

@Named
public class GatewayServerManager {

  private final Collection<GatewayServerStatus> rsStatuses = new LinkedList<>();

  public void addGatewayServer(Address address) {

  }

}
