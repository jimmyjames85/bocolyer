<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="bo.util.Logger"%>
<html>
<link rel="stylesheet" type="text/css" href="../css/bo.css" />
<%
	Logger.pageVisit(request,"about.jsp");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bo Colyer</title>
</head>
<body>
	<div class="topDiv">
		<img src="../imgs/Bo_360x480.jpg" class="textWrapLeft">
		<h1>Bo Daniel Colyer</h1>

		<div class="textWrapRight">
			<%@include file="aaronsPayPal.info"%>
			<a href="login.jsp"><img border="0" src="../imgs/thermometer.png"></a>
			<%-- img border="0" src="http://thermometer.fund-raising-ideas-center.com/thermometer.php?currency=dollar&goal=<%=goal%>&current=<%=current%>&color=red&size=large"--%>
			<!--img border="0" src="http://thermometer.fund-raising-ideas-center.com/thermometer.php?currency=dollar&goal=2000&current=25&color=red&size=large"-->
		</div>
		<%@include file="about.data"%>

		<p>
			<%@include file="aaronsPayPal.info"%>
		</p>

		<a href="https://www.facebook.com/groups/428067717369099/"> <img
			src="../imgs/facebook40x40.png" class="textWrapLeft">
			<H2>The Grand Adventures of Bo Daniel Colyer</H2>
		</a>
	</div>
</body>


</html>