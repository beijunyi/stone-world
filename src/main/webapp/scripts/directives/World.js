app.directive('world', function(ResourcesService, PalettesService, SceneService) {
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
    }
  };
});