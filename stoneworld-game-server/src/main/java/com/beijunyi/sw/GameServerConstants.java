package com.beijunyi.sw;

import java.nio.file.Path;

import static com.beijunyi.sw.AppConstants.APP_HOME;

public class GameServerConstants {

  public final static String MODULE_NAME = "Game Server";
  public final static Path MODULE_HOME = APP_HOME.resolve(MODULE_NAME);

  public final static String GAME_SERVER_URL_SUFFIX = "/ws/game";

}
