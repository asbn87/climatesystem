<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextRoot" value="${pageContext.request.contextPath}" />

<div class="row">
	<div class="col-md-10 ml-auto mr-auto mt-5">
		<div class="dashboard-container">
			
			<div class="row d-flex justify-content-right m-1">
				<div class="m-1">
					<button onclick="connect()">Connect</button>
				</div>
				<div class="m-1">
					<button onclick="disconnect()">Disconnect</button>
				</div>
				<div class="m-2">
					Status: <span id="status-text">Disconnected</span>
				</div>
			</div>
			
			<div class="row d-flex justify-content-center">
			
				<div class="col-md-4 text-center mt-1">
					<div class="card">
						<div class="card-header" id="temp-header">
							<svg width="100" height="100">
								<image width="100" height="100" xlink:href="${contextRoot}/img/svg/temp.svg"/>
							</svg>
							<div class="row d-flex justify-content-center">
								<span>Temperature</span>
							</div>
						</div>
						<div class="card-body font-weight-bold">
							<span id="card-value temp">0</span><span id="card-value-sign">&deg;C</span>
						</div>
						<div class="card-footer">
							<span id="card-time temp">HH:MM:SS</span>
						</div>
					</div>
				</div>
	
				<div class="col-md-4 text-center mt-1">
					<div class="card">
						<div class="card-header" id="hum-header">
							<svg width="100" height="100">
								<image width="100" height="100" xlink:href="${contextRoot}/img/svg/hum.svg"/>
							</svg>
							<div class="row d-flex justify-content-center">
								<span>Humidity</span>
							</div>
						</div>
						<div class="card-body font-weight-bold">
							<span id="card-value hum">0</span><span id="card-value-sign">%</span>
						</div>
						<div class="card-footer">
							<span id="card-time hum">HH:MM:SS</span>
						</div>
					</div>
				</div>
	
	
				<div class="col-md-4 text-center mt-1">
					<div class="card" id="co2-card">
						<div class="card-header" id="co2-header">
							<svg width="100" height="100">
								<image width="100" height="100" xlink:href="${contextRoot}/img/svg/signal.svg"/>
							</svg>
							<div class="row d-flex justify-content-center">
								<span>CO&sup2;</span>
							</div>
						</div>
						<div class="card-body font-weight-bold">
							<span id="card-value co2">0</span><span id="card-value-sign">ppm</span>
						</div>
						<div class="card-footer" >
							<span id="card-time co2">HH:MM:SS</span>
						</div>
					</div>
				</div>
				
			</div>
			
		</div>
	</div>
</div>

<script type="text/javascript">
	var wsPath = "ws://" + location.hostname + (location.port ? ":" + location.port: "") + "/climatesystem/ws/dashboard"
	var ws;
	
	function disconnect() {
		ws.close();
	}
	
	function connect() {
		ws = new WebSocket(wsPath);
		
		ws.onopen = function() {
			document.getElementById("status-text").innerHTML = "Connected";
			console.log('Connected to ws');
		}

		ws.onclose = function() {
			document.getElementById("status-text").innerHTML = "Disconnected";
			console.log('Disconnected');
		}
		
		ws.onconnect = function(event) {
			console.log(event.data);
		}
		
		ws.onmessage = function(event) {

			console.log(event.data);
			
			var obj = JSON.parse(event.data);
			
			// CO2
			if(obj.mac === '5C:CF:7F:F0:B5:10') {
				
				if(obj.co2 >= 1000) {
					document.getElementById("co2-header").style.backgroundColor=getColorForPercentage(0);
					document.getElementById("co2-header").style.transition="all 1s";
					
					document.getElementById("card-value co2").style.color=getColorForPercentage(0);
					document.getElementById("card-value co2").style.transition="all 1s";
					
					document.getElementById("card-value co2").innerHTML = obj.co2;
				}
				else if(obj.co2 <= 999 && obj.co2 >= 600) {
					document.getElementById("co2-header").style.backgroundColor=getColorForPercentage(0.5);
					document.getElementById("co2-header").style.transition="all 1s";
					
					document.getElementById("card-value co2").style.color=getColorForPercentage(0.5);
					document.getElementById("card-value co2").style.transition="all 1s";
					
					document.getElementById("card-value co2").innerHTML = obj.co2;
				}
				else if(obj.co2 <= 599 && obj.co2 > 400) {
					document.getElementById("card-value co2").style.color=getColorForPercentage(1);
					document.getElementById("card-value co2").style.transition="all 1s";
					
					document.getElementById("co2-header").style.backgroundColor=getColorForPercentage(1);
					document.getElementById("co2-header").style.transition="all 1s";
					
					document.getElementById("card-value co2").innerHTML = obj.co2;
				}
				else {
					document.getElementById("card-value co2").style.color=getColorForPercentage(1);
					document.getElementById("card-value co2").style.transition="all 1s";
					
					document.getElementById("co2-header").style.backgroundColor=getColorForPercentage(1);
					document.getElementById("co2-header").style.transition="all 1s";
					
					document.getElementById("card-value co2").innerHTML = "<" + obj.co2;
				}
				document.getElementById("card-time co2").innerHTML = obj.date;

			}
			if(obj.mac === 'BC:DD:C2:14:67:64') {
				
				// Temperature
				if(obj.temp >= 25) {
					document.getElementById("temp-header").style.backgroundColor=getColorForPercentage(0);
					document.getElementById("temp-header").style.transition="all 1s";
					
					document.getElementById("card-value temp").style.color=getColorForPercentage(0);
					document.getElementById("card-value temp").style.transition="all 1s";
				}
				else if(obj.temp <= 24 && obj.temp >= 20) {
					document.getElementById("temp-header").style.backgroundColor=getColorForPercentage(0.5);
					document.getElementById("temp-header").style.transition="all 1s";
					
					document.getElementById("card-value temp").style.color=getColorForPercentage(0.5);
					document.getElementById("card-value temp").style.transition="all 1s";
				}
				else {
					document.getElementById("temp-header").style.backgroundColor=getColorForPercentage(1);
					document.getElementById("temp-header").style.transition="all 1s";
					
					document.getElementById("card-value temp").style.color=getColorForPercentage(1);
					document.getElementById("card-value temp").style.transition="all 1s";
				}
				document.getElementById("card-value temp").innerHTML = obj.temp;
				document.getElementById("card-time temp").innerHTML = obj.date;
				
				// Humidity
				if(obj.hum >= 40) {
					document.getElementById("hum-header").style.backgroundColor=getColorForPercentage(0);
					document.getElementById("hum-header").style.transition="all 1s";
					
					document.getElementById("card-value hum").style.color=getColorForPercentage(0);
					document.getElementById("card-value hum").style.transition="all 1s";
				}
				else if(obj.hum <= 39 && obj.hum >= 30) {
					document.getElementById("hum-header").style.backgroundColor=getColorForPercentage(0.5);
					document.getElementById("hum-header").style.transition="all 1s";
					
					document.getElementById("card-value hum").style.color=getColorForPercentage(0.5);
					document.getElementById("card-value hum").style.transition="all 1s";
				}
				else {
					document.getElementById("hum-header").style.backgroundColor=getColorForPercentage(1);
					document.getElementById("hum-header").style.transition="all 1s";
					
					document.getElementById("card-value hum").style.color=getColorForPercentage(1);
					document.getElementById("card-value hum").style.transition="all 1s";
				}
				document.getElementById("card-value hum").innerHTML = obj.hum;
				document.getElementById("card-time hum").innerHTML = obj.date;
			}
		}
	}

	var percentColors = [
	    { pct: 0.0, color: { r: 241, g: 67, b: 63 } },	// red
	    { pct: 0.5, color: { r: 247, g: 233, b: 103 } }, // yellow
	    { pct: 1.0, color: { r: 109, g: 172, b: 64 } } ];	// green

	var getColorForPercentage = function(pct) {
	    for (var i = 1; i < percentColors.length - 1; i++) {
	        if (pct < percentColors[i].pct) {
	            break;
	        }
	    }
	    var lower = percentColors[i - 1];
	    var upper = percentColors[i];
	    var range = upper.pct - lower.pct;
	    var rangePct = (pct - lower.pct) / range;
	    var pctLower = 1 - rangePct;
	    var pctUpper = rangePct;
	    var color = {
	        r: Math.floor(lower.color.r * pctLower + upper.color.r * pctUpper),
	        g: Math.floor(lower.color.g * pctLower + upper.color.g * pctUpper),
	        b: Math.floor(lower.color.b * pctLower + upper.color.b * pctUpper)
	    };
	    return 'rgb(' + [color.r, color.g, color.b].join(',') + ', 0.9)';
	}
</script>