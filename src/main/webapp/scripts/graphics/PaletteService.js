app.service('PaletteService', function($q, ResourcesService) {

  var active = null;
  var palettes = {};

  return {
    preparePalette: function(id) {
      if(palettes[id] == null) {
        ResourcesService.palette(id).then(function(palette) {
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