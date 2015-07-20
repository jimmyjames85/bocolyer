var canvas     = document.getElementById("canvas");
var processing = new Processing(canvas, function (processing)
		{
			processing.size(200, 400);
			processing.background(0xF0F);
			var mouseIsPressed       = false;
			processing.mousePressed  = function ()
			{
				mouseIsPressed = true;
			};
			processing.mouseReleased = function ()
			{
				mouseIsPressed = false;
			};
			var keyIsPressed         = false;
			processing.keyPressed    = function ()
			{
				keyIsPressed = true;
			};
			processing.keyReleased   = function ()
			{
				keyIsPressed = false;
			};
			function getImage(s)
			{
				var url = "https://www.kasandbox.org/programming-images/" + s + ".png";
				processing.externals.sketch.imageCache.add(url);
				return processing.loadImage(url);
			}

			with (processing)
			{
				/////////////////////////// INSERT YOUR KHAN ACADEMY PROGRAM HERE //////////////////////////////
				var baseSize = 2 / 8 * canvas.width;
				var baseX    = 3 / 16 * canvas.width;//1 / 2 * canvas.width;
				var baseY       = 0.925 * canvas.height;
				var stemW       = 6 / 10 * baseSize;
				var stemH       = 0.875 * canvas.height;
				var markerX1    = baseX;
				var markerX2    = baseX - 1 / 3 * baseSize;
				var zeroMarkerY = baseY - 1 / 2 * baseSize;
				var nextMarker  = 1 / 10 * (zeroMarkerY - (baseY - stemH));

				// amtRaised and totalGoal should be the inputs from the site
				//These global variables are populated outside of this .js file
				//var amtRaised = 1500;
				//var totalGoal = 2000;

				var percent   = amtRaised / totalGoal;
				var fillPoint = zeroMarkerY - percent * (zeroMarkerY - (baseY - stemH));

				var draw = function ()
				{

					background(238, 238, 238);
					noStroke();

					//thermometer border
					borderSize = 1;
					fill(0,0,0);// I have no idea why I have to make it 3x the border size ------~.
					rect(baseX - 1 / 3 * baseSize -borderSize, baseY - stemH -borderSize, stemW+3*borderSize, stemH+3*borderSize);
					arc(baseX - borderSize, baseY - stemH - 2*borderSize, stemW+borderSize, stemW+borderSize, 180, 360);

					// bottom of thermometer border
					ellipse(baseX - borderSize, baseY - borderSize, baseSize + 3*borderSize, baseSize+3*borderSize);

					// thermometer
					//fill(201, 245, 238);
					fill(230,230, 250);
					rect(baseX - 1 / 3 * baseSize, baseY - stemH, stemW, stemH);
					arc(baseX, baseY - stemH, stemW, stemW, 180, 360);

					// bottom of thermometer
					fill(255, 0, 0);
					ellipse(baseX, baseY, baseSize, baseSize);

					// mercury
					rect(baseX - 1 / 3 * baseSize, fillPoint, stemW, baseY - fillPoint);

					// scale markers
					stroke(0, 0, 0);
					strokeWeight(2);
					line(markerX1, zeroMarkerY, markerX2, zeroMarkerY);
					line(markerX1, zeroMarkerY - nextMarker, markerX2, zeroMarkerY - nextMarker);
					line(markerX1, zeroMarkerY - 2 * nextMarker, markerX2, zeroMarkerY - 2 * nextMarker);
					line(markerX1, zeroMarkerY - 3 * nextMarker, markerX2, zeroMarkerY - 3 * nextMarker);
					line(markerX1, zeroMarkerY - 4 * nextMarker, markerX2, zeroMarkerY - 4 * nextMarker);
					line(markerX1, zeroMarkerY - 5 * nextMarker, markerX2, zeroMarkerY - 5 * nextMarker);
					line(markerX1, zeroMarkerY - 6 * nextMarker, markerX2, zeroMarkerY - 6 * nextMarker);
					line(markerX1, zeroMarkerY - 7 * nextMarker, markerX2, zeroMarkerY - 7 * nextMarker);
					line(markerX1, zeroMarkerY - 8 * nextMarker, markerX2, zeroMarkerY - 8 * nextMarker);
					line(markerX1, zeroMarkerY - 9 * nextMarker, markerX2, zeroMarkerY - 9 * nextMarker);
					line(markerX1, zeroMarkerY - 10 * nextMarker, markerX2, zeroMarkerY - 10 * nextMarker);

					// text
					fill(100, 100, 255);
					textSize(25);
					var goalMsg   = ("$" + totalGoal);
					text(goalMsg, 0.8*baseX + baseSize, baseY - stemH);
					//var raisedMsg = ("Amount Raised: $" + amtRaised);
					var raisedMsg = ("$" + amtRaised);
					fill(255, 0, 0);
					textSize(30);
					text(raisedMsg, baseX + baseSize, fillPoint);

				};
				/////////////////////////// END KHAN ACADEMY PROGRAM ////////////////////////////////
			}
			if (typeof draw !== 'undefined')
				processing.draw = draw;
		});
