<!-- callChain.jsp //-->
<%@ page import="fission.dm.valueobject.CallChainVO,
                 engine.sp.*,
                 edit.common.*,
                 edit.common.vo.*" %>

<!-- ****** JAVA CODE ***** //-->
<%
	CallChainVO vo = (CallChainVO) request.getAttribute("callChainVO"); 	

	ScriptChainNodeWrapper rootNode = (ScriptChainNodeWrapper)(vo.getRow(0).get(0));
	

	// These must be set for the javascript to work
	int totalPixels = 3000;
	int maxRowNum = vo.getRowCount();
%>
 

<html>

<head>


<!-- ****** Styles ***** //-->
<style>

	.specialButton {

		background-color:#669999; 
		border-style:groove;
		font-family:Arial;	
		font-weight:bold
	}

</style>



<!-- ****** JAVAScript CODE ***** //-->
	 
<script language="Javascript1.2">

	var nextPixel = 0;
	var totalPixels = <%= totalPixels %>;
	
	var maxRowNum = <%= maxRowNum %>;
	
	var callTableWidth;
	var callTableHeight;	
	
	var baseCellSide  = 75;
	
	var f = null;	

	var cellSpan = null;
	
	// This is the original data (from the server) to be stored as an array of arrays. 
	// This way, we can create uneven 2-D arrays to simulate a tree structure
	
	var cellData = new Array(maxRowNum);
	
	
	document.onmousemove = showCoordinates;
	
	function showCoordinates(){
	
		window.status = "x:" + window.event.clientX + "  y:" + window.event.clientY;
	}

	function closeWindow(){
	
		window.close();
	}
	

	// *******************************
	// ***** Graphing Functions *****
	// *******************************

	function resetPixels(){
	
		for (var i = 0; i < nextPixel; i++){
		
			document.getElementById("img" + i).style.top = 0;
			document.getElementById("img" + i).style.left = 0;
		}
		
		nextPixel = 0;
	}


	function Point(x, y){
	
		this.x = x;
		this.y = y;
	}
	
	function findCenterPoint(cell){	
		
		// Find clientX
		var clientX =  cell.offsetLeft + cell.offsetWidth/2;
		
		// Find clientY	
		var clientY = cell.offsetTop + cell.offsetHeight/2;
		
		return new Point(clientX, clientY);	
	}	

	function drawLine(pointA, pointB){

		// find y = (y2 - y2)/(x2 - x1) + b;
		
		var x1 = pointA.x;
		var y1 = pointA.y;
		
		var x2 = pointB.x;
		var y2 = pointB.y;
		
		// m
		
		var m;
		
		if ((x1 == x2) & (y1 == y2)){
		
			return;
		}		
		else if (x1 == x2) {
		
			// Just let m approach infinity 
			m = 1000000;
		}
		else if (y1 == y2){
		
			// just let m approach zero
			m = .000001
		}
		else {
		
			m = (y2 - y1) / (x2 - x1);		
		}
		
			
		// b
		var b = y1 - m*x1;
		
		// now loop through the y range, finding the appropriate y;
		
		
		for (var y = y2; y < y1; y +=6){		

			x = (y - b)/m;		
			
			document.getElementById("img" + nextPixel).style.top = y;
			document.getElementById("img" + nextPixel).style.left = x;
			
			nextPixel++;		
		}		
	}	
	
	function connectCells(){
	
		nextPixel = 0;	
		
		for (var i = 0; i < maxRowNum; i++){
		
			var cellsInRow = cellData[i].length;
			
			for (var j = 0; j < cellsInRow; j++){
			
				var cellId = cellData[i][j].id;
			
				var childCell = document.getElementById(cellId);
				var parentCell = document.getElementById(childCell.parentId);
				
				drawLine(findCenterPoint(childCell), findCenterPoint(parentCell));			
			}		
		}		
	}
	
	
	
	// ***********************************
	// ***** Look and Feel Functions *****
	// ***********************************

	function zoom(level){
	
		var xBase;
	
		if (level == "1:1"){
			
			xBase = baseCellSide;
		}		
		else if (level == "2:1"){

			xBase = baseCellSide * 2;			
		}		
		else if (level == "3:1"){

			xBase = baseCellSide * 3;			
		}	
		else if (level == "1:2"){
		
			xBase = baseCellSide / 2;				
		}		
		else if (level == "1:3"){

			xBase = baseCellSide / 3;			
		}			
		
		
		var cellCount = 0;
		
		for (var i = 0; i < cellData.length; i++){
			
			for (var j = 0; j < cellData[i].length; j++){
			
				var cellId = cellData[i][j].id;			
			
				var cell = document.getElementById(cellId);
				
				cell.style.pixelWidth  = xBase;
				cell.style.pixelHeight = xBase;	
			}		
		}		
		
		resetPixels();
		setTimeout("connectCells()",5);
	}

	function setHasFocusLook(){
	
		var srcCell = window.event.srcElement;
		
		srcCell.style.backgroundColor = "#30548E";
		srcCell.style.color = "#FFFFFF";
	}
	
	function setLostFocusLook(){

		var srcCell = window.event.srcElement;
		
		srcCell.style.backgroundColor = "#669999";
		srcCell.style.color = "#000000";	
	}
	
	
	// *********************************
	// ***** Operational Functions *****
	// *********************************
	


	
	function showCellDetail(){
	
		
		// Pop up the dialog, and send the appropriate transaction / action;

		cellSpan = window.event.srcElement;
		
		window.open("/PORTAL/engine/jsp/callChainDetail.jsp","callChainDetail","width=300,height=300,toolbar=no,menubar=no,location=no,status=no,scrollbars=yes,resizable=yes");
	}
	
	function getCurrentCellSpan(){

		return cellSpan;
	}	
	
	function sendTransactionAction(transaction, action, target) {
	
		f.transaction.value = transaction;
		f.action.value		= action;
		f.target			= target;
		f.submit();		
	}


	// ***********************************
	// ***** Initial Setup Functions *****
	// ***********************************	

	function init() {
	
		f = document.callChainForm;
	
		callTableHeight = document.body.clientHeight * .75;
		callTableWidth  = document.body.clientWidth * .75;

		buildCellModel();		
		buildTableStructure();	
		
		
		setTimeout("connectCells()",5);						
	}	

	function getPreferredWidth() {

	
		return 0.9 * screen.width;
	}
	
	function getPreferredHeight() {		


		return 0.9 * screen.height;
	}
	

	// Construct the MODEL	
	function buildCellModel(){	
			
		<%
		for (int i = 0; i < maxRowNum; i++) {

			out.println("cellData[" + i + "] = new Array(" + vo.getRowSize(i) + ");");
		}
		
		out.println("var cellId = '';");

		for (int i = 0; i < maxRowNum; i++){

			for (int j = 0; j < vo.getRowSize(i); j++){
			
				ScriptChainNodeWrapper node = (ScriptChainNodeWrapper)(vo.getRow(i).get(j));

				out.println("cellId = 'cell_" + node.getNodeId() + "';");
			
				out.println("cellData[" + i + "][" + j + "] = new Object();");
				
				// a. assing the unique cellId
				out.println("cellData[" + i + "][" + j + "].id = cellId;");
				
				// b. assign the parentId				
				
				if (node.getParent() == null){
				
					out.println("cellData[" + i + "][" + j + "].parentId = 'cell_" + node.getNodeId() + "';"); 
				}
				else {
				
					out.println("cellData[" + i + "][" + j + "].parentId = 'cell_" + node.getParent().getNodeId() + "';");
				}
				
				// c. assign the scriptId
				out.println("cellData[" + i + "][" + j + "].scriptId = '" + node.getScriptId() + "';");
				
				// d. assign the display data
				out.println("cellData[" + i + "][" + j + "].displayData = \"" + node.getNodeDescription() + "\";");
				
				// e. assign the scriptLines

				ScriptLineVO[] scriptLines = node.getScriptLines();

				out.println("cellData[" + i + "][" + j + "].scriptLines = new Array();");

				for (int k = 0; k < scriptLines.length; k++){
				
					out.println("cellData[" + i + "][" + j + "].scriptLines[" + k + "] = \"" + scriptLines[k].getScriptLine() + "\";");
				}
											
			}		
		}				

		%>
	}	
	
	
	// CONSTRUCT THE VIEW
	function buildTableStructure(){	
		
		document.all.callTable.style.pixelWidth = callTableWidth;
		document.all.callTable.style.pixelHeight = callTableHeight;
		
		
		// build the html table		

		var cellCount = 0;
		
		for (var i = 0; i < cellData.length; i++){
		
			var tableRow = document.all.callTable.insertRow();
			var tableCell = tableRow.insertCell();
			
			tableCell.noWrap = true;	
			
			tableRow.align="center";	
			
			var innerHTML = "";			
			
			// 1. Build a table where each row contains a varying number of spans.
			for (var j = 0; j < cellData[i].length; j++){
			
			
				// This is where you would add the display contents of each cell.
				// Here, I'm just showing the cell_id
			
				var cellId = cellData[i][j].id;				
				
				// Each span needs a unique ID.
				innerHTML += "<span id='" + cellId + "'></span>";				
			}		

			// set the span elements into the cell

			tableCell.innerHTML = innerHTML;
			
			// 2. Now loop through these spans, and map the data from the Model to the Display
			
			for (var j = 0; j < cellData[i].length; j++){			
				
				var cellId = cellData[i][j].id;				
				
				var spanCell = document.getElementById(cellId);
				
				spanCell.style.position = "relative";
				spanCell.style.pixelWidth = baseCellSide;
				spanCell.style.pixelHeight = baseCellSide;
				spanCell.style.margin="50px";				
				spanCell.style.backgroundColor = "#669999";
				spanCell.style.borderStyle = "solid";
				spanCell.style.borderWidth="thin";	
				spanCell.style.zIndex = 10;		
				spanCell.onmouseover = setHasFocusLook;
				spanCell.onmouseout  = setLostFocusLook;	
				spanCell.onclick = showCellDetail;			
				
				
				// a. Assign the uniqueId
				spanCell.id = cellData[i][j].id;
				
				// b. assign the child / parent relationships to the spanCell				
				spanCell.parentId = cellData[i][j].parentId;	
				
				// c. assign the scriptId associated with this spanCell
				spanCell.scriptId = cellData[i][j].scriptId; 												
				
				// e. assign the display data to the spanCell
				spanCell.innerHTML = "<br>" + cellData[i][j].displayData;

				// f. assign the scriptLines over
				spanCell.scriptLines = cellData[i][j].scriptLines;
			}
		}	
	}
