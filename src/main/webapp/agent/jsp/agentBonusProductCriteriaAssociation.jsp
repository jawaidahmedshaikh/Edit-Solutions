<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="edit.common.vo.BusinessDayVO,
                 fission.utility.Util,
                 java.text.SimpleDateFormat,
                 edit.common.EDITDate,
                 edit.common.vo.ProductStructureVO,
                 agent.business.Agent,
                 agent.component.AgentComponent,
                 edit.common.vo.ContributingProductVO,
                 agent.BonusCriteria,
                 edit.common.EDITBigDecimal,
                 agent.PremiumLevel,
                 agent.BonusProgram,
                 agent.BonusContributingProduct,
                 java.util.*,
                 engine.ProductStructure"%>
<!--
 * User: dlataille
 * Date: Feb 2, 2006
 * Time: 11:46:17 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%

    PremiumLevel premiumLevel = (PremiumLevel) request.getAttribute("selectedPremiumLevel");

    BonusCriteria bonusCriteria = (BonusCriteria) request.getAttribute("selectedBonusCriteria");

    String responseMessage = (String) request.getAttribute("responseMessage");

    ProductStructure[] companyStructures = (ProductStructure[]) request.getAttribute("companyStructures");

    EDITBigDecimal issuePremiumLevel = (EDITBigDecimal) Util.initObject(premiumLevel, "issuePremiumLevel", new EDITBigDecimal());

    long bonusCriteriaPK = Util.initLong(bonusCriteria, "bonusCriteriaPK", 0L);
    EDITBigDecimal bonusAmount = (EDITBigDecimal) Util.initObject(bonusCriteria, "bonusAmount", new EDITBigDecimal());
    EDITBigDecimal excessBonusAmount = (EDITBigDecimal) Util.initObject(bonusCriteria, "excessBonusAmount", new EDITBigDecimal());
    EDITBigDecimal bonusBasisPoint = (EDITBigDecimal) Util.initObject(bonusCriteria, "bonusBasisPoint", new EDITBigDecimal());
    EDITBigDecimal excessBonusBasisPoint = (EDITBigDecimal) Util.initObject(bonusCriteria, "excessBonusBasisPoint", new EDITBigDecimal());
    EDITBigDecimal excessPremiumLevel = (EDITBigDecimal) Util.initObject(bonusCriteria, "excessPremiumLevel", new EDITBigDecimal());
%>
<%!
    /**
     * Sorts the specified ProductStructures by
     * @param companyStructures
     * @return
     */
    private ProductStructure[] sortProductStructures(ProductStructure[] productStructures)
    {
        ProductStructure[] sortedCompanyStructures = null;

        sortedCompanyStructures = (ProductStructure[]) Util.sortObjects(productStructures, new String[]{"getCompanyName", "getMarketingPackageName", "getGroupProductName", "getAreaName", "getBusinessContractName"});

        return sortedCompanyStructures;
    }
%>

