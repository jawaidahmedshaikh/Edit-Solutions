<%@ page import="client.ClientDetail,
                 fission.utility.Util"%>

<!--
 *
 * User: cgleason
 * Date: Jan 12, 2006
 * Time: 2:28:25 PM
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
<title>EDIT SOLUTIONS - Case Search</title>
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
        initScrollTable(document.getElementById("CaseSearchTableModelScrollTable"));
    }

    function showCaseAfterSearch()
    {
        sendTransactionAction("CaseDetailTran", "showCaseAfterSearch", "main");

        window.close();
    }

    function doSearch()
    {
<%--        if (textElementIsEmpty(f.caseNumber) && textElementIsEmpty(f.caseName))--%>
<%--        {--%>
<%--            alert('Please Enter [Case Number] Or [Case Name]');--%>
<%--            return;--%>
<%--        }--%>
<%----%>
<%--        if (!textElementIsEmpty(f.caseNumber) && !textElementIsEmpty(f.caseName))--%>
<%--        {--%>
<%--            alert("Invalid search parameters.");--%>
<%--            return;--%>
<%--        }--%>

        var tdSearchMessage = document.getElementById("searchMessage");
        tdSearchMessage.innerHTML = "<font face='arial' size='3' color='blue'>Searching ...</font>";

        sendTransactionAction("CaseDetailTran", "showCaseSearchDialog", "_self");
    }
    
    /*
    Callback method from TableModel implemenation.
    */
    function onTableRowDoubleClick(tableId)
    {
    	showCaseAfterSearch();
    }

    function checkForEnter()
    {
        var eventObj = window.event;

        if (eventObj.keyCode == 13)
        {
            doSearch();
        }
    }

    function resetForm()
    {
        f.reset();
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap>
                        Case Number :&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="caseNumber" size="15" maxlength="15" onKeyPress="checkForEnter()" onFocus="f.caseName.value=''">
        </td>
        <td align="left" nowrap="nowrap" rowspan="2">
          <input type="button" value=" Search  " onclick="doSearch()"/>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Case Name:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="caseName" size="30" maxlength="30" onKeyPress="checkForEnter()" onFocus="f.caseNumber.value=''">
        </td>

    </tr>
<%--    END Form Content --%>

    <tr class="filler">
        <td id="searchMessage" align="middle" nowrap>
            &nbsp;
        </td>
        <td colspan="2">
           &nbsp;
        </td>

    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>
<br>

<%--Summary--%>
<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="CaseSearchTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="double"/>
</jsp:include>
<%-- ****************************** END Summary Area ****************************** --%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right" width="33%">
                        <input type="button" value=" Enter " onClick="showCaseAfterSearch()"/>
                        <input type="button" value=" Cancel " onClick="window.close()">                        
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