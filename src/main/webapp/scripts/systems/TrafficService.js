app.service('TrafficService', function($timeout, TrafficConstants) {
  var queues = {};
  var fns = {};
  angular.forEach(TrafficConstants.ALL_TYPES, function(type) {
    queues[type] = [];
    fns[type] = [];
  });

  return {
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