<!-- ****** JAVA CODE ***** -->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="fission.beans.PageBean" %>

<jsp:useBean id="paramBean"
	class="fission.beans.SessionBean" scope="session"/>

<%
	PageBean pageBean = paramBean.getPageBean("debugScriptBean");

	String[] scriptLines = pageBean.getValues("scriptLines");

	boolean isScriptLoaded = scriptLines.length>0?true:false;

	String scriptName = pageBean.getValue("scriptName");

%>


<html>
<head>
<title>Analyzer Buttons</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/ENGINE.css" rel="stylesheet" type="text/css">



<!-- ****** Java Script CODE ***** -->
<script language="JavaScript">
<!--
	var f = null;

	function init() {
		f = document.debugForm;

	}

	function sendTransactionAction(transaction, action, target) {
		f.transaction.value = transaction;
		f.action.value = action;
		f.target = target;
		f.submit();
	}

	function changeParameters() {

        top.window.close();
	}

	function runCurrentScriptInstruction() {
		sendTransactionAction("ScriptTran", "debugStep", "viewer");
	}

	function runScript() {
		sendTransactionAction("ScriptTran", "debugRun", "_top");
	}

	function resetScript() {
		sendTransactionAction("ScriptTran", "debugReset", "_top");
	}

	function clearScript() {

        top.window.close();
	}

// -->
</script>
</head>


<!-- ****** HTML CODE ***** //-->
<body style="margin-top:0;" onLoad="init();" bgColor="#99BBBB">
<form method="post" action="/PORTAL/servlet/RequestManager" name="debugForm">


<table  border="0" cellspacing="1" cellpadding="1">
    <tr>
        <td align="left" width="22%">

               Mode: Debug

        </td>
        <td align="right">
            <input type="button"  name="parms" value="Param"
                title="Change Parameters"
                onClick="changeParameters();"
            >
        </td>
        <td align="center">
            <input type="button" name="step" value=" Step "
				<%= isScriptLoaded?"":"DISABLED" %>
                title="Run Current Script Instruction"
                onClick="runCurrentScriptInstruction();"
             >
        </td>
        <td align="center">
            <input type="button" name="run" value="  Run  "
				<%= isScriptLoaded?"":"DISABLED" %>
				title="Run Script"
                onClick="runScript();"
            >
        </td>
        <td align="center">
            <input type="button" name="reset" value="Reset"
                title="Reset Script to Run Again"
                onClick="resetScript();"
            >
        </td>
        <td align="center">
            <input type="button" name="clear" value=" Clear"
                title="Clear Script"
                onClick="clearScript();"
            >
        </td>
    </tr>

    <tr>
        <td align="left" width="35%">
			Script: <%= scriptName %>
        </td>

    </tr>

</table>


<!-- ****** Hidden Fields ***** //-->
<input id="transaction"   type="hidden" name="transaction" value="">
<input id="action"        type="hidden" name="action" value="">
<input id="scriptId"      type="hidden" name="scriptId" value="">
<input id="financialType" type="hidden" name="financialType" value="">
<input id="mode"          type="hidden" name="mode" value="D">

</form>
</body>
</html>