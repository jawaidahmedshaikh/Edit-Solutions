<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper" %>


<jsp:useBean id="accountingRunPageBean"
    class="fission.beans.PageBean" scope="request"/>
<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] outputFileTypes = codeTableWrapper.getCodeTableEntries("OUTPUTFILETYPE");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.accountingRunForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function processAccounting()
    {
        if (f.suppressExtractInd.checked == true)
        {
            f.suppressExtractIndStatus.value = "checked";
        }
        else
        {
            f.suppressExtractIndStatus.value = "";
        }

		sendTransactionAction("DailyDetailTran", "runAccounting", "main");
	}

	function bCancel() {

		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}

</script>

<title>EDITSolutions - Accounting Run Date</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="accountingRunForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="accounting" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Process Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="processDate"
              attributesText="id='processDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.processDate', f.processDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
      <td nowrap>&nbsp;</td>
      <td nowarp align="left">Suppress Extract
        <input type="checkbox" name="suppressExtractInd">
      </td>
    </tr>
    <tr>
      <td nowrap colspan="4">&nbsp;</td>
    </tr>
    <tr>
	  <td nowrap align="right">Output File Type:&nbsp;</td>
      <td nowrap align="left">
		<select name="outputFileType">
		  <option> Please Select </option>
		  <%
            for(int i = 0; i < outputFileTypes.length; i++)
            {
                String code = outputFileTypes[i].getCode();
                String codeDesc = outputFileTypes[i].getCodeDesc();

                out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
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
        <input type="button" name="enter" value=" Enter " onClick="processAccounting()">
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
  <input type="hidden" name="suppressExtractIndStatus" value="">

</form>
</body>
</html>