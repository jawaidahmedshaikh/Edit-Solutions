<%@ page import="edit.common.vo.*,
                 java.util.TreeMap,
                 java.util.Map,
                 java.util.Iterator,
                 fission.utility.*"%>
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input"%>
                 
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<jsp:useBean id="enrollmentPKStringBean" class="edit.common.ui.StringBean" scope="request"/>


<%
     // Pops-up the same dialog as is used for Agent-Search (BA's wanted that same dialog and I didn't want to copy/paste).
     // I put a "flag" in the dialog so that the ultimate selection knows to target the existing Agent (like it normally did)
     // or the LeadServiceAgent they are trying to add (new stuff). GF
    String pageTarget = (String) request.getAttribute("pageTarget"); // Check request/attribute scope first
    
    if (pageTarget == null)
    {
        pageTarget = Util.initString(request.getParameter("pageTarget"), ""); // Check request/param scope second
    }
    
    //String enrollmentPK = Util.initString(request.getParameter("enrollmentPK"), "");
    
    UISearchClientAgentVO[] uiSearchClientAgentVOs = (UISearchClientAgentVO[]) request.getAttribute("uiSearchClientAgentVOs");

    String searchStatus = (String) request.getAttribute("searchStatus");
%>

<%!
	private TreeMap sortSearchByName(UISearchClientAgentVO[] uiSearchClientAgentVOs)
    {
		TreeMap sortedSearch = new TreeMap();

		for (int i = 0; i < uiSearchClientAgentVOs.length; i++)
        {
            UISearchClientVO uiSearchClientVO = uiSearchClientAgentVOs[i].getUISearchClientVO();
            if (uiSearchClientVO != null)
            {
                String clientName = uiSearchClientVO.getName();
                sortedSearch.put(clientName + uiSearchClientVO.getClientDetailPK(), uiSearchClientAgentVOs[i]);
            }
            else
            {
                sortedSearch.put("" + i, uiSearchClientAgentVOs[i]);
            }
		}

		return sortedSearch;
	}
%>

