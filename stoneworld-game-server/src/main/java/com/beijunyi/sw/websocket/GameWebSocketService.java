package com.beijunyi.sw.websocket;

import javax.inject.Named;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

@Named
public class GameWebSocketService extends BinaryWebSocketHandler {

  @Override
  protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
    super.handleBinaryMessage(session, message);
    System.out.println(message.getPayload().array());
  }
}
