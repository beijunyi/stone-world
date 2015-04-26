app.service('SceneService', function($q, ResourcesService, TrafficService, SceneConstants, TrafficConstants) {

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
        ResourcesService.scene(id).then(function(scene) {
          cache[id] = scene;
          TrafficService.dequeue(TrafficConstants.SCENE, id)
        });
      }
      active = id;
    },

    move: function(east, south) {
      var scene = cache[active];

      var bufferEastStart = Math.max(0, east - SceneConstants.BUFFER_RADIUS);
      var bufferSouthStart = Math.max(0, south - SceneConstants.BUFFER_RADIUS);
      var bufferEastEnd = Math.min(scene.east, east - SceneConstants.BUFFER_RADIUS);
      var bufferSouthEnd = Math.min(scene.south, south - SceneConstants.BUFFER_RADIUS);
      var e, s;
      for(s = bufferSouthStart; s < bufferSouthEnd; s++) {
        for(e = bufferEastStart; e < bufferEastEnd; e++) {

        }
      }

      var renderEastStart = Math.max(0, east - SceneConstants.RENDER_RADIUS);
      var renderSouthStart = Math.max(0, south - SceneConstants.RENDER_RADIUS);
      var renderEastEnd = Math.min(scene.east, east - SceneConstants.RENDER_RADIUS);
      var renderSouthEnd = Math.min(scene.south, south - SceneConstants.RENDER_RADIUS);

    },

    getScene: function() {
      return cache[active];
    }
  }
});