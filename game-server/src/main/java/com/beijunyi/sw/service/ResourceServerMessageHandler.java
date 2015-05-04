package com.beijunyi.sw.service;

import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.config.model.GameServerProperties;
import com.beijunyi.sw.message.gameserver.GameServerOnline;
import com.beijunyi.sw.message.resourceserver.ResourceServerMessage;
import org.jgroups.Address;
import org.jgroups.JChannel;

@Named
public class ResourceServerMessageHandler {

  private final JChannel channel;
  private final GameServerProperties props;

  @Inject
  public ResourceServerMessageHandler(JChannel channel, GameServerProperties props) {
    this.channel = channel;
    this.props = props;
  }

  public void handle(ResourceServerMessage message, Address src) throws Exception {
    switch(message.getType()) {
      case RESOURCE_SERVER_ONLINE:
        channel.send(src, new GameServerOnline(props.getName(), props.getIp(), props.getPort()));
        break;
    }
  }

}