<%!
    /**
     * Sorts the specified BonusCriteria by BonusCriteriaPK
     * @param 
     * @return
     */
    private BonusCriteria[] sortBonusCriteria(BonusCriteria[] bonusCriteria)
    {
        BonusCriteria[] sortedBonusCriteria = null;

        sortedBonusCriteria = (BonusCriteria[]) Util.sortObjects(bonusCriteria, new String[]{"getBonusCriteriaPK"});

        return sortedBonusCriteria;
    }
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Bonus Criteria / Products Association</title>
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

        formatCurrency();
    
        // Initialize scroll tables
        // initScrollTable(document.getElementById("BonusCriteriaTableModelScrollTable"));
    }

    function showBonusCriteriaDeatil()
    {
        f.bonusCriteriaPK.value = getSelectedRowId("bonusCriteriaSummary");
        
        sendTransactionAction("AgentBonusTran", "showBonusCriteriaDetail", "_self");
    }

    function addBonusCriteria()
    {
        sendTransactionAction("AgentBonusTran", "addAgentBonusProdCritAssoc", "_self");
    }

    function saveBonusCriteria()
    {
        sendTransactionAction("AgentBonusTran", "saveBonusCriteria", "_self");
    }

    function cancelBonusCriteria()
    {
        sendTransactionAction("AgentBonusTran", "clearAgentBonusProdCritAssoc", "_self");
    }

    function deleteBonusCriteria()
    {
        if (f.bonusCriteriaPK.value == 0)
        {
            alert("Please Select A Bonus Criteria Record for Delete");
        }
        else
        {
            sendTransactionAction("AgentBonusTran", "deleteBonusCriteria", "_self");
        }
    }

    /**
     * Adds a CompanyStructure to the set of CompanyStructures that can participate in the BonusProgram.
     */
    function attachContributingCompanyStructures()
    {
        var selectedRowIds = getSelectedRowIds("companyStructureSummary");

        if (f.bonusCriteriaPK.value == 0)
        {
            alert("Please Select A Bonus Criteria Record for Attachment");
        }
        else if (valueIsEmpty(selectedRowIds))
        {
            alert("Product Selection Required");
        }
        else
        {
            f.selectedCompanyStructurePKs.value = selectedRowIds;

            sendTransactionAction("AgentBonusTran", "attachContributingCompanyStructures", "_self");
        }
    }

    /**
     * Removes a CompanyStructure from the set of CompanyStructures that can participate in the BonusProgram.
     */
    function detachContributingCompanyStructures()
    {
        var selectedRowIds = getSelectedRowIds("companyStructureSummary");

        if (f.bonusCriteriaPK.value == 0)
        {
            alert("Please Select A Bonus Criteria Record for Detachment");
        }
        else if (valueIsEmpty(selectedRowIds))
        {
            alert("Product Selection Required");
        }
        else
        {
            f.selectedCompanyStructurePKs.value = selectedRowIds;

            sendTransactionAction("AgentBonusTran", "detachContributingCompanyStructures", "_self");
        }
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<table class="formdata" width="100%" border="0" cellspacing="0" cellpadding="0">

  <%--Section One--%>
  <tr>
    <td width="5%">&nbsp;</td>
    <td width="90%">
      <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td align="right" nowrap>Issue Premium Level:&nbsp;</td>
          <td align="left" nowrap>
            <input type="text" name="issuePremiumLevel" size="20" maxlength="20" value="<%= issuePremiumLevel %>" CURRENCY>
          </td>
        </tr>
      </table>
    </td>
    <td width="5%">&nbsp;</td>
  </tr>

  <%--Section Two    --%>
  <tr>
    <td>&nbsp;</td>
    <td>
      <fieldset style="border-style:solid; border-width:1px; border-color:gray">
      <legend align="top"><font color="black">Bonus Criteria</font></legend>
      <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td align="right" nowrap width="25%">Bonus Amount:&nbsp;</td>
          <td align="left" nowrap width="25%">
            <input type="text" name="bonusAmount" size="20" maxlength="20" value="<%= bonusAmount %>" CURRENCY>
          </td>
          <td align="right" nowrap width="25%">Excess Bonus Amount:&nbsp;</td>
          <td align="left" nowrap width="25%">
            <input type="text" name="excessBonusAmount" size="20" maxlength="20" value="<%= excessBonusAmount %>" CURRENCY>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap width="25%">Bonus Basis Points:&nbsp;</td>
          <td align="left" nowrap width="25%">
            <input type="text" name="bonusBasisPoint" size="12" maxlength="10" value="<%= bonusBasisPoint %>">
          </td>
          <td align="right" nowrap width="25%">Excess Bonus Basis Points:&nbsp;</td>
          <td align="left" nowrap width="25%">
            <input type="text" name="excessBonusBasisPoint" size="12" maxlength="10" value="<%= excessBonusBasisPoint %>">
          </td>
        </tr>
        <tr>
          <td align="right" nowrap width="25%">&nbsp;</td>
          <td align="left" nowrap width="25%">&nbsp;</td>
          <td align="right" nowrap width="25%">Excess Premium Level:&nbsp;</td>
          <td align="left" nowrap width="25%">
            <input type="text" name="excessPremiumLevel" size="20" maxlength="20" value="<%= excessPremiumLevel %>" CURRENCY>
          </td>
        </tr>
        <tr>
          <td nowrap colspan="4">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr valign="top">
                <td nowrap align="left">
                  <input type="button" id="btnAdd" name="add" value="Add" onClick="addBonusCriteria()">
                  <input type="button" id="btnSave" name="save" value="Save" onClick="saveBonusCriteria()">
                  <input type="button" id="btnCancel" name="cancel" value="Cancel" onClick="cancelBonusCriteria()">
                  <input type="button" id="btnDelete" name="delete" value="Delete" onClick="deleteBonusCriteria()">
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr height="50%">
          <td nowrap colspan="4">
            <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr class="heading">
                    <td width="25%" nowrap>
                        Bonus Amount
                    </td>
                    <td width="25%" nowrap>
                        Excess Bonus Amount
                    </td>
                    <td width="25%" nowrap>
                        Bonus Basis Points
                    </td>
                    <td width="25%" nowrap>
                        Excess Bonus Basis Points
                    </td>
                </tr>
            </table>
            
            <%--Summary--%>
            <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:80%; top:0; left:0;">
                <table id="bonusCriteriaSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <%
                    BonusCriteria[] bonusCriterias = (BonusCriteria[]) premiumLevel.getBonusCriterias().toArray(new BonusCriteria[premiumLevel.getBonusCriterias().size()]);
                    
                    bonusCriterias = sortBonusCriteria(bonusCriterias);
                    
                    for (int i = 0; i < bonusCriterias.length; i++) 
                    {                    
                        long currentBonusCriteriaPK = bonusCriterias[i].getBonusCriteriaPK().longValue();
                        
                        String currentBonusAmount = bonusCriterias[i].getBonusAmount().toString();
                        
                        String currentExcessBonusAmount = bonusCriterias[i].getExcessBonusAmount().toString();
                        
                        String currentBonusBasisPoint = bonusCriterias[i].getBonusBasisPoint().toString();
                        
                        String currentExcessBonusBasisPoint = bonusCriterias[i].getExcessBonusBasisPoint().toString();
                    
                        boolean isSelected = false;
                        
                        String className = null;
                    
                        if (currentBonusCriteriaPK == bonusCriteriaPK)
                        {
                            isSelected = true;
            
                            className = "highlighted";
                        }
                        else
                        {
                            className = "default";
                        }
            %>
                    <tr class="<%= className %>" id="<%= currentBonusCriteriaPK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showBonusCriteriaDeatil()">
                        <td width="25%" nowrap>
                            <%= "<script>document.write(formatAsCurrency(" + currentBonusAmount + "))</script>" %>
                        </td>
                        <td width="25%" nowrap>
                            <%= "<script>document.write(formatAsCurrency(" + excessBonusAmount + "))</script>" %>
                        </td>
                        <td width="25%" nowrap>
                            <%= currentBonusBasisPoint %>
                        </td>
                        <td width="25%" nowrap>
                            <%= currentExcessBonusBasisPoint %>
                        </td>
                    </tr>
            <%
                    } // end for
            %>
                    <tr class="filler">
                        <td colspan="4">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </span>
            <%-- ****************************** END Summary Area ****************************** --%>

          </td>
        </tr>
      </table>
      </fieldset>
    </td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td nowrap colspan="3">&nbsp;</td>
  </tr>
</table>

<%-- ****************************** BEGIN Summary Area ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            <input type="button" value="Attach" onClick="attachContributingCompanyStructures()">
            <input type="button" value="Detach" onClick="detachContributingCompanyStructures()">
        </td>
        <td align="left" width="33%">
            <span class="tableHeading">Contributing Products</span>
        </td>
        <td align="right" width="33%">
            &nbsp;
        </td>
    </tr>
</table>

<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="20%" nowrap>
            Company
        </td>
        <td width="20%" nowrap>
            Marketing Package
        </td>
        <td width="20%" nowrap>
            Group Product
        </td>
        <td width="20%" nowrap>
            Area
        </td>
        <td width="20%" nowrap>
            Business Contract
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0;">
    <table id="companyStructureSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
        companyStructures = sortProductStructures(companyStructures);

        boolean associatedBonusCriteriaFound = false;

        for (int i = 0; i < companyStructures.length; i++) // Loop through the target VOs.
        {
            associatedBonusCriteriaFound = false;

            ProductStructure currentCompanyStructure = companyStructures[i];

            long currentCompanyStructurePK = currentCompanyStructure.getProductStructurePK().longValue();

            if (bonusCriteria != null)
            {
                Set bonusContributingProducts = bonusCriteria.getBonusContributingProducts();
                Iterator it = bonusContributingProducts.iterator();

                while (it.hasNext())
                {
                    BonusContributingProduct bonusContribProd = (BonusContributingProduct) it.next();
                    if (bonusContribProd.getProductStructureFK().longValue() == currentCompanyStructurePK)
                    {
                        associatedBonusCriteriaFound = true;
                    }
                }
            }

            String currentCompanyName = currentCompanyStructure.getCompanyName();

            String currentMarketingPackageName = currentCompanyStructure.getMarketingPackageName();

            String currentGroupProductName = currentCompanyStructure.getGroupProductName();

            String currentAreaName = currentCompanyStructure.getAreaName();

            String currentBusinessContractName = currentCompanyStructure.getBusinessContractName();

            boolean isSelected = false;

            boolean isAssociated = false;

            String className = null;

            if (associatedBonusCriteriaFound)
            {
                isAssociated = true;

                className = "associated";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= currentCompanyStructurePK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(true)">
            <td width="20%" nowrap>
                <%= currentCompanyName %>
            </td>
            <td width="20%" nowrap>
                <%= currentMarketingPackageName %>
            </td>
            <td width="20%" nowrap>
                <%= currentGroupProductName %>
            </td>
            <td width="20%" nowrap>
                <%= currentAreaName %>
            </td>
            <td width="20%" nowrap>
                <%= currentBusinessContractName %>
            </td>
        </tr>
<%
        } // end for
%>
        <tr class="filler">
            <td colspan="4">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
<%-- ****************************** END Summary Area ****************************** --%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2" align="right" nowrap>
      <input type="button" name="close" value="Close" onClick="closeWindow()">
    </td>
  </tr>
</table>


<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="selectedCompanyStructurePKs">
<input type="hidden" name="bonusCriteriaPK" value="<%= bonusCriteriaPK %>">
<input:hidden name="bonusProgramPK" attributesText="id=\"bonusProgramPK\""/>
<input:hidden name="premiumLevelPK" attributesText="id=\"premiumLevelPK\""/>
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>