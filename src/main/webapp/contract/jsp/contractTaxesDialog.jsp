<!-- contractTaxesDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.portal.common.session.UserSession" %>

<jsp:useBean id="contractTaxesSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
	String exchangeIndStatus                   = contractTaxesSessionBean.getValue("exchangeIndStatus");
    String taxReportingGroup                   = contractTaxesSessionBean.getValue("taxReportingGroup");
    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;
	var editableContractStatus = true;
	
	function init() {

		// check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;

		f = document.taxesForm;
	}

	function sendTransactionAction(transaction, action, target) {

		if (f.exchangeInd.checked == true) {
			f.exchangeIndStatus.value = "checked";
		}

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function saveTaxes() {
		
		if (editableContractStatus == true)
		{
	        sendTransactionAction("ContractDetailTran", "saveTaxes", "contentIFrame");
			window.close();
			
		} else {
			alert("This Contract Cannot Be Edited Due to Terminated Status.");
		}
	}

	function cancelTaxes() {

		window.close();
	}

</script>

<title>Taxes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()" bgcolor="#DDDDDD">
<form name="taxesForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="80%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="left">
        <input type="checkbox" name="exchangeInd" tabindex="2" <%= exchangeIndStatus %>>
        1035 Exchange
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Tax Reporting Group:&nbsp;
        <input type="text" name="taxReportingGroup" tabindex="4" value= "<%= taxReportingGroup %>">
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td width="57%" align="right" nowrap>
        <input type="button" name="enter" value="Enter" onClick="saveTaxes()">
        <input type="button" name="enter" value="Cancel" onClick="cancelTaxes()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="exchangeIndStatus" value="">

</form>
</body>
</html>
