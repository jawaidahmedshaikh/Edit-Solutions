<!-- contractNumberDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO" %>

<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
	String contractNumber 		= contractMainSessionBean.getValue("contractId");
    String pendingClassChangeIndStatus = formBean.getValue("pendingClassChangeIndStatus");

	if ( contractNumber.equals("") ) {

		contractNumber = formBean.getValue("contractNumber");

	}

	String autoAssignNumberBox = formBean.getValue("autoAssignNumberBox");
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		// Resize for billing changes where loaded in full-size screen
        window.resizeTo(0.3 * screen.width, 0.2 * screen.height);
		
		f = document.contractNumberForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function cancelDialog() {

		window.close();
	}

	function saveContract() {

        sendTransactionAction("ContractDetailTran", "saveContract", "contentIFrame");  

		window.close();
	}

</script>

<title>Save Contract</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="contractNumberForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="51%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td colspan="3" nowrap>Contract Number:
        <input type="text" name="contractNumber" disabled maxlength="15" size="15" value="<%= contractNumber%>">
      </td>
    </tr>
    <tr>
      <td colspan="3" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3" align="right" valign="bottom" nowrap>
        <input type="button" name="enter" value="Enter" onClick="saveContract()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="contractNumber" value="<%=contractNumber %>">
  <input type="hidden" name="pendingClassChangeIndStatus" value="<%=pendingClassChangeIndStatus %>">

</form>
</body>
</html>
