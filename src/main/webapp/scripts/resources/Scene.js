app.factory('Scene', function() {
  return function(data) {
    var pos = 0;
    var nameLength = data.getUint8(pos++);
    this.name = new TextDecoder().decode(new DataView(data, pos, nameLength));
    pos += nameLength;
    this.east = data.getUint16(pos);
    pos += 2;
    this.south = data.getUint16(pos);
    pos += 2;
    var total = this.east * this.south;
    this.tiles = new Uint32Array(data, pos, total);
    pos += total * 4;
    this.objects = new Uint32Array(data, pos, total);
  };
});