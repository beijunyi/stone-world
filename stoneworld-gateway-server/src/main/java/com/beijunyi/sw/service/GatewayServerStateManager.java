package com.beijunyi.sw.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.message.InternalMessageBroker;
import com.beijunyi.sw.message.InternalMessageHandler;
import com.beijunyi.sw.message.gatewayserver.GatewayServerOffline;
import com.beijunyi.sw.message.gatewayserver.GatewayServerOnline;

@Named
public class GatewayServerStateManager extends InternalMessageHandler {

  @Inject
  public GatewayServerStateManager(InternalMessageBroker broker) {
    super(broker);
  }

  @PostConstruct
  public void init() throws Exception {
    broadcast(new GatewayServerOnline());
  }

  @PreDestroy
  public void dispose() throws Exception {
    broadcast(new GatewayServerOffline());
  }

}
