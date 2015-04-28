app.service('SceneLoader', function($q, Scene, ResourceApi, TextureService, TrafficService, SceneConstants, TrafficConstants) {

  var active = null;
  var cache = {};

  return {

    isSceneReady: function(id) {
      return cache[id] != null && cache[id] != false;
    },

    prepareScene: function(id) {
      if(cache[id] == null) {
        cache[id] = false;
        TrafficService.enqueue(TrafficConstants.SCENE, id);
        ResourceApi.scene(id).then(function(raw) {
          cache[id] = new Scene(raw);
          TrafficService.dequeue(TrafficConstants.SCENE, id)
        });
      }
      active = id;
    },

    move: function(east, south) {
      var scene = cache[active];

      var bufferEastStart = Math.max(0, east - SceneConstants.BUFFER_RADIUS);
      var bufferSouthStart = Math.max(0, south - SceneConstants.BUFFER_RADIUS);
      var bufferEastEnd = Math.min(scene.east, east + SceneConstants.BUFFER_RADIUS);
      var bufferSouthEnd = Math.min(scene.south, south + SceneConstants.BUFFER_RADIUS);
      var e, s, tile, object;
      for(s = bufferSouthStart; s < bufferSouthEnd; s++) {
        for(e = bufferEastStart; e < bufferEastEnd; e++) {
          tile = scene.tiles[s][e];
          if(tile != 0)
            TextureService.prepareTexture(tile);
          object = scene.objects[s][e];
          if(object != 0)
            TextureService.prepareTexture(object);
        }
      }

      var ready = true;
      var renderEastStart = Math.max(0, east - SceneConstants.RENDER_RADIUS);
      var renderSouthStart = Math.max(0, south - SceneConstants.RENDER_RADIUS);
      var renderEastEnd = Math.min(scene.east, east - SceneConstants.RENDER_RADIUS);
      var renderSouthEnd = Math.min(scene.south, south - SceneConstants.RENDER_RADIUS);
      for(s = renderSouthStart; s < renderSouthEnd; s++) {
        for(e = renderEastStart; e < renderEastEnd; e++) {
          tile = scene.tiles[s][e];
          if(tile != 0)
            ready &= TextureService.isTextureReady(tile);
          object = scene.objects[s][e];
          if(object != 0)
            ready &= TextureService.isTextureReady(object);
        }
      }

    },

    getScene: function() {
      return cache[active];
    }
  }
});