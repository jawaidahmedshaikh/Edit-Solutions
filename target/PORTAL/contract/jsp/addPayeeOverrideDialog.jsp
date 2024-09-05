<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.*" %>


<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    String searchStatus = "";

    ClientDetailVO[] clientDetailVOs = (ClientDetailVO[])session.getAttribute("clientDetailVOs");
//    if (clientDetailVOs == null)
//    {
//        searchStatus = "noData";
//    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");

//    CodeTableVO[] lifeRoles = codeTableWrapper.getCodeTableEntries("LIFERELATIONTYPE", Long.parseLong(companyStructureId));
    CodeTableVO[] options       = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));

    String optionId = contractMainSessionBean.getPageBean("formBean").getValue("optionId");

    PageBean formBean = (PageBean) request.getAttribute("formBean");

    String segmentFK = formBean.getValue("segmentFK");
    String filterMessage = formBean.getValue("filterMessage");
    String rowId = formBean.getValue("rowId");
    String selectedSegmentPK = formBean.getValue("selectedSegmentPK");

    String dob = "";
    String effectiveDate = "";

%>

<html>

<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<!-- ***** JAVASCRIPT *****  -->


<script language="Javascript1.2">

    var f = null;
    var searchStatus = "<%= searchStatus %>";

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
        else
        {
            currentRow.style.backgroundColor = colorHighlighted;
        }
    }

    function init() {

	    f = document.contractClientAddForm;

        // Check for empty search results
        if (searchStatus == "noData"){


            alert("Sorry, no clients found.");
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

<%--    function showClientDetail(){--%>
<%----%>
<%--        openDialog("","clientDetail","top=0,left=0,width="+ screen.width + ",height=" + screen.height,"PortalLoginTran","quickAddClient");--%>
<%--    }--%>

	function openDialog(theURL,winName,features,transaction,action) {

	  dialog = window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, winName);
	}

    function checkForEnter(){

        var eventObj = window.event;

        if (eventObj.keyCode == 13){

            doSearch();
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

        window.close();
    }

    function doClientSearch(){

        if (f.name.value.length > 0 && f.dob.value.length > 0)
        {
            sendTransactionAction("ContractDetailTran", "findPayeeClientsByNameDOB", "_self");
        }
        else if (f.name.value.length > 0 && f.dob.value == "") {

            sendTransactionAction("ContractDetailTran", "findPayeeClientsByName", "_self");
        }
        else if (f.taxIdentification.value.length > 0){

            sendTransactionAction("ContractDetailTran", "findPayeeClientByTaxId", "_self");
        }
        else {

            var tdSearchMessage = document.getElementById("searchMessage");
            tdSearchMessage.innerHTML = "&nbsp;"

            alert("Invalid search parameters.");
        }
    }

    function buildPayeeOverride(){

<%--        var selectedRoles = getSelectedRoles();--%>
<%--        f.selectedRoles.value = selectedRoles;--%>

        if (f.optionId.selectedIndex == 0) {

            alert("Please Select Coverage");
        }
        else if (f.selectedClientDetailPK.value == "")
        {
            alert("Please Select Client");
        }
        else if (f.allocationPercent.value == "")
        {
            alert("Please Enter Allocation Percent");
        }
        else
        {
            sendTransactionAction("ContractDetailTran", "saveSelectedClientForPayeeOvrd", "payeeDialog");

            window.close();
        }
    }

<%--    function getSelectedRoles(){--%>
<%----%>
<%--        var table=document.all.roleTable;--%>
<%----%>
<%--        var selectedRoles = "";--%>
<%----%>
<%--        for (var i = 0; i < table.rows.length; i++) {--%>
<%----%>
<%--            if (table.rows[i].isSelected == "true") {--%>
<%----%>
<%--                selectedRoles += table.rows[i].roleId + ",";--%>
<%--            }--%>
<%--        }--%>
<%----%>
<%--        return selectedRoles;--%>
<%--    }--%>

    function highlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = colorMouseOver;
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
        else
        {
            currentRow.style.backgroundColor = colorHighlighted;
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
<title>Add Payee Overrides</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1; background-color:#DDDDDD">

<form  name="contractClientAddForm" method="post" action="/PORTAL/servlet/RequestManager">

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

      <td align="right" nowrap>Date Of Birth:
           <input type="text" name="dob" value="<%= dob %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.dob', f.dob.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="4">
        <input type="button" value="Enter" onClick="doClientSearch()" tabindex="6">
      </td>
    </tr>
  </table>
</span>
<br>
  <table id = "summaryTable" class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading" >
      <th align="left">Name</th>
      <th align="left">Tax Id</th>
      <th align="left">Date Of Birth</th>
      <th align="left">City</th>
      <th align="left">State</th>
    </tr>
  </table>
 <span id="span2" class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:15%; top:0; left:0; background-color:#BBBBBB">
   <table class="summary" id="selectedClientSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">

      <%
//        Map clientDataPageBeans = contractClientAddSessionBean.getPageBeans();
          if (clientDetailVOs != null)
          {
              for (int i = 0; i < clientDetailVOs.length; i++)
              {
                   String clientDetailPK = clientDetailVOs[i].getClientDetailPK() + "";
                   String lastName = Util.initString(clientDetailVOs[i].getLastName(), "");
                   String firstName = Util.initString(clientDetailVOs[i].getFirstName(), "");
                   String corporateName = clientDetailVOs[i].getCorporateName();
                   String name = "";
                   if (lastName.equals(""))
                   {
                       name = corporateName;
                   }
                   else
                   {
                       name = lastName + ", " + firstName;
                   }
                   String taxIdNumber = Util.initString(clientDetailVOs[i].getTaxIdentification(), "");
                   String dateOfBirth = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(clientDetailVOs[i].getBirthDate());
                   String city = "";
                   String state = "";
                   if (clientDetailVOs[i].getClientAddressVOCount() > 0)
                   {
                      city = Util.initString(clientDetailVOs[i].getClientAddressVO(0).getCity(), "");
                      state = Util.initString(clientDetailVOs[i].getClientAddressVO(0).getStateCT(), "");
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
        <%= city %>
      </td>
      <td nowrap>
        <%= state %>
      </td>
    </tr>
      <%
            }
        }
      %>
  </table>
</span>
<span id="span3" style="position:relative; width:100%; height:35%; top:0; left:0; z-index:0">
  <table width="100%" height="2%">
    <tr>
        &nbsp;&nbsp;
<%--      <td align="right">--%>
<%--        <input type="button" value="Detail" onClick="showClientDetail()">--%>
<%--      </td>--%>
    </tr>
  </table>
  <table align="left" width="100%" height="98%">
    <tr>
      <td align="left" nowrap width="19%">Allocation %:
        <input type="text" name="allocationPercent" maxlength="5" size="5" tabindex="7">
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Effective Date:
        <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
       </td>
        &nbsp;&nbsp;
  	    Coverage:&nbsp;
        <select name="optionId" tabindex="11">
          <option selected value="Please Select"> Please Select </option>
            <%

                for(int i = 0; i < options.length; i++) {

                    String codeTablePK = options[i].getCodeTablePK() + "";
                    String codeDesc    = options[i].getCodeDesc();
                    String code        = options[i].getCode();

                    if (optionId.equalsIgnoreCase(code))
                    {
                        out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                    }
                }


            %>
        </select>
      </td>
    </tr>
  </table>
</span>

<span id="span4" style="position:relative; width:100%; height:10%; top:0; left:0; z-index:0">
  <table width="100%">
    <tr>
      <td align="right">
        <input type="button" value="Enter" onClick="buildPayeeOverride()">
        <input type="button" value="Cancel" onClick="closeDialog()">
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">
<input type="hidden" name="selectedClientDetailPK" value="">
<input type="hidden" name="segmentFK" value="<%= segmentFK %>">
<input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">
<input type="hidden" name="filterMessage" value="<%= filterMessage %>">
<input type="hidden" name="rowId" value="<%= rowId %>">
<input type="hidden" name="selectedSegmentPK" value="<%= selectedSegmentPK %>">

</form>
</body>
</html>
