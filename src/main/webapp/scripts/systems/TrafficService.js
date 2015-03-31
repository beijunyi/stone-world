app.service('TrafficService', function() {
  var queue = [];
  var count = 0;
  return {
    onRequest: function() {
      count++;
    },
    onResponse: function() {
      if(--count == 0) {
        angular.forEach(queue, function(fn) {
          fn();
        });
      }
    },
    whenReady: function(fn) {
      if(count != 0)
        queue.push(fn);
      else
        fn();
    }
  }
});

app.config(function($httpProvider) {
  $httpProvider.interceptors.push(function(TrafficService) {
    return {
      request: function(config) {
        TrafficService.onRequest(config);
      },
      response: function(response) {
        TrafficService.onResponse(response);
      }
    }
  });
});