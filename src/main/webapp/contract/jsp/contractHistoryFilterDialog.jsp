<!--
 * User: sdorman
 * Date: Feb 21, 2006
 * Time: 12:41:39 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO,
                 codetable.business.*" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] filterPeriods = codeTableWrapper.getCodeTableEntries("FILTERPERIOD");
    CodeTableVO[] trxTypes = codeTableWrapper.getTRXCODE_CodeTableEntries();
    CodeTableVO[] statusRestrictions = codeTableWrapper.getCodeTableEntries("STATUSRESTRICTION");

    String fromDate = "";
    String toDate = "";

%>

<html>
<head>

<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

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

	function filterContractHistory()
    {
        if (f.filterPeriod.value != 0 &&
            (f.fromDate.value != "" || f.toDate.value != ""))
        {
            alert("Cannot Select Both The Filter Period And From/To Dates");
        }
        else if (f.filterPeriod.value == 0 &&
                 (f.fromDate.value == "" || f.toDate.value == ""))
        {
            alert("Please Select A Filter Period Or Enter Complete From/To Dates");
        }
        else
        {
            f.fromDate.value = convertMMDDYYYYToYYYYMMDD(f.fromDate.value);
            f.toDate.value = convertMMDDYYYYToYYYYMMDD(f.toDate.value);
            sendTransactionAction("ContractDetailTran", "filterContractHistory", "contentIFrame");
            window.close();
        }
	}

	function cancelContractHistoryFilter()
    {
		window.close();
	}

</script>

<title>Contract History Filter</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()" bgcolor="#DDDDDD">
<form name="filterForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="80%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Filter Period:&nbsp;</td>
      <td nowrap align="left" colspan="4">
        <select name="filterPeriod">
          <option value="0">Please Select</option>
          <%
              for(int i = 0; i < filterPeriods.length; i++) {

                  String code       = filterPeriods[i].getCode();
                  String codeDesc   = filterPeriods[i].getCodeDesc();

                  out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
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
        <input type="text" name="fromDate" size='10' maxlength="10" value="<%= fromDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.fromDate', f.fromDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td>
        &nbsp;
      </td>
      <td align="right" nowrap>To:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="toDate" size='10' maxlength="10" value="<%= toDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.toDate', f.toDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
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
          <option value="null">Please Select</option>
          <%
              for(int i = 0; i < trxTypes.length; i++) {

                  String code       = trxTypes[i].getCode();
                  String codeDesc   = trxTypes[i].getCodeDesc();

                  out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
              }
          %>
        </select>
      </td>
    </tr>
    <tr>
          <td colspan="5">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap align="right">Status Restriction:&nbsp;</td>
      <td nowrap align="left" colspan="4">
        <select name="statusRestriction">
          <option value="null">Please Select</option>
          <%
              for(int i = 0; i < statusRestrictions.length; i++) {

                  String code      = statusRestrictions[i].getCode();
                  String codeDesc   = statusRestrictions[i].getCodeDesc();

                  out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
              }
          %>
        </select>
      </td>
    </tr>
    <tr>
      <td colspan="5">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td width="57%" align="right" nowrap colspan="5">
        <input type="button" name="enter" value="Enter" onClick="filterContractHistory()">
        <input type="button" name="enter" value="Cancel" onClick="cancelContractHistoryFilter()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
