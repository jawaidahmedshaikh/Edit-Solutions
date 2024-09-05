<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.*" %>

<%
    String agentMessage = (String) request.getAttribute("agentMessage");
    if (agentMessage == null)
    {
        agentMessage = "";
    }

    String searchStatus = (String) request.getAttribute("searchStatus");
    if (searchStatus == null)
    {
        searchStatus = "";
    }

    String dob = "";
%>

<%!
    private String getClientDetailName(ClientDetailVO clientDetailVO)
    {
        String name = null;

        if (clientDetailVO.getLastName() == null)
        {
            name = clientDetailVO.getCorporateName();
        }
        else
        {
            name = clientDetailVO.getLastName() + ", " + clientDetailVO.getFirstName();
            String middleName = Util.initString(clientDetailVO.getMiddleName(), "");
            String namePrefix = Util.initString(clientDetailVO.getNamePrefix(), "");
            String nameSuffix = Util.initString(clientDetailVO.getNameSuffix(), "");
            if (!middleName.equals(""))
            {
                name = name + ", " + clientDetailVO.getMiddleName();
            }
            if (!namePrefix.equals(""))
            {
                name = name + ", " + clientDetailVO.getNamePrefix();
            }
            if (!nameSuffix.equals(""))
            {
                name = name + ", " + clientDetailVO.getNameSuffix();
            }
        }

        return name;
    }

%>

<html>

<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<!-- ***** JAVASCRIPT *****  -->

<script language="Javascript1.2">

    var f = null;
    var searchStatus = "<%= searchStatus %>";
    var agentMessage = "<%= agentMessage %>";

    var colorMouseOver = "#00BB00";
    var colorHighlighted = "#FFFFCC";
    var colorMainEntry   = "#BBBBBB";

    function highlightRoleRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = colorMouseOver;
    }

    function unhighlightRoleRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected != "true") {

            if (className == "highLighted") {

                currentRow.style.backgroundColor = colorHighlighted;
            }

            else {

                currentRow.style.backgroundColor = colorMainEntry;
            }
        }
        else {

            currentRow.style.backgroundColor = colorHighlighted;
        }
    }

    function init() {

	    f = document.agentAddForm;

        // Check for empty search results
        if (searchStatus == "noData"){

            alert("Sorry, no clients found.");
        }
        else if (searchStatus != "") {

            alert(searchStatus);
        }
    }

    function selectRoleRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;
        var className = currentRow.className;

        if (currentRow.isSelected == "false") {

            currentRow.style.backgroundColor = "#FFFFCC";
            currentRow.isSelected = "true";
        }
        else {

            if (className == "mainEntry") {

                currentRow.style.backgroundColor = "#BBBBBB";
            }

            currentRow.isSelected = "false";
        }
    }

	function openDialog(theURL,winName,features,transaction,action) {

	  dialog = window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, winName);
	}

    function checkForEnter(){

        var eventObj = window.event;

        if (eventObj.keyCode == 13){

            doAgentSearch();
        }
    }

    function selectClient() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;
        var className = currentRow.className;

        f.selectedClientDetailPK.value = currentRow.id;

        if (currentRow.isSelected == "false") {

            currentRow.style.backgroundColor = "#FFFFCC";
            currentRow.isSelected = "true";
        }
        else {

            if (className == "mainEntry") {

                currentRow.style.backgroundColor = "#BBBBBB";
            }

            currentRow.isSelected = "false";
        }
    }

    function updateMessages() {

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

        tdMessages.innerHTML = "Client " + currentClientCount + "/" + totalClientCount;
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

        sendTransactionAction("AgentDetailTran", "cancelAgentAdd", "_self");
        window.close();
    }

    function doAgentSearch(){

        if (f.name.value.length > 0 && f.dob.value.length > 0)
        {
            sendTransactionAction("AgentDetailTran", "findAgentsByNameDOB", "_self");
        }
        else if (f.name.value.length > 0 &&
                 f.dob.value.length == 0) {

            sendTransactionAction("AgentDetailTran", "findAgentsByName", "_self");
        }
        else if (f.taxIdentification.value.length > 0){

            sendTransactionAction("AgentDetailTran", "findAgentsByTaxId", "_self");
        }
        else if (f.agentId.value.length > 0) {

            sendTransactionAction("AgentDetailTran", "findAgentsByAgentId", "_self");
        }
        else {

            var tdSearchMessage = document.getElementById("searchMessage");
            tdSearchMessage.innerHTML = "&nbsp;"

            alert("Invalid search parameters.");
        }
    }

    function buildAgentRole(){

        if (f.selectedClientDetailPK.value == "") {

            alert("Please Select Client Record for Agent");
        }
        else {

            sendTransactionAction("AgentDetailTran", "buildAgentRole", "contentIFrame");

            window.close();
        }
    }

    function highlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = colorHighlighted;
    }

    function unhighlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected != "true") {

            if (className == "highLighted") {

                currentRow.style.backgroundColor = colorHighlighted;
            }

            else {

                currentRow.style.backgroundColor = colorMainEntry;
            }
        }
    }

    function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
    	f.action.value = action;

    	f.target = target;

    	f.submit();
    }

