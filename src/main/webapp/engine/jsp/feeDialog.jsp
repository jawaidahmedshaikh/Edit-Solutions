<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="edit.common.vo.FeeVO,
                 edit.common.vo.CodeTableVO,
                 edit.common.vo.FeeDescriptionVO,
                 edit.common.vo.*,
                 edit.common.*,
                 fission.utility.*"%>
 <%--
  User: sprasad
  Date: Jan 10, 2005
  Time: 3:08:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
    String filteredFundPK = (String) request.getAttribute("filteredFundPK");
    String fundNumber = (String) request.getAttribute("fundNumber");
    String fundName = (String) request.getAttribute("fundName");
    String filterPeriod = (String) request.getAttribute("filterPeriod");
    String filterDateType = (String) request.getAttribute("filterDateType");
    String fromDate = (String) request.getAttribute("fromDate");
    String toDate = (String) request.getAttribute("toDate");
    String fromAmount = (String) request.getAttribute("fromAmount");
    String toAmount = (String) request.getAttribute("toAmount");
    String filterTransaction = (String) request.getAttribute("filterTransaction");
    String sortByDateType = (String) request.getAttribute("sortByDateType");
    String sortOrder = (String) request.getAttribute("sortOrder");
    FeeDescriptionVO[] feeDescriptionVOs = (FeeDescriptionVO[]) request.getAttribute("feeDescriptionVOs");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    CodeTableVO[] feeTrxTypeCTs = codeTableWrapper.getCodeTableEntries("FEETRXTYPE");
    CodeTableVO[] feeTypeCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("FEETYPE");
    CodeTableVO[] toFromVOs = CodeTableWrapper.getSingleton().getCodeTableEntries("TOFROM");

    ChargeCodeVO[] chargeCodeVOs = (ChargeCodeVO[]) request.getAttribute("chargeCodeVOs");

    FeeVO feeVO = (FeeVO) request.getAttribute("feeVO");

    long feePK = 0;
    long feeDescriptionPK = 0;
    String effectiveMonth = "";
    String effectiveDay = "";
    String effectiveYear = "";
    String processDateToDisplay = "";
    String redemptionDateToDisplay = "";
    String releaseDateToDisplay = "";
    String originalProcDateToDisplay = "";
    String releaseInd = "";
    String accountingPendingStatus = "";
    String statusCT = "";
    String trxTypeCT = "";
    String feeTypeCT = "";
    String feeAmount = "";
    String toFromInd = "";
    String policyNumber = "";
    String correspondence = "";
    String operator = "";
    String maintDateTime = "";
    String accountingPeriod = "";
    boolean feeCorrespondenceExists = false;
    String selectedChargeCode = "";
    String selectedChargeCodeFK = "";

    EDITDate processDate = null;
    EDITDate redemptionDate = null;
    EDITDate releaseDate = null;
    EDITDate originalProcDate = null;


    engine.business.Lookup lookup = new engine.component.LookupComponent();
    FeeDescriptionVO feeDescriptionVO = null;

    if (feeVO != null)
    {
        feePK = feeVO.getFeePK();

        if (feeVO.getEffectiveDate() != null)
        {
            EDITDate effectiveDate = new EDITDate(feeVO.getEffectiveDate());

            effectiveMonth = effectiveDate.getFormattedMonth();
            effectiveDay = effectiveDate.getFormattedDay();
            effectiveYear = effectiveDate.getFormattedYear();
        }

        if (feeVO.getProcessDateTime() != null)
        {
            EDITDateTime processDateTime = new EDITDateTime(feeVO.getProcessDateTime());

            processDate = processDateTime.getEDITDate();

            processDateToDisplay = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(processDate.getFormattedDate());
        }

        if (feeVO.getRedemptionDate() != null)
        {
            redemptionDate = new EDITDate(feeVO.getRedemptionDate());

            redemptionDateToDisplay = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(redemptionDate.getFormattedDate());
        }

        if (feeVO.getReleaseDate() != null)
        {
            releaseDate = new EDITDate(feeVO.getReleaseDate());

            releaseDateToDisplay = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(releaseDate.getFormattedDate());
        }

        if (feeVO.getOriginalProcessDate() != null)
        {
            originalProcDate = new EDITDate(feeVO.getOriginalProcessDate());

            originalProcDateToDisplay = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(originalProcDate.getFormattedDate());
        }

        releaseInd = feeVO.getReleaseInd();
        if ( releaseInd != null)
        {
            if (releaseInd.equalsIgnoreCase("Y"))
            {
                releaseInd = "checked";
            }
            else
            {
                releaseInd = "unchecked";
            }
        }
        trxTypeCT = feeVO.getTransactionTypeCT();
        feeDescriptionPK = feeVO.getFeeDescriptionFK();
        if (feeDescriptionPK != 0)
        {
            feeDescriptionVO = lookup.findFeeDescriptionBy_FeeDescriptionPK(feeDescriptionPK);
            if (feeDescriptionVO != null) feeTypeCT = feeDescriptionVO.getFeeTypeCT();
        }

        feeAmount = Util.initString(feeVO.getTrxAmount()+"","0.0");
        toFromInd = feeVO.getToFromInd();
        policyNumber = Util.initString(feeVO.getContractNumber(), "");
        operator = Util.initString(feeVO.getOperator(), "");
        maintDateTime = Util.initString(feeVO.getMaintDateTime(), "");
        accountingPeriod = Util.initString(feeVO.getAccountingPeriod(), "");

        accountingPendingStatus = feeVO.getAccountingPendingStatus();
        statusCT = feeVO.getStatusCT();

        if (feeVO != null)
        {
            long chargeCodeFK = feeVO.getChargeCodeFK();
            if (chargeCodeFK != 0)
            {
                for (int i = 0; i < chargeCodeVOs.length; i++)
                {
                    ChargeCodeVO chargeCodeVO = chargeCodeVOs[i];
                    if (chargeCodeFK == chargeCodeVO.getChargeCodePK())
                    {
                        selectedChargeCode = chargeCodeVO.getChargeCode();
                        selectedChargeCodeFK = chargeCodeVO.getChargeCodePK()+"";
                        break;
                    }
                }
            }
        }

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        FeeCorrespondenceVO feeCorrespondenceVO = engineLookup.findFeeCorrespondenceBy_FeePK(feePK);
        if (feeCorrespondenceVO != null)
        {
            feeCorrespondenceExists = true;
        }
    }
