<%
    String agentPK = (String) session.getAttribute("searchValue");

    String iFrameSrc = null;

    if (agentPK == null){

        iFrameSrc = "/PORTAL/servlet/RequestManager?transaction=AgentDetailTran&action=showAgentDetailMainDefault";
    }
    else {

        iFrameSrc = "/PORTAL/servlet/RequestManager?transaction=AgentDetailTran&action=showAgentDetailMainContent&agentPK=" + agentPK;
    }
%>
<html>
<script language="JavaScript">
<!--

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v3.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document); return x;
}

function MM_swapImage() { //v3.0
  	var i,j=0,x,a=MM_swapImage.arguments;
	document.MM_sr=new Array;

	for(i=0;i<(a.length-2);i+=3)
   		if ((x=MM_findObj(a[i]))!=null)
		{
			document.MM_sr[j++]=x;
			if(!x.oSrc) x.oSrc=x.src;
			x.src=a[i+2];
		}
}

function setActiveTab(tabName) {

	if (tabName == "mainTab")  {

		MM_swapImage('agentMain','','/PORTAL/agent/images/agentMainTag.gif','financial','','/PORTAL/agent/images/financialTab.gif','license','','/PORTAL/agent/images/licenseTab.gif','contract','','/PORTAL/agent/images/contractTab.gif','adjustments','','/PORTAL/agent/images/adjustmentTab.gif','redirect','','/PORTAL/agent/images/redirectTab.gif','history','','/PORTAL/agent/images/historyTab.gif','hierarchy','','/PORTAL/agent/images/hierarchyTab_off.gif','bonus','','/PORTAL/agent/images/bonus.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif',1);
	}
	else if (tabName == "financialTab") {

		MM_swapImage('agentMain','','/PORTAL/agent/images/agentMainTab.gif','financial','','/PORTAL/agent/images/financialTag.gif','license','','/PORTAL/agent/images/licenseTab.gif','contract','','/PORTAL/agent/images/contractTab.gif','adjustments','','/PORTAL/agent/images/adjustmentTab.gif','redirect','','/PORTAL/agent/images/redirectTab.gif','history','','/PORTAL/agent/images/historyTab.gif','hierarchy','','/PORTAL/agent/images/hierarchyTab_off.gif','bonus','','/PORTAL/agent/images/bonus.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif',1);
	}
	else if (tabName == "licenseTab") {

		MM_swapImage('agentMain','','/PORTAL/agent/images/agentMainTab.gif','financial','','/PORTAL/agent/images/financialTab.gif','license','','/PORTAL/agent/images/licenseTag.gif','contract','','/PORTAL/agent/images/contractTab.gif','adjustments','','/PORTAL/agent/images/adjustmentTab.gif','redirect','','/PORTAL/agent/images/redirectTab.gif','history','','/PORTAL/agent/images/historyTab.gif','hierarchy','','/PORTAL/agent/images/hierarchyTab_off.gif','bonus','','/PORTAL/agent/images/bonus.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif',1);
	}
	else if (tabName == "contractTab") {

		MM_swapImage('agentMain','','/PORTAL/agent/images/agentMainTab.gif','financial','','/PORTAL/agent/images/financialTab.gif','license','','/PORTAL/agent/images/licenseTab.gif','contract','','/PORTAL/agent/images/contractTag.gif','adjustments','','/PORTAL/agent/images/adjustmentTab.gif','redirect','','/PORTAL/agent/images/redirectTab.gif','history','','/PORTAL/agent/images/historyTab.gif','hierarchy','','/PORTAL/agent/images/hierarchyTab_off.gif','bonus','','/PORTAL/agent/images/bonus.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif',1);
	}
	else if (tabName == "adjustmentTab") {

		MM_swapImage('agentMain','','/PORTAL/agent/images/agentMainTab.gif','financial','','/PORTAL/agent/images/financialTab.gif','license','','/PORTAL/agent/images/licenseTab.gif','contract','','/PORTAL/agent/images/contractTab.gif','adjustments','','/PORTAL/agent/images/adjustmentTag.gif','redirect','','/PORTAL/agent/images/redirectTab.gif','history','','/PORTAL/agent/images/historyTab.gif','hierarchy','','/PORTAL/agent/images/hierarchyTab_off.gif','bonus','','/PORTAL/agent/images/bonus.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif',1);
	}
	else if (tabName == "redirectTab") {

		MM_swapImage('agentMain','','/PORTAL/agent/images/agentMainTab.gif','financial','','/PORTAL/agent/images/financialTab.gif','license','','/PORTAL/agent/images/licenseTab.gif','contract','','/PORTAL/agent/images/contractTab.gif','adjustments','','/PORTAL/agent/images/adjustmentTab.gif','redirect','','/PORTAL/agent/images/redirectTag.gif','history','','/PORTAL/agent/images/historyTab.gif','hierarchy','','/PORTAL/agent/images/hierarchyTab_off.gif','bonus','','/PORTAL/agent/images/bonus.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif',1);
	}
	else if (tabName == "hierarchyTab") {

		MM_swapImage('agentMain','','/PORTAL/agent/images/agentMainTab.gif','financial','','/PORTAL/agent/images/financialTab.gif','license','','/PORTAL/agent/images/licenseTab.gif','contract','','/PORTAL/agent/images/contractTab.gif','adjustments','','/PORTAL/agent/images/adjustmentTab.gif','redirect','','/PORTAL/agent/images/redirectTab.gif','history','','/PORTAL/agent/images/historyTab.gif','hierarchy','','/PORTAL/agent/images/hierarchyTab_on.gif','bonus','','/PORTAL/agent/images/bonus.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif',1);
	}
    else if (tabName == "historyTab") {

		MM_swapImage('agentMain','','/PORTAL/agent/images/agentMainTab.gif','financial','','/PORTAL/agent/images/financialTab.gif','license','','/PORTAL/agent/images/licenseTab.gif','contract','','/PORTAL/agent/images/contractTab.gif','adjustments','','/PORTAL/agent/images/adjustmentTab.gif','redirect','','/PORTAL/agent/images/redirectTab.gif','history','','/PORTAL/agent/images/historyTag.gif','hierarchy','','/PORTAL/agent/images/hierarchyTab_off.gif','bonus','','/PORTAL/agent/images/bonus.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif',1);
	}
    else if (tabName == "requirementsTab") {

		MM_swapImage('agentMain','','/PORTAL/agent/images/agentMainTab.gif','financial','','/PORTAL/agent/images/financialTab.gif','license','','/PORTAL/agent/images/licenseTab.gif','contract','','/PORTAL/agent/images/contractTab.gif','adjustments','','/PORTAL/agent/images/adjustmentTab.gif','redirect','','/PORTAL/agent/images/redirectTab.gif','history','','/PORTAL/agent/images/historyTab.gif','hierarchy','','/PORTAL/agent/images/hierarchyTab_off.gif','bonus','','/PORTAL/agent/images/bonus.gif','requirements','','/PORTAL/quote/images/requirementsTag.gif',1);
	}
}

