<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.*" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    PageBean formBean = (PageBean) request.getAttribute("formBean");
    SessionBean contractPayeeOverrides = (SessionBean) session.getAttribute("contractPayeeOverrides");

    ClientDetailVO clientDetailVO = (ClientDetailVO)session.getAttribute("selectedClientDetailVO");
    String allocationPercent = Util.initString((String)session.getAttribute("selectedAllocationPercent"), "");

    String clientLastName = "";
    String clientFirstName = "";
    String clientMiddleName = "";
    String corporateName = "";
    String taxId = "";
    String dob = "";
    String disbursementSource = "";
    String segmentFK = "";
    String clientDetailPK = "";

    if (clientDetailVO != null)
    {
        clientLastName = clientDetailVO.getLastName();
        clientFirstName = Util.initString(clientDetailVO.getFirstName(), "");
        clientMiddleName = Util.initString(clientDetailVO.getMiddleName(), "");
        corporateName = Util.initString(clientDetailVO.getCorporateName(), "");
        taxId = clientDetailVO.getTaxIdentification();
        dob = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(clientDetailVO.getBirthDate());

        if (clientDetailVO.getPreferenceVOCount() > 0)
        {
            disbursementSource = Util.initString(clientDetailVO.getPreferenceVO(0).getDisbursementSourceCT(), "");
        }
        clientDetailPK = clientDetailVO.getClientDetailPK() + "";
    }
    else
    {
        clientDetailPK = formBean.getValue("clientDetailPK");
    }

    segmentFK = formBean.getValue("segmentFK");
    String selectedSegmentPK = formBean.getValue("selectedSegmentPK");

    String contractClientFK = formBean.getValue("contractClientFK");
    String contractClientAllocOvrdPK = formBean.getValue("contractClientAllocationOverridePK");
    String withholdingOverridePK = formBean.getValue("withholdingOverridePK");
    String contractClientAllocationFK = formBean.getValue("contractClientAllocationFK");
    String withholdingFK = formBean.getValue("withholdingFK");
    String companyStructureId = formBean.getValue("companyStructureId");

    String filterMessage       = formBean.getValue("filterMessage");
    String filter              = formBean.getValue("filter");
    String segmentName         = formBean.getValue("segmentName");
    String filterAllocPct      = formBean.getValue("filterAllocPct");
    String filterAllocDollars  = formBean.getValue("filterAllocDollars");
    String costBasis  	       = formBean.getValue("costBasis");
    String amountReceived      = formBean.getValue("amountReceived");
    String suspenseAmt         = formBean.getValue("amountUsed");
    String payeeIndStatus      = formBean.getValue("payeeIndStatus");
    String investmentIndStatus = formBean.getValue("investmentIndStatus");
    String deathStatusEnabled  = formBean.getValue("deathStatusEnabled");
    String deathStatus         = formBean.getValue("deathStatus");
    String transactionType     = formBean.getValue("transactionType");
    String rowId               = formBean.getValue("rowId");

    String fedWithholdingType = Util.initString(formBean.getValue("fedWithholdingType"), "");
    String fedWithholdingAmt = Util.initString(formBean.getValue("fedWithholdingAmt"), "");
    String fedWithholdingPct = Util.initString(formBean.getValue("fedWithholdingPct"), "");

    String stateWithholdingType = Util.initString(formBean.getValue("stateWithholdingType"), "");
    String stateWithholdingAmt = Util.initString(formBean.getValue("stateWithholdingAmt"), "");
    String stateWithholdingPct = Util.initString(formBean.getValue("stateWithholdingPct"), "");

    String cityWithholdingType = Util.initString(formBean.getValue("cityWithholdingType"), "");
    String cityWithholdingAmt = Util.initString(formBean.getValue("cityWithholdingAmt"), "");
    String cityWithholdingPct = Util.initString(formBean.getValue("cityWithholdingPct"), "");

    String countyWithholdingType = Util.initString(formBean.getValue("countyWithholdingType"), "");
    String countyWithholdingAmt = Util.initString(formBean.getValue("countyWithholdingAmt"), "");
    String countyWithholdingPct = Util.initString(formBean.getValue("countyWithholdingPct"), "");

    String searchStatus	 = (String) request.getAttribute("searchStatus");

