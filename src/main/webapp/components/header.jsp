<%--
  Created by IntelliJ IDEA.
  User: rewerk
  Date: 18.02.25
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="net.rewerk.servlets.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="app_header">
  <a class="logo" href="${pageContext.request.contextPath}/" title="To main page">
    servlets app
  </a>
  <c:choose>
    <c:when test="${authenticated && user != null}">
      <a class="userinfo flat_button" href="${pageContext.request.contextPath}/user" title="To user area page">
        <div>
          <c:out value="${user.username.charAt(0)}" />
        </div>
        <div>
          <c:out value="${user.username}" />
        </div>
      </a>
    </c:when>
    <c:otherwise>
      <a class="flat_primary_button" href="${pageContext.request.contextPath}/login">
        Login
      </a>
      <a class="flat_primary_button" href="${pageContext.request.contextPath}/registration">
        Registration
      </a>
    </c:otherwise>
  </c:choose>
</div>
