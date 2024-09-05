<%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 23, 2004
  Time: 1:03:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%-- ****************************** BEGIN Java Code ****************************** --%>
<%

%>
<%-- ****************************** End Java Code ****************************** --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>Cancel Reinsurer</title>
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
     *
     */
    function ok()
    {
        alert("As you wish...");
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" height="90%" border="0" cellspacing="0" cellpadding="5">
    <tr height="50%">
        <td colspan="2" nowrap>
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
    
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap width="100%">

        </td>
    </tr>
    <tr>
        <td align="right" nowrap width="50%">
            Foo2:
        </td>
        <td align="left" nowrap width="50%">
            <input type="button" name=" OK  " value="O.K." onClick="cancelReinsurerEdits()">
            <input type="button" name="Cancel" value="Cancel" onClick="closeWindow()">
        </td>
    </tr>
<%--    END Form Content --%>
    
    <tr height="50%">
        <td colspan="2" nowrap>
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
        <td align="right" nowrap>
            <input type="button" value="  O.K.  " onClick="ok()">
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