//    ContractClientVO[] contractClientVOs = (ContractClientVO[]) session.getAttribute("contractClientVOs");

	String rowToMatchBase = segmentFK + clientDetailPK;

    CodeTableVO[] fedWithholdingTypes    = codeTableWrapper.getCodeTableEntries("FEDWITHHOLDINGTYPE", Long.parseLong(companyStructureId));
    CodeTableVO[] stateWithholdingTypes  = codeTableWrapper.getCodeTableEntries("STATEWITHHOLDINGTYPE", Long.parseLong(companyStructureId));
    CodeTableVO[] cityWithholdingTypes   = codeTableWrapper.getCodeTableEntries("CITYWITHHOLDINGTYPE", Long.parseLong(companyStructureId));
    CodeTableVO[] countyWithholdingTypes = codeTableWrapper.getCodeTableEntries("CNTYWITHHOLDINGTYPE", Long.parseLong(companyStructureId));


%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;
    var searchStatus = "<%= searchStatus %>";

    var formContractClientFK = "<%= contractClientFK %>";
    var formClientDetailPK = "<%= clientDetailPK %>";
<%--	var clientFound = "<%= clientFound %>";--%>
<%--    var clientId    = "<%= payeeClientId %>";--%>

    var colorMouseOver = "#00BB00";
    var colorHighlighted = "#FFFFCC";
    var colorMainEntry   = "#BBBBBB";

    var width = screen.width;
    var height = screen.height;

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

        if (currentRow.isSelected != true) {

            if (className == "highLighted") {

                currentRow.style.backgroundColor = colorHighlighted;
            }

            else {

                currentRow.style.backgroundColor = colorMainEntry;
            }
        }
    }

	function init() {

		f = document.payeeForm;

        f.taxId.onkeydown = getClientInfo;

        if (searchStatus == "noData"){

            alert("Sorry, client not found.");
        }

        formatCurrency();
	}

    function getClientInfo() {

        //If TAB Key
        if (window.event.keyCode == 9) {

            sendTransactionAction("ContractDetailTran", "getClientInfo", "_self");
        }
    }

    function closePayeeDialog() {

        sendTransactionAction("ContractDetailTran", "createNewContractClientOverrides", "filterAllocDialog");
		window.close();
    }

	function selectRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

        var contractClientFK = trElement.contractClientFK;
        f.contractClientFK.value = contractClientFK;

        var clientDetailPK = trElement.clientDetailPK;
        f.clientDetailPK.value = clientDetailPK;

        sendTransactionAction("ContractDetailTran", "showPayeeOverrideDetailSummary", "_self");
	}

	function addNewContractClient()	 {

        openDialog("","addPayeeOverrideDialog","left=0,top=0,resizable=no,width=" + .88 * width + ",height=" + .50 * height,"ContractDetailTran","addContractClient","addPayeeOverrideDialog");
	}

	function cancelContractClient()	 {

		sendTransactionAction("ContractDetailTran","cancelContractClient","_self");
	}

    function deleteContractClient() {

        f.contractClientFK.value = "<%= contractClientFK %>";

        sendTransactionAction("ContractDetailTran", "deleteContractClient","_self");
    }

	function cancelPayeeOverride() {

		var key           = f.key.value;
		var transactionId = f.transactionId.value;

		clearForm();

		sendTransactionAction("ContractDetailTran","cancelPayeeOverride","_self")
	}

	function updateContractClient() {

        if (f.contractClientFK.value == "") {

            f.contractClientFK.value = formContractClientFK;
        }

        if (f.clientDetailPK.value == "") {

            f.clientDetailPK.value = formClientDetailPK;
        }

		sendTransactionAction("ContractDetailTran", "updateContractClient", "_self");
	}

	function deletePayeeOverride() {

		sendTransactionAction("ContractDetailTran","deletePayeeOverride","_self")
	}

	function checkForExistingClient() {

<%--		if (clientFound == "false") {--%>
<%----%>
<%--			f.clientId.value = clientId;--%>
<%----%>
<%--			alert("Client Not Found In Database");--%>
<%--		}--%>
<%----%>
<%--        else if (clientFound == "true") {--%>
<%----%>
<%--			f.clientId.value = clientId;--%>
<%----%>
<%--            openDialog("","clientInfoDialog","left=0,top=0,resizable=no,width=1,height=1","ContractDetailTran","showClientQuickAddDialog","clientInfoDialog");--%>
<%--        }--%>
	}

	function clearForm() {

		f.taxId.value = "";
		f.allocationPercent.value  = "";
	}

	function openDialog(theURL,winName,features,transaction,action,target) {

		dialog = window.open(theURL,winName,features);

	  	sendTransactionAction(transaction, action, winName, target);
	}


