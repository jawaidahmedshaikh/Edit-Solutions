<%@ page import="edit.common.vo.*,
                 fission.beans.SessionBean,
                 fission.beans.PageBean,
                 java.util.*,
                 fission.utility.Util,
                 edit.portal.common.session.UserSession,
                 engine.ProductStructure"%>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%@ taglib uri="/WEB-INF/SecurityTaglib.tld" prefix="security" %>


<%
    // not needed with jsp tag
    //CompanyStructureVO[] companyStructureVOs = (CompanyStructureVO[]) request.getAttribute("companyStructureVOs");
    SearchResponseVO[] searchResponseVOs = (SearchResponseVO[]) request.getAttribute("searchClientContractVOs");
    String segmentStatus = Util.initString((String) request.getAttribute("segmentStatus"), "");
    String searchStatus = (String) request.getAttribute("searchStatus");
    String lastAction = (String) request.getAttribute("lastAction");
    String segmentPK = (String) request.getAttribute("segmentPK");
    String message = (String) request.getAttribute("message");

//    if (searchResponseVOs != null)
//    {
//        searchResponseVOs = (SearchResponseVO[]) Util.sortObjects(searchResponseVOs, new String[] {"getClientName", "getClientDetailFK", "getOverrideStatus", "getContractNumber"});
//    }
%>

<html>
<head>
<title>Inquire/Search</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<!-- ***** JAVASCRIPT *****  -->

