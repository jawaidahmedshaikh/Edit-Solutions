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
                 java.util.*,
                 edit.common.*,
                 java.io.*,
                 fission.utility.*,
                 engine.*"%>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
    String message = (String) request.getAttribute("message");
    message = (message == null)?"":message;

    // User selected items...
    String selectedProductStructurePK = initValue((String) request.getAttribute("selectedProductStructurePK"));
    FilteredFundVO[] attachedFilteredFundVOs = (FilteredFundVO[]) request.getAttribute("attachedFilteredFundVOs");

    // Dropdown items...

    // Summary items...
    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("productStructureVOs");
    FilteredFundVO[]   filteredFundVOs   = (FilteredFundVO[]) request.getAttribute("filteredFundVOs");
    FundVO[] fundVOs = (FundVO[]) request.getAttribute("fundVOs");

    // From the previous screen selection ...

    // Sort them...
    filteredFundVOs = (FilteredFundVO[]) Util.sortObjects(filteredFundVOs, new String[]{"getFundNumber"});

    if (filteredFundVOs == null){

        filteredFundVOs = new FilteredFundVO[0];
    }

    // Utility objects...
    List attachedFilteredFundPKs  = loadAttachedFilteredFundPKs(attachedFilteredFundVOs);
%>
<%!
    private String initValue(String value){

        if (value != null){

            return value;
        }
        else {

            return "";
        }
    }

    private String[] initValues(String[] values){

        if (values != null){

            return values;
        }
        else{

            return new String[0];
        }
    }

    private List loadAttachedFilteredFundPKs(FilteredFundVO[] attachedFilteredFundVOs){

        List attachedFilteredFundPKs = new ArrayList();
        String filteredFundPK = null;

        if (attachedFilteredFundVOs != null){

            for (int i = 0; i < attachedFilteredFundVOs.length; i++){

                filteredFundPK = (attachedFilteredFundVOs[i].getFilteredFundPK() + "");
                attachedFilteredFundPKs.add(attachedFilteredFundVOs[i].getFilteredFundPK() + "");
            }
        }

        return attachedFilteredFundPKs;
    }
%>

<html>
<head>

<!-- ***** JAVASCRIPT *****  -->

<script language="Javascript1.2">

    var f               = null;
    var previousCSRow   = null;
    var previousESRow   = null;
    var previousESRowAttached = null;
    var message = "<%= message %>";

    function highlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = "#FFFFCC";
    }

    function unhighlightRow(){

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected != "true") {

            if (className == "assocEntry")
            {
                currentRow.style.backgroundColor = "#00BB00";
            }
            else if (className == "highLighted")
            {
                currentRow.style.backgroundColor = "#FFFFCC";
            }
            else
            {
                currentRow.style.backgroundColor = "#BBBBBB";
            }
        }
    }

    function init() {

	    f = document.theForm;

        if (message.length > 0){

            alert(message);
        }
    }

    function cancelEdits(){

        sendTransactionAction("FundTran", "showAttachedFilteredFunds", "_self");
    }

    function selectCSRow() {

    	var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;

        f.selectedProductStructurePK.value = currentRow.id;

        sendTransactionAction("FundTran", "showAttachedFilteredFunds", "_self");
    }

    function selectESRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected == "false"){

            currentRow.style.backgroundColor = "#FFFFCC";
            currentRow.isSelected = "true";
        }
        else {

            if (className == "mainEntry"){

                currentRow.style.backgroundColor = "#BBBBBB";
            }
            else if (className == "assocEntry"){

                currentRow.style.backgroundColor = "#00BB00";
            }

            currentRow.isSelected = "false";
        }
    }

    function openDialog(theURL,winName,features, transaction, action, target) {

        dialog = window.open(theURL,winName,features);
        sendTransactionAction(transaction, action, target);
    }

    function detachFilteredFund() {

	    f.selectedFilteredFundPKs.value = getSelectedFilteredFundPKs();

        sendTransactionAction("FundTran", "detachFilteredFund", "_self");
    }

    function attachFilteredFund(){

	    f.selectedFilteredFundPKs.value = getSelectedFilteredFundPKs();

        sendTransactionAction("FundTran", "attachFilteredFund", "_self");
    }

    function checkForCompanyAndFilteredFund(){

        var itemsAreSelected = true;

        if (f.selectedProductStructurePK.value == null || f.selectedProductStructurePK.value.length == 0){
            itemsAreSelected = false;
        }

        if (f.selectedFilteredFundPK.value == null || f.selectedFilteredFundPK.value.length == 0){

            itemsAreSelected = false;
        }

        if(!itemsAreSelected){

            alert("Company Structure and Filtered Fund must be selected.");

            return false;
        }

        return true;
    }

    function getSelectedFilteredFundPKs(){

        var filteredFundSummaryTable = document.all.filteredFundSummaryTable;

        var selectedFilteredFundPKs = "";

        for (var i = 0; i < filteredFundSummaryTable.rows.length; i++)
        {
            if (filteredFundSummaryTable.rows[i].isSelected == "true")
            {
                selectedFilteredFundPKs += filteredFundSummaryTable.rows[i].id + ",";
            }
        }

        return selectedFilteredFundPKs;
    }

    function showSummary() {

        sendTransactionAction("FundTran", "showFilteredFundSummary", "main");
    }

    function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
    	f.action.value = action;

    	f.target = target;

    	f.submit();
    }
</script>

<title>Filtered Fund Relation</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init()">

