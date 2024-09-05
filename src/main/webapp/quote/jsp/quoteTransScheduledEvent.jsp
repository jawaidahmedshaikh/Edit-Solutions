<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 fission.utility.*" %>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    PageBean mainBean = quoteMainSessionBean.getPageBean("formBean");
    String companyStructureId = Util.initString(mainBean.getValue("companyStructureId"), "0");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    EDITTrxVO[] editTrxVOs = (EDITTrxVO[]) session.getAttribute("pendingTransactions");
    EDITTrxVO selectedEDITTrx = (EDITTrxVO) request.getAttribute("selectedTransaction");
        
    String editTrxPK = "";
    String transactionType = Util.initString((String) request.getAttribute("transactionType"), "");
    String effectiveDate = "";
    String startDate = "";
    String stopDate = "";
    String frequency = "";
    String sequenceNumber = "";
    String amount = "";
    String taxYear = "";
    String investmentInd = "unchecked";
    String eventInd = "unchecked";
    String payeeInd = "unchecked";
    String operator = "";
    String maintDate = "";
    String incDecPercent = "";
    String costOfLivingInd = "unchecked";
    String distributionCode = "";
    String lifeContingent = "";
    String optionCode = "";

    if (selectedEDITTrx != null)
    {   
        ClientSetupVO clientSetupVO = (ClientSetupVO) selectedEDITTrx.getParentVO(ClientSetupVO.class);
        ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
        GroupSetupVO groupSetupVO = (GroupSetupVO) contractSetupVO.getParentVO(GroupSetupVO.class);
        SegmentVO segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);

        editTrxPK = selectedEDITTrx.getEDITTrxPK() + "";
    	transactionType	= selectedEDITTrx.getTransactionTypeCT();
        effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(selectedEDITTrx.getEffectiveDate());
        
        if (groupSetupVO.getScheduledEventVOCount() > 0)
        {
            ScheduledEventVO scheduledEventVO = groupSetupVO.getScheduledEventVO(0);
            startDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(scheduledEventVO.getStartDate());
            stopDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(scheduledEventVO.getStopDate());
            frequency = Util.initString(scheduledEventVO.getFrequencyCT(), "");
            if (scheduledEventVO.getCostOfLivingInd() != null &&
                scheduledEventVO.getCostOfLivingInd().equalsIgnoreCase("Y"))
            {
                costOfLivingInd = "checked";
            }

            lifeContingent = Util.initString(scheduledEventVO.getLifeContingentCT(), "");
        }
        
        sequenceNumber	= selectedEDITTrx.getSequenceNumber() + "";
        amount = selectedEDITTrx.getTrxAmount().toString();
	    taxYear = selectedEDITTrx.getTaxYear() + "";
        if (contractSetupVO.getInvestmentAllocationOverrideVOCount() > 0)
        {
	        investmentInd = "checked";
        }
        
        if (groupSetupVO.getChargeVOCount() > 0)
        {
	        eventInd = "checked";
        }
        
        if (clientSetupVO.getContractClientAllocationOvrdVOCount() > 0)
        {
	        payeeInd = "checked";
        }
        
	    operator = selectedEDITTrx.getOperator();
	    maintDate = selectedEDITTrx.getMaintDateTime();

        distributionCode = Util.initString(groupSetupVO.getDistributionCodeCT(), "");

        optionCode = Util.initString(segmentVO.getOptionCodeCT(), "");
    }

    CodeTableVO[] trxTypes       = codeTableWrapper.getCodeTableEntries("TRXTYPE", Long.parseLong(companyStructureId));
    CodeTableVO[] frequencies    = codeTableWrapper.getCodeTableEntries("FREQUENCY", Long.parseLong(companyStructureId));
	CodeTableVO[] distributions  = codeTableWrapper.getCodeTableEntries("DISTRIBUTIONCODE", Long.parseLong(companyStructureId));
	CodeTableVO[] options        = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));
	CodeTableVO[] lifeContingencies = codeTableWrapper.getCodeTableEntries("LIFECONTINGENT", Long.parseLong(companyStructureId));

    String trxDescription = "";
    for (int i = 0; i < trxTypes.length; i++)
    {

        if (transactionType.equalsIgnoreCase(trxTypes[i].getCode())) {

            trxDescription = trxTypes[i].getCodeDesc();
            break;
        }
    }

	String rowToMatchBase = transactionType + effectiveDate + sequenceNumber;

//    String oldOptionId = formBean.getValue("oldOptionId");
//    String oldFrequencyId = formBean.getValue("oldFrequencyId");
//    String oldStartDate = formBean.getValue("oldStartDate");
//
    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<%!
	private TreeMap sortTransactionsByEffectiveDate(EDITTrxVO[] editTrxVOs)
    {
		TreeMap sortedTransactions = new TreeMap();

		for (int i = 0; i < editTrxVOs.length; i++)
        {
			sortedTransactions.put(editTrxVOs[i].getEffectiveDate() + editTrxVOs[i].getEDITTrxPK(), editTrxVOs[i]);
		}

		return sortedTransactions;
	}
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>


