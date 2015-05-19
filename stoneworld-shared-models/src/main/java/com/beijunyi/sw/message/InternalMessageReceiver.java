package com.beijunyi.sw.message;

import java.util.Collection;
import javax.inject.Inject;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

public class InternalMessageReceiver extends ReceiverAdapter {

  private Collection<InternalMessageHandler> handlers;

  @Inject
  public InternalMessageReceiver(Collection<InternalMessageHandler> handlers) {
    this.handlers = handlers;
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

}
