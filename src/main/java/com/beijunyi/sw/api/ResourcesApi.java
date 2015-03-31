package com.beijunyi.sw.api;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.beijunyi.sw.output.PaletteManager;
import com.beijunyi.sw.output.SceneManager;
import com.beijunyi.sw.output.TextureManager;

@Named
@Singleton
@Path("/api/resources")
public class ResourcesApi {

  private final PaletteManager pm;
  private final SceneManager sm;
  private final TextureManager tm;

  public ResourcesApi(PaletteManager pm, SceneManager sm, TextureManager tm) {
    this.pm = pm;
    this.sm = sm;
    this.tm = tm;
  }

  @GET
  @Path("/texture/{id}.bin")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getTexture(@PathParam("id") int id) {
    return Response.ok(tm.getTexture(id)).build();
  }

  @GET
  @Path("/palette/{id}.bin")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getPalette(@PathParam("id") int id) {
    return Response.ok(pm.getPalette(id)).build();
  }

  @GET
  @Path("/scene/{id}.bin")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getScene(@PathParam("id") int id) {
    return Response.ok(sm.getScene(id)).build();
  }

}
