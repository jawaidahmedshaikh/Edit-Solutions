<%@ page import="edit.common.vo.*,
                 edit.common.vo.user.*,
                 edit.portal.common.session.UserSession,
                 fission.beans.*"%>
<%@ page import="edit.services.config.*" %>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

 <!-- ****** JAVA CODE ***** //-->
<%
	String status  = (String)session.getAttribute("status");

    UserSession userSession = (UserSession) session.getAttribute("userSession");

    String allowPRASERecording = ServicesConfig.getAllowPRASERecording();

%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/help.js"></script>

<script language="JavaScript">

// ******* JAVASCRIPT ******

var tmpTransaction = "";
var tmpAction = "";
var f = null;

var elementIsLocked = <%= userSession.getSegmentIsLocked() %>;
var editableContractStatus = true;
var username = null;
var elementPK = 0;
var status = "<%= status %>";

function init() {

	f = document.quoteToolBarForm;
	
	editableContractStatus = "<%= userSession.getEditableSegmentStatus() %>";

    updateLockState(<%= userSession.getSegmentIsLocked() %>, "<%= userSession.getUsername() %>", "<%= userSession.getSegmentPK() %>");
}

function jumpTo(jumpToTarget){

    if (elementIsLocked){

        var width   = 0.35 * screen.width;
        var height  = 0.20 * screen.height;

        f.jumpToTarget.value = jumpToTarget;

        openDialog("jumpToDialog", "top=0, left=0, resizable=yes", width, height);

        sendTransactionAction("QuoteDetailTran", "showJumpToDialog", "jumpToDialog");
    }
    else{

        var action = null;

        if (jumpToTarget == "QuoteAndCommit"){

            action = "showQuote";
        }
        else if (jumpToTarget == "Client"){

            action="showIndividuals";
        }
        else if (jumpToTarget == "Case"){

            action="showCase";
        }
        else if (jumpToTarget == "Contract"){

            action = "showContract";
        }
        else if (jumpToTarget == "PRASE"){

            action = "showProductProfessionals";
        }
        else if (jumpToTarget == "Main")
        {
            action = "goToMain";
        }
        else if (jumpToTarget == "Logout")
        {
            action = "doLogOut";
        }

        sendTransactionAction("PortalLoginTran",action, "_top");
    }
}

function updateLockState(theElementIsLocked, theUsername, theElementPK){

    elementIsLocked = theElementIsLocked;
    username = theUsername;
    elementPK = theElementPK;

    var tdLockMessage = document.getElementById("lockMessage");

    if (theElementIsLocked == true){

        tdLockMessage.innerHTML = "<font face='' size='2' color='yellow'><b>" + "Locked by '" + username + "'</b></font>";

        setButtonStateForLockedMode();
    }
    else if (theElementIsLocked == false){

        tdLockMessage.innerHTML = "&nbsp;";

        setButtonStateForUnlockedMode();
    }
}

function setButtonStateForLockedMode(){

    // Enabled Buttons
    document.getElementById("btnCancel").src = "/PORTAL/common/images/btnCancel.gif";
    document.getElementById("btnCancel").disabled = false;

    document.getElementById("btnSave").src = "/PORTAL/common/images/btnSave.gif";
    document.getElementById("btnSave").disabled = false;

    document.getElementById("btnAnalyze").src = "/PORTAL/quote/images/btnAnalyze.gif";
    document.getElementById("btnAnalyze").disabled = false;

    document.getElementById("btnReversals").src = "/PORTAL/contract/images/btnReversals.gif";
    document.getElementById("btnReversals").disabled = false;
    
    // Disabled Buttons
    document.getElementById("btnAdd").src = "/PORTAL/common/images/btnAddDis.gif";
    document.getElementById("btnAdd").disabled = true;

    document.getElementById("btnDelete").src = "/PORTAL/common/images/btnDeleteDis.gif";
    document.getElementById("btnDelete").disabled = true;

    document.getElementById("btnChange").src = "/PORTAL/common/images/btnChangeDis.gif";
    document.getElementById("btnChange").disabled = true;

    document.getElementById("btnChangeKey").src = "/PORTAL/quote/images/btnChangeKeyDis.gif";
    document.getElementById("btnChangeKey").disabled = true;

    document.getElementById("btnSearch").src = "/PORTAL/common/images/btnSearchDis.gif";
    document.getElementById("btnSearch").disabled = true;

    document.getElementById("btnQuote").src = "/PORTAL/quote/images/btnQuoteDis.gif";
    document.getElementById("btnQuote").disabled = true;
    
    document.getElementById("btnTransaction").src = "/PORTAL/contract/images/btnTransactionDis.gif";
	document.getElementById("btnTransaction").disabled = true;
	
	document.getElementById("btnSuspense").src = "/PORTAL/contract/images/btnSuspenseDis.gif";
	document.getElementById("btnSuspense").disabled = true;
    
    if (editableContractStatus == false || editableContractStatus == "false") {
    	document.getElementById("btnProposal").src = "/PORTAL/common/images/btnProposalDis.gif";
    	document.getElementById("btnProposal").disabled = true;
    	
    	document.getElementById("btnCommit").src = "/PORTAL/quote/images/btnCommitDis.gif";
        document.getElementById("btnCommit").disabled = true;
        
        document.getElementById("btnProposal").src = "/PORTAL/common/images/btnProposalDis.gif";
        document.getElementById("btnProposal").disabled = true;
    	
    } else {
    	document.getElementById("btnProposal").src = "/PORTAL/common/images/btnProposal.gif";
    	document.getElementById("btnProposal").disabled = false;
    	
    	document.getElementById("btnCommit").src = "/PORTAL/quote/images/btnCommit.gif";
        document.getElementById("btnCommit").disabled = false;
        
        document.getElementById("btnProposal").src = "/PORTAL/common/images/btnProposal.gif";
        document.getElementById("btnProposal").disabled = false;
    }

}

