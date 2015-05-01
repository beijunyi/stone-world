package com.beijunyi.sw.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.message.MessageSerializer;
import com.beijunyi.sw.message.resourceserver.ResourceServerOffline;
import com.beijunyi.sw.message.resourceserver.ResourceServerOnline;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

@Named
public class ResourceMessageReceiver extends ReceiverAdapter {

  private final JChannel channel;
  private final MessageSerializer serializer;

  @Inject
  public ResourceMessageReceiver(JChannel channel, MessageSerializer serializer) throws Exception {
    this.channel = channel;
    this.serializer = serializer;
    channel.setReceiver(this);
  }

  @PostConstruct
  public void notifyOnline() throws Exception {
    channel.setReceiver(this);
    channel.send(null, serializer.getByteArray(new ResourceServerOnline()));
  }

  @PreDestroy
  public void notifyOffline() throws Exception {
    channel.send(null, serializer.getByteArray(new ResourceServerOffline()));
  }

  @Override
  public void receive(Message msg) {
    System.out.println(msg);
  }

}
