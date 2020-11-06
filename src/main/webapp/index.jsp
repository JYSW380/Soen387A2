<%@ page import="model.User" %>
<%@ page import="java.util.Set" %>
<%--
  Created by IntelliJ IDEA.
  User: junyang
  Date: 2020-11-06
  Time: 2:08 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="ServletExtractUser" method="get">
    <input type="submit" >
</form>
<div>

    <c:choose>
        <c:when test="${empty allU}">
            <h3>Welcome</h3>
        </c:when>

    </c:choose>
</div>


</body>
</html>