<script language="Javascript1.2">

    var f = null;
    var clientPKs = null;
    var currentClientIndex = 0;
    var lastClientIndex    = 0;
    var totalClientCount   = 0;
    var segmentStatus = "<%= segmentStatus %>";
    var searchStatus = "<%= searchStatus %>";
    var lastAction = "<%= lastAction %>";
    var segmentPK = "<%= segmentPK %>";
    var message = "<%= message %>";
    var lastRowColor = null;

    function init() {

	    f = document.theForm;

        alertMessage();

        // Check for empty search results
        if (searchStatus == "noData"){

<%--            var span2 = document.getElementById("span2");--%>
<%----%>
<%--            span2.innerHTML = "<h1>Sorry, no results found.</h1>";--%>
        }

        else {

            // You might have to forward to Quote or Contract
            if (lastAction == "findByCompanyStructureContractNumber"){

                if (segmentStatus == "Pending" || segmentStatus == "IssuedPendingReq" ||
                    segmentStatus == "Decline" || segmentStatus == "Incomplete" ||
                    segmentStatus == "Postponed" || segmentStatus == "Withdrawn" ||
                    segmentStatus == "Submitted" || segmentStatus == "Submit Pending" ||
                    segmentStatus == "Initial Underwriting Review" || segmentStatus == "Approved" ||
                    segmentStatus == "Reinstatement - Pending" || segmentStatus == "Declined - Medical" ||
                    segmentStatus == "Declined - Eligibility" || segmentStatus == "Declined - Requirements" ||
                    segmentStatus == "PendingIssue" || segmentStatus == "Reopen") {

                    loadQuote(segmentPK);

                    closeDialog();
                }

                else {

                      loadContract(segmentPK);

                      closeDialog();
                }
            }

            // Store clientPKs for indexed access
            <%
                if (searchResponseVOs != null) {
            %>
                    totalClientCount = <%= searchResponseVOs.length %>;
                    clientPKs = new Object(<%= searchResponseVOs.length %>);
            <%
                    for (int i = 0; i < searchResponseVOs.length; i++){
                        long clientPK = searchResponseVOs[i].getClientDetailFK();
            %>
                        clientPKs[<%= i%>] = new Object();
                        clientPKs[<%= i%>].clientPK = <%= clientPK %>;
            <%

                    }
                }
            %>

            // Update the client counts
            updateMessages();
        }
    }

    function alertMessage()
    {
        if (message != "null")
        {
            alert(message);
        }
    }

    function loadContract(segmentPK){

        opener.top.location.href="/PORTAL/servlet/RequestManager?transaction=ContractDetailTran&action=loadContractAfterSearch&searchValue=" + segmentPK;
    }

    function loadQuote(segmentPK){

        opener.top.location.href="/PORTAL/servlet/RequestManager?transaction=QuoteDetailTran&action=loadQuoteAfterSearch&searchValue=" + segmentPK;
    }

    function loadClientPage(clientDetailPK){

        opener.top.location.href="/PORTAL/servlet/RequestManager?transaction=ClientDetailTran&action=loadClientAfterSearch&searchValue=" + clientDetailPK;

        closeDialog();
    }

    function checkForEnter(){

        var eventObj = window.event;

        if (eventObj.keyCode == 13){

            doSearch();
        }
    }

    function loadSegmentPage(contractStatus, segmentPK){

        if (contractStatus == "Pending" || contractStatus == "IssuedPendingReq" ||
            contractStatus == "Decline" || contractStatus == "Incomplete" ||
            contractStatus == "Postponed" || contractStatus == "Withdrawn" ||
            contractStatus == "Submitted" || contractStatus == "Submit Pending" ||
            contractStatus == "Initial Underwriting Review" || contractStatus == "Approved" ||
            contractStatus == "New Business Reinstatement Pending" || contractStatus == "Declined - Medical" ||
            contractStatus == "Declined - Eligibility" || contractStatus == "Declined - Requirements" ||
            contractStatus == "PendingIssue" || contractStatus == "Reopen")
        {
            loadQuote(segmentPK);

            closeDialog();
        }

        else {

            loadContract(segmentPK);

            closeDialog();
        }
    }

    function updateMessages(){

        var tdMessages = document.getElementById("clientMessages");
        var currentClientCount = 0;

        if (currentClientIndex == 0 && totalClientCount != 0){

            currentClientCount = 1;
        }
        else if (currentClientIndex == 0 && totalClientCount == 0){

            currentClientCount = 0;
        }
        else {

            currentClientCount = currentClientIndex + 1;
        }

        tdMessages.innerHTML = "Found: " + totalClientCount;
    }

    function scrollToNextClient(){

        if (currentClientIndex == totalClientCount - 1){

            return;
        }
        else {

            currentClientIndex++;

            var clientPK = clientPKs[currentClientIndex].clientPK;

            scrollClientIntoView(clientPK);

            updateMessages();
        }
    }

    function scrollToPrevClient(){

        if (currentClientIndex == 0){

            return;
        }
        else {

            currentClientIndex--;

            var clientPK = clientPKs[currentClientIndex].clientPK;

            scrollClientIntoView(clientPK);

            updateMessages();
        }
    }

    function scrollClientIntoView(clientPK) {

        var clientRow = document.getElementById(clientPK);

        if (clientRow != null) {

            clientRow.scrollIntoView(true);
        }
    }

    function closeDialog(){

        window.close();
    }

    function doSearch(){

        var tdSearchMessage = document.getElementById("searchMessage");
        tdSearchMessage.innerHTML = "<font face='arial' size='3' color='blue'>Searching ...</font>";

        if (f.clientName.value.length > 0){

            sendTransactionAction("SearchTran", "findByClientName", "_self");
        }
        else if (f.contractNumber.value.length > 0 && f.companyStructurePK.selectedIndex > 0){

            sendTransactionAction("SearchTran", "findByCompanyStructureContractNumber", "_self");
        }
        else if (f.contractNumber.value.length > 0 && f.companyStructurePK.selectedIndex == 0) {

            sendTransactionAction("SearchTran", "findByContractNumberOnly", "_self");
        }
        else if (f.taxId.value.length > 0){

            sendTransactionAction("SearchTran", "findByTaxId", "_self");
        }
        else {

            var tdSearchMessage = document.getElementById("searchMessage");
            tdSearchMessage.innerHTML = "&nbsp;"

            alert("Invalid search parameters.");
        }
    }

    function clearFields(whichOnes){

        if (whichOnes == "NameAndTax"){

            f.clientName.value = "";
            f.taxId.value = "";
        }
        else if (whichOnes == "All"){

            resetForm();
        }
    }

    function resetForm(){

        f.reset();
    }

    function highlightRow() {

    	var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        lastRowColor = currentRow.style.backgroundColor;

        currentRow.style.backgroundColor = "#AAAAAA";
    }

    function unhighlightRow(){

    	var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

         currentRow.style.backgroundColor = lastRowColor;
    }

    function cancelSearch(){

        sendTransactionAction("SearchTran", "showSearchDialog", "_self");
    }

    function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
    	f.action.value = action;

    	f.target = target;

    	f.submit();
    }