<form  name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

    <span class="tableHeading">Company Structure</span><br>
    <span class="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:35%; top:0; left:0; z-index:0; overflow:visible">

        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <th class="dataHeading4" align="left" width="20%">Company</th>
            <th class="dataHeading3" align="left" width="20%">Marketing Package</th>
            <th class="dataHeading3" align="left" width="20%">Group Product</th>
			<th class="dataHeading3" align="left" width="20%">Area</th>
            <th class="dataHeading3" align="left" width="20%">Business Contract</th>
 		  </tr>
          <tr>
            <td height="98%" colspan="5">
              <span class="summaryTable" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">
                <table id="companySummaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
		        <%
                    if (productStructureVOs != null)
                    {
                        String selected = "";

                        for (int i = 0; i < productStructureVOs.length; i++)
                        {
                            String currentProductStructurePK = null;
                            currentProductStructurePK = productStructureVOs[i].getProductStructurePK() + "";
                            Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));
                            // Display fields
                            String tdCompanyName = initValue(company.getCompanyName());
                            String tdMPName      = initValue(productStructureVOs[i].getMarketingPackageName());
                            String tdGPName      = initValue(productStructureVOs[i].getGroupProductName());
                            String tdAreaName    = initValue(productStructureVOs[i].getAreaName());
                            String tdBCName      = initValue(productStructureVOs[i].getBusinessContractName());

                            String className = null;
                            if (currentProductStructurePK.equals(selectedProductStructurePK))
                            {
                                className = "highLighted";

                                selected = "true";
                            }
                            else
                            {
                                className = "mainEntry";

                                selected="false";
                            }
		        %>
                <tr class="<%= className %>" selected="<%= selected %>" id="<%= currentProductStructurePK %>"
                    onClick="selectCSRow()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
                    <td align="left" nowrap width="20%">
                        <%= tdCompanyName %>
                    </td>
                    <td align="left" nowrap width="20%">
                        <%= tdMPName %>
                    </td>
                    <td align="left" nowrap width="20%">
                        <%= tdGPName %>
                    </td>
                    <td align="left" nowrap width="20%">
                        <%= tdAreaName %>
                    </td>
                    <td align="left" nowrap width="20%">
                        <%= tdBCName %>
                    </td>
                </tr>
                <%
                        }// end for
                    }// end if
		        %>
                </table>
              </span>
            </td>
          </tr>
		</table>
    </span>

    <br><br>

    <table width="100%">
      <tr>
        <td width="33%">
          &nbsp;
        </td>
        <td align="center" valign="bottom" width="33%">
          <span class="tableHeading">Filtered Fund</span>
        </td>
        <td align="right" valign="bottom" width="33%">
          <input type="button" id="btnSummaryFilteredFunds" name="summaryFunds" value="Filtered Fund Summary" onClick="showSummary()">
        </td>
      </tr>
    </table>

    <span class="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:50%; top:0; left:0; z-index:0; overflow:visible">

        <table width="100%" height="50%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <th class="dataHeading3" align="left" width="33%">Fund Number</th>
            <th class="dataHeading3" align="left" width="33%">Fund Name</th>
            <th class="dataHeading3" align="left" width="33%">Effective Date</th>
 		  </tr>
          <tr>
            <td height="98%" colspan="3">
              <span class="summaryTable">
                <table id="filteredFundSummaryTable" class="scrollableArea" width="100%" border="0" cellspacing="0" cellpadding="0">
		        <%
                    String className = "";
                    String currentFilteredFundPK = "";
                    String tdFundName  = "";
                    String tdFundNumber = "";
                    String tdEffectiveDate = "";

                    if (filteredFundVOs != null)
                    {
                        for (int i = 0; i < filteredFundVOs.length; i++)
                        {
                            currentFilteredFundPK = filteredFundVOs[i].getFilteredFundPK() + "";

                            // Display fields
                            tdFundNumber      = initValue(filteredFundVOs[i].getFundNumber());
                            tdEffectiveDate   = initValue(filteredFundVOs[i].getEffectiveDate());
                            if (!tdEffectiveDate.equals(""))
                            {
                                tdEffectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate(tdEffectiveDate));
                            }
                            
                            long fundKey = filteredFundVOs[i].getFundFK();

                            for (int f = 0; f < fundVOs.length; f++)
                            {
                                if (fundVOs[f].getFundPK() == fundKey)
                                {
                                    tdFundName = fundVOs[f].getName();
                                    break;
                                }
                            }

                            if (attachedFilteredFundPKs.contains(currentFilteredFundPK))
                            {
                                className = "assocEntry";
                            }
                            else
                            {
                                className = "mainEntry";
                            }
                %>
                <tr class="<%= className %>" id="<%= currentFilteredFundPK %>"
                    isSelected="false" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()"
                    onClick="selectESRow()">
                  <td align="left" nowrap width="33%">
                    <%= tdFundNumber %>
                  </td>
                  <td align="left" nowrap width="33%">
                    <%= tdFundName %>
                  </td>
                  <td align="left" nowrap width="33%">
                    <%= tdEffectiveDate %>
                  </td>
                </tr>
                <%
                        }// end for
                    }// end if
		        %>
                </table>
              </span>
            </td>
          </tr>
		</table>

    </span>

    <table>
      <tr>
        <td align="right" valign="bottom">
          <input type="button" name="attach" value="Attach" onClick="attachFilteredFund()">
          <input type="button" name="detach" value="Detach" onClick="detachFilteredFund()">
          <input type="button" name="cancel" value="Cancel" onClick="cancelEdits()">
        </td>
      </tr>
    </table>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

<input type="hidden" name="selectedProductStructurePK" value="<%= selectedProductStructurePK %>">
<input type="hidden" name="selectedFilteredFundPKs" value="">


</form>


</body>
</html>