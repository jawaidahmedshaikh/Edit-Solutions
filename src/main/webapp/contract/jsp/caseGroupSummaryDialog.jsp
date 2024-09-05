<%@ page import="edit.common.EDITDate,
                 fission.utility.Util,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.services.db.hibernate.*,
                 java.util.*,
                 contract.*,
                 group.ContractGroup"%>
<!--
 * User: cgleason
 * Date: Dec 13, 2005
 * Time: 3:59:50 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    ContractGroup activeContractGroup = (ContractGroup) request.getAttribute("activeContractGroup");
    String caseNumber =  Util.initString((String) request.getAttribute("caseNumber"), "");
    String caseName   =  Util.initString((String)request.getParameter("caseName"), "");
    String casePK     =  Util.initString((String)request.getParameter("casePK"), null);
    String clientDetailPK = Util.initString((String)request.getParameter("clientDetailPK"), null);

    long activeContractGroupPK   = 0;
    String groupName           = "";
    String groupNumber         = "";

    if (activeContractGroup != null)
    {
        activeContractGroupPK = Util.initLong(activeContractGroup, "contractGroupPK", 0L);
        groupName = (String) Util.initObject(activeContractGroup, "groupName", "");
        groupNumber = (String) Util.initObject(activeContractGroup, "contractGroupNumber", "");
    }

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Summary</title>
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

    function saveGroupEntry()
    {
        if (textElementIsEmpty(f.groupNumber))
        {
            alert("Please Enter Group Number");
            return;
        }

        if (textElementIsEmpty(f.groupName))
        {
            alert("Please Enter Group Name");
            return;
        }

        disableActionButtons();

        sendTransactionAction("CaseDetailTran", "saveGroupEntry", "_self");
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("CaseDetailTran", "showGroupDetail", "_self");
    }

    function resetFormValues()
    {
        f.groupName.value = "";
        f.groupNumber.value = "";
    }


    function deleteGroupEntry()
    {
        if (textElementIsEmpty(f.groupNumber))
        {
            alert("Please Select Group to Delete");
            return;
        }

        disableActionButtons();

        sendTransactionAction("CaseDetailTran", "deleteGroupEntry", "_self");
    }

    function closeWindow()
    {
        sendTransactionAction("CaseDetailTran", "showCaseMain", "main");
        window.close();
    }

    function disableActionButtons()
    {
        document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

        document.all.btnAdd.style.backgroundColor = "#99BBBB";
        document.all.btnSave.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";
        document.all.btnClose.style.backgroundColor = "#99BBBB";

        document.all.btnAdd.disabled = true;
        document.all.btnSave.disabled = true;
        document.all.btnCancel.disabled = true;
        document.all.btnDelete.disabled = true;
        document.all.btnClose.disabled = true;
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
        <td width="50%" height="100%">
          <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="6">
            <tr>
                <td align="left" nowrap>
                    Group Summary
                </td>
                <td align="right" nowrap>
                    Case Number:&nbsp;
                </td>
                <td align="left" nowrap>
                    <input disabled type="text" name="caseNumber" size="15" value="<%= caseNumber %>">
                </td>
                <td align="right" nowrap>
                    Case Name:&nbsp;
                </td>
                <td align="left" nowrap>
                    <input disabled type="text" name="caseName" size="30" maxLength="60" value="<%= caseName %>">
                </td>
            </tr>
          </table>
        </td>
    </tr>
    <tr>
     <td width="50%" height="100%">
      <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="6">
        <tr>
            <td align="left" nowrap>
                Group Number:&nbsp;
                <input type="text" name="groupNumber" size="15" value="<%= groupNumber %>">
            </td>
            <td align="left" nowrap>
                Group Name:&nbsp;
                <input type="text" name="groupName" size="15" value="<%= groupName %>">
            </td>
            <td align="right" nowrap>
                &nbsp;
            </td>
        </tr>

        <tr>
            <td nowrap align="left">
                <input id="btnAdd" type="button" value="   Add   " onClick="resetFormValues()">
                <input id="btnSave" type="button" value="   Save  " onClick="saveGroupEntry()">
                <input id="btnCancel" type="button" value="  Cancel " onClick="resetFormValues()">
                <input id="btnDelete" type="button" value="  Delete " onClick="deleteGroupEntry()">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <td align="left" colspan="3">
                &nbsp;
            </td>
             <td id="trxMessage">
                &nbsp;
             </td>
              <td align="right" nowrap>
                &nbsp;
    <%--        </td>--%>
                <input id="btnClose" type="button" value=" Close " onClick="closeWindow()">
              </td>
            </td>
        </tr>
      </table>
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

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="GroupSummaryTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="contractGroupPK" value="<%= activeContractGroupPK %>">
<input type="hidden" name="caseNumber" value="<%= caseNumber %>">
<input type="hidden" name="caseName" value="<%= caseName %>">
<input type="hidden" name="casePK" value="<%= casePK %>">
<input type="hidden" name="clientDetailPK" value="<%= clientDetailPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
