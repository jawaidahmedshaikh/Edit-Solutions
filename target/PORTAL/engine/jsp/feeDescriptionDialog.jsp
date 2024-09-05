<%@ page import="edit.common.vo.FeeDescriptionVO,
                 fission.utility.Util,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper"%>
<%--
  User: sprasad
  Date: Jan 10, 2005
  Time: 3:08:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    CodeTableVO[] feeTypeCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("FEETYPE");

    CodeTableVO[] pricingTypeCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("PRICINGTYPE");

    FeeDescriptionVO feeDescriptionVO = (FeeDescriptionVO) request.getAttribute("feeDescriptionVO");

    long feeDescriptionPK = 0;
    String feeTypeCT = "";
    String pricingTypeCT = "";
    String feeRedemption = "";
    long clientDetailPK = 0;
    String isClientAssociated = "";
    String feeRedemptionStatus = "";

    String isFromClientPage = (String) request.getAttribute("isFromClientPage");
    if (isFromClientPage != null && isFromClientPage.equalsIgnoreCase("true"))
    {
        // if page is shown after client being selected then get the values from request object
        String feeDescriptionPKStr = (String) request.getAttribute("feeDescriptionPK");
        feeDescriptionPK = Long.parseLong(feeDescriptionPKStr);
        feeTypeCT = (String) request.getAttribute("feeTypeCT");
        pricingTypeCT = (String) request.getAttribute("pricingTypeCT");
        feeRedemptionStatus = (String) request.getAttribute("feeRedemptionStatus");
        if (feeRedemptionStatus.equalsIgnoreCase("Y"))
        {
            feeRedemption = "checked";
        }
        else
        {
            feeRedemption = "unchecked";
        }
        String clientDetailPKStr = (String) request.getAttribute("clientDetailPK");
        clientDetailPK = Long.parseLong(clientDetailPKStr);
    }
    else
    {
        // if page is displayed from filtered fund summary
        if (feeDescriptionVO != null)
        {
            feeDescriptionPK = feeDescriptionVO.getFeeDescriptionPK();
            feeTypeCT = Util.initString(feeDescriptionVO.getFeeTypeCT(), "");
            pricingTypeCT = Util.initString(feeDescriptionVO.getPricingTypeCT(), "");
            feeRedemption = Util.initString(feeDescriptionVO.getFeeRedemption(), "");
            if(feeRedemption.equalsIgnoreCase("Y"))
            {
                feeRedemption = "checked";
            }
            else
            {
                feeRedemption = "unchecked";
            }
            clientDetailPK = feeDescriptionVO.getClientDetailFK();
        }
    }

    if (clientDetailPK != 0)
    {
        isClientAssociated = "checked";
    }
    else
    {
        isClientAssociated = "unchecked";
    }

    FeeDescriptionVO[] feeDescriptionVOs = (FeeDescriptionVO[]) request.getAttribute("feeDescriptionVOs");

    String filteredFundPK = (String) request.getAttribute("filteredFundPK");
    String fundNumber = (String) request.getAttribute("fundNumber");
    String fundName = (String) request.getAttribute("fundName");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Filtered Fund Fee Description</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
<style>
    .borderbottom
    {
        border-bottom-style:solid;
        border-bottom-width:1px;
        border-bottom-color:black;
    }