</script>

<head>
<title>Agent Add</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1">

<form  name="agentAddForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="span1" style="border-style:solid; border-width:1;  position:relative; width:100%; height:5%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="4">
    <tr>
      <td align="right" nowrap>
        Tax Identification Number:
      </td>
      <td align="left" nowrap colspan = "3">
        <input type="text" name="taxIdentification" size="11" tabindex="1">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>
        Name:
      </td>
      <td align="left" nowrap>
        <input type="text" name="name" size="30" tabindex="2">
      </td>
      <td align="right" nowrap>Date Of Birth:&nbsp;</td>
        <td align="left" nowrap>
          <input type="text" name="dob" size='10' maxlength="10" value="<%= dob %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
          <a href="javascript:show_calendar('f.dob', f.dob.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="4">
        <input type="button" value="Search" onClick="doAgentSearch()">
      </td>
    </tr>
  </table>
</span>

<span id="span2" style="border-style:solid; border-width:1; position:relative; width:100%; height:60%; top:0; left:0; z-index:0; overflow:scroll">
  <table class="summaryArea" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr height="10">
      <th align="left" width="20%">Name</th>
      <th align="left" width="20%">Tax Id</th>
      <th align="left" width="20%">Date Of Birth</th>
      <th align="left" width="20%">State</th>
    </tr>
      <%
        ClientDetailVO[] clientDetailVOs = (ClientDetailVO[]) session.getAttribute("clientDetailVOs");

        if (clientDetailVOs != null) {

            for (int c = 0; c < clientDetailVOs.length; c++) {

                String clientDetailPK = clientDetailVOs[c].getClientDetailPK() + "";
                String name = getClientDetailName(clientDetailVOs[c]);

                String taxIdNumber = clientDetailVOs[c].getTaxIdentification();
                String dateOfBirth = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(clientDetailVOs[c].getBirthDate());
                String state = "";
                ClientAddressVO[] clientAddresses = clientDetailVOs[c].getClientAddressVO();
                if (clientAddresses != null) {

                    for (int d = 0; d < clientAddresses.length; d++) {

                        String addressType = clientAddresses[d].getAddressTypeCT();
                        String termDate = clientAddresses[d].getTerminationDate();
                        if (addressType.equalsIgnoreCase("Primary") &&
                            (termDate.equals(EDITDate.DEFAULT_MAX_DATE) ||
                             termDate.equals(""))) {

                            state = clientAddresses[d].getStateCT();
                            break;
                        }
                    }
                }

                String className = "mainEntry";
                String clientSelected = "false";

      %>
    <tr class="<%= className %>" id="<%= clientDetailPK %>" onClick="selectClient()"
        onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" isSelected="<%= clientSelected %>">
      <td nowrap>
        <%= name %>
      </td>
      <td nowrap>
        <%= taxIdNumber %>
      </td>
      <td nowrap>
        <%= dateOfBirth %>
      </td>
      <td nowrap>
        <%= state %>
      </td>
    </tr>
      <%
            }
        }
      %>

    <tr class="filler"> <!-- A dummy row to help with sizing -->
        <td colspan="4">
            &nbsp;
        </td>
    </tr>

  </table>
</span>

<span id="span4" style="position:relative; width:100%; height:5%; top:0; left:0; z-index:0">
  <table width="100%">
    <tr>
      <td align="right">
        <input type="button" value="Enter" onClick="buildAgentRole()">
        <input type="button" value="Cancel" onClick="closeDialog()">
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">
<input type="hidden" name="selectedClientDetailPK" value="">
<input type="hidden" name="selectedRoles" value="">

</form>
</body>
</html>
