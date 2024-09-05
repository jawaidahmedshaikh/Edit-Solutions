<!--
 * 
 * User: cgleason
 * Date: June 3, 2006
 * Time: 2:54:46 PM
 * 
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  is subject to the license agreement.
 -->

 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- ****************************** BEGIN Java Code ****************************** --%>
<%

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

<script>


	function exportScript()
    {
        sendTransactionAction("FileExportTran", "exportSelectedScripts", "_self");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>


    <tr>
        <td>SCRIPT EXPORT:</td>
    </tr>
<br>


<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="ScriptExportSummaryTableModel"/>
    <jsp:param name="tableHeight" value="80"/>
    <jsp:param name="multipleRowSelect" value="true"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>


    <table width="100%">

        
        <tr>
             <td align="left" valign="bottom">
                <input type="button" name="export" value="Export" onClick="exportScript()">
             </td>
        </tr>

    </table>

</html>
