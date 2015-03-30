app.directive('world', function(ResourcesApi, PalettesService, StageService) {
  return {
    restrict: 'E',
    scope: {
      params: '='
    },
    controller: function($scope) {
    },
    link: function($scope, $element) {
      PalettesService.initialize(1).then(function() {
        $element.append(StageService.initialize(0x000000, 1200 , 675));
      });
    }
  };
});