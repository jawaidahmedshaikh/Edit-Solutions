<!-- taxAdjustmentDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] filterPeriods = codeTableWrapper.getCodeTableEntries("FILTERPERIOD");
    CodeTableVO[] trxTypes = codeTableWrapper.getTRXCODE_CodeTableEntries();
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.filterForm;
	}

	function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function filterCommissionHistory()
    {
        if (f.filterPeriod.value != 0 &&
            (f.fromMonth.value != "" ||
             f.fromDay.value != "" ||
             f.fromYear.value != "" ||
             f.toMonth.value != "" ||
             f.toDay.value != "" ||
             f.toYear.value != ""))
        {
            alert("Cannot Select Both The Filter Period And From/To Dates");
        }
        else if (f.filterPeriod.value == 0 &&
                 (f.fromMonth.value == "" ||
                  f.fromDay.value == "" ||
                  f.fromYear.value == "" ||
                  f.toMonth.value == "" ||
                  f.toDay.value == "" ||
                  f.toYear.value == ""))
        {
            alert("Please Select A Filter Period Or Enter Complete From/To Dates");
        }
        else
        {
            sendTransactionAction("AgentDetailTran", "filterCommissionHistory", "contentIFrame");
            window.close();
        }
	}

	function cancelCommissionHistoryFilter()
    {
		window.close();
	}

</script>

<title>Commission History Filter</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()" bgcolor="#DDDDDD">
<form name="filterForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="80%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Filter Period:&nbsp;</td>
      <td nowrap align="left" colspan="4">
        <select name="filterPeriod">
          <option>Please Select</option>
          <%
              for(int i = 0; i < filterPeriods.length; i++) {

                  String codeTablePK = filterPeriods[i].getCodeTablePK() + "";
                  String codeDesc    = filterPeriods[i].getCodeDesc();

                  out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
              }
          %>
        </select>
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>From:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="fromMonth" size="2" maxlength="2">
        /
        <input type="text" name="fromDay" size="2" maxlength="2">
        /
        <input type="text" name="fromYear" size="4" maxlength="4">
      </td>
      <td>
        &nbsp;
      </td>
      <td align="right" nowrap>To:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="toMonth" size="2" maxlength="2">
        /
        <input type="text" name="toDay" size="2" maxlength="2">
        /
        <input type="text" name="toYear" size="4" maxlength="4">
      </td>
    </tr>
    <tr>
      <td colspan="5">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Filter Transaction:&nbsp;</td>
      <td nowrap align="left" colspan="4">
        <select name="filterTransaction">
          <option>Please Select</option>
          <%
              for(int i = 0; i < trxTypes.length; i++) {

                  String codeTablePK = trxTypes[i].getCodeTablePK() + "";
                  String codeDesc    = trxTypes[i].getCodeDesc();

                  out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
              }
          %>
        </select>
      </td>
    </tr>
    <tr>
          <td colspan="5">&nbsp;</td>
    </tr>
    <tr>
            <td nowrap align="right">
                Policy:&nbsp;
    		</td>
    		<td>
                <input align="left" colspan="4" type="text" name="policyNumber" size="20" maxlength="20">
            </td>
    </tr>
    <tr>
      <td colspan="5">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td width="57%" align="right" nowrap colspan="5">
        <input type="button" name="enter" value="Enter" onClick="filterCommissionHistory()">
        <input type="button" name="enter" value="Cancel" onClick="cancelCommissionHistoryFilter()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
