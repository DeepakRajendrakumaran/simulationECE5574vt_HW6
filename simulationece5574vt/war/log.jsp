<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SO question 4112686</title>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script>
        //$(document).on("click", "#somebutton", 
        function ajaxGet() {  // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
            $.get("log", function(responseJson) {    // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
                //var $ul = $("<ul>").appendTo($("#somediv"));
            	var str = "<div>";

            	//var $span = $("<span>").appendTo($("#somediv"));// Create HTML <ul> element and append it to HTML DOM element with ID "somediv".
                $.each(responseJson, function(index, item) { // Iterate over the JSON array.
                    //$("<li>").text(item).appendTo($span);      // Create HTML <li> element, set its text content with currently iterated item and append it to the <ul>.
                	str += "<span style='width:100px'>" + item +" </span>"
                });
            	str += "</div>";
            	$('#somediv').append(str);
            });
        }
        nIntervId = setInterval(ajaxGet, 1000);
        </script>
    </head>
    <body>
    	<h2> Logs are in format (agent id xpos ypos)</h2>
        <!-- <button id="somebutton">press here</button>  -->
        <div id="somediv"></div>
    </body>
</html>