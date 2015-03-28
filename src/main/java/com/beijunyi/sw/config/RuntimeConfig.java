package com.beijunyi.sw.config;

import java.io.*;

import com.beijunyi.sw.config.custom.CustomResourcesSettings;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;

@Configuration
@Import({KryoConfig.class})
@ComponentScan(basePackages = "com.beijunyi.sw")
public class RuntimeConfig {

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
    settings.setGmsvDataPath(gmsvData);

    String saData = System.getProperty("sa.data");
    if(saData == null)
      throw new IllegalArgumentException("Missing property \"sa.data\"");
    settings.setSaDataPath(saData);

    String output = System.getProperty("output");
    if(output == null)
      throw new IllegalArgumentException("Missing property \"output\"");
    settings.setOutputPath(output);

    return settings;
  }

  @Bean
  public ObjectMapper getObjectMapper() {
    return new ObjectMapper();
  }

}
