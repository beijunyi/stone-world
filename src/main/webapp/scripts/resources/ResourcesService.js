app.service('ResourcesService', function($http, $q, Palette, Scene, Texture) {

  return {
    palette: function(id) {
      var deferred = $q.defer();
      $http.get('/api/resources/palette/' + id + ".bin", {responseType: "arraybuffer"}).success(function(data) {
        deferred.resolve(new Palette(data));
      });
      return deferred.promise;
    },
    scene: function(id) {
      var deferred = $q.defer();
      $http.get('/api/resources/scene/' + id + ".bin", {responseType: "arraybuffer"}).success(function(data) {
        deferred.resolve(new Scene(data));
      });
      return deferred.promise;
    },
    texture: function(id) {
      var deferred = $q.defer();
      $http.get('/api/resources/texture/' + id + ".bin", {responseType: "arraybuffer"}).success(function(data) {
        deferred.resolve(new Texture(data));
      });
      return deferred.promise;
    }
  }
});