function setButtonStateForUnlockedMode(){

    // Enabled Buttons
    document.getElementById("btnAdd").src = "/PORTAL/common/images/btnAdd.gif";
    document.getElementById("btnAdd").disabled = false;

    document.getElementById("btnDelete").src = "/PORTAL/common/images/btnDelete.gif";
    document.getElementById("btnDelete").disabled = false;

    document.getElementById("btnChange").src = "/PORTAL/common/images/btnChange.gif";
    document.getElementById("btnChange").disabled = false;
    
    document.getElementById("btnSuspense").src = "/PORTAL/contract/images/btnSuspense.gif";
	document.getElementById("btnSuspense").disabled = false;

    document.getElementById("btnSearch").src = "/PORTAL/common/images/btnSearch.gif";
    document.getElementById("btnSearch").disabled = false;

    // Disabled Buttons
    document.getElementById("btnSave").src = "/PORTAL/common/images/btnSaveDis.gif";
    document.getElementById("btnSave").disabled = true;

    document.getElementById("btnCancel").src = "/PORTAL/common/images/btnCancelDis.gif";
    document.getElementById("btnCancel").disabled = true;

    document.getElementById("btnProposal").src = "/PORTAL/common/images/btnProposalDis.gif";
    document.getElementById("btnProposal").disabled = true;

    document.getElementById("btnAnalyze").src = "/PORTAL/quote/images/btnAnalyzeDis.gif";
    document.getElementById("btnAnalyze").disabled = true;

    document.getElementById("btnQuote").src = "/PORTAL/quote/images/btnQuoteDis.gif";
    document.getElementById("btnQuote").disabled = true;

    document.getElementById("btnCommit").src = "/PORTAL/quote/images/btnCommitDis.gif";
    document.getElementById("btnCommit").disabled = true;

    document.getElementById("btnReversals").src = "/PORTAL/contract/images/btnReversalsDis.gif";
    document.getElementById("btnReversals").disabled = true;

    if (editableContractStatus == false || editableContractStatus == "false") {
    	
   	    document.getElementById("btnChangeKey").src = "/PORTAL/quote/images/btnChangeKeyDis.gif";
   	    document.getElementById("btnChangeKey").disabled = true;

   	    document.getElementById("btnTransaction").src = "/PORTAL/contract/images/btnTransactionDis.gif";
   	    document.getElementById("btnTransaction").disabled = true;
    	
    } else {
    	
   	    document.getElementById("btnChangeKey").src = "/PORTAL/quote/images/btnChangeKey.gif";
   	    document.getElementById("btnChangeKey").disabled = false;

   	    document.getElementById("btnTransaction").src = "/PORTAL/contract/images/btnTransaction.gif";
   	    document.getElementById("btnTransaction").disabled = false;
    }
    
    // If there is no Segment currently displayed
    if (elementPK == 0) {
        document.getElementById("btnChange").src = "/PORTAL/common/images/btnChangeDis.gif";
        document.getElementById("btnChange").disabled = true;

        document.getElementById("btnChangeKey").src = "/PORTAL/quote/images/btnChangeKeyDis.gif";
        document.getElementById("btnChangeKey").disabled = true;

        document.getElementById("btnDelete").src = "/PORTAL/common/images/btnDeleteDis.gif";
        document.getElementById("btnDelete").disabled = true;

        document.getElementById("btnTransaction").src = "/PORTAL/quote/images/btnTransactionOff.gif";
        document.getElementById("btnTransaction").disabled = true;
    }
}

