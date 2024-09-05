<%@ page import="fission.utility.Util"%>
<%
    String segmentPK = (String) session.getAttribute("searchValue");
    String optionCode = Util.initString((String) session.getAttribute("optionCode"), "");
    session.removeAttribute("searchValue");

    String iFrameSrc = null;

    if (segmentPK == null){

        iFrameSrc = "/PORTAL/servlet/RequestManager?transaction=ContractDetailTran&action=showContractMainDefault";
    }
    else {

        iFrameSrc = "/PORTAL/servlet/RequestManager?transaction=ContractDetailTran&action=loadContract&segmentPK=" + segmentPK;
    }
%>
<html>
<script language="JavaScript">
<!--
var option = "<%= optionCode %>";

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

    if (option == "Traditional")
    {
        if (tabName == "mainTab")  {

            MM_swapImage('contractMain','','/PORTAL/contract/images/contractMainTag.gif','clients','','/PORTAL/contract/images/clientsTab.gif','agent','','/PORTAL/contract/images/agentsTab.gif','loan','','/PORTAL/contract/images/loanTab.gif','requirements','','/PORTAL/contract/images/requirementsTab.gif','history','','/PORTAL/contract/images/historyTab.gif',1);
        }
        else if (tabName == "loanTab") {

            MM_swapImage('contractMain','','/PORTAL/contract/images/contractMainTab.gif','clients','','/PORTAL/contract/images/clientsTab.gif','agent','','/PORTAL/contract/images/agentsTab.gif','loan','','/PORTAL/contract/images/loanTag.gif','requirements','','/PORTAL/contract/images/requirementsTab.gif','history','','/PORTAL/contract/images/historyTab.gif',1);
        }
        else if (tabName == "clientTab") {

            MM_swapImage('contractMain','','/PORTAL/contract/images/contractMainTab.gif','clients','','/PORTAL/contract/images/clientsTag.gif','agent','','/PORTAL/contract/images/agentsTab.gif','loan','','/PORTAL/contract/images/loanTab.gif','requirements','','/PORTAL/contract/images/requirementsTab.gif','history','','/PORTAL/contract/images/historyTab.gif',1);
        }
        else if (tabName == "agentTab") {

            MM_swapImage('contractMain','','/PORTAL/contract/images/contractMainTab.gif','clients','','/PORTAL/contract/images/clientsTab.gif','agent','','/PORTAL/contract/images/agentsTag.gif','loan','','/PORTAL/contract/images/loanTab.gif','requirements','','/PORTAL/contract/images/requirementsTab.gif','history','','/PORTAL/contract/images/historyTab.gif',1);
        }
        else if (tabName == "requirementsTab") {

            MM_swapImage('contractMain','','/PORTAL/contract/images/contractMainTab.gif','clients','','/PORTAL/contract/images/clientsTab.gif','agent','','/PORTAL/contract/images/agentsTab.gif','loan','','/PORTAL/contract/images/loanTab.gif','requirements','','/PORTAL/contract/images/requirementsTag.gif','history','','/PORTAL/contract/images/historyTab.gif',1);
        }
        else if (tabName == "historyTab") {

            MM_swapImage('contractMain','','/PORTAL/contract/images/contractMainTab.gif','clients','','/PORTAL/contract/images/clientsTab.gif','agent','','/PORTAL/contract/images/agentsTab.gif','loan','','/PORTAL/contract/images/loanTab.gif','requirements','','/PORTAL/contract/images/requirementsTab.gif','history','','/PORTAL/contract/images/historyTag.gif',1);
        }
    }
    else
    {
        if (tabName == "mainTab")  {

            MM_swapImage('contractMain','','/PORTAL/contract/images/contractMainTag.gif','clients','','/PORTAL/contract/images/clientsTab.gif','agent','','/PORTAL/contract/images/agentsTab.gif','investment','','/PORTAL/contract/images/investmentsTab.gif','requirements','','/PORTAL/contract/images/requirementsTab.gif','history','','/PORTAL/contract/images/historyTab.gif',1);
        }
        else if (tabName == "investmentTab") {

            MM_swapImage('contractMain','','/PORTAL/contract/images/contractMainTab.gif','clients','','/PORTAL/contract/images/clientsTab.gif','agent','','/PORTAL/contract/images/agentsTab.gif','investment','','/PORTAL/contract/images/investmentsTag.gif','requirements','','/PORTAL/contract/images/requirementsTab.gif','history','','/PORTAL/contract/images/historyTab.gif',1);
        }
        else if (tabName == "clientTab") {

            MM_swapImage('contractMain','','/PORTAL/contract/images/contractMainTab.gif','clients','','/PORTAL/contract/images/clientsTag.gif','agent','','/PORTAL/contract/images/agentsTab.gif','investment','','/PORTAL/contract/images/investmentsTab.gif','requirements','','/PORTAL/contract/images/requirementsTab.gif','history','','/PORTAL/contract/images/historyTab.gif',1);
        }
        else if (tabName == "agentTab") {

            MM_swapImage('contractMain','','/PORTAL/contract/images/contractMainTab.gif','clients','','/PORTAL/contract/images/clientsTab.gif','agent','','/PORTAL/contract/images/agentsTag.gif','investment','','/PORTAL/contract/images/investmentsTab.gif','requirements','','/PORTAL/contract/images/requirementsTab.gif','history','','/PORTAL/contract/images/historyTab.gif',1);
        }
        else if (tabName == "requirementsTab") {

            MM_swapImage('contractMain','','/PORTAL/contract/images/contractMainTab.gif','clients','','/PORTAL/contract/images/clientsTab.gif','agent','','/PORTAL/contract/images/agentsTab.gif','investment','','/PORTAL/contract/images/investmentsTab.gif','requirements','','/PORTAL/contract/images/requirementsTag.gif','history','','/PORTAL/contract/images/historyTab.gif',1);
        }
        else if (tabName == "historyTab") {

            MM_swapImage('contractMain','','/PORTAL/contract/images/contractMainTab.gif','clients','','/PORTAL/contract/images/clientsTab.gif','agent','','/PORTAL/contract/images/agentsTab.gif','investment','','/PORTAL/contract/images/investmentsTab.gif','requirements','','/PORTAL/contract/images/requirementsTab.gif','history','','/PORTAL/contract/images/historyTag.gif',1);
        }
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

		currentPage = "contractMain";
 	}

	function requiredFieldsMissing() {

		return false;
	}

	function showContractMain() {

		currentPage = "contractMain";

		window.frames["contentIFrame"].prepareToSendTransactionAction("ContractDetailTran", "showContractMainContent", "contentIFrame");
	}

	function showContractRider() {

		currentPage = "contractRider";

		window.frames["contentIFrame"].prepareToSendTransactionAction("ContractDetailTran", "showContractRiders", "contentIFrame");
	}

	function showContractInvestments() {

		requiredFieldsMissing();

		currentPage = "contractInvestments";

		window.frames["contentIFrame"].prepareToSendTransactionAction("ContractDetailTran", "showContractInvestments", "contentIFrame");
	}

	function showContractLoan()
    {
		requiredFieldsMissing();

		currentPage = "contractLoan";

		window.frames["contentIFrame"].prepareToSendTransactionAction("ContractDetailTran", "showLoanInfoForTrad", "contentIFrame");
	}

	function showContractNonPayeeOrPayee() {

		requiredFieldsMissing();

		currentPage = "contractNonPayee";

		window.frames["contentIFrame"].prepareToSendTransactionAction("ContractDetailTran", "showContractNonPayeeOrPayee", "contentIFrame");
	}

	function showContractAgents() {

		requiredFieldsMissing();

		currentPage = "contractAgents";

		window.frames["contentIFrame"].prepareToSendTransactionAction("ContractDetailTran", "showContractAgents", "contentIFrame");
	}

	function showContractRequirements() {

		currentPage = "contractRequirements";

		window.frames["contentIFrame"].prepareToSendTransactionAction("ContractDetailTran", "showContractRequirements", "contentIFrame");
	}

	function showContractHistory() {

		currentPage = "contractHistory";

		window.frames["contentIFrame"].prepareToSendTransactionAction("ContractDetailTran", "showContractHistory", "contentIFrame");
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;
		f.target = target;

		f.submit();
	}

