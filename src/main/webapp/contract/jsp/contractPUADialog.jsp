<!--
 * User: sdorman
 * Date: Sep 23, 2008
 * Time: 3:07:16 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
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

    InherentRider[] inherentRiders = (InherentRider[]) request.getAttribute("inherentRiders");

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - PUA</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

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

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <th width="50%" nowrap>
            Effective Date:&nbsp;
        </th>
        <th width="50%" nowrap>
            Amount:&nbsp;
        </th>
     </tr>
</table>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:70%; top:0; left:0; background-color:#BBBBBB">     
    <table class="summary" id="contractPUASummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
     <%
        for (int i = 0; i < inherentRiders.length; i++)
        {
     %>
            <tr class="default">
                <td width="50%" nowrap>
                    <%= DateTimeUtil.formatEDITDateAsMMDDYYYY(inherentRiders[i].getRiderEffectiveDate()) %>    
                </td>
                <td width="50%" nowrap>
                    <script>document.write(formatAsCurrency(<%= inherentRiders[i].getAmount() %>))</script>
                </td>
            </tr>
     <%
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