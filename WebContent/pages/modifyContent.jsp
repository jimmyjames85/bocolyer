<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="bo.ContentManager" %>
<%@ page import="bo.users.UserManager" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<div>
    <%@ include file="header.jsp" %>
</div>

<c:if test="<%=ContentManager.validateContentUser(request)%>">


    <div id="controls">
        <pre><%--= ContentManager.getContentIds() --%></pre>

        main.page.intro:
        <form action="../modifyContent" method="POST">

            <input type="hidden" name="action" value="update">
            <input type="hidden" name="contentId" value="main.page.intro">
            <input type="button" value="Create an Account">
        <textarea cols="40" rows="5"
                  name="contentValue"><%=ContentManager.retrieveContent("main.page.intro") %></textarea>
            <input
                    type="submit">
        </form>
    </div>

</c:if>


</body>
</html>