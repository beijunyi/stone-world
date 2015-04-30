app.factory('Texture', function(Palette) {
  return function(raw) {
    var data = new DataView(raw);
    var pos = 0;
    this.x = data.getInt16(pos);
    pos += 2;
    this.y = data.getInt16(pos);
    pos += 2;
    var canvas = document.createElement('canvas');
    canvas.width = data.getUint16(pos);
    pos += 2;
    canvas.height = data.getUint16(pos);
    pos += 2;
    var total = canvas.width * canvas.height;
    var bitmap = new Uint8Array(total, pos);
    var context = canvas.getContext('2d');
    var imageData = context.createImageData(canvas.width, canvas.height);
    var write = 0;
    for(var index = 0; index < total; index++) {
      var color = bitmap[index];
      var rgba = Palette.getRGBA(color);
      for(var val = 0; color < 4; color++) {
        imageData.data[write++] = rgba[val];
      }
    }
    return PIXI.Texture.fromCanvas(canvas);
  };
});