<!--
 * User: gfrosti
 * Date: Nov 15, 2004
 * Time: 9:32:58 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="fission.utility.*,
                 edit.common.vo.*,
                 edit.common.*,
                 java.math.*,
                 reinsurance.business.*,
                 reinsurance.component.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    ContractTreatyVO activeContractTreatyVO = (ContractTreatyVO) request.getAttribute("activeContractTreatyVO");

    String activeCasePK = (String) request.getAttribute("activeCasePK");

    String activeSegmentPK = (String) request.getAttribute("activeSegmentPK");

    String activeTreatyPK = (String) request.getAttribute("activeTreatyPK");

    String activeContractTreatyPK = (String) request.getAttribute("activeContractTreatyPK");
    
    String effectiveDateMonth = "";

    String effectiveDateDay = "";

    String effectiveDateYear = "";

    BigDecimal maxReinsuranceAmount = new BigDecimal("0.00");

    String reinsuranceIndicatorCT = "";

    String treatyTypeCT = "";

    String reinsuranceTypeCT = "";

    BigDecimal retentionAmount = new BigDecimal("0.00");

    BigDecimal poolPercentage = new BigDecimal("0.00");

    String reinsuranceClassCT = "";

    String tableRatingCT = "";

    BigDecimal flatExtra = new BigDecimal("0.00");

    int flatExtraAge = 0;

    int flatExtraDuration = 0;

    BigDecimal percentExtra = new BigDecimal("0.00");

    int percentExtraAge = 0;

    int percentExtraDuration = 0;

    String treatyOverrideInd = "N";

    String policyOverrideInd = "N";

    if (activeContractTreatyVO != null)
    {
        String effectiveDate = activeContractTreatyVO.getEffectiveDate();

        if (effectiveDate != null)
        {
            EDITDate editEffectiveDate = new EDITDate(effectiveDate);
            effectiveDateMonth = editEffectiveDate.getFormattedMonth();

            effectiveDateDay = editEffectiveDate.getFormattedDay();

            effectiveDateYear = editEffectiveDate.getFormattedYear();
        }

        maxReinsuranceAmount = activeContractTreatyVO.getMaxReinsuranceAmount();

        reinsuranceIndicatorCT = Util.initString(activeContractTreatyVO.getReinsuranceIndicatorCT(), "");

        treatyTypeCT = Util.initString(activeContractTreatyVO.getTreatyTypeCT(), "");

        retentionAmount = activeContractTreatyVO.getRetentionAmount();

        poolPercentage = activeContractTreatyVO.getPoolPercentage();

        reinsuranceTypeCT = Util.initString(activeContractTreatyVO.getReinsuranceTypeCT(), "");

        reinsuranceClassCT = Util.initString(activeContractTreatyVO.getReinsuranceClassCT(), "");

        tableRatingCT = Util.initString(activeContractTreatyVO.getTableRatingCT(), "");

        flatExtra = activeContractTreatyVO.getFlatExtra();

        flatExtraAge = activeContractTreatyVO.getFlatExtraAge();

        flatExtraDuration = activeContractTreatyVO.getFlatExtraDuration();

        percentExtra = activeContractTreatyVO.getPercentExtra();

        percentExtraAge = activeContractTreatyVO.getPercentExtraAge();

        percentExtraDuration = activeContractTreatyVO.getPercentExtraDuration();

        treatyOverrideInd = activeContractTreatyVO.getTreatyOverrideInd();

        policyOverrideInd = activeContractTreatyVO.getPolicyOverrideInd();
    }

    CodeTableVO[] reinsuranceIndicatorCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("REINSURANCEINDICATOR");

    CodeTableVO[] treatyTypeCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("TREATYTYPE");

    CodeTableVO[] reinsuranceTypeCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("REINSURANCETYPE");

    CodeTableVO[] reinsuranceClassCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("REINSURANCECLASS");

    CodeTableVO[] tableRatingCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("TABLERATING");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Treaty Group List</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    var activeContractTreatyPK = <%= activeContractTreatyPK %>

    var treatyOverrideInd = "<%= treatyOverrideInd %>"

    var policyOverrideInd = "<%= policyOverrideInd %>"

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();

        f.effectiveDateMonth.focus();

        setCheckBoxState();

        setTextfieldState();

        formatCurrency();
    }

    /**
     * When the page first loads, checkboxes are put in their proper state.
     */
    function setCheckBoxState()
    {
        if (treatyOverrideInd == "Y")
        {
            f.treatyOverrideInd.status = true;
        }
        else
        {
            f.treatyOverrideInd.status = false;
        }

        if (policyOverrideInd == "Y")
        {
            f.policyOverrideInd.status = true;
        }
        else
        {
            f.policyOverrideInd.status = false;
        }
    }

    /**
     * Depending on the override status(s), certain fields will be enabled/disabled.
     */
    function setTextfieldState()
    {
        if (f.treatyOverrideInd.status == false)
        {
            f.poolPercentage.contentEditable = false;
            f.retentionAmount.contentEditable = false;
        }
        else if (f.treatyOverrideInd.status == true)
        {
            f.poolPercentage.contentEditable = true;
            f.retentionAmount.contentEditable = true;
        }

        if (f.policyOverrideInd.status == false)
        {

        }
    }

    /**
     *  Saves the state of the current Contract Treaty.
     */
    function saveContractTreatyOverrides()
    {
        sendTransactionAction("ReinsuranceTran", "saveContractTreatyOverrides", "_self");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" height="95%" border="0" cellspacing="0" cellpadding="5">

<%--    BEGIN Form Content --%>
    <tr>
        <td width="25%" nowrap align="right">
            Effective Date:&nbsp;
        </td>
        <td align="left" nowrap width="25%">
            <input type="text" name="effectiveDateMonth" size="2" maxlength="2" value="<%= effectiveDateMonth %>"> /
            <input type="text" name="effectiveDateDay" size="2" maxlength="2" value="<%= effectiveDateDay %>"> /
            <input type="text" name="effectiveDateYear" size="4" maxlength="4" value="<%= effectiveDateYear %>">
        </td>
        <td width="25%" nowrap >
            &nbsp;
        </td>
        <td width="25%" nowrap >
            &nbsp;
        </td>
    </tr>
    <tr>
        <td nowrap align="right">
            Max Reinsurance Amt:&nbsp;
        </td>
        <td nowrap align="left">
            <input type="text" name="maxReinsuranceAmount" size="10" value="<%= maxReinsuranceAmount %>" CURRENCY>
        </td>
        <td colspan="2" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td colspan="2" nowrap width="50%">
            Treaty Info &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font face="" size="2">(Use Overrides <input type="checkbox" name="treatyOverrideInd" onClick="setTextfieldState()">)</font>
            <table width="100%" height="90%" cellspacing="0" border="0" cellpadding="5" style="border-style:solid; border-width:1; border-color:black">
                <tr>
                    <td align="right" nowrap width="50%">
                        Reinsurance Ind:&nbsp;
                    </td>
                    <td align="left" nowrap width="50%">
            <select name="reinsuranceIndicatorCT">
                <option name="id" value="">Please Select</option>
<%
    if (reinsuranceIndicatorCTs != null)
    {
        for (int i = 0; i < reinsuranceIndicatorCTs.length; i++)
        {
            String currentCode = reinsuranceIndicatorCTs[i].getCode();

            String currentCodeDesc = reinsuranceIndicatorCTs[i].getCodeDesc();

            if (currentCode.equals(reinsuranceIndicatorCT))
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
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Treaty Type:&nbsp;
                    </td>
                    <td align="left" nowrap>
            <select name="treatyTypeCT">
                <option name="id" value="">Please Select</option>
<%
    if (treatyTypeCTs != null)
    {
        for (int i = 0; i < treatyTypeCTs.length; i++)
        {
            String currentCode = treatyTypeCTs[i].getCode();

            String currentCodeDesc = treatyTypeCTs[i].getCodeDesc();

            if (currentCode.equals(treatyTypeCT))
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
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Reinsurance Type:
                    </td>
                    <td align="left" nowrap>
            <select name="reinsuranceTypeCT">
                <option name="id" value="">Please Select</option>
<%
    if (reinsuranceTypeCTs != null)
    {
        for (int i = 0; i < reinsuranceTypeCTs.length; i++)
        {
            String currentCode = reinsuranceTypeCTs[i].getCode();

            String currentCodeDesc = reinsuranceTypeCTs[i].getCodeDesc();

            if (currentCode.equals(reinsuranceTypeCT))
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
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Retention Amt:
                    </td>
                    <td align="left" nowrap>
                        <input type="text" name="retentionAmount" size="10" maxlength="10" value="<%= retentionAmount %>" CURRENCY>
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Pool Percentage:
                    </td>
                    <td align="left" nowrap>
                        <input type="text" name="poolPercentage" size="10" maxlength="10" value="<%= poolPercentage %>">
                    </td>
                </tr>
                <tr>
                    <td nowrap colspan="2">
                        <br>
                    </td>
                </tr>
                <tr>
                    <td nowrap colspan="2">
                        <br>
                    </td>
                </tr>
                <tr>
                    <td nowrap colspan="2">
                        <br><br>
                    </td>
                </tr>
                <tr class="filler">
                    <td nowrap colspan="2">
                        &nbsp;
                    </td>
                </tr>
            </table>
        </td>
        <td colspan="2" nowrap width="50%">
            Policy Info &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font face="" size="2">(Use Overrides <input type="checkbox" name="policyOverrideInd">)</font>            <table width="100%" height="90%" cellspacing="0" cellpadding="5"  border="0" style="border-style:solid; border-width:1; border-color:black">
                <tr>
                    <td align="right" nowrap width="50%">
                        Reinsurance Class:&nbsp;
                    </td>
                    <td align="left" nowrap width="50%">
            <select name="reinsuranceClassCT">
                <option name="id" value="">Please Select</option>
<%
    if (reinsuranceClassCTs != null)
    {
        for (int i = 0; i < reinsuranceClassCTs.length; i++)
        {
            String currentCode = reinsuranceClassCTs[i].getCode();

            String currentCodeDesc = reinsuranceClassCTs[i].getCodeDesc();

            if (currentCode.equals(reinsuranceClassCT))
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
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Table Rating:&nbsp;
                    </td>
                    <td align="left" nowrap>
            <select name="tableRatingCT">
                <option name="id" value="">Please Select</option>
<%
    if (tableRatingCTs != null)
    {
        for (int i = 0; i < tableRatingCTs.length; i++)
        {
            String currentCode = tableRatingCTs[i].getCode();

            String currentCodeDesc = tableRatingCTs[i].getCodeDesc();

            if (currentCode.equals(tableRatingCT))
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
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Flat Extra:
                    </td>
                    <td align="left" nowrap>
                        <input type="text" name="flatExtra" size="10" maxlength="10" value="<%= flatExtra %>" CURRENCY>
                    </td>
                </tr>

                <tr>
                    <td align="right" nowrap>
                        Flat Extra Age:
                    </td>
                    <td align="left" nowrap>
                        <input type="text" name="flatExtraAge" size="10" maxlength="10" value="<%= flatExtraAge %>">
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Flat Extra Dur:
                    </td>
                    <td align="left" nowrap>
                        <input type="text" name="flatExtraDuration" size="10" maxlength="10" value="<%= flatExtraDuration %>">
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Percent Extra:
                    </td>
                    <td align="left" nowrap>
                        <input type="text" name="percentExtra" size="10" maxlength="10" value="<%= percentExtra %>">
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Percent Extra Age:
                    </td>
                    <td align="left" nowrap>
                        <input type="text" name="percentExtraAge" size="10" maxlength="10" value="<%= percentExtraAge %>">
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Percent Extra Dur:
                    </td>
                    <td align="left" nowrap>
                        <input type="text" name="percentExtraDuration" size="10" maxlength="10" value="<%= percentExtraDuration %>">
                    </td>
                </tr>
                <tr class="filler">
                    <td nowrap colspan="2">
                        &nbsp;
                    </td>
                </tr>
            </table>
        </td>
    </tr>
<%--    END Form Content --%>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right" width="100%">
            <input type="button" value=" Save " onClick="saveContractTreatyOverrides()">
            <input type="button" value=" Cancel " onClick="closeWindow()">
        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="activeContractTreatyPK" value="<%= activeContractTreatyPK %>">
<input type="hidden" name="activeCasePK" value="<%= activeCasePK %>">
<input type="hidden" name="activeSegmentPK" value="<%= activeSegmentPK %>">
<input type="hidden" name="activeTreatyPK" value="<%= activeTreatyPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>