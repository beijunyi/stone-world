package com.beijunyi.sw.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.beijunyi.sw.AppConstants;
import com.beijunyi.sw.config.custom.CustomResourcesSettings;
import com.esotericsoftware.kryo.Kryo;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.jgroups.JChannel;
import org.springframework.context.annotation.*;

@Configuration
@Import({GatewayServerSecurityConfig.class})
@ComponentScan(basePackages = "com.beijunyi.sw")
public class GatewayServerConfig {

  @Bean
  public Kryo kryo() {
    Kryo kryo = new Kryo();
    kryo.setReferences(false);
    return kryo;
  }

  @Bean
  public CustomResourcesSettings getCustomResourcesSettings() throws IOException {
    ObjectMapper mapper = getObjectMapper();
    try(InputStream is = getClass().getResourceAsStream("/custom_resources.json")) {
      return mapper.readValue(IOUtils.toByteArray(is), CustomResourcesSettings.class);
    }
  }

  @Bean
  public Settings getSettings() {
    Settings settings = new Settings();

    String gmsvData = System.getProperty("gmsv.data");
    if(gmsvData == null)
      throw new IllegalArgumentException("Missing property \"gmsv.data\"");
    Path gmsvDataPath = Paths.get(gmsvData);
    if(!Files.isDirectory(gmsvDataPath))
      throw new IllegalArgumentException("Invalid \"gmsv.data\" path: " + gmsvData);
    settings.setGmsvDataPath(gmsvDataPath);

    String saData = System.getProperty("client.data");
    if(saData == null)
      throw new IllegalArgumentException("Missing property \"client.data\"");
    Path saDataPath = Paths.get(saData);
    if(!Files.isDirectory(saDataPath))
      throw new IllegalArgumentException("Invalid  \"client.data\" path: " + saData);
    settings.setSaDataPath(saDataPath);

    String output = System.getProperty("output");
    if(output == null)
      throw new IllegalArgumentException("Missing property \"output\"");
    Path outputPath = Paths.get(output);
    try {
      Files.createDirectories(outputPath);
    } catch(IOException e) {
      throw new IllegalArgumentException("Cannot create \"output\" path: " + outputPath, e);
    }
    settings.setOutputPath(outputPath);

    return settings;
  }

  @Bean
  public JChannel jChannel() throws Exception {
    JChannel channel = new JChannel();
    channel.connect(System.getProperty("cluster.name", AppConstants.DEFAULT_CLUSTER));
    return channel;
  }

  @Bean
  public ObjectMapper getObjectMapper() {
    return new ObjectMapper();
  }

}
