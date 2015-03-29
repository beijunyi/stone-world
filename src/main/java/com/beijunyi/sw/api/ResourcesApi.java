package com.beijunyi.sw.api;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.ContentEncoding;
import org.jboss.resteasy.annotations.GZIP;

@Named
@Singleton
@Path("/api/resources")
public class ResourcesApi {

  @GET
  @Path("/image/1.jpg")
  @Produces("image/jpg")
  @GZIP
  public Response getImage() {
    return Response.ok(new File("G:/origin.jpg")).build();
  }

  @GET
  @Path("/image/2.jpg")
  @Produces("image/jpg")
  public Response getImageGz() {
    return Response.ok(new File("G:/origin.jpg.gz")).header(HttpHeaders.CONTENT_ENCODING, "gzip").build();
  }

}
