package com.beijunyi.sw.config.custom;

import java.util.Map;

public class AudioMapping {
  private Map<Integer, AudioSpecialMapping> specials;
  private AudioDefaultMapping defaults;

  public Map<Integer, AudioSpecialMapping> getSpecials() {
    return specials;
  }

  public void setSpecials(Map<Integer, AudioSpecialMapping> specials) {
    this.specials = specials;
  }

  public AudioDefaultMapping getDefaults() {
    return defaults;
  }

  public void setDefaults(AudioDefaultMapping defaults) {
    this.defaults = defaults;
  }
}
