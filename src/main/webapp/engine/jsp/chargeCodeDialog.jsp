<%@ page import="fission.utility.Util,
                 edit.common.vo.*,
                 edit.common.*"%><!--
 * User: sprasad
 * Date: Mar 2, 2005
 * Time: 5:10:18 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    ChargeCodeVO chargeCodeVO = (ChargeCodeVO) request.getAttribute("chargeCodeVO");

    long chargeCodePK = 0;
    String chargeCode = "";
    EDITBigDecimal accumPremium = new EDITBigDecimal("0.00");
    String clientFundNumber = "";
    String newIssuesInd = "";

    if (chargeCodeVO != null)
    {
        chargeCodePK = chargeCodeVO.getChargeCodePK();

        chargeCode   = chargeCodeVO.getChargeCode();

        clientFundNumber =  Util.initString(chargeCodeVO.getClientFundNumber(), "");
        newIssuesInd = Util.initString(chargeCodeVO.getNewIssuesIndicatorCT(), "");

    }

    ChargeCodeVO[] chargeCodeVOs = (ChargeCodeVO[]) request.getAttribute("chargeCodeVOs");

    String filteredFundFK = (String) request.getAttribute("filteredFundFK");
    String fundNumber = (String) request.getAttribute("fundNumber");
    String fundName = (String) request.getAttribute("fundName");

    CodeTableVO[] yesNo = CodeTableWrapper.getSingleton().getCodeTableEntries("YESNO");

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Charge Code</title>
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
        f = document.theForm;

        checkForResponseMessage();
    }

    function showTableSummaryDetail()
    {

        var selectedRowId = getSelectedRowId("tableSummary");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Value Is Empty");
        }
        else
        {
            f.chargeCodePK.value = selectedRowId;

            sendTransactionAction("FundTran", "showChargeCodeDialog", "chargeCodeDialog");
        }
    }

    function addChargeCode()
    {
        sendTransactionAction("FundTran", "addChargeCode", "chargeCodeDialog");
    }

    function checkIfOKToUpdate()
    {

        var chgCode = f.chargeCode.value;

        if (chgCode == "")
        {
            alert("Please enter a Charge Code");
            return false;
        }

        return true;

    }

    function saveChargeCode()
    {
        if (checkIfOKToUpdate())
        {
            sendTransactionAction("FundTran", "saveChargeCode", "chargeCodeDialog");
        }
    }

    function cancelChargeCode()
    {
        sendTransactionAction("FundTran", "cancelChargeCode", "chargeCodeDialog");
    }

    function deleteChargeCode()
    {
        sendTransactionAction("FundTran", "deleteChargeCode", "chargeCodeDialog");
    }



    // MOVE TO COMMON JS

    function isNumeric(string) { return !isNaN(Number(string)); }

    function checkNumericAndNotify(objName)
    {
    	if (isNumeric(objName.value) == false)
    	{
    	   var msg = objName.name + " must be numeric.";
    	   alert(msg);
           objName.select();
    	   objName.focus();
    	   return false;
    	}
    	else
    	{
    	   return true;
    	}
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
            Charge Code:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="chargeCode" size="7" maxlength="6" value="<%= chargeCode %>"  >
        </td>
 
        <td align="right" nowrap>
            Client Fund Number:
        </td>
        <td align="left" nowrap>
            <input type="text" name="clientFundNumber" size="15" maxlength="20" value="<%= clientFundNumber %>" >
        </td>

    </tr>
  <tr>
    <td align="right" nowrap>New Issues Indicator:&nbsp;</td>
    <td align="left" nowrap>
      <select name="newIssuesInd">
        <option>Please Select</option>
          <%

            for(int i = 0; i < yesNo.length; i++) {

                String codeTablePK = yesNo[i].getCodeTablePK() + "";
                String codeDesc    = yesNo[i].getCodeDesc();
                String code        = yesNo[i].getCode();

                if (newIssuesInd.equalsIgnoreCase(code)) {

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
            <input type="button" value=" Add  " onClick="addChargeCode()">
            <input type="button" value=" Save " onClick="saveChargeCode()">
            <input type="button" value="Cancel" onClick="cancelChargeCode()">
            <input type="button" value="Delete" onClick="deleteChargeCode()">
        </td>
        <td width="33%">
            &nbsp;
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
            Charge Code
        </td>

        <td width="34%" nowrap>
            Client Fund Number
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:60%; top:0; left:0;">
    <table id="tableSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (chargeCodeVOs != null) // Test for the existence of the target VOs.
    {
        for (int i = 0; i < chargeCodeVOs.length; i++) // Loop through the target VOs.
        {
            ChargeCodeVO currentChargeCodeVO = chargeCodeVOs[i];

            long currentChargeCodePK = currentChargeCodeVO.getChargeCodePK();

            String currentChargeCode = currentChargeCodeVO.getChargeCode();

            String currentClientFundNumber =
                    Util.initString(currentChargeCodeVO.getClientFundNumber(), "");

            boolean isSelected = false;

            boolean isAssociated = false; // (e.g. CompanyStructure to Foo associations)

            String className = null;

            if (currentChargeCodePK == chargeCodePK)
            {
                isSelected = true;

                className = "highlighted";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= currentChargeCodePK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showTableSummaryDetail()">
            <td width="33%" nowrap>
                <%= currentChargeCode %>
            </td>

            <td width="34%" nowrap>
                <%= currentClientFundNumber %>
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
<table width="100%" border="0" cellspacing="0" cellpadding="5">
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
<input type="hidden" name="chargeCodePK" value="<%= chargeCodePK %>">
<input type="hidden" name="filteredFundPK" value="<%= filteredFundFK %>">
<input type="hidden" name="filteredFundFK" value="<%= filteredFundFK %>">
<input type="hidden" name="fundNumber" value="<%= fundNumber %>">
<input type="hidden" name="fundName" value="<%= fundName %>">

<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>