<!-- ****** JAVA CODE ***** //-->

<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.*,
                 edit.common.CodeTableWrapper" %>

<%
    CodeTableVO[] contractCodes = CodeTableWrapper.getSingleton().getCodeTableEntries("CONTRACTCODE");
    CodeTableVO[] frequencies  = CodeTableWrapper.getSingleton().getCodeTableEntries("BONUSFREQUENCY");

    String message = (String) request.getAttribute("message");
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

	function runBonusCommissionStatements() {

<%--        var month = f.processDateMonth.value;--%>
<%--        var day = f.processDateDay.value;--%>
<%--        var year = f.processDateYear.value;--%>

        try
        {
<%--            f.processDate.value = formatDate(month, day, year, true);--%>

            if (selectElementIsEmpty(f.companyName))
            {
                alert("Contract Code Required");
            }
            else if (selectElementIsEmpty(f.mode))
            {
                alert("Mode Required");
            }
            else
            {
               sendTransactionAction("ReportingDetailTran", "runBonusCommissionStatements", "main");
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

<title>Bonus Commission Statements</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="reporting" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td width="1%" nowrap>Contract Code:&nbsp;</td>
      <td width="1%" nowrap>
          <select name="companyName">
          <option selected value="-1">
             Please Select
          </option>

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
        <td nowrap>
            &nbsp;
            &nbsp;
        </td>
    <tr>
      <td nowrap align="right">Process Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="processDate"
              attributesText="id='processDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.processDate', f.processDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>

    </tr>
         <td nowrap>
             &nbsp;
             &nbsp;
        </td>
    <tr>
        <td align="right" nowrap>Mode:&nbsp;</td>
        <td align="left" nowrap>
          <select name="mode">
            <option>Please Select</option>
              <%
                  if (frequencies != null)
                  {
                      for(int i = 0; i < frequencies.length; i++)
                      {
                          String codeDesc    = frequencies[i].getCodeDesc();
                          String code        = frequencies[i].getCode();

                          out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                  }
              %>
          </select>
        </td>
    </tr>
       <td nowrap>
            &nbsp;
       </td>
    <tr>
      <td width="1%" nowrap>&nbsp;</td>
      <td width="1%" nowrap>&nbsp;</td>
      <td width="98%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runBonusCommissionStatements()">
        <input type="button" name="cancel" value="Cancel" onClick="showReportingMain()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
<%--  <input type="hidden" name="processDate">--%>

</form>
</body>
</html>