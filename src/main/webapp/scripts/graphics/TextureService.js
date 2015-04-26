app.service('TextureService', function($q, Canvas, ResourcesService, TrafficConstants, TrafficService) {

  var cache = {};

  return {

    isTextureReady: function(id) {
      return cache[id] != null && cache[id] != false;
    },

    prepareTexture: function(ids) {
      angular.forEach(ids, function(id) {
        if(cache[id] == null) {
          cache[id] = false;
          TrafficService.enqueue(TrafficConstants.TEXTURE, id);
          ResourcesService.texture(id).then(function(texture) {
            var canvas = new Canvas(texture);
            cache[id] = PIXI.Texture.fromCanvas(canvas);
            TrafficService.dequeue(TrafficConstants.TEXTURE, id)
          });
        }
      });
    }

  }
});