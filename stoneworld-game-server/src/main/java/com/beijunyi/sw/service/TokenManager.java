package com.beijunyi.sw.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.GameServerConstants;
import com.beijunyi.sw.config.model.GameServerProperties;
import com.beijunyi.sw.message.InternalMessage;
import com.beijunyi.sw.message.InternalMessageHandler;
import com.beijunyi.sw.message.gameserver.PlayerToken;
import com.beijunyi.sw.message.gatewayserver.GatewayMessageEnum;
import com.beijunyi.sw.message.gatewayserver.RequestToken;
import org.jgroups.Address;
import org.jgroups.JChannel;

@Named
public class TokenManager implements InternalMessageHandler {

  private final Map<String, PlayerToken> tokens = new HashMap<>();

  @Inject
  private JChannel channel;
  @Inject
  private GameServerProperties props;

  private String url;

  @PostConstruct
  public void init() throws Exception {
    url = props.getIp() + ":" + props.getPort() + GameServerConstants.GAME_SERVER_URL_SUFFIX;
  }

  public PlayerToken requestToken(String key) {
    synchronized(tokens) {
      PlayerToken token = tokens.get(key);
      if(token == null) {
        String uid = UUID.randomUUID().toString();
        token = new PlayerToken(key, url, uid);
        tokens.put(key, token);
      }
      return token;
    }
  }

  public void destroyToken(String key) {
    synchronized(tokens) {
      tokens.remove(key);
    }
  }

  public boolean checkToken(String key, String uid) {
    synchronized(tokens) {
      PlayerToken expected = tokens.get(key);
      return expected != null && expected.getUid().equals(uid);
    }
  }

  @Override
  public void handle(InternalMessage msg, Address src) throws Exception {
    if(GatewayMessageEnum.REQUEST_TOKEN.equals(msg.getType())) {
      RequestToken reqToken = (RequestToken) msg;
      channel.send(src, requestToken(reqToken.getKey()));
    }
  }
}
