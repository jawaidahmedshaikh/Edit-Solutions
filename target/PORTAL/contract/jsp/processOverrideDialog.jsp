<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<jsp:useBean id="contractTransactionSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    PageBean formBean = contractTransactionSessionBean.getPageBean("formBean");

    String noCorrespondenceIndStatus = formBean.getValue("noCorrespondenceIndStatus");
    String noAccountingIndStatus = formBean.getValue("noAccountingIndStatus");
    String noCommissionIndStatus = formBean.getValue("noCommissionIndStatus");
    String noCheckEFTStatus = formBean.getValue("noCheckEFTStatus");
    String zeroLoadIndStatus = formBean.getValue("zeroLoadIndStatus");
%>

<html>
<head>
<title>Process Overrides</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.processForm;
	}

    function cancelProcessOverride() {

    	window.close();
    }

	function saveProcessOverride() {

		if (f.noCorrespondenceInd.checked == true) {

			f.noCorrespondenceIndStatus.value = "checked";
		}
        if (f.noAccountingInd.checked == true) {

            f.noAccountingIndStatus.value = "checked";
        }
        if (f.noCommissionInd.checked == true) {

            f.noCommissionIndStatus.value = "checked";
        }
        if (f.zeroLoadInd.checked == true) {

            f.zeroLoadIndStatus.value = "checked";
        }
        if (f.noCheckEFT.checked == true)
        {
            f.noCheckEFTStatus.value = "checked";
        }

		sendTransactionAction("ContractDetailTran", "updateProcessOverride", "transactionDialog");
        window.close();
	}

    function sendTransactionAction(transaction, action, target) {

	   	f.transaction.value=transaction;
    	f.action.value=action;

    	f.target = target;

    	f.submit();
    }

</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="processForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="40%" border="0" cellspacing="5" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="right" width="10%" nowrap height="31">No Correspondence&nbsp;</td>
      <td align="left" width="10%" nowrap height="31">
        <input type="checkbox" name="noCorrespondenceInd" <%= noCorrespondenceIndStatus %>>
      </td>
    </tr>
    <tr>
      <td align="right" width="10%" nowrap height="31">No Accounting&nbsp;</td>
      <td align="left" width="10%" nowrap height="31">
        <input type="checkbox" name="noAccountingInd" <%= noAccountingIndStatus %>>
      </td>
    </tr>
    <tr>
      <td align="right" width="10%" nowrap height="31">No Commission&nbsp;</td>
      <td align="left" width="10%" nowrap height="31">
        <input type="checkbox" name="noCommissionInd" <%= noCommissionIndStatus %>>
      </td>
    </tr>
    <tr>
      <td align="right" width="10%" nowrap height="31">Zero Load&nbsp;</td>
      <td align="left" width="10%" nowrap height="31">
        <input type="checkbox" name="zeroLoadInd" <%= zeroLoadIndStatus %>>
      </td>
    </tr>
    <tr>
      <td align="right" width="10%" nowrap height="31">No Check/EFT&nbsp;</td>
      <td align="left" width="10%" nowrap height="31">
        <input type="checkbox" name="noCheckEFT" <%= noCheckEFTStatus %>>
      </td>
    </tr>
    <tr>
      <td align="right" width="10%" nowrap>&nbsp;</td>
      <td align="right" width="10%" nowrap>
        <input type="button" name="enter" value=" Enter" onClick="saveProcessOverride()">
        <input type="button" name="cancel" value="Cancel" onClick ="cancelProcessOverride()">
      </td>
    </tr>
  </table>

  <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction"  value="">
  <input type="hidden" name="action"       value="">

  <input type="hidden" name="noCorrespondenceIndStatus" value="">
  <input type="hidden" name="noAccountingIndStatus" value="">
  <input type="hidden" name="noCommissionIndStatus" value="">
  <input type="hidden" name="zeroLoadIndStatus" value="">
  <input type="hidden" name="noCheckEFTStatus" value="">

</form>

</body>
</html>
