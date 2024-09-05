<%@ page import="edit.common.vo.*,
                 edit.common.vo.user.*,
                 edit.portal.common.session.UserSession"%>
<%@ page import="edit.services.config.*" %>
<!-- ****** JAVA CODE ***** //-->
<%

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

//******* JAVASCRIPT ******

var tmpTransaction = "";
var tmpAction = "";
var f = null;

var elementIsLocked = true;
var username = null;
var elementPK = 0;

function init() {

	f = document.clientToolBar;

    updateLockState(<%= userSession.getClientDetailIsLocked() %>, "<%= userSession.getUsername() %>", "<%= userSession.getClientDetailPK() %>");
}

function jumpTo(jumpToTarget){

    if (elementIsLocked){

        var width   = 0.35 * screen.width;
        var height  = 0.20 * screen.height;

        f.jumpToTarget.value = jumpToTarget;

        openDialog("jumpToDialog","top=0,left=0,resizable=no", width, height);
        sendTransactionAction("ClientDetailTran", "showJumpToDialog", "jumpToDialog");
    }
    else{

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
        else if (jumpToTarget == "Case"){

            action="showCase";
        }
        else if (jumpToTarget == "Contract"){

            action = "showContract";
        }
        else if (jumpToTarget == "Agent"){

            action = "showAgentDetail";
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

<%--    document.getElementById("btnRoles").src = "/PORTAL/client/images/btnRoles.gif";--%>
<%--    document.getElementById("btnRoles").disabled = false;--%>

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

    document.getElementById("btnSave").src = "/PORTAL/common/images/btnSaveDis.gif";
    document.getElementById("btnSave").disabled = true;

    document.getElementById("btnCancel").src = "/PORTAL/common/images/btnCancelDis.gif";
    document.getElementById("btnCancel").disabled = true;

    // If there is no Segment currently displayed
    if (elementPK == 0) {
        document.getElementById("btnChange").src = "/PORTAL/common/images/btnChangeDis.gif";
        document.getElementById("btnChange").disabled = true;

        document.getElementById("btnDelete").src = "/PORTAL/common/images/btnDeleteDis.gif";
        document.getElementById("btnDelete").disabled = true;
    }
}

function showCancelClientConfirmationDialog() {

    var width   = 0.30 * screen.width;
    var height  = 0.20 * screen.height;

    openDialog("cancelClientDialog","resizable=yes", width, height);
    sendTransactionAction("ClientDetailTran", "showCancelClientConfirmationDialog", "cancelClientDialog");
}

function lockClient(){

	var contentIFrame = top.frames["main"].window.frames["contentIFrame"];

	contentIFrame.sendTransactionAction("ClientDetailTran", "lockClient", "contentIFrame");
}

function addNewClient(){

	sendTransactionAction("ClientDetailTran", "addNewClient", "contentIFrame");
}

function saveClientDetails()  {

	var contentIFrame = top.frames["main"].window.frames["contentIFrame"];

    document.getElementById("btnSave").src = "/PORTAL/common/images/btnSaveDis.gif";
    document.getElementById("btnSave").disabled = true;

    document.getElementById("btnCancel").src = "/PORTAL/common/images/btnCancelDis.gif";
    document.getElementById("btnCancel").disabled = true;

    // Set the value of recordPRASEEvents in the clientDetail.jsp so the tran can get it
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

    contentIFrame.sendTransactionAction("ClientDetailTran", "saveClientDetails", "contentIFrame");
}

function showDeleteClientConfirmationDialog()
{
    var width   = 0.40 * screen.width;
    var height  = 0.20 * screen.height;

    openDialog("deleteClientDialog","resizable=yes", width, height);
	sendTransactionAction("ClientDetailTran", "showDeleteClientConfirmationDialog", "deleteClientDialog");
}

function showClientDetail() {

	top.frames ["main"].showClientDetails();
}

function showSearchDialog() {

    var width = 0.99 * screen.width;
    var height = 0.87 * screen.height;

	openDialog("searchDialog", "top=0,left=0,resizable=yes", width, height);

	sendTransactionAction("SearchTran", "showSearchDialog", "searchDialog");
}

/*
 * Brings up the flex PRASE Events application
 */
function showPRASEEventsDialog()
{
    window.open("/PORTAL/flex/SPResultsApplication.jsp", "_blank", "toolbar=no, resizable=yes");
}

function showClientRoles() {

    var width = 0.90 * screen.width;
    var height = 0.90 * screen.height;

	openDialog("rolesDialog", "left=0,top=0,resizable=yes", width, height);

	sendTransactionAction("ClientDetailTran", "showClientRoles", "rolesDialog");
}

function showAgentDetail() {

	top.frames ["main"].showAgentDetail();
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


function showhideLayers() {
	  var i,p,v,obj,args=showhideLayers.arguments;
	  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
	    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v; }
	    obj.visibility=v; }
}
//-->
</script>

</head>

<!-- ****** HTML CODE ***** //-->
<body  bgColor="#99BBBB"  onLoad="init()">
<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

<form name="clientToolBar" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" border="0" cellspacing="0" cellpadding="3">
    <tr>
      <td colspan="3" bgcolor="#30548E">
        &nbsp;
      </td>
      <td colspan="4" align="center" bgcolor="#30548E">
        <b><i>
            <font face="Arial, Helvetica, sans-serif">
               <font color="#FFFFFF" size="2">EDIT<font size="1">SOLUTIONS- </FONT>Client</font>
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
                <span onClick="jumpTo('QuoteAndCommit')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">New Business</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('Client')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Client</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('Roles')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Roles</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('Case')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Case</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('Contract')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Contract</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('Agent')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Agt Detail</span>
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
      <td valign="bottom" colspan="5" nowrap width="50%">
        <img src="../../common/images/btnAdd.gif" id="btnAdd" width="77" height="17" name="add" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('add','','../../common/images/btnAddOver.gif',1)" onClick="addNewClient()">
        <img src="../../common/images/btnChange.gif" id="btnChange" width="77" height="17" name="lock" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('lock','','../../common/images/btnChangeOver.gif',1)" onClick="lockClient()">
        <img src="../../common/images/btnSave.gif" id="btnSave" width="77" height="17" name="save" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('save','','../../common/images/btnSaveOver.gif',1)" onClick="saveClientDetails()">
        <img src="../../common/images/btnCancel.gif" id="btnCancel" width="77" height="17" name="cancel" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('cancel','','../../common/images/btnCancelOver.gif',1)" onClick="showCancelClientConfirmationDialog()">
        <img src="../../common/images/btnDelete.gif" id="btnDelete" width="77" height="17" name="delete" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('delete','','../../common/images/btnDeleteOver.gif',1)" onClick="showDeleteClientConfirmationDialog()">
      </td>
      <td colspan="4" nowrap valign="bottom" align="left" width="25%">
        <img src="../../common/images/btnSearch.gif" id="btnSearch" width="77" height="17" name="search" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('search','','../../common/images/btnSearchOver.gif',1)" onClick="showSearchDialog()">
      </td>
      <%
         if (allowPRASERecording.equals("Y"))
         {
      %>
            <td nowrap valign="bottom" width="25%">
                <font face="Arial, Helvetica, sans-serif" size="2">
                    PRASE Events: &nbsp;
                    <input type="checkbox" name="recordPRASEEventsCheckBox">Record &nbsp;
                    <img src="../../common/images/btnShow.gif" id="btnPRASEEvents" width="77" height="17" name="search" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('search','','../../common/images/btnShowOver.gif',1)" onClick="showPRASEEventsDialog()">
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
