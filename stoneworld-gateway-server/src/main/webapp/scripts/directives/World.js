app.directive('world', function(CharacterStatus, SceneLoader, TrafficService, TrafficConstants) {
  return {
    restrict: 'E',
    scope: {
      params: '='
    },
    controller: function($scope) {
    },
    link: function($scope, $element) {
      SceneLoader.prepareScene(2000);
      TrafficService.waitFor(TrafficConstants.SCENES_ONLY, function() {
        SceneLoader.move(100, 100);
      });
      TrafficService.waitFor(TrafficConstants.ALL_TYPES, function() {
        console.log('hi');
      });
    }
  };
});