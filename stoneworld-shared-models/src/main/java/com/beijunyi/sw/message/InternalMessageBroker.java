package com.beijunyi.sw.message;

import java.util.ArrayList;
import java.util.Collection;

import org.jgroups.*;

public class InternalMessageBroker extends ReceiverAdapter {

  private final JChannel channel;
  private final Collection<InternalMessageHandler> handlers = new ArrayList<>();

  public InternalMessageBroker(JChannel channel) {
    this.channel = channel;
  }

  @Override
  public void receive(Message msg) {
    Object msgObj = msg.getObject();
    if(msgObj instanceof InternalMessage) {
      for(InternalMessageHandler handler : handlers) {
        try {
          handler.handle((InternalMessage) msgObj, msg.getSrc());
        } catch(Exception e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  void send(InternalMessage msg, Object dest) {
    try {
      channel.send((Address) dest, msg);
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

  void broadcast(InternalMessage msg) {
    send(msg, null);
  }

}
