package com.beijunyi.sw.sa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ClientResource implements SaResource {
  ADRN("^adrn_(\\d+)\\.bin$"),
  REAL("^real_(\\d+)\\.bin$"),
  SPR("^spr_(\\d+)\\.bin$"),
  SPRADRN("^spradrn_(\\d+)\\.bin$"),
  PALET("^palet_(\\d+)\\.sap"),
  AUDIO_SAD("^sad_(\\d+)\\.wav$"),
  AUDIO_SAE("^sae_(\\d+)\\.wav$"),
  AUDIO_SAP("^sap_(\\d+)\\.wav$"),
  ;


  private final Pattern pattern;

  ClientResource(String regex) {
    pattern = Pattern.compile(regex);
  }

  public String matches(String name) {
    Matcher matcher = pattern.matcher(name.toLowerCase());
    if(matcher.matches()) {
      return matcher.group(1);
    } else
      return null;
  }

  public Pattern getPattern() {
    return pattern;
  }
}
