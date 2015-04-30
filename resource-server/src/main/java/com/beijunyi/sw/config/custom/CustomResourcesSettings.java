package com.beijunyi.sw.config.custom;

public class CustomResourcesSettings {
  private CustomTextureSettings textures;
  private CustomAnimationSettings animations;
  private CustomAudioSettings audios;

  public CustomTextureSettings getTextures() {
    return textures;
  }

  public void setTextures(CustomTextureSettings textures) {
    this.textures = textures;
  }

  public CustomAnimationSettings getAnimations() {
    return animations;
  }

  public void setAnimations(CustomAnimationSettings animations) {
    this.animations = animations;
  }

  public CustomAudioSettings getAudios() {
    return audios;
  }

  public void setAudios(CustomAudioSettings audios) {
    this.audios = audios;
  }
}
