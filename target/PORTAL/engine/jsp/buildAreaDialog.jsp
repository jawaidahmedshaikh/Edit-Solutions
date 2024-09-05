<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="edit.common.vo.*,
                 edit.common.*,
                 fission.utility.*,
                 engine.business.*,
                 engine.component.*,
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

    AreaKeyVO[] areaKeyVOs = (AreaKeyVO[]) request.getAttribute("areaKeyVOs");
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

    function enableDisableAreaSelection()
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
     * Associates the selected AreaValues to the selected CompanyStructure.
     */
    function attachAreaValuesToProductStructure()
    {
        var areaValuePKs = getSelectedRowIds("areaValueTable");

        if (areaValuePKs.length == 0)
        {
            alert("Area Value(s) Required");
        }
        else
        {
            f.areaValuePKs.value = areaValuePKs;

            sendTransactionAction("AreaTran", "attachAreaValuesToProductStructure", "buildAreaDialog");
        }
    }

    /**
     * Removes the association between the selected AreaValues and the selected ProductStructure.
     */
    function detachAreaValuesFromProductStructure()
    {
        var areaValuePKs = getSelectedRowIds("areaValueTable");

        if (areaValuePKs.length == 0)
        {
            alert("Area Value(s) Required");
        }
        else
        {
            f.areaValuePKs.value = areaValuePKs;

            sendTransactionAction("AreaTran", "detachAreaValuesFromProductStructure", "buildAreaDialog");
        }
    }

    /**
     * Clones the active ProductStructures AreaValues to the selected ProductStructure.
     */
    function cloneAreaValues()
    {
        if (f.cloneToProductStructurePK.value == "null")
        {
            alert("Clone To Product Structure Required");
        }
        else
        {
            sendTransactionAction("AreaTran", "cloneAreaValues", "_self");
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
      <select name="cloneToProductStructurePK" onChange="enableDisableAreaSelection()">
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

<%-- ****************************** BEGIN Area Values Summary ****************************** --%>
<span class="tableHeading">Area Value Summary</span>

<%--Header--%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="17%">
            Grouping
        </td>
        <td width="17%">
            Field
        </td>
        <td width="16%">
            Effective Date
        </td>
        <td width="17%">
            Area
        </td>
        <td width="17%">
            Qualifier
        </td>
        <td width="16%">
            Value
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:80%; top:0; left:0;">
    <table id="areaValueTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (areaKeyVOs != null)
    {
        Lookup calculatorLookup = new LookupComponent();

        areaKeyVOs = (AreaKeyVO[]) Util.sortObjects(areaKeyVOs, new String[]{"getGrouping", "getField"});

        for (int i = 0; i < areaKeyVOs.length; i++)
        {
            String currentAreaKeyPK = String.valueOf(areaKeyVOs[i].getAreaKeyPK());

            String grouping = areaKeyVOs[i].getGrouping();

            String field = areaKeyVOs[i].getField();

            String effectiveDate = "&nbsp;";

            String areaValue = "&nbsp;";

            String areaCT = "&nbsp";

            String qualifierCT = "&nbsp";

            boolean isAssociated = false;

            boolean isSelected = false;

            String className = "default";

            AreaValueVO[] currentAreaValueVOs = calculatorLookup.findAreaValuesBy_AreaKeyPK(Long.parseLong(currentAreaKeyPK));

            if (currentAreaValueVOs != null)
            {
                currentAreaValueVOs = (AreaValueVO[]) Util.sortObjects(currentAreaValueVOs, new String[]{"getAreaCT", "getEffectiveDate"});

                for (int j = 0; j < currentAreaValueVOs.length; j++)
                {
                    long currentAreaValuePK = currentAreaValueVOs[j].getAreaValuePK();

                    effectiveDate = new EDITDate(currentAreaValueVOs[j].getEffectiveDate()).getFormattedDate();

                    areaValue = currentAreaValueVOs[j].getAreaValue();

                    areaCT = currentAreaValueVOs[j].getAreaCT();

                    qualifierCT = currentAreaValueVOs[j].getQualifierCT();

                    if (activeProductStructurePK != null)
                    {
                        ProductStructureVO productStructureVO = calculatorLookup.findProductStructureBy_ProductStructurePK_AreaValuePK(Long.parseLong(activeProductStructurePK), currentAreaValuePK);

                        if (productStructureVO != null)
                        {
                            isAssociated = true;

                            className = "associated";
                        }
                        else
                        {
                            isAssociated = false;

                            className = "default";
                        }
                    }

%>
        <tr class="<%= className %>" id="<%= currentAreaValuePK %>" isAssociated="<%= isAssociated %>"
            isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()"
            onClick="selectRow(true)">
            <td width="17%">
                <%= grouping %>
            </td>
            <td width="17%">
                <%= field %>
            </td>
            <td width="16%">
                <%= effectiveDate %>
            </td>
            <td width="17%">
                <%= areaCT %>
            </td>
            <td width="17%">
                <%= qualifierCT %>
            </td>
            <td width="16%">
                <%= areaValue %>
            </td>
        </tr>
<%
                } // end for
            } // end if
        }// end for
    } // end if
%>
        <tr class="filler">
            <td colspan="2">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
<%-- ****************************** END Area Values Summary ****************************** --%>

<br><br>

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" name="cloneButton" value=" Clone " onClick="cloneAreaValues()">
            <input type="button" name="attachButton" value="Attach" onClick="attachAreaValuesToProductStructure()">
            <input type="button" name="detachButton" value="Detach" onClick="detachAreaValuesFromProductStructure()">
            <input type="button" name="cancelButton" value="Cancel" onClick="cancelDialog()">
        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">

<input type="hidden" name="activeProductStructurePK" value="<%= activeProductStructurePK %>">
<input type="hidden" name="cloneToProductStructurePK" value="null">
<input type="hidden" name="areaValuePKs" value="">
<%-- ****************************** END Hidden Variables ****************************** --%>
</form>
</body>

</html>