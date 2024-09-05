<!--
 * User: gfrosti
 * Date: Nov 3, 2004
 * Time: 12:46:16 PM
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


<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    ProductStructure[] productStructures = (ProductStructure[]) request.getAttribute("productStructures");

    String activeProductStructurePK = Util.initString((String) request.getAttribute("activeProductStructurePK"), "0");

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
    }

    /**
     * Convenience link to the AreaSummary page.
     */
    function showAreaSummary()
    {
        sendTransactionAction("AreaTran", "showAreaSummary", "main");
    }

    /**
     * Shows associated AreaValues (if any).
     */
    function showAssociatedAreaValues()
    {
        var selectedId = getSelectedRowId("companyStructureTable");

        f.activeProductStructurePK.value = selectedId;

        sendTransactionAction("AreaTran", "showAssociatedAreaValues", "main");
    }

    /**
     * Shows the clone AreaValues dialog.
     */
    function showBuildAreaDialog()
    {
        if (f.activeProductStructurePK.value == "0")
        {
            alert("Please Select Company Structure For Build")
        }
        else
        {
            var dialogHeight = 0.90 * getScreenHeight();

            var dialogWidth = getScreenWidth();

            openDialog("buildAreaDialog", null, dialogWidth, dialogHeight);

            sendTransactionAction("AreaTran", "showBuildAreaDialog", "buildAreaDialog");
        }
    }

    /**
     * Cancels current area relation operations.
     */
    function cancelAreaRelations()
    {
        sendTransactionAction("AreaTran", "cancelAreaRelations", "main");
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
    <td width="100%">
      <span class="tableHeading">Company Structure Summary</span>
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
    if (productStructures != null)
    {
        productStructures = (ProductStructure[]) Util.sortObjects(productStructures, new String[]{"getCompanyName", "getMarketingPackageName", "getGroupProductName", "getAreaName", "getBusinessContractName"});

        for (int i = 0; i < productStructures.length; i++)
        {
            String currentProductStructurePK = productStructures[i].getProductStructurePK() + "";

            String currentCompanyName = productStructures[i].getCompanyName();
            String currentMarketingPackageName = productStructures[i].getMarketingPackageName();
            String groupProductName = productStructures[i].getGroupProductName();
            String areaName = productStructures[i].getAreaName();
            String businessContractName = productStructures[i].getBusinessContractName();

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
        <tr class="<%= className %>" id="<%= currentProductStructurePK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showAssociatedAreaValues()">
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

<%-- ****************************** BEGIN Area Values Summary ****************************** --%>
<table width="100%">
  <tr>
    <td width="33%">
      &nbsp;
    </td>
    <td align="center" valign="bottom" width="33%">
      <span class="tableHeading">Area Value Summary</span>
    </td>
    <td align="right" width="33%">
      <input type="button" value="Area Table Summary" onClick="showAreaSummary()">
    </td>
  </tr>
</table>

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
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:41%; top:0; left:0;">
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
        <tr class="<%= className %>" id="<%= currentAreaValuePK %>" isAssociated="<%= isAssociated %>">
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

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" name="build" value="Build" onClick="showBuildAreaDialog()">
            <input type="button" name="cancel" value="Cancel" onClick="cancelAreaRelations()">
        </td>
    </tr>
</table>
<%-- ****************************** END Area Values Summary ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">

<input type="hidden" name="activeProductStructurePK" value="<%= activeProductStructurePK %>">
<input type="hidden" name="areaValuePKs" value="">
<%-- ****************************** END Hidden Variables ****************************** --%>
</form>
</body>

</html>