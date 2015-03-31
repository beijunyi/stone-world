package com.beijunyi.sw.api;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.beijunyi.sw.resources.PaletteManager;
import com.beijunyi.sw.resources.SceneManager;
import com.beijunyi.sw.resources.TextureManager;
import org.jboss.resteasy.annotations.GZIP;

@Named
@Singleton
@Path("/api/resources")
public class ResourcesApi {

  private final PaletteManager pm;
  private final SceneManager sm;
  private final TextureManager tm;

  @Inject
  public ResourcesApi(PaletteManager pm, SceneManager sm, TextureManager tm) {
    this.pm = pm;
    this.sm = sm;
    this.tm = tm;
  }

  @GET
  @Path("/texture/{id}.bin")
  @GZIP
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getTexture(@PathParam("id") int id) {
    return Response.ok(tm.getTextureData(id)).build();
  }

  @GET
  @Path("/palette/{id}.bin")
  @GZIP
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getPalette(@PathParam("id") int id) {
    return Response.ok(pm.getPaletteData(id)).build();
  }

  @GET
  @Path("/scene/{id}.bin")
  @GZIP
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getScene(@PathParam("id") int id) {
    return Response.ok(sm.getSceneData(id)).build();
  }

}