//-->
</script>

<script language="Javascript1.2">

	var f = null;
	var currentPage = "";

 	function init()  {

		f = document.tabContentForm;
		document.all.contentIFrame.style.pixelWidth = .95 * document.all.contentIFrame.document.body.clientWidth;
		document.all.contentIFrame.style.pixelHeight = document.body.clientHeight;

		currentPage = "agentMain";
 	}

	function requiredFieldsMissing() {

		return false;
	}

	function showAgentDetailMain() {

		currentPage = "agentMain";

		window.frames["contentIFrame"].sendTransactionAction("AgentDetailTran", "showAgentDetailMainContent", "contentIFrame");
    }

	function showFinancial() {

		currentPage = "financial";

		window.frames["contentIFrame"].sendTransactionAction("AgentDetailTran", "showFinancial", "contentIFrame");
	}

	function showLicense() {

		currentPage = "license";

		window.frames["contentIFrame"].sendTransactionAction("AgentDetailTran", "showLicense", "contentIFrame");
	}

	function showContract() {

		requiredFieldsMissing();

		currentPage = "contract";

		window.frames["contentIFrame"].sendTransactionAction("AgentDetailTran", "showContract", "contentIFrame");
	}

	function showAdjustments() {

		requiredFieldsMissing();

		currentPage = "adjustments";

		window.frames["contentIFrame"].sendTransactionAction("AgentDetailTran", "showAdjustments", "contentIFrame");
	}

	function showRedirect() {

		currentPage = "redirect";

		window.frames["contentIFrame"].sendTransactionAction("AgentDetailTran", "showRedirect", "contentIFrame");
	}

	function showHierarchy() {

		currentPage = "hierarchy";

		window.frames["contentIFrame"].sendTransactionAction("AgentDetailTran", "showHierarchy", "contentIFrame");
	}

	function showHistory() {

		currentPage = "history";

		window.frames["contentIFrame"].sendTransactionAction("AgentDetailTran", "showHistory", "contentIFrame");
	}

	function showRequirements()
    {
		currentPage = "requirements";

		window.frames["contentIFrame"].sendTransactionAction("AgentDetailTran", "showRequirements", "contentIFrame");
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;
		f.target = target;

		f.submit();
	}

