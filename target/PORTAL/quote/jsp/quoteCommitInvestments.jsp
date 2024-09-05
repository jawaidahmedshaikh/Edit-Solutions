<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 fission.utility.Util,
                 edit.portal.common.session.UserSession,
                 edit.common.vo.UIFilteredFundVO" %>

<jsp:useBean id="quoteFunds"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="statusBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    String investmentMessage = (String) request.getAttribute("investmentMessage");
    if (investmentMessage == null)
    {
        investmentMessage = "";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

	PageBean formBean = quoteMainSessionBean.getPageBean("investmentFormBean");

    PageBean mainBean = quoteMainSessionBean.getPageBean("formBean");
    String companyStructureId = Util.initString(mainBean.getValue("companyStructureId"), "0");

    String investmentPK       = formBean.getValue("investmentPK");
    String filteredFundFK     = formBean.getValue("filteredFundFK");
    String fundName           = formBean.getValue("fundName");
    String allocationPercent  = formBean.getValue("allocationPercent");
    if (Util.isANumber(allocationPercent))
    {
        allocationPercent = Util.roundDoubleToString(allocationPercent);
    }
    String fundDollars        = formBean.getValue("dollars");
    String fundUnits          = formBean.getValue("units");
    String lastValDate        = formBean.getValue("lastValDate");
    String depositDate        = formBean.getValue("depositDate");
    String poUnits            = formBean.getValue("poUnits");
    String poDollars          = formBean.getValue("poDollars");
    String excessIntCalcDate  = formBean.getValue("excessIntCalcDate");
    String excessIntPymtDate  = formBean.getValue("excessIntPymtDate");
    String excessInterest     = formBean.getValue("excessInterest");
    String excessIntMethod    = formBean.getValue("excessIntMethod");
    String excessIntStartDate = formBean.getValue("excessIntStartDate");
    String air                = formBean.getValue("air");
    String fundType           = formBean.getValue("fundType");
    String optionId           = formBean.getValue("optionId");

    CodeTableVO[] options     = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));

    UIFilteredFundVO[] uiFilteredFundVOs = (UIFilteredFundVO[]) request.getAttribute("uiFilteredFundVOs");

	String rowToMatchBase     = optionId + filteredFundFK;

    String baseOptionId = quoteMainSessionBean.getPageBean("formBean").getValue("optionId");
    if (optionId.equals(""))
    {
        optionId = baseOptionId;
    }

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script language="Javascript1.2">

    var investmentMessage = "<%= investmentMessage %>";
	var f = null;

    var shouldShowLockAlert = true;
    var filteredFundFK = "<%= filteredFundFK %>";
    var optionId = "<%= optionId %>";

    var colorMouseOver   = "#00BB00";
    var colorHighlighted = "#FFFFCC";
    var colorMainEntry   = "#BBBBBB";

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

        if (className == "highLighted") {

            currentRow.style.backgroundColor = colorHighlighted;
        }

        else {

            currentRow.style.backgroundColor = colorMainEntry;
        }
    }

	function init() {

		f = document.quoteCommitInvestmentForm;

		top.frames["main"].setActiveTab("investmentTab");

        if (investmentMessage != "" &&
            investmentMessage != null) {

            alert(investmentMessage);
        }

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true) )
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }
	}

    function showLockAlert(){

        if (shouldShowLockAlert == true){

            alert("The Contract can not be edited.");

            return false;
        }
    }

    function checkForRequiredFields()
    {
		return true;
    }

	function addNewFund() {

        sendTransactionAction("QuoteDetailTran", "clearInvestmentsForAddOrCancel", "contentIFrame");
	}

	function cancelFundInfo() {

        sendTransactionAction("QuoteDetailTran", "clearInvestmentsForAddOrCancel", "contentIFrame");
	}

	function clearForm() {

		f.fundId.selectedIndex = 0;
        f.optionId.selectedIndex = 0;
		f.allocationPercent.value = "";
        f.air.value = "";
	}

	function saveFundToSummary() {

        if (f.fundId.value == "" ||
            f.fundId.value == "Please Select" ||
            f.optionId.value == "" ||
            f.optionId.value == "Please Select" ||
            f.allocationPercent.value == "") {

            alert ("Coverage, Fund, and Allocation Percent Must Be Selected/Entered");
        }

        else {

            sendTransactionAction("QuoteDetailTran", "saveFundToSummary", "contentIFrame");
        }
	}

	function deleteSelectedFund() {

		f.selectedFundId.value = filteredFundFK;
		f.selectedOptionId.value = optionId;

		clearForm();

		sendTransactionAction("QuoteDetailTran", "deleteSelectedFund", "contentIFrame");
	}

	function selectRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var rowId     = trElement.id;
		var parsedRowId = (rowId.split("_"))[1];

		var fundId  = trElement.filteredFundFK;

		var optionTd  = document.getElementById("optionId_" + parsedRowId);
		var optionId  = optionTd.innerText;

		var allocationTd = document.getElementById("allocationId_" + parsedRowId);
		var allocationId = allocationTd.innerText;

		f.selectedFundId.value = fundId;
		f.selectedOptionId.value = optionId;
		f.selectedAllocationId.value = allocationId;

		sendTransactionAction("QuoteDetailTran", "showFundDetailSummary", "contentIFrame");
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value = action;

		f.target = target;

		f.submit();
	}

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target)
    }

</script>

