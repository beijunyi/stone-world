app.factory('Texture', function() {
  return function(data) {
    data = new DataView(data);
    var pos = 0;
    this.x = data.getInt16(pos);
    pos += 2;
    this.y = data.getInt16(pos);
    pos += 2;
    this.width = data.getUint16(pos);
    pos += 2;
    this.height = data.getUint16(pos);
    pos += 2;
    this.bitmap = new Uint8Array(this.width * this.height, pos);
  };
});