app.service('TrafficService', function($timeout, TrafficConstants) {
  var queue = [];
  var count = 0;

  var queues = {};
  var fns = {};
  angular.forEach(TrafficConstants.ALL_TYPES, function(type) {
    queues[type] = [];
    fns[type] = [];
  });

  return {
    onRequest: function() {
      count++;
    },
    onResponse: function() {
      if(--count == 0) {
        $timeout(function() {
          angular.forEach(queue, function(fn) {
            fn();
          });
        });
      }
    },
    whenReady: function(fn) {
      if(count != 0)
        queue.push(fn);
      else
        fn();
    },

    enqueue: function(type, id) {
      queues[type].push(id);
    },

    dequeue: function(type, id) {
      var queue = queues[type];
      var index = queue.indexOf(id);
      queue.splice(index, 1);
      if(queue.length == 0) {
        angular.forEach(fns[type], function(fn) {
          fn();
        });
      }
    },

    waitFor: function(types, fn) {
      fn.pending = types.slice(0);
      angular.forEach(types, function(type) {
        fns[type].push(function() {
          var index = fn.pending.indexOf(type);
          fn.pending.splice(index, 1);
          if(fn.pending.length == 0)
            fn();
        });
      });
    }

  }
});

app.config(function($httpProvider) {
  $httpProvider.interceptors.push(function(TrafficService) {
    return {
      request: function(config) {
        TrafficService.onRequest(config);
        return config;
      },
      response: function(response) {
        TrafficService.onResponse(response);
        return response;
      }
    }
  });
});