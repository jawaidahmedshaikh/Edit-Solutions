<!--
 * User: cgleason
 * Date: Apr 11, 2008
 * Time: 12:41:39 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.utility.Util,
                 event.*" %>

<%
    String suspenseMessage = Util.initString((String) request.getAttribute("suspenseMessage"), "");

    String suspensePK = Util.initString((String) request.getAttribute("selectedSuspensePK"), "0");
    Suspense suspense = (Suspense)request.getAttribute("suspense");

    if (!suspensePK.equals("0"))
    {
        suspense = Suspense.findByPK(new Long(suspensePK));
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    CodeTableVO[]  reasonCodeCTVO = codeTableWrapper.getCodeTableEntries("SUSPENSEREASONCODE");

    String reasonCode = Util.initString((String)request.getAttribute("reasonCode"), "");

    String suspenseAmount = null;
    if (suspense != null)
    {
        suspenseAmount = suspense.getSuspenseAmount().toString();
    }

    String amount = Util.initString((String)request.getAttribute("amount"), "");

    String contractNumber = Util.initString((String)request.getAttribute("contractNumber"), "");

    //to keep in session
    String selectedRowId = (String)request.getAttribute("selectedTransferPK");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>

<script language="JavaScript1.2">

<!--******* JAVASCRIPT ******//-->

var f = null;

var suspenseMessage = "<%= suspenseMessage %>";

    function init()
    {
        f = document.suspenseTransferForm;

        if (suspenseMessage != "")
        {
            alert(suspenseMessage);
        }

        formatCurrency();

        // Initialize scroll tables
        initScrollTable(document.getElementById("SuspenseTransferTableModelScrollTable"));
    }

	function onTableRowSingleClick(tableId)
    {
		sendTransactionAction("ContractDetailTran", "showSuspenseTransferDetail", "_self");
	}

	function addCancelTransferSuspense()
    {
        f.contractNumber.value = "";
        f.amount.value = ".00";
	}

	function saveTransferSuspenseToSummary()
    {
		sendTransactionAction("ContractDetailTran", "suspenseTransferSaveToSummary", "_self");
	}

	function deleteTransferSuspense()
    {
		sendTransactionAction("ContractDetailTran", "suspenseTransferDelete", "_self");
	}

    function suspenseTransfer()
    {
<%--        f.suspenseAmount.disabled = false;--%>
        sendTransactionAction("ContractDetailTran", "suspenseTransfer", "suspenseTransferDialog");
    }

    function closeTransfer()
    {
       sendTransactionAction("ContractDetailTran", "closeSuspenseTransferDialog", "suspenseDialog");
       window.close();
    }


</script>
<head>
<title>Suspense Transfer</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name="suspenseTransferForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
<table class="formData" width="100%" height="4%" border="0" cellspacing="0" cellpadding="5">
  <tr colspan="4">
    <td align="right" nowrap>Suspense Amount:&nbsp;</td>
    <td disabled align="left" nowrap>
      <input type="text" name="suspenseAmount" size="15" maxlength="20" value="<%= suspenseAmount %>" CURRENCY>
    </td>
          <td nowrap align="right">Reason Code:</td>
          <td nowrap align="left">
            <select name="reasonCode">
            <option value="null">Please Select</option>
            <%
                  for(int i = 0; i < reasonCodeCTVO.length; i++) {

                      String codeDesc    = reasonCodeCTVO[i].getCodeDesc();
                      String code        = reasonCodeCTVO[i].getCode();

                     if (reasonCode.equals(code))
                     {
                         out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                     }
                     else
                     {
                         out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                     }
                  }

             %>
            </select>
      </td>
  </tr>
</table>

<br>
<br>


<table width="100%" height="4%" style="border:1px solid black" cellspacing="0" cellpadding="0">
  <tr>
     <td align="left" nowrap>New Contract Number:&nbsp;
       <input type="text" name="contractNumber" size="15" maxlength="15"  value="<%= contractNumber %>">
     </td>
    <td align="right" nowrap>Amount:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="amount" size="15" maxlength="20" value="<%= amount %>" CURRENCY>
    </td>
  </tr>
  <tr>
      <td colspan="3">
        &nbsp;
      </td>
  </tr>
  <tr>
	  <td nowrap align="left">
	  	<input type="button" name="btnAdd" value="   Add   " onClick="addCancelTransferSuspense()">
		<input type="button" name="btnSave" value="   Save  " onClick="saveTransferSuspenseToSummary()">
	  	<input type="button" name="btnCancel" value=" Cancel  " onClick="addCancelTransferSuspense()">
	  	<input type="button" name="btnDelete" value="  Delete " onClick="deleteTransferSuspense()">
    </td>
  </tr>
</table>

<br>
<br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="SuspenseTransferTableModel"/>
    <jsp:param name="tableHeight" value="40"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

<%--</table>--%>

  <br>

 <table  width="100%" border="0">
      <tr>
        <td align="right" colspan="2">
            &nbsp;
        </td>
        <td align="right" nowrap>
          <input type="button" name="save" value="Save" onClick="suspenseTransfer()">
          <input type="button" name="cancel" value="Close" onClick="closeTransfer()">
        </td>
      </tr>
</table>

</span>


<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="selectedSuspensePK" value="<%= suspensePK %>">
    <input type="hidden" name="selectedTransferPK" value="<%= selectedRowId %>">

</form>
</body>

</html>
