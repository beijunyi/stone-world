app.service('ResourcesApi', function($http) {
  return {
    palette: function(id) {
      return $http.get('/api/resources/palette/' + id + ".bin", {responseType: "arraybuffer"});
    },
    texture: function(id) {
      return $http.get('/api/resources/texture/' + id + '.bin', {responseType: "arraybuffer"});
    }
  }
});