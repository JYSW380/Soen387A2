<%--
  Created by IntelliJ IDEA.
  User: Owner
  Date: 11/24/2020
  Time: 12:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--why cannot access the session --%>
<div>
    <div>
        <label>Message</label>
        <input type="text" value="${currentPost}" name="editMessage">
        <input type="hidden" value="${currentPost.id}" name="id">
        <input type="checkbox" value="xml" name="xml"><label>XML</label><br/>
    </div>
    <button type="submit" value="download" name="download">Download<i class="fal fa-cloud-download-alt"></i></button>
</div>
