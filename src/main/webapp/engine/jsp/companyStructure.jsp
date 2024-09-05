<!-- *********Java Code ******* -->


<%@ page import="edit.common.vo.*,
                 java.util.*,
                 fission.utility.*,                        
                 edit.common.CodeTableWrapper,
                 engine.*"%>

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>


<%
    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("productStructureVOs");

    FundVO[] fundVOs = (FundVO[]) request.getAttribute("fundVOs");
    if (fundVOs == null)  {

        fundVOs = new FundVO[0];
    }


    // we use a set of product ones to form a modal dialog below
    List productCompStructList = new ArrayList();
    for (int i = 0; i < productStructureVOs.length; i++)
    {
        ProductStructureVO productStructureVO = productStructureVOs[i];
        if (productStructureVO.getTypeCodeCT().equalsIgnoreCase("Product"))
        {
            productCompStructList.add(productStructureVO);
        }
    }
    ProductStructureVO[] productProductStructureVOs =
            (ProductStructureVO[]) productCompStructList.toArray(new ProductStructureVO[0]);


    ProductStructureVO productStructureVO = (ProductStructureVO) request.getAttribute("productStructureVO");
    String activeProductStructurePK = null;
    String companyName = null;
    String policyNumberPrefix = null;
    String policyNumberSuffix = null;
    int policyNumberSequenceNumber = 0;
    int policySequenceLength = 0;
    String billingCompanyNumber = null;
    String marketingPackageName = null;
    String groupProductName = null;
    String areaName = null;
    String businessContractName = null;
    String maintDateTime = null;
    String operator = null;
    String typeCode = null;
 	String externalProductName = null;
    String hedgeFundInterimAccountFK = null;
    String groupTypeCT = null;
    String productTypeCT = null;

    if (productStructureVO != null)
    {
        activeProductStructurePK = productStructureVO.getProductStructurePK() + "";
        Company company = Company.findByPK(new Long(productStructureVO.getCompanyFK()));

        companyName = company.getCompanyName();
        policyNumberPrefix = company.getPolicyNumberPrefix();
        policyNumberSuffix = company.getPolicyNumberSuffix();
        policyNumberSequenceNumber = company.getPolicyNumberSequenceNumber();
        policySequenceLength = company.getPolicySequenceLength();
        billingCompanyNumber = company.getBillingCompanyNumber();
        marketingPackageName = productStructureVO.getMarketingPackageName();
        groupProductName = productStructureVO.getGroupProductName();
        areaName = productStructureVO.getAreaName();
        businessContractName = productStructureVO.getBusinessContractName();
        maintDateTime = productStructureVO.getMaintDateTime();
        operator = productStructureVO.getOperator();
        typeCode = productStructureVO.getTypeCodeCT();
        externalProductName = productStructureVO.getExternalProductName();
        hedgeFundInterimAccountFK = productStructureVO.getHedgeFundInterimAccountFK() + "";
        groupTypeCT = productStructureVO.getGroupTypeCT();
        productTypeCT = productStructureVO.getProductTypeCT();
    }

    activeProductStructurePK = Util.initString(activeProductStructurePK, "0");
    companyName = Util.initString(companyName, "");
    policyNumberPrefix = Util.initString(policyNumberPrefix, "");
    policyNumberSuffix = Util.initString(policyNumberSuffix, "");
    billingCompanyNumber = Util.initString(billingCompanyNumber, "");
    marketingPackageName = Util.initString(marketingPackageName, "");
    groupProductName = Util.initString(groupProductName, "");
    areaName = Util.initString(areaName, "");
    businessContractName = Util.initString(businessContractName, "");
    maintDateTime = Util.initString(maintDateTime, "");
    operator = Util.initString(operator, "");
    typeCode = Util.initString(typeCode, "");
    externalProductName = Util.initString(externalProductName, "");
    hedgeFundInterimAccountFK = Util.initString(hedgeFundInterimAccountFK, "");
    groupTypeCT = Util.initString(groupTypeCT, "");
    productTypeCT = Util.initString(productTypeCT, "");

    CompanyStructureNameVO companyStructureNameVO = (CompanyStructureNameVO) request.getAttribute("companyStructureNameVO");
    String[] companyNames = null;
    String[] marketingPackageNames = null;
    String[] groupProductNames = null;
    String[] areaNames = null;
    String[] businessContractNames = null;
    if (companyStructureNameVO != null)
    {
        companyNames = companyStructureNameVO.getCompanyName();
        marketingPackageNames = companyStructureNameVO.getMarketingPackageName();
        groupProductNames = companyStructureNameVO.getGroupProductName();
        areaNames = companyStructureNameVO.getAreaName();
        businessContractNames = companyStructureNameVO.getBusinessContractName();
    }

    List attachedProductStructurePKs = (List) request.getAttribute("attachedProductStructurePKs");

    String message = (String) request.getAttribute("message");
    message = Util.initString(message, "");

    String pageMode = (String) request.getAttribute("pageMode");
    pageMode = Util.initString(pageMode, "VIEW");
    CodeTableVO[] typeCodeCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("TYPECODE");
    CodeTableVO[] groupTypes = CodeTableWrapper.getSingleton().getCodeTableEntries("GROUPTYPE");
    CodeTableVO[] productTypes = CodeTableWrapper.getSingleton().getCodeTableEntries("PRODUCTTYPE");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

