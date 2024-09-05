<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper" %>

<jsp:useBean id="acctgDetailRptPageBean"
    class="fission.beans.PageBean" scope="request"/>

<%
	String[]  companyNames = acctgDetailRptPageBean.getValues("companyNames");

    CodeTableVO[] dateTypes = CodeTableWrapper.getSingleton().getCodeTableEntries("DATETYPE");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.acctgDetailRptForm;
	}

	function runAccountingDetailReport() {

        sendTransactionAction("DailyDetailTran", "runAccountingDetailReport", "main");

	}

	function closeSelectAcctgDetailParams() {

		sendTransactionAction("DailyDetailTran", "showReports", "main");
	}

</script>

<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="acctgDetailRptForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table name="taxes" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td width="26%" nowrap align="right">Company Key:</td>
      <td width="9%" nowrap align="left">
        &nbsp;
        <select name="companyName">
         <option selected value="Please Select">
           Please Select
         </option>
	     <option> All </option>

         <%
        	for(int i = 0; i < companyNames.length; i++) {

   		    	out.println("<option name=\"companyName\">" + companyNames[i] + "</option>");
      	    }
         %>
        </select>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">From:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="fromDate"
              attributesText="id='fromDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.fromDate', f.fromDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
      <td nowrap align="right">To:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="toDate"
              attributesText="id='toDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.toDate', f.toDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td width="26%" nowrap align="right">DateType:</td>
      <td width="9%" nowrap align="left">
        &nbsp;
		<select name="dateType">
		  <option> Please Select </option>
		  <%
            for(int i = 0; i < dateTypes.length; i++)
            {
                String code = dateTypes[i].getCode();
                String codeDesc = dateTypes[i].getCodeDesc();

                if (!code.startsWith("Effective"))
                {
                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
            }
		  %>
		</select>
      </td>
      <td width="65%">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="65%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runAccountingDetailReport()">
        <input type="button" name="cancel" value="Cancel" onClick="closeSelectAcctgDetailParams()">
      </td>
    </tr>
  </table>


<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>
