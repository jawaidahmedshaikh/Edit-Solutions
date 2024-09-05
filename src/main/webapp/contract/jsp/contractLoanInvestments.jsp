<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession" %>

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
    String fundName           = formBean.getValue("fundName");
    String fundType           = formBean.getValue("fundType");
    String loanType           = formBean.getValue("loanType");
    String numberOfLoans      = formBean.getValue("numberOfLoans");
    String depositBucketsIndStatus = formBean.getValue("depositBucketsIndStatus");

    CodeTableVO[] options     = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));
    CodeTableVO[] fundTypeCTVOs = codeTableWrapper.getCodeTableEntries("FUNDTYPE", Long.parseLong(companyStructureId));
	String optionId           = formBean.getValue("optionId");
    String optionIdDesc       = "";
    for(int i = 0; i < options.length; i++)
    {
        if (optionId.equalsIgnoreCase(options[i].getCode()))
        {
            optionIdDesc = options[i].getCodeDesc();
            break;
        }
    }


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
    var origFilteredFundFK = "<%= filteredFundFK %>";

    var shouldShowLockAlert = true;

	function init()
    {
		f = document.contractLoanInvestmentForm;

        top.frames["main"].setActiveTab("investmentTab");

        if (investmentMessage != "" &&
            investmentMessage != null)
        {
            alert(investmentMessage);
        }

        formatCurrency();
	}

	function addNewFund()
    {
        sendTransactionAction("ContractDetailTran", "clearInvestmentsForAddOrCancel", "contentIFrame");
	}

	function cancelFundInfo()
    {
        sendTransactionAction("ContractDetailTran", "clearInvestmentsForAddOrCancel", "contentIFrame");
	}

    function showLoanPayoffQuoteDialog()
    {
        var width = .90 * screen.width;
        var height = .55 * screen.height;

        openDialog("loanPayoffDialog","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("ContractDetailTran", "showLoanPayoffQuoteDialog", "loanPayoffDialog");
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
            var height = .70 * screen.height;

            openDialog("depositBucketsDialog", "left=0,top=0,resizable=no", width, height);

            sendTransactionAction("ContractDetailTran", "showDepositBuckets", "depositBucketsDialog");
        }
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
<form name= "contractLoanInvestmentForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="contractInfoHeader.jsp" flush="true"/>

  <table width="100%" height="48%" border="0" cellspacing="0" cellpadding="5">
    <tr valign="top">
	  <td colspan="2" nowrap><br>Coverage:&nbsp;
        <input disabled type="text" name="optionId" maxlength="20" size="20" value="<%= optionIdDesc %>">
        <br>
        <br>
		Number Of Loans:&nbsp;
        <input disabled type="text" name="numberOfLoans" maxlength="3" size="3" value="<%= numberOfLoans %>">
        <br>
        <br>
        <input disabled type="checkbox" name="depositBucketsInd" <%= depositBucketsIndStatus %>>
            <a href = "javascript:showDepositBuckets()">Deposit Buckets</a>
        <br>
        <br>
        <br>
	  </td>
	  <td nowrap>
        <br>
	    Fund:&nbsp;
        <input disabled type="text" name="fund" maxlength="20" size="20" value="<%= fundName %>">
        <br>
        <br>
        Loan Type:&nbsp;
        <input disabled type="text" name="loanType" maxlength="20" size="20" value="<%= loanType %>">
	  </td>
      <td nowrap>
        <br>
		Fund Type:&nbsp;
        <input disabled type="text" name="fundType" maxlength="20" size="10" value="<%= fundType %>">
      </td>
    </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td nowrap align="left">
		<input type="button" value="Add" style="background-color:#DEDEDE" onClick="addNewFund()">
		<input type="button" value="Cancel" style="background-color:#DEDEDE" onClick="cancelFundInfo()">
        <input type="button" value="Loan Payoff" style="background-color:#DEDEDE" onClick="showLoanPayoffQuoteDialog()">
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
    <table class="summary" id="loanInvestmentsSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
          int rowId   = 0;
          int columnId = 0;

          String rowToMatch = "";
          String trClass = "";
          String selected = "";
          String sOption = "";
          String sAllocationPct = "";
          String sCumDollars = "";
          String sCumUnits = "";
          String sGuarCumValue = "";
          String sFundName = "";
          String sFundType = "";

          Map allFunds = contractFunds.getPageBeans();

          Iterator it = allFunds.values().iterator();

          while (it.hasNext())
          {
              PageBean fund = (PageBean)it.next();

              String sFilteredFundFK = fund.getValue("filteredFundFK");
              sFundName = fund.getValue("fundName");
              if (sFundName.length() > 20)
              {
                  sFundName = fundName.substring(0, 20);
              }

              sFundType = fund.getValue("fundType");
              sOption = fund.getValue("optionId");
              sAllocationPct = fund.getValue("allocationPercent");
              sCumDollars = fund.getValue("cumDollars");
              sCumUnits = fund.getValue("cumUnits");
              sGuarCumValue = fund.getValue("guarCumValue");
              String cumDollarsUnits = "";
              if (sFundType.equalsIgnoreCase("Loan"))
              {
                  EDITBigDecimal loanValue = new EDITBigDecimal(sCumDollars).addEditBigDecimal(new EDITBigDecimal(sGuarCumValue));
                  cumDollarsUnits = loanValue.toString() + "";
              }
              else
              {
                  if (sCumUnits.equals("0.0"))
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
              if (sCumUnits.equals("0.0") || sFundType.equalsIgnoreCase("Loan"))
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
          }// end while
      %>
    </table>
  </span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="selectedFundId" value="">
 <input type="hidden" name="selectedOptionId" value="">

 <input type="hidden" name="investmentPK" value="<%= investmentPK %>">
 <input type="hidden" name="segmentFK" value="<%= segmentFK %>">

 <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">
</form>
</body>
</html>