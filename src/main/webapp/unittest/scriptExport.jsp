<!--
 * 
 * User: cgleason
 * Date: Mar 3, 2006
 * Time: 2:54:46 PM
 * 
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 -->

 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");


%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Script Export</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>


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

        // Initialize scroll tables
        initScrollTable(document.getElementById("ScriptSummaryTableModelScrollTable"));


<%--       sendTransactionAction("ScriptExportTran", "showScriptList", "_self");--%>

    }


<%--    function onTableRowSingleClick(tableId)--%>
<%--    {--%>
<%----%>
<%--       sendTransactionAction("ScriptExportTran", "copySelectedScripts", "_self");--%>
<%--    }--%>

    function copySelectedScripts()
    {
       sendTransactionAction("ScriptExportTran", "copySelectedScripts", "_self");
    }


</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">


<br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="ScriptSummaryTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="true"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>
    <tr>
        </td>        <td align="right">
         <input id="btnSave" type="button" value=" Save " onClick="copySelectedScripts()">
        </td>
    </tr>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">

<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
