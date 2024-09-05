<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 edit.common.vo.UIFilteredFundVO,
                 fission.utility.Util" %>

<jsp:useBean id="contractFunds"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="statusBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    String investmentMessage = (String) request.getAttribute("investmentMessage");
    if (investmentMessage == null)
    {
        investmentMessage = "";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");

	PageBean formBean         = contractMainSessionBean.getPageBean("investmentFormBean");

    String investmentPK       = formBean.getValue("investmentPK");
    String filteredFundFK     = formBean.getValue("filteredFundFK");
    String segmentFK          = formBean.getValue("segmentFK");
    String invAllocPK         = formBean.getValue("invAllocPK");
    String fundName           = formBean.getValue("fundName");
    String allocationPercent  = formBean.getValue("allocationPercent");
    if (Util.isANumber(allocationPercent))
    {
    	allocationPercent = Util.formatDecimal("###,###,##0.0000", new EDITBigDecimal(allocationPercent));
    }
    String fundDollars        = formBean.getValue("fundDollars");
    String fundUnits          = formBean.getValue("fundUnits");
    String lastValDate        = formBean.getValue("lastValDate");
    String depositDate        = formBean.getValue("depositDate");
    String excessIntCalcDate  = formBean.getValue("excessIntCalcDate");
    String excessIntPymtDate  = formBean.getValue("excessIntPymtDate");
    String excessInterest     = formBean.getValue("excessInterest");
    String excessIntMethod    = formBean.getValue("excessIntMethod");
    String excessIntStartDate = formBean.getValue("excessIntStartDate");
    String air                = formBean.getValue("air");
    String fundType           = formBean.getValue("fundType");
    String depositBucketsIndStatus = formBean.getValue("depositBucketsIndStatus");
    String overrideStatus     = formBean.getValue("overrideStatus");

    CodeTableVO[] options     = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));
	String optionId           = formBean.getValue("optionId");

    UIFilteredFundVO[] uiFilteredFundVOs = (UIFilteredFundVO[]) session.getAttribute("uiFilteredFundVOs");

	String rowToMatchBase     = optionId + filteredFundFK;

    String baseOptionId = contractMainSessionBean.getPageBean("formBean").getValue("optionId");
    if (optionId.equals(""))
    {
        optionId = baseOptionId;
    }

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

    var investmentMessage = "<%= investmentMessage %>";
    var depositBucketsIndStatus = "<%= depositBucketsIndStatus %>";
	var f = null;
    var segmentFK = "<%= segmentFK %>";
    var filteredFundFK = "<%= filteredFundFK %>";
    var optionId = "<%= optionId %>";
    var origFilteredFundFK = "<%= filteredFundFK %>";
    var overrideStatus = "<%= overrideStatus %>";

    var shouldShowLockAlert = true;

	function init()
    {
		f = document.contractInvestmentForm;

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

        formatCurrency();
	}

    function showLockAlert()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Contract can not be edited.");

            return false;
        }
    }

	function addNewFund()
    {
        sendTransactionAction("ContractDetailTran", "clearInvestmentsForAddOrCancel", "contentIFrame");
	}

	function cancelFundInfo()
    {
        sendTransactionAction("ContractDetailTran", "clearInvestmentsForAddOrCancel", "contentIFrame");
	}

	function clearForm()
    {
		f.filteredFundFK.selectedIndex = 0;
		f.optionId.selectedIndex = 0;
		f.allocationPercent.value = "";
        f.fundType.value = "";
        f.air.value = "";
	}

	function saveFundToSummary()
    {
        if (f.filteredFundFK.value == "" ||
            f.filteredFundFK.value == "Please Select" ||
            f.optionId.value == "" ||
            f.optionId.value == "Please Select" ||
            f.allocationPercent.value == "")
        {
            alert ("Coverage, Fund, and Allocation Percent Must Be Selected/Entered");
        }
        else if ((segmentFK != "0" &&
                  segmentFK != "") &&
                  f.filteredFundFK.value != origFilteredFundFK)
        {
            alert("Fund Cannot Be Changed on Active Contract.  You Must Add a New Fund.");
        }
        else if (overrideStatus == "0")
        {
            alert("Override Fund Cannot Be Changed");
        }
        else
        {
            sendTransactionAction("ContractDetailTran", "saveFundToSummary", "contentIFrame");
        }
	}

	function showDepositBuckets()
    {
        if (depositBucketsIndStatus == "unchecked")
        {
            alert("There is no deposit bucket information for this fund");
        }
        else
        {
            var width = .75 * screen.width;
            var height = .92 * screen.height;

            openDialog("depositBucketsDialog","left=0,top=0,resizable=no", width, height);

            sendTransactionAction("ContractDetailTran", "showDepositBuckets", "depositBucketsDialog");
        }
	}

	function deleteSelectedFund()
    {
		f.selectedFundId.value     = filteredFundFK;
		f.selectedOptionId.value   = optionId;

		clearForm();

		sendTransactionAction("ContractDetailTran", "deleteSelectedFund", "contentIFrame");
	}

	function selectRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var rowId     = trElement.id;
		var parsedRowId = (rowId.split("_"))[1];

		var filteredFundFK  = trElement.filteredFundFK;

		var optionTd  = document.getElementById("optionId_" + parsedRowId);
		var optionId  = optionTd.innerText;

		f.selectedFundId.value = filteredFundFK;
		f.selectedOptionId.value = optionId;

		sendTransactionAction("ContractDetailTran", "showFundDetailSummary", "contentIFrame");
	}

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
    }
