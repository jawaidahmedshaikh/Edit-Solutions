<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.FundVO,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 fission.utility.Util" %>


<%
	String[] marketingPackageNames = (String[]) request.getAttribute("marketingPackageNames");

    String selectedMarketingPackage = Util.initString((String) request.getAttribute("selectedMarketingPackage"), "");

    FundVO[] fundVOs = (FundVO[]) request.getAttribute("fundVOs");

    CodeTableVO[] dateTypes = CodeTableWrapper.getSingleton().getCodeTableEntries("DATETYPE");
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
		f = document.caseManagerReviewParamForm;
	}

    function filterFundsForMarketingPackage()
    {
        sendTransactionAction("DailyDetailTran", "filterFundsForMarketingPackage", "main");
    }

	function runCaseManagerReview()
    {
        if (f.selectedMarketingPackage.value == "Please Select")
        {
            alert("Please Select Marketing Package");
        }
        else
        {
            sendTransactionAction("DailyDetailTran", "runCaseManagerReview", "main");
        }
	}

	function bCancel()
    {
		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}
</script>

<title>Case Manager Review</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="caseManagerReviewParamForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="banking" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td width="26%" nowrap align="right">Marketing Package:&nbsp;</td>
      <td width="9%" nowrap align="left">
      <select name="selectedMarketingPackage" onChange="filterFundsForMarketingPackage()">
      <%
        if (selectedMarketingPackage.equals(""))
        {
      %>
          <option selected value="Please Select">Please Select</option>
    	  <option value="All">All</option>
      <%
        }

        if (selectedMarketingPackage.equalsIgnoreCase("All"))
        {
      %>
    	  <option selected value="All">All</option>
      <%
        }
        for(int i = 0; i < marketingPackageNames.length; i++)
        {
            if (!marketingPackageNames[i].equals("*"))
            {
                if (marketingPackageNames[i].equals(selectedMarketingPackage))
                {
                    out.println("<option selected name=\"id\" value=\"" + marketingPackageNames[i] + "\">" + marketingPackageNames[i] + "</option>");
                }
                else
                {
                    out.println("<option name=\"id\" value=\"" + marketingPackageNames[i] + "\">" + marketingPackageNames[i] + "</option>");
                }
            }
        }
      %>
      </select>
      </td>
      <td width="30%">&nbsp;</td>
      <td width="35%">&nbsp;</td>
    </tr>
    <tr>
      <td width="100%" colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%" nowrap align="right">Fund Name:&nbsp;</td>
      <td width="9%" nowrap align="left">
      <select name="selectedFund">
      <option selected value="Please Select">Please Select</option>
	  <option>All</option>
      <%
        if (fundVOs != null)
        {
            for(int i = 0; i < fundVOs.length; i++)
            {
                out.println("<option name=\"id\" value=\"" + fundVOs[i].getFundPK() + "\">" + fundVOs[i].getName() + "</option>");
            }
        }
      %>
      </select>
      </td>
      <td width="30%">&nbsp;</td>
      <td width="35%">&nbsp;</td>
    </tr>
    <tr>
      <td width="100%" colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap align="right">Start Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="startDate"
              attributesText="id='startDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
      <td nowrap align="right">End Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="endDate"
              attributesText="id='endDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.endDate', f.endDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td width="100%" colspan="4">&nbsp;</td>
    </tr>
    <tr>
	  <td width="26%" nowrap align="right">DateType:&nbsp;</td>
      <td width="9%" nowrap align="left">
		<select name="dateType">
		  <option> Please Select </option>
		  <%
            for(int i = 0; i < dateTypes.length; i++)
            {
                String code = dateTypes[i].getCode();
                String codeDesc = dateTypes[i].getCodeDesc();

                out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
            }

		  %>
		</select>
      </td>
      <td width="30%">&nbsp;</td>
      <td width="35%">&nbsp;</td>
    </tr>
    <tr>
      <td width="100%" colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="30%">&nbsp;</td>
      <td width="35%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runCaseManagerReview()">
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

  <input type="hidden" name="returnPage" value="/daily/jsp/caseManagerReviewParams.jsp">

</form>
</body>
</html>