<%--
  Created by IntelliJ IDEA.
  User: rewerk
  Date: 17.02.25
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="includes/vars.jsp" %>
<c:set var="errors" value='<%=request.getAttribute("errors")%>'/>
<c:set var="success" value='<%=request.getAttribute("success")%>'/>
<html>
  <head>
    <%@include file="includes/head.jsp" %>
    <title>Registration</title>
  </head>
  <body>
    <h1>Registration form</h1>
    <div class="info_block">
      Now registered <%=request.getAttribute("usersTotal")%> users
    </div>
    <c:if test="${errors != null}">
      <div class="error_block">
        Some errors occurred:
        <ul>
          <c:forEach var="err" items="${errors}">
            <li>${err}</li>
          </c:forEach>
        </ul>
      </div>
    </c:if>
    <c:choose>
      <c:when test="${success && errors == null}">
        <div class="success_block">
          Registration success
        </div>
        <a href="${contextPath}/" class="button">
          To main page
        </a>
      </c:when>
      <c:otherwise>
        <form action="${contextPath}/registration" method="post">
          <label for="usernameInput">
            Input username
          </label>
          <input type="text" name="username" id="usernameInput" placeholder="Input text">
          <label for="passwordInput">
            Input password
          </label>
          <input type="password" name="password" id="passwordInput" placeholder="Input text">
          <label for="passwordInputConfirmation">
            Repeat password
          </label>
          <input type="password" name="passwordConfirmation" id="passwordInputConfirmation" placeholder="Input text">
          <input type="submit" value="Register">
          <a href="${contextPath}/" class="button">Go back</a>
        </form>
      </c:otherwise>
    </c:choose>
  </body>
</html>
