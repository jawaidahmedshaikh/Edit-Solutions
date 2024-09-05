<%@ page import="edit.common.vo.*,
                 edit.common.*,
                 fission.utility.*,
                 engine.business.*,
                 engine.component.*,
                 java.util.List,
                 java.util.ArrayList,
                 engine.*"%>
 <%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 3, 2004
  Time: 12:46:16 PM
  To change this template use File | Settings | File Templates.
--%>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("companyStructureVOs");

    ProductStructureVO activeProductStructureVO = (ProductStructureVO) request.getAttribute("cloneFromCompanyStructureVO");

    String activeCompanyStructurePK = null;
    String activeCompany = null;
    String activeMarketingPackage = null;
    String activeGroupProduct = null;
    String activeArea = null;
    String activeBusinessContract = null;
    String activeProductStructure = null;
    if (productStructureVOs != null)
    {
        activeCompanyStructurePK = activeProductStructureVO.getProductStructurePK() + "";
        Company company = Company.findByPK(new Long(activeProductStructureVO.getCompanyFK()));

        activeCompany = company.getCompanyName();
        activeMarketingPackage = activeProductStructureVO.getMarketingPackageName();
        activeGroupProduct = activeProductStructureVO.getGroupProductName();
        activeArea = activeProductStructureVO.getAreaName();
        activeBusinessContract = activeProductStructureVO.getBusinessContractName();
        activeProductStructure = activeCompany + ", " + activeMarketingPackage + "," +
                                  activeGroupProduct + ", " + activeArea + ", " + activeBusinessContract;
    }

    activeCompanyStructurePK = Util.initString(activeCompanyStructurePK, "0");

    RequirementVO[] attachedRequirementVOs = (RequirementVO[]) request.getAttribute("attachedRequirementVOs");
    RequirementVO[] requirementVOs = (RequirementVO[]) request.getAttribute("requirementVOs");

    List attachedRequirementPKs = loadAttachedRequirementPKs(attachedRequirementVOs);
%>
<%!
    private List loadAttachedRequirementPKs(RequirementVO[] attachedRequirementVOs) {

        List attachedRequirementPKs = new ArrayList();

        if (attachedRequirementVOs != null) {

            for (int i = 0; i < attachedRequirementVOs.length; i++) {

                attachedRequirementPKs.add(attachedRequirementVOs[i].getRequirementPK() + "");
            }
        }

        return attachedRequirementPKs;
    }
