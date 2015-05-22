package com.beijunyi.sw.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.GameServerConstants;
import com.beijunyi.sw.config.model.GameServerMainProperties;
import com.beijunyi.sw.message.InternalMessage;
import com.beijunyi.sw.message.InternalMessageBroker;
import com.beijunyi.sw.message.InternalMessageHandler;
import com.beijunyi.sw.message.gameserver.PlayerToken;
import com.beijunyi.sw.message.gatewayserver.GatewayMessageEnum;
import com.beijunyi.sw.message.gatewayserver.RequestToken;

@Named
public class TokenManager extends InternalMessageHandler {

  private final Map<String, PlayerToken> tokens = new HashMap<>();

  private final GameServerMainProperties props;
  private final String url;

  @Inject
  public TokenManager(InternalMessageBroker broker, GameServerMainProperties props) {
    super(broker);
    this.props = props;
    this.url = GameServerConstants.GAME_SERVER_URL_PROTOCOL + props.getIp() + ":" + props.getPort() + GameServerConstants.GAME_SERVER_URL_PATH;
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
  protected void handle(InternalMessage msg, Object src) throws Exception {
    if(GatewayMessageEnum.REQUEST_TOKEN.equals(msg.getType())) {
      RequestToken reqToken = (RequestToken) msg;
      send(requestToken(reqToken.getKey()), src);
    }
  }
}
