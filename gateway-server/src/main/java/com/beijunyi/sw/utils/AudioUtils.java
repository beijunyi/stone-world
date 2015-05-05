package com.beijunyi.sw.utils;

public class AudioUtils {


  // 0 silent
  // impact audio 1-9
  // weapon and misc 10-29
  // pre-attack 30-127
  public static short mapAnimationAudio(short saAnimationAudio) {

    if(saAnimationAudio >= 50 && saAnimationAudio < 82)
      return (short) (saAnimationAudio - 21); // 30~61 = sae_01 ~ sae_32

    if(saAnimationAudio > 0 && saAnimationAudio < 15)
      return (short) (saAnimationAudio + 9); // 10~23 = sap_01 ~ sap_14

    if(saAnimationAudio == 250)
      saAnimationAudio = 16; // sad_01
    else if(saAnimationAudio == 252)
      saAnimationAudio = 20; // sad_03
    else if(saAnimationAudio == 255)
      saAnimationAudio = 17; // sad_05
    else if(saAnimationAudio >= 251)
      saAnimationAudio = 18; // sad_02
    else if(saAnimationAudio == 103)
      saAnimationAudio = 16; // sad_01
    else if(saAnimationAudio >= 124)
      saAnimationAudio = 17; // sad_05
    else if(saAnimationAudio >= 100 && saAnimationAudio < 116)
      return 49; // sae_20

    if(saAnimationAudio >= 116 && saAnimationAudio <= 120)
      saAnimationAudio -= 100;  // e.g 117 -> 17

    if(saAnimationAudio >= 16 && saAnimationAudio <= 20) {
      switch(saAnimationAudio) {
        case 16:
          return 2 + 128; // sad_01 + impact
        case 17:
          return 3 + 128; // sad_05 + impact
        case 20:
          return 4 + 128; // sad_03 + impact
        default:
          return 1 + 128; // sad_02 + impact
      }
    }

    return 0;
  }

}
