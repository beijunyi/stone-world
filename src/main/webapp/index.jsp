<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
  <base href="/">
  <meta charset="utf-8"/>
  <title>ch</title>

  <jwr:style src="/lib.css"/>
  <jwr:style src="/app.css"/>

</head>
<body  ng-app="app">

<div ng-controller="MainController">
  <pixi params="pixiParams"></pixi>
</div>

<jwr:script src="/lib.js"/>
<jwr:script src="/app.js"/>
</body>

</html>