<script language="Javascript1.2">

	var f = null;

    var shouldShowLockAlert = true;
    var trxType = "<%= transactionType %>"

	function init() 
    {
		f = document.trxScheduledForm;

		top.frames["main"].setActiveTab("schedEventsTab");

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

        if (trxType == "IN") {

            f.costOfLivingInd.disabled = false;
        }
        else {

            f.costOfLivingInd.disabled = true;
        }

        formatCurrency();

        // Initialize scroll tables
        initScrollTable(document.getElementById("QuoteScheduledEventTableModelScrollTable"));
	}

    function showLockAlert()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Contract can not be edited.");

            return false;
        }
    }

    function checkForRequiredFields()
    {
		return true;
    }

	function selectRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var rowId     = trElement.id;
		var parsedRowId = (rowId.split("_"))[1];
        var parsedRowKey = (rowId.split("_"))[0];

		var optionTd  = document.getElementById("optionId_" + parsedRowId);
		var optionId  = optionTd.innerText;

		var transactionTd = document.getElementById("transactionId_" + parsedRowId);
		var transactionId = transactionTd.innerText;

        var frequencyTd = document.getElementById("frequency_" + parsedRowId);
        var frequency = frequencyTd.innerText;

        var startDateTd = document.getElementById("startDate_" + parsedRowId);
        var startDateId = startDateTd.innerText;

        var effectiveDate = parsedRowKey.substring(2, 12);

		f.selectedTransactionId.value = transactionId;
		f.selectedOptionId.value = optionId;
		f.selectedFrequencyId.value = frequency;
        f.selectedEffectiveDate.value = effectiveDate;
        f.selectedDueDate.value = startDateId;

		prepareToSendTransactionAction("QuoteDetailTran", "showTransactionDetailSummary", "contentIFrame");
	}

	function showPayeeDialog()
    {
        var width = 1;
        var height = 1;

		openDialog("payeeDialog", "resizable=no", width, height);

        prepareToSendTransactionAction("QuoteDetailTran", "showPayeeDialog", "payeeDialog");
	}

	function showEventDialog()
    {
        var width = 1;
        var height = 1;

		openDialog("eventDialog", "resizable=no", width, height);

        prepareToSendTransactionAction("QuoteDetailTran", "showEventDialog", "eventDialog");
	}

	function showInvestmentDialog()
    {
        var width = 1;
        var height = 1;

		openDialog("investmentDialog", "resizable=no", width, height);

        prepareToSendTransactionAction("QuoteDetailTran", "showInvestmentDialog", "investmentDialog");
	}

	function showTransactionTypeDialog()
    {
        var width = 1;
        var height = 1;

	  	openDialog("transactionTypeDialog", "resizable=no", width, height);

        prepareToSendTransactionAction("QuoteDetailTran", "showTransactionTypeDialog", "transactionTypeDialog");
	}

	function addNewTransaction()
    {
		f.reset();

        var width = 1;
        var height = 1;

		openDialog("transactionTypeDialog", "resizable=no", width, height);

        prepareToSendTransactionAction("QuoteDetailTran", "showTransactionTypeDialog", "transactionTypeDialog");
	}

	function saveTransactionToSummary()
    {
		prepareToSendTransactionAction("QuoteDetailTran", "saveTransactionToSummary", "contentIFrame");
	}

	function deleteTransaction()
    {
		prepareToSendTransactionAction("QuoteDetailTran", "deleteTransaction", "contentIFrame");
	}

	function prepareToSendTransactionAction(transaction, action, target)
    {
        if (f.costOfLivingInd.checked == true) {
            f.costOfLivingIndStatus.value = "checked";
        }

        sendTransactionAction(transaction, action, target);
	}

	function cancelTransaction()
    {
		prepareToSendTransactionAction("QuoteDetailTran", "cancelTransaction", "contentIFrame");
	}
</script>

