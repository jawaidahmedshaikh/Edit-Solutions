<%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 12, 2004
  Time: 1:56:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="fission.utility.*,
                 edit.common.vo.*,
                 edit.common.*,
                 reinsurance.business.*,
                 reinsurance.component.*,
                 java.math.*,
                 edit.portal.common.session.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    UserSession userSession = (UserSession) session.getAttribute("userSession");

    String responseMessage = (String) request.getAttribute("responseMessage");

    TreatyGroupVO[] treatyGroupVOs = (TreatyGroupVO[]) request.getAttribute("treatyGroupVOs");

    TreatyVO[] treatyVOs = (TreatyVO[]) request.getAttribute("treatyVOs");

    TreatyVO treatyVO = (TreatyVO) request.getAttribute("treatyVO");

    String startDateMonth = "";
    String startDateDay = "";
    String startDateYear = "";
    String stopDateMonth = "";
    String stopDateDay = "";
    String stopDateYear = "";
    String lastCheckDateMonth = "";
    String lastCheckDateDay = "";
    String lastCheckDateYear = "";
    String settlementPeriod = "";
    String retentionAmount = "";
    String poolPercentage = "";
    String paymentModeCT = "";
    String calculationModeCT = "";
    String reinsurerBalance = "";
    long reinsurerFK = 0;
    long treatyGroupFK = 0;
    long treatyPK = 0;

    if (treatyVO != null)
    {
        if (treatyVO.getStartDate() != null)
        {
            EDITDate startDate = new EDITDate(treatyVO.getStartDate());
            startDateMonth = startDate.getFormattedMonth();
            startDateDay = startDate.getFormattedDay();
            startDateYear = startDate.getFormattedYear();
        }
        
        if (treatyVO.getStopDate() != null)
        {
            EDITDate stopDate = new EDITDate(treatyVO.getStopDate());
            stopDateMonth = stopDate.getFormattedMonth();
            stopDateDay = stopDate.getFormattedDay();
            stopDateYear = stopDate.getFormattedYear();
        }

        if (treatyVO.getLastCheckDate() != null)
        {
            EDITDate lastCheckDate = new EDITDate(treatyVO.getLastCheckDate());
            lastCheckDateMonth = lastCheckDate.getFormattedMonth();
            lastCheckDateDay = lastCheckDate.getFormattedDay();
            lastCheckDateYear = lastCheckDate.getFormattedYear();
        }

        settlementPeriod = Util.initString(String.valueOf(treatyVO.getSettlementPeriod()), "");
        retentionAmount = Util.initString(String.valueOf(treatyVO.getRetentionAmount()), "");
        poolPercentage = Util.initString(String.valueOf(treatyVO.getPoolPercentage()), "");
        paymentModeCT = Util.initString(String.valueOf(treatyVO.getPaymentModeCT()), "");
        calculationModeCT = Util.initString(String.valueOf(treatyVO.getCalculationModeCT()), "");
        reinsurerFK = treatyVO.getReinsurerFK();
        treatyGroupFK = treatyVO.getTreatyGroupFK();
        treatyPK = treatyVO.getTreatyPK();
        reinsurerBalance = String.valueOf(treatyVO.getReinsurerBalance());
    }

    CodeTableVO[] paymentModeCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("PAYMENTMODE");

    CodeTableVO[] calculationModeCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("PAYMENTMODE");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Reinsurer Treaty</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;

    var shouldShowLockAlert = true;

    var pageIsLocked = <%= userSession.getReinsurerIsLocked()%>;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        f.startDateMonth.focus();

        setLockState();

        checkForResponseMessage();

        formatCurrency();
    }

    /**
     *  Shows the details of the selected Treaty.
     */
    function showTreatyDetails()
    {
        var selectedRowId = getSelectedRowId("treatySummary");

        f.treatyPK.value = selectedRowId;

        sendTransactionAction("ReinsuranceTran","showTreatyDetails", "contentIFrame");
    }

    /**
     *  Determines if the page should be locked.
     */
    function setLockState()
    {
        shouldShowLockAlert = !pageIsLocked;

        for (var i = 0; i < f.elements.length; i++) {

            elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "select-one") &&
                 (shouldShowLockAlert == true) ) {

                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }
    }

    /**
     * Certain events can be assigned to this function to test for lock status. E.g - a mouse click.
     */
    function showLockAlert()
    {
        if (shouldShowLockAlert == true) {

            alert("The Page Can Not Be Edited");

            return false;
        }
    }

    /**
     * Prepares for the entry of a new Treaty.
     */
    function addTreaty()
    {
        sendTransactionAction("ReinsuranceTran","addTreaty", "contentIFrame");
    }

    /**
     * Saves the current treaty to the Treaty summary.
     */
    function saveTreatyToSummary()
    {
        if (selectElementIsEmpty(f.treatyGroupFK))
        {
            alert("Treaty Group # Required");
        }
        else if (textElementIsEmpty(f.startDateMonth))
        {
            alert("Start Date Month Required");
        }
        else if (textElementIsEmpty(f.startDateDay))
        {
            alert("Start Date Day Required");
        }
        else if (textElementIsEmpty(f.startDateYear))
        {
            alert("Start Date Year Required");
        }
        else if(textElementIsEmpty(f.poolPercentage))
        {
            alert("Pool Percentage Required");
        }
        else
        {
            sendTransactionAction("ReinsuranceTran","saveTreatyToSummary", "contentIFrame");
        }
    }

    /**
     * Opens the Treaty Group Dialog for group number management.
     */
    function showReinsurerTreatyGroupDialog()
    {
        var width = getScreenWidth() * 0.50;

        var height = getScreenHeight() * 0.50;

        openDialog("reinsuranceAddDialog", null, width, height);

        sendTransactionAction("ReinsuranceTran", "showReinsurerTreatyGroupDialog", "reinsuranceAddDialog");
    }

    /**
     * Cancel current Treaty edits.
     */
    function cancelTreatyEdits()
    {
        sendTransactionAction("ReinsuranceTran","cancelTreatyEdits", "contentIFrame");
    }

    /**
     * Deletes the selected Treaty.
     */
    function deleteTreaty()
    {
        sendTransactionAction("ReinsuranceTran","deleteTreaty", "contentIFrame");
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
    <tr>
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>

<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap width="25%">
            Treaty Group #:
        </td>
        <td align="left" nowrap width="25%">
            <select name="treatyGroupFK">
                <option name="id" value="0">Please Select</option>
<%
    if (treatyGroupVOs != null)
    {
        for (int i = 0; i < treatyGroupVOs.length; i++)
        {
            String currentTreatyGroupPK = String.valueOf(treatyGroupVOs[i].getTreatyGroupPK());

            String currentGroupNumber = treatyGroupVOs[i].getTreatyGroupNumber();

            if (currentTreatyGroupPK.equals(String.valueOf(treatyGroupFK)))
            {
                out.println("<option selected name=\"id\" value=\"" + currentTreatyGroupPK + "\">" + currentGroupNumber + "</option>");
            }
            else
            {
                out.println("<option name=\"id\" value=\"" + currentTreatyGroupPK + "\">" + currentGroupNumber + "</option>");
            }
        }
    }
%>
            </select>

            <input type="button" value="Groups" onClick="showReinsurerTreatyGroupDialog()">
        </td>
        <td align="right" nowrap width="25%">
            &nbsp;
        </td>
        <td align="left" nowrap width="25%">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td colspan="4">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Start Date:
        </td>
        <td align="left" nowrap>
            <input type="text" name="startDateMonth" value="<%= startDateMonth %>" size="2" maxlength="2"> /
            <input type="text" name="startDateDay" value="<%= startDateDay %>" size="2" maxlength="2"> /
            <input type="text" name="startDateYear" value="<%= startDateYear %>" size="4" maxlength="4">
        </td>
        <td align="right" nowrap>
            Stop Date:
        </td>
        <td align="left" nowrap>
            <input type="text" name="stopDateMonth" value="<%= stopDateMonth %>" size="2" maxlength="2"> /
            <input type="text" name="stopDateDay" value="<%= stopDateDay %>" size="2" maxlength="2"> /
            <input type="text" name="stopDateYear" value="<%= stopDateYear %>" size="4" maxlength="4">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Settlement Period:
        </td>
        <td align="left" nowrap>
            <input type="text" name="settlementPeriod" value="<%= settlementPeriod %>" size="5" maxlength="5">
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
            Retention Amount:
        </td>
        <td align="left" nowrap>
            <input type="text" name="retentionAmount" value="<%= retentionAmount %>" size="5" CURRENCY>
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
            Pool %:
        </td>
        <td align="left" nowrap>
            <input type="text" name="poolPercentage" value="<%= poolPercentage %>" size="5">
        </td>
        <td align="right" nowrap>
            Reinsurance Balance:
        </td>
        <td align="left" nowrap>
            <input type="text" name="reinsurerBalance" value="<%= reinsurerBalance %>" size="5" CONTENTEDITABLE="false" CURRENCY>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Payment Mode:
        </td>
        <td align="left" nowrap>
            <select name="paymentModeCT">
                <option name="id" value="0">Please Select</option>
<%
    if (paymentModeCTs != null)
    {
        for (int i = 0; i < paymentModeCTs.length; i++)
        {
            String currentCode = paymentModeCTs[i].getCode();

            String currentCodeDesc = paymentModeCTs[i].getCodeDesc();

            if (currentCode.equals(paymentModeCT))
            {
                out.println("<option selected name=\"id\" value=\"" + currentCode + "\">" + currentCodeDesc + "</option>");
            }
            else
            {
                out.println("<option name=\"id\" value=\"" + currentCode + "\">" + currentCodeDesc + "</option>");
            }
        }
    }
%>
            </select>
        </td>
        <td align="right" nowrap>
            Last Check Date:
        </td>
        <td align="left" nowrap>
            <input type="text" name="lastCheckDateMonth" value="<%= lastCheckDateMonth %>" size="2" maxlength="2" CONTENTEDITABLE="false"> /
            <input type="text" name="lastCheckDateDay" value="<%= lastCheckDateDay %>" size="2" maxlength="2" CONTENTEDITABLE="false"> /
            <input type="text" name="lastCheckDateYear" value="<%= lastCheckDateYear %>"  size="4" maxlength="4" CONTENTEDITABLE="false">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Calc Mode:
        </td>
        <td align="left" nowrap>
            <select name="calculationModeCT">
                <option name="id" value="0">Please Select</option>
<%
    if (calculationModeCTs != null)
    {
        for (int i = 0; i < calculationModeCTs.length; i++)
        {
            String currentCode = calculationModeCTs[i].getCode();

            String currentCodeDesc = calculationModeCTs[i].getCodeDesc();

            if (currentCode.equals(calculationModeCT))
            {
                out.println("<option selected name=\"id\" value=\"" + currentCode + "\">" + currentCodeDesc + "</option>");
            }
            else
            {
                out.println("<option name=\"id\" value=\"" + currentCode + "\">" + currentCodeDesc + "</option>");
            }
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
<%--    END Form Content --%>

    <tr>
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
        <td align="left" width="33%">
            <input type="button" value="  Add " onClick="addTreaty()">
            <input type="button" value=" Save " onClick="saveTreatyToSummary()">
            <input type="button" value="Cancel" onClick="cancelTreatyEdits()">
            <input type="button" value="Delete" onClick="deleteTreaty()">
        </td>
        <td width="33%">
            <span class="tableHeading">Treaty Summary</span>
        </td>
        <td align="right" width="33%">
            &nbsp;
        </td>
    </tr>
</table>

<%--Header--%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="25%" nowrap>
            Treaty Group #
        </td>
        <td width="25%" nowrap>
            Start Date
        </td>
        <td width="25%" nowrap>
            Stop Date
        </td>
        <td width="25%" nowrap>
            Pool Percentage
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:40%; top:0; left:0;">
    <table id="treatySummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (treatyVOs != null) // Test for the existence of the target VOs.
    {
        Reinsurance reinsurance = new ReinsuranceComponent();

        for (int i = 0; i < treatyVOs.length; i++) // Loop through the target VOs.
        {
            TreatyVO currentTreatyVO = treatyVOs[i];

            if (currentTreatyVO.getVoShouldBeDeleted() ||
                currentTreatyVO.getStatus() != null)
            {
                continue;
            }

            long currentTreatyPK = currentTreatyVO.getTreatyPK();

            long currentTreatyGroupPK = currentTreatyVO.getTreatyGroupFK();

            TreatyGroupVO currentTreatyGroupVO = reinsurance.findTreatyGroupBy_TreatyGroupPK(currentTreatyGroupPK);

            String currentTreatyGroupNumber = Util.initString(currentTreatyGroupVO.getTreatyGroupNumber(), "&nbsp;");

            String currentStartDate = Util.initString(treatyVOs[i].getStartDate(), null);

            currentStartDate = (currentStartDate != null)?DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentStartDate):"&nbsp;";

            String currentStopDate = Util.initString(treatyVOs[i].getStopDate(), null);

            currentStopDate = (currentStopDate != null)?DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentStopDate):"&nbsp;";

            BigDecimal currentPoolPercentage = treatyVOs[i].getPoolPercentage();

            boolean isSelected = false;
            
            boolean isAssociated = false;

            String className = null;

            if (currentTreatyPK == treatyPK)
            {
                isSelected = true;
                
                className = "highlighted";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= currentTreatyPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showTreatyDetails()">
            <td width="25%" nowrap>
                <%= currentTreatyGroupNumber %>
            </td>
            <td width="25%" nowrap>
                <%= currentStartDate %>
            </td>
            <td width="25%" nowrap>
                <%= currentStopDate %>
            </td>
            <td width="25%" nowrap>
                <%= currentPoolPercentage %>
            </td>

        </tr>
<%
        }// end for
    } // end if
%>      
        <tr class="filler">
            <td colspan="4">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="pageName" value="reinsurerTreaty">
<input type="hidden" name="reinsurerFK" value="<%= reinsurerFK %>">
<input type="hidden" name="treatyPK" value="<%= treatyPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>