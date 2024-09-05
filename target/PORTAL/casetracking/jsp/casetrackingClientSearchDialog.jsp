<%@ page import="client.ClientDetail,
                 edit.common.EDITDate,
                 fission.utility.Util"%>
<!--
 * User: sprasad
 * Date: Mar 18, 2005
 * Time: 2:24:17 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking - Client Search</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
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
        initScrollTable(document.getElementById("ClientSearchTableModelScrollTable"));
    }

    function showClientAfterSearch(clientDetailPK)
    {
        f.clientDetailPK.value = clientDetailPK;

        sendTransactionAction("CaseTrackingTran", "showClientAfterSearch", "main");

        window.close();
    }

    function doSearch()
    {
        if (textElementIsEmpty(f.taxId) && textElementIsEmpty(f.name))
        {
            alert('Please Enter [Tax Identification Number] Or [Name]');
            return;
        }

        if (!textElementIsEmpty(f.taxId) && !textElementIsEmpty(f.name))
        {
            alert("Invalid search parameters.");
            return;
        }

        var tdSearchMessage = document.getElementById("searchMessage");
        tdSearchMessage.innerHTML = "<font face='arial' size='3' color='blue'>Searching ...</font>";

        sendTransactionAction("CaseTrackingTran", "searchClients", "_self");
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
            Name:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="name" size="30" maxlength="30" onKeyPress="checkForEnter()">
        </td>
        <td align="right" nowrap>
            Tax Identification Number:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="taxId" size="20" maxlength="11" onKeyPress="checkForEnter()">
        </td>
    </tr>
<%--    END Form Content --%>

    <tr class="filler">
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="33%">
            &nbsp;
        </td>
        <td id="searchMessage" align="middle" width="33%" nowrap>
                &nbsp;
        </td>
        <td align="right" width="33%">
            <input type="button" value=" Search  " onClick="doSearch()">
            <input type="button" value=" Cancel " onClick="resetForm()">
        </td>
    </tr>
</table>

<br>

<%--Summary--%>
<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="ClientSearchTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="none"/>
</jsp:include>
<%-- ****************************** END Summary Area ****************************** --%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right" width="33%">
            <input type="button" value=" Close  " onClick="window.close();">

        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="clientDetailPK" value="">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>