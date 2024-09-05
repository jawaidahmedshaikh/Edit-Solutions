<!--
 * User: cgleason
 * Date: Jan 13, 2009
 * Time: 3:07:16 PM
 *
 * (c) 2000-2009 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="fission.beans.*,
                 fission.utility.*,
                 contract.*" %>

 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%

    String responseMessage = (String) request.getAttribute("responseMessage");

    ValueAtIssue[] valueAtIssues = (ValueAtIssue[]) request.getAttribute("valueAtIssues");

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Values At Issue</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";


    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();
    }

    function closeWindow()
    {
        window.close();
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <th width="33%" nowrap>
            Effective Date&nbsp;
        </th>
        <th width="33%" nowrap>
            Guaranteed Premium&nbsp;
        </th>
        <th width="33%" nowrap>
            Increase Amount&nbsp;
        </th>
     </tr>
</table>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:70%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="contractPUASummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
     <%
         if (valueAtIssues != null)
         {
             for (int i = 0; i < valueAtIssues.length; i++)
             {
          %>
                 <tr class="default">
                     <td width="33%" nowrap>
                         <%= DateTimeUtil.formatEDITDateAsMMDDYYYY(valueAtIssues[i].getEffectiveDate()) %>
                     </td>
                     <td width="33%" nowrap>
                         <script>document.write(formatAsCurrency(<%= valueAtIssues[i].getGuaranteedPremium() %>))</script>
                     </td>
                     <td width="33%" nowrap>
                         <script>document.write(formatAsCurrency(<%= valueAtIssues[i].getIncreaseAmount() %>))</script>
                     </td>
                 </tr>
          <%
             }
         }
     %>
    </table>
</span>

<table width="100%" border="0" cellspacing="0" cellpadding="5">
    <tr>
        <td align="left" colspan="4">
            &nbsp;
        </td>
       <td align="right">
            <input id="btnClose" type="button" value=" Close " onClick="closeWindow()">
        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">

<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>