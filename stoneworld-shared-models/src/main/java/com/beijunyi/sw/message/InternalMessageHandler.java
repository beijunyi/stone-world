package com.beijunyi.sw.message;

public abstract class InternalMessageHandler {

  private final InternalMessageBroker broker;

  protected InternalMessageHandler(InternalMessageBroker broker) {
    this.broker = broker;
    broker.addHandler(this);
  }

  protected void handle(InternalMessage msg, Object src) throws Exception {
  }

  protected void send(InternalMessage msg, Object dest) {
    broker.send(msg, dest);
  }

  protected void broadcast(InternalMessage msg) {
    broker.broadcast(msg);
  }

}
