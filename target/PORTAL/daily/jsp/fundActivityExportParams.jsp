<!--
 * User: sprasad
 * Date: Apr 2, 2007
 * Time: 4:04:43 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.FundVO,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 event.batch.ActivityFileExportProcessor,
                 fission.utility.Util" %>


<%
    String[] marketingPackageNames = (String[]) request.getAttribute("marketingPackageNames");

    String selectedMarketingPackage = Util.initString((String) request.getAttribute("selectedMarketingPackage"), "");

    String[] dateTypes = ActivityFileExportProcessor.DATE_TYPES;

    String[] dateTypeValues = ActivityFileExportProcessor.DATE_TYPE_VALUES;

    String selectedDateType = Util.initString((String) request.getAttribute("selectedDateType"), "");

    String startDate = Util.initString((String) request.getAttribute("startDate"), null);

    String endDate = Util.initString((String) request.getAttribute("endDate"), null);

    String startMonth = "";
    String startDay = "";
    String startYear = "";
    String endMonth = "";
    String endDay = "";
    String endYear = "";

    if (selectedDateType != "")
    {
        if (selectedDateType.equals(dateTypeValues[1]))
        {
            if (startDate != null)
            {
                startMonth = Util.fastTokenizer(startDate, "/")[1];
                startDay = Util.fastTokenizer(startDate, "/")[2];
                startYear = Util.fastTokenizer(startDate, "/")[0];
            }
            
            if (endDate != null)
            {
                endMonth = Util.fastTokenizer(endDate, "/")[1];
                endDay = Util.fastTokenizer(endDate, "/")[2];
                endYear = Util.fastTokenizer(endDate, "/")[0];
            }
        }
        else if (selectedDateType.equals(dateTypeValues[0]))
        {
            if (startDate != null)
            {
                startMonth = Util.fastTokenizer(startDate, "/")[1];
                startYear = Util.fastTokenizer(startDate, "/")[0];
            }
            
            if (endDate != null)
            {
                endDate = Util.fastTokenizer(endDate, "/")[1];
                endDate = Util.fastTokenizer(endDate, "/")[0];
            }
        }
    }

    String responseMessage = (String) request.getAttribute("responseMessage");
%>

<html>
<head>

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

    var responseMessage = "<%= responseMessage %>";

	function init()
    {
		f = document.fundActivityExportParamForm;

        checkForResponseMessage();
	}

    function filterFundsForMarketingPackage()
    {
        sendTransactionAction("DailyDetailTran", "filterFundsForMarketingPackage", "main");
    }

	function runFundActivityExport()
    {
        var isValidForm = validateForm(f);

        var isValidDate = false;

        if (isValidForm)
        {
            if (f.dateType.value == "ProcessDate")
            {
                try
                {
                    var startDate = formatDate(f.startMonth.value, f.startDay.value, f.startYear.value, true);

                    var endDate = formatDate(f.endMonth.value, f.endDay.value, f.endYear.value, true);

                    f.startDate.value = startDate;

                    f.endDate.value = endDate;

                    isValidDate = true;
                }
                catch(e)
                {
                    alert(e);
                }
            }
            else if (f.dateType.value == "AccountingPeriod")
            {
                f.startDate.value = f.startYear.value + "/" + f.startMonth.value;

                f.endDate.value = f.endYear.value + "/" + f.endMonth.value;

                isValidDate = true;
            }
        } // end isValidForm


        if (isValidDate)
        {
            sendTransactionAction("DailyDetailTran", "runFundActivityExport", "main");
        }
	}

	function bCancel()
    {
		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}

    /*
     * Moves rows.
     */
    function moveRows(tableId)
    {
        move(tableId, "DailyDetailTran", "updateFundActivityFundNameDoubleTableModel", "main");
    }

    function showDateFields()
    {
        sendTransactionAction("DailyDetailTran", "showFundActivityExportDateFields", "main");
    }
</script>

<title>Fund Activity Export</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="fundActivityExportParamForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="banking" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td width="26%" nowrap align="right">Marketing Package:&nbsp;</td>
      <td width="9%" nowrap align="left">
      <select name="marketingPackage" onChange="filterFundsForMarketingPackage()" REQUIRED>
     	  <option value="null">Please Select</option>
      <%
        if (selectedMarketingPackage.equalsIgnoreCase("All"))
        {
      %>
    	  <option selected value="All">All</option>
      <%
        }
        else
        {
      %>
          <option value="All">All</option>
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
      <td width="26%" nowrap align="right" valign="top">Fund Name:&nbsp;</td>
      <td colspan="3" align="left" valign="top">
        <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <jsp:include page="/common/jsp/widget/doubleTableWidget.jsp" flush="true">
                <jsp:param name="tableId" value="FundActivityFundNameDoubleTableModel"/>
                <jsp:param name="multipleRowSelect" value="true"/>
              </jsp:include>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td width="100%" colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%" nowrap align="right">Date Type:&nbsp;</td>
      <td width="9%" nowrap align="left">
        <select name="dateType" onChange="showDateFields()" REQUIRED>
          <option selected value="null">Please Select</option>
          <%
            for(int i = 0; i < dateTypes.length; i++)
            {
                if (dateTypeValues[i].equals(selectedDateType))
                {
                    out.println("<option selected name=\"id\" value=\"" + dateTypeValues[i] + "\">" + dateTypes[i] + "</option>");
                }
                else
                {
                    out.println("<option name=\"id\" value=\"" + dateTypeValues[i] + "\">" + dateTypes[i] + "</option>");
                }
            }
          %>
        </select>
      </td>
      <td width="30%" nowrap align="right">&nbsp;</td>
      <td width="35%" nowrap align="left">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td width="100%" colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%" nowrap align="right">Start Date:&nbsp;</td>
      <td width="9%" nowrap align="left">
        <input type="text" name="startMonth" maxlength="2" size="2" value="<%= startMonth %>" REQUIRED>
        /
        <% if (selectedDateType == "" || dateTypeValues[1].equals(selectedDateType))
           { %>
              <input type="text" name="startDay" maxlength="2" size="2" value="<%= startDay %>" REQUIRED>
              /
        <% } %>
        <input type="text" name="startYear" maxlength="4" size="4" value="<%= startYear %>" REQUIRED>
      </td>
      <td width="30%" nowrap align="right">End Date:&nbsp;</td>
      <td width="35%" nowrap align="left">
        <input type="text" name="endMonth" maxlength="2" size="2" value="<%= endMonth %>" REQUIRED>
        /
        <% if (selectedDateType == "" || dateTypeValues[1].equals(selectedDateType))
           { %>
              <input type="text" name="endDay" maxlength="2" size="2" value="<%= endDay %>" REQUIRED>
              /
        <% } %>
        <input type="text" name="endYear" maxlength="4" size="4"  value="<%= endYear %>"REQUIRED>
      </td>
    </tr>
    <tr>
      <td width="100%" colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td width="100%" colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="30%">&nbsp;</td>
      <td width="35%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runFundActivityExport()">
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
  <input type="hidden" name="generateExportFileInd" value="">

  <input type="hidden" name="startDate" value="">
  <input type="hidden" name="endDate" value="">

  <input type="hidden" name="returnPage" value="/daily/jsp/fundActivityExportParams.jsp">

</form>
</body>
</html>