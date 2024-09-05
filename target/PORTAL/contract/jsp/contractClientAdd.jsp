<%-- /!-- ****** JAVA CODE ***** //-->--%>

<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.*" %>

<jsp:useBean id="contractClientAddSessionBean"
             class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="contractMainSessionBean"
             class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="contractRiders"
             class="fission.beans.SessionBean" scope="session"/>

<%
    String searchStatus = contractClientAddSessionBean.getValue("searchStatus");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");

    CodeTableVO[] lifeRoles = codeTableWrapper.getCodeTableEntries("LIFERELATIONTYPE", Long.parseLong(companyStructureId));
    CodeTableVO[] options = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));

    String optionId = contractMainSessionBean.getPageBean("formBean").getValue("optionId");

    String nameSearch = search.business.Lookup.FIND_BY_CLIENT_NAME;
    String taxIdSearch = search.business.Lookup.FIND_BY_TAX_ID;


    EDITDate now = new EDITDate();
    String effectiveDate = "" + now.getMMDDYYYYDate();
    String dob = "";

    Map allRiders = contractRiders.getPageBeans();
%>

<html>

<!-- ***** JAVASCRIPT *****  contract.jsp.contractClientAdd.jsp -->

<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="/PORTAL/common/javascript/jquery.maskedinput.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">

<script>
  $('document').ready(function() {
    $( ".datepicker" ).datepicker({
	       dateFormat: "mm/dd/yy",
	       altFormat: "yy/mm/dd",
	       showOn: "button",
	       buttonImage: "/PORTAL/common/images/calendarIcon.gif" });
    $( ".datepicker" ).mask("99/99/9999", {placedholder:"mm/dd/yyyy"});
  });
</script>

<script>

    var f = null;
    var searchStatus = "<%= searchStatus %>";
    var nameSearch = "<%= nameSearch %>";
    var taxIdSearch = "<%= taxIdSearch %>";
    var gCity = "";
	var gState = "";
	var gAddressLine1 = "";
	var gZipCode = "";
	
    var colorMouseOver = "#00BB00";
    var colorHighlighted = "#FFFFCC";
    var colorMainEntry = "#BBBBBB";

    var now = "<%=now.getMMDDYYYYDate()%>";
    function checkEffectiveDate(efDateElement, date) {
        var now = "<%=now.getMMDDYYYYDate()%>";
        var nowYYYYMMDD = convertMDYtoYMDNoSlash(now);
        var dateFormatted = convertMDYtoYMDNoSlash(date);
        var greaterThanOrEqual = Number(dateFormatted) >= Number(nowYYYYMMDD);

        if (!greaterThanOrEqual) {
            alert("Effective Date may not be back-dated.")
            efDateElement.value = now;
            return false;
        } else {
            return true;
        }
    }


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
            } else {

                currentRow.style.backgroundColor = colorMainEntry;
            }
        } else {
            currentRow.style.backgroundColor = colorHighlighted;
        }
    }

    function init() {

        f = document.contractClientAddForm;

        // Check for empty search results
        if (searchStatus == "noData") {


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
        } else {

            if (className == "mainEntry") {

                currentRow.style.backgroundColor = "#BBBBBB";
            }

            currentRow.isSelected = "false";
        }
    }

    function openDialog(theURL, winName, features, transaction, action) {

        dialog = window.open(theURL, winName, features);

        sendTransactionAction(transaction, action, winName);
    }

    function checkForEnter() {

        var eventObj = window.event;

        if (eventObj.keyCode == 13) {

            doSearch();
        }
    }

    function selectClient() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;
        var className = currentRow.className;

        f.selectedClientDetailPK.value = currentRow.id;
