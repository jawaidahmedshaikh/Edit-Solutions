<!-- agentTaxInfoDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.*,
                 fission.utility.Util" %>

<%
    String taxIndicator = "";
    String ficaStatus = "unchecked";

    ClientDetailVO clientDetailVO = (ClientDetailVO) session.getAttribute("clientDetailVO");
    if (clientDetailVO != null) {

        TaxInformationVO[] taxInformationVO = clientDetailVO.getTaxInformationVO();
        if (taxInformationVO != null && taxInformationVO.length > 0) {

            TaxProfileVO[] taxProfileVOs = taxInformationVO[0].getTaxProfileVO();
            if (taxProfileVOs != null) {

                for (int t = 0; t < taxProfileVOs.length; t++) {

                    String overrideStatus = taxProfileVOs[t].getOverrideStatus();
                    if (overrideStatus.equalsIgnoreCase("P")) {

                        taxIndicator = taxProfileVOs[t].getTaxIndicatorCT();
                        String fica = taxProfileVOs[t].getFicaIndicator();
                        if (fica.equalsIgnoreCase("Y")) {

                            ficaStatus = "checked";
                        }
                        break;
                    }
                }
            }
        }
    }
    taxIndicator   =   Util.initString(taxIndicator,"");
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.taxInfoForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value = action;

		f.target = target;

		f.submit();
	}

	function closeTaxInfoDialog() {

		window.close();
	}

</script>

<title>Tax Info</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="dialog" onLoad="init()" bgcolor="#DDDDDD">
<form name="taxInfoForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="80%" height="70%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="left">Tax Indicator:&nbsp;
        <input disabled type="text" name="taxIndicator" maxlength="20" size="20" value="<%= taxIndicator %>">
      </td>
    </tr>
    <tr>
        <td nowrap align="left">FICA: &nbsp;
          <input disabled type="checkbox" name="fica" value="<%= ficaStatus %>">
        </td>
    </tr>
  </table>
  <table width="90%" height="15%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td width="100%" align="right" nowrap>
        <input type="button" name="close" value="Close" onClick="closeTaxInfoDialog()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
