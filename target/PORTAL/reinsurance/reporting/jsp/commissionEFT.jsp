<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.*,
                 edit.common.CodeTableWrapper,
                 edit.portal.taglib.InputSelect" %>

<%
	String[]  companyNames = (String[]) request.getAttribute("companyNames");

    String message = (String) request.getAttribute("message");

    CodeTableVO[] paymentModeVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("PAYMENTMODE");
    InputSelect paymentModeCTs = new InputSelect(paymentModeVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);
    CodeTableVO[] yesNoVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("YESNO");
    InputSelect yesNoCTs = new InputSelect(yesNoVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);
%>

<html>
<head>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

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

	function runCommissionEFT() {

        try
        {
            if (selectElementIsEmpty(f.companyName))
            {
                alert("Company Key Required");
            }
            else if (selectElementIsEmpty(f.paymentModeCT))
            {
                alert("Payment Mode Required");
            }
            else
            {
               sendTransactionAction("ReportingDetailTran", "createCommissionEFTCK", "main");
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

<title>Commission EFT</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="reporting" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td width="1%" nowrap>Company Key:&nbsp;</td>
      <td width="1%" nowrap>
          <select name="companyName">
          <option selected value="-1">
             Please Select
          </option>
        <option value="All"> All </option>

          <%
              if (companyNames != null)
              {
                  for(int i = 0; i < companyNames.length; i++)
                  {
                      out.println("<option name=\"id\" value=\"" + companyNames[i] + "\">" + companyNames[i] + "</option>");
                  }
              }
          %>
          </select>
      </td>
      <td width="98%">&nbsp;</td>
    </tr>
    <tr>
      <td width="1%" nowrap>Payment Mode:&nbsp;</td>
      <td width="1%" nowrap>
          <input:select name="paymentModeCT" options="<%= paymentModeCTs.getOptions() %>" attributesText="id='paymentModeCT'"/>
      </td>
      <td width="98%">&nbsp;</td>
    </tr>
    <tr>
        <td nowrap>
            Force-out Minimum Balance:&nbsp;
        </td>
        <td nowrap>
            <input:select name="forceOutMinBal" options="<%= yesNoCTs.getOptions() %>" attributesText="id='forceOutMinBal'"/>
        </td>
        <td nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
      <td width="1%" nowrap>&nbsp;</td>
      <td width="1%" nowrap>&nbsp;</td>
      <td width="98%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runCommissionEFT()">
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