</script>

<head>
<title>Contract Investments</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "contractInvestmentForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="contractInfoHeader.jsp" flush="true"/>

  <table width="100%" height="48%" border="0" cellspacing="0" cellpadding="5">
    <tr valign="top">
	  <td colspan="2" nowrap><br>Coverage:
		<select name="optionId">
		  <option> Please Select </option>
		  <%

            for(int i = 0; i < options.length; i++) {

                String codeTablePK = options[i].getCodeTablePK() + "";
                String codeDesc    = options[i].getCodeDesc();
                String code        = options[i].getCode();

                if (optionId.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                }
            }

		  %>
		</select>
        <br>
        <br>
		Allocation %:&nbsp;
        <input type="text" name="allocationPercent" maxlength="9" size="9" value="<%= allocationPercent %>">
        <br>
        <br>
        <input type="checkbox" name="depositBucketsInd" <%= depositBucketsIndStatus %> disabled>
            <a href = "javascript:showDepositBuckets()">Deposit Buckets</a>
        <br>
        <br>
        <br>
	  </td>
	  <td nowrap>
        <br>
	    Fund:&nbsp;
	    <select name="filteredFundFK" value="<%= filteredFundFK %>">
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
        <input type="text" name="air" disabled maxlength="9" size="9" value="<%= air %>">
	  </td>
      <td nowrap>
        <br>
		Fund Type:&nbsp;
        <input type="text" name="fundType" disabled maxlength="20" size="10" value="<%= fundType %>">
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
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th width="25%" align="left">Fund</th>
      <th width="25%" align="left">Allocation</th>
	  <th width="25%" align="left">Cum Dollars/Units</th>
      <th width="25%" align="left">Coverage</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="investmentsSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
          int rowId   = 0;
          int columnId = 0;

          String rowToMatch = "";
          String trClass = "";
          String selected = "";
          String sOption = "";
          String sOverrideStatus = "";
          String sAllocationPct = "";
          String sFilteredFundFK = "";
          String sFundName = "";
          String sFundType = "";
          String sCumDollars = "";
          String sCumUnits = "";
          String sGuarCumValue = "";

          Map allFunds = contractFunds.getPageBeans();

          Iterator it = allFunds.values().iterator();

          while (it.hasNext())
          {
              PageBean fund = (PageBean)it.next();

              String status = fund.getValue("status");
              if (status == null || status.equals(""))
              {
                  sFilteredFundFK = fund.getValue("filteredFundFK");
                  sFundName = fund.getValue("fundName");
                  if (fundName.length() > 20)
                  {
                      fundName = fundName.substring(0, 20);
                  }

                  sFundType = fund.getValue("fundType");
                  sOption = fund.getValue("optionId");
                  sOverrideStatus = fund.getValue("overrideStatus");
                  sAllocationPct = fund.getValue("allocationPercent");
                  if (!sAllocationPct.equals(""))
                  {
                      sAllocationPct = Util.formatDecimal("###,###,##0.0000", new EDITBigDecimal(sAllocationPct));
                  }
                  sCumDollars = fund.getValue("cumDollars");
                  sCumUnits = fund.getValue("cumUnits");
                  sGuarCumValue = fund.getValue("guarCumValue");
                  String cumDollarsUnits = "";
                if (sOverrideStatus.equalsIgnoreCase("P") ||
                    (sOverrideStatus.equalsIgnoreCase("O") &&
                     (!new EDITBigDecimal(sCumDollars).isEQ(new EDITBigDecimal()) ||
                      !new EDITBigDecimal(sCumUnits).isEQ(new EDITBigDecimal()))))
                {
                    if (sFundType.equalsIgnoreCase("Loan"))
                    {
                        //double loanValue = Double.parseDouble(sCumDollars) + Double.parseDouble(sGuarCumValue);
                        EDITBigDecimal loanValue = new EDITBigDecimal(sCumDollars).addEditBigDecimal(new EDITBigDecimal(sGuarCumValue));
                        cumDollarsUnits = loanValue.toString() + "";
                    }
                    else
                    {
                        if (new EDITBigDecimal(sCumUnits).isEQ(new EDITBigDecimal()))
                        {
                            cumDollarsUnits = sCumDollars;
                        }
                        else
                        {
                            cumDollarsUnits = sCumUnits;
                        }
                    }

                      rowToMatch = sOption + sFilteredFundFK;

                      if (rowToMatch.equals(rowToMatchBase))
                      {
                          trClass = "highlighted";
                          selected = "true";
                      }
                      else
                      {
                          trClass = "default";
                          selected="false";
                      }
      %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>" id="rowId_<%= rowId++ %>"
            filteredFundFK="<%= sFilteredFundFK %>" onClick="selectRow()"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
          <td width="25%" nowrap id="fundName_<%= columnId %>">
            <%= sFundName %>
          </td>
          <td width="25%" nowrap id="allocationId_<%= columnId %>">
            <%= sAllocationPct %>
          </td>
          <td width="25%" nowrap id="cumDollarsUnitsId_<%= columnId %>">
            <%
                if (new EDITBigDecimal(sCumUnits).isEQ(new EDITBigDecimal()))
                {
            %>
            <script>document.write(formatAsCurrency(<%= cumDollarsUnits %>))</script>
            <%
                }
                else
                {
            %>
            <%= cumDollarsUnits %>
            <%
                }
            %>
          </td>
          <td width="25%" nowrap id="optionId_<%= columnId++ %>">
            <%= sOption %>
          </td>
        </tr>
      <%
                  }// end if
              }// end if (status == null)
          }// end while
      %>
    </table>
  </span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="selectedFundId" value="">
 <input type="hidden" name="selectedOptionId"     value="">

 <input type="hidden" name="investmentPK" value="<%= investmentPK %>">
 <input type="hidden" name="segmentFK" value="<%= segmentFK %>">
 <input type="hidden" name="invAllocPK" value="<%= invAllocPK %>">
 <input type="hidden" name="dollars" value="<%= fundDollars %>">
 <input type="hidden" name="units" value="<%= fundUnits %>">
 <input type="hidden" name="depositDate" value="<%= depositDate %>">
 <input type="hidden" name="excessIntCalcDate" value="<%= excessIntCalcDate %>">
 <input type="hidden" name="excessIntPymtDate" value="<%= excessIntPymtDate %>">
 <input type="hidden" name="excessInterest" value="<%= excessInterest %>">
 <input type="hidden" name="excessIntMethod" value="<%= excessIntMethod %>">
 <input type="hidden" name="excessInStartDate" value="<%= excessIntStartDate %>">
 <input type="hidden" name="overrideStatus" value="<%= overrideStatus %>">
 <input type="hidden" name="fundType" value="<%= fundType %>">

</form>
</body>
</html>