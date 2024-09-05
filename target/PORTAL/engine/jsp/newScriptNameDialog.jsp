<!-- ******JAVA*CODE***** //-->

<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.net.*, java.util.*, engine.dm.*, fission.global.*, fission.beans.*" %>


<jsp:useBean id="pageBean" class="fission.beans.PageBean" scope="request"/>

<html>
<head>
<title>New Script Name</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">


<!-- ******JAVASCRIPT*CODE****** //-->

<script language="Javascript1.2">

	var f 				= null;
	var scriptId   		= "0";
	var scriptName 		= null;
	var transaction		= null;
	var action			= null;

	function init() {

		f = document.scriptSelectForm;
	}

	function bEnter() {

        scriptName = f.scriptName.value;
        if (scriptName == "") {

            alert("New Script Name Must Be Entered");
        }

        else {

            sendTransactionAction("ScriptTran", "setupForNewScript", "main");
            window.close();
        }
	}

	function bCancel() {

		window.close();
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
    	f.action.value		= action;
		f.target 			= target;
		f.submit();
	}


</script>
</head>

<!-- ******HTML*CODE****** //-->

<body class="dialog" onLoad="init()">
<form name="scriptSelectForm" action="/PORTAL/servlet/RequestManager">

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left">
        Enter New Script Name:<br>
        <input type="text" name="scriptName">
    </td>
  </tr>

  <tr>
    <td align="right">
        <input type="button" name="enter" value="Enter" onClick="bEnter()">
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
    </td>
  </tr>
</table>


<!-- ******HIDDEN*FIELDS****** //-->

	<input type="hidden" name="transaction" value="">
	<input type="hidden" name="action" value="">

  </form>
</body>
</html>
