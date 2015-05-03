package com.beijunyi.sw.service;

import java.util.*;
import javax.inject.Named;

import com.beijunyi.sw.service.model.GameServerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class GameServerManager {

  private static final Logger log = LoggerFactory.getLogger(GameServerManager.class);

  private final List<GameServerStatus> gsStatuses = new LinkedList<>();
  private final Map<String, GameServerStatus> gsMaps = new HashMap<>();

  public void addGameServer(String name, String ip, int port) {
    if(gsMaps.containsKey(name))
      throw new IllegalArgumentException();
    GameServerStatus gss = new GameServerStatus(name, ip, port);
    gsStatuses.add(gss);
    gsMaps.put(name, gss);
    log.info("Successfully registered Game Server: " + name);
  }

  public void removeGameServer(String name) {
    GameServerStatus gss = gsMaps.remove(name);
    if(gss == null)
      throw new IllegalArgumentException();
    gsStatuses.remove(gss);
    log.info("Successfully unregistered Game Server: " + name);
  }
}
