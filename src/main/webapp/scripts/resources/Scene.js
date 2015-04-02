app.factory('Scene', function() {
  return function(data) {
    data = new DataView(data);
    var pos = 0;
    var nameLength = data.getUint8(pos++);
    this.name = new TextDecoder().decode(data.slice(pos, pos += nameLength));
    this.east = data.getUint16(pos);
    pos += 2;
    this.south = data.getUint16(pos);
    pos += 2;
    var total = this.east * this.south;
    this.tiles = [];
    var i;
    for(i = 0; i < total; i++) {
      this.tiles.push(data.getUint32(pos));
      pos += 4;
    }
    this.objects = [];
    for(i = 0; i < total; i++) {
      this.objects.push(data.getUint32(pos));
      pos += 4;
    }
  };
});