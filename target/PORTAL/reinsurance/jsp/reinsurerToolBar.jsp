<%@ page import="edit.portal.common.session.UserSession"%><!-- ****** JAVA CODE ***** //-->
<%
    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>
<head>
<title>Reinsurer Details</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/help.js"></script>

<script language="JavaScript">
<!--
<!--******* JAVASCRIPT ******//-->

var tmpTransaction = "";
var tmpAction = "";
var f = null;

var elementIsLocked = true;
var username = null;
var elementPK = 0;

/**
 * Opens the dialog to allow the user to select the client to use as a reinsurer.
 */
function showReinsurerAddDialog()
{
    var width = getScreenWidth();

    var height = getScreenHeight() * 0.50;

    openDialog("reinsuranceAddDialog", null, width, height);

    sendTransactionAction("ReinsuranceTran", "showReinsurerAddDialog", "reinsuranceAddDialog");
}

/**
 * Collect all of the data in "cloudland", and persists it.
 */
function saveReinsurerDetails()
{
	var contentIFrame = top.frames["main"].window.frames["contentIFrame"];

	contentIFrame.sendTransactionAction("ReinsuranceTran", "saveReinsurerDetails", "contentIFrame");

    top.frames["main"].setActiveImage("reinsurer");
}

/**
 * Opens the reinsurerSearchDialog.
 */
function showReinsurerSearchDialog()
{
    var width = getScreenWidth();

    var height = getScreenHeight() * 0.50;

    openDialog("reinsurerSearchDialog", null, width, height);

    sendTransactionAction("ReinsuranceTran", "showReinsurerSearchDialog", "reinsurerSearchDialog");
}

/**
 * Lock this Reinsurer for exlusive edit access.
 */
function lockReinsurer()
{
    sendTransactionAction("ReinsuranceTran", "lockReinsurer", "contentIFrame");

    top.frames["main"].setActiveImage("reinsurer");
}

/**
 * Cancels current edits and unlocks the Reinurer.
 */
function cancelReinsurerEdits()
{
    sendTransactionAction("ReinsuranceTran", "cancelReinsurerEdits", "contentIFrame");

    top.frames["main"].setActiveImage("reinsurer");
}

/**
 * Deletes the active Reinsurer.
 */
function deleteReinsurer()
{
    alert("To Be Implemented");
}

/**
 * Called after the body of the document is loaded for default actions.
 */
function init() {

	f = document.theForm;

    updateLockState(<%= userSession.getReinsurerIsLocked() %>, "<%= userSession.getUsername() %>", "<%= userSession.getReinsurerPK() %>");
}

function jumpTo(jumpToTarget){

        var action = null;

        if (jumpToTarget == "ReinsurerDetails"){

            action = "showReinsurerDetails";
        }
        else if (jumpToTarget == "TreatyRelations"){

            action="showTreatyRelations";
        }
        else if (jumpToTarget == "ContractTreaty"){

            action="showContractTreatyRelations";
        }
        else if (jumpToTarget == "Client"){

            action="showIndividuals";
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

    // Disabled Buttons
    document.getElementById("btnAdd").src = "/PORTAL/common/images/btnAddDis.gif";
    document.getElementById("btnAdd").disabled = true;

    document.getElementById("btnDelete").src = "/PORTAL/common/images/btnDeleteDis.gif";
    document.getElementById("btnDelete").disabled = true;

    document.getElementById("btnChange").src = "/PORTAL/common/images/btnChangeDis.gif";
    document.getElementById("btnChange").disabled = true;

    document.getElementById("btnSearch").src = "/PORTAL/common/images/btnSearchDis.gif";
    document.getElementById("btnSearch").disabled = true;
}

function setButtonStateForUnlockedMode(){

    // Enabled Buttons
    document.getElementById("btnAdd").src = "/PORTAL/common/images/btnAdd.gif";
    document.getElementById("btnAdd").disabled = false;

    document.getElementById("btnDelete").src = "/PORTAL/common/images/btnDelete.gif";
    document.getElementById("btnDelete").disabled = false;

    document.getElementById("btnChange").src = "/PORTAL/common/images/btnChange.gif";
    document.getElementById("btnChange").disabled = false;

    document.getElementById("btnSearch").src = "/PORTAL/common/images/btnSearch.gif";
    document.getElementById("btnSearch").disabled = false;

    // If there is no Segment currently displayed
    if (elementPK == 0) {
        document.getElementById("btnChange").src = "/PORTAL/common/images/btnChangeDis.gif";
        document.getElementById("btnChange").disabled = true;

        document.getElementById("btnDelete").src = "/PORTAL/common/images/btnDeleteDis.gif";
        document.getElementById("btnDelete").disabled = true;

        document.getElementById("btnSave").src = "/PORTAL/common/images/btnSaveDis.gif";
        document.getElementById("btnSave").disabled = true;

        document.getElementById("btnCancel").src = "/PORTAL/common/images/btnCancelDis.gif";
        document.getElementById("btnCancel").disabled = true;
    }
}
//-->
</script>
</head>

<!-- ****** HTML CODE ***** //-->
<body  class="mainTheme" onLoad="init()">
<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td colspan="1" bgcolor="#30548E" width="10%">
        &nbsp;
      </td>
      <td colspan="1" align="left" bgcolor="#30548E" nowrap width="80%">
        <b><i>
            <font face="Arial, Helvetica, sans-serif">
               <font color="#FFFFFF" size="2">EDIT<font size="1">SOLUTIONS- </font>Reinsurance</font>
           </font>
        </i></b>
        </td>
      <td colspan="1" bgcolor="#30548E" id="lockMessage" align="right"  width="10%">
        &nbsp;
      </td>
    </tr>


    <tr bgcolor="#30548E">
      <td colspan="2" NOWRAP>
        <font color="#FFFFFF" size="2" style="font-weight:normal">
            [<u>
                <span onClick="jumpTo('ReinsurerDetails')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Reinsurer Detail</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('TreatyRelations')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Treaty Relations</span>
           </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('ContractTreaty')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Contract Treaty</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('Client')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Client</span>
            </u>] &nbsp;
        </font>
      </td>
      <td colspan="1" align="right" NOWRAP>
        <font color="#FFFFFF" size="2" style="font-weight:normal">
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
      <td valign="bottom" colspan="1" width="50%" NOWRAP><br>
        <img src="../../common/images/btnAdd.gif" id="btnAdd" width="77" height="17" name="add" onClick="showReinsurerAddDialog()" onMouseOver=>
        <img src="../../common/images/btnChange.gif" id="btnChange" width="77" height="17" name="lock" onClick="lockReinsurer()">
        <img src="../../common/images/btnSave.gif" id="btnSave" width="77" height="17" name="save" onClick="saveReinsurerDetails()">
        <img src="../../common/images/btnCancel.gif" id="btnCancel" width="77" height="17" name="cancel" onClick="cancelReinsurerEdits()">
        <img src="../../common/images/btnDelete.gif" id="btnDelete" width="77" height="17" name="delete" onClick="deleteReinsurer()">
      </td>
      <td nowrap valign="bottom" align="left" width="50" NOWRAP><br>
        <img src="../../common/images/btnSearch.gif" id="btnSearch" width="77" height="17" name="search" onClick="showReinsurerSearchDialog()">
      </td>
      <td colspan="1" align="right" valign="bottom" NOWRAP><br>
        &nbsp;
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">

</form>

</span>
</body>
</html>