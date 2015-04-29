package com.beijunyi.sw.ws;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;


public class WebSocketEndPoint extends BinaryWebSocketHandler {

  @Override
  protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
    super.handleBinaryMessage(session, message);
  }
}
