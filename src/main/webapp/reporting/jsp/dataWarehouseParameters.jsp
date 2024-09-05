<!-- ****** JAVA CODE ***** //-->

<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.portal.taglib.InputSelect,
                 engine.Company" %>

<jsp:useBean id="dataWarehouseParamsPageBean"
    class="fission.beans.PageBean" scope="request"/>

<%
	String[]  companyNames = dataWarehouseParamsPageBean.getValues("companyNames");

    String message = (String) request.getAttribute("message");
%>

<html>
<head>

<title>Data Warehouse Parameters</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>
<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.dataWarehouseParametersForm;

	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();

	}

	function runDataWarehouse()
    {
        if (f.parameterDate.value == "")
        {
            alert("Please Enter Process Date");
            return;
        }
        else
        {
            f.dataWarehouseDate.value = convertMMDDYYYYToYYYYMMDD(f.parameterDate.value);
        }

        if (f.companyName.selectedIndex == 0)
        {
            alert("Please Select Company");
            return;
        }

		sendTransactionAction("ReportingDetailTran", "runDataWarehouse", "main");
	}

	function bCancel()
    {
		sendTransactionAction("ReportingDetailTran", "showReportingMain", "main");
	}


</script>
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="dataWarehouseParametersForm" method="post" action="/PORTAL/servlet/RequestManager">
<span id="mainContent" style="border-style:solid; border-width:0;  position:relative; width:100%; height:70%; top:0; left:0; z-index:0; overflow:visible">
  <table name="batch" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Process Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="parameterDate"
              attributesText="id='parameterDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.parameterDate', f.parameterDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Company:&nbsp;</td>
      <td nowrap align="left">
        <select name="companyName">
          <option selected value="Please Select">
             Please Select
          </option>
          <option> All </option>
          <%
          	for(int i = 0; i < companyNames.length; i++)
            {
			    out.println("<option name=\"companyName\">" + companyNames[i] + "</option>");
      	    }
          %>
        </select>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Case Number:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="caseNumber" maxlength="10" size="10">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Group Number:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="groupNumber" maxlength="10" size="10">
      </td>
    </tr>
    <tr>
      <td colspan="3" align="right">
        <input type="button" name="enter" value=" Enter " onClick="runDataWarehouse()">
        <input type="button" name="cancel" value="Cancel"  onClick="bCancel()">
      </td>
    </tr>
  </table>


<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
  <input type="hidden" name="dataWarehouseDate" value="">

</form>
</body>
</html>
