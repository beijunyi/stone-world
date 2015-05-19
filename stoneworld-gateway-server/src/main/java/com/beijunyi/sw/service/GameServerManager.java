package com.beijunyi.sw.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.message.InternalMessage;
import com.beijunyi.sw.message.InternalMessageHandler;
import com.beijunyi.sw.message.gameserver.GameMessageEnum;
import com.beijunyi.sw.message.gameserver.GameServerOffline;
import com.beijunyi.sw.message.gameserver.GameServerOnline;
import com.beijunyi.sw.message.gatewayserver.GatewayMessageEnum;
import com.beijunyi.sw.message.gatewayserver.RequestToken;
import com.beijunyi.sw.service.model.GameServerStatus;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class GameServerManager implements InternalMessageHandler {

  private static final Logger log = LoggerFactory.getLogger(GameServerManager.class);

  private final List<GameServerStatus> gsStatuses = new LinkedList<>();
  private int nextGsIndex = 0;

  @Inject
  private JChannel channel;

  private void addGameServer(String name, String ip, int port, Address address) {
    synchronized(gsStatuses) {
      GameServerStatus gss = new GameServerStatus(name, ip, port, address);
      gsStatuses.add(0, gss);
      nextGsIndex++;
    }
    log.info("Successfully registered Game Server: " + name);
  }

  private void removeGameServer(String name) {
    synchronized(gsStatuses) {
      Iterator<GameServerStatus> gsIt = gsStatuses.iterator();
      int index = 0;
      while(gsIt.hasNext()) {
        GameServerStatus gs = gsIt.next();
        if(gs.getName().equals(name)) {
          gsIt.remove();
          if(index < nextGsIndex)
            nextGsIndex--;
          break;
        }
      }
    }
    log.info("Successfully unregistered Game Server: " + name);
  }

  private GameServerStatus getNextGs() {
    synchronized(gsStatuses) {
      if(gsStatuses.isEmpty())
        return null;
      GameServerStatus gs = gsStatuses.get(nextGsIndex++);
      if(nextGsIndex == gsStatuses.size())
        nextGsIndex = 0;
      return gs;
    }
  }

  public boolean requestPlayerToken(String key) {
    GameServerStatus gs = getNextGs();
    if(gs == null)
      return false;
    try {
      channel.send(gs.getAddress(), new RequestToken(key));
    } catch(Exception e) {
      log.error("Could not request token from " + gs.getAddress(), e);
      return false;
    }
    return true;
  }

  @Override
  public void handle(InternalMessage msg, Address src) throws Exception {
    if(GameMessageEnum.GAME_SERVER_ONLINE.equals(msg.getType())) {
      GameServerOnline gsOnline = (GameServerOnline) msg;
      addGameServer(gsOnline.getName(), gsOnline.getIp(), gsOnline.getPort(), src);
    } else if(GatewayMessageEnum.GATEWAY_SERVER_OFFLINE.equals(msg.getType())) {
      GameServerOffline gsOffline = (GameServerOffline) msg;
      removeGameServer(gsOffline.getName());
    }
  }
}
