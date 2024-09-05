
<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.*,
                 fission.utility.Util" %>

<%
    String paymentMode = "";
    String paymentMethod = "";
    String minimumCheck = "";
    String accountType = "";
    String routingNumber = "";
    String accountNumber = "";
    String checks = "";
    String checkSpecialHandling = "";
    String statements = "";
    String statementsSpecialHandling = "";
    String correspondence = "";

    ClientDetailVO clientDetailVO = (ClientDetailVO) session.getAttribute("clientDetailVO");
    if (clientDetailVO != null) {

        PreferenceVO[] preferenceVOs = clientDetailVO.getPreferenceVO();
        if (preferenceVOs != null) {

            for (int p = 0; p < preferenceVOs.length; p++) {

                String overrideStatus = preferenceVOs[p].getOverrideStatus();
                if (overrideStatus.equalsIgnoreCase("P")) {

                    paymentMode = preferenceVOs[p].getPaymentModeCT();
                    paymentMethod = preferenceVOs[p].getDisbursementSourceCT();
                    minimumCheck = preferenceVOs[p].getMinimumCheck().toString();
                    accountType = preferenceVOs[p].getBankAccountTypeCT();
                    routingNumber = preferenceVOs[p].getBankRoutingNumber();
                    accountNumber = preferenceVOs[p].getBankAccountNumber();
                    break;
                }
            }
        }
    }
    
    paymentMode     = Util.initString(paymentMode,"");
    paymentMethod   = Util.initString(paymentMethod,"");
    minimumCheck    = Util.initString(minimumCheck,"");
    accountType     = Util.initString(accountType,"");
    routingNumber   = Util.initString(routingNumber,"");
    accountNumber   = Util.initString(accountNumber,"");

%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.paymentRoutingForm;

        formatCurrency();
	}

</script>

<title>Payment/Routing Info</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="dialog" onLoad="init()" bgcolor="#DDDDDD">
<form name="paymentRoutingForm" method="post" action="/PORTAL/servlet/RequestManager">
  <b>Payment Info</b>
  <span style="border-style:solid; border-width:1; position:relative; width:90%; height:40%; top:0; left:0; z-index:0; overflow:visible">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
      <tr>
        <td nowrap align="right">Pay Mode:&nbsp;</td>
        <td nowrap align="left">
          <input disabled type="text" name="paymentMode" maxlength="20" size="20" value="<%= paymentMode %>">
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Pay Method:&nbsp;</td>
        <td nowrap align="left">
          <input disabled type="text" name="paymentMethod" maxlength="20" size="20" value="<%= paymentMethod %>">
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Minimum Check:&nbsp;</td>
        <td nowrap align="left">
          <input disabled type="text" name="minimumCheck" maxlength="11" size="11" value="<%= minimumCheck %>" CURRENCY>
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Account Type:&nbsp;</td>
        <td nowrap align="left">
          <input disabled type="text" name="minimumCheck" maxlength="20" size="20" value="<%= accountType %>">
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Routing Number:&nbsp;</td>
        <td nowrap align="left">
          <input disabled type="text" name="routingNumber" maxlength="9" size="9" value="<%= routingNumber %>">
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Account Number:&nbsp;</td>
        <td nowrap align="left">
          <input disabled type="text" name="accountNumber" maxlength="17" size="17" value="<%= accountNumber %>">
        </td>
      </tr>
    </table>
  </span>
  <br>
  <br>
  <b>Routing Info</b>
  <span style="border-style:solid; border-width:1; position:relative; width:90%; height:20%; top:0; left:0; z-index:0; overflow:visible">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
      <tr>
        <td nowrap align="right">Checks:&nbsp;</td>
        <td nowrap align="left">
          <input disabled type="text" name="checks" maxlength="20" size="20" value="">
        </td>
        &nbsp;
        <td nowrap align="right">Spcl Handle:&nbsp;</td>
        <td nowrap align="left">
          <input disabled type="text" name="checkSpecialHandling" maxlength="1" size="1" value="">
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Statements:&nbsp;</td>
        <td nowrap align="left">
          <input disabled type="text" name="statements" maxlength="20" size="20" value="">
        </td>
        &nbsp;
        <td nowrap align="right">Spcl Handle:&nbsp;</td>
        <td nowrap align="left">
          <input disabled type="text" name="statementSpecialHandling" maxlength="1" size="1" value="">
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Correspondence:&nbsp;</td>
        <td nowrap align="left" colspan="3">
          <input disabled type="text" name="correspondence" maxlength="20" size="20" value="">
        </td>
      </tr>
    </table>
  </span>
  <span>
    <table width="90%" height="10%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
      <tr>
        <td width="100%" align="right" nowrap>
          <input type="button" name="close" value="Close" onClick="closeWindow()">
        </td>
      </tr>
    </table>
  </span>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
