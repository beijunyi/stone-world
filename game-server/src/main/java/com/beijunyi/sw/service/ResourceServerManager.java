package com.beijunyi.sw.service;

import java.util.Collection;
import java.util.LinkedList;
import javax.inject.Named;

import com.beijunyi.sw.service.model.ResourceServerStatus;
import org.jgroups.Address;

@Named
public class ResourceServerManager {

  private final Collection<ResourceServerStatus> rsStatuses = new LinkedList<>();

  public void addResourceServer(Address address) {

  }

}
