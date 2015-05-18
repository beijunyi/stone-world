app.service('TrafficConstants', function() {

  var types = {
    SYSTEM:  0,
    PALETTE: 1,
    SCENE  : 2,
    TEXTURE: 3
  };

  return {
    SYSTEM: types.SYSTEM,
    PALETTE: types.PALETTE,
    SCENE: types.SCENE,
    TEXTURE: types.TEXTURE,

    SYSTEMS_ONLY    : [types.SYSTEM],
    PALETTES_ONLY   : [types.PALETTE],
    SCENES_ONLY     : [types.SCENE],
    TEXTURES_ONLY   : [types.TEXTURE],
    ALL_TYPES       : [types.SYSTEM, types.PALETTE, types.SCENE, types.TEXTURE],

    SCENES_ANDS_TEXTURES : [types.SCENE, types.TEXTURE]
  }
});