</script>

<body bgColor="#99BBBB" onLoad="init();MM_preloadImages('/PORTAL/agent/images/financialTab.gif','/PORTAL/agent/images/licenseTab.gif','/PORTAL/agent/images/contractTab.gif','/PORTAL/agent/images/financialTag.gif','/PORTAL/agent/images/licenseTag.gif','/PORTAL/agent/images/contractTag.gif','/PORTAL/agent/images/agentMainTag.gif','/PORTAL/agent/images/agentMainTab.gif','/PORTAL/agent/images/adjustmentTab.gif','/PORTAL/agent/images/adjustmentTag.gif','/PORTAL/agent/images/redirectTab.gif','/PORTAL/agent/images/redirectTag.gif')">
<form name="tabContentForm" method="post" action="/PORTAL/servlet/RequestManager">

<table style="position:relative; width:100%; top:0; left:0" cellspacing="0" cellpadding="0" border="0">
  <tr valign="bottom">
    <td>
      <img src="/PORTAL/agent/images/agentMainTab.gif"  style="position:relative; top:5; left:0" onClick="showAgentDetailMain()" name="agentMain" width="60" height="25">
      <img src="/PORTAL/agent/images/licenseTab.gif" style="position:relative; top:5; left:0" onClick="showLicense()" name="license" width="60" height="25">
      <img src="/PORTAL/agent/images/contractTab.gif" style="position:relative; top:5; left:0" onClick="showContract()" name="contract" width="60" height="26">
      <img src="/PORTAL/agent/images/hierarchyTab_off.gif" style="position:relative; top:5; left:0" onClick="showHierarchy()" name="hierarchy" width="60" height="25">
      <img src="/PORTAL/agent/images/adjustmentTab.gif" style="position:relative; top:5; left:0" onClick="showAdjustments()" name="adjustments" width="60" height="26">
      <img src="/PORTAL/agent/images/redirectTab.gif" style="position:relative; top:5; left:0" onClick="showRedirect()" name="redirect" width="60" height="26">
      <img src="/PORTAL/quote/images/requirementsTab.gif" style="position:relative; top:5; left:0" onClick="showRequirements()" name="requirements" width="60" height="26">
      <img src="/PORTAL/agent/images/financialTab.gif" style="position:relative; top:5; left:0" onClick="showFinancial()" name="financial" width="60" height="25">
      <img src="/PORTAL/agent/images/historyTab.gif" style="position:relative; top:5; left:0" onClick="showHistory()" name="history" width="60" height="26">
    </td>
  </tr>
  <tr bgcolor="#30548E">
	<td>&nbsp;
	</td>
  </tr>
  <tr>
	<td>
	  <iframe style="overflow:visible"
            name="contentIFrame"
            id="contentIFrame"
			scrolling="yes"
			frameborder="0"
			src="<%= iFrameSrc %>">
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


