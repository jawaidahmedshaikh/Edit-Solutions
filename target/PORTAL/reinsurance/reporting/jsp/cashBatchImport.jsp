<!--
 * User: sdorman
 * Date: Oct 2, 2007
 * Time: 1:22:42 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<%@ taglib uri="/WEB-INF/SecurityTaglib.tld" prefix="security" %>

<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    String importDate = "";
    String filename = "";
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<head>
<title>EDITSolutions - Cash Batch Import</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript">

    var responseMessage = "<%= responseMessage %>";
    var f = null;

	function init()
    {
        f = document.FileForm;
        checkForResponseMessage();
    }

    function importCashBatch()
    {
        if (f.importDate == null)
        {
            alert("Import date must be entered");
        }

        try
        {
            sendTransactionAction("ReportingDetailTran", "importCashBatch", "_self");
        }
        catch (e)
        {
            alert(e);
        }
    }

</script>
</head>

<body  bgColor="#99BBBB"  onLoad="init()">

<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

<form name="FileForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="90%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>&nbsp</td>
    </tr>
    <tr>
      <td align="right" nowrap>Import Date:&nbsp;</td>

      <td align="left" nowrap>
        <input type="text" name="importDate" size='10' maxlength="10" value="<%= importDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.importDate', f.importDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>&nbsp;</td>
      <td align="left" nowrap>&nbsp;</td>
    </tr>

    <tr>
      <td align="right" nowrap>File Name:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="filename" size='20' maxlength="50" value="<%= filename %>" >
      </td>
    </tr>
    <tr>
    </tr>
    <tr>
      <td nowrap>&nbsp;</td>
      <td align="right" nowrap>
        <input type="button" name="import" value="Import" onClick="importCashBatch()">
      </td>
    </tr>
  </table>

<!-- ****** Hidden Values ***** //-->
	<input type="hidden" name="transaction">
	<input type="hidden" name="action">

</form>
</span>
</body>
</html>
