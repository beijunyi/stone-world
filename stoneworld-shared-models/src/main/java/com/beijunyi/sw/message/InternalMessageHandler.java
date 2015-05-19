package com.beijunyi.sw.message;

import org.jgroups.Address;

public interface InternalMessageHandler {

  void handle(InternalMessage msg, Address src) throws Exception;

}
