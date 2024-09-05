<!--
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 -->
<%@ taglib uri="/WEB-INF/SecurityTaglib.tld" prefix="security" %>

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 fission.utility.*,
                 event.*,
                 contract.*,
                 edit.portal.common.session.UserSession" %>

<%
    Bucket selectedLoanDetail = (Bucket) request.getAttribute("selectedLoanDetail");

    String responseMessage = Util.initString((String) request.getAttribute("responseMessage"), "");

    PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");


    String depositDate             = "";
    String bucketSource            = "";
    String depositAmount	       = "";
    String loanPrincipalRemaining  = "";
    String loanInterestRate		   = "";

    if (selectedLoanDetail != null)
    {
        bucketSource = Util.initString(selectedLoanDetail.getBucketSourceCT(), "");
        depositDate	= DateTimeUtil.formatYYYYMMDDToMMDDYYYY(selectedLoanDetail.getDepositDate().getFormattedDate());

        depositAmount = selectedLoanDetail.getDepositAmount() + "";
        loanPrincipalRemaining = selectedLoanDetail.getLoanPrincipalRemaining() + "";

        loanInterestRate = selectedLoanDetail.getLoanInterestRate() + "";
    }

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>


<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>

<script language="Javascript1.2">


    var height = screen.height;
    var width  = screen.width;
    var responseMessage = "<%= responseMessage %>";

	var f = null;

    var shouldShowLockAlert = true;

	function init()
    {

		f = document.contractLoanTradForm;

        top.frames["main"].setActiveTab("loanTab");

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true) )
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        if (responseMessage != "")
        {
            alert(responseMessage);
        }

        // Initialize scroll tables
        initScrollTable(document.getElementById("LoanSummaryTableModelScrollTable"))

        formatCurrency();
	}

    function showLockAlert()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Contract can not be edited.");

            return false;
        }
    }

	function onTableRowSingleClick(tableId)
    {

		sendTransactionAction("ContractDetailTran", "showLoanDetailForTrad", "_self");
	}

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
    }

</script>

<head>
<title>EDITSOLUTIONS - Transactions</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init()">
 <form name="contractLoanTradForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="contractInfoHeader.jsp" flush="true"/>

  <span>

  <table width="100%" height="60%" border="0" style="border-style:solid; border-width:1; border-color:Black; background-color:#BBBBBB">
    <tr>
      <td nowrap align="right" >Loan Source:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="loanSource" size="20" maxlength="25" value="<%= bucketSource%>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right" >Effective Date:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="effectiveDate" size="10" maxlength="10" value="<%= depositDate%>">

    </tr>
    <tr>
      <td nowrap align="right" >Original Loan Amount:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="loanAmount" size="12" maxlength="12" value="<%= depositAmount%>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right" >Principal Remaining:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="principalRemaining" size="12" maxlength="12" value="<%= loanPrincipalRemaining%>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Interest Rate:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="interestRate" size="12" maxlength="12" value="<%= loanInterestRate%>">
      </td>
    </tr>

  </table>
  </span>


    <%-- ****************************** BEGIN Summary Area ****************************** --%>

    <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
        <jsp:param name="tableId" value="LoanSummaryTableModel"/>
        <jsp:param name="tableHeight" value="40"/>
        <jsp:param name="multipleRowSelect" value="false"/>
        <jsp:param name="singleOrDoubleClick" value="single"/>
    </jsp:include>

    <%-- ****************************** END Summary Area ****************************** --%>



<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"   value="">
 <input type="hidden" name="action"        value="">
 <input type="hidden" name="key"           value="">

 <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">

</form>
</body>
</html>
