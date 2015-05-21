app.service('GameSocket', function(GameApi) {

  var socket;
  GameApi.token().then(function(token) {
    socket = new WebSocket(token.url)
  });

  return {

  }
});