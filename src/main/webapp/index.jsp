<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
  <link rel="icon" type="image/x-icon" href="favicon.ico">
  <base href="/">
  <meta charset="utf-8"/>
  <title>stone-world</title>

  <jwr:style src="/lib.css"/>
  <jwr:style src="/app.css"/>

</head>
<body  ng-app="app">

<world></world>

<jwr:script src="/lib.js"/>
<jwr:script src="/app.js"/>
</body>

</html>