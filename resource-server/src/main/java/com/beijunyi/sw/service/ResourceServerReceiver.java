package com.beijunyi.sw.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import com.beijunyi.sw.model.resourceserver.ResourceServerOffline;
import com.beijunyi.sw.model.resourceserver.ResourceServerOnline;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import org.jgroups.*;

@Named
public class ResourceServerReceiver implements Receiver {

  private final JChannel channel;
  private final Kryo kryo;

  @Inject
  public ResourceServerReceiver(JChannel channel, @Named("KryoRef") Kryo kryo) throws Exception {
    this.channel = channel;
    this.kryo = kryo;
    channel.setReceiver(this);
  }

  @PostConstruct
  public void notifyOnline() throws Exception {
    try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      kryo.writeObject(new Output(out), new ResourceServerOnline());
      channel.send(null, out.toByteArray());
    }
  }

  @PreDestroy
  public void notifyOffline() throws Exception {
    try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      kryo.writeObject(new Output(out), new ResourceServerOffline());
      channel.send(null, out.toByteArray());
    }
  }

  @Override
  public void viewAccepted(View new_view) {

  }

  @Override
  public void suspect(Address suspected_mbr) {

  }

  @Override
  public void block() {

  }

  @Override
  public void unblock() {

  }

  @Override
  public void receive(Message msg) {
    System.out.println(msg);
  }

  @Override
  public void getState(OutputStream output) throws Exception {

  }

  @Override
  public void setState(InputStream input) throws Exception {

  }
}
