
<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.Util" %>

<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    PageBean mainBean = quoteMainSessionBean.getPageBean("formBean");
    String companyStructureId = Util.initString(mainBean.getValue("companyStructureId"), "0");


    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] trxTypes = codeTableWrapper.getCodeTableEntries("TRXTYPE", Long.parseLong(companyStructureId));
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.transactionTypeForm;

		window.resizeTo(getPreferredWidth(), getPreferredHeight());
	}

	function getPreferredWidth() {

		return 1.25 * document.all.table1.offsetWidth;
	}

	function getPreferredHeight() {

		return 1.50 * document.all.table1.offsetHeight;
	}

	function openDialog(theURL,winName,features,transaction,action) {

	  dialog = window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, winName);
	}

	function showTransactionDefault() {

		f.transactionType.value = f.transactionTypeSelect.options[f.transactionTypeSelect.selectedIndex].text;

		sendTransactionAction("QuoteDetailTran", "showTransactionDefault", "contentIFrame");
		window.close();
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

</script>

<title>Select Transaction Type</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="transactionTypeForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="51%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap width="1%">Transaction Type:</td>
      <td>
        <select name="transactionTypeSelect">
        <%

            for(int i = 0; i < trxTypes.length; i++) {

                String codeTablePK = trxTypes[i].getCodeTablePK() + "";
                String codeDesc    = trxTypes[i].getCodeDesc();

                out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
            }

       %>

        </select>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="right">
        <input type="button" name="enter" value="Enter" onClick="showTransactionDefault()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>
  <!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">
<input type="hidden" name="transactionType"  value="">

</form>
</body>
</html>
