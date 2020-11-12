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
    <title>Welcome</title>
    <link rel="stylesheet" href="styles.css"/>
    <link href="https://fonts.googleapis.com/css2?family=Alata&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.15.0/css/all.css"  crossorigin="anonymous"/>
</head>
<body>
<div class="welcomecont">
    <div class="welcomediv">
        <c:choose>
            <c:when test="${empty allU}">
                <div class="spacer"></div>
                <i class="fal fa-chalkboard-teacher mainicon"></i>
                <h3>Message Board</h3>
            </c:when>

        </c:choose>
        <form action="ServletExtractUser" method="get">
            <button class="submitbtn">Enter<i class="far fa-long-arrow-right"></i></button>
        </form>
    </div>

</div>


</body>
</html>
