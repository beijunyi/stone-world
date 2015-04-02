app.service('StageService', function() {
  var stage;
  var tiles;
  var objects;
  var effectLayer;
  var renderer;

  var tickers = [];

  var count = 0;
  var max = 0;
  function setSpeedFactor(factor) {
    count = 0;
    max = factor;
  }
  function resetSpeedFactor() {
    setSpeedFactor(0);
  }
  function tickRenderer() {
    var d = (count - max) | 0;
    if(d == 0) {
      count -= max;
      return true;
    } else {
      count++;
      return false;
    }
  }

  return {
    initialize: function(background, width, height) {
      if(renderer == null) {
        stage = new PIXI.Stage(background);
        tiles = new PIXI.DisplayObjectContainer();
        objects = new PIXI.DisplayObjectContainer();
        effectLayer = new PIXI.DisplayObjectContainer();
        stage.addChild(tiles);
        stage.addChild(objects);
        stage.addChild(effectLayer);
        renderer = PIXI.autoDetectRenderer(width, height);
      }
      return renderer.view;
    },

    start: function() {
      (function animate() {
        requestAnimFrame(animate);
        if(tickRenderer) {
          angular.forEach(tickers, function(ticker) {
            ticker._tick();
          });
          angular.forEach(stage.children, function(layer) {
            layer.children.sort(function(a, b) {
              if(a.y != b.y)
                return a.y - b.y;
              else
                return a.x - b.x;
            });
          });
          renderer.render(stage);
        }
      })();
    },

    stage: function() {
      return stage;
    },

    tileLayer: function() {
      return tiles;
    },

    objectLayer: function() {
      return objects;
    },

    effectLayer: function() {
      return effectLayer;
    },

    addTicker: function(ticker) {
      tickers.push(ticker);
    }
  }
});