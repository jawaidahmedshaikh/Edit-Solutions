<!-- callChainDetail.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function setCellSpan(cellSpan){

		alert("here");
	}

	function init() {

		f = document.callDetailForm;

		showCellDetail(opener.getCurrentCellSpan());
	}

	function showCellDetail(cellSpan){

		var scriptLines = cellSpan.scriptLines;

		var scriptLinesText = "";

		for (var i = 0; i < scriptLines.length; i++){

			scriptLinesText += i+" "+scriptLines[i] + "<br>";
		}

		document.all.scriptLines.innerHTML = scriptLinesText;
	}

	function closeWindow(){

		window.close();
	}


	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();

	}


</script>

<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF" onLoad="init()">
<form name="callChainDetailForm" method="post" action="/CONTRACT/servlet/RequestManager">

  <table id="table1" width="100%" border="0" cellspacing="0" cellpadding="0">



	<tr>
		<td  id="scriptLines" nowrap>

		</td>
	</tr>

	<tr>
		<td align="right" nowrap>

			<input type="button" value="close" onClick="closeWindow()">
		</td>
	</tr>

  </table>

 <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="bankAccountNumber" value="">

</form>
</body>
</html>