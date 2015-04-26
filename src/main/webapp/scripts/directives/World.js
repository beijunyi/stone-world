app.directive('world', function(ResourcesService, PalettesService, SceneService, TrafficService, TrafficConstants) {
  return {
    restrict: 'E',
    scope: {
      params: '='
    },
    controller: function($scope) {
    },
    link: function($scope, $element) {
      PalettesService.preparePalette(1);
      SceneService.prepareScene(2000);
      TrafficService.waitFor(TrafficConstants.SCENES_ONLY, function() {
        SceneService.move(100, 100);
      });
    }
  };
});