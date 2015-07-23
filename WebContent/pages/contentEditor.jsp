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
                    Funds Raised: <input type="text" name="fundsRaised"
                                         value="<%=ContentManager.retrieveContent("funds.raised")%>">
                    <br>
                    Funds Target: <input type="text" name="fundsTarget"
                                         value="<%=ContentManager.retrieveContent("funds.target")%>">
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

    <div id="progress">

    </div>
    <div id="progress2">

    </div>
    <div >
        <button id="stopBtn" onclick="sendStop();">Toggle</button>
    </div>
    <div id="stopProgress">

    </div>
</div>

<div id="thefooter">
test test o test
</div>


<script>

    var stop = true;
    var TIMER_SERVLET_URL =  getBaseURL() + "/timer";

    function sendStop()
    {
        var headers = new Object();
        headers.stop = stop;
        if(!stop)
        myVar = setInterval(updateProgressBar,3000);

        sendAjaxQueryWithHeaders("POST",TIMER_SERVLET_URL,"",headers,function(request)
        {
            setInnerHtml("stopProgress",request);
        });
    }
    var count = 0;
    var myVar;

    myVar = setInterval(updateProgressBar, 3000);

    function updateProgressBar()
    {

        var headers = new Object();
        headers.one = "one oh one";
        headers.two = "two yeah two";
        headers.three = "three = one plus two (last one)"
        var msg = getBaseURL();

        count++;
        msg += "method call count: " + count;

        setInnerHtml("progress", msg);


        var noCacheIEHack = "?nc" + (new Date().getTime()) + "=1";
        sendAjaxQueryWithHeaders("GET",TIMER_SERVLET_URL + noCacheIEHack, "", headers, function (response)
        {
            if(response=="COMPLETE")
            {
                setInnerHtml("progress2", "It has been stopped and now we are stopping timmer #" + myVar);
                stop = false;
                document.getElementById('stopBtn').innerHTML = 'Start'
                clearInterval(myVar);
            }
            else
            {
                stop = true;
                document.getElementById('stopBtn').innerHTML = 'Stop'
                setInnerHtml("progress2",response);
            }

        });

    }


    function toggleFooter()
    {
        var footer = document.getElementById('thefooter');
        if(footer)
        {
            if(footer.currentStyle)
                console.log("IE:currentDisplay = " + footer.currentStyle.display);
            else if(window.getComputedStyle)
                console.log("FF:currentDisplay = " +document.defaultView.getComputedStyle(footer,null).getPropertyValue('display'));

            var display = footer.style.display;
            if (footer.style.display === 'none')
                footer.style.display = 'block';
            else
                footer.style.display = 'none';

            console.log("display was '" + display + "' ==now=it=is==> '" + footer.style.display + "'");
        }
        else
        {
            console.log('no footer!!???!?!');
        }

    }
</script>
</body>
</html>
