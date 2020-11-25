<%--
  Created by IntelliJ IDEA.
  User: Owner
  Date: 11/24/2020
  Time: 12:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="messagerow">
    <p>
        ${currentsPost.message} <%//result of the search%>
    </p>
    <p>
        # ${currentsPost.hashTag} <%//result of the search%>
    </p>
    <p>
        ${currentsPost.updateTime} <%//result of the search%>
    </p>
    <p>
        by ${currentsPost.userName} <%//result of the search%>
    </p>
    <p>
        within ${currentsPost.group} <%//result of the search%>
    </p>
</div>
