<%@ page import="fission.utility.*, edit.common.*"%>
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input"%>
<jsp:useBean id="filteredProduct" class="contract.FilteredProduct" scope="request"/>


<%
  String contractGroupPK = request.getParameter("contractGroupPK");
    
%>
<script type="text/javascript">

    function init()
    {
            f.masterContractNumber.disabled = true;
            f.masterContractName.disabled = true;
            f.uIFPEffectiveDate.disabled = true;    
    }
  /**
   * Finds products that have not yet been associated to the current Case. These
   * products can be found by CompanyName or BusinessContractName.
   */
  function findCandidateProducts()
  {
    if (valueIsEmpty(f.companyName.value) && valueIsEmpty(f.businessContractName.value))
    {
      alert("A search value is required.");
    }

    if (!valueIsEmpty(f.companyName.value))
    {
      sendTransactionAction("CaseDetailTran", "findProductKeysByCompanyName", "_self");
    }
    else if (!valueIsEmpty(f.businessContractName.value))
    {
      sendTransactionAction("CaseDetailTran", "findProductKeysByBusinessContractName", "_self");
    }
  }

  /**
  * Associates the selected ProductStructure to the current ContractGroup.
  */
  function addProductToContractGroup()
  {

     sendTransactionAction("CaseDetailTran", "addProductToContractGroup", "_self");

  }

    function onTableRowSingleClick(tableId)
    {
        if (tableId == "CandidateProductStructureTableModel")
        {
<%--            sendTransactionAction("CaseDetailTran", "showMasterContractDetail", "_self");--%>
            f.masterContractNumber.disabled = false;
            f.masterContractName.disabled = false;
            f.uIFPEffectiveDate.disabled = false;
        }
    }

  /**
  * Runs the search ignoring any search parameters (in essence - clearing them).
  */
  function clearSearchResults()
  {
    f.companyName.value = "";

    f.businessContractName.value = "";

    sendTransactionAction("CaseDetailTran", "showCaseProductAdd", "_self");
  }

  /**
   * Removes the association of ContractGroup to ProductStructure via FilteredProduct.
   */
  function removeProductFromContractGroup()
  {
    sendTransactionAction("CaseDetailTran", "removeProductFromContractGroup", "_self");
  }

</script>
<table cellspacing="3" cellpadding="2" border="0" width="100%">
      <tr>
            <td align="right">Company:</td>
            <td align="left">
                  <input:text name="companyName"
                              attributesText="id=\'companyName\' onKeyPress=\'if (enterKeyPressed()){findCandidateProducts()}\'  onFocus=\"f.businessContractName.value=\'\'\" "/>
            </td>
            <td align="right">Business Contract:</td>
            <td align="left">
                  <input:text name="businessContractName"
                              attributesText="id=\'businessContractName\' onKeyPress=\'if (enterKeyPressed()){findCandidateProducts()}\' onFocus=\"f.companyName.value=\'\'\" "/>
            </td>
            <td align="left" nowrap="nowrap">
                  <input type="button" value="Find"
                         onclick="findCandidateProducts()"/>
                  <input type="button" value="Clear" onClick="clearSearchResults()"/>
            </td>
            <td width="52%">&nbsp;</td>
      </tr>
</table>
<table cellspacing="3" cellpadding="2" border="0" width="100%" height="80%">
      <tr>
            <td width="50%" height="5%">
                  <span class="tableHeading">Candidate Products</span>
            </td>
            <td>&nbsp;</td>
            <td width="50%" height="5%">
                  <span class="tableHeading">Active Products</span>
            </td>
      </tr>
      <tr>
            <td width="47%">
                  <jsp:include page="/common/jsp/widget/tableWidget.jsp"
                               flush="true">
                        <jsp:param name="tableId"
                                   value="CandidateProductStructureTableModel"/>
                        <jsp:param name="tableHeight" value="100"/>
                        <jsp:param name="multipleRowSelect" value="false"/>
                        <jsp:param name="singleOrDoubleClick" value="single"/>
                  </jsp:include>
            </td>
            <td width="6%" align="center">
                  <input name="btnRight" value="  &#8594;  " type="button"
                         style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 11pt;"
                         onclick="addProductToContractGroup()" align="left"
                         title="Add product to Case/Group."></input>
                  <br></br>
                  <br></br>
                  <input name="btnLeft" value="  &#8592;  " type="button"
                         style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 11pt;"
                         onclick="removeProductFromContractGroup()"></input>
            </td>
            <td width="47%">
                  <jsp:include page="/common/jsp/widget/tableWidget.jsp"
                               flush="true">
                        <jsp:param name="tableId"
                                   value="FilteredProductTableModel"/>
                        <jsp:param name="tableHeight" value="100"/>
                        <jsp:param name="multipleRowSelect" value="false"/>
                        <jsp:param name="singleOrDoubleClick" value="single"/>
                  </jsp:include>
            </td>
      </tr>
</table>
<table cellspacing="2" cellpadding="3" border="0" width="100%">
      <tr>
            <td align="right" colspan="6" width="52%" nowrap="nowrap">
                  <input type="button" value="Close" onclick="opener.showCaseMain();window.close()"/>
            </td>
      </tr>
</table>
<input:hidden name="contractGroupPK" default="<%= contractGroupPK %>"/>