</script>
<head>
<title>Payee Overrides</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" conte="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>
<body class="dialog" onLoad="init(); checkForExistingClient()"style="background-color:#DDDDDD">
<form name="payeeForm" method="post" action="/PORTAL/servlet/RequestManager">

  <table height="20%" width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td align="left" nowrap>Tax Id:&nbsp;
	    <input disabled type="text" name="taxId" maxlength="11" size="11" value="<%= taxId %>">
	  </td>
	  <td align="left" nowrap>Last Name:&nbsp;
	    <input disabled type="text" name="lastName" maxlength="30" size="30" value="<%= clientLastName %>">
	  </td>
	  <td align="left" colspan="2" nowrap>First Name:&nbsp;
	    <input disabled type="text" name="firstName" maxlength="15" size="15" value="<%= clientFirstName %>">
	  </td>
	</tr>
    <br>
	<tr>
	  <td align="left" nowrap>DOB:&nbsp;
	    <input disabled type="text" name="dob" maxlength ="10" size="10" value="<%= dob %>">
	  </td>
	  <td align="left" nowrap>Middle Name:&nbsp;
	    <input disabled type="text" name="middleName" maxlength="15" size="15" value="<%= clientMiddleName%>">
	  </td>
	  <td align="left" nowrap colspan="2">Disbursement Source:&nbsp;
        <input disabled type="text" name="disbursementSource" maxlength="20" size="20" value="<%= disbursementSource %>">
	  </td>
	</tr>
	<tr>
	  <td align="left" nowrap colspan="4">Corporate Name:&nbsp;
	    <input disabled type="text" name="corporateName" maxlength="60" size="60" value="<%= corporateName %>">
	  </td>
	</tr>
    <tr>
      <td align="left" nowrap width="19%">Allocation %:
        <input type="text" name="allocationPercent" maxlength="5" size="5" tabindex="6" value="<%= allocationPercent %>">
      </td>
    </tr>

  </table>
    <br>

 <span>
    Withholding Overrides:
  <table align="center" id="withholdingTable" height="20%" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="right" nowrap>Federal W/H Type:</td>
      <td align="left" nowrap>
        <select name="fedWithholdingType">
          <option>Please Select</option>
            <%
              for(int i = 0; i < fedWithholdingTypes.length; i++) {

                  String codeDesc    = fedWithholdingTypes[i].getCodeDesc();
                  String code        = fedWithholdingTypes[i].getCode();

                  if (fedWithholdingType.equals(code)) {

                      out.println("<option selected value=\""+ code + "\">" + codeDesc + "</option>");
                  }
                  else {

                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
            %>
        </select>
      </td>
      <td align="right" nowrap>Federal W/H Amt:</td>
      <td align="left" nowrap>
        <input type="text" name="fedWithholdingAmt" size="11" maxlength="11" value="<%= fedWithholdingAmt %>" CURRENCY>
      </td>
      <td align="right" nowrap>Federal W/H Pct:</td>
      <td align="left" nowrap>
        <input type="text" name="fedWithholdingPct" size="11" maxlength="11" value="<%= fedWithholdingPct %>" >
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>State W/H Type:</td>
      <td align="left" nowrap>
        <select name="stateWithholdingType">
          <option>Please Select</option>
            <%
                for(int i = 0; i < stateWithholdingTypes.length; i++) {

                    String codeDesc    = stateWithholdingTypes[i].getCodeDesc();
                    String code        = stateWithholdingTypes[i].getCode();

                    if (stateWithholdingType.equals(code)) {

                        out.println("<option selected value=\""+ code + "\">" + codeDesc + "</option>");
                    }
                    else {

                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
            %>
        </select>
      </td>
      <td align="right" nowrap>State W/H Amt:</td>
      <td align="left" nowrap>
        <input type="text" name="stateWithholdingAmt" size="11" maxlength="11" value="<%= stateWithholdingAmt %>"  CURRENCY>
      </td>
      <td align="right" nowrap>State W/H Pct:</td>
      <td align="left" nowrap>
        <input type="text" name="stateWithholdingPct" size="11" maxlength="11" value="<%= stateWithholdingPct %>" onFocus="checkChangeability()">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>City W/H Type:</td>
      <td align="left" nowrap>
        <select name="cityWithholdingType">
          <option>Please Select</option>
            <%
                for(int i = 0; i < cityWithholdingTypes.length; i++) {

                    String codeDesc    = cityWithholdingTypes[i].getCodeDesc();
                    String code        = cityWithholdingTypes[i].getCode();

                    if (cityWithholdingType.equals(code)) {

                        out.println("<option selected value=\""+ code + "\">" + codeDesc + "</option>");
                    }
                    else {

                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
            %>
        </select>
      </td>
      <td align="right" nowrap>City W/H Amt:</td>
      <td align="left" nowrap>
        <input type="text" name="cityWithholdingAmt" size="11" maxlength="11" value="<%= cityWithholdingAmt %>" CURRENCY>
      </td>
      <td align="right" nowrap>City W/H Pct:</td>
      <td align="left" nowrap>
        <input type="text" name="cityWithholdingPct" size="11" maxlength="11" value="<%= cityWithholdingPct %>" onFocus="checkChangeability()">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>County W/H Type:</td>
      <td align="left" nowrap>
        <select name="countyWithholdingType">
          <option>Please Select</option>
            <%
                for(int i = 0; i < countyWithholdingTypes.length; i++) {

                    String codeDesc    = countyWithholdingTypes[i].getCodeDesc();
                    String code        = countyWithholdingTypes[i].getCode();

                    if (countyWithholdingType.equals(code)) {

                        out.println("<option selected value=\""+ code + "\">" + codeDesc + "</option>");
                    }
                    else {

                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
            %>
        </select>
      </td>
      <td align="right" nowrap>County W/H Amt:</td>
      <td align="left" nowrap>
        <input type="text" name="countyWithholdingAmt" size="11" maxlength="11" value="<%= countyWithholdingAmt %>" onFocus="checkChangeability()" CURRENCY>
      </td>
      <td align="right" nowrap>County W/H Pct:</td>
      <td align="left" nowrap>
        <input type="text" name="countyWithholdingPct" size="11" maxlength="11" value="<%= countyWithholdingPct %>" onFocus="checkChangeability()">
      </td>
    </tr>
  </table>
  </span>
  <br>
  <table >
	<tr>
	  <td align="left" colspan="3">
		<input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNewContractClient()">
		<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="updateContractClient()">
		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelContractClient()">
		<input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deleteContractClient()">
	  </td>
	</tr>
  </table>
  <table id="summaryTable" class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr class="heading">
	  <th width="33%">Name</th>
	  <th width="33%">Tax Id</th>
	  <th width="33%">Allocation Percent</th>
	</tr>
  </table>
 <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:15%; top:0; left:0; background-color:#BBBBBB">
   <table class="summary" id="selectedPayeeSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">

          <%
              String rowToMatch = "";

              String className = "mainEntry";
              String selected = "false";

              Map payeeOverridesHT = contractPayeeOverrides.getPageBeans();
              Iterator payeeOverridesEnum = payeeOverridesHT.values().iterator();

              while (payeeOverridesEnum.hasNext()) {

                  PageBean payeeOverridePB = (PageBean) payeeOverridesEnum.next();
                  String status = payeeOverridePB.getValue("status");
                  if (!status.equalsIgnoreCase("deleted")) {

                      String sSegmentFK = payeeOverridePB.getValue("segmentFK");
                      if (sSegmentFK.equals(segmentFK)) {

                          String lastName = payeeOverridePB.getValue("lastName");
                          String firstName = payeeOverridePB.getValue("firstName");
                          String middleName = payeeOverridePB.getValue("middleName");
                          String middleInitial = "";
                          if (middleName.length() > 0)
                          {
                              middleInitial = middleName.substring(0,1);
                          }
                          String sName = "";
                          if (lastName.equals(""))
                          {
                              sName = payeeOverridePB.getValue("corporateName");
                          }
                          else{
                              sName = lastName + ", " + firstName + " " + middleInitial;
                          }
                          String sTaxId = payeeOverridePB.getValue("taxId");
                          String sAllocationPercent = payeeOverridePB.getValue("allocationPercent");
                          String sContractClientFK = payeeOverridePB.getValue("contractClientFK");
                          String sClientDetailPK = payeeOverridePB.getValue("clientDetailPK");
                          rowToMatch = sSegmentFK + sClientDetailPK;

                          if (rowToMatch.equals(rowToMatchBase) &&
                              !rowToMatchBase.equals("")) {

                              className = "highLighted";
                              selected = "true";
                          }
                          else {

                              className = "mainEntry";
                              selected="false";
                          }
          %>
		  <tr class="<%= className %>" isSelected="<%= selected %>" clientDetailPK="<%= sClientDetailPK %>"
              onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
			<td width="33%" nowrap>
			  <%= sName %>
			</td>
			<td width="33%" nowrap>
			  <%= sTaxId %>
			</td>
			<td width="33%" nowrap>
			  <%= sAllocationPercent %>
			</td>
		  </tr>
	  	  <%
                      } // end if (segment keys equal)
                  } // end if (deleted status)
              } // end while
  		  %>

   </table>
  </span>
   <table width="100%" border="0" cellspacing="6" cellpadding="0">
    <tr>
	  <td align="right" nowrap>
	    <input type="button" name="close" value="Close" onClick ="closePayeeDialog()">
	  </td>
    </tr>
  </table>


<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="segmentFK" value="<%= segmentFK %>">
 <input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">
 <input type="hidden" name="contractClientAllocationOverridePK" value="<%= contractClientAllocOvrdPK %>">
 <input type="hidden" name="withholdingOverridePK" value="<%= withholdingOverridePK %>">
 <input type="hidden" name="contractClientAllocationFK" value="<%= contractClientAllocationFK %>">
 <input type="hidden" name="contractClientFK" value="">
 <input type="hidden" name="clientDetailPK" value="">
 <input type="hidden" name="withholdingFK" value="<%= withholdingFK %>">
 <input type="hidden" name="taxId" value="<%= taxId %>">
 <input type="hidden" name="lastName" value="<%= clientLastName %>">
 <input type="hidden" name="firstName" value="<%= clientFirstName %>">
 <input type="hidden" name="middleName" value="<%= clientMiddleName %>">

 <input type="hidden" name="filter" value="<%= filter %>">
 <input type="hidden" name="filterMessage" value="<%= filterMessage %>">
 <input type="hidden" name="segmentName" value="<%= segmentName %>">
 <input type="hidden" name="filterAllocPct" value="<%= filterAllocPct %>">
 <input type="hidden" name="filterAllocDollars" value="<%= filterAllocDollars %>">
 <input type="hidden" name="costBasis" value="<%= costBasis %>">
 <input type="hidden" name="amountReceived" value="<%= amountReceived %>">
 <input type="hidden" name="amountUsed" value="<%= suspenseAmt %>">
 <input type="hidden" name="payeeIndStatus" value="<%= payeeIndStatus %>">
 <input type="hidden" name="investmentIndStatus" value="<%= investmentIndStatus %>">
 <input type="hidden" name="deathStatusEnabled" value="<%= deathStatusEnabled %>">
 <input type="hidden" name="deathStatus" value="<%= deathStatus %>">
 <input type="hidden" name="transactionType" value="<%= transactionType %>">
 <input type="hidden" name="rowId" value="<%= rowId %>">
 <input type="hidden" name="selectedSegmentPK" value="<%= selectedSegmentPK %>">


</form>
</body>
</html>
