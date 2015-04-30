package com.beijunyi.sw;

import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Inject;
import javax.inject.Named;

import org.jgroups.*;

@Named
public class ResourceServerReceiver implements Receiver {

  @Inject
  public ResourceServerReceiver(JChannel channel) throws Exception {
    channel.setReceiver(this);
    channel.connect(System.getProperty("cluster.name", "stoneworld"));
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

  }

  @Override
  public void getState(OutputStream output) throws Exception {

  }

  @Override
  public void setState(InputStream input) throws Exception {

  }
}
