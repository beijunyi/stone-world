app.service('PalettesService', function($q, ResourcesService) {

  var active = null;
  var palettes = {};

  return {
    usePalette: function(id) {
      if(palettes[id] == null) {
        ResourcesService.palette(id).success(function(palette) {
          palettes[id] = palette;
        });
      }
      active = id;
    },

    getPalette: function() {
      return palettes[active];
    }
  }
});