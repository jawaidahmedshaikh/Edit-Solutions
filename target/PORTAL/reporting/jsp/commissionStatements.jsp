<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.*,
                 edit.common.CodeTableWrapper,
                 edit.portal.taglib.InputSelect" %>

<%
    CodeTableVO[] contractCodes = CodeTableWrapper.getSingleton().getCodeTableEntries("CONTRACTCODE");

    String message = (String) request.getAttribute("message");

    CodeTableVO[] paymentModeVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("PAYMENTMODE");
    InputSelect paymentModeCTs = new InputSelect(paymentModeVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    CodeTableVO[] fileTypes = CodeTableWrapper.getSingleton().getCodeTableEntries("OUTPUTFILETYPE");
%>

<html>
<head>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;

    var message = "<%= message %>";

	function init() {

		f = document.theForm;

        if (message != "null")
        {
            alert(message);
        }
	}

	function runCommissionStatements() {

<%--        var month = f.processDateMonth.value;--%>
<%--        var day = f.processDateDay.value;--%>
<%--        var year = f.processDateYear.value;--%>

        try
        {
<%--            f.processDate.value = formatDate(month, day, year, true);--%>

            if (selectElementIsEmpty(f.contractCodeCT))
            {
                alert("Contract Code Required");
            }
            if (selectElementIsEmpty(f.paymentModeCT))
            {
                alert("Payment Mode Required");
            }
            else
            {
               sendTransactionAction("ReportingDetailTran", "runCommissionStatements", "main");
            }
        }
        catch (e)
        {
            alert(e);
        }
	}

    function showReportingMain()
    {
		sendTransactionAction("ReportingDetailTran", "showReportingMain", "main");
    }

</script>

<title>Commission Statements</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="reporting" width="56%" border="0" cellspacing="0" cellpadding="0" height="150" bgcolor="#DDDDDD">
    <tr>
      <td width="1%" nowrap>Contract Code:&nbsp;</td>
      <td width="1%" nowrap>
          <select name="contractCodeCT">
          <option selected value="-1">
             Please Select
          </option>
	      <option> All </option>

          <%
              if (contractCodes != null)
              {
                  for(int i = 0; i < contractCodes.length; i++)
                  {
                      String code = contractCodes[i].getCode();
                      String codeDesc = contractCodes[i].getCodeDesc();
                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
          %>
          </select>
      </td>
      <td width="98%">&nbsp;</td>
    </tr>
    <tr>
       <td nowrap align="right">Process Date:&nbsp;</td>
       <td nowrap align="left">
         <input:text name="processDate"
               attributesText="id='processDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
         <a href="javascript:show_calendar('f.processDate', f.processDate.value);"><img
                src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                alt="Select a date from the calendar"></a>
       </td>

        <td nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
      <td width="1%" nowrap>Payment Mode:&nbsp;</td>
      <td width="1%" nowrap>
        <input:select name="paymentModeCT" options="<%= paymentModeCTs.getOptions() %>" attributesText="id='paymentModeCT'"/>
      </td>
      <td width="98%">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap>Output File Type:</td>
      <td nowrap>
          <select name="outputFileType">
              <option value="null">Please Select</option>
              <%
                    for(int i = 0; i < fileTypes.length; i++)
                    {
                        if (!fileTypes[i].getCode().equalsIgnoreCase("Flat"))
                        {
                            String codeDesc = fileTypes[i].getCodeDesc();
                            out.println("<option name=\"outputFileType\">" + codeDesc + "</option>");
                        }
                    }
              %>
          </select>
      </td>
      <td nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td width="1%" nowrap>&nbsp;</td>
      <td width="1%" nowrap>&nbsp;</td>
      <td width="98%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runCommissionStatements()">
        <input type="button" name="cancel" value="Cancel" onClick="showReportingMain()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>