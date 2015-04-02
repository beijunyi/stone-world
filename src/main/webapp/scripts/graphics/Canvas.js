app.factory('Canvas', function($q, PaletteService) {
  return function(texture) {
    this.x = texture.x;
    this.y = texture.y;
    this.width = texture.width;
    this.height = texture.height;

    var canvas = document.createElement('canvas');
    canvas.width = texture.width;
    canvas.height = texture.height;
    var context = canvas.getContext('2d');
    var imageData = context.createImageData(texture.width, texture.height);
    var total = texture.width * texture.height;
    var pal = PaletteService.getPalette();
    var write = 0;
    for(var pix = 0; pix < total; pix++) {
      var index = texture.bitmap[pix];
      var rgba = pal.colors[index];
      for(var color = 0; color < 4; color++) {
        imageData.data[write++] = rgba[color];
      }
    }
    this.element = canvas;
  }
});