/*      var name = currentRow.cells[0].innerHTML;
        var taxId = currentRow.cells[1].innerHTML;
        var clientStatus = currentRow.cells[2].innerHTML;
        var dob = currentRow.cells[3].innerHTML;
 */        

 		gCity = currentRow.cells[6].innerHTML;
		gState = currentRow.cells[7].innerHTML;
		gAddressLine1 = currentRow.cells[8].getElementsByTagName('input')[0].value;
		gZipCode = currentRow.cells[9].getElementsByTagName('input')[0].value;
        
        if (currentRow.isSelected == "false") {

            currentRow.style.backgroundColor = "#FFFFCC";
            currentRow.isSelected = "true";
        } else {

            if (className == "mainEntry") {

                currentRow.style.backgroundColor = "#BBBBBB";
            }

            currentRow.isSelected = "false";
        }
    }

    function updateMessages() {

        var tdMessages = document.getElementById("clientMessages");
        var currentClientCount = 0;

        if (currentClientIndex == 0 && totalClientCount != 0) {

            currentClientCount = 1;
        } else if (currentClientIndex == 0 && totalClientCount == 0) {

            currentClientCount = 0;
        } else {

            currentClientCount = currentClientIndex + 1;
        }

        tdMessages.innerHTML = "Client " + currentClientCount + "/" + totalClientCount;
    }

    function scrollToNextClient() {

        if (currentClientIndex == totalClientCount - 1) {

            return;
        } else {

            currentClientIndex++;

            var clientPK = clientPKs[currentClientIndex].clientPK;

            scrollClientIntoView(clientPK);

            updateMessages();
        }
    }

    function scrollToPrevClient() {

        if (currentClientIndex == 0) {

            return;
        } else {

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

    function checkForEnter() {

        var eventObj = window.event;

        if (eventObj.keyCode == 13) {

            doClientSearch();
        }
    }

    function closeDialog() {

        window.close();
    }

    function doClientSearch() {
        var tdSearchMessage = document.getElementById("searchMessage");
        tdSearchMessage.innerHTML = "<font face='arial' size='3' color='blue'>Searching ...</font>";

        if (f.clientName.value.length > 0 && f.dob.value.length != "") {
            sendTransactionAction("ContractDetailTran", "findClientsByNameDOB", "_self");
        } else if (f.clientName.value.length > 0 && f.dob.value == "") {
            f.searchType.value = nameSearch;
            sendTransactionAction("ContractDetailTran", "findClients", "_self");
        } else if (f.taxId.value.length > 0) {
            f.searchType.value = taxIdSearch;
            sendTransactionAction("ContractDetailTran", "findClients", "_self");
        } else {

            var tdSearchMessage = document.getElementById("searchMessage");
            tdSearchMessage.innerHTML = "&nbsp;"

            alert("Invalid search parameters.");
        }
    }

    function buildContractClientRoles() {
        if(!checkEffectiveDate(document.getElementById('effectiveDate'),f.effectiveDate.value))
        {
            return;
        }

		/*         
		 *	Checking to see if one of the selected roles is a Secondary Addressee.  If so,
		 *  we want to ensure they have a full address.
		 */        
  		if (selectedRolesContainsSecondaryAddressee()) {
			if (f.selectedClientDetailPK.value != "" && !clientHasFullAddress()) {
				alert("This Client does not have an address. Please complete the address before attempting to select as a Secondary Addressee.");
				return;
			}
        }

		var selectedRoles = getSelectedRoles();
        f.selectedRoles.value = selectedRoles;

        <%--        if (f.optionId.selectedIndex == 0) {--%>
        <%----%>
        <%--            alert("Please Select Coverage");--%>
        <%--        }--%>
        <%--        else if (f.selectedClientDetailPK.value == "")--%>
        if (f.selectedClientDetailPK.value == "") {
            alert("Please Select Client");
        } else {
            sendTransactionAction("ContractDetailTran", "buildRolesAndContractClients", "contentIFrame");

            window.close();
        }
    }

    //All of the fields must have a value or it is not a valid address.
    function clientHasFullAddress() {
        if (gCity != "" && gState != "" && gAddressLine1 != "" && gZipCode != "") {
            return true;
        }
        return false;
    }
    
    function selectedRolesContainsSecondaryAddressee() {
        var table = document.all.roleTable;

        var selectedRoles = "";

        for (var i = 0; i < table.rows.length; i++) {
        	var row = table.rows[i];
        	var cellValue = row.cells[0].innerHTML;
            if (table.rows[i].isSelected == "true" && cellValue == "Secondary Addressee ") {
				return true;               
            }
        }
        return false;
    }

    function getSelectedRoles() {

        var table = document.all.roleTable;

        var selectedRoles = "";

        for (var i = 0; i < table.rows.length; i++) {

            if (table.rows[i].isSelected == "true") {

                selectedRoles += table.rows[i].roleId + ",";
            }
        }

        return selectedRoles;
    }

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
            } else {

                currentRow.style.backgroundColor = colorMainEntry;
            }
        } else {
            currentRow.style.backgroundColor = colorHighlighted;
        }
    }

    function highlightRiderRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = colorMouseOver;
    }

    function unhighlightRiderRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected != "true") {

            if (className == "highLighted") {

                currentRow.style.backgroundColor = colorHighlighted;
            } else {

                currentRow.style.backgroundColor = colorMainEntry;
            }
        } else {
            currentRow.style.backgroundColor = colorHighlighted;
        }
    }

    function selectRider() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;
        var className = currentRow.className;

        f.selectedRiderNumber.value = currentRow.id;
        f.selectedOptionId.value = currentRow.optionId;

        if (currentRow.isSelected == "false") {

            currentRow.style.backgroundColor = "#FFFFCC";
            currentRow.isSelected = "true";
        } else {

            if (className == "mainEntry") {

                currentRow.style.backgroundColor = "#BBBBBB";
            }

            currentRow.isSelected = "false";
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
    <title>Contract Client Add</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <meta http-equiv="Cache-Control" content="no-store">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">

    <link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1">

<form name="contractClientAddForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="span1"
      style="border-style:solid; border-width:1;  position:relative; width:100%; height:5%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="4">
    <tr>
      <td align="right" nowrap>
        Tax Identification Number:
      </td>
      <td align="left" nowrap colspan="3">
        <input type="text" name="taxId" size="11" tabindex="1" onKeyDown="checkForEnter()">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>
        Name:
      </td>
      <td align="left" nowrap>
        <input type="text" name="clientName" size="30" tabindex="2" onKeyDown="checkForEnter()">
      </td>
      <td align="right" nowrap>
        Date Of Birth:
      </td>
      <td align="left" nowrap>
           <input type="text" name="dob" value="<%= dob %>" size='10' maxlength="10"
                  onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.dob', f.dob.value);"><img src="/PORTAL/common/images/calendarIcon.gif"
                                                                          width="16" height="16" border="0"
                                                                          alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td id="searchMessage" align="middle" nowrap colspan="3">
        &nbsp;
      </td>
      <td align="right" nowrap>
        <input type="button" value="Enter" onClick="doClientSearch()">
      </td>
    </tr>
  </table>
</span>

    <span id="span2"
          style="border-style:solid; border-width:1; position:relative; width:100%; height:15%; top:0; left:0; z-index:0; overflow:scroll">
  <table class="summaryArea" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr height="10">
      <th align="left" width="12%">Name</th>
      <th align="left" width="12%">Tax Id</th>
      <th align="left" width="12%">Last Operator</th>
      <th align="left" width="12%">Maintenance Date</th>
      <th align="left" width="12%">Client Status</th>
      <th align="left" width="12%">Date Of Birth</th>
      <th align="left" width="12%">City</th>
      <th align="left" width="12%">State</th>
      <th/>
      <th/>
    </tr>
      <%
          Map clientDataPageBeans = contractClientAddSessionBean.getPageBeans();

          if (!clientDataPageBeans.isEmpty()) {

              Iterator clientDataEnum = clientDataPageBeans.values().iterator();

              while (clientDataEnum.hasNext()) {

                  PageBean clientDataPageBean = (PageBean) clientDataEnum.next();

                  String clientDetailPK = clientDataPageBean.getValue("clientDetailPK");
                  String lastName = clientDataPageBean.getValue("lastName");
                  String firstName = clientDataPageBean.getValue("firstName");
                  String corporateName = clientDataPageBean.getValue("corporateName");
                  String name = "";
                  if (lastName.equals("")) {
                      name = corporateName;
                  } else {
                      name = lastName + ", " + firstName;
                      String middleName = clientDataPageBean.getValue("middleName");
                      String namePrefix = clientDataPageBean.getValue("namePrefix");
                      String nameSuffix = clientDataPageBean.getValue("nameSuffix");
                      if (!middleName.equals("")) {
                          name = name + ", " + middleName;
                      }
                      if (!namePrefix.equals("")) {
                          name = name + ", " + namePrefix;
                      }
                      if (!nameSuffix.equals("")) {
                          name = name + ", " + nameSuffix;
                      }
                  }
                  String taxIdNumber = clientDataPageBean.getValue("taxIdNumber");
                  String dateOfBirth = clientDataPageBean.getValue("dateOfBirth");
                  String city = clientDataPageBean.getValue("city");
                  String state = clientDataPageBean.getValue("state");
                  String addressLine1 = clientDataPageBean.getValue("addressLine1");
                  String zipCode = clientDataPageBean.getValue("zipCode");
                  String clientStatus = clientDataPageBean.getValue("clientStatus");
                  String className = "mainEntry";
                  String clientSelected = "false";
                  String maintDateTime = clientDataPageBean.getValue("maintDateTime");
                  String operator = clientDataPageBean.getValue("operator");

      %>
    <tr class="<%= className %>" id="<%= clientDetailPK %>" onClick="selectClient()"
        onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" isSelected="<%= clientSelected %>">
      <td align="left" width="12%" nowrap>
        <%= name %>
      </td>
      <td align="left" width="12%" nowrap>
        <%= taxIdNumber %>
      </td>
      <td align="left" width="12%" nowrap>
        <%= operator %>
      </td>
      <td align="left" width="12%" nowrap>
        <%= maintDateTime %>
      </td>
      <td align="left" width="12%" nowrap>
        <%= clientStatus %>
      </td>
      <td align="left" width="12%" nowrap>
        <%= dateOfBirth %>
      </td>
      <td align="left" width="12%" nowrap>
        <%= city %>
      </td>
      <td align="left" width="12%" nowrap>
        <%= state %>
      </td>
      <td><input type="hidden" name="hiddenAddressLine1" value='<%= addressLine1 %>'></td>
      <td><input type="hidden" name="hiddenZipCode" value=<%= zipCode %>></td>
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
    </tr>
  </table>
  <table align="left" width="100%" height="98%">
    <tr>
      <td nowrap width="100%" height="100%">
        Roles:
        <br>
        <span id="span3a"
              style="border-style:solid; border-width:1; position:relative; width:99%; height:84%; top:0; left:0; z-index:0; overflow:scroll">
        <table class="contentArea" id="roleTable" width="100%" border="0" cellspacing="0" cellpadding="0">
          <%
              for (int i = 0; i < lifeRoles.length; i++) {

                  String className = "mainEntry";
                  String roleSelected = "false";

                  String codeTablePK = lifeRoles[i].getCodeTablePK() + "";
                  String codeDesc = lifeRoles[i].getCodeDesc();
                  String code = lifeRoles[i].getCode();
          %>
          <tr class="<%= className %>" roleId="<%= codeTablePK %>" onMouseOver="highlightRoleRow()"
              onMouseOut="unhighlightRoleRow()" onClick="selectRoleRow()" isSelected="<%= roleSelected %>">
            <td>
              <%= codeDesc %>
            </td>
          </tr>
          <%
              }
          %>
        </table>
        </span>
      </td>
    </tr>
    <tr>
    <td align="left" nowrap>Effective Date:&nbsp;
    <input class="datepicker" type="text"
						id="effectiveDate" onChange="checkEffectiveDate(this,this.value)"
						value="<%=effectiveDate%>"
						name="effectiveDate" size="10"></td>
    </tr>
  </table>
</span>

    <span id="span4" style="position:relative; width:100%; height:10%; top:0; left:0; z-index:0">
  <table width="100%">
    <tr>
      <td align="right">
        <input type="button" value="Enter" onClick="buildContractClientRoles()">
        <input type="button" value="Cancel" onClick="closeDialog()">
      </td>
    </tr>
  </table>
</span>

    <table align="left" width="100%" height="98%">
        <tr>
            <td nowrap width="100%" height="100%">
                Coverage Info:
                <br>
                <span id="span5"
                      style="border-style:solid; border-width:1; position:relative; width:100%; height:15%; top:0; left:0; z-index:0; overflow:scroll">
      <table class="summaryArea" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr height="10">
          <th align="left">Coverage</th>
           <th align="left">Rider Number</th>
          <th align="left">Effective Date</th>
          <th align="left">Amount</th>
        </tr>
            <%
                Map pageBeans = contractRiders.getPageBeans();

                Map sortedRidersByRiderNumber = new TreeMap();

                Iterator itrKeys = pageBeans.keySet().iterator();

                while (itrKeys.hasNext()) {
                    String key = (String) itrKeys.next();
                    if (!key.equals("")) {
                        sortedRidersByRiderNumber.put(key, allRiders.get(key));
                    }
                }

                String className = "mainEntry";
                String selected = "false";
                String currentOptionCode = "";
                String currentEffectiveDate = "";
                String currentOptionCodePK = "";
                String amountT = "";
                String currentRiderNumber = "";

                Iterator it = sortedRidersByRiderNumber.values().iterator();


                while (it.hasNext()) {
                    PageBean rider = (PageBean) it.next();

                    currentOptionCodePK = rider.getValue("optionCodePK");


                    if (!currentOptionCodePK.equalsIgnoreCase("")) {
                        currentOptionCode = codeTableWrapper.getCodeTableEntry(Long.parseLong(currentOptionCodePK)).getCode();
                    }

                    if (currentOptionCode.equalsIgnoreCase("LT")) {
                        currentEffectiveDate = rider.getValue("effectiveDate");
                        amountT = rider.getValue("amount");
                        currentRiderNumber = rider.getValue("riderNumber");
            %>
                      <tr class="<%= className %>" id="<%= currentRiderNumber %>"
                          riderNumber="<%= currentRiderNumber %>"
                          isSelected="<%= selected %>" onClick="selectRider()" optionId="<%= currentOptionCode %>"
                          onMouseOver="highlightRiderRow()" onMouseOut="unhighlightRiderRow()">
                        <td nowrap>
                          <%= currentOptionCode %>
                        </td>
                        <td nowrap>
                          <%= currentRiderNumber %>
                        </td>
                        <td nowrap>
                          <%= currentEffectiveDate %>
                        </td>
                        <td nowrap>
                          <%= amountT %>
                        </td>
                      </tr>
              <%
                      }//end of if
                  }// end while
              %>
      </table>
     </span>
            </td>
        </tr>
    </table>

    <!-- ****** HIDDEN FIELDS ***** //-->
    <input type="hidden" name="transaction" value="">
    <input type="hidden" name="action" value="">
    <input type="hidden" name="selectedClientDetailPK" value="">
    <input type="hidden" name="selectedRoles" value="">
    <input type="hidden" name="searchType" value="">
    <input type="hidden" name="selectedRiderNumber" value="">
    <input type="hidden" name="selectedOptionId" value="">

</form>
</body>
</html>
