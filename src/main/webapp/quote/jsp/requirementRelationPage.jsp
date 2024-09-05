<%@ page import="edit.common.vo.*,
                 edit.common.vo.user.*,
                 java.util.*,
                 fission.utility.Util,
                 engine.*"%>

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
    String activeCompanyStructurePK = Util.initString((String) request.getAttribute("activeCompanyStructurePK"), "0");

    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("companyStructureVOs");
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
<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

var rule = null;
var company = null;

function init()
{
	f = document.requirementRelationForm;

    var scrollToRow = document.getElementById("<%= activeCompanyStructurePK %>");

    if (scrollToRow != null) {

        scrollToRow.scrollIntoView(false);
    }
}

/**
 * Shows the buildRequirement dialog.
 */
function showBuildRequirementDialog()
{
    if (f.activeCompanyStructurePK.value == "0")
    {
        alert("Please Select Company Structure For Build")
    }
    else
    {
        var dialogHeight = 0.90 * getScreenHeight();

        var dialogWidth = getScreenWidth();

        openDialog("buildRequirementDialog", null, dialogWidth, dialogHeight);

        sendTransactionAction("QuoteDetailTran", "showBuildRequirementDialog", "buildRequirementDialog");
    }
}

function showRequirementsSummary()
{
    sendTransactionAction("QuoteDetailTran", "showRequirementDetailSummary", "_self");
}

function cancelRelation()
{
    f.activeCompanyStructurePK.value = "0";

    sendTransactionAction("QuoteDetailTran","cancelRelation", "_self");
}

function showRelation()
{
    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;
    var selectedId = getSelectedRowId("companyStructureTable");

    f.activeCompanyStructurePK.value = selectedId;

    sendTransactionAction("QuoteDetailTran", "showRelation", "_self");
}

</script>
<title>Requirement Relation</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init()">
<form name="requirementRelationForm">

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
        <td align="right" valign="bottom" width="33%">
            <input type="button" value="Requirements Summary" onClick="showRequirementsSummary()">
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
              if (productStructureVOs != null){

                  for (int i = 0; i < productStructureVOs.length; i++){

                      String currentCompanyStructurePK = productStructureVOs[i].getProductStructurePK() + "";
                      Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));

                      String currentCompanyName = company.getCompanyName();
                      String currentMarketingPackageName = productStructureVOs[i].getMarketingPackageName();
                      String groupProductName = productStructureVOs[i].getGroupProductName();
                      String areaName = productStructureVOs[i].getAreaName();
                      String businessContractName = productStructureVOs[i].getBusinessContractName();

                      String className = null;

                      boolean isSelected = false;
                      if (activeCompanyStructurePK.equals(currentCompanyStructurePK)) {

                          className = "highlighted";
                          isSelected = true;
                      }
                      else {

                          className = "default";
                      }
    %>
        <tr class="<%= className %>" id="<%= currentCompanyStructurePK %>" isSelected="<%= isSelected %>"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showRelation()">
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

<%-- ****************************** BEGIN Requirements Summary ****************************** --%>
<table width="100%">
  <tr>
    <td align="center">
      <span class="tableHeading">Requirements Summary</span>
    </td>
  </tr>
</table>

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
        isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>">
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

<table width="100%">
  <tr>
    <td align="right" valign="bottom">
      <input type="button" name="build" value="Build" onClick="showBuildRequirementDialog()">
      <input type="button" name="cancel" value="Cancel" onClick="cancelRelation()">
    </td>
  </tr>
</table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction">
 <input type="hidden" name="action">
 <input type="hidden" name="activeCompanyStructurePK" value="<%= activeCompanyStructurePK %>">

</form>
</body>
</html>