<html>
<head>
<title>Agent Inquire/Search</title>
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
    var searchStatus = "<%= searchStatus %>";
    var lastRowColor = null;
    var pageTarget = "<%= pageTarget %>";

    function init() {

	    f = document.theForm;


        // Check for empty search results
        if (searchStatus == "noData"){

<%--            var span2 = document.getElementById("span2");--%>
<%----%>
<%--            span2.innerHTML = "<h1>Sorry, no results found.</h1>";--%>
        }

        else {

            // Store clientPKs for indexed access
            <%
                if (uiSearchClientAgentVOs != null) {
            %>
                    totalClientCount = <%= uiSearchClientAgentVOs.length %>;
                    clientPKs = new Object(<%= uiSearchClientAgentVOs.length %>);
            <%
                    for (int i = 0; i < uiSearchClientAgentVOs.length; i++){

                        UISearchClientVO uiSearchClientVO = uiSearchClientAgentVOs[i].getUISearchClientVO();

                        if (uiSearchClientVO != null) {

                            long clientPK = uiSearchClientVO.getClientDetailPK();
            %>
                            clientPKs[<%= i%>] = new Object();
                            clientPKs[<%= i%>].clientPK = <%= clientPK %>;
            <%
                        }
                    }
                }
            %>

            // Update the client counts
            updateMessages();
        }
    }

    function loadClientPage(){

    	var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;
        var clientDetailPK = currentRow.id;

        if (pageTarget != "EnrollmentLeadServiceAgent") // We ignore Client loads when searching for EnrollmentLeadServiceAgents.
        {
            opener.top.location.href="/PORTAL/servlet/RequestManager?transaction=ClientDetailTran&action=loadClientAfterSearch&searchValue=" + clientDetailPK;        
            
            closeDialog();            
        }
    }

    function loadAgentDetails(){

    	var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;
        var agentPK = currentRow.id;
        var agentNumber = currentRow.agentNumber;

        if (pageTarget == "EnrollmentLeadServiceAgent")
        {
            f.agentPK.value = agentPK;
            
            f.agentNumber.value = agentNumber;
        
            sendTransactionAction("CaseDetailTran", "showEnrollmentLeadServiceAgentsAfterSearch", "enrollmentLeadServiceAgentsDialog");         
        }
        else
        {
            opener.top.location.href="/PORTAL/servlet/RequestManager?transaction=AgentDetailTran&action=loadAgentDetailAfterSearch&searchValue=" + agentPK;
        }
        
        closeDialog();
    }

    function checkForEnter(){

        var eventObj = window.event;

        if (eventObj.keyCode == 13){

            doSearch();
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

        if (f.clientName.value.length > 0 &&
            f.dobMonth.value.length == 0){

            sendTransactionAction("SearchTran", "findAgentByClientName", "_self");
        }
        else if (f.clientName.value.length > 0 &&
                 f.dobMonth.value.length > 0 &&
                 f.dobDay.value.length > 0 &&
                 f.dobYear.value.length >0) {

            sendTransactionAction("SearchTran", "findAgentByClientNameDOB", "_self");
        }
        else if (f.taxId.value.length > 0){

            sendTransactionAction("SearchTran", "findAgentByTaxId", "_self");
        }
        else if (f.agentId.value.length > 0) {

            sendTransactionAction("SearchTran", "findAgentByAgentId", "_self");
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

        sendTransactionAction("SearchTran", "showAgentSearchDialog", "_self");
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

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table class="formData" width="100%" height="15%" border="0" cellspacing="0" cellpadding="4">
    <tr>
      <td align="right" nowrap>Name:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" size="30" tabindex="1" name="clientName" onFocus="clearFields('All')" onKeyDown="checkForEnter()">
      </td>
      <td align="right" nowrap>Date Of Birth:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" tabindex="2" name="dobMonth" maxlength="2" size="2">
        /
        <input type="text" tabindex="3" name="dobDay" maxlength="2" size="2">
        /
        <input type="text" tabindex="4" name="dobYear" maxlength="4" size="4" onKeyDown="checkForEnter()">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Agent Number:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" size="11" tabindex="5" name="agentId" onFocus="clearFields('All')" onKeyDown="checkForEnter()">
      </td>
      <td align="right" nowrap>Tax Identification Number:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" size="20" tabindex="6" name="taxId" onFocus="clearFields('All')" onKeyDown="checkForEnter()">
      </td>
    </tr>
    <tr>
      <td id="clientMessages" align="right" nowrap>
        &nbsp;
      </td>
      <td colspan="2" id="searchMessage" align="middle" nowrap>
        &nbsp;
      </td>
      <td align="right" nowrap>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" value="Search" tabindex="7" onClick="doSearch()">
        <input type="button" value="Cancel" tabindex="8" onClick="cancelSearch()">
      </td>
    </tr>
  </table>

  <br><br>

  <table id="summaryTable" class="summary" width="100%" height="60%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading" height="2%">
      <th align="left" width="25%">Name</th>
      <th align="left" width="25%">Agent </th>
      <th align="left" width="25%">Agent Type</th>
      <th align="left" width="25%">Status</th>
    </tr>
    <tr>
      <td colspan="4" height="98%">
        <span class="scrollableContent">
          <table id="summaryTableInner" class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
              if (uiSearchClientAgentVOs != null)
              {
    		      Map sortedSearch = sortSearchByName(uiSearchClientAgentVOs);

                  Iterator it = sortedSearch.values().iterator();

                  String color1 = "#E1E1E1";
                  String color2 = "#F9F9F9";
                  String bgColor = color1;

			      while (it.hasNext())
                  {
			          UISearchClientAgentVO uiSearchClientAgentVO = (UISearchClientAgentVO) it.next();

                      UISearchClientVO uiSearchClientVO = uiSearchClientAgentVO.getUISearchClientVO();

                      if (bgColor.equals(color1))
                      {
                            bgColor = color2;
                      }
                      else
                      {
                            bgColor = color1;
                      }

                      UISearchAgentVO[] uiSearchAgentVOs = uiSearchClientAgentVO.getUISearchAgentVO();

                      if (uiSearchClientVO != null)
                      {
            %>
            <tr bgColor="<%= bgColor %>" rowType="contract" id="<%= uiSearchClientVO.getClientDetailPK() %>"
                onDblClick="loadClientPage()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
              <td nowrap align="left" width="25%">
                 <%= uiSearchClientVO.getName() %>
              </td>
              <td nowrap align="left" width="25%">
                 &nbsp;
              </td>
              <td nowrap align="left" width="25%">
                 &nbsp;
              </td>
              <td nowrap align="left" width="25%">
                 &nbsp;
              </td>
            </tr>
            <%
                          if (uiSearchAgentVOs != null)
                          {    
                              for (int j = 0; j < uiSearchAgentVOs.length; j++) 
                              {    
                                  String agentType = Util.initString(uiSearchAgentVOs[j].getAgentTypeCT(), "&nbsp;");
                                  String agentStatus = Util.initString(uiSearchAgentVOs[j].getAgentStatusCT(), "&nbsp;");
                                  if (uiSearchAgentVOs[j].getAgentNumberListCount() > 0)
                                  {
                                      AgentNumberList[] agentNumberList = uiSearchAgentVOs[j].getAgentNumberList();
                                      for (int k = 0; k < agentNumberList.length; k++)
                                      {
            %>
            <tr bgColor="<%= bgColor %>" rowType="contract" id="<%= uiSearchAgentVOs[j].getAgentPK() %>" agentNumber="<%= agentNumberList[k].getAgentNumber() %>"
                onDblClick="loadAgentDetails()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
              <td nowrap align="left" width="25%">
                 &nbsp;
              </td>
              <td nowrap align="left" width="25%">
                 <%= agentNumberList[k].getAgentNumber() %>
              </td>
              <td nowrap align="left" width="25%">
                 <%= agentType %>
              </td>
              <td nowrap align="left" width="25%">
                 <%= agentStatus %>
              </td>
            </tr>
            <%
                                      }
                                  }
                                  else
                                  {
            %>
            <tr bgColor="<%= bgColor %>" rowType="contract" id="<%= uiSearchAgentVOs[j].getAgentPK() %>"
                onDblClick="loadAgentDetails()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
              <td nowrap align="left" width="25%">
                 &nbsp;
              </td>
              <td nowrap align="left" width="25%">
                 &nbsp;
              </td>
              <td nowrap align="left" width="25%">
                 <%= agentType %>
              </td>
              <td nowrap align="left" width="25%">
                 <%= agentStatus %>
              </td>
            </tr>
            <%
                                  }
                              } // end for
                          } // end if
                      } // end for
                  }// end if
              }
            %>
          </table>
        </span>
      </td>
    </tr>
  </table>
</span>

<span id="span3" style="position:relative; width:100%; height:10%; top:0; left:0; z-index:0">
  <table width="100%">
    <tr>
      <td align="right">
<%--        <input tabindex="9" type="button" value="<Prev" onClick="scrollToPrevClient()">--%>
<%--        <input tabindex="10" type="button" value="Next>" onClick="scrollToNextClient()">--%>
        &nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp;
        <input tabindex="11" type="button" value="Close" onClick="closeDialog()">
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">
<input type="hidden" name="pageTarget" value="<%= pageTarget %>"/>
<input type="hidden" name="clientDetailPK" value=""/>
<input type="hidden" name="agentPK" value=""/>
<input type="hidden" name="agentNumber" value=""/>
<input:hidden bean="enrollmentPKStringBean" name="enrollmentPK"/>

</form>
</body>
</html>
