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
    <title>Select a User</title>
    <link rel="stylesheet" href="styles.css"/>
    <link href="https://fonts.googleapis.com/css2?family=Alata&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.15.0/css/all.css"  crossorigin="anonymous"/>
</head>
<body>
<div class="welcomecont">
    <div class="welcomediv">
        <div class="spacer"></div>
        <i class="fal fa-users mainicon"></i>
        <h3>Select A User</h3>
        <div class="spacers"></div>
    <form action="ServletAuth" method="get">
        <c:choose>
            <c:when test="${empty allU}">
                <h3>Currently, there are no employees.</h3>
            </c:when>
            <c:otherwise>
                <c:forEach  items="${allU}" var="u">
                    <label class="selectuserinp">
                        <span><i class="fas fa-user-alt"></i>${u}</span>
                        <input type="radio" value="${u}" name="user">
                    </label>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <div class="spacer"></div>
        <button type="submit" class="submitbtn">Log In<i class="fal fa-sign-in-alt"></i></button>
    </form>
    </div>
</div>

</body>
</html>