<head>
<title>New Business Investments</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "quoteCommitInvestmentForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="quoteInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="48%" border="0" cellspacing="0" cellpadding="3">
    <tr valign="top">
		<td colspan="2" nowrap><br>Coverage:
			<select name="optionId" tabindex="1">
			  <option> Please Select </option>
			  <%
                for (int i = 0; i < options.length; i++) {

                    String codeTablePK = options[i].getCodeTablePK() + "";
                    String codeDesc    = options[i].getCodeDesc();
                    String code        = options[i].getCode();

                    if (optionId.equals(""))
                    {
                        optionId = baseOptionId;
                    }

                    if (optionId.equalsIgnoreCase(code)) {

                        out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                    }
                    else  {

                        out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                    }
                }
			  %>
			  </select><br><br>

              Allocation %: <input type="text" name="allocationPercent" tabindex="3" maxlength="9" size="9" value="<%= allocationPercent %>">
		</td>
		<td nowrap><br>
				Fund:
		  <select name="fundId" tabindex="2" value="<%= filteredFundFK %>">
			<option> Please Select </option>
			<%
               if (uiFilteredFundVOs != null) {

                   for(int i = 0; i < uiFilteredFundVOs.length; i++) {

                       FilteredFundVO[] filteredFundVO = uiFilteredFundVOs[i].getFilteredFundVO();
                       String uiFilteredFundPK = filteredFundVO[0].getFilteredFundPK() + "";

                       FundVO[] fundVO = uiFilteredFundVOs[i].getFundVO();
                       String uiFundName = fundVO[0].getName();

                       if (filteredFundFK.equals(uiFilteredFundPK)) {

                           out.println("<option selected name=\"id\" value=\"" + uiFilteredFundPK + "\">" + uiFundName + "</option>");
                       }
                       else {

                           out.println("<option name=\"id\" value=\"" + uiFilteredFundPK + "\">" + uiFundName + "</option>");
                       }
                   }
               }
			%>
		  </select>
          <br>
          <br>
          AIR:&nbsp;
          <input type="text" name="air" tabindex="4" maxlength="9" size="9" value="<%= air %>">
		</td>
		<td nowrap>
        <br>
		  Fund Type:&nbsp;
          <input type="text" name="fundType" disabled maxlength="10" size="10" value="<%= fundType %>">
		</td>
    </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td nowrap align="left">
        <input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNewFund()">
		<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveFundToSummary()">
		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelFundInfo()">
		<input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deleteSelectedFund()">
	  </td>
	</tr>
  </table>
  <table class="summaryArea" id="summaryTable" width="100%" height="40%" border="0" cellspacing="0" cellpadding="0">
    <tr height="1%">
      <th width="33%" align="left">Fund</th>
      <th width="33%" align="left">Allocation</th>
      <th width="33%" align="left">Coverage</th>
    </tr>
    <tr width="100%" height="99%">
      <td colspan="3">
        <span class="scrollableContent">
          <table class="scrollableArea" id="addressSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
	        <%
		      int rowId   = 0;
              int columnId = 0;

              String rowToMatch = "";
              String trClass = "";
              String selected = "";
              String option   = "";
              String invStatus = "";

              Map allFunds = quoteFunds.getPageBeans();

              Iterator it = allFunds.values().iterator();

              while (it.hasNext()) {

                  PageBean fund = (PageBean)it.next();

                  invStatus = fund.getValue("status");
                  if (invStatus == null || invStatus.equals(""))
                  {
                      filteredFundFK    = fund.getValue("filteredFundFK");
                      fundName          = fund.getValue("fundName");
                      option            = fund.getValue("optionId");
                      allocationPercent = fund.getValue("allocationPercent");
                      if (!allocationPercent.equals(""))
                      {
                          allocationPercent = Util.roundDoubleToString(allocationPercent);
                      }

                      rowToMatch = option + filteredFundFK;

                      if (rowToMatch.equals(rowToMatchBase)) {

                          trClass = "highLighted";

                          selected = "true";
                      }
                      else {

                          trClass = "dummy";

                          selected="false";
                      }
            %>
	        <tr class="<%= trClass %>" selected="<%= selected %>" id="rowId_<%= rowId++ %>"
                filteredFundFK="<%= filteredFundFK %>" onClick="selectRow()"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
              <td width="33%" nowrap id="fundName_<%= columnId %>">
                <%= fundName %>
              </td>
              <td width="33%" nowrap id="allocationId_<%= columnId %>">
                <%= allocationPercent %>
              </td>
              <td width="33%" nowrap id="optionId_<%= columnId++ %>">
                <%= option %>
              </td>
            </tr>
            <%
                  }// end if (status != null)
              }// end while
            %>
          </table>
        </span>
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="selectedFundId" value="">
 <input type="hidden" name="selectedOptionId"     value="">
 <input type="hidden" name="selectedAllocationId" value="">

 <input type="hidden" name="investmentPK" value="<%= investmentPK %>">
 <input type="hidden" name="filteredFundFK" value="<%= filteredFundFK %>">
 <input type="hidden" name="dollars" value="<%= fundDollars %>">
 <input type="hidden" name="units" value="<%= fundUnits %>">
 <input type="hidden" name="lastValDate" value="<%= lastValDate %>">
 <input type="hidden" name="depositDate" value="<%= depositDate %>">
 <input type="hidden" name="poUnits" value="<%= poUnits %>">
 <input type="hidden" name="poDollars" value="<%= poDollars %>">
 <input type="hidden" name="excessIntCalcDate" value="<%= excessIntCalcDate %>">
 <input type="hidden" name="excessIntPymtDate" value="<%= excessIntPymtDate %>">
 <input type="hidden" name="excessInterest" value="<%= excessInterest %>">
 <input type="hidden" name="excessIntMethod" value="<%= excessIntMethod %>">
 <input type="hidden" name="excessInStartDate" value="<%= excessIntStartDate %>">
 <input type="hidden" name="fundType" value="<%= fundType %>">

 <!-- recordPRASEEvents is set by the toolbar when saving the client -->
 <input type="hidden" name="recordPRASEEvents" value="false">

</form>
</body>
</html>