var f = null;

var message = "<%= message %>";

var pageMode = "<%= pageMode %>";

function deleteProductStructure()
{
    sendTransactionAction("ProductStructureTran", "deleteProductStructure", "main");
}

function addProductStructure()
{
    sendTransactionAction("ProductStructureTran", "addProductStucture", "main");
}

function checkForNew()
{
    var select = window.event.srcElement;

    var selectName = select.name;
    var rootSelectName = selectName.substring(0, selectName.length - 6);
    var textField = document.getElementById(rootSelectName);

    if (select.selectedIndex == 0) // If "Please Select"
    {
        textField.value = "";
    }
    else if (select.selectedIndex == 1) // if <new>
    {
        textField.value = "";
        textField.focus();
    }
    else if (select.selectedIndex > 1) // Anything other than "Please Select"
    {
        textField.value = select.options[select.selectedIndex].text;
    }
}

function saveProductStructure()
{
    if (f.typeCodeSelect.value  < 1)
    {
        alert("Type Code Required");
    }
    else
    {
        if (f.activeProductStructurePK.value == "0" && f.typeCodeSelect.options[f.typeCodeSelect.selectedIndex].value == "Product")
        {
            // we are adding a product type company structure, let them clone security if they want
            var width   = 0.3 * screen.width;
            var height  = 0.2 * screen.height;

            //  Center the dialog to the middle of the screen.  Calculate the center
            var left = screen.width/2 - width/2;
            var top = screen.height/2 - height/2;

            var features = "left=" + left + ",top=" + top + ",resizable=yes";

            openDialog("cloneSecurityDialog", features, width, height);

            sendTransactionAction("ProductStructureTran", "showCloneSecurityDialog", "cloneSecurityDialog");
        }
        else
        {
            sendTransactionAction("ProductStructureTran", "saveProductStructure", "main");
        }
    }
}

function showProductStructure()
{
    sendTransactionAction("ProductRuleStructureTran", "showProductStructure", "main");
}

function showAttachedRules()
{
    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement.parentElement; // img -> td -> TR (currentRow)

    f.activeProductStructurePK.value = currentRow.productStructurePK;

    var width   = 0.98 * screen.width;
    var height  = 0.50 * screen.height;

    openDialog("attachedRulesDialog", null, width, height);

    sendTransactionAction("ProductStructureTran", "showAttachedRules", "attachedRulesDialog");
}

