<%@ page import="edit.common.vo.*"%>
 <%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 12, 2004
  Time: 4:43:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    TreatyGroupVO[] treatyGroupVOs = (TreatyGroupVO[]) request.getAttribute("treatyGroupVOs");
%>
<%-- ****************************** End Java Code ****************************** --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>Reinsurer History Filter</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>
    var f = null;

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;
    }

    /**
     * Filters the history by the selected filter criteria.
     */
    function filterHistory()
    {
        alert("Filter history...");
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" height="90%" border="0" cellspacing="0" cellpadding="5">
    <tr height="50%">
        <td colspan="2">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
    
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap width="25%">
            Filter Period:
        </td>
        <td align="left" nowrap width="25%">
            <input type="text" name="foo1" size="15" maxlength="50" onKeyPress="if (enterKeyPressed()){filterHistory()}">
        </td>
        <td align="right" nowrap width="25%">
            &nbsp;
        </td>
        <td align="left" nowrap width="25%">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            From:
        </td>
        <td align="left" nowrap>
            <input type="text" name="fromDateMonth" size="2" maxlength="2"> /
            <input type="text" name="fromDateDay" size="2" maxlength="2"> /
            <input type="text" name="fromDateYear" size="4" maxlength="4">
        </td>
        <td align="right" nowrap>
            To:
        </td>
        <td align="left" nowrap>
            <input type="text" name="toDateMonth" size="2" maxlength="2"> /
            <input type="text" name="toDateDay" size="2" maxlength="2"> /
            <input type="text" name="toDateYear" size="4" maxlength="4">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Treaty #:
        </td>
        <td align="left" nowrap>
            <select name="areaCT">
                <option name="id" value="0">Please Select</option>
<%
    if (treatyGroupVOs != null)
    {
        for (int i = 0; i < treatyGroupVOs.length; i++)
        {
            String currentTreatyGroupPK = String.valueOf(treatyGroupVOs[i].getTreatyGroupPK());

            out.println("<option name=\"id\" value=\"" + currentTreatyGroupPK + "\">" + "FOO" + "</option>");
        }
    }
%>
            </select>
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
<%--    END Form Content --%>
    
    <tr height="50%">
        <td colspan="2">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%-- ****************************** BEGIN Buttons ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" value="Enter " onClick="filterHistory()">
            <input type="button" value="Cancel" onClick="closeWindow()">
        </td>
    </tr>
</table>
<%-- ****************************** END Buttons ****************************** --%>


<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>