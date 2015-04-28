app.service('ResourceApi', function($http, $q) {

  var prefix = '/api/resources/';
  
  return {
    palette: function(id) {
      var deferred = $q.defer();
      $http.get(prefix + 'palette/' + id + '.bin', {responseType: 'arraybuffer'}).success(function(data) {
        deferred.resolve(data);
      });
      return deferred.promise;
    },
    scene: function(id) {
      var deferred = $q.defer();
      $http.get(prefix + 'scene/' + id + '.bin', {responseType: 'arraybuffer'}).success(function(data) {
        deferred.resolve(data);
      });
      return deferred.promise;
    },
    texture: function(id) {
      var deferred = $q.defer();
      $http.get(prefix + 'texture/' + id + '.bin', {responseType: 'arraybuffer'}).success(function(data) {
        deferred.resolve(data);
      });
      return deferred.promise;
    }
  }
});