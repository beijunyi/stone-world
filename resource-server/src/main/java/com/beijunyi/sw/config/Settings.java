package com.beijunyi.sw.config;

import java.nio.file.Path;

public class Settings {

  private Path saDataPath;
  private Path gmsvDataPath;
  private Path outputPath;

  public Path getSaDataPath() {
    return saDataPath;
  }

  public void setSaDataPath(Path saDataPath) {
    this.saDataPath = saDataPath;
  }

  public Path getGmsvDataPath() {
    return gmsvDataPath;
  }

  public void setGmsvDataPath(Path gmsvDataPath) {
    this.gmsvDataPath = gmsvDataPath;
  }

  public Path getOutputPath() {
    return outputPath;
  }

  public void setOutputPath(Path outputPath) {
    this.outputPath = outputPath;
  }
}
