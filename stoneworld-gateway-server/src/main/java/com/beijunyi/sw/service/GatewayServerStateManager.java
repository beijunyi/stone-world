package com.beijunyi.sw.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.message.InternalMessage;
import com.beijunyi.sw.message.InternalMessageHandler;
import com.beijunyi.sw.message.gatewayserver.GatewayServerOffline;
import com.beijunyi.sw.message.gatewayserver.GatewayServerOnline;
import org.jgroups.Address;
import org.jgroups.JChannel;

@Named
public class GatewayServerStateManager implements InternalMessageHandler {

  @Inject
  private JChannel channel;


  @PostConstruct
  public void init() throws Exception {
    channel.send(null, new GatewayServerOnline());
  }

  @PreDestroy
  public void dispose() throws Exception {
    channel.send(null, new GatewayServerOffline());
  }

  @Override
  public void handle(InternalMessage msg, Address src) throws Exception {

  }
}
