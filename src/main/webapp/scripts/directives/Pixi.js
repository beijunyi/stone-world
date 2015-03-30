app.directive('pixi', function() {
  return {
    restrict: 'E',
    scope: {
      params: '='
    },
    controller: function($scope) {
    },
    link: function($scope, $element) {
      $scope.stage = new PIXI.Stage(0xffffff);
      $scope.renderer = PIXI.autoDetectRenderer($scope.params.width, $scope.params.height);
      $element.append($scope.renderer.view);
    }
  };
});