</style>

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.feeDescription;

        checkForResponseMessage(responseMessage);
    }

    /**
     * Summary method to show how one might show the detail of a selected row in the summary table.
     */
    function showFeeDescriptionDetail()
    {
        var selectedRowId = getSelectedRowId("feeDescriptionSummary");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Value Is Empty");
        }
        else
        {
            f.feeDescriptionPK.value = selectedRowId;

            sendTransactionAction("FundTran", "showFeeDescriptionDialog", "_self");
        }
    }

    function addFeeDescription()
    {
        sendTransactionAction("FundTran", "addFeeDescription", "_self");
    }

    function saveFeeDescription()
    {
        setFeeRedemption();

        var feeTypeCT = document.getElementById("feeTypeCT");
        var pricingTypeCT = document.getElementById("pricingTypeCT");
        var isValid = true;

        if (selectElementIsEmpty(feeTypeCT))
        {
            alert("Pl. Select Fee Type");
            isValid = false;
        }
        else if (selectElementIsEmpty(pricingTypeCT))
        {
            alert("Pl. Select Pricing Type");
            isValid = false;
        }

        if (isValid)
        {
            if (f.pricingTypeCT.options[f.pricingTypeCT.selectedIndex].value == "HedgeFund")
            {
                if (f.clientDetailPK.value == "0")
                {
                    alert("Client needs to be associated for [PricingType = 'HedgeFund']");
                }
                else
                {
                    sendTransactionAction("FundTran", "saveFeeDescription", "_self");
                }
            }
            else
            {
                sendTransactionAction("FundTran", "saveFeeDescription", "_self");
            }
        }
    }

    function cancelFeeDescription()
    {
        sendTransactionAction("FundTran", "cancelFeeDescription", "_self");
    }

    function deleteFeeDescription()
    {
        var selectedRowId = getSelectedRowId("feeDescriptionSummary");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Pl. select the Fee Description to delete");
        }
        else
        {
            sendTransactionAction("FundTran", "deleteFeeDescription", "_self");
        }
    }

    function setFeeRedemption()
    {
        if ( f.feeRedemption.checked == true)
        {
            f.feeRedemptionStatus.value = "Y";
        }
        else
        {
            f.feeRedemptionStatus.value = "N";
        }
    }

    /**
    * Displays Client Dialog
    */
    function showClientDialog()
    {
        setFeeRedemption();

        var width = .85 * screen.width;
        var height = .50 * screen.height;

        openDialog("clientAddForFeeDescription", "top=0,left=0,resizable=no", width, height);

        sendTransactionAction("FundTran", "showClientAddDialog", "clientAddForFeeDescription");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="feeDescription" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" class="borderbottom" nowrap>
            Fund #:&nbsp;
        </td>
        <td align="left" class="borderbottom" nowrap>
            <%= fundNumber %>&nbsp;
        </td>
        <td align="right" class="borderbottom" nowrap>
            Fund Name:&nbsp;
        </td>
        <td align="left" class="borderbottom" nowrap>
            <%= fundName %>&nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Fee Type:&nbsp;
        </td>
        <td align="left" nowrap>
            <select name="feeTypeCT">
                <option value="null">Please Select</option>
                <%
                if (feeTypeCTs != null)
                {
                    for (int i = 0; i < feeTypeCTs.length; i++)
                    {
                        String feeTypeCTCode = feeTypeCTs[i].getCode();
                        String feeTypeCTDescription = feeTypeCTs[i].getCodeDesc();

                        if (feeTypeCTCode.equals(feeTypeCT))
                        {
                            out.println("<option selected name=\"id\" value=\"" + feeTypeCTCode + "\">" + feeTypeCTDescription + "</option>");
                        }
                        else
                        {
                            out.println("<option name=\"id\" value=\"" + feeTypeCTCode + "\">" + feeTypeCTDescription + "</option>");
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
    <tr>
        <td align="right" nowrap>
            Pricing Type:&nbsp;
        </td>
        <td align="left" nowrap>
            <select name="pricingTypeCT">
                <option value="null">Please Select</option>
                <%
                if (pricingTypeCTs != null)
                {
                    for (int i = 0; i < pricingTypeCTs.length; i++)
                    {
                        String pricingTypeCTCode = pricingTypeCTs[i].getCode();
                        String pricingTypeCTDescription = pricingTypeCTs[i].getCodeDesc();

                        if (pricingTypeCTCode.equals(pricingTypeCT))
                        {
                            out.println("<option selected name=\"id\" value=\"" + pricingTypeCTCode + "\">" + pricingTypeCTDescription + "</option>");
                        }
                        else
                        {
                            out.println("<option name=\"id\" value=\"" + pricingTypeCTCode + "\">" + pricingTypeCTDescription + "</option>");
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
    <tr>
        <td align="right" nowrap>
            Fee Redemption:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="checkbox" name="feeRedemption" <%= feeRedemption %> onClick="setFeeRedemption();">
        </td>
        <td align="right" nowrap>
            <input disabled type="checkbox" name="isClientAssociated" <%= isClientAssociated %>>
        </td>
        <td align="left" nowrap>
            <a href="javascript:showClientDialog()">Payee</a>
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
            <input type="button" value=" Add  " onClick="addFeeDescription()">
            <input type="button" value=" Save " onClick="saveFeeDescription()">
            <input type="button" value="Cancel" onClick="cancelFeeDescription()">
            <input type="button" value="Delete" onClick="deleteFeeDescription()">
        </td>
        <td width="33%">
            <span class="tableHeading">&nbsp;</span>
        </td>
        <td align="right" width="33%">
            &nbsp;
        </td>
    </tr>
</table>

<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="33%" nowrap>
            Fee Type
        </td>
        <td width="33%" nowrap>
            Pricing Type
        </td>
        <td width="34%" nowrap>
            Fee Redemption
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:40%; top:0; left:0;">
    <table id="feeDescriptionSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (feeDescriptionVOs != null) // Test for the existence of the target VOs.
    {
        long currentFeeDescriptionPK = 0;
        String currentFeeTypeCT = "";
        String currentPricingTypeCT = "";
        String currentFeeRedemption = "";

        for (int i = 0; i < feeDescriptionVOs.length; i++) // Loop through the target VOs.
        {
            currentFeeDescriptionPK = feeDescriptionVOs[i].getFeeDescriptionPK();
            currentFeeTypeCT = feeDescriptionVOs[i].getFeeTypeCT();
            currentPricingTypeCT = feeDescriptionVOs[i].getPricingTypeCT();
            currentFeeRedemption = feeDescriptionVOs[i].getFeeRedemption();

            boolean isSelected = false;
            boolean isAssociated = false; // (e.g. CompanyStructure to Foo associations)
            String className = "";

            if (currentFeeDescriptionPK == feeDescriptionPK)
            {
                isSelected = true;
                className = "highlighted";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= currentFeeDescriptionPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showFeeDescriptionDetail()">
            <td width="33%" nowrap>
                <%= currentFeeTypeCT %>
            </td>
            <td width="33%" nowrap>
                <%= currentPricingTypeCT %>
            </td>
            <td width="34%" nowrap>
                <%= currentFeeRedemption %>
            </td>
        </tr>
<%
        }// end for
    } // end if
%>
        <tr class="filler">
            <td colspan="3">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
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
<input type="hidden" name="feeDescriptionPK" value="<%= feeDescriptionPK %>">
<input type="hidden" name="fundNumber" value="<%= fundNumber %>">
<input type="hidden" name="fundName" value="<%= fundName %>">
<input type="hidden" name="feeRedemptionStatus">
<input type="hidden" name="filteredFundPK" value="<%= filteredFundPK %>">
<input type="hidden" name="clientDetailPK" value="<%= clientDetailPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>