<head>
<title>New Business Scheduled Event</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1">
<form name="trxScheduledForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="quoteInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="position:relative; border-style:solid; border-width:1; width:100%; height:2%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="20%" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td nowrap align="right">Transaction Type: </td>
      <td nowrap align="left">
        <input type="text" name="transactionType" size="15" maxlength="15" value="<%= trxDescription %>">
      </td>
      <td nowrap align="right">Frequency: </td>
      <td nowrap align="left">
        <select name="frequencyId">
          <%
            out.println("<option>Please Select</option>");

            for(int i = 0; i < frequencies.length; i++)
            {
                String codeTablePK = frequencies[i].getCodeTablePK() + "";
                String codeDesc    = frequencies[i].getCodeDesc();
                String code        = frequencies[i].getCode();

                if (frequency.equalsIgnoreCase(code))
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
      <td nowrap align="right">Sequence: </td>
      <td nowrap align="left">
        <input type="text" name="sequenceNumber" size="4" maxlength="4" value="<%= sequenceNumber %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Amount: </td>
      <td nowrap align="left">
        <input type="text" name="amount" size="12" maxlength="12" value="<%= amount %>" CURRENCY>
      </td>
      <td nowrap align="right">Percent: </td>
      <td nowrap align="left">
        <input type="text" name="incDecPercent" size="12" maxlength="12" value="<%= incDecPercent %>">
      </td>
      <td nowrap align="left" colspan="2">
        <input type="checkbox" name="costOfLivingInd" <%= costOfLivingInd %>>Cost Of Living
      </td>
    </tr>
    <tr>
      <td nowrap align="right">StartDate:</td>
      <td nowrap align="left">
           <input type="text" name="startDate" value="<%= startDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>

      <td nowrap align="right">StopDate: </td>
      <td nowrap align="left" colspan="3">
           <input type="text" name="stopDate" value="<%= stopDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.stopDate', f.stopDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Distribution Code: </td>
      <td nowrap align="left">
        <select name="distributionId">
          <%
		    out.println("<option>Please Select</option>");

            for(int i = 0; i < distributions.length; i++)
            {
                String codeTablePK = distributions[i].getCodeTablePK() + "";
                String codeDesc    = distributions[i].getCodeDesc();
                String code        = distributions[i].getCode();

                if (distributionCode.equalsIgnoreCase(code))
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
      <td nowrap align="right">Tax Year: </td>
      <td nowrap align="left" colslpan="3">
        <input type="text" name="taxYear" size="4" maxlength="4" value="<%= taxYear %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Coverage/Rider: </td>
      <td nowrap align="left">
        <select name="optionId">
          <%
		      out.println("<option>Please Select</option>");

              for(int i = 0; i < options.length; i++)
              {
                  String codeTablePK = options[i].getCodeTablePK() + "";
                  String codeDesc    = options[i].getCodeDesc();
                  String code        = options[i].getCode();

                  if (optionCode.equalsIgnoreCase(code))
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
      <td nowrap align="left" colspan="4"><b>Overrides:</b></td>
    </tr>
    <tr>
      <td nowrap align="right">Life Contingent:</td>
      <td nowrap align="left">
        <select name="lifeContingentId">
          <%
		      out.println("<option>Please Select</option>");

              for(int i = 0; i < lifeContingencies.length; i++)
              {
                String codeTablePK = lifeContingencies[i].getCodeTablePK() + "";
                String codeDesc    = lifeContingencies[i].getCodeDesc();
                String code        = lifeContingencies[i].getCode();

                if (lifeContingent.equalsIgnoreCase(code))
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
      <td nowrap align="left" colspan="4">
        <input type="checkbox" name="investmentInd" <%= investmentInd %> disabled>
        <a href="javascript:showInvestmentDialog()">Investments</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="checkbox" name="eventInd" <%= eventInd %> disabled>
        <a href="javascript:showEventDialog()"> Event</a>
        <input type="checkbox" name="payeeInd" <%= payeeInd %> disabled>
        <a href="javascript:showPayeeDialog()">Payee</a></td>
    </tr>
    <tr>
      <td nowrap align="right">Operator: </td>
      <td nowrap align="left">
        <input type="text" name="operator" size="10" maxlength="10" value="<%= operator %>" disabled>
      </td>
      <td nowrap align="right">Date/Time: </td>
      <td nowrap align="left" colspan="3">
        <input type="text" name="dateTime" size="10" maxlength="35" value="<%= maintDate %>" disabled>
      </td>
    </tr>
  </table>
</span>
<table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td nowrap align="left">
  	  <input type="button" value="   Add   " onClick="showTransactionTypeDialog()">
  	  <input type="button" value="   Save  " onClick="saveTransactionToSummary()">
  	  <input type="button" value="  Cancel " onClick="cancelTransaction()">
  	  <input type="button" value="  Delete " onClick="deleteTransaction()">
  	</td>
  </tr>
</table>

<%-- ********** BEGIN Summary Area ********** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
  <jsp:param name="tableId" value="QuoteScheduledEventTableModel"/>
  <jsp:param name="tableHeight" value="50"/>
  <jsp:param name="multipleRowSelect" value="false"/>
  <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ********** END Summary Area ********** --%>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"   value="">
 <input type="hidden" name="transactionPK" value="<%= editTrxPK %>">
 <input type="hidden" name="action"        value="">
 <input type="hidden" name="key"           value="">
 <input type="hidden" name="costOfLivingIndStatus" value="">

 <input type="hidden" name="selectedTransactionId" value="">
 <input type="hidden" name="selectedOptionId"      value="">
 <input type="hidden" name="selectedFrequencyId"   value="">
 <input type="hidden" name="selectedEffectiveDate" value="">
 <input type="hidden" name="selectedDueDate"       value="">

 <input type="hidden" name="oldOptionId" value="">
 <input type="hidden" name="oldFrequencyId" value="">
 <input type="hidden" name="oldStartDate" value="">

</form>
</body>
</html>