</script>

</head>

<!-- ****** HTML CODE ***** //-->

<body onLoad="init()" bgColor="white">

<form name="callChainForm" method="post" action="/PORTAL/servlet/RequestManager">


<table align="center" border="0" width="100%" cellspacing="5">
	<tr>
		<td valign="bottom" align="center" nowrap>
		
			<h2> Script: <%= rootNode.getNodeDescription() %><h2>
		
		</td>
	</tr>
</table>

<table id="callTable" align="center" border="0" cellspacing="5">

</table>

<table align="center" border="0" cellspacing="5">
	<tr>
		<td valign="bottom" nowrap>
			<font face="arial" size="4">ZOOM:</font> 
			(-) <input type="button" value="  1:3  " onClick="zoom('1:3')" class="specialButton">
			<input type="button" value="  1:2  " onClick="zoom('1:2')" class="specialButton">
			<input type="button" value="  1:1  " onClick="zoom('1:1')" class="specialButton">
			<input type="button" value="  2:1  " onClick="zoom('2:1')" class="specialButton">
			<input type="button" value="  3:1  " onClick="zoom('3:1')" class="specialButton"> (+)			

			&nbsp;&nbsp;&nbsp;<input type="button" class="specialButton" value="Close" onClick="closeWindow()">
		</td>
	</tr>
</table>


<!-- Create a pool of image points. //-->
<%

	for (int i = 0; i < totalPixels; i++) {
	
		out.println("<img id=\"img" + i + "\" src=../engine/images/pixel.gif style=\"position:absolute; top:0; left:0; z-index:1\" width=5 height=5>");
	}
%>


<!-- ****** HIDDEN FIELDS ***** //-->

<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

</form>


</body>
</html> 