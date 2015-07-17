<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="bo.util.Logger"%>
<%@ page import="bo.ContentManager" %>
<html>
<link rel="stylesheet" type="text/css" href="../css/bo.css" />


<%
	Logger.pageVisit(request,"index.jsp");
%>

<head>
<style>
#map-canvas {
	width: 400px;
	height: 320px;
	background-color: #CCC;
	/*	float: left;*/
	margin: 10px 10px 10px 0px;
}
#canvas {
	width: 100px;
	height: 320px;
	background-color: #CCC;
	/*	float: left;*/
	margin: 0px 0px 0px 0px;
}
</style>
<script src="https://maps.googleapis.com/maps/api/js"></script>
<!-- https://developers.google.com/maps/tutorials/fundamentals/adding-a-google-map -->
<script>
	function initialize() {
		var mapCanvas = document.getElementById('map-canvas');
		var mapOptions = {
			center : new google.maps.LatLng(41.7060081, -93.6105145),
			zoom : 15,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		}
		var map = new google.maps.Map(mapCanvas, mapOptions);
	}
	google.maps.event.addDomListener(window, 'load', initialize);
	//mapTypeId is used to specify what type of map to use. Your choices are ROADMAP, SATELLITE, HYBRID, or TERRAIN.
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bo Colyer - Fund Run</title>
</head>
<body>
	<div class="topDiv">

		<div>
			<a href="about.jsp"><img width="180" height="240" src="../imgs/Bo_360x480.jpg"
				class="textWrapLeft"></a>
			<h1>Bo Daniel Colyer - Fund Run</h1>

			<p>
				On February 13th, 2015 <a href="about.jsp">Bo Daniel Colyer</a> was
				born via emergency C-section weighing 2 lbs 14 ounces and spent his
				first 70 days in the NICU. His parents are overwhelmed with medical
				bills and could use your support. Any donation amount is greatly
				appreciated.
			</p>
		</div>

		<table class="reviewSteps">

			<tr>
				<td>When</td>
				<td colspan="2">Saturday, August 8, 2015</td>
			</tr>
			<tr>
				<td>Where</td>
				<td colspan="2">DMACC Ankeny Campus, South Ankeny Boulevard, Ankeny, IA</td>
			</tr>
			<tr>
				<td>Details</td>
				<td colspan="2">Each runner/walker will receive a free Bloomin' Onion card
					<a
					href="https://www.google.com/maps/place/Outback+Steakhouse/@41.7053209,-93.5749454,17z/data=!3m1!4b1!4m2!3m1!1s0x87ee8ffdf5211f3d:0xbc30edfbb845d7b7">
						<img src="../imgs/outback_bc.jpg" width="43px" height="26px">
				</a> from Outback Steakhouse in Ankeny!

					<ul>
						<li>Pet friendly</li>
						<li>Stroller friendly</li>
						<li>Water will be provided</li>
						<li>Bathrooms are available</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td>Donation Options</td>
				<td colspan="2">The suggested donation is ten dollars per person. We will
					be collecting donations on campus on the day of the run. If you are
					unable to make it and would still like to donate, please contact
					Heather Knittel at <b>515.371.2706</b> or donate via PayPal. <b>You
						do not need a PayPal account to donate.</b><br> <br> <%@include
						file="aaronsPayPal.info"%>
				</td>
			</tr>
			<tr>
				<td>Map</td>
				<td>
					<div id="map-canvas"></div>
				</td>
				<td>
  					<a href="about.jsp"><canvas id="canvas" width="200" height="400"></canvas></a>
				</td>
			</tr>
		</table>

		<%@include file="footer.jsp"%>

	</div>
</body>
<script>
	var amtRaised = <%=ContentManager.retrieveContent("funds.raised")%>;
	var totalGoal = <%=ContentManager.retrieveContent("funds.target")%>;
</script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/processing.js/1.4.8/processing.min.js"></script>
<script src="../scripts/thermometer.js"></script>
</html>