</script>

<!-- ****** STYLE CODE ***** //-->
<style>

	.highLighted {

		background-color: #FFFFCC;
	}

    .attached {

		background-color: #CCFFCC;
	}
</style>

</head>

<body class="mainTheme" onLoad="init()">

<form  name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
    <table class="formData" width="100%" height="25%" border="0" cellspacing="0" cellpadding="4">
        <tr>
            <td align="right" nowrap>
                Company:
            </td>


            <td align="left" nowrap>

                <%--
                    Select pulldown for CompanyStructures.  dynamicAttributes is optional.
                    This will get the product-type company structures that the user is
                    allowed.  If development mode, then all product-type companies are displayed.
                    Note - the selected company structure PK will be assigned to the name
                    variable.

                    In the Tran java class, there is no magic.  You must manually put the
                    selected PK value into the UserSession.  There is a helper method
                    that will do this for you.

                        CompanyStructure.setCurrentCompanyStructureInSession(
                                appReqBlock,
                                "companyStructurePK");

                    If the variable is null or 0, it won't change the current
                    company structure in the session.
                --%>
                <security:selectForUserCompanyStructures
                        name="companyStructurePK"
                        dynamicAttributes="tabindex='1' onFocus='clearFields(\"NameAndTax\")' onKeyDown='checkForEnter()'"
                />

            </td>

            <td align="right" nowrap>
                Contract:
            </td>
            <td align="left" nowrap>
                <input tabindex="2" type="text" size="20" name="contractNumber" onFocus="clearFields('NameAndTax')" onKeyDown="checkForEnter()">
            </td>
            <td>
                &nbsp;
            </td>
            <td>
                &nbsp;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap>
                Name:
            </td>
            <td align="left" nowrap>
                <input type="text" size="30" tabindex="3" name="clientName" onFocus="clearFields('All')" onKeyDown="checkForEnter()">
            </td>
            <td align="right" nowrap>
                Tax Identification Number:
            </td>
            <td align="left" nowrap>
                <input type="text" size="20" tabindex="4" name="taxId" onFocus="clearFields('All')" onKeyDown="checkForEnter()">
            </td>
            <td>
                &nbsp;
            </td>
            <td>
                &nbsp;
            </td>
        </tr>

        <tr>
            <td id="clientMessages" align="right" nowrap>
                &nbsp;
            </td>
            <td id="searchMessage" align="middle" nowrap>
                &nbsp;
            </td>
            <td align="right" colspan="3">
                &nbsp;
            </td>
            <td align="right" nowrap width="15%">
                <input type="button" value="Search" tabindex="6" onClick="doSearch()">
                <input type="button" value="Cancel" tabindex="7" onClick="cancelSearch()">
            </td>
        </tr>


    </table>

    <br><br>


        <table id="summaryTable" class="summary" width="100%" height="60%" border="0" cellspacing="0" cellpadding="0">
           <tr class="heading" height="10">
              <th width="11%" align="left">Name</th>
              <th width="11%" align="left">Tax Id</th>
              <th width="11%" align="left">DateOfBirth</th>
              <th width="11%" align="left">Client Status</th>
              <th width="11%" align="left">Group #</th>
              <th width="12%" align="left">Bus Cntrct</th>
              <th width="11%" align="left">Contract #</th>
              <th width="11%" align="left">Status</th>
			  <th width="11%" align="left">Relationship</th>
		   </tr>
           <tr>
            <td colspan="9" height="98%">
                <span class="scrollableContent">
                    <table id="summaryTableInner" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">


            <%
                if (searchResponseVOs != null)
                {

                    String defaultCharacter = "&nbsp";

                    String lastTaxId = null;

                    String color1 = "#E1E1E1";
                    String color2 = "#F9F9F9";
                    String bgColor = color1;


                    for (int i = 0; i < searchResponseVOs.length; i++)
                    {
                        String clientName = Util.initString(searchResponseVOs[i].getClientName(), defaultCharacter);
                        String clientStatus = Util.initString(searchResponseVOs[i].getClientStatus(), defaultCharacter);
                        String taxId = Util.initString(searchResponseVOs[i].getTaxIdentification(), defaultCharacter);
                        String thisTaxId = taxId;
                        String dateOfBirth = Util.initString(searchResponseVOs[i].getDateOfBirth(), defaultCharacter);
                        String contractGroupNumber  = Util.initString(searchResponseVOs[i].getContractGroupNumber(), defaultCharacter);

                        if (lastTaxId == null) lastTaxId = thisTaxId;

                        if (! lastTaxId.equals(thisTaxId))
                        {
                            if (bgColor.equals(color1))
                            {
                                bgColor = color2;
                            }
                            else
                            {
                                bgColor = color1;
                            }
                        }

                        lastTaxId = thisTaxId;
            %>
                        <tr bgColor="<%= bgColor %>" rowType="contract" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
                            <td width="11%" align="left">
                               <a href ="javascript:loadClientPage('<%= searchResponseVOs[i].getClientDetailFK() %>')"><%= clientName %></a>
                            </td>
                            <td width="11%" align="left">
                                <%= taxId %>
                            </td>
                            <td width="12%" align="left">
                                <%= dateOfBirth %>
                            </td>

                            <td width="11%" align="left">
                                <%= clientStatus %>
                            </td>
                            <td width="11%" align="left">
                                 <%= contractGroupNumber %>
                            </td>
                            <td width="11%" align="left">
                                &nbsp;
                            </td>
                            <td width="11%" align="left">
                                &nbsp;
                            </td>
                            <td width="11%" align="left">
                                &nbsp;
                            </td>
                            <td width="25%" align="left">
                                &nbsp;
                            </td>
                        </tr>
            <%
                        String overrideStatus = "";
                        if (searchResponseVOs[i].getSearchResponseContractInfoCount() > 0)
                        {
                            SearchResponseContractInfo[] contractInfo = searchResponseVOs[i].getSearchResponseContractInfo();
                            for (int j = 0; j < contractInfo.length; j++)
                            {
                                overrideStatus = Util.initString(contractInfo[j].getOverrideStatus(), defaultCharacter);
                                if (!overrideStatus.equalsIgnoreCase("D") && !overrideStatus.equalsIgnoreCase("Z"))
                                {
                                    String businessContract = Util.initString(contractInfo[j].getBusinessContractName(), defaultCharacter);
                                    String relationship = Util.initString(contractInfo[j].getRoleType(), defaultCharacter);
                                    String contractNumber = Util.initString(contractInfo[j].getContractNumber(), defaultCharacter);
                                    String optionCode = Util.initString(contractInfo[j].getOptionCode(), defaultCharacter);
                                    String status = Util.initString(contractInfo[j].getSegmentStatus(), defaultCharacter);
            %>
                        <tr bgColor="<%= bgColor %>" rowType="contract" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
                            <td width="11%" align="left">
                                &nbsp;
                            </td>
                            <td width="11%" align="left">
                                &nbsp;
                            </td>
                            <td width="11%" align="left">
                                &nbsp;
                            </td>
                            <td width="11%" align="left">
                                &nbsp;
                            </td>
                            <td width="11%" align="left">
                                &nbsp;
                            </td>
                            <td width="12%" align="left">
                                <%= businessContract %>
                            </td>
                            <td width="11%" align="left">
                                <a href ="javascript:loadSegmentPage('<%= status %>', '<%= contractInfo[j].getSegmentFK() %>')"><%= contractNumber %></a>
                            </td>
                            <td width="11%" align="left">
                                <%= status %>&nbsp;
                            </td>
                            <td width="11%" align="left">
                                <%= relationship %>
                            </td>
                        </tr>
            <%
                                }
                            }
                        }
                    }
                }
            %>
                            <tr class="filler">
                                <td colspan="9">
                                     &nbsp;
                                </td>
                            </tr>
                        </table>
                    </span>
                </td>
            </tr>
   		</table>

<table width="100%">
    <tr>
        <td align="right">
        &nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp;
        <input tabindex="10" type="button" value="Close" onClick="closeDialog()">
        </td>
    </tr>
</table>
<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

</form>
</body>
</html>
