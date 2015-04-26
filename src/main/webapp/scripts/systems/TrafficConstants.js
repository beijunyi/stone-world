app.service('TrafficConstants', function() {

  var types = {
    SCENE   : 1,
    TEXTURE : 2
  };

  return {
    SCENE: types.SCENE,
    TEXTURE: types.TEXTURE,

    SCENES_ONLY   : [types.SCENE],
    TEXTURES_ONLY : [types.TEXTURE],

    ALL_TYPES : [types.SCENE, types.TEXTURE]
  }
});
