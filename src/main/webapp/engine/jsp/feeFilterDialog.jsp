<!--
 * User: dlataill
 * Date: Sep 8, 2006
 * Time: 9:48:37 AM
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
    CodeTableVO[] trxTypes = codeTableWrapper.getCodeTableEntries("FEETRXTYPE");
    CodeTableVO[] dateTypes = codeTableWrapper.getCodeTableEntries("DATETYPE");

    String filteredFundPK = (String) request.getAttribute("filteredFundPK");
    String fundName = (String) request.getAttribute("fundName");
    String fundNumber = (String) request.getAttribute("fundNumber");

    String fromDate = "";
    String toDate = "";

%>

<html>
<head>

<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.filterForm;

        formatCurrency();
	}

	function filterFees()
    {
        if (f.filterPeriod.value != 0 &&
            (f.fromDate.value != "" || f.toDate.value != ""))
        {
            alert("Cannot Select Both The Filter Period And From/To Dates");
        }
        else if (f.filterDateType.value != 0 && f.filterPeriod.value == 0 &&
                 (f.fromDate.value == "" || f.toDate.value == ""))
        {
            alert("Please Enter Complete From/To Dates");
        }
        else if (f.filterPeriod.value == 0 &&
                 (f.fromDate.value == "" || f.toDate.value == ""))
        {
            alert("Please Select A Filter Period or Select A FilterDate Type AND Enter Complete From/To Dates");
        }
        else
        {
            // Format the dates to be yyyy/mm/dd before going to the back end
            if (f.fromDate.value != "")
            {
                f.fromDate.value = convertMMDDYYYYToYYYYMMDD(f.fromDate.value);
            }

            if (f.toDate.value != "")
            {
                f.toDate.value = convertMMDDYYYYToYYYYMMDD(f.toDate.value);
            }

            sendTransactionAction("FundTran", "filterFees", "feeDialog");
            window.close();
        }
	}

	function cancelFeeFilter()
    {
		window.close();
	}

</script>

<title>Division Fee Filter</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()" bgcolor="#DDDDDD">
<form name="filterForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="80%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Filter Period:&nbsp;</td>
      <td nowrap align="left">
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
      <td>
        &nbsp;
      </td>
      <td nowrap align="right">Filter Date Type:&nbsp;</td>
      <td nowrap align="left">
        <select name="filterDateType">
          <option value="0">Please Select</option>
          <%
              for(int i = 0; i < dateTypes.length; i++)
              {
                  //Exclude AccountingPeriod as valid date type
                  if (!dateTypes[i].getCode().startsWith("Account"))
                  {
                      String code       = dateTypes[i].getCode();
                      String codeDesc   = dateTypes[i].getCodeDesc();

                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
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
      <td align="right" nowrap>From Date:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="fromDate" size='10' maxlength="10" value="<%= fromDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.fromDate', f.fromDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td>
        &nbsp;
      </td>
      <td align="right" nowrap>To Date:&nbsp;</td>
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
      <td align="right" nowrap>From Amount:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="fromAmount" size='15' maxlength="15" CURRENCY>
      </td>
      <td>
        &nbsp;
      </td>
      <td align="right" nowrap>To Amount:&nbsp;</td>
      <td align="left" nowrap colspan="2">
        <input type="text" name="toAmount" size='15' maxlength="15" CURRENCY>
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
      <td nowrap align="right">Sort By Date Type:&nbsp;</td>
      <td nowrap align="left">
        <select name="sortByDateType">
          <option value="null">Please Select</option>
          <%
              for(int i = 0; i < dateTypes.length; i++)
              {
                  //Exclude AccountingPeriod as valid date type
                  if (!dateTypes[i].getCode().startsWith("Account"))
                  {
                      String code = dateTypes[i].getCode();
                      String codeDesc = dateTypes[i].getCodeDesc();

                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
          %>
        </select>
      </td>
      <td>
        &nbsp;
      </td>
      <td nowrap align="right">Sort Order:&nbsp;</td>
      <td nowrap align="left">
        <select name="sortOrder">
          <option value="">Please Select</option>
          <option value="Ascending">Ascending</option>
          <option value="Descending">Descending</option>
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
        <input type="button" name="enter" value="Enter" onClick="filterFees()">
        <input type="button" name="enter" value="Cancel" onClick="cancelFeeFilter()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="filteredFundPK" value="<%= filteredFundPK %>">
  <input type="hidden" name="fundName" value="<%= fundName %>">
  <input type="hidden" name="fundNumber" value="<%= fundNumber %>">

</form>
</body>
</html>
