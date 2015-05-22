app.service('GameApi', function($http, $q, $timeout) {

  var prefix = '/api/game/';

  return {
    token: function() {
      var deferred = $q.defer();
      function requestToken(retry, delay) {
        $http.get(prefix + 'token').success(function(resp) {
          if(angular.isObject(resp)) {
            deferred.resolve(resp);
          } else {
            if(retry == 0) {
              deferred.reject('Cannot obtain login token');
            } else {
              $timeout(function() {
                requestToken(retry - 1, delay);
              }, delay)
            }
          }
        });
      }
      requestToken(20, 3000);
      return deferred.promise;
    }

  }
});