app.factory('Stage', function(StageConstants) {
  var stage = new PIXI.Stage(StageConstants.BACKGROUND);
  var tiles = new PIXI.DisplayObjectContainer();
  var objects = new PIXI.DisplayObjectContainer();
  stage.addChild(tiles);
  stage.addChild(objects);
  var renderer = PIXI.autoDetectRenderer(StageConstants.WIDTH, StageConstants.HEIGHT);
  return {
    tiles: tiles,
    objects: objects
  }
});