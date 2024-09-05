<%@ page import="fission.utility.Util"%>
<%
    String segmentPK = (String) session.getAttribute("searchValue");
    String optionCode = Util.initString((String) session.getAttribute("optionCode"), "");
    session.removeAttribute("searchValue");

    String iFrameSrc = null;

    if (segmentPK == null){

        iFrameSrc = "/PORTAL/servlet/RequestManager?transaction=QuoteDetailTran&action=showQuoteMainDefault";
    }
    else {

        iFrameSrc = "/PORTAL/servlet/RequestManager?transaction=QuoteDetailTran&action=loadQuote&segmentPK=" + segmentPK;
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
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function setActiveTab(tabName) {

    if (option == "Traditional")
    {
        if (tabName == "mainTab")  {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTag.gif',1);MM_swapImage('rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTab.gif',0);
        }
<%--        else if (tabName == "loanTab") {--%>
<%----%>
<%--            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTag.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTab.gif',1);--%>
<%--        }--%>
        else if (tabName == "clientTab") {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTag.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTab.gif',1);
        }
        else if (tabName == "agentTab") {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTag.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTab.gif',1);
        }
        else if (tabName == "riderTab") {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTag.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTab.gif',1);
        }
        else if (tabName == "schedEventsTab") {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTag.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTab.gif',1);
        }
        else if (tabName == "requirementsTab") {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTag.gif','history','','/PORTAL/quote/images/historyTab.gif',1);
        }
        else if (tabName == "historyTab") {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTag.gif',1);
        }
    }
    else
    {
        if (tabName == "mainTab")  {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTag.gif',1);MM_swapImage('rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTab.gif',0);
        }
        else if (tabName == "investmentTab") {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTag.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTab.gif',1);
        }
        else if (tabName == "clientTab") {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTag.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTab.gif',1);
        }
        else if (tabName == "agentTab") {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTag.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTab.gif',1);
        }
        else if (tabName == "riderTab") {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTag.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTab.gif',1);
        }
        else if (tabName == "schedEventsTab") {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTag.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTab.gif',1);
        }
        else if (tabName == "requirementsTab") {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTag.gif','history','','/PORTAL/quote/images/historyTab.gif',1);
        }
        else if (tabName == "historyTab") {

            MM_swapImage('quoteMain','','/PORTAL/quote/images/quoteCommitMainTab.gif','rider','','/PORTAL/quote/images/ridersTab.gif','clients','','/PORTAL/quote/images/clientsTab.gif','agent','','/PORTAL/quote/images/agentsTab.gif','investment','','/PORTAL/quote/images/investmentsTab.gif','schedEvent','','/PORTAL/quote/images/schedEventsTab.gif','requirements','','/PORTAL/quote/images/requirementsTab.gif','history','','/PORTAL/quote/images/historyTag.gif',1);
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

		currentPage = "quoteMain";
 	}

	function requiredFieldsMissing() {

		// check for optionId in the main page

		if (currentPage == "quoteMain")  {

			if (window.frames["contentIFrame"].f.optionId.value == "Please Select")

			alert("Please select an Annuity Option.");

			return true;
		}

		return false;
	}

	function showQuoteMain() {

		currentPage = "quoteMain";

//		if (requiredFieldsMissing()) {
//
//			return;
//		}

		window.frames["contentIFrame"].prepareToSendTransactionAction("QuoteDetailTran", "showQuoteMainContent", "contentIFrame");
	}

	function showQuoteRider() {

        var enteredOption = true;

 //		Removed per WO33991
 //       if (currentPage == "quoteMain")
 //       {
 //           enteredOption = window.frames["contentIFrame"].checkForRequiredFields();
 //       }

		if (enteredOption == true)
        {
    		currentPage = "quoteRider";
    		window.frames["contentIFrame"].prepareToSendTransactionAction("QuoteDetailTran", "showQuoteRiders", "contentIFrame");
        }
	}

	function showQuoteInvestments() {

        var enteredOption = true;

//		Removed per WO33991
//        if (currentPage == "quoteMain")
//       {
//            enteredOption = window.frames["contentIFrame"].checkForRequiredFields();
//        }

		if (enteredOption == true)
        {
    		currentPage = "quoteInvestments";
    		window.frames["contentIFrame"].prepareToSendTransactionAction("QuoteDetailTran", "showQuoteInvestments", "contentIFrame");
        }
	}

	function showQuoteLoan()
    {
        var enteredOption = true;


//		Removed per WO33991        
//       	if (currentPage == "quoteMain")
//        {
//            enteredOption = window.frames["contentIFrame"].checkForRequiredFields();
//        }

		if (enteredOption == true)
        {
    		currentPage = "quoteLoan";
    		window.frames["contentIFrame"].prepareToSendTransactionAction("QuoteDetailTran", "showQuoteLoan", "contentIFrame");
        }
	}

	function showQuoteNonPayeeOrPayee() {

        var enteredOption = true;

//		Removed per WO33991
//        if (currentPage == "quoteMain")
//        {
//            enteredOption = window.frames["contentIFrame"].checkForRequiredFields();
//        }

		if (enteredOption == true)
        {
    		currentPage = "quoteNonPayee";
    		window.frames["contentIFrame"].prepareToSendTransactionAction("QuoteDetailTran", "showQuoteNonPayeeOrPayee", "contentIFrame");
        }
	}

	function showQuoteAgents() {

        var enteredOption = true;

//		Removed per WO33991
//		if (currentPage == "quoteMain")
//        {
//            enteredOption = window.frames["contentIFrame"].checkForRequiredFields();
//        }

		if (enteredOption == true)
        {
    		currentPage = "quoteAgents";
    		window.frames["contentIFrame"].prepareToSendTransactionAction("QuoteDetailTran", "showQuoteAgents", "contentIFrame");
        }
	}

    function showSchedEvents() {

        var enteredOption = true;

//		Removed per WO33991
//       if (currentPage == "quoteMain")
//        {
//            enteredOption = window.frames["contentIFrame"].checkForRequiredFields();
//        }

		if (enteredOption == true)
        {
            currentPage = "quoteSchedEvents";
            window.frames["contentIFrame"].prepareToSendTransactionAction("QuoteDetailTran", "showQuoteSchedEvents", "contentIFrame");
        }
    }

    function showQuoteRequirements() {

        var enteredOption = true;

//		Removed per WO33991
//		if (currentPage == "quoteMain")
//        {
//            enteredOption = window.frames["contentIFrame"].checkForRequiredFields();
//        }

		if (enteredOption == true)
        {
            currentPage = "quoteRequirements";
            window.frames["contentIFrame"].prepareToSendTransactionAction("QuoteDetailTran", "showQuoteRequirements", "contentIFrame");
        }
    }

    function showHistory() {

        var enteredOption = true;

//		Removed per WO33991
//        if (currentPage == "quoteMain")
//        {
//            enteredOption = window.frames["contentIFrame"].checkForRequiredFields();
//        }

		if (enteredOption == true)
        {
            currentPage = "history";
            window.frames["contentIFrame"].prepareToSendTransactionAction("QuoteDetailTran", "showHistory", "contentIFrame");
        }
    }

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;
		f.target = target;

		f.submit();
	}

</script>

<body onLoad="init()"  bgColor="#99BBBB">
<form name="tabContentForm" method="post" action="/PORTAL/servlet/RequestManager">
<input type="hidden" name="page" value="">
<table style="position:relative; width:100%; top:0; left:0" cellspacing="0" cellpadding="0" border="0">

	<tr valign="bottom">

      <td> <img src="/PORTAL/quote/images/quoteCommitMainTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showQuoteMain()" name="quoteMain" width="60" height="26">
        <img src="/PORTAL/quote/images/ridersTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showQuoteRider()" name="rider" width="60" height="26">
        <img src="/PORTAL/quote/images/clientsTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showQuoteNonPayeeOrPayee()" width="60" height="25" name="clients">
        <img src="/PORTAL/quote/images/agentsTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showQuoteAgents()" name="agent" width="60" height="26">
        <%
            if (!optionCode.equalsIgnoreCase("Traditional"))
            {
        %>
        <img src="/PORTAL/quote/images/investmentsTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showQuoteInvestments()" name="investment">
        <%
            }
        %>

        <img src="/PORTAL/quote/images/requirementsTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showQuoteRequirements()" name="requirements">
        <%
            if (!optionCode.equalsIgnoreCase("Traditional"))
            {
        %>
        <img src="/PORTAL/quote/images/schedEventsTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showSchedEvents()" name="schedEvent">
        <%
            }
        %>
        
        <img src="/PORTAL/quote/images/historyTab.gif" style="position:relative; top:5; left:0; cursor: pointer;" onClick="showHistory()" name="history">
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