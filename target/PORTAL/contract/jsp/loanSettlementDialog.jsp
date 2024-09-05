
<%@ page import="fission.beans.PageBean,
                 fission.utility.Util,
                 edit.common.vo.*,
                 edit.common.*,
                 java.util.*"%><!--
 *
 * User: cgleason
 * Date: December 12, 2006
 * Time: 10:47:58 AM
 *
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.

 -->
<jsp:useBean id="contractFunds"
    class="fission.beans.SessionBean" scope="session"/>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String errorMessage = (String) request.getAttribute("ErrorMessage");

    if (errorMessage == null) errorMessage = "";

    String responseMessage = (String) request.getAttribute("responseMessage");

    Hashtable loanSettlement = (Hashtable)session.getAttribute("loanSettlement");

    EDITBigDecimal loanPrincipalPayoff = new EDITBigDecimal();
    EDITBigDecimal loanInterestPayoff = new EDITBigDecimal();
    EDITBigDecimal totalLoanSettlementAmount = new EDITBigDecimal();

    if (errorMessage.equals(""))
    {
        loanPrincipalPayoff = new EDITBigDecimal((String)loanSettlement.get("loanPrincipalPayoff"));
        loanInterestPayoff = new EDITBigDecimal((String)loanSettlement.get("loanInterestPayoff"));

        totalLoanSettlementAmount = loanPrincipalPayoff.addEditBigDecimal(loanInterestPayoff);
    }



%>
<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var errorMessage = "<%= errorMessage %>";

    function init()
    {
        if (errorMessage != "")
        {
            alert(errorMessage);
            window.close();
        }
    }

	function closeLoanSettlementDialog() {

		window.close();
	}
</script>

<head>
<title>Loan Settlement Info</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "loanSettlementForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible;  background-color:#DDDDDD">
  <table id="table1" width="40%" border="0" cellspacing="5" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right" width="5%">Loan Principal Payoff:</td>
      <td>
        <input type="text" name="loanPrincipalPayoff" size="25" maxlength="25" disabled value="<%= loanPrincipalPayoff %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Loan Interest Payoff:</td>
      <td>
        <input type="text" name="loanInterestPayoff" size="25" maxlength="25" disabled value="<%= loanInterestPayoff %>">
      </td>
    </tr>
     <tr>
      <td nowrap align="right" width="5%">Total Loan Settlement Amount:</td>
      <td>
        <input type="text" name="loanInterestPayoff" size="25" maxlength="25" disabled value="<%= totalLoanSettlementAmount %>">
      </td>
    </tr>

    &nbsp;&nbsp;
  </table>

  <table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0">
	<tr>
	  <td align="right" nowrap colspan="2">
		<input type="button" name="close" value="Close" onClick ="closeLoanSettlementDialog()">
	  </td>
	</tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">


</form>
</body>
</html>
