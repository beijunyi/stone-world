app.service('TextureService', function($q, Texture, ResourceLoader, TrafficConstants, TrafficService) {

  var cache = {};

  return {

    isTextureReady: function(id) {
      return cache[id] != null && cache[id] != false;
    },

    prepareTexture: function(id) {
      if(cache[id] == null) {
        cache[id] = false;
        TrafficService.enqueue(TrafficConstants.TEXTURE, id);
        ResourceLoader.texture(id).then(function(raw) {
          cache[id] = new Texture(raw);
          TrafficService.dequeue(TrafficConstants.TEXTURE, id)
        });
      }
    }
  }
});