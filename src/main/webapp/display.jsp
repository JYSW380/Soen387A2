<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
    <title>Board</title>
    <link rel="stylesheet" href="styles.css"/>
    <link href="https://fonts.googleapis.com/css2?family=Alata&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.15.0/css/all.css"  crossorigin="anonymous"/>
</head>
<body>
<div class="messageboard">

    <c:choose>
        <c:when test="${empty user}">
            <c:redirect url="index.jsp"></c:redirect>
        </c:when>
        <c:otherwise>
            <nav>
                <form action="ServletPostManager" method="get" class="mainsearch">
                    <div class="mainleft">
                    <h3>Message Board Discussions</h3>
                    <div>
                        <input type="text" name="searchText" placeholder="Find a message...">
                        <button type="submit" name="search"><i class="far fa-search"></i></button>
                    </div>
                    </div>
                    <div class="mainright">
                        <form action="ServletPostManager" method="get" class="logoutdiv">
                            <button type="submit" value="LogOut" name="LogOut">Log Out<i class="fal fa-power-off"></i></button>
                        </form>
<%--                        why cannot call getter of user--%>
                        <div class="userdiv"><i class="fas fa-user-alt"></i><h6>${user}</h6></div>
                    </div>
                </form>
            </nav>
            <div class="boardcontent">
            <form action="ServletPostManager" method="post" enctype="multipart/form-data" class="posterdiv">
                <input type="text" name="message" placeholder="Enter a message..."><br/>
                <select name="group" id="group">
                    <option value=""> </option>
                    <c:forEach items="${userGroup}" var="uGroup" >
                        <option value="${uGroup}">${uGroup}</option>
                    </c:forEach>
                </select>
                <label>
                    <input type="file" name="file">
                    <i class="fal fa-upload uploadfile"></i>
                </label>
                <button type="submit" value="post" name="post">Post</button>
            </form>

<%--                empty is not working in hashmap since you always set key--%>
<%--                <c:when test="${not empty allPost}">--%>

                    <c:forEach items="${allPost}" var="groupPost" >
                        <div class="messagerow">
                        ${groupPost.key}
                            <c:if test="${empty groupPost.value}">
                                <p>No post within the group</p>
                            </c:if>
                            <c:if test="${ not empty groupPost.value}">
                                <c:forEach items="${groupPost.value}" var="post">
                                    <form action="ServletPostManager" method="post" enctype="multipart/form-data">
<%--                                        set value so the include page can use it --%>
                                        <c:set var="currentPost" value="${post}" scope="session"></c:set>
                                        <c:set var="uGroup" value="${EditGroup}" scope="session"></c:set>
                                        <jsp:include page="post.jsp" />
                                        <c:choose>
                                            <c:when test= "${user.equals(post.userName) || fn:contains(userGroup, 'admins')}" >
                                                <jsp:include page="extPost.jsp"></jsp:include>
                                            </c:when>
                                            <c:otherwise>
                                            </c:otherwise>
                                        </c:choose>

                                    </form>
                                </c:forEach>
                            </c:if>
                        </div>

                    </c:forEach>
            <c:choose>
                <c:when test="${not empty searchPost}">
                    <p>Result Search</p>
                    <c:forEach items="${searchPost}" var="spost" >
                        <c:set var="currentsPost" value="${spost}" scope="session"></c:set>
                        <jsp:include page="searchPost.jsp" />
                    </c:forEach>
                </c:when>

            </c:choose>
                <div class="spacerl"></div>
            </div>

        </c:otherwise>
    </c:choose>



</div>
</body>
</html>
