<%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 15, 2004
  Time: 9:06:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="fission.utility.*,
                 edit.common.vo.*,
                 reinsurance.business.*,
                 reinsurance.component.*,
                 engine.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("productStructureVOs");

    String responseMessage = (String) request.getAttribute("responseMessage");
    
    String activeProductStructurePK = (String) request.getAttribute("activeProductStructurePK");

    TreatyGroupVO[] treatyGroupVOs = (TreatyGroupVO[]) request.getAttribute("treatyGroupVOs");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Treaty Grouping Relations</title>
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
     * Shows the set of TreatyGroups associated with the select Product Structure.
     */
    function showAttachedTreatyGroups()
    {
        var selectedRowId = getSelectedRowId("companyStructureTable");

        f.activeProductStructurePK.value = selectedRowId;

        sendTransactionAction("ReinsuranceTran", "showAttachedTreatyGroups", "main");
    }

    /**
     * Attaches the set of selected Treaty Groups to the selected Product Structure.
     */
    function attachTreatyGroupsToProductStructure()
    {
        var treatyGroupPKs = getSelectedRowIds("treatyGroupSummary");

        if (treatyGroupPKs.length == 0)
        {
            alert("Treaty Group(s) Required");
        }
        else if (valueIsEmpty(f.activeProductStructurePK.value))
        {
            alert("Company Structure Required");
        }
        else
        {
            f.treatyGroupPKs.value = treatyGroupPKs;

            sendTransactionAction("ReinsuranceTran", "attachTreatyGroupsToProductStructure", "main");
        }
    }

    /**
     * Detaches the set of selected Treaty Groups to the selected Product Structure.
     */
    function detachTreatyGroupsFromProductStructure()
    {
        var treatyGroupPKs = getSelectedRowIds("treatyGroupSummary");

        if (treatyGroupPKs.length == 0)
        {
            alert("Treaty Group(s) Required");
        }
        else if (valueIsEmpty(f.activeProductStructurePK.value))
        {
            alert("Company Structure Required");
        }
        else
        {
            f.treatyGroupPKs.value = treatyGroupPKs;

            sendTransactionAction("ReinsuranceTran", "detachTreatyGroupsFromProductStructure", "main");
        }
    }

    /**
     * Shows the dialog rendering the complete list of Treaties for the selected Treaty Group.
     */
    function showTreatyGroupListDialog()
    {
        var imgElement = window.event.srcElement;

        var tdElement = imgElement.parentElement;

        var trElement = tdElement.parentElement;

        f.activeTreatyGroupPK.value = trElement.id;

        window.event.cancelBubble = true;

        var width = getScreenWidth() * 0.75;

        var height = getScreenHeight() * 0.75;

        openDialog("treatySearchDialog", null, width, height);

        sendTransactionAction("ReinsuranceTran", "showTreatyGroupListDialog", "treatySearchDialog");
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN CompanyStructure Summary ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            &nbsp;
        </td>
        <td width="33%">
            <span class="tableHeading">Company Structure Summary</span>
        </td>
        <td align="right" width="33%">
            &nbsp;
        </td>
    </tr>
</table>
<%--Header--%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="20%">
            Company
        </td>
        <td width="20%">
            Marketing Package
        </td>
        <td width="20%">
            Group Product
        </td>
        <td width="20%">
            Area
        </td>
        <td width="20%">
            Business Contract
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:41%; top:0; left:0;">
    <table id="companyStructureTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (productStructureVOs != null)
    {
        productStructureVOs = (ProductStructureVO[]) Util.sortObjects(productStructureVOs, new String[]{"getMarketingPackageName", "getGroupProductName", "getAreaName", "getBusinessContractName"});

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            String currentProductStructurePK = productStructureVOs[i].getProductStructurePK() + "";
            Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));

            String currentCompanyName = company.getCompanyName();
            String currentMarketingPackageName = productStructureVOs[i].getMarketingPackageName();
            String groupProductName = productStructureVOs[i].getGroupProductName();
            String areaName = productStructureVOs[i].getAreaName();
            String businessContractName = productStructureVOs[i].getBusinessContractName();

            boolean isSelected = false;

            String className = null;

            if (currentProductStructurePK.equals(activeProductStructurePK))
            {
                isSelected = true;

                className = "highlighted";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= currentProductStructurePK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showAttachedTreatyGroups()">
            <td width="20%">
                <%= currentCompanyName %>
            </td>
            <td width="20%">
                <%= currentMarketingPackageName %>
            </td>
            <td width="20%">
                <%= groupProductName %>
            </td>
            <td width="20%">
                <%= areaName %>
            </td>
            <td width="20%">
                <%= businessContractName %>
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
<%-- ****************************** END CompanyStructure Summary ****************************** --%>

<br><br>

<%-- ****************************** BEGIN Grouping Summary ****************************** --%>
<%--Table Heading--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            &nbsp;
        </td>
        <td width="33%">
            <span class="tableHeading">Treaty Group Summary</span>
        </td>
        <td align="right" width="33%">
            &nbsp;
        </td>
    </tr>
</table>

<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="100%" nowrap>
            Treaty Group Number
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:41%; top:0; left:0;">
    <table id="treatyGroupSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (treatyGroupVOs != null) // Test for the existence of the target VOs.
    {
        for (int i = 0; i < treatyGroupVOs.length; i++) // Loop through the target VOs.
        {
            String currentTreatyGroupPK = String.valueOf(treatyGroupVOs[i].getTreatyGroupPK());

            String currentGroupNumber = treatyGroupVOs[i].getTreatyGroupNumber();

            boolean isSelected = false;
            
            boolean isAssociated = false;

            String className = "default";

            if (activeProductStructurePK != null)
            {
                engine.business.Lookup engineLookup = new engine.component.LookupComponent();

                ProductStructureVO productStructureVO = engineLookup.findProductStructureBy_ProductStructurePK_TreatyGroupPK(Long.parseLong(activeProductStructurePK), Long.parseLong(currentTreatyGroupPK));

                if (productStructureVO != null)
                {
                    className = "associated";

                    isAssociated = true;
                }
            }
            else
            {
                className = "default";

                isAssociated = false;
            }
%>
        <tr class="<%= className %>" id="<%= currentTreatyGroupPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(true)">
            <td>
                <img src="/PORTAL/common/images/I.gif" alt="Show Treaty Group List" onClick="showTreatyGroupListDialog()">&nbsp;<%= currentGroupNumber %>
            </td>
        </tr>
<%
        }// end for
    } // end if
%>      
        <tr class="filler">
            <td colspan="1">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
<%-- ****************************** END Grouping Summary ****************************** --%>

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right" width="100%">
            <input type="button" value=" Attach " onClick="attachTreatyGroupsToProductStructure()">
            <input type="button" value=" Detach " onClick="detachTreatyGroupsFromProductStructure()">
        </td>
    </tr>
</table>
<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="activeProductStructurePK" value="<%= activeProductStructurePK %>">
<input type="hidden" name="treatyGroupPKs">
<input type="hidden" name="activeTreatyGroupPK">

<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>