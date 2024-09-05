<!-- ****** JAVA CODE ***** //-->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="fission.beans.*, java.util.*" %>

<jsp:useBean id="paramBean"
	class="fission.beans.SessionBean" scope="session"/>
	
<% 

	// Unable to get pageBean from request scope when within a frameset.
	// Therefore, we had to explicitly get it from the session scope.
	PageBean pageBean = paramBean.getPageBean("debugScriptBean");

	String[] scriptLines = pageBean.getValues("scriptLines");	
	
	String[] breakPoints = pageBean.getValues("breakPoints");	
	
	String instPtr = pageBean.getValue("instPtr")==""?"0":pageBean.getValue("instPtr");
		
	String lastInstPtr = pageBean.getValue("lastInstPtr")==""?"0":pageBean.getValue("lastInstPtr");
	
	String currentRow = pageBean.getValue("currentRow")==""?"0":pageBean.getValue("currentRow");
	
%>


<html>
<head>
<title>Script</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/ENGINE.css" rel="stylesheet" type="text/css">




<!-- ****** JAVASCRIPT CODE ***** //-->
<script language="JavaScript">
<!--
var f = null;

	function init() {
		f = document.scriptForm;
		// should call selectRow function to highlight
		selectRow(<%= instPtr %>,<%= lastInstPtr %>,<%= currentRow %>);
	}

	function sendTransactionAction(transaction, action, target) {
		f.transaction.value = transaction;
    	f.action.value = action;
		f.target = target
    	f.submit();
	}


	function processBP (rIndex) {
		f.breakpoint.value = rIndex;
    	sendTransactionAction("ScriptTran", "processBreakPoint", "_top");
	}


	function selectRow (currInst, lastInst, currRow) {

		//alert("values from scrip  " + currInst + " : " + lastInst + " : " + currRow);
    	currInstTD = document.getElementById("ln" + currInst);
    	lastInstTD = document.getElementById("ln" + lastInst);
    	currRowTD  = document.getElementById("ln" + currRow);
    	if (currInstTD != null) {
        	currInstTD.style.backgroundColor="#D3D3D3";
    	}
    	if ((lastInstTD != null) && (currInst > 0)) {
			//alert(lastInstTD.id);
			//alert(lastInstTD.style.backgroundColor);
        	lastInstTD.style.backgroundColor="#FFFFFF";
			//alert(lastInstTD.style.backgroundColor);

    	}
    	if (currRowTD != null) {
        	currRowTD.scrollIntoView(false);
    	}
	}
//-->
</script>
</head>


<!-- ****** HTML CODE ***** //-->
<body class="withNoImage" style="margin-top:0;margin-left:0;" marginheight="0" marginwidth="0"
      onload="init();" bgColor="#FFFFFF">
<form method="post" action="/PORTAL/servlet/RequestManager" name="scriptForm"
    id="scriptForm" target="main">

<table id="scriptTable" bgColor="FFFFFF" border="0" width="100%" cellpadding="2" cellspacing="0">
<% if ((scriptLines != null) && (scriptLines.length > 0))  {
	for (int i=0; i < scriptLines.length; i++) { %>        
		<tr ondblclick="processBP(rowIndex);">
			<td class="bp" width="5">
				<% if (breakPoints != null && breakPoints.length > 0)  { 
					for (int j=0; j<breakPoints.length; j++) { 
						if ((Integer.toString(i)).equals(breakPoints[j])) { %>
        					B
        			<%  break; } } }%>
			</td>
        	<td class="ln" width="10"> <%= i %>: </td>
			<td align="left" width="90%" nowrap id="ln<%= i %>" >
        		<%= scriptLines[i] %></td>
   		</tr>
	<% } } %>
</table>


<!-- ****** Hidden Fields ***** //-->
<input id="transaction" type="hidden" name="transaction" value="">
<input id="action"      type="hidden" name="action"      value="">
<input id="breakpoint"  type="hidden" name="breakpoint"  value="">
</form>
</body>
</html>