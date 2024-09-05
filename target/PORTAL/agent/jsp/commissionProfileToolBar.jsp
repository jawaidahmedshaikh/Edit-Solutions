<%@ page import="edit.common.vo.*,
                 edit.common.vo.user.*,
                 edit.portal.common.session.UserSession"%>
 <!-- ****** JAVA CODE ***** //-->
<%

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>
<html>
<head>
<title>Untitled Document</title>
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

function init() {

	f = document.commissionProfileToolBar;

    updateLockState(<%= userSession.getAgentIsLocked() %>, "<%= userSession.getUsername() %>", "<%= userSession.getAgentPK() %>");
}

function jumpTo(jumpToTarget){

    if (elementIsLocked){

        var width   = 0.30 * screen.width;
        var height  = 0.20 * screen.height;

        f.jumpToTarget.value = jumpToTarget;

        openDialog("jumpToDialog","resizable=yes", width, height);
        sendTransactionAction("QuoteDetailTran", "showJumpToDialog", "jumpToDialog");
    }
    else{

        var action = null;

        if (jumpToTarget == "Client"){

            action="showIndividuals";
        }
        else if (jumpToTarget == "Roles"){

            action="showRoleAdmin";
        }
        else if (jumpToTarget == "AgentDetail"){

            action = "showAgentDetail";
        }
        else if (jumpToTarget == "AgentHierarchy"){

            action = "showAgentHierarchy";
        }
        else if (jumpToTarget == "AgentBonusProgram"){

            action = "showAgentBonusProgram";
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

    // Disabled Buttons

    document.getElementById("btnSearch").src = "/PORTAL/common/images/btnSearchDis.gif";
    document.getElementById("btnSearch").disabled = true;
}

function setButtonStateForUnlockedMode(){

    // Enabled Buttons

    document.getElementById("btnSearch").src = "/PORTAL/common/images/btnSearch.gif";
    document.getElementById("btnSearch").disabled = false;
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

function addNewAgent(){

    var width = 0.99 * screen.width;
    var height = 0.87 * screen.height;

	openDialog("newAgentDialog", "top=0,left=0,resizable=yes", width, height);

	sendTransactionAction("AgentDetailTran", "addNewAgent", "newAgentDialog");
}

function saveAgentDetails()  {

	var contentIFrame = top.frames["main"].window.frames["contentIFrame"];

	contentIFrame.sendTransactionAction("AgentDetailTran", "saveAgentDetails", "contentIFrame");
}

function showDeleteAgentConfirmationDialog() {

	var contentIFrame = top.frames["main"].window.frames["contentIFrame"];

	contentIFrame.sendTransactionAction("AgentDetailTran", "showDeleteAgentConfirmationDialog", "contentIFrame");
}

function showAgentDetail() {

	top.frames ["main"].showAgentDetail();
}

function showSearchDialog() {

    var width = 0.99 * screen.width;
    var height = 0.87 * screen.height;

	openDialog("searchDialog", "top=0,left=0,resizable=no", width, height);

	sendTransactionAction("SearchTran", "showAgentSearchDialog", "searchDialog");
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

<form name="commissionProfileToolBar" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" border="0" cellspacing="0" cellpadding="3">
    <tr>
      <td colspan="3" bgcolor="#30548E">
        &nbsp;
      </td>
      <td colspan="4" align="center" bgcolor="#30548E">
        <b><i>
            <font face="Arial, Helvetica, sans-serif">
               <font color="#FFFFFF" size="2">EDIT<font size="1">SOLUTIONS- </FONT>Commission Profile</font>
           </FONT>
        </i></b>
        </td>
      <td colspan="3" bgcolor="#30548E" id="lockMessage" align="right">
        &nbsp;
      </td>
    </tr>


    <tr bgcolor="#30548E">
      <td colspan="6">
        <font color="#FFFFFF" size="2">
            [<u>
                <span onClick="jumpTo('AgentDetail')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Agent Detail</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('AgentHierarchy')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Agent Hierarchy</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('AgentBonusProgram')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Agent Bonus Program</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('Client')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Client</span>
            </u>] &nbsp;
            [<u>
                <span onClick="jumpTo('Roles')" onmouseover="this.style.fontWeight='bold'" onmouseout="this.style.fontWeight='normal'">Roles</span>
            </u>]
        </font>
      </td>
      <td colspan="4" align="right">
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
        &nbsp;
      </td>
      <td colspan="4" nowrap valign="bottom" align="left" width="50">
        <img src="../../common/images/btnSearch.gif" id="btnSearch" width="77" height="17" name="search" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('search','','../../common/images/btnSearchOver.gif',1)" onClick="showSearchDialog()">
      </td>
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
