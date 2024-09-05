<%--
  Created by IntelliJ IDEA.
  User: ${USER}
  Date: ${DATE}
  Time: ${TIME}
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Area Grouping</title>
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

        f.grouping.focus();
    }

    /**
     * Adds a new AreaKey.
     */
    function addAreaKey()
    {
        var grouping = f.grouping;

        var field = f.field;

        if (textElementIsEmpty(grouping))
        {
            alert("Grouping Is Required");
        }
        else if (textElementIsEmpty(field))
        {
            alert("Field Is Required");
        }
        else
        {
            sendTransactionAction("AreaTran", "addAreaKey", "main");

            closeWindow();
        }
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
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Grouping:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="grouping" size="15" maxlength="50" onKeyPress="if (enterKeyPressed()){addAreaKey()}">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Field:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="field" size="15" maxlength="50" onKeyPress="if (enterKeyPressed()){addAreaKey()}">
        </td>
    </tr>
    <tr height="50%">
        <td colspan="2">
            &nbsp;
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
            <input type="button" value="  OK  " onClick="addAreaKey()">
            <input type="button" value=" Cancel " onClick="closeWindow()">
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