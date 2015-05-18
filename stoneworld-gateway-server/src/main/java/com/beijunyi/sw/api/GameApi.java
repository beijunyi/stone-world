package com.beijunyi.sw.api;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.beijunyi.sw.api.util.UserHelper;
import com.beijunyi.sw.service.PlayerManager;
import org.springframework.security.core.userdetails.UserDetails;

@Named
@Singleton
@Path("/api/game")
public class GameApi {

  @Inject
  private PlayerManager playerManager;

  @GET
  @Path("/token")
  public Response requestToken() {
    UserDetails user = UserHelper.getUserDetails();
    return Response.ok(playerManager.getToken(user.getUsername())).build();
  }

}