function lockQuote(){

	sendTransactionAction("QuoteDetailTran", "lockQuote", "contentIFrame");
}

function showSearchDialog() {

    var width = 0.75 * screen.width;

    var height = 0.75 * screen.height;

    openDialog("searchDialog", "top=0, left=0, resizable=yes", width, height);

	sendTransactionAction("SearchTran", "showSearchDialog", "searchDialog");
}

function saveQuoteDetail() {

	var contentIFrame = top.frames["main"].window.frames["contentIFrame"];

    var enteredOption = contentIFrame.checkForRequiredFields();
    if (enteredOption == true)
    {
        // Set the value of recordPRASEEvents in the main jsp so the tran can get it
        <%
            if (allowPRASERecording.equals("Y"))  // if it is N, there is no checkbox
            {
        %>
                if (f.recordPRASEEventsCheckBox.checked == true)
                {
                    contentIFrame.f.recordPRASEEvents.value = "true";
                }
        <%
            }
        %>

        var width = 1;
        var height = 1;

        openDialog("contractNumberDialog", "top=0, left=0, resizable=yes", width, height);

	    contentIFrame.prepareToSendTransactionAction("QuoteDetailTran", "saveQuoteDetail", "contractNumberDialog");
    }
}

function showProposal()
{
	var contentIFrame = top.frames["main"].window.frames["contentIFrame"];

    var enteredOption = contentIFrame.checkForRequiredFields();
    if (enteredOption == true)
    {
        var height  = 0.85 * screen.height;
        var width   = 0.90 * screen.width;

        openDialog("", "proposalDialog", "left=0,top=0,resizable=yes,width=" + width + ",height=" + height);

	    contentIFrame.prepareToSendTransactionAction("QuoteDetailTran", "showProposalDialog", "proposalDialog");
    }
}

function analyzeQuote() {

	var contentIFrame = top.frames["main"].window.frames["contentIFrame"];

    var enteredOption = contentIFrame.checkForRequiredFields();

    if (enteredOption == true)
    {
	    contentIFrame.prepareToSendTransactionAction("QuoteDetailTran", "analyzeQuote", "contentIFrame");
    }
}

function getQuote() {

	var contentIFrame = top.frames["main"].window.frames["contentIFrame"];

    var enteredOption = contentIFrame.checkForRequiredFields();

    if (enteredOption == true)
    {
    	contentIFrame.prepareToSendTransactionAction("QuoteDetailTran", "getQuote", "contentIFrame");
    }
}

function deleteQuoteDetail() {

    var width   = 0.50 * screen.width;
    var height  = 0.10 * screen.height;

    openDialog("deleteQuoteConfirmationDialog", "top=0, left=0, resizable=yes", width, height);

	var contentIFrame = top.frames["main"].window.frames["contentIFrame"];

	contentIFrame.prepareToSendTransactionAction("QuoteDetailTran", "confirmQuoteDelete", "deleteQuoteConfirmationDialog");
}

function showCancelQuoteConfirmationDialog() {

    var width   = 0.30 * screen.width;
    var height  = 0.20 * screen.height;

    openDialog("cancelQuoteDialog", "top=0, left=0, resizable=yes", width, height);

    sendTransactionAction("QuoteDetailTran", "showCancelQuoteConfirmationDialog", "cancelQuoteDialog");
}

function addNewQuote() {

    var width   = 0.55 * screen.width;
    var height  = 0.30 * screen.height;

    openDialog("selectCoverageDialog", "top=0, left=0, resizable=yes", width, height);

    sendTransactionAction("QuoteDetailTran", "showSelectCoverageDialog", "selectCoverageDialog");
}

function changeKeyOnQuote() {

    var width   = 0.50 * screen.width;
    var height  = 0.30 * screen.height;

    openDialog("changeCoverageDialog", "top=0, left=0, resizable=yes", width, height);

    sendTransactionAction("QuoteDetailTran", "showChangeCoverageDialog", "changeCoverageDialog");
}

