app.directive('world', function(SceneService, TrafficService, TrafficConstants) {
  return {
    restrict: 'E',
    scope: {
      params: '='
    },
    controller: function($scope) {
    },
    link: function($scope, $element) {
      SceneService.prepareScene(2000);
      TrafficService.waitFor(TrafficConstants.SCENES_ONLY, function() {
        SceneService.move(100, 100);
      });
      TrafficService.waitFor(TrafficConstants.ALL_TYPES, function() {
        console.log('hi');
      });
    }
  };
});