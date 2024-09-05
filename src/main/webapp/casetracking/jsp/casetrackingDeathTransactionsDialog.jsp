<%@ page import="edit.common.vo.CodeTableVO,
                 codetable.dm.dao.CodeTableDAO,
                 edit.common.CodeTableWrapper,
                 client.ClientDetail,
                 edit.common.EDITDate,
                 fission.utility.*"%>
<!--
 * User: sprasad
 * Date: Mar 18, 2005
 * Time: 5:07:16 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

//    CodeTableVO[] deathVO = new CodeTableDAO().findByTableNameAndCode("DEATHTRXTYPE", "DE");
//    CodeTableVO[] deathPendingVO = new CodeTableDAO().findByTableNameAndCode("TRXTYPE", "DP");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] states = codeTableWrapper.getCodeTableEntries("STATE");
    CodeTableVO[] deathTrxTypes = codeTableWrapper.getCodeTableEntries("DEATHTRXTYPE");
    CodeTableVO[] conditions = codeTableWrapper.getCodeTableEntries("CONDITION");

    Long clientDetailPK = (Long) session.getAttribute("casetracking.selectedClientDetailPK");
    ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);
    String transactionType = "";
    String condition = "";

    String stateOfDeath = (String) Util.initObject(clientDetail, "stateOfDeathCT", "");
    String residentStateAtDeath = (String) Util.initObject(clientDetail, "residentStateAtDeathCT", "");
    EDITDate dateOfDeath = (EDITDate) Util.initObject(clientDetail, "dateOfDeath", null);
    EDITDate proofOfDeathReceivedDate = (EDITDate) Util.initObject(clientDetail, "proofOfDeathReceivedDate", null);
    EDITDate notificationReceivedDate = (EDITDate) Util.initObject(clientDetail, "notificationReceivedDate", null);
    EDITDate currentDate = new EDITDate();

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking - Death Transactions</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>


