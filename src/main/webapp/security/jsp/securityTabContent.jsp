<%
    String iFrameSrc = "/PORTAL/servlet/RequestManager?transaction=SecurityAdminTran&action=showRolesPage";

%>
<html>
<script language="JavaScript">
<!--
function setActiveTab(tabName)
{
	if (tabName == "rolesTab")
    {
		MM_swapImage('rolesTab','','/PORTAL/security/images/rolesTab.gif','operators','','/PORTAL/security/images/operatorsTab.gif','profile','','/PORTAL/security/images/profileTab.gif','securityLog','','/PORTAL/security/images/securityLogTab.gif',1);
	}
	else if (tabName == "operatorsTab")
    {
		MM_swapImage('rolesTab','','/PORTAL/security/images/rolesTab.gif','operators','','/PORTAL/security/images/operatorsTab.gif','profile','','/PORTAL/security/images/profileTab.gif','securityLog','','/PORTAL/security/images/securityLogTab.gif',1);
	}
	else if (tabName == "profileTab")
    {
		MM_swapImage('rolesTab','','/PORTAL/security/images/rolesTab.gif','operators','','/PORTAL/security/images/operatorsTab.gif','profile','','/PORTAL/security/images/profileTab.gif','securityLog','','/PORTAL/security/images/securityLogTab.gif',1);
	}
	else if (tabName == "securityLogTab")
    {
		MM_swapImage('rolesTab','','/PORTAL/security/images/rolesTab.gif','operators','','/PORTAL/security/images/operatorsTab.gif','profile','','/PORTAL/security/images/profileTab.gif','securityLog','','/PORTAL/security/images/securityLogTab.gif',1);
	}
}

//-->
</script>

<script language="Javascript1.2">

	var f = null;
	var currentPage = "";


    function init()
    {
		f = document.tabContentForm;
		document.all.contentIFrame.style.pixelWidth = .95 * document.all.contentIFrame.document.body.clientWidth;
		document.all.contentIFrame.style.pixelHeight = document.body.clientHeight;

		currentPage = "roles";
 	}

	function requiredFieldsMissing()
    {
		return false;
	}

	function showRoles()
    {
        document.all.tab1.src = "/PORTAL/security/images/tab1On.gif";
        document.all.tab2.src = "/PORTAL/security/images/tab2Off.gif";
        document.all.tab3.src = "/PORTAL/security/images/tab3Off.gif";
        document.all.tab4.src = "/PORTAL/security/images/tab4Off.gif";

		currentPage = "roles";

		window.frames["contentIFrame"].sendTransactionAction("SecurityAdminTran", "showRolesPage", "contentIFrame");
    }

	function showOperators()
    {
        document.all.tab1.src = "/PORTAL/security/images/tab1Off.gif";
        document.all.tab2.src = "/PORTAL/security/images/tab2On.gif";
        document.all.tab3.src = "/PORTAL/security/images/tab3Off.gif";
        document.all.tab4.src = "/PORTAL/security/images/tab4Off.gif";

		currentPage = "operators";

		window.frames["contentIFrame"].sendTransactionAction("SecurityAdminTran", "showOperatorsPage", "contentIFrame");
	}

	function showProfile()
    {
        document.all.tab1.src = "/PORTAL/security/images/tab1Off.gif";
        document.all.tab2.src = "/PORTAL/security/images/tab2Off.gif";
        document.all.tab3.src = "/PORTAL/security/images/tab3On.gif";
        document.all.tab4.src = "/PORTAL/security/images/tab4Off.gif";

		currentPage = "profile";

		window.frames["contentIFrame"].sendTransactionAction("SecurityAdminTran", "showProfilePage", "contentIFrame");
	}

	function showSecurityLog()
    {
        document.all.tab1.src = "/PORTAL/security/images/tab1Off.gif";
        document.all.tab2.src = "/PORTAL/security/images/tab2Off.gif";
        document.all.tab3.src = "/PORTAL/security/images/tab3Off.gif";
        document.all.tab4.src = "/PORTAL/security/images/tab4On.gif";

		currentPage = "securityLog";

		window.frames["contentIFrame"].sendTransactionAction("SecurityAdminTran", "showSecurityLogPage", "contentIFrame");
	}

	function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value=transaction;
		f.action.value=action;
		f.target = target;

		f.submit();
	}

</script>

<body bgColor="#99BBBB" onLoad="init()">
<form name="tabContentForm" method="post" action="/PORTAL/servlet/RequestManager">

<table width="100%" style="position:relative; top:0; left:0" cellspacing="0" cellpadding="0" border="0">
  <tr valign="bottom">
    <td>
      <img id="tab1" src="/PORTAL/security/images/tab1On.gif"  style="position:relative; top:3; left:0" onClick="showRoles()" name="roles" width="60" height="26">
      <img id="tab2" src="/PORTAL/security/images/tab2Off.gif" style="position:relative; top:3; left:0" onClick="showOperators()" name="operators" width="60" height="26">
      <img id="tab3" src="/PORTAL/security/images/tab3Off.gif" style="position:relative; top:3; left:0" onClick="showProfile()" name="profile" width="60" height="26">
      <img id="tab4" src="/PORTAL/security/images/tab4Off.gif" style="position:relative; top:3; left:0" onClick="showSecurityLog()" name="securityLog" width="60" height="26">
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