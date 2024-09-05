<%@ page import="edit.portal.common.session.UserSession"%><!-- ****** JAVA CODE ***** //-->
<%
    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/help.js"></script>

<!-- ****** JAVA Scriptcode ***** //-->

<script language="JavaScript">
<!--
    var editMode = "";

	var f = null;

    var elementIsLocked = true;
    var username = null;
    var elementPK = 0;

	function getMode() {

		f.mode.value;
	}

	function init() {

		f = document.navigationform;

        updateLockState(<%= userSession.getScriptIsLocked() %>, "<%= userSession.getUsername() %>", "<%= userSession.getScriptPK() %>");
	}

	function getScriptCallchain(){

        var width   = 1.00 * screen.width;
        var height  = 1.00 * screen.height;

        openBrowserDialog("","callChainWindow","left=0,top=0,scrollbars=yes,resizable=yes,width=" + width + ",height=" + height,"ParamTran","callScriptChain");
	}

	function openBrowserDialog(theURL,winName,features,transaction,action) {

	  window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, winName);
    }

	function sendTransactionAction(transaction, action, target) {

		document.navigationform.transaction.value = transaction;
		document.navigationform.action.value = action;
		document.navigationform.target = target;

		document.navigationform.submit();
	}

	function closeDialog(dialog) {

		dialog.close();
	}

    function showNavigator()
    {


    }

    function showToolkitSelection()
    {
        sendTransactionAction("ProductStructureTran", "showToolkitSelection", "main");
    }

    function jumpTo(jumpToTarget)
    {
        if (elementIsLocked)
        {
            var width   = 0.35 * screen.width;
            var height  = 0.20 * screen.height;

            f.jumpToTarget.value = jumpToTarget;

            openDialog("jumpToDialog","top=0,left=0,resizable=no", width, height);
            sendTransactionAction("ScriptTran", "showJumpToDialog", "jumpToDialog");
        }
        else
        {
            var action = null;

            if (jumpToTarget == "Main")
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

	function showDefaultDebugBaseParametersScreen() {

		f.mode.value = "Debug";

		sendTransactionAction('ParamTran','showDefaultDebugBaseParametersScreen', 'main')
	}

	function showDefaultBaseParametersScreen() {

		f.mode.value = "Base Parameters";

		sendTransactionAction('ParamTran','showDefaultBaseParametersScreen', 'main')
	}

	function deleteTable(selectionId, selection, mode){

		f.mode.value = mode;
		f.tableName.value = selection;
		f.tableId.value = selectionId;

		sendTransactionAction("TableTran", "deleteTable", "main");
	}

	function showRuleSummary(companyStructureId)  {

	    if ((companyStructureId != null) &&
	        (companyStructureId != "")) {

	        document.navigationform.transaction.value="ProductRuleStructureTran";
	        document.navigationform.action.value="showRuleSummary";
	        document.navigationform.companyStructureId.value=companyStructureId;

	        document.navigationform.target="main";
	        document.navigationform.submit();
    	}
	}

	function openKeySelectionDialog() {

		openBrowserDialog("", "keySelectionDialog", "top=10,left=10,scrollbars=no,width=350,height=200", "ParamTran","showKeySelectionDialog");
	}

	function exportAsXML() {

		top.frames["main"].exportAsXML();
	}

    function showImage(gifName)
    {
        var img = window.event.srcElement;

        img.src = "/PORTAL/engine/images/" + gifName;
    }

    function updateLockState(theElementIsLocked, theUsername, theElementPK)
    {
        elementIsLocked = theElementIsLocked;
        username = theUsername;
        elementPK = theElementPK;

        var tdLockMessage = document.getElementById("lockMessage");

        if (theElementIsLocked == true){

            tdLockMessage.innerHTML = "<font face='' size='2' color='yellow'><b>" + "Locked by '" + username + "'</b></font>";
        }
        else if (theElementIsLocked == false){

            tdLockMessage.innerHTML = "&nbsp;";
        }
    }
//-->
</script>
<style type="text/css">

</style>
</head>

<!-- ****** HTML CODE ***** //-->

<body  bgColor="#99BBBB" onLoad="init()">

<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

<form name="navigationform" method="post" action="/PORTAL/servlet/RequestManager">
<table width="100%" border="0" bgcolor="#30548E">
  <tr valign="middle" align="center">
      <td colspan="3" bgcolor="#30548E">
        &nbsp;
      </td>
      <td><font face="Arial, Helvetica, sans-serif"><b><i><font color="#FFFFFF" size="2">EDIT<font size="1">SOLUTIONS
        - <font size="2">PRASE</font></font></font></i></b></font></td>
      <td colspan="3" bgcolor="#30548E" id="lockMessage" align="right">
        &nbsp;
      </td>
  </tr>
</table>
<table width="100%" border="0" align="center" bgcolor="#30548E" height="21">
    <tr valign="middle" >

        <td align="left">

            <img src="/PORTAL/engine/images/sitemapblue_b1.gif" onMouseOver="this.src='/PORTAL/engine/images/sitemapblue_b1_over.gif'" onMouseOut="this.src='/PORTAL/engine/images/sitemapblue_b1.gif'" onClick="showToolkitSelection()">

        </td>

        <td align="right">
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
</table>
<table width="100%" border="0">
  <tr align="center" valign="middle">
    <td align="left">
        <img src="/PORTAL/engine/images/btnKey.gif" width="77" height="17" alt="Select Key" name="Image1" onMouseOver="showImage('btnKeyOver.gif')" onMouseOut="showImage('btnKey.gif')" border="0" align="bottom" onClick="openKeySelectionDialog()">
        <img src="/PORTAL/engine/images/btnCallChain.gif" width="77" height="17" alt="View Script Call Chain" name="Image10" onClick="getScriptCallchain()" onMouseOut="showImage('btnCallChain.gif')" onMouseOver="showImage('btnCallChainOver.gif')" align="bottom">
  </td>
  </tr>
</table>
    <!-- ****** Hidden Values ***** //-->
  <p>
    <input type="hidden" name="transaction">
    <input type="hidden" name="action">
    <input type="hidden" name="scriptName">
    <input type="hidden" name="scriptId">
    <input type="hidden" name="script">
    <input type="hidden" name="oldScriptName">
    <input type="hidden" name="tableName">
    <input type="hidden" name="tableId">
    <input type="hidden" name="newTableName">
    <input type="hidden" name="accessType">
    <input type="hidden" name="companyStructureId">
    <input type="hidden" name="ruleStructure">
    <input type="hidden" name="mode">
    <input type="hidden" name="jumpToTarget">
  </p>
</form>
</span>
</body>
</html>

