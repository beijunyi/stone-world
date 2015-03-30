app.service('PalettesService', function($q, ResourcesApi) {

  var active = null;
  var palettes = {};

  return {
    initialize: function(id) {
      var deferred = $q.defer();
      active = id;
      if(palettes[id] != null)
        deferred.resolve(palettes[id]);
      else {
        ResourcesApi.palette(id).success(function(paletteData) {
          palettes[id] = new Uint8Array(paletteData);
          deferred.resolve(palettes[id]);
        });
      }
      return deferred.promise;
    },

    getPalette: function() {
      return palettes[active];
    }
  }
});