%>
<%!
    long getFeeDesciptionPKIfFeeTypeExistsInSelectedFilteredFundFeeTypes(FeeDescriptionVO[] feeDescriptionVOs, String feeTypeCT)
    {
        long feeDescriptionPK = 0;

        if (feeDescriptionVOs == null || feeTypeCT == null)
        {
            return feeDescriptionPK;
        }

        for (int i = 0; i < feeDescriptionVOs.length; i++)
        {
            if (feeDescriptionVOs[i].getFeeTypeCT().equalsIgnoreCase(feeTypeCT))
            {
                feeDescriptionPK = feeDescriptionVOs[i].getFeeDescriptionPK();
                break;
            }
        }

        return feeDescriptionPK;
    }
%>

<%-- ****************************** End Java Code ****************************** --%>

<html>

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>

<script>

    var f = null;
    var responseMessage = "<%= responseMessage %>";
    var feeCorrespondenceExists = "<%= feeCorrespondenceExists %>";
    var shouldShowLockAlert = false;
    var isFeeReleased = "<%= releaseInd %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        if (isFeeReleased == "checked")
        {
            shouldShowLockAlert = true;
        }

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ((elementType == "text" || elementType == "select-one" || elementType == "checkbox") && (shouldShowLockAlert == true))
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        if (feeCorrespondenceExists == "true")
        {
            f.correspondence.checked = true;
        }
        else
        {
            f.correspondence.checked = false;
        }

        /* if(!valueIsEmpty(feeDescriptionMessage))
        {
            alert(feeDescriptionMessage);
            window.close();
        } */

        checkForResponseMessage(responseMessage);

        formatCurrency();

        // Initialize scroll tables
        initScrollTable(document.getElementById("FeeSummaryTableModelScrollTable"));
    }

    function showLockAlert()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Fee cannot be edited.");

            return false;
        }
    }

	function onTableRowSingleClick(tableId)
    {
		sendTransactionAction("FundTran", "showFeeDialog", "_self");
	}

    function checkForResponseMessage(responseMessage)
    {
        if (!valueIsEmpty(responseMessage))
        {
            alert(responseMessage);
        }
    }

    /**
     * Summary method to show how one might show the detail of a selected row in the summary table.
     */
    function showFeeDetail()
    {
        var selectedRowId = getSelectedRowId("feeSummary");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Value Is Empty");
        }
        else
        {
            f.feePK.value = selectedRowId;

            sendTransactionAction("FundTran", "showFeeDialog", "_self");
        }
    }

    function addFee()
    {
        sendTransactionAction("FundTran", "addFee", "_self");
    }

    function saveFee()
    {
        var isValid = doFormValidation();

        if (isValid)
        {
            var pricingTypeCT = f.feeTypeCT.options[f.feeTypeCT.selectedIndex].pricingTypeCT;

            // to make the sure the release check box is checked when
            // pricingTypeCT is 'Automated' for selected feeTypeCT.
            if (pricingTypeCT == "Automated")
            {
                f.releaseInd.checked = true;
            }

            setReleaseStatus();
            sendTransactionAction("FundTran", "saveFee", "_self");
        }
    }

    function cancelFee()
    {
        sendTransactionAction("FundTran", "cancelFee", "_self");
    }

    function deleteFee()
    {
        var selectedRowId = getSelectedRowId("feeSummary");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Please Select the Fee to Delete");
        }
        else
        {
            sendTransactionAction("FundTran", "deleteFee", "_self");
        }
    }

    function setChargeCode()
    {

        var selected =  f.chargeCodeSelect.options.selectedIndex;

        var chargeCodeSelected = f.chargeCodeSelect.options[selected].value;

        f.chargeCodeFK.value = chargeCodeSelected;
    }

    function setFeeDescriptionPKAndVerifyReleaseStatus()
    {
        f.feeDescriptionPK.value = f.feeTypeCT.options[f.feeTypeCT.selectedIndex].feeDescriptionPK;

        var pricingTypeCT = f.feeTypeCT.options[f.feeTypeCT.selectedIndex].pricingTypeCT;

        if (!valueIsEmpty(pricingTypeCT))
        {
            if (pricingTypeCT == "Automated")
            {
                f.releaseInd.checked = true;
            }
            else
            {
                f.releaseInd.checked = false;
            }
        }
    }

    function setReleaseStatus()
    {
        if (f.releaseInd.checked == true)
        {
            f.releaseIndStatus.value = "Y";
        }
        else
        {
            f.releaseIndStatus.value = "N";
        }
    }

    function doFormValidation()
    {
         var isValid = true;
         var inputFieldName;
         var fieldType;

         if (textElementIsEmpty(f.effectiveMonth) ||
             textElementIsEmpty(f.effectiveDay) ||
             textElementIsEmpty(f.effectiveYear))
         {
            isValid = false;
            inputFieldName = "Effective Date";
            fieldType = " Enter ";
         }
         else if (selectElementIsEmpty(f.transactionTypeCT))
         {
            isValid = false;
            inputFieldName = "Transaction Type";
            fieldType = " Select ";
         }
         else if (selectElementIsEmpty(f.feeTypeCT))
         {
            isValid = false;
            inputFieldName = "Fee Type";
            fieldType = " Select ";
         }
         else if (selectElementIsEmpty(f.chargeCodeSelect) &&
                  f.chargeCodeSelect.disabled == false)
         {
                isValid = false;
                inputFieldName = "Charge Code";
                fieldType = " Select ";
         }
         else if (valueIsZero(f.feeAmount.value))
         {
            isValid = false;
            inputFieldName = "Fee Amount"
            fieldType = " Enter ";
         }
         else if (selectElementIsEmpty(f.toFromInd))
         {
            isValid = false;
            inputFieldName = "To/From"
            fieldType = " Select ";
         }
         if (!isValid)
         {
            var alertString = "Please" + fieldType + inputFieldName;
            alert(alertString);
         }

         return isValid;
    }

    function showCorrespondenceDialog()
    {
        var width = .70 * screen.width;
        var height = .50 * screen.height;

        var selectedRowId = getSelectedRowId("feeSummary");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Please Select the Fee");
        }
        else
        {
            var pricingTypeCT = f.feeTypeCT.options[f.feeTypeCT.selectedIndex].pricingTypeCT;

            if (pricingTypeCT == "HedgeFund")
            {
                openDialog("correspondenceDialog","top=0,left=0,resizable=no",width,height);
                sendTransactionAction("FundTran", "showCorrespondenceDialog", "correspondenceDialog");
            }
            else
            {
                alert("Only [PricingType = 'HedgeFund'] has Correspondence Records");
            }
        }
    }

    function showFeeFilter()
    {
        var width = .55 * screen.width;
        var height = .35 * screen.height;

        openDialog("feeFilterDialog","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("FundTran", "showFeeFilterDialog", "feeFilterDialog");
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

<head>
<title>Filtered Fund Fees</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" class="borderbottom" nowrap>Fund #:&nbsp;</td>
        <td align="left" class="borderbottom" nowrap><%= fundNumber %>&nbsp;</td>
        <td align="right" class="borderbottom" nowrap>Fund Name:&nbsp;</td>
        <td align="left" class="borderbottom" colspan="3" nowrap><%= fundName %>&nbsp;</td>
    </tr>
    <tr>
        <td align="right" nowrap>Effective Date:&nbsp;</td>
        <td align="left" nowrap>
            <input name="effectiveMonth" type="text" size="2" maxlength="2" value="<%= effectiveMonth %>"> /
            <input name="effectiveDay" type="text" size="2" maxlength="2" value="<%= effectiveDay %>"> /
            <input name="effectiveYear" type="text" size="4" maxlength="4" value="<%= effectiveYear %>">
        </td>
        <td align="right" nowrap>Process Date:&nbsp;</td>
        <td align="left" nowrap>
            <input disabled type="text" name="processDate" size="15" maxlength="20" value="<%= processDateToDisplay %>">
        </td>
        <td align="right" nowrap>Redemption Date:&nbsp;</td>
        <td align="left" nowrap>
            <input disabled type="text" name="redemptionDate" size="15" maxlength="20" value="<%= redemptionDateToDisplay %>">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>Release Date:&nbsp;</td>
        <td align="left" nowrap>
            <input disabled type="text" name="releaseDate" size="15" maxlength="20" value="<%= releaseDateToDisplay %>">
        </td>
        <td align="right" nowrap>Original Process Date:&nbsp;</td>
        <td align="left" nowrap>
            <input disabled type="text" name="originalProcDate" size="15" maxlength="20" value="<%= originalProcDateToDisplay %>">
        </td>
        <td align="right" nowrap>Acctg Period (yyyy/mm):&nbsp;</td>
        <td align="left" nowrap>
          <input type="text" size="7" maxlength="7" name="accountingPeriod" value="<%= accountingPeriod %>">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>Release:&nbsp;</td>
        <td align="left" nowrap>
            <input type="checkbox" name="releaseInd" <%= releaseInd %> onClick="setReleaseStatus();">
        </td>
        <td align="right" nowrap>Transaction Type:&nbsp;</td>
        <td align="left" nowrap>
            <select name="transactionTypeCT">
                <option value="null">Please Select</option>
                <%
                if (feeTrxTypeCTs != null)
                {
                    for (int i = 0; i < feeTrxTypeCTs.length; i++)
                    {
                        String feeTrxTypeCTCode = feeTrxTypeCTs[i].getCode();
                        String feeTrxTypeCTDescription = feeTrxTypeCTs[i].getCodeDesc();

                        if (feeTrxTypeCTCode.equals(trxTypeCT))
                        {
                            out.println("<option selected name=\"id\" value=\"" + feeTrxTypeCTCode + "\">" + feeTrxTypeCTDescription + "</option>");
                        }
                        else
                        {
                            out.println("<option name=\"id\" value=\"" + feeTrxTypeCTCode + "\">" + feeTrxTypeCTDescription + "</option>");
                        }
                    }
                }
            %>
            </select>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>Fee Type:&nbsp;</td>
        <td align="left" nowrap>
            <select name="feeTypeCT" onChange="setFeeDescriptionPKAndVerifyReleaseStatus()">
                <option value="null">Please Select</option>
                <%
                if (feeTypeCTs != null)
                {
                    long curFeeDescriptionPK = 0;
                    String curPricingTypeCT = "";
                    for (int i = 0; i < feeTypeCTs.length; i++)
                    {
                        String feeTypeCTCode = feeTypeCTs[i].getCode();
                        String feeTypeCTDescription = feeTypeCTs[i].getCodeDesc();
                        curFeeDescriptionPK = getFeeDesciptionPKIfFeeTypeExistsInSelectedFilteredFundFeeTypes(feeDescriptionVOs, feeTypeCTCode);
                        // if curFeeDescriptionPK is not zero it does exist in feeDescription table
                        if (curFeeDescriptionPK != 0)
                        {
                            FeeDescriptionVO curFeeDescriptionVO = lookup.findFeeDescriptionBy_FeeDescriptionPK(curFeeDescriptionPK);
                            if (curFeeDescriptionVO != null) curPricingTypeCT = curFeeDescriptionVO.getPricingTypeCT();
                            if (feeTypeCTCode.equals(feeTypeCT))
                            {
                                out.println("<option selected name=\"id\" feeDescriptionPK=\"" + curFeeDescriptionPK + "\" pricingTypeCT=\"" + curPricingTypeCT + "\" value=\""  + feeTypeCTCode + "\">" + feeTypeCTDescription + "</option>");
                            }
                            else
                            {
                                out.println("<option name=\"id\" feeDescriptionPK=\"" + curFeeDescriptionPK + "\" pricingTypeCT=\"" + curPricingTypeCT + "\" value=\"" + feeTypeCTCode + "\">" + feeTypeCTDescription + "</option>");
                            }
                        }
                    }
                }
            %>
            </select>
        </td>
        <td align="right" nowrap>Charge Code:&nbsp;</td>
        <td align="left" nowrap>
            <%
            // SELECT FOR SETING CHARGE CODES
            if (chargeCodeVOs == null || chargeCodeVOs.length == 0)
            {
                out.println("<select name=\"chargeCodeSelect\" disabled >");
                out.println("<option value=\"0\">None Available</option>");
                out.println("</select>");
            }
            else
            {
                out.println("<select name=\"chargeCodeSelect\" onChange=\"setChargeCode()\">");
                out.println("<option value=\"0\">Please Select</option>");
                for (int i = 0; i < chargeCodeVOs.length; i++)
                {
                    String curChargeCode = chargeCodeVOs[i].getChargeCode();
                    String curChargeCodeFK = chargeCodeVOs[i].getChargeCodePK() + "";

                    if (curChargeCode.equals(selectedChargeCode))
                    {
                        out.println("<option selected value=\""  + curChargeCodeFK + "\">" + curChargeCode + "</option>");
                    }
                    else
                    {
                        out.println("<option value=\""  + curChargeCodeFK + "\">" + curChargeCode + "</option>");
                    }
                }
                out.println("</select>");
            }

            %>

        </td>
    </tr>
    <tr>
        <td align="right" nowrap>Fee Amount:&nbsp;</td>
        <td align="left" nowrap>
            <input type="text" name="feeAmount" size="15" maxlength="20" value="<%= feeAmount %>" CURRENCY>
        </td>
        <td align="right" nowrap>To/From:&nbsp;</td>
        <td align="left" nowrap>
            <select name="toFromInd">
                <option value="null">Please Select</option>
                <%
                if (toFromVOs != null)
                {
                    for (int i = 0; i < toFromVOs.length; i++)
                    {
                        String code = toFromVOs[i].getCode();
                        String codeDesc = toFromVOs[i].getCodeDesc();

                        if (code.equals(toFromInd))
                        {
                            out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                        else
                        {
                            out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                    }
                }
            %>
            </select>
        </td>
        <td align="right" nowrap>Policy #:&nbsp;</td>
        <td align="left" nowrap>
            <input type="text" name="policyNumber" size="15" maxlength="20" value="<%= policyNumber %>">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>&nbsp;</td>
        <td align="left" nowrap>&nbsp;</td>
        <td align="right" nowrap>
            <input disabled type="checkbox" name="correspondence" <%= correspondence %> onClick="setCorrespondenceStatus();">
        </td>
        <td align="left" colspan="3" nowrap>
            <a href="javascript:showCorrespondenceDialog()">Correspondence</a>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>Reversal:&nbsp;</td>
        <td align="left" nowrap>
            <input type="text" name="reversal" size="2" maxlength="1">
        </td>
        <td align="right" nowrap>Operator:&nbsp;</td>
        <td align="left" nowrap>
            <input type="text" name="operator" size="15" maxlength="20" value="<%= operator %>" disabled>
        </td>
        <td align="right" nowrap>DateTime:&nbsp;</td>
        <td align="left" nowrap>
            <input type="text" name="dateTime" size="15" maxlength="20" value="<%= maintDateTime %>" disabled>
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
        <td align="left" width="33%">
            <input type="button" value=" Add  " onClick="addFee()">
            <input type="button" value=" Save " onClick="saveFee()">
            <input type="button" value="Cancel" onClick="cancelFee()">
            <input type="button" value="Delete" onClick="deleteFee()">
        </td>
        <td width="33%">
            &nbsp;
        </td>
        <td align="right" width="33%">
            <input type="button" value="Filter" onClick="showFeeFilter()">
        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="FeeSummaryTableModel"/>
    <jsp:param name="tableHeight" value="45"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>


<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" value=" Close  " onClick="window.close()">
        </td>
    </tr>
</table>

<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="feePK" value="<%= feePK %>">
<input type="hidden" name="filteredFundPK" value="<%= filteredFundPK %>">
<input type="hidden" name="fundNumber" value="<%= fundNumber %>">
<input type="hidden" name="fundName" value="<%= fundName %>">
<input type="hidden" name="feeDescriptionPK" value="<%= feeDescriptionPK %>">
<input type="hidden" name="chargeCode" value="<%= selectedChargeCode %>">
<input type="hidden" name="chargeCodeFK" value="<%= selectedChargeCodeFK %>">
<input type="hidden" name="processDate" value="<%= processDate %>">
<input type="hidden" name="redemptionDate" value="<%= redemptionDate %>">
<input type="hidden" name="releaseDate" value="<%= releaseDate %>">
<input type="hidden" name="originalProcDate" value="<%= originalProcDate %>">
<input type="hidden" name="releaseIndStatus">
<input type="hidden" name="accountingPendingStatus" value="<%= accountingPendingStatus %>">
<input type="hidden" name="statusCT" value="<%= statusCT %>">

<input type="hidden" name="filterPeriod" value="<%= filterPeriod %>">
<input type="hidden" name="filterDateType" value="<%= filterDateType %>">
<input type="hidden" name="fromDate" value="<%= fromDate %>">
<input type="hidden" name="toDate" value="<%= toDate %>">
<input type="hidden" name="fromAmount" value="<%= fromAmount %>">
<input type="hidden" name="toAmount" value="<%= toAmount %>">
<input type="hidden" name="filterTransaction" value="<%= filterTransaction %>">
<input type="hidden" name="sortByDateType" value="<%= sortByDateType %>">
<input type="hidden" name="sortOrder" value="<%= sortOrder %>">

<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