function showBuildCompanyStructureDialog()
{
    if (f.activeProductStructurePK.value == "0")
    {
        alert("Please Select Company Structure For Build")
    }
    else
    {
        var width   = .90 * screen.width;
        var height  = .30 * screen.height;

        openDialog("buildCompanyStructureDialog", null, width, height);

        sendTransactionAction("ProductStructureTran", "showBuildCompanyStructureDialog", "buildCompanyStructureDialog");
    }
}

function showProductStructureDetail()
{
    var tdElement = window.event.srcElement;

    if (tdElement.tagName == "IMG") // The IMG could have been the source of the click, therefore take a different path.
    {
        showAttachedRules();
    }
    else
    {
        var currentRow = tdElement.parentElement;

        f.activeProductStructurePK.value = currentRow.productStructurePK;

        sendTransactionAction("ProductStructureTran", "showProductStructureDetail", "main");
    }
}

function cancelProductStructureEdits()
{
    f.activeProductStructurePK.value = "";

    sendTransactionAction("ProductStructureTran","cancelProductStructureEdits", "main");
}

function disableProductStructureDetails(shouldDisable)
{
    if (shouldDisable)
    {
        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button" || elementType == "select-one") && (f.elements[i].id.indexOf("btn") < 0))
            {
                f.elements[i].disabled = true;
            }
        }
    }
}

function initForPageMode(pageMode)
{
    f.buildButton.disabled = false;

    if (pageMode == "VIEW")
    {
        disableProductStructureDetails(true);

        f.btnAdd.disabled = false;
        f.btnSave.disabled = true;
        f.btnCancel.disabled = true;
        f.btnDelete.disabled = false;
    }
    else if (pageMode == "ADD")
    {
        disableProductStructureDetails(false);

        f.btnAdd.disabled = true;
        f.btnSave.disabled = false;
        f.btnCancel.disabled = false;
        f.btnDelete.disabled = true;
    }
    else if (pageMode == "CHANGE")
    {
        f.btnAdd.disabled = true;
        f.btnSave.disabled = false;
        f.btnCancel.disabled = false;
        f.btnDelete.disabled = false;
    }
}

function init()
{
	f = document.theForm;

    if (message.length > 0)
    {
        alert(message);
    }

    initForPageMode(pageMode);

    var scrollToRow = document.getElementById("<%= activeProductStructurePK %>");
    if (scrollToRow != null)
    {
        scrollToRow.scrollIntoView(false);
    }
}

</script>
<title>Company Structure Summary</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init()">

<form name="theForm">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%">
      <span class="tableHeading">Company Structure Information</span><br>
    </td>
  </tr>
</table>

