app.service('TrafficConstants', function() {

  var types = {
    PALETTE: 1,
    SCENE  : 2,
    TEXTURE: 3
  };

  return {
    PALETTE: types.PALETTE,
    SCENE: types.SCENE,
    TEXTURE: types.TEXTURE,

    PALETTES_ONLY   : [types.PALETTE],
    SCENES_ONLY   : [types.SCENE],
    TEXTURES_ONLY : [types.TEXTURE],
    ALL_TYPES : [types.PALETTE, types.SCENE, types.TEXTURE],

    SCENES_ANDS_TEXTURES : [types.SCENE, types.TEXTURE]
  }
});
