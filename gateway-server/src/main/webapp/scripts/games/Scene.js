app.factory('Scene', function() {
  return function(raw) {
    var data = new DataView(raw);
    var pos = 0;
    var nameLength = data.getUint8(pos++);
    this.name = new TextDecoder().decode(raw.slice(pos, pos += nameLength));
    this.east = data.getUint16(pos);
    pos += 2;
    this.south = data.getUint16(pos);
    pos += 2;
    var total = this.east * this.south;
    this.tiles = [];
    var i, s, e, row;
    for(i = 0, s = 0, e = 0; i < total; i++) {
      if(e == 0) {
        row = [];
        this.tiles.push(row);
      }
      row.push(data.getUint32(pos));
      pos += 4;
      if(++e == this.east) {
        e = 0;
        s++;
      }
    }
    this.objects = [];
    for(i = 0, s = 0, e = 0; i < total; i++) {
      if(e == 0) {
        row = [];
        this.objects.push(row);
      }
      row.push(data.getUint32(pos));
      pos += 4;
      if(++e == this.east) {
        e = 0;
        s++;
      }
    }
  };
});