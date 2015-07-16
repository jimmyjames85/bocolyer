<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bo" uri="http://bocolyer.homenet.org" %>
<%@ page import="bo.ContentManager" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" href="../css/bo.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>ContentManger</title>
</head>
<body>
<div>
    <%@ include file="header.jsp" %>
</div>
<div class="topDiv">

    <c:choose>
        <c:when test="${bo:validateUser(cookie.get('s').value)}">

            <div id="controls">
                <form action="../modifyContent" method="POST">
                    Funds Raised: <input type="text" name="fundsRaised" value="<%=ContentManager.retrieveContent("funds.raised")%>">
                    <br>
                    Funds Target: <input type="text" name="fundsTarget" value="<%=ContentManager.retrieveContent("funds.target")%>">
                    <br>
                    <input type="submit">
                </form>
            </div>

            <div id='log' style="overflow-y: scroll; height: 300px; overflow-x: hidden; font-size: xx-small;">
                <%=ContentManager.getLogAsHTMLTable()%>
            </div>
            <script>
                var objDiv = document.getElementById("log");
                objDiv.scrollTop = objDiv.scrollHeight;
            </script>
        </c:when>
        <c:otherwise>
            <div>
                You are not a content manager.
            </div>
        </c:otherwise>
    </c:choose>


</div>
</body>
</html>