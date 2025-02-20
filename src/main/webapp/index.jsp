<%--
  Created by IntelliJ IDEA.
  User: rewerk
  Date: 17.02.25
  Time: 20:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="includes/vars.jsp" %>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="includes/head.jsp" %>
    <title>Main page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
  </head>
  <body>
    <%@include file="components/header.jsp" %>
    <h1>Servlets APP main page</h1>
    <c:choose>
      <c:when test="${authenticated}">
        <div class="info_block">
          Logged in as <strong><c:out value="${user.username}"/></strong>
        </div>
        <a href="${contextPath}/books" class="primary_button">
          Books
        </a>
      </c:when>
      <c:otherwise>
        <div class="info_block">
          Not logged in
        </div>
        <div>
          <a href="${contextPath}/login" class="flat_primary_button">Login</a>
          <a href="${contextPath}/registration" class="flat_primary_button">Registration</a>
        </div>
      </c:otherwise>
    </c:choose>
  </body>
</html>
