<%@ page import="contract.*,
                 fission.utility.*,
                 client.*,
                 group.ContractGroup"%>
<!--
 * User: cgleason
 * Date: Dec 19, 2005
 * Time: 3:37:37 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    ContractGroup activeContractGroup = (ContractGroup) request.getAttribute("activeContractGroup");
    String caseNumber =  Util.initString((String) request.getAttribute("caseNumber"), "");
    String caseName   =  Util.initString((String) request.getAttribute("caseName"), "");
    String casePK     =  Util.initString((String) session.getAttribute("casePK"), null);
    String groupPK = (String) request.getAttribute("selectedContractGroupPK");

    long activeContractGroupPK   = 0;
    String groupName           = "";
    String groupNumber         = "";

    if (activeContractGroup != null)
    {
//        activePolicyGroupPK = Util.initLong(activePolicyGroup, "policyGroupPK", 0L);
        groupName = (String) Util.initObject(activeContractGroup, "groupName", "");
        groupNumber = (String) Util.initObject(activeContractGroup, "contractGroupNumber", "");
    }

    String toCaseNumber = Util.initString((String)request.getAttribute("toCaseNumber"), "");
    String toCasePK = Util.initString((String)request.getAttribute("toCasePK"), null);
    String clientDetailPK = Util.initString((String)request.getAttribute("toClientDetailPK"), "");
    ClientDetail clientDetail = null;
    String toCaseName = "";
    if (!clientDetailPK.equals(""))
    {
        clientDetail = ClientDetail.findByPK(new Long(clientDetailPK));
        if (clientDetail.isCorporate())
        {
            toCaseName = clientDetail.getCorporateName();
        }
        else
        {
            toCaseName = clientDetail.getLastName() + ", " + clientDetail.getFirstName();
        }
    }

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Change</title>
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
        initScrollTable(document.getElementById("GroupSummaryTableModelScrollTable"));
    }


    function saveCaseChange()
    {
<%--        disableActionButtons();--%>

        sendTransactionAction("CaseDetailTran", "saveCaseChange", "_self");
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("CaseDetailTran", "showSelectedPolicyGroup", "_self");
    }

    function closeCaseChangeDialog()
    {
        sendTransactionAction("CaseDetailTran", "showCaseMain", "main");

        window.close();
    }

    function disableActionButtons()
    {
        document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

        document.all.btnSave.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";

        document.all.btnSave.disabled = true;
        document.all.btnCancel.disabled = true;
    }


</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <table>
        <tr>
            <td align="left" >
                Case/Group Change&nbsp;&nbsp;&nbsp;
            <hr size="1"  noshade>
            </td>
        </tr>
    </table>
    <table> <colspan="4">
        <tr>
            <td align="right" nowrap>
                From Case Number:&nbsp;
            </td>
            <td align="left" nowrap>
                <input disabled type="text" name="caseNumber" size="15" value="<%= caseNumber %>">
            </td>
            <td align="right" nowrap>
                From Case Name:&nbsp;
            </td>
            <td align="left" nowrap>
                <input disabled type="text" name="caseName" size="30" value="<%= caseName %>">
            </td>
        </tr>
        <tr>
            <td align="right" nowrap>
                To Case Number:&nbsp;
            </td>
            <td align="left" nowrap>
                <input type="text" name="toCaseNumber" size="15" value="<%= toCaseNumber %>">
            </td>
            <td align="right" nowrap>
                To Case Name:&nbsp;
            </td>
            <td align="left" nowrap>
                <input  type="text" name="toCaseName" size="30" value="<%= toCaseName %>">
            </td>
        </tr>
        <tr>
            <td align="right" nowrap>
                Group Number:&nbsp;
            </td>
            <td align="left" nowrap>
                <input type="text" name="groupNumber" size="15" value="<%= groupNumber %>">
            </td>
            <td align="right" nowrap>
                Group Name:&nbsp;
            </td>
            <td align="left" nowrap>
                <input type="text" name="groupName" size="30" value="<%= groupName %>">
            </td>
        </tr>
    </table>
<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="GroupSummaryTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

    <tr>
	  	<td nowrap align="right">
			<input id="btnSave" type="button" value="   Save  " onClick="saveCaseChange()">
	  		<input id="btnCancel" type="button" value="  Cancel " onClick="closeCaseChangeDialog()">
        </td>
    </tr>

<%--    END Form Content --%>

    <tr class="filler">
        <td colspan="6">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>


<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="casePK" value="<%= casePK %>">
<input type="hidden" name="selectedPolicyGroupPK" value="<%= groupPK %>">
<input type="hidden" name="caseNumber" value="<%= caseNumber %>">
<input type="hidden" name="caseName" value="<%= caseName %>">
<input type="hidden" name="toCaseNumber" value="<%= toCaseNumber %>">
<input type="hidden" name="toCaseName" value="<%= toCaseName %>">
<input type="hidden" name="toClientDetailPK" value="<%= clientDetailPK %>">
<input type="hidden" name="toCasePK" value="<%= toCasePK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
