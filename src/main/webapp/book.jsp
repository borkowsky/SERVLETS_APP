<%--
  Created by IntelliJ IDEA.
  User: rewerk
  Date: 19.02.25
  Time: 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="includes/vars.jsp" %>
<c:set var="book" value='<%=request.getAttribute("book")%>' />
<!doctype html>
<html lang="en">
  <head>
    <%@include file="includes/head.jsp" %>
    <c:if test="${book == null}">
      <title>Book not found</title>
    </c:if>
    <c:if test="${book != null}">
      <title><c:out value="${book.title}" /></title>
    </c:if>
  </head>
  <body>
  <%@include file="components/header.jsp"%>
    <c:if test="${book != null}">
      <div class="book_info_block">
        <div class="cover">
          <div class="book_cover">
            <i class="ri-book-2-fill"></i>
          </div>
        </div>
        <div class="info">
          <h1><c:out value="${book.title}" /></h1>
          <div>
            By <c:out value="${book.author}" />, <c:out value="${book.year}" />
            <br>
            <c:out value="${book.pages}" /> pages
          </div>
        </div>
      </div>
      <a href="${contextPath}/books" class="button">
        Back
      </a>
    </c:if>
    <c:if test="${book == null}">
      <h1>Book not found</h1>
      <div class="info_block">
        <a href="${contextPath}/books" class="button">
          Go back
        </a>
      </div>
    </c:if>
  </body>
</html>
