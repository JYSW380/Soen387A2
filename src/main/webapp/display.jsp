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
                        <div class="userdiv"><i class="fas fa-user-alt"></i><h6>${user}</h6></div>
                    </div>
                </form>
            </nav>
            <div class="boardcontent">
            <form action="ServletPostManager" method="post" enctype="multipart/form-data" class="posterdiv">
                <input type="text" name="message" placeholder="Enter a message..."><br/>
                <label>
                    <input type="file" name="file">
                    <i class="fal fa-upload uploadfile"></i>
                </label>
                <button type="submit" value="post" name="post">Post</button>
            </form>
            <c:choose>
                <c:when test="${not empty allPost}">

                    <c:forEach items="${allPost}" var="post" >
                        <div class="messagerow">
                                <form action="ServletPostManager" method="post" enctype="multipart/form-data">
                                    <div>
                                        <div>
                                            <label>Message</label>
                                            <input type="text" value="${post}" name="editMessage">
                                            <input type="hidden" value="${post.id}" name="id">
                                        </div>
                                        <button type="submit" value="download" name="download">Download<i class="fal fa-cloud-download-alt"></i></button>
                                    </div>
                                    <c:choose>
                                        <c:when test= "${user.equals(post.userName)}">
                                            <div>
                                            <label class="filelabel">File
                                                <input type="file" name="updateFile">
                                                <i class="fal fa-upload uploadfile"></i>
                                            </label>
                                            <div>
                                                <button type="submit" value="edit" name="edit">Edit<i class="fal fa-edit"></i></button>
                                                <button type="submit" value="delete" name="delete">Delete<i class="fal fa-trash"></i></button>
                                            </div>
                                            </div>
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
                                    ${spost} <%//result of the search%>
                            </p>
                        </div>
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
