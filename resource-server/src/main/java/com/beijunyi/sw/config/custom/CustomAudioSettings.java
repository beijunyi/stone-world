package com.beijunyi.sw.config.custom;

import java.util.Map;

public class CustomAudioSettings {
  private Map<String, AudioMapping> mappings;

  public Map<String, AudioMapping> getMappings() {
    return mappings;
  }

  public void setMappings(Map<String, AudioMapping> mappings) {
    this.mappings = mappings;
  }
}
