<%@ page import="edit.common.vo.ClientDetailVO,
                 edit.common.vo.ClientAddressVO,
                 java.lang.reflect.Array,
                 fission.utility.Util"%><!--
 * User: sprasad
 * Date: Jan 12, 2005
 * Time: 2:58:38 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    String filteredFundPK = (String) request.getAttribute("filteredFundPK");
    String fundNumber = (String) request.getAttribute("fundNumber");
    String fundName = (String) request.getAttribute("fundName");
    String feeDescriptionPK = (String) request.getAttribute("feeDescriptionPK");
    String feeTypeCT = (String) request.getAttribute("feeTypeCT");
    String pricingTypeCT = (String) request.getAttribute("pricingTypeCT");
    String feeRedemptionStatus = (String) request.getAttribute("feeRedemptionStatus");

    ClientDetailVO clientDetailVO = (ClientDetailVO) request.getAttribute("clientDetailVO");

    long clientDetailPK = 0;

    if (clientDetailVO != null)
    {
        clientDetailPK = clientDetailVO.getClientDetailPK();
    }

    ClientDetailVO[] clientDetailVOs = (ClientDetailVO[]) request.getAttribute("clientDetailVOs");

    if (clientDetailVOs == null && clientDetailVO != null)
    {
        clientDetailVOs = (ClientDetailVO[]) Array.newInstance(ClientDetailVO.class, 1);
        Array.set(clientDetailVOs, 0, clientDetailVO);
    }
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Add Client For Fee Description</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

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

    /**
     * Dummary method to show how one might show the detail of a selected row in the summary table.
     */
    function showClientDetail()
    {
        var selectedRowId = getSelectedRowId("clientSummary");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Value Is Empty");
        }
        else
        {
            f.clientDetailPK.value = selectedRowId;

            // sendTransactionAction("FundTran", "showClientDetail", "_self");
        }
    }

    function searchClients()
    {
        if (f.name.value.length > 0 && f.taxId.value.length > 0)
        {
            alert("Enter only one parameter");
        }

        if (f.name.value.length > 0)
        {
            sendTransactionAction("FundTran", "searchClientsByName", "_self");
        }
        else if (f.taxId.value.length > 0)
        {
            sendTransactionAction("FundTran", "searchClientsByTaxId", "_self");
        }
        else
        {
            alert("Invalid search parameters");
        }
    }

    function selectClientAndCloseClientDialog()
    {
        if (f.clientDetailPK.value == "0")
        {
            alert("Pl. Select the client");
        }
        else
        {
            sendTransactionAction("FundTran", "getClientDetailPK", "feeDescriptionDialog");
            window.close();
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
        <td align="right" nowrap>
            Tax Identification Number:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="taxId" size="15" maxlength="20">
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
            Name:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="name" size="15" maxlength="20">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" colspan="3" nowrap>
            &nbsp;
        </td>
        <td align="right">
            <input type="button" value=" Enter  " onClick="searchClients()">
        </td>
    </tr>
<%--    END Form Content --%>

</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Summary Area ****************************** --%>
<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="20%" nowrap>
            Name
        </td>
        <td width="20%" nowrap>
            Tax Id
        </td>
        <td width="20%" nowrap>
            Address Line 1
        </td>
        <td width="20%" nowrap>
            City
        </td>
        <td width="20%" nowrap>
            State
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:60%; top:0; left:0;">
    <table id="clientSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (clientDetailVOs != null) // Test for the existence of the target VOs.
    {
        ClientAddressVO clientAddressVO = null;

        client.business.Lookup clientLookup = new client.component.LookupComponent();

        String clientName = "";
        String taxId = "";
        String addressLine1 = "";
        String city = "";
        String state = "";

        for (int i = 0; i < clientDetailVOs.length; i++) // Loop through the target VOs.
        {
            long currentClientDetailPK = clientDetailVOs[i].getClientDetailPK();

            if (clientDetailVOs[i].getLastName() != null)
            {
                clientName = clientDetailVOs[i].getLastName() + ", " + Util.initString(clientDetailVOs[i].getFirstName(), "");
            }
            else if (clientDetailVOs[i].getCorporateName() != null)
            {
                clientName = clientDetailVOs[i].getCorporateName();
            }

            taxId = clientDetailVOs[i].getTaxIdentification();

            clientAddressVO = clientLookup.findClientAddessBy_AddressType(currentClientDetailPK, "PrimaryAddress");

            if (clientAddressVO != null)
            {
                addressLine1 = clientAddressVO.getAddressLine1();

                city = clientAddressVO.getCity();

                state = clientAddressVO.getStateCT();
            }

            boolean isSelected = false;

            boolean isAssociated = false; // (e.g. CompanyStructure to Foo associations)

            String className = null;

            if (currentClientDetailPK == clientDetailPK)
            {
                isSelected = true;

                className = "highlighted";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= currentClientDetailPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showClientDetail()">
            <td width="20%" nowrap>
                <%= clientName %>
            </td>
            <td width="20%" nowrap>
                <%= taxId %>
            </td>
            <td width="20%" nowrap>
                <%= addressLine1 %>
            </td>
            <td width="20%" nowrap>
                <%= city %>
            </td>
            <td width="20%" nowrap>
                <%= state %>
            </td>
        </tr>
<%
            clientName = "";
            taxId = "";
            addressLine1 = "";
            city = "";
            state = "";
        }// end for
    } // end if
%>
        <tr class="filler">
            <td colspan="5">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" value=" Enter  " onClick="selectClientAndCloseClientDialog()">
            <input type="button" value=" Close  " onClick="window.close()">
        </td>
    </tr>
</table>
<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="clientDetailPK" value="<%= clientDetailPK %>">
<input type="hidden" name="filteredFundPK" value="<%= filteredFundPK %>">
<input type="hidden" name="fundNumber" value="<%= fundNumber %>">
<input type="hidden" name="fundName" value="<%= fundName %>">
<input type="hidden" name="feeDescriptionPK" value="<%= feeDescriptionPK %>">
<input type="hidden" name="feeTypeCT" value="<%= feeTypeCT %>">
<input type="hidden" name="pricingTypeCT" value="<%= pricingTypeCT %>">
<input type="hidden" name="feeRedemptionStatus" value="<%= feeRedemptionStatus %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>