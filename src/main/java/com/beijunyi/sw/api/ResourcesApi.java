package com.beijunyi.sw.api;

import java.io.*;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.beijunyi.sw.output.PaletteManager;
import com.beijunyi.sw.output.TextureManager;

@Named
@Singleton
@Path("/api/resources")
public class ResourcesApi {

  private final PaletteManager pm;
  private final TextureManager tm;

  public ResourcesApi(PaletteManager pm, TextureManager tm) {
    this.pm = pm;
    this.tm = tm;
  }

  @GET
  @Path("/texture/{id}.jpg")
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

}