</script>

<body bgColor="#99BBBB" onLoad="init();MM_preloadImages('/PORTAL/contract/images/investmentsTab.gif','/PORTAL/contract/images/clientsTab.gif','/PORTAL/contract/images/agentsTab.gif','/PORTAL/contract/images/investmentsTag.gif','/PORTAL/contract/images/clientsTag.gif','/PORTAL/contract/images/agentsTag.gif','/PORTAL/contract/images/contractMainTag.gif','/PORTAL/contract/images/contractMainTab.gif','/PORTAL/contract/images/historyTab.gif')">
<form name="tabContentForm" method="post" action="/PORTAL/servlet/RequestManager">
<input type="hidden" name="page" value="">
<table style="position:relative; width:100%; top:0; left:0" cellspacing="0" cellpadding="0" border="0">

	<tr valign="bottom">

      <td> <img src="/PORTAL/contract/images/contractMainTab.gif"  style="position:relative; top:5; left:0; cursor: pointer;" name="contractMain" onClick="showContractMain()" width="60" height="26">
        <img src="/PORTAL/contract/images/clientsTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showContractNonPayeeOrPayee()" name="clients" width="60" height="26">
        <img src="/PORTAL/contract/images/agentsTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showContractAgents()" name="agent" width="60" height="26">
        <%
            if (optionCode.equalsIgnoreCase("Traditional"))
            {
        %>
        <img src="/PORTAL/quote/images/loanTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showContractLoan()" name="loan">
        <%
            }
            else
            {
        %>
        <img src="/PORTAL/contract/images/investmentsTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showContractInvestments()" name="investment" width="60" height="26">
        <%
            }
        %>
        <img src="/PORTAL/contract/images/requirementsTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showContractRequirements()" name="requirements" width="60" height="26">
        <img src="/PORTAL/contract/images/historyTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showContractHistory()" name="history" width="60" height="26">
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
			src="<%= iFrameSrc %>"
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