%>
<%-- ****************************** End Java Code ****************************** --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>
            Area Relations
        </title>
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

        f.cloneButton.disabled = true;
    }

    function enableDisableRequirementSelection()
    {
        var select = window.event.srcElement;

        if (select.selectedIndex == 0) // If "Please Select"
        {
            f.attachButton.disabled = false;
            f.detachButton.disabled = false;
            f.cloneButton.disabled = true;
        }
        else
        {
            f.attachButton.disabled = true;
            f.detachButton.disabled = true;
            f.cloneButton.disabled = false;
        }
    }

    /**
     * Associates the selected Requirements to the selected ProductStructure.
     */
    function attachCompanyAndRequirement()
    {
        if (f.activeCompanyStructurePK.value == "0") {

            alert("Please Select Company Structure");
        }
        else {

            f.selectedRequirementPKs.value = getSelectedRequirementPKs();

            if (f.selectedRequirementPKs.value == "") {

                alert("Please Select Requirement(s)");
            }
            else {

                sendTransactionAction("QuoteDetailTran", "attachCompanyAndRequirement", "_self");
            }
        }
    }

    /**
     * Removes the association between the selected AreaValues and the selected ProductStructure.
     */
    function detachCompanyAndRequirement(){

        if (f.activeCompanyStructurePK.value == "0")
        {
            alert("Please Select Company Structure");
        }
        else {

            f.selectedRequirementPKs.value = getSelectedRequirementPKs();

            if (f.selectedRequirementPKs.value == "") {

                alert("Please Select Requirement(s)");
            }
            else {

                sendTransactionAction("QuoteDetailTran", "detachCompanyAndRequirement", "_self");
            }
        }
    }

    function getSelectedRequirementPKs()
    {
        var requirementsTable = document.all.requirementsTable;

        var selectedRequirementPKs = "";

        for (var i = 0; i < requirementsTable.rows.length; i++)
        {
            if (requirementsTable.rows[i].isSelected == "true")
            {
                selectedRequirementPKs += requirementsTable.rows[i].requirementPK + ",";
            }
        }

        return selectedRequirementPKs;
    }

    /**
     * Clones the active ProductStructures AreaValues to the selected ProductStructure.
     */
    function cloneRequirements()
    {
        if (f.cloneToCompanyStructurePK.value == "null")
        {
            alert("Clone To Company Structure Required");
        }
        else
        {
            sendTransactionAction("QuoteDetailTran", "cloneRequirements", "_self");
        }
    }

    /**
     * Closes this dialog.
     */
    function cancelDialog()
    {
        window.close();
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

    </head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%--Header--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" nowrap>Active Company Structure:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled type="text" size="50" maxlength="50" name="activeProductStructure" value="<%= activeProductStructure %>">
    </td>
    <td align="right" nowrap>Clone To:&nbsp;</td>
    <td align="left" nowrap>
      <select name="cloneToCompanyStructurePK" onChange="enableDisableRequirementSelection()">
        <option name="id" value="0">Please Select</option>
        <%
            if (productStructureVOs != null)
            {
                for(int i = 0; i < productStructureVOs.length; i++)
                {
                    String cloneProductStructurePK = productStructureVOs[i].getProductStructurePK() + "";
                    Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));

                    String cloneCompany = company.getCompanyName();
                    String cloneMarketingPackage = productStructureVOs[i].getMarketingPackageName();
                    String cloneGroupProduct = productStructureVOs[i].getGroupProductName();
                    String cloneArea = productStructureVOs[i].getAreaName();
                    String cloneBusinessContract = productStructureVOs[i].getBusinessContractName();
                    String cloneProductStructure = cloneCompany + ", " + cloneMarketingPackage + "," +
                                              cloneGroupProduct + ", " + cloneArea + ", " + cloneBusinessContract;

                    out.println("<option name=\"id\" value=\"" + cloneProductStructurePK + "\">" + cloneProductStructure + "</option>");
                }
            }
        %>
      </select>
    </td>
  </tr>
</table>

<br>

<%-- ****************************** BEGIN Requirements Summary ****************************** --%>
<span class="tableHeading">Requirements Summary</span>

<%--Header--%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr class="heading">
    <td width="50%">Requirement Id</td>
    <td width="50%">Requirement Description</td>
  </tr>
</table>
<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:41%; top:0; left:0;">
  <table id="requirementsTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <%
    if (requirementVOs != null)
    {
        for (int i = 0; i < requirementVOs.length; i++)
        {
            String currentRequirementPK = requirementVOs[i].getRequirementPK() + "";
            String currentRequirementId = Util.initString(requirementVOs[i].getRequirementId(), "");
            String currentDescription = Util.initString(requirementVOs[i].getRequirementDescription(), "");

            String trClass = null;
            boolean isSelected = false;
            boolean isAssociated = false;

            if (attachedRequirementPKs.contains(currentRequirementPK)) {

                trClass = "associated";
                isAssociated = true;
            }
            else {

                trClass = "default";
            }
  %>
    <tr height="15" class="<%= trClass %>" requirementPK="<%= currentRequirementPK %>"
        isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>"
        onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(true)">
      <td width="50%">
        <%= currentRequirementId %>
      </td>
      <td width="50%">
        <%= currentDescription %>
      </td>
    </tr>
  <%
        }
    }
  %>
    <tr class="filler">
      <td colspan="2">
          &nbsp;
      </td>
    </tr>
  </table>
</span>

<br><br>

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" name="cloneButton" value=" Clone " onClick="cloneRequirements()">
            <input type="button" name="attachButton" value="Attach" onClick="attachCompanyAndRequirement()">
            <input type="button" name="detachButton" value="Detach" onClick="detachCompanyAndRequirement()">
            <input type="button" name="cancelButton" value="Cancel" onClick="cancelDialog()">
        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">

<input type="hidden" name="activeCompanyStructurePK" value="<%= activeCompanyStructurePK %>">
<input type="hidden" name="cloneToProductStructurePK" value="null">
<input type="hidden" name="selectedRequirementPKs" value="">
<%-- ****************************** END Hidden Variables ****************************** --%>
</form>
</body>

</html>