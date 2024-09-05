<!-- taxAdjustmentDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 fission.utility.Util,
                 edit.common.vo.CodeTableVO" %>


<jsp:useBean id="contractTaxesSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
//    SessionBean contractMainSessionBean = (SessionBean) session.getAttribute("contractMainSessionBean");
//	PageBean formBean = contractMainSessionBean.getPageBean("historyFormBean");

    SessionBean historySession = (SessionBean) session.getAttribute("contractHistories");
	PageBean formBean = historySession.getPageBean("fh");

	String costBasis = formBean.getValue("costBasis");
    String taxYear = formBean.getValue("taxYear");
    String editTrxHistoryPK = (String)request.getAttribute("editTrxHistoryPK");


%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.taxAdjustmentForm;

		window.resizeTo(getPreferredWidth(), getPreferredHeight());

        formatCurrency();
	}

	function getPreferredWidth()
    {
		return .75 * document.all.table1.offsetWidth;
	}

	function getPreferredHeight()
    {
		return 1.75 * document.all.table1.offsetHeight;
	}

	function saveTaxes()
    {
        sendTransactionAction("ContractDetailTran", "saveTaxAdjustment", "contentIFrame");
		window.close();
	}
</script>

<title>Taxes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()" bgcolor="#DDDDDD">
<form name="taxAdjustmentForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="80%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
       <td nowrap align="left">Tax Year:&nbsp;&nbsp;
        <input type="text" name="taxYear" tabindex="4" value= "<%= taxYear %>">
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
      <td nowrap align="left">Cost Basis:&nbsp;
        <input type="text" name="costBasis" tabindex="4" value= "<%= costBasis %>" CURRENCY>
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
        <input type="button" name="enter" value="Cancel" onClick="closeWindow()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="filterTransaction" value="<%= request.getAttribute("filterTransaction") %>">
  <input type="hidden" name="statusRestriction" value="<%= request.getAttribute("statusRestriction") %>">
  <input type="hidden" name="fromDate" value="<%= request.getAttribute("fromDate") %>">
  <input type="hidden" name="toDate" value="<%= request.getAttribute("toDate") %>">
  <input type="hidden" name="filterPeriod" value="<%= request.getAttribute("filterPeriod") %>">
 <input type="hidden" name="editTrxHistoryPK" value="<%= editTrxHistoryPK %>">
</form>
</body>
</html>