function commitContractDetail() {

	var contentIFrame = top.frames["main"].window.frames["contentIFrame"];

    var enteredOption = contentIFrame.checkForRequiredFields();
    if (enteredOption == true)
    {
        // Set the value of recordPRASEEvents in the main jsp so the tran can get it
         <%
            if (allowPRASERecording.equals("Y"))  // if it is N, there is no checkbox
            {
        %>
                if (f.recordPRASEEventsCheckBox.checked == true)
                {
                    contentIFrame.f.recordPRASEEvents.value = "true";
                }
        <%
            }
        %>

        var width = .6 * screen.width;
        var height = .5 * screen.height;

        openDialog("contractNumberDialog", "top=0, left=0, resizable=yes", width, height);

    	contentIFrame.prepareToSendTransactionAction("QuoteDetailTran", "commitContractDetail", "contractNumberDialog");
    }
}

function loadTransactions() {

    var height = .80 * screen.height;
    var width  = .97 * screen.width;


    openDialog("transactionDialog","left=0,top=0,scrollbars=yes,resizable=yes", width, height)
    sendTransactionAction("QuoteDetailTran", "showTransactionDefault", "transactionDialog");
}

function showContractSuspense() {

    var height  = 0.90 * screen.height;
    var width   = 0.98 * screen.width;

	openDialog("suspenseDialog", "left=0,top=0,scrollbars=yes,resizable=yes", width, height);

	sendTransactionAction("ContractDetailTran", "showSuspense", "suspenseDialog");
}

function loadBatchReversals() {

	var height = .90 * screen.height;
	var width  = .97 * screen.width;
	
	openDialog("reversalDialog","left=5,top=5,scrollbars=yes,resizable=yes", width, height)
	sendTransactionAction("ContractDetailTran", "showBatchReversalDialog", "reversalDialog");
}

/*
 * Brings up the flex PRASE Events application
 */
function showPRASEEventsDialog()
{
    window.open("/PORTAL/flex/SPResultsApplication.jsp", "_blank", "toolbar=no, resizable=yes");
}

function showhideLayers() {
	  var i,p,v,obj,args=showhideLayers.arguments;
	  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
	    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v; }
	    obj.visibility=v; }
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
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


function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
//-->
</script>
</head>

<!-- ****** HTML CODE ***** //-->
<body  bgColor="#99BBBB"  onLoad="init()">
<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

