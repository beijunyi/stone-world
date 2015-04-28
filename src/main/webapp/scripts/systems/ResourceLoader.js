app.service('ResourceLoader', function($http, $q) {

  return {
    palette: function(id) {
      var deferred = $q.defer();
      $http.get('/api/resources/palette/' + id + ".bin", {responseType: "arraybuffer"}).success(function(data) {
        deferred.resolve(data);
      });
      return deferred.promise;
    },
    scene: function(id) {
      var deferred = $q.defer();
      $http.get('/api/resources/scene/' + id + ".bin", {responseType: "arraybuffer"}).success(function(data) {
        deferred.resolve(data);
      });
      return deferred.promise;
    },
    texture: function(id) {
      var deferred = $q.defer();
      $http.get('/api/resources/texture/' + id + ".bin", {responseType: "arraybuffer"}).success(function(data) {
        deferred.resolve(data);
      });
      return deferred.promise;
    }
  }
});