<%--
  Created by IntelliJ IDEA.
  User: rewerk
  Date: 19.02.25
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="net.rewerk.servlets.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    Boolean authenticated = (Boolean) request.getAttribute("authenticated");
    User user = (User) request.getAttribute("user");
    String contextPath = request.getContextPath();
%>
<c:set var="authenticated" value="<%=authenticated%>" />
<c:set var="user" value="<%=user%>" />
<c:set var="contextPath" value="<%=contextPath%>" />
