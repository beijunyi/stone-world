app.factory('Palette', function() {
  return function(data) {
    var flatColors = new Uint8Array(data);
    this.colors = [];
    for(var i = 0; i < 256; i++) {
      var rgba = [
        flatColors[i * 3],
        flatColors[i * 3 + 1],
        flatColors[i * 3 + 2],
        flatColors[i * 3 + 3]
      ];
      this.colors.push(rgba);
    }
  };
});