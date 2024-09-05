<%@ page import="edit.common.EDITDate,
                 fission.utility.Util,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.services.db.hibernate.*,
                 java.util.*,
                 contract.*,
                 client.*,
                 engine.*,
                 edit.portal.common.session.*"%>
<!--
 * User: dlataille
 * Date: Aug 15, 2007
 * Time: 11:18:50 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String groupNumber = (String) request.getAttribute("selectedGroupNumber");
    String fromDate = (String) request.getAttribute("fromDate");
    String toDate = (String) request.getAttribute("toDate");
    String selectedPDPK = (String) request.getAttribute("selectedPDPK");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - PRD Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/contract/javascript/caseMainTabFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>


<script>

    var f = null;

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;
    }

    function closePRDReport()
    {
        sendTransactionAction("CaseDetailTran", "showPRDDetail", "main");
        window.close();
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="dialog" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="PRDReportTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="none"/>
</jsp:include>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	  	<td nowrap align="right" colspan="6">
            <input type="button" name="close" value="Close" onClick="closePRDReport()">
        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="pageToShow">
<input type="hidden" name="selectedGroupNumber" value="<%= groupNumber %>">
<input type="hidden" name="fromDate" value="<%= fromDate %>">
<input type="hidden" name="toDate" value="<%= toDate %>">
<input type="hidden" name="selectedPDPK" value="<%= selectedPDPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