<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    var currentDate = "<%= currentDate %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();

        // Initialize scroll tables
        initScrollTable(document.getElementById("ContractClientDoubleTableModelScrollTable"));
    }

    /*
     * Moves rows from one table to other in DoubleTableModel.
     */
    function moveRows(tableId)
    {
        move(tableId, "CaseTrackingTran", "updateDeathTranDoubleTable", "_self");
    }

    function saveDeathTransactionDialog()
    {
       try
       {
            if (verifyIfRowsExist("ToTableSummary") == false)
            {
                alert('No clients selected');
                return;
            }

            if (selectElementIsEmpty(f.transactionType))
            {
                alert("Please Select Transaction Type");
                return;
            }

           if (f.conditionCT.selectedIndex == 0)
           {
               f.conditionCT.value = "";
           }

            //the dateOfDeath returned will always be valid, otherwise an error in the format was detected
            if (f.transactionType.value == "DE")
            {
                if (textElementIsEmpty(f.dateOfDeath))
                {
                    alert("Please Enter Date of Death");
                    return;
                }

                if (f.dateOfDeath.value >= currentDate)
                {
                    alert("DateOfDeath must be less the CurrentDate");
                    return;
                }
            }
       }
       catch (e)
       {
            alert(e);
            return;
       }

        disableActionButtons();

        sendTransactionAction("CaseTrackingTran", "saveDeathTransactionPage", "_self");
    }

    function closeAndShowClientPage()
    {
        sendTransactionAction("CaseTrackingTran", "showCasetrackingClient", "main");

        window.close();
    }

        /**
         * While a tranaction is being saved, we want to disable all buttons to prevent double-saves.
         */
        function disableActionButtons()
        {
            document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

            document.all.btnSave.style.backgroundColor = "#99BBBB";
            document.all.btnCancel.style.backgroundColor = "#99BBBB";
            document.all.btnClose.style.backgroundColor = "#99BBBB";

            document.all.btnSave.disabled = true;
            document.all.btnCancel.disabled = true;
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
        <td colspan="6">
            <jsp:include page="/common/jsp/widget/doubleTableWidget.jsp" flush="true">
                <jsp:param name="tableId" value="ContractClientDoubleTableModel"/>
                <jsp:param name="multipleRowSelect" value="true"/>
            </jsp:include>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Transaction Type:&nbsp;
        </td>
        <td align="left" nowrap>
            <select name="transactionType">
                <option value="null">Please Select</option>
                <%
                    for(int i = 0; i < deathTrxTypes.length; i++)
                    {
                        String codeDesc    = deathTrxTypes[i].getCodeDesc();
                        String code        = deathTrxTypes[i].getCode();

                        if (transactionType.equalsIgnoreCase(code)) {

                            out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                        else  {

                            out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
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
    <tr>
        <td align="right" nowrap>
            Date of Death:&nbsp;
        </td>
        <td align="left" nowrap>
           <input type="text" name="dateOfDeath" value="<%= dateOfDeath == null? "": DateTimeUtil.formatYYYYMMDDToMMDDYYYY(dateOfDeath.getFormattedDate()) %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.dateOfDeath', f.dateOfDeath.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
        <td align="right" nowrap>
            State of Death:&nbsp;
        </td>
        <td align="left" nowrap>
            <select name="stateOfDeathCT">
                <option value="null">Please Select</option>
                <%
                    for(int i = 0; i < states.length; i++)
                    {
                        String codeDesc    = states[i].getCodeDesc();
                        String code        = states[i].getCode();

                        if (stateOfDeath.equalsIgnoreCase(code)) {

                            out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                        else  {

                            out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                    }
                %>
            </select>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Notification Received Date:&nbsp;
        </td>
        <td align="left" nowrap>
           <input type="text" name="notificationReceivedDate" value="<%= notificationReceivedDate == null? "": DateTimeUtil.formatYYYYMMDDToMMDDYYYY(notificationReceivedDate.getFormattedDate()) %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.notificationReceivedDate', f.notificationReceivedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>

        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
        <td align="right" nowrap>
            Resident State at Death:&nbsp;
        </td>
        <td align="left" nowrap>
            <select name="residentStateAtDeathCT">
              <option value="null">Please Select</option>
              <%
                for(int i = 0; i < states.length; i++)
                {
                    String codeDesc    = states[i].getCodeDesc();
                    String code        = states[i].getCode();

                    if (residentStateAtDeath.equalsIgnoreCase(code))
                    {
                        out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
              %>
            </select>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Proof of Death Received Date:&nbsp;
        </td>
        <td align="left" nowrap>
           <input type="text" name="proofOfDeathReceivedDate" value="<%= proofOfDeathReceivedDate == null? "": DateTimeUtil.formatYYYYMMDDToMMDDYYYY(proofOfDeathReceivedDate.getFormattedDate()) %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.proofOfDeathReceivedDate', f.proofOfDeathReceivedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>

        <td align="right" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Condition:&nbsp;
        </td>
        <td align="left" nowrap>
            <select name="conditionCT">
                <option>Please Select</option>
                <%
                    for(int i = 0; i < conditions.length; i++)
                    {
                        String codeDesc    = conditions[i].getCodeDesc();
                        String code        = conditions[i].getCode();

                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                %>
            </select>
        </td>
    </tr>
<%--    END Form Content --%>

    <tr>
        <td align="left" colspan="3">
            &nbsp;
        </td>
        <td id="trxMessage">
            &nbsp;
        </td>
        <td align="left" colspan="1">
            &nbsp;
        </td>        <td align="right">
            <input id="btnSave" type="button" value=" Save " onClick="saveDeathTransactionDialog()">
            <input id="btnCancel" type="button" value=" Cancel " onClick="resetForm()">
            <input id="btnClose" type="button" value=" Close " onClick="closeAndShowClientPage()">
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<%--<input type="hidden" name="dateOfDeath">--%>
<%--<input type="hidden" name="notificationReceivedDate">--%>
<%--<input type="hidden" name="proofOfDeathReceivedDate">--%>
<input type="hidden" name="clientDetailPK" value="<%= clientDetailPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>