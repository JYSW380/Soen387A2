<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Owner
  Date: 10/26/2020
  Time: 3:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <c:choose>
        <c:when test="${empty user}">
            <c:redirect url="index.jsp"></c:redirect>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${not empty allPost}">

                    <c:forEach items="${allPost}" var="post" >
                        <div>
                                <form action="ServletPostManager" method="post" enctype="multipart/form-data">
                                    <label>Message</label>
                                    <input type="text" value="${post}" name="editMessage"><br/>
                                    <input type="hidden" value="${post.id}" name="id">
                                    <input type="submit" value="download" name="download"><br/>
                                    <c:choose>
                                        <c:when test= "${user.equals(post.userName)}">
                                            <label>File</label>
                                            <input type="file" name="updateFile"><br/>
                                            <input type="submit" value="edit" name="edit"><br/>
                                            <input type="submit" value="delete" name="delete"><br/>
                                        </c:when>
                                        <c:otherwise>
                                        </c:otherwise>
                                    </c:choose>

                                </form>
                        </div>
                    </c:forEach>
                </c:when>
             </c:choose>
            <c:choose>
                <c:when test="${not empty searchPost}">
                    <c:forEach items="${searchPost}" var="spost" >
                        <div>
                            <p>
                                    ${spost}
                            </p>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
            <form action="ServletPostManager" method="get">
                <label>search</label>
                <input type="text" name="searchText">
                <input type="submit" value="search" name="search">
            </form>

            <form action="ServletPostManager" method="post" enctype="multipart/form-data">
                <label>Message</label>
                <input type="text" name="message"><br/>
                <label>File</label>
                <input type="file" name="file"><br/>
                <input type="submit" value="post" name="post"><br/>
            </form>

        </c:otherwise>
    </c:choose>

    <form action="ServletPostManager" method="get">
        <input type="submit" value="LogOut" name="LogOut">
    </form>
</body>
</html>
