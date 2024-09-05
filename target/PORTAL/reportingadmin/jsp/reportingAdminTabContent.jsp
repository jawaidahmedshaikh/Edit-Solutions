<html>

<head>
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<script language="JavaScript">

	var f = null;

 	function init()  {

		f = document.theForm;
		document.all.contentIFrame.style.pixelWidth = .95 * document.all.contentIFrame.document.body.clientWidth;
		document.all.contentIFrame.style.pixelHeight = document.body.clientHeight;
 	}

	function showErrorStructureSummary() {

		sendTransactionAction("ReportingAdminTran", "showReportingRuleSummary", "contentIFrame");
	}

    function showErrorRelations(){

        sendTransactionAction("ReportingAdminTran", "showReportingRuleRelations", "contentIFrame");
    }

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value = action;
		f.target = target;

		f.submit();
	}

function setActiveTab(tabName) {

    // Inactivate all of them...
    document.getElementById("summaryTab").src = "/PORTAL/editing/images/summaryTabInactive.gif";
    document.getElementById("relationTab").src = "/PORTAL/editing/images/relationTabInactive.gif";
    document.getElementById("structureTab").src = "/PORTAL/editing/images/structureTabInactive.gif";

    // ... then just set the active one.
    document.getElementById(tabName).src = "/PORTAL/editing/images/" + tabName + "Active.gif";
}

</script>
</head>
<body  bgColor="#99BBBB" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
<input type="hidden" name="page" value="">
  <table style="position:relative; width:100%; top:0; left:0" cellspacing="0" cellpadding="0" border="0" height="3%" width="100%">
    <tr valign="bottom">

      <td height="22">
        <img src="/PORTAL/editing/images/summaryTabActive.gif"  style="position:relative; top:5; left:0" id="summaryTab" onClick= "setActiveTab('summaryTab');showErrorStructureSummary()">
        <img src="/PORTAL/editing/images/relationTabInactive.gif"   style="position:relative; top:5; left:0" id="relationTab" onClick= "setActiveTab('relationTab');showErrorRelations()">
        <img src="/PORTAL/editing/images/structureTabInactive.gif"  style="position:relative; top:5; left:0"  id="structureTab" onClick= "setActiveTab('structureTab');showCompanyStructures()">
      </td>
	</tr>
	<tr>
      <td bgcolor="#30548E">&nbsp; </td>
	</tr>
	<tr>
		<td>
		  <iframe style="overflow:visible"
		  	name="contentIFrame"
			id="contentIFrame"
			scrolling="yes"
			frameborder="0"
			src="/PORTAL/servlet/RequestManager?transaction=ReportingAdminTran&action=showReportingRuleSummary"
			>
	      </iframe>
		</td>
	</tr>
</table>

<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">

</form>
</body>
</html>