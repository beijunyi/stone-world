app.service('GameSocket', function($q, $timeout, GameApi) {

  var connection;
  var queue;

  function waitAndResolveConnection(deferred) {
    if(connection.readyState == 1)
      deferred.resolve(connection);
    else {
      $timeout(function() {
        waitAndResolveConnection(deferred);
      }, 500);
    }
  }

  function makeConnection(socket) {
    var deferred = $q.defer();
    waitAndResolveConnection(socket);
    return deferred.promise;
  }

  function resolveQueue() {
    angular.forEach(queue, function(deferred) {
      deferred.resolve(connection);
    });
  }

  function queueRequest(deferred) {
    if(queue == null) {
      queue = [];
      GameApi.token().then(function(token) {
        makeConnection(new WebSocket(token.url)).then(function() {
          resolveQueue();
        });
      });
    }
    queue.push(deferred);
  }

  function tryResolve(deferred) {
    if(connection == null)
      queueRequest(deferred);
    else
      deferred.resolve(connection);
  }

  return {
    getConnection: function() {
      var deferred = $q.defer();
      tryResolve(deferred);
      return deferred.promise;
    }
  }
});