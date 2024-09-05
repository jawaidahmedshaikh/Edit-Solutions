<!-- ****** JAVA CODE ***** //-->

<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 engine.Company" %>


<jsp:useBean id="accountingRunPageBean"
    class="fission.beans.PageBean" scope="request"/>

<%
    String[] companies = Company.find_All_CompanyNamesForProductType();
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.clientAccountingExtractForm;
	}

	function processClientAccountingExtract()
    {
        if (selectElementIsEmpty(f.companyName))
        {
            alert("Please Select A Company Name");
        }
        else
        {
		    sendTransactionAction("DailyDetailTran", "createClientAccountingExtract", "main");
        }
	}

	function bCancel()
    {
		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}

</script>

<title>EDITSolutions - Create Client Accounting Extract Records</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="clientAccountingExtractForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="accounting" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Extract Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="extractDate"
              attributesText="id='extractDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.extractDate', f.extractDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td nowrap colspan="4">&nbsp;</td>
    </tr>
    <tr>
	  <td nowrap align="right">Company Name:&nbsp;</td>
      <td nowrap align="left">
		<select name="companyName">
		  <option> Please Select </option>
		  <%
            for(int i = 0; i < companies.length; i++)
            {
                out.println("<option name=\"id\" value=\"" + companies[i] + "\">" + companies[i] + "</option>");
            }
		  %>
		</select>
      </td>
    </tr>
    <tr>
      <td nowrap colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap colspan="3">&nbsp;</td>
      <td nowrap align="right">
        <input type="button" name="enter" value=" Enter " onClick="processClientAccountingExtract()">
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>