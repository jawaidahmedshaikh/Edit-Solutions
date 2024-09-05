<!--
 * User: cgleason
 * Date: Dec 13, 2005
 * Time: 3:59:50 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->
 
<%@ page import="edit.portal.common.session.UserSession,
                 fission.utility.*"%>
<!-- ****** JAVA CODE ***** //-->
<%
    UserSession userSession = (UserSession) session.getAttribute("userSession");

%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script language="JavaScript">
<!--
<!--******* JAVASCRIPT ******//-->

var f = null;
var elementIsLocked = true;
var username = null;
var elementPK = 0;

function init()
{
	f = document.theForm;

    updateLockState(<%= userSession.getCaseIsLocked() %>, "<%= userSession.getUsername() %>", "<%= userSession.getCasePK() %>");
}


function jumpTo(jumpToTarget)
{

        var action = null;

        if (jumpToTarget == "QuoteAndCommit"){

            action = "showQuote";
        }
        else if (jumpToTarget == "Client"){

            action="showIndividuals";
        }
        else if (jumpToTarget == "Roles"){

            action="showRoleAdmin";
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
        else if (jumpToTarget == "Case"){

            action="showCase";
        }

        sendTransactionAction("PortalLoginTran",action, "_top");
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

    // Disabled Buttons
    document.getElementById("btnDelete").src = "/PORTAL/common/images/btnDeleteDis.gif";
    document.getElementById("btnDelete").disabled = true;

    document.getElementById("btnChange").src = "/PORTAL/common/images/btnChangeDis.gif";
    document.getElementById("btnChange").disabled = true;

    document.getElementById("btnSearch").src = "/PORTAL/common/images/btnSearchDis.gif";
    document.getElementById("btnSearch").disabled = true;

    document.getElementById("btnAdd").src = "/PORTAL/common/images/btnAddDis.gif";
    document.getElementById("btnAdd").disabled = true;

    document.getElementById("btnSave").src = "/PORTAL/common/images/btnSave.gif";
    document.getElementById("btnSave").disabled = false;
}

function setButtonStateForUnlockedMode(){

    // Enabled Buttons
    document.getElementById("btnDelete").src = "/PORTAL/common/images/btnDelete.gif";
    document.getElementById("btnDelete").disabled = false;

    document.getElementById("btnChange").src = "/PORTAL/common/images/btnChange.gif";
    document.getElementById("btnChange").disabled = false;

    document.getElementById("btnSearch").src = "/PORTAL/common/images/btnSearch.gif";
    document.getElementById("btnSearch").disabled = false;

    document.getElementById("btnAdd").src = "/PORTAL/common/images/btnAdd.gif";
    document.getElementById("btnAdd").disabled = false;

    document.getElementById("btnSave").src = "/PORTAL/common/images/btnSaveDis.gif";
    document.getElementById("btnSave").disabled = true;
}

function showSearchDialog()
{

    //var width = 0.40 * screen.width;
    //var height = 0.60 * screen.height;
    var width = 600;
    var height = 500;

    openDialog("caseSearchDialog", "top=0,left=0,resizable=yes", width,  height);
	sendTransactionAction("CaseDetailTran", "showCaseSearchDialog", "caseSearchDialog");
}

function getHelp()
{

    alert("To be implemented");
}

function showAddCaseDialog()
{
//    var width = 0.99 * screen.width;
    var width = 950;
    var height = 0.70 * screen.height;
    //var height = 0.88 * screen.height;

    openDialog("addContractGroupDialog", "top=0,left=0,resizable=no", width,  height);

    sendTransactionAction("CaseDetailTran", "showAddCaseEntry", "addContractGroupDialog");
}

function lockCurrentTab()
{
    parent.main.sendTransactionAction("CaseDetailTran", "lockCurrentTab", "main");
}

/**
* Performs requirements checking first (inlined in the calling event). Upon passing,
* executes a generic save to save the current page
* by bypassing any backend business rules. The intention
* us to allow the user to benefit from a partial save.
*/

function saveCurrentPage()
{
    parent.main.sendTransactionAction("CaseDetailTran", "saveCurrentPage", "main");
}

function deleteCaseEntry()
{
<%--    alert("Case Delete Currently Unavailable");--%>
    sendTransactionAction("CaseDetailTran", "deleteCaseEntry", "main");
}

function showCaseQuote()
{
    alert("To be implemented");
}

function showCaseChangeDialog()
{
    var width = 0.90 * screen.width;
    var height = 0.60 * screen.height;

    openDialog("caseChangeDialog", "top=0,left=0,resizable=no", width,  height);
    sendTransactionAction("CaseDetailTran", "showCaseChangeDialog", "caseChangeDialog");
}

function showTransactionDialog()
{
    var width = 0.99 * screen.width;
    var height = 0.90 * screen.height;

	openDialog("caseTransactionDialog", "top=0,left=0,resizable=no", width,  height);

	sendTransactionAction("CaseDetailTran", "showCaseTransactionDialog", "caseTransactionDialog");
}

function swapImage(id, state)
{
	var imgElement = document.getElementById(id);
	var imageBase = "/PORTAL/common/images/";


	if(state == "over")
	{
		imgElement.src = imageBase + imgElement.id + "Over.gif";
	}
	else if(state == "out")
	{
		imgElement.src = imageBase + imgElement.id + ".gif";
	}
}

function swapImageContract(id, state)
{
	var imgElement = document.getElementById(id);
	var imageBase = "/PORTAL/contract/images/";

	if(state == "over")
	{
		imgElement.src = imageBase + imgElement.id + "Over.gif";
	}
	else if(state == "out")
	{
		imgElement.src = imageBase + imgElement.id + ".gif";
	}
}
//-->
</script>
</head>

<!-- ****** HTML CODE ***** //-->
<body  bgColor="#99BBBB" onLoad="init()">
<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" border="0" cellspacing="0" cellpadding="3" height="2%">
    <tr>
      <td colspan="3" bgcolor="#30548E">
        &nbsp;
      </td>
      <td colspan="4" align="center" bgcolor="#30548E">
        <b><i>
            <font face="Arial, Helvetica, sans-serif">
               <font color="#FFFFFF" size="2">EDIT<font size="1">SOLUTIONS- </FONT>Case Detail</font>
           </FONT>
        </i></b>
        </td>
      <td colspan="3" bgcolor="#30548E" id="lockMessage" align="right">
        &nbsp;
      </td>
    </tr>


  <tr bgcolor="#30548E">
    <td colspan="5">
        <font color="#FFFFFF" size="2">
            [<u>
                <span onClick="jumpTo('Case')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Case</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('QuoteAndCommit')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">New Business</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('Client')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Client</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('Contract')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Contract</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('PRASE')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">PRASE</span>
            </u>]
        </font>
    </td>
    <td colspan="5" align="right">
        <font color="#FFFFFF" size="2">
            [<u>
                <span onClick="jumpTo('Main')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Main</span>
            </u>] &nbsp;
            [<u>
                <span onClick="getHelp()" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Help</span>
            </u>] &nbsp;
           <%
                if (userSession.userLoggedIn())
                {
           %>
            [<u>
                <span onClick="jumpTo('Logout')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Logout</span>
            </u>]
            <%
                }
            %>
        </font>
    </td>
    </tr>
    <tr bgcolor="#99BBBB">
      <td valign="bottom" colspan="5" nowrap>
        <img src="/PORTAL/common/images/btnAdd.gif" id="btnAdd" width="77" height="17" name="add" onMouseOut="swapImage('btnAdd', 'out')" onMouseOver="swapImage('btnAdd', 'over')" onClick="showAddCaseDialog()">
        <img src="/PORTAL/common/images/btnChange.gif" id="btnChange" width="77" height="17" name="lock" onMouseOut="swapImage('btnChange', 'out')" onMouseOver="swapImage('btnChange', 'over')" onClick="lockCurrentTab()">
<%--        <img src="/PORTAL/common/images/btnSave.gif" id="btnSave" width="77" height="17" name="save" onMouseOut="swapImage('btnSave', 'out')" onMouseOver="swapImage('btnSave', 'over')" onClick="if (validateForm(this.form, 'REQUIRED')){saveCurrentPage()}">--%>
         <img src="/PORTAL/common/images/btnSave.gif" id="btnSave" width="77" height="17" name="save" onMouseOut="swapImage('btnSave', 'out')" onMouseOver="swapImage('btnSave', 'over')" onClick="saveCurrentPage()">
        <img src="/PORTAL/common/images/btnDelete.gif" id="btnDelete" width="77" height="17" name="delete" onMouseOut="swapImage('btnDelete', 'out')" onMouseOver="swapImage('btnDelete', 'over')" onClick="deleteCaseEntry()">
        </td>
      <td nowrap valign="bottom">
            <img src="/PORTAL/common/images/btnSearch.gif" id="btnSearch" width="77" height="17" name="search" onMouseOut="swapImage('btnSearch', 'out')" onMouseOver="swapImage('btnSearch', 'over')" onClick="showSearchDialog()">
      </td>
      <td colspan="4" height="2" align="right" nowrap valign="bottom">
            <img src="/PORTAL/contract/images/btnQuote.gif" id="btnQuote" width="77" height="17" name="quote" onMouseOut="swapImageContract('btnQuote', 'out')" onMouseOver="swapImageContract('btnQuote', 'over')" onClick="showCaseQuote()">
            <!-- made Transaction button inactive because we don't have transactions for case/group yet.  The page that would normally be brought up has the old
                 case/policygroup structure instead of the new ContractGroup tables -->
            <img src="/PORTAL/contract/images/btnTransaction.gif" id="btnTransaction" width="77" height="17" name="transaction"  >
<%--            <img src="/PORTAL/contract/images/btnTransaction.gif" id="btnTransaction" width="77" height="17" name="transaction" onMouseOut="swapImageContract('btnTransaction', 'out')" onMouseOver="swapImageContract('btnTransaction', 'over')" onClick="showTransactionDialog()">--%>
            <img src="/PORTAL/contract/images/btnCaseChange.gif" id="btnCaseChange" width="77" height="17" name="caseChange" onMouseOut="swapImageContract('btnCaseChange', 'out')" onMouseOver="swapImageContract('btnCaseChange', 'over')" onClick="showCaseChangeDialog()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="jumpToTarget" value="">
    <input type="hidden" name="pageName" value="caseMain"> <!-- This add button always refers to CaseMain -->

</form>

</span>
</body>
</html>
