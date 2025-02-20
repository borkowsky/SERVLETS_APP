<%--
  Created by IntelliJ IDEA.
  User: rewerk
  Date: 19.02.25
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="includes/vars.jsp" %>
<html>
  <head>
    <%@include file="includes/head.jsp" %>
    <title>User page</title>
  </head>
  <body>
    <%@include file="components/header.jsp" %>
    <h1>User info page</h1>
    <div class="info_block">
      Logged in as <strong><c:out value="${user.username}"/></strong>
      <br>
      Role: <strong><c:out value="${user.role}"/></strong>
    </div>
    <a href="${contextPath}/logout" class="primary_button my-2">
      Logout
    </a>
  </body>
</html>
