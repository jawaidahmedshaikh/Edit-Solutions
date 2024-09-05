<!-- contractCommitContractNumberDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*"%>

<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>

<%

    String clientId = "";
%>
<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.clientIdSelectionForm;
        f.clientId.focus();
	}

    function showClientRoles() {

        sendTransactionAction("RoleTran", "showRolesForSelectedClient", "contentIFrame");
        window.close();
    }

    function cancelClientIdSelection() {

        window.close();
    }

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

</script>

<title>Please Enter Client Id</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="clientIdSelectionForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="51%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td colspan="2" nowrap>Client ID:
        <input type="text" name="clientId" maxlength="15" size="15" value="<%= clientId %>">
      </td>
    </tr>
    <tr>
      <td colspan="2" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="2" align="right" valign="bottom" nowrap>
        <input type="button" name="enter" value="Enter" onClick="showClientRoles()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelClientIdSelection()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
