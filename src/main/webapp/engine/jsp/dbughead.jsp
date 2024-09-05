<!-- ****** JAVA CODE ***** //-->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<html>
<head>
<title>Analyzer Header</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/ENGINE.css" rel="stylesheet" type="text/css">



<!-- ****** Java Script CODE ***** //-->
<script language="JavaScript">

	var f = null;

	function showStackViewer() {

		sendTransactionAction("ScriptTran", "showStackViewer", "viewer");
	}


	function showTableViewer() {

		sendTransactionAction("ScriptTran", "showFunctionViewer", "viewer");
	}

    function showVectorViewer() {

		sendTransactionAction("ScriptTran", "showVectorViewer", "viewer");
	}

	function showOutputViewer()	{

		sendTransactionAction("ScriptTran", "showOutputViewer", "viewer");
	}
	
	function showDocumentViewer() {
		sendTransactionAction("ScriptTran", "showDocumentViewer", "viewer");
	}

	function getScriptCallChain() {

        var height = .80 * screen.height;
        var width  = screen.width;

        openDialog("","callChainDialog","left=0,top=0,scrollbars=yes,resizable=no,width=" + width + ",height=" + height)
		sendTransactionAction("ScriptTran","getScriptCallChain","callChainDialog");
	}

    function openDialog(theURL,winName,features) {

        dialog = window.open(theURL,winName,features);
    }

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value	  = action;
		f.target 	  = target;
		f.submit();
	}


	function init()	{
		f = document.headForm;
	}

</script>
</head>


<!-- ****** HTML CODE ***** //-->

<body onLoad="init()" bgColor="#99BBBB">

<table width="100%" border="0" bgColor="#DDDDDD"  style="border-style:solid; border-width:1; border-color: black">

<tr>
    <td><br><br><br><br>
        <a href="javascript:showStackViewer()">
             <u>Stack/Working Storage</u>
        </a>
    </td>
    <td></td>
</tr>

<tr>
   <td><br><br>
        <a href="javascript:showTableViewer()">
            <u>Tables</u>
        </a>
    </td>
    <td></td>
</tr>

<tr>
   <td><br><br>
        <a href="javascript:showVectorViewer()">
            <u>Vectors</u>
        </a>
    </td>
    <td></td>
</tr>

<tr>
    <td><br><br>
        <a href="javascript:getScriptCallChain()">
            <u>Call Chain</u>
      </td>
</tr>
<tr>
    <td><br><br>
        <a href="javascript:showDocumentViewer()">
            <u>Documents</u>
      </td>
</tr>
</table>

<form method="Post" name="headForm" action="/PORTAL/servlet/RequestManager">


<!-- ****** HIDDEN FIELDS ***** //-->

<input type="hidden" name="transaction" value="">
<input type="hidden" name="action" value="">

</form>
</body>
</html>