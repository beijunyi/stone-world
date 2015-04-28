app.factory('Palette', function(ResourceLoader, TrafficService, TrafficConstants, PaletteConstants) {
  var colors = false;
  TrafficService.enqueue(TrafficConstants.PALETTE, PaletteConstants.PALETTE_ID);
  ResourceLoader.palette(PaletteConstants.PALETTE_ID).then(function(raw) {
    colors = [];
    var data = new Uint8Array(raw);
    for(var i = 0; i < 256; i++) {
      var rgba = [
        data[i * 3],
        data[i * 3 + 1],
        data[i * 3 + 2],
        data[i * 3 + 3]
      ];
      colors.push(rgba);
    }
    TrafficService.dequeue(TrafficConstants.PALETTE, PaletteConstants.PALETTE_ID);
  });

  return {
    getRGBA: function(index) {
      return colors[index];
    }
  };
});