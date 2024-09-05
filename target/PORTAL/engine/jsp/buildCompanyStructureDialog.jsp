<%@ page import="edit.common.vo.*,
                 edit.common.*,
                 fission.utility.*,
                 engine.business.*,
                 engine.component.*,
                 engine.*"%>
 <%--
  Created by IntelliJ IDEA.
  User: dlataille
  Date: June 30, 2005
  Time: 10:00:00 AM
  To change this template use File | Settings | File Templates.
--%>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("productStructureVOs");

    ProductStructureVO activeProductStructureVO = (ProductStructureVO) request.getAttribute("cloneFromProductStructureVO");

    String activeProductStructurePK = null;
    String activeCompany = null;
    String activeMarketingPackage = null;
    String activeGroupProduct = null;
    String activeArea = null;
    String activeBusinessContract = null;
    String activeProductStructure = null;
    if (productStructureVOs != null)
    {
        activeProductStructurePK = activeProductStructureVO.getProductStructurePK() + "";
        Company company = Company.findByPK(new Long(activeProductStructureVO.getCompanyFK()));

        activeCompany = company.getCompanyName();
        activeMarketingPackage = activeProductStructureVO.getMarketingPackageName();
        activeGroupProduct = activeProductStructureVO.getGroupProductName();
        activeArea = activeProductStructureVO.getAreaName();
        activeBusinessContract = activeProductStructureVO.getBusinessContractName();
        activeProductStructure = activeCompany + ", " + activeMarketingPackage + "," +
                                  activeGroupProduct + ", " + activeArea + ", " + activeBusinessContract;
    }

    activeProductStructurePK = Util.initString(activeProductStructurePK, "0");
%>
<%-- ****************************** End Java Code ****************************** --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>
            Build Company Structure
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

    function enableDisableClone()
    {
        var select = window.event.srcElement;

        if (select.selectedIndex == 0) // If "Please Select"
        {
            f.cloneButton.disabled = true;
        }
        else
        {
            f.cloneButton.disabled = false;
        }
    }

    /**
     * Clones the active CompanyStructures AreaValues to the selected CompanyStructure.
     */
    function cloneAllFilteredValues()
    {
        if (f.cloneToProductStructurePK.value == "null")
        {
            alert("Clone To Company Structure Required");
        }
        else
        {
            sendTransactionAction("ProductStructureTran", "cloneAllFilteredValues", "_self");
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
      <select name="cloneToProductStructurePK" onChange="enableDisableClone()">
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
  <tr>
    <td align="left" colspan="4">&nbsp;</td>
  </tr>
  <tr>
    <td align="right">Clone: &nbsp;</td>
    <td align="left" colspan="3">
    Rules
    </td>
  </tr>
  <tr>
    <td align="right">       &nbsp;</td>
    <td align="left" colspan="3">
    CodeTable
    </td>
  </tr>
  <tr>
    <td align="right">       &nbsp;</td>
    <td align="left" colspan="3">
    AreaTable (Except "State Approval")
    </td>
  </tr>
  <tr>
    <td align="right">       &nbsp;</td>
    <td align="left" colspan="3">
    Requirements
    </td>
  </tr>
  <tr>
    <td align="left" colspan="4">&nbsp;</td>
  </tr>
  <tr>
    <td align="left" colspan="4">
    By Selecting "Clone", the above relationships for the "Active" company structure will be duplicated for the "CloneTo" company structure.
    </td>
  </tr>
</table>

<br><br>

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" name="cloneButton" value=" Clone " onClick="cloneAllFilteredValues()">
            <input type="button" name="cancelButton" value="Cancel" onClick="cancelDialog()">
        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">

<input type="hidden" name="activeProductStructurePK" value="<%= activeProductStructurePK %>">
<input type="hidden" name="cloneToProductStructurePK" value="null">
<%-- ****************************** END Hidden Variables ****************************** --%>
</form>
</body>

</html>