<span id="companyStructureDetailsArea" contentEditable="false" class="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:25%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" cellpadding="3" cellspacing="0" border="0">
    <tr>
      <td width="20%" nowrap>Company:&nbsp;</td>
      <td width="20%" nowrap>Marketing Package:&nbsp;</td>
      <td width="20%" nowrap>Group Product:&nbsp;</td>
      <td width="20%" nowrap>Area:&nbsp;</td>
      <td width="20%">Business Contract:&nbsp;</td>
    </tr>
    <tr>
      <td width="20%" nowrap>
        <select name="companyNameSelect" onChange="checkForNew()">
          <option name="id" value="-1">Please Select</option>
          <option name="id" value="0">&lt;new&gt;</option>
          <%
              if (companyNames != null)
              {
                  for(int i = 0; i < companyNames.length; i++)
                  {
                      String currentCompanyName = companyNames[i];

                      if (currentCompanyName.equalsIgnoreCase(companyName))
                      {
                          out.println("<option selected name=\"id\" value=\"" + currentCompanyName + "\">" + currentCompanyName + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + currentCompanyName + "\">" + currentCompanyName + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
      <td width="20%" nowrap>
        <select name="marketingPackageNameSelect" onChange="checkForNew()">
          <option name="id" value="-1">Please Select</option>
          <option name="id" value="0">&lt;new&gt;</option>
          <%
              if (marketingPackageNames != null)
              {
                  for(int i = 0; i < marketingPackageNames.length; i++)
                  {
                      String currentMarketingPackageName = marketingPackageNames[i];

                      if (currentMarketingPackageName.equalsIgnoreCase(marketingPackageName))
                      {
                          out.println("<option selected name=\"id\" value=\"" + currentMarketingPackageName + "\">" + currentMarketingPackageName + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + currentMarketingPackageName + "\">" + currentMarketingPackageName + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
      <td width="20%" nowrap>
        <select name="groupProductNameSelect" onChange="checkForNew()">
          <option name="id" value="-1">Please Select</option>
          <option name="id" value="0">&lt;new&gt;</option>
          <%
              if (groupProductNames != null)
              {
                  for(int i = 0; i < groupProductNames.length; i++)
                  {
                      String currentGroupProductName = groupProductNames[i];

                      if (currentGroupProductName.equalsIgnoreCase(groupProductName))
                      {
                          out.println("<option selected name=\"id\" value=\"" + currentGroupProductName + "\">" + currentGroupProductName + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + currentGroupProductName + "\">" + currentGroupProductName + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
      <td width="20%" nowrap>
        <select name="areaNameSelect" onChange="checkForNew()">
          <option name="id" value="-1">Please Select</option>
          <option name="id" value="0">&lt;new&gt;</option>
          <%
              if (areaNames != null)
              {
                  for(int i = 0; i < areaNames.length; i++)
                  {
                      String currentAreaName = areaNames[i];

                      if (currentAreaName.equalsIgnoreCase(areaName))
                      {
                          out.println("<option selected name=\"id\" value=\"" + currentAreaName + "\">" + currentAreaName + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + currentAreaName + "\">" + currentAreaName + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
      <td width="20%" nowrap>
        <select name="businessContractNameSelect" onChange="checkForNew()">
          <option name="id" value="-1">Please Select</option>
          <option name="id" value="0">&lt;new&gt;</option>
          <%
              if (businessContractNames != null)
              {
                  for(int i = 0; i < businessContractNames.length; i++)
                  {
                      String currentBusinessContractName = businessContractNames[i];

                      if (currentBusinessContractName.equalsIgnoreCase(businessContractName))
                      {
                          out.println("<option selected name=\"id\" value=\"" + currentBusinessContractName + "\">" + currentBusinessContractName + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + currentBusinessContractName + "\">" + currentBusinessContractName + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
    </tr>
    <tr>
      <td width="20%" nowrap>
        <input type="text" size="15" maxlength="15" id="companyName" name="companyName" value="<%= companyName %>">
      </td>
      <td width="20%" nowrap>
        <input type="text" size="15" maxlength="15" id="marketingPackageName" name="marketingPackageName" value="<%= marketingPackageName %>">
      </td>
      <td width="20%" nowrap>
        <input type="text" size="15" maxlength="15" id="groupProductName" name="groupProductName" value="<%= groupProductName %>">
      </td>
      <td width="20%" nowrap>
        <input type="text" size="15" maxlength="15" id="areaName" name="areaName" value="<%= areaName %>">
      </td>
      <td width="20%" nowrap>
        <input type="text" size="15" maxlength="15" id="businessContractName" name="businessContractName" value="<%= businessContractName %>">
      </td>
    </tr>
  </table>
  <br>
  <table>
    <tr>
      <td align="left" nowrap>Policy Number Prefix:&nbsp;</td>
      <td>
        <input type="text" size="8" maxlength="8" id="policyNumberPrefix" name="policyNumberPrefix" value="<%= policyNumberPrefix %>">
      </td>
      <td align="left" nowrap>&nbsp;</td>
      <td align="left" nowrap>Policy Number Seq #:&nbsp;</td>
      <td>
        <input type="text" size="8" maxlength="8" id="policyNumberSequenceNumber" name="policyNumberSequenceNumber" value="<%= policyNumberSequenceNumber %>">
      </td>
      <td align="left" nowrap>&nbsp;</td>
      <td align="left" nowrap>Policy Sequence Length:&nbsp;</td>
      <td>
        <input type="text" size="4" maxlength="4" id="policySequenceLength" name="policySequenceLength" value="<%= policySequenceLength %>">
      </td>
      <td align="left" nowrap>Policy Number Suffix:&nbsp;</td>
      <td>
        <input type="text" size="8" maxlength="8" id="policyNumberSuffix" name="policyNumberSuffix" value="<%= policyNumberSuffix %>">
      </td>
      <td align="left" nowrap>&nbsp;</td>
      <td nowrap align="left">Billing Company Number:&nbsp;</td>
      <td>
        <input type="text" size="20" maxlength="20" id="billingCompanyNumber" name="billingCompanyNumber" value="<%= billingCompanyNumber %>">
      </td>
    </tr>
  </table>
  <br>
  <table>
    <tr>
      <td align="left" nowrap>Type Code:&nbsp;</td>
      <td>
        <select name="typeCodeSelect">
          <option name="id" value="-1">Please Select</option>
          <%
              if (typeCodeCTs != null)
              {
                  for(int i = 0; i < typeCodeCTs.length; i++)
                  {
                      String code = typeCodeCTs[i].getCode();
                      String codeDesc = typeCodeCTs[i].getCodeDesc();
                      if ( (typeCode != null) && (typeCode.equals(code)))
                      {
                          out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
      <td align="left" nowrap>&nbsp;</td>
      <td align="left" nowrap>Group Type:&nbsp;</td>
      <td>
        <select name="groupTypeSelect">
          <option name="id" value="-1">Please Select</option>
          <%
              if (groupTypes != null)
              {
                  for(int i = 0; i < groupTypes.length; i++)
                  {
                      String code = groupTypes[i].getCode();
                      String codeDesc = groupTypes[i].getCodeDesc();
                      if ( (groupTypeCT != null) && (groupTypeCT.equals(code)))
                      {
                          out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
      <td align="left" nowrap>&nbsp;</td>
      <td align="left" nowrap>Product Type:&nbsp;</td>
      <td>
        <select name="productTypeSelect">
          <option name="id" value="-1">Please Select</option>
          <%
              if (productTypes != null)
              {
                  for(int i = 0; i < productTypes.length; i++)
                  {
                      String code = productTypes[i].getCode();
                      String codeDesc = productTypes[i].getCodeDesc();
                      if ( (productTypeCT != null) && (productTypeCT.equals(code)))
                      {
                          out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
      <td align="left" nowrap>&nbsp;</td>
      <td nowrap align="left">Hedge Fund Interim Account:&nbsp;</td>
      <td>
        <select name="hedgeFundInterimAccountFK">
          <%
              out.println("<option>Please Select</option>");

              if (fundVOs != null)
              {
                  for (int i = 0; i < fundVOs.length; i++)
                  {
                      String fundPK = fundVOs[i].getFundPK() + "";

                      if (hedgeFundInterimAccountFK.equals(fundPK))
                      {
                          out.println("<option selected value=\"" + fundPK + "\">" + fundVOs[i].getName() + "</option>");
                      }
                      else
                      {
                          out.println("<option value=\"" + fundPK + "\">" + fundVOs[i].getName() + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
    </tr>
  </table>
  <br>
  <table>
    <tr>
      <td nowrap>External Product Name:&nbsp;</td>
      <td>
        <input type="text" size="100" maxlength="100" id="externalProductName" name="externalProductName" value="<%= externalProductName %>">
      </td>
    </tr>
  </table>
</span>

<br><br>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left" width="33%">
      <input type="button" id="btnAdd" name="add" value=" Add " onClick="addProductStructure()">
      <input type="button" id="btnSave" name="save" value=" Save " onClick="saveProductStructure()">
      <input type="button" id="btnCancel" name="cancel" value="Cancel" onClick="cancelProductStructureEdits()">
      <input type="button" id="btnDelete" name="delete" value="Delete" onClick="deleteProductStructure()">
    </td>
    <td align="center" width="33%">
      <span class="tableHeading">Company Structure Summary</span>
    </td>
    <td width="33%">&nbsp;</td>
  </tr>
</table>

<%-- Header --%>
<table class="summary" id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
<tr class="heading">
  <td align="left" width="3%">&nbsp;</td>
  <td align="left" width="16%">Company</td>
  <td align="left" width="16%">Marketing Package</td>
  <td align="left" width="16%">Group Product</td>
  <td align="left" width="16%">Area</td>
  <td align="left" width="16%">Business Contract</td>
  <td align="left" width="16%">Type Code</td>
</tr>
</table>

<%-- Summary --%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:60%; top:0; left:0;">
  <table class="summary" id="requirementsSummary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <%
      if (productStructureVOs != null)
      {
          productStructureVOs = (ProductStructureVO[]) Util.sortObjects(productStructureVOs, new String[]{"getBusinessContractName"});

          for (int i = 0; i < productStructureVOs.length; i++)
          {
              String currentProductStructurePK = productStructureVOs[i].getProductStructurePK() + "";
              Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));

              String currentCompanyName = company.getCompanyName();
              String currentMarketingPackageName = productStructureVOs[i].getMarketingPackageName();
              String currentGroupProductName = productStructureVOs[i].getGroupProductName();
              String currentAreaName = productStructureVOs[i].getAreaName();
              String currentBusinessContractName = productStructureVOs[i].getBusinessContractName();
              String currentTypeCodeCT = Util.initString(productStructureVOs[i].getTypeCodeCT(), "");

              String className = null;
              boolean isSelected = false;

             if (activeProductStructurePK.equals(currentProductStructurePK))
             {
                 className = "highlighted";
                 isSelected = true;
             }
             else
             {
                 className = "default";
             }
  %>
    <tr class="<%= className %>" isSelected="<%= isSelected %>" id="<%= currentProductStructurePK %>"
        productStructurePK="<%= currentProductStructurePK %>" onMouseOver="highlightRow()"
        onMouseOut="unhighlightRow()" onClick="showProductStructureDetail()">
      <td width="3%">
        <%
            if (attachedProductStructurePKs.contains(new Long(currentProductStructurePK)))
            {
                out.println("<img src=\"/PORTAL/engine/images/repeating_b2.gif\" width=\"28\" height=\"15\" alt=\"Show Attached Rules\" onMouseOver=\"this.src='/PORTAL/engine/images/repeating_b2_over.gif'\" onMouseOut=\"this.src='/PORTAL/engine/images/repeating_b2.gif'\">");
            }
            else
            {
                out.println("&nbsp");
            }
        %>
      </td>
      <td width="16%" align="left">
        <%= currentCompanyName %>
      </td>
      <td width="16%" align="left">
        <%= currentMarketingPackageName %>
      </td>
      <td width="16%" align="left">
        <%= currentGroupProductName %>
      </td>
      <td width="16%" align="left">
        <%= currentAreaName %>
      </td>
      <td width="16%" align="left">
        <%= currentBusinessContractName %>
      </td>
      <td width="16%" align="left">
        <%= currentTypeCodeCT %>
      </td>
    </tr>
  <%
          }
      }
  %>
    <tr class="filler">
      <td colspan="6">&nbsp;</td>
    </tr>
  </table>
</span>

<br>

<table width="100%">
  <tr>
    <td align="left" valign="bottom">
      <img src="/PORTAL/engine/images/repeating_b2.gif" width="28" height="15"> = Attached Company Structure
    </td>
    <td align="right" valign="bottom">
      <input type="button" name="buildButton" value="Build" onClick="showBuildCompanyStructureDialog()">
    </td>
  </tr>
</table>

<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="activeProductStructurePK" value="<%= activeProductStructurePK %>">



</form>
</body>
</html>