<form name="quoteToolBarForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" border="0" cellspacing="0" cellpadding="3">
    <tr>
      <td colspan="3" bgcolor="#30548E">
        &nbsp;
      </td>
      <td colspan="4" align="center" bgcolor="#30548E">
        <b><i>
            <font face="Arial, Helvetica, sans-serif">
               <font color="#FFFFFF" size="2">EDIT<font size="1">SOLUTIONS- </FONT>New Business</font>
           </FONT>
        </i></b>
        </td>
      <td colspan="4" bgcolor="#30548E" id="lockMessage" align="right">
        &nbsp;
      </td>
    </tr>


  <tr bgcolor="#30548E">
    <td colspan="6">
        <font color="#FFFFFF" size="2">
            [<u style="cursor: pointer;">
                <span onClick="jumpTo('QuoteAndCommit')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">New Business</span>
            </u>] &nbsp;
            [<u style="cursor: pointer;">
                <span onClick="jumpTo('Client')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Client</span>
            </u>] &nbsp;
            [<u style="cursor: pointer;">
                <span onClick="jumpTo('Case')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Case</span>
            </u>] &nbsp;
            [<u style="cursor: pointer;">
                <span onClick="jumpTo('Contract')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Contract</span>
           </u>] &nbsp;
            [<u style="cursor: pointer;">
                <span onClick="jumpTo('PRASE')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">PRASE</span>
            </u>]
        </font>
    </td>
    <td colspan="6" align="right">
        <font color="#FFFFFF" size="2">
            [<u style="cursor: pointer;">
                <span onClick="jumpTo('Main')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Main</span>
           </u>] &nbsp;
            [<u style="cursor: pointer;">
                <span onClick="getHelp()" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Help</span>
           </u>] &nbsp;
           <%
                if (userSession.userLoggedIn())
                {
           %>
            [<u style="cursor: pointer;">
                <span onClick="jumpTo('Logout')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Logout</span>
            </u>]
            <%
                }
            %>
        </font>
    </td>
  </tr>




    <tr bgcolor="#99BBBB">
      <td valign="bottom" colspan="6" nowrap>
        <img src="../../common/images/btnAdd.gif" id="btnAdd" style="cursor: pointer;" width="70" height="17" name="add" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('add','','../../common/images/btnAddOver.gif',1)" onClick="addNewQuote()">
        <img src="../../common/images/btnChange.gif" id="btnChange" style="cursor: pointer;" width="70" height="17" name="lock" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('lock','','../../common/images/btnChangeOver.gif',1)" onClick="lockQuote()">
        <img src="../../common/images/btnSave.gif" id="btnSave" style="cursor: pointer;" width="70" height="17" name="save" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('save','','../../common/images/btnSaveOver.gif',1)" onClick="saveQuoteDetail()">
        <img src="../../common/images/btnCancel.gif" id="btnCancel" style="cursor: pointer;" width="70" height="17" name="cancel" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('cancel','','../../common/images/btnCancelOver.gif',1)" onClick="showCancelQuoteConfirmationDialog()">
        <img src="../../common/images/btnDelete.gif" id="btnDelete" style="cursor: pointer;" width="70" height="17" name="delete" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('delete','','../../common/images/btnDeleteOver.gif',1)" onClick="deleteQuoteDetail()">
        <img src="../../quote/images/btnChangeKey.gif" id="btnChangeKey" style="cursor: pointer;" width="70" height="17" name="changeKey" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('changeKey','','../../quote/images/btnChangeKeyOver.gif',1)" onClick="changeKeyOnQuote()">
        </td>
      <td nowrap valign="bottom">
          <img src="../../common/images/btnSearch.gif" id="btnSearch" style="cursor: pointer;" width="70" height="17" name="search" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('search','','../../common/images/btnSearchOver.gif',1)" onClick="showSearchDialog()">
      </td>
      <td colspan="5" height="2" align="right" nowrap valign="bottom">
            <img src="../../contract/images/btnReversals.gif" id="btnReversals" style="cursor: pointer;" width="77" height="17" name="reversals" onMouseOut="MM_swapImgRestore()" onClick="loadBatchReversals()" onMouseOver="MM_swapImage('reversals','','../../contract/images/btnReversalsOver.gif',1)">&nbsp;&nbsp;
            <img src="../../common/images/btnProposal.gif" id="btnProposal" style="cursor: pointer;" width="65" height="17" name="proposal" onMouseOut="MM_swapImgRestore()" onClick="showProposal()" onMouseOver="MM_swapImage('proposal','','../../common/images/btnProposalOver.gif',1)">&nbsp;&nbsp;
            <img src="../../quote/images/btnAnalyze.gif" id="btnAnalyze" style="cursor: pointer;" width="65" height="17" name="analyze" onMouseOut="MM_swapImgRestore()" onClick="analyzeQuote()" onMouseOver="MM_swapImage('analyze','','../../quote/images/btnAnalyzeOver.gif',1)">&nbsp;&nbsp;
            <img src="../../quote/images/btnQuote.gif" id="btnQuote" style="cursor: pointer;" width="65" height="17" name="quote" onMouseOut="MM_swapImgRestore()" onClick="getQuote()" onMouseOver="MM_swapImage('quote','','../../quote/images/btnQuoteOver.gif',1)">&nbsp;&nbsp;
            <img src="../../quote/images/btnCommit.gif" id="btnCommit" style="cursor: pointer;" width="68" height="17" name="commit" onMouseOut="MM_swapImgRestore()" onClick="commitContractDetail()" onMouseOver="MM_swapImage('commit','','../../quote/images/btnCommitOver.gif',1)">&nbsp;&nbsp;
            <img src="../../contract/images/btnSuspense.gif" id="btnSuspense" style="cursor: pointer;" width="65" height="17" name="suspense" onClick="showContractSuspense()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('suspense','','../../contract/images/btnSuspenseOver.gif',1)">&nbsp;&nbsp;
            <img src="../../contract/images/btnTransaction.gif" id="btnTransaction" style="cursor: pointer;" width="65" height="17" name="transaction" onMouseOut="MM_swapImgRestore()" onClick="loadTransactions()" onMouseOver="MM_swapImage('transaction','','../../contract/images/btnTransactionOver.gif',1)">
      </td>
    </tr>
    <tr>
        <%
         if (allowPRASERecording.equals("Y"))
         {
      %>
            <td colspan="11" nowrap height="20" align="right">
                <font face="Arial, Helvetica, sans-serif" size="2">
                    PRASE Events: &nbsp;
                    <input type="checkbox" name="recordPRASEEventsCheckBox">Record &nbsp;
                    <img src="../../common/images/btnShow.gif" id="btnPRASEEvents" style="cursor: pointer;" width="77" height="17" name="search" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('search','','../../common/images/btnShowOver.gif',1)" onClick="showPRASEEventsDialog()">
                </font>
            </td>
        <%
          }
        %>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="jumpToTarget" value="">
</form>
</span>
</body>
</html>