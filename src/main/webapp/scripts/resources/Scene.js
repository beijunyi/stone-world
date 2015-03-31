app.factory('Scene', function() {
  return function(data) {
    var pos = 0;
    var nameLength = new DataView(data.slice(pos, ++pos)).getUint8(0);
    this.name = new TextDecoder().decode(new DataView(data.slice(pos, pos += nameLength)));
    this.east = new DataView(data.slice(pos, pos += 2)).getUint16(0);
    this.south = new DataView(data.slice(pos, pos += 2)).getUint16(0);
    var total = this.east * this.south;
    this.tiles = new Uint32Array(data.slice(pos, pos += total * 4));
    this.objects = new Uint32Array(data.slice(pos, pos + total * 4));
  };
});