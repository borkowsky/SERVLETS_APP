<%--
  Created by IntelliJ IDEA.
  User: rewerk
  Date: 19.02.25
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="includes/vars.jsp" %>
<c:set var="books" value='<%=request.getAttribute("books")%>'/>
<c:set var="action" value='<%=request.getParameter("action")%>'/>
<c:set var="operation" value='<%=request.getAttribute("operation")%>'/>
<c:set var="book" value='<%=request.getAttribute("book")%>'/>
<c:set var="success" value='<%=request.getAttribute("success")%>'/>
<c:set var="errors" value='<%=request.getAttribute("errors")%>'/>
<c:set var="booksTotal" value='<%=request.getAttribute("booksTotal")%>'/>
<!doctype html>
<html lang="en">
<head>
  <%@include file="includes/head.jsp" %>
  <title>Books page</title>
</head>
<body>
<%@include file="components/header.jsp" %>
<div class="page_actions">
  <h1>Books</h1>
  <c:if test="${user.isAdmin()}">
    <div>
      <a href="${contextPath}/books?action=add" class="primary_button">
        <i class="ri-add-line"></i>
        Add
      </a>
    </div>
  </c:if>
</div>
<div class="info_block">Books total <strong><c:out value="${booksTotal}" /></strong></div>
<c:if test="${success && operation != null}">
  <div class="success_block">
    <c:choose>
      <c:when test='${operation.equalsIgnoreCase("create")}'>
        Book created
      </c:when>
      <c:when test='${operation.equalsIgnoreCase("update")}'>
        Book updated
      </c:when>
      <c:when test='${operation.equalsIgnoreCase("delete")}'>
        Book deleted
      </c:when>
      <c:otherwise>
        Unknown operation
      </c:otherwise>
    </c:choose>
  </div>
</c:if>
<c:if test="${action != null && user.isAdmin()}">
  <c:if test='${action.equalsIgnoreCase("add")}'>
    <form action="${contextPath}/books" method="post">
      <div class="form_title">
        Create book
      </div>
      <label for="titleInputCreate">Title</label>
      <input type="text" name="title" id="titleInputCreate" placeholder="Input text">
      <label for="authorInputCreate">Author</label>
      <input type="text" name="author" id="authorInputCreate" placeholder="Input text">
      <label for="yearInputCreate">Year</label>
      <input type="number" name="year" id="yearInputCreate" placeholder="Input number">
      <label for="pagesInputCreate">Number of pages</label>
      <input type="number" name="pages" id="pagesInputCreate" placeholder="Input number">
      <input type="submit" value="Add book">
      <a href="${contextPath}/books" class="button">Cancel</a>
    </form>
  </c:if>
  <c:if test='${action.equalsIgnoreCase("edit") && book != null}'>
    <form action="${contextPath}/books" method="post">
      <div class="form_title">
        Edit book
      </div>
      <input type="hidden" name="id" value="${book.id}">
      <label for="titleInputEdit">Title</label>
      <input type="text" name="title" id="titleInputEdit" placeholder="Input text" value="${book.title}">
      <label for="authorInputEdit">Author</label>
      <input type="text" name="author" id="authorInputEdit" placeholder="Input text" value="${book.author}">
      <label for="yearInputEdit">Year</label>
      <input type="number" name="year" id="yearInputEdit" placeholder="Input number" value="${book.year}">
      <label for="pagesInputEdit">Number of pages</label>
      <input type="number" name="pages" id="pagesInputEdit" placeholder="Input number" value="${book.pages}">
      <input type="submit" value="Save book">
      <a href="${contextPath}/books" class="button">Cancel</a>
    </form>
  </c:if>
  <c:if test='${action.equalsIgnoreCase("delete") && book != null}'>
    <form action="${contextPath}/books?action=delete" method="post">
      <div class="form_title">
        Delete book
      </div>
      <div class="info_block">
        Are you sure you want to delete the book <strong><c:out value="${book.title}" /></strong> ?
      </div>
      <input type="hidden" name="id" value="${book.id}">
      <input type="submit" value="Yes, delete book">
      <a href="${contextPath}/books" class="button">Cancel</a>
    </form>
  </c:if>
</c:if>
<div class="list">
  <c:choose>
    <c:when test="${books.size() > 0}">
      <c:forEach var="bookItem" items="${books}">
        <div class="list_item">
          <a href="${contextPath}/book?id=${bookItem.id}" class="book_info">
            <div class="image">
              <i class="ri-book-line"></i>
            </div>
            <div>
              <div class="font-bold text-sm">
                <c:out value="${bookItem.title}" />
              </div>
              <div>
                By <strong><c:out value="${bookItem.author}" /></strong>, <c:out value="${bookItem.year}" />,
                <c:out value="${bookItem.pages}" />
              </div>
            </div>
          </a>
          <c:if test="${user.isAdmin()}">
            <div class="actions">
              <a href="${contextPath}/books?action=edit&id=${bookItem.id}" class="action_button">
                <i class="ri-pencil-line"></i>
              </a>
              <a href="${contextPath}/books?action=delete&id=${bookItem.id}" class="action_button danger">
                <i class="ri-delete-bin-line"></i>
              </a>
            </div>
          </c:if>
        </div>
      </c:forEach>
    </c:when>
    <c:otherwise>
      <div class="empty_list_item">
        No books found
      </div>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
