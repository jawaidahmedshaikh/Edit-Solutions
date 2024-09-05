<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.ProductStructureVO,
                 fission.utility.Util" %>


<%
	ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("productStructureVOs");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.rmdNotificationForm;
	}

	function runRmdNotification()
    {
        if (f.productStructure.value == "" ||
            f.productStructure.value ==  "Please Select")
        {
            alert("Please Select A Company Structure");
        }
        else if (f.startDate.value == "")
        {
            alert("Please Enter the Start RMD Annual Date");
        }
        else if (f.endDate.value == "" )
        {
            alert("Please Enter the End RMD Annual Date");
        }
        else
        {
		    sendTransactionAction("ReportingDetailTran", "runRmdNotification", "main");
        }
	}

	function closeRmdNotificationParams()
    {
		sendTransactionAction("ReportingDetailTran", "showReportingMain", "main");
	}

</script>

<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="rmdNotificationForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="rmd" name="rmdParams" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td width="26%" nowrap>Company Key:</td>
      <td width="9%" nowrap>
        <select name="productStructure">
          <option selected value="Please Select">Please Select</option>
  	      <option value="All">All</option>
          <%
            for(int i = 0; i< productStructureVOs.length; i++)
            {
                String currentProductStructurePK = productStructureVOs[i].getProductStructurePK() + "";

                out.println("<option value=\""+ currentProductStructurePK + "\">" + Util.getProductStructure(productStructureVOs[i], ",") + "</option>");
            }
          %>
        </select>
      </td>
      <td width="42%">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap colspan="5">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap align="right">Start RMD Annual Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="startDate"
              attributesText="id='startDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
      <td nowrap>&nbsp;</td>
      <td nowrap align="right">End RMD Annual Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="endDate"
              attributesText="id='endDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.endDate', f.endDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td nowrap colspan="5">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap colspan="4">&nbsp;</td>
      <td nowrap align="right">
        <input type="button" name="enter" value="Enter" onClick="runRmdNotification()">
        <input type="button" name="cancel" value="Cancel" onClick="closeRmdNotificationParams()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>