<!--
 * User: cgleason
 * Date: Apr 09, 2008
 * Time: 12:41:39 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
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
    CodeTableVO[] reasonCodeCTVO = codeTableWrapper.getCodeTableEntries("SUSPENSEREASONCODE");

    String fromDate = "";
    String toDate = "";
    String reasonCode = "";
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

	function filterSuspense()
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
        else if (f.filterUserNumber.value != "" && f.filterReasonCode.value != "null" && f.filterOperator.value != "")
        {
             alert("Cannot Select ContractNumber, Operator and ReasonCode at the same time");
        }
        else if (f.filterUserNumber.value != "" && f.filterOperator.value != "")
        {
             alert("Cannot Select Both ContractNumber and Operator");
        }
        else if (f.filterUserNumber.value != "" && f.filterReasonCode.value != "null")
        {
             alert("Cannot Select Both ContractNumber and ReasonCode");
        }
        else
        {
            sendTransactionAction("ContractDetailTran", "showSuspense", "suspenseDialog");
            window.close();
        }
	}

	function cancelSuspenseFilter()
    {
		window.close();
	}

</script>

<title>Suspense Filter</title>
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
      <td nowrap align="right">Contract Number:&nbsp;</td>
      <td nowrap align="left" colspan="4">
        <input type="text" name="filterUserNumber" size="15" maxlength="15">
      </td>
    </tr>
    <tr>
          <td colspan="5">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap align="right">Operator:&nbsp;</td>
      <td nowrap align="left" colspan="4">
        <input type="text" name="filterOperator" size="15" maxlength="15">
      </td>
    </tr>
    <tr>
          <td colspan="5">&nbsp;</td>
    </tr>
    <tr>
          <td nowrap align="right">Reason Code:</td>
          <td nowrap align="left">
            <select name="filterReasonCode">
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
    <tr>
          <td colspan="5">&nbsp;</td>
    </tr>

    <tr>
      <td colspan="5">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td width="57%" align="right" nowrap colspan="5">
        <input type="button" name="enter" value="Enter" onClick="filterSuspense()">
        <input type="button" name="enter" value="Cancel" onClick="cancelSuspenseFilter()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
