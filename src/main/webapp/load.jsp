<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: junyang
  Date: 2020-11-06
  Time: 2:09 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <form action="ServletAuth" method="get">
        <c:choose>
            <c:when test="${empty allU}">
                <h3>Currently, there are no employees.</h3>
            </c:when>
            <c:otherwise>
                <c:forEach  items="${allU}" var="u">
                    <input type="radio" value="${u.username}" name="user">${u.username}<br/>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        <input type="submit">
    </form>

</div>

</body>
</html>