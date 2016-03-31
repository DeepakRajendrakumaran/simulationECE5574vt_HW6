<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>ece 5574 simulation component</title>
</head>

<body>
<center>
<h1 style="color: blue;">Welcome to ece 5574 simulation
component web interface! </h1>
<h2 style="color: green;">
<br>
<h4> To upload config file <a href="upload.jsp">click here</a></h4>

<!-- form action="simulationece5574vt" method="post">
Upload the Config File:<br>
  <input name="FploadFile" value="submitcfgfile="
 type="file"> 
  <input name="FileSave" value="Upload" type="submit">
</form>
<br> -->
<form action="simulationece5574vt" method="get">
Start the Simulation: <br>
  <input name="StartSim" value="Start" type="submit">
</form>
<br>
<form action="simulationece5574vt" method="get">
Stop the simulation:<br>
  <input name="StopSim" value="Stop" type="submit">
</form>
<br>
<form action="log.jsp" method="get">
View the simulation logs:<br>
  <input name="ViewLogs" value="View" type="submit">
</form>
</h3>
<br>
<br>
</center>

</body>
</html>
