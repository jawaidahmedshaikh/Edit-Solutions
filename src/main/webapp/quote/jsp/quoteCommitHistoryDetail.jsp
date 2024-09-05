<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 event.EDITTrx,
                 edit.portal.common.session.UserSession,
                 fission.utility.*" %>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

     String companyStructureKey = quoteMainSessionBean.getPageBean("formBean").getValue("companyStructureId");

    CodeTableVO[] reversalReasonCodes = new edit.common.vo.CodeTableVO[0];
    if (companyStructureKey.equals(""))
    {
        reversalReasonCodes = codeTableWrapper.getCodeTableEntries("REVERSALREASONCODE");
    }
    else
    {
        reversalReasonCodes = codeTableWrapper.getCodeTableEntries("REVERSALREASONCODE", Long.parseLong(companyStructureKey));
    }

    EDITTrxVO[] editTrxVOs = (EDITTrxVO[]) session.getAttribute("editTrxVOs");
    ChangeHistoryVO[] changeHistoryVOs = (ChangeHistoryVO[]) session.getAttribute("changeHistoryVOs");

    String rowToMatchBase   = "";
    String effectiveDate    = "";
	String dateTime 		= "";
	String operator  		= "";
    String processDate	 	= "";
	String transactionType 	= "";
	String optionId 	 	= "";
    String optionIdValue    = "";
	String sequence		   	= "";
    String origProcessDate  = "";
	String premiumType      = "";
	String taxYear          = "";
	String grossAmount      = "";
    String charges          = "";
	String netAmount        = "";
    String reversalReasonCode = "";

    String selectedEDITTrxHistoryPK = (String) request.getAttribute("selectedEDITTrxHistoryPK");
    String correspondenceExistsStatus = "unchecked";
    String editTrxPK = "";
    if (selectedEDITTrxHistoryPK == null)
    {
        selectedEDITTrxHistoryPK = "";
    }
    else
    {
        rowToMatchBase = selectedEDITTrxHistoryPK;
    }

    if (editTrxVOs != null && !selectedEDITTrxHistoryPK.equals(""))
    {
        for (int h = 0; h < editTrxVOs.length; h++)
        {
            EDITTrxHistoryVO[] editTrxHistoryVOs = editTrxVOs[h].getEDITTrxHistoryVO();
            if (editTrxHistoryVOs != null)
            {
                for (int i = 0; i < editTrxHistoryVOs.length; i++)
                {
                    if ((editTrxHistoryVOs[i].getEDITTrxHistoryPK() + "").equals(selectedEDITTrxHistoryPK))
                    {
                        editTrxPK = editTrxVOs[h].getEDITTrxPK() + "";
                        ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVOs[h].getParentVO(ClientSetupVO.class);
                        ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
                        GroupSetupVO groupSetupVO = (GroupSetupVO) contractSetupVO.getParentVO(GroupSetupVO.class);
                        SegmentVO segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                        long companyStructureId = segmentVO.getProductStructureFK();
                        int correspondenceCount = editTrxVOs[h].getEDITTrxCorrespondenceVOCount();
                        if (correspondenceCount > 0)
                        {
                            correspondenceExistsStatus = "checked";
                        }

                        transactionType = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", editTrxVOs[h].getTransactionTypeCT(), companyStructureId);

                        effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(editTrxVOs[h].getEffectiveDate());


                        EDITDateTime processDateTime = new EDITDateTime(editTrxHistoryVOs[i].getProcessDateTime());

                        processDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(processDateTime.getEDITDate().getFormattedDate());

                        int financialHistoryCount = editTrxHistoryVOs[i].getFinancialHistoryVOCount();
                        if (financialHistoryCount > 0)
                        {
                            FinancialHistoryVO financialHistoryVO = editTrxHistoryVOs[i].getFinancialHistoryVO(0);
                            grossAmount = Util.formatDecimal("########0.00", Util.roundToNearestCent(financialHistoryVO.getGrossAmount()));
                            netAmount = Util.formatDecimal("########0.00", Util.roundToNearestCent(financialHistoryVO.getNetAmount()));
                        }

                        int chargeHistoryCount = editTrxHistoryVOs[i].getChargeHistoryVOCount();
                        if (chargeHistoryCount > 0) {

                            EDITBigDecimal fees = new EDITBigDecimal();
                            EDITBigDecimal loads = new EDITBigDecimal();
                            EDITBigDecimal premTaxes = new EDITBigDecimal();
                            EDITBigDecimal chargeAmount = new EDITBigDecimal();
                            ChargeHistoryVO[] chargeHistoryVOs = editTrxHistoryVOs[i].getChargeHistoryVO();
                            for (int c = 0; c < chargeHistoryVOs.length; c++)
                            {
                                String chargeName = chargeHistoryVOs[c].getChargeTypeCT();
                                if (chargeName == null)
                                {
                                    chargeName = "";
                                }
                                chargeAmount = new EDITBigDecimal( chargeHistoryVOs[c].getChargeAmount() );
                                if (chargeName.equalsIgnoreCase("MVA"))
                                {
                                    chargeAmount = chargeAmount.negate();
                                }
                                if (chargeName.equalsIgnoreCase("PremiumTax"))
                                {
                                    premTaxes = premTaxes.addEditBigDecimal(chargeAmount);
                                }
                                if (chargeName.equalsIgnoreCase("TransactionFee"))
                                {
                                    fees = fees.addEditBigDecimal(chargeAmount);
                                }
                                else
                                {
                                    loads = loads.addEditBigDecimal(chargeAmount);
                                }
                            }

                            EDITBigDecimal totalCharges = new EDITBigDecimal();
                            totalCharges = totalCharges.addEditBigDecimal(fees);
                            totalCharges = totalCharges.addEditBigDecimal(loads);
                            totalCharges = totalCharges.addEditBigDecimal(premTaxes);
                            charges = Util.formatDecimal( "########0.00", Util.roundToNearestCent(totalCharges) );
                        }

                        operator = Util.initString(editTrxVOs[h].getOperator(), "");
                        dateTime = editTrxVOs[h].getMaintDateTime();
                        reversalReasonCode = Util.initString(editTrxVOs[h].getReversalReasonCodeCT(), "");
                        sequence = editTrxVOs[h].getSequenceNumber() + "";
                        premiumType = groupSetupVO.getPremiumTypeCT();
                        if (premiumType == null)
                        {
                            premiumType = "";
                        }
                        taxYear = editTrxVOs[h].getTaxYear() + "";


                        EDITDateTime originalProcessDateTime = new EDITDateTime(editTrxHistoryVOs[i].getOriginalProcessDateTime());

                        origProcessDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(originalProcessDateTime.getEDITDate().getFormattedDate());

//                        SegmentVO segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                        optionId = segmentVO.getOptionCodeCT();
                        optionIdValue = codeTableWrapper.getCodeDescByCodeTableNameAndCode("OPTIONCODE", optionId, companyStructureId);
                    }
                }
            }
        }
    }

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<%!
	private EDITTrxVO[] sortHistoryByEffectiveDate(EDITTrxVO[] editTrxVOs,
                                                ChangeHistoryVO[] changeHistoryVOs)
    {
//		TreeMap sortedHistories = new TreeMap();

        if (editTrxVOs != null)
        {
            editTrxVOs = (EDITTrxVO[])Util.sortObjects(editTrxVOs, new String[]{ "getEffectiveDate", "getSequenceNumber"});

//            for (int e = 0; e < editTrxVOs.length; e++)
//            {
//                sortedHistories.put(editTrxVOs[e].getEffectiveDate() +
//                                     editTrxVOs[e].getEDITTrxPK() + "fh", editTrxVOs[e]);
//            }
        }

//        if (changeHistoryVOs != null)
//        {
//            for (int c = 0; c < changeHistoryVOs.length; c++)
//            {
//                sortedHistories.put(changeHistoryVOs[c].getEffectiveDate() +
//                                     changeHistoryVOs[c].getChangeHistoryPK() + "ch", changeHistoryVOs[c]);
//            }
//        }


		return editTrxVOs;
	}
%>


<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

    var shouldShowLockAlert = false;
    var historyKey = "<%= selectedEDITTrxHistoryPK %>";
    var editTrxPK = "<%= editTrxPK %>";
    var height = screen.height;
    var width  = screen.width;
    var responseMessage = "<%= responseMessage %>";

	function init()
    {
		f = document.newBusinessFinHistForm;
		top.frames["main"].setActiveTab("historyTab");

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
        checkForResponseMessage();        
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

		var key = trElement.id;

		f.historyKey.value = key;

		prepareToSendTransactionAction("QuoteDetailTran", "showHistoryDetailSummary", "contentIFrame");
	}

	function showCharges()
    {
        f.historyKey.value = historyKey;

        var width = 0.75 * screen.width;
        var height = .50 * screen.height;

		openDialog("chargesDialog", "left=0,top=0,resizable=no", width, height);

        prepareToSendTransactionAction("QuoteDetailTran", "showChargesDialog", "chargesDialog");
	}

    function showCorrespondenceDialog()
    {
        var width = 0.75 * screen.width;
        var height = .50 * screen.height;

        f.editTrxPK.value = editTrxPK;
		openDialog("correspondenceDialog", "left=0,top=0,resizable=no", width, height);

        prepareToSendTransactionAction("QuoteDetailTran", "showCorrespondenceDialog", "correspondenceDialog");
    }

	function runReversal()
    {
		if (f.reversal.value == "")
        {
			alert("Reversal Needs To Be Entered");
		}

        else if (status == "U" || status == "R")
        {
            alert("Cannot Reverse an Undone/Reversed Transaction");
        }
		else
        {
			prepareToSendTransactionAction("QuoteDetailTran", "runReversal", "contentIFrame")
		}
	}

	function cancelReversal()
    {
		f.reversal.value = "";
	}

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target)
    }
</script>

<head>
<title>New Business History Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "newBusinessFinHistForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="quoteInfoHeader.jsp" flush="true"/>

  <table width="80%" height="40%" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="left" nowrap>Effective Date:&nbsp;
        <input disabled type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10">
       </td>
      <td align="left" nowrap>Process Date:&nbsp;
        <input disabled type="text" name="processDate" value="<%= processDate %>" size='10' maxlength="10">
        </td>
      <td align="left" nowrap>Transaction Type:&nbsp;
        <input disabled type="text" name="transactionType" value="<%= transactionType%>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Segment:&nbsp;
        <input disabled type="text" name="segment" value="<%= optionIdValue %>">
      </td>
      <td align="left" nowrap>Sequence:&nbsp;
        <input disabled type="text" name="sequenceNumber" value="<%= sequence%>">
      </td>
      <td align="left" nowrap>Original Process Date:&nbsp;
        <input disabled type="text" name="origProcessDate" value="<%= origProcessDate %>" size='10' maxlength="10">
        </td>
    </tr>
    <tr>
      <td align="left" nowrap>Premium Type:&nbsp;
        <input disabled type="text" name="premiumType" value="<%= premiumType%>">
      </td>
      <td align="left" nowrap>Tax Year:&nbsp;
        <input disabled type="text" name="taxYear" size="4" maxlength="4" value="<%= taxYear%>">
      </td>
      <td align="left" nowrap>
        <input type="checkbox" name="correspondenceInd" <%= correspondenceExistsStatus %> disabled>
        <a href="javascript:showCorrespondenceDialog()">Correspondence</a>
      </td>
    </tr>
    <tr>
      <td colspan="3">Transaction Amounts:</td>
    </tr>
    <tr>
      <td align="left" nowrap>Gross Amount:&nbsp;
        <input disabled type="text" name="grossAmount" value="<%= grossAmount%>" CURRENCY>
      </td>
      <td align="left" nowrap><a href ="javascript:showCharges()">Total Charges</a>
        <input type="text" name="charges" size="10" value="<%= charges %>" CURRENCY>
      </td>
      <td align="left" nowrap>Net Amount:&nbsp;
        <input disabled type="text" name="netAmount" value="<%= netAmount%>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td colspan="3" nowrap>Investment Amounts:</td>
    </tr>
  </table>
  <table class="summary" id="summaryTable2" width="100%" border="0" cellspacing="0" cellpadding="2">
	<tr class="heading">
	  <th align="left" width="16%">Investment/Fund</th>
	  <th align="left" width="16%">Alloc %</th>
	  <th align="left" width="16%">Dollars</th>
	  <th align="left" width="16%">Units</th>
	  <th align="left" width="16%">Cum Unit/Dollars</th>
	  <th align="left" width="16%">Gain/Loss</th>
	</tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:28%; top:0; left:0; background-color:#BBBBBB">
    <table id="quoteHistoryFundSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
			<%
                String fundDescription = "";
                String allocationPercent = "";
                String dollars = "0.00";
                String units = "";
                String summaryValue = "";
                String gainLoss = "";
			%>
			<tr>
			  <td nowrap width="16%">
				<%= fundDescription %>
			  </td>
			  <td nowrap width="16%">
				<%= allocationPercent %>
			  </td>
			  <td nowrap width="16%">
                <script>document.write(formatAsCurrency(<%= dollars %>))</script>
			  </td>
			  <td nowrap width="16%">
				<%= units %>
			  </td>
			  <td nowrap width="16%">
				<%= summaryValue %>
			  </td>
			  <td nowrap width="16%">
				<%= gainLoss %>
			  </td>
			</tr>
			<%
			%>
          </table>
        </span>
      </td>
    </tr>
    
  </table>
  <table>
     <tr>
	   <td nowrap align="left">Operator:&nbsp;
	      <input type="text" name="operator" disabled value="<%= operator%>">
	   </td>
	   <td nowrap align="left">Date/Time:&nbsp;
	      <input type="text" name="dateTime" disabled value="<%= dateTime%>">
	   </td>
	   <td nowrap align="left">Reversal Reason Code:&nbsp;
	     <input type="text" name="dateTime" disabled value="<%= reversalReasonCode %>">
	  </td>
	 </tr>
  </table>
  <%--<table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	  <td nowrap align="left">Reversal:&nbsp;
 	    <input type="text" size="1" maxlength="1" name="reversal">
	  </td>
      <td nowrap align="left">Reversal Reason Code:&nbsp;
        <select name="reversalReasonCode">
          <%
         	  out.println("<option>Please Select</option>");

              for(int i = 0; i < reversalReasonCodes.length; i++)
              {
                  String codeTablePK = reversalReasonCodes[i].getCodeTablePK() + "";
                  String codeDesc    = reversalReasonCodes[i].getCodeDesc();
                  String code        = reversalReasonCodes[i].getCode();

                  if (reversalReasonCode.equalsIgnoreCase(code))
                  {
                      out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
          %>
        </select>
      </td>
	  <td nowrap align="left">Operator:&nbsp;
        <input type="text" name="operator" disabled value="<%= operator%>">
      </td>
      <td nowrap align="left">Date/Time:&nbsp;
        <input type="text" name="dateTime" disabled value="<%= dateTime%>">
      </td>
    </tr>
  </table>
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
  	  <td nowrap align="left">
  		<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="runReversal()">
  		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelReversal()">
      </td>
  	</tr>
  </table>--%>
  <br/><br/>
  <table class="summary" id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th width="11%" align="left">Acct Pend</th>
      <th width="11%" align="left">Eff Date</th>
      <th width="11%" align="left">Proc Date</th>
      <th width="11%" align="left">Tran Type</th>
	  <th width="11%" align="left">Seq</th>
	  <th width="11%" align="left">Status</th>
	  <th width="11%" align="left">Gross Amt</th>
	  <th width="11%" align="left">Check Amt</th>
	  <th width="11%" align="left">Coverage</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="quoteHistorySummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
        <%
          String rowToMatch = "";
          String trClass = "";
          boolean selected = false;

          EDITTrxVO[] sortedEDITTrxVOs = sortHistoryByEffectiveDate(editTrxVOs, changeHistoryVOs);

//          Iterator it = sortedHistories.values().iterator();

          ChangeHistoryVO[] sortedChangeHistoryVOs = null;
          if (changeHistoryVOs != null)
          {
              sortedChangeHistoryVOs = (ChangeHistoryVO[])Util.sortObjects(changeHistoryVOs, new String[]{ "getEffectiveDate"});
          }

          if (sortedEDITTrxVOs != null)
          {
              for (int j = 0; j < sortedEDITTrxVOs.length; j++)
              {
                  EDITTrxVO editTrxVO = sortedEDITTrxVOs[j];
                  String accountPendingInd = "";
                  String hEffectiveDate = "";
                  String hProcessDate = "";
                  String hTransactionType = "";
                  String hSequence = "";
                  String hStatus = "";
                  String hGrossAmount = "0";
                  String hCheckAmount = "0";
                  String hOptionIdValue = "";
                  String key = "";



                  if (editTrxVO != null)
                  {
                      ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
                      ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
                      GroupSetupVO groupSetupVO = (GroupSetupVO) contractSetupVO.getParentVO(GroupSetupVO.class);
                      SegmentVO segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                      long companyStructureId = segmentVO.getProductStructureFK();

                      EDITTrxHistoryVO[] editTrxHistoryVOs = editTrxVO.getEDITTrxHistoryVO();
                      if (editTrxHistoryVOs != null)
                      {
                          for (int i = 0; i < editTrxHistoryVOs.length; i++)
                          {
                              key = editTrxHistoryVOs[i].getEDITTrxHistoryPK() + "";
                              accountPendingInd = editTrxHistoryVOs[i].getAccountingPendingStatus();

                              hEffectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(editTrxVO.getEffectiveDate());

                              EDITDateTime processDateTime = new EDITDateTime(editTrxHistoryVOs[i].getProcessDateTime());
                              hProcessDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(processDateTime.getEDITDate().getFormattedDate());

                              hTransactionType = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", editTrxVO.getTransactionTypeCT(), companyStructureId);
                              hSequence = editTrxVO.getSequenceNumber() + "";
                              hStatus = editTrxVO.getStatus();
                              if (editTrxHistoryVOs[i].getFinancialHistoryVOCount() > 0)
                              {
                                  FinancialHistoryVO financialHistoryVO = editTrxHistoryVOs[i].getFinancialHistoryVO(0);
                                  hGrossAmount = Util.roundToNearestCent(financialHistoryVO.getGrossAmount()).toString();
                                  hCheckAmount = Util.roundToNearestCent(financialHistoryVO.getCheckAmount()).toString();
                              }

                              hOptionIdValue = segmentVO.getOptionCodeCT();

                              rowToMatch = key;
                              if (rowToMatch.equals(rowToMatchBase))
                              {
                                  trClass = "highlighted";
                                  selected = true;
                              }
                              else
                              {
                                  trClass = "default";
                                  selected = false;
                              }
        %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>" id="<%= "fh_" + key %>"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
          <td nowrap width="11%">
            <%= accountPendingInd %>
          </td>
          <td nowrap width="11%">
            <%= hEffectiveDate %>
          </td>
          <td nowrap width="11%">
            <%= hProcessDate %>
          </td>
          <td nowrap width="11%">
            <%= hTransactionType %>
          </td>
          <td nowrap width="11%">
            <%= hSequence %>
          </td>
          <td nowrap width="11%">
            <%= hStatus %>
          </td>
          <td nowrap width="11%">
            <script>document.write(formatAsCurrency(<%= hGrossAmount %>))</script>
          </td>
          <td nowrap width="11%">
            <script>document.write(formatAsCurrency(<%= hCheckAmount %>))</script>
          </td>
          <td nowrap width="11%">
            <%= hOptionIdValue %>
          </td>
        </tr>
        <%
                          } // end for (EDITTrxHistory loop)
                      } // end if (EDITTrxHistory)
                  } // end if (EDITTrx)
              } //  end for
          } // end if (sortedEDITTrxVOs)

            if (sortedChangeHistoryVOs != null)
            {
                for (int i = 0; i < sortedChangeHistoryVOs.length; i++ )
                {
                    ChangeHistoryVO changeHistoryVO = sortedChangeHistoryVOs[i];
                    String accountPendingInd = "";
                    String hEffectiveDate = "";
                    String hProcessDate = "";
                    String hTransactionType = "";
                    String hSequence = "";
                    String hStatus = "";
                    String hGrossAmount = "0";
                    String hCheckAmount = "0";
                    String hOptionIdValue = "";
                    String key = "";
                    if (changeHistoryVO != null)
                    {

                        key = changeHistoryVO.getChangeHistoryPK() + "";

                        hEffectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(changeHistoryVO.getEffectiveDate());

                        hProcessDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(changeHistoryVO.getProcessDate());

                        hTransactionType = "Non Financial";
                        SegmentVO segmentVO = (SegmentVO) changeHistoryVO.getParentVO(SegmentVO.class);
                        hOptionIdValue = segmentVO.getOptionCodeCT();

                        rowToMatch = key;

                        if (rowToMatch.equals(rowToMatchBase))
                        {
                            trClass = "highlighted";
                            selected = true;
                        }
                        else
                        {
                            trClass = "default";
                            selected = false;
                        }
              %>
              <tr class="<%= trClass %>" isSelected="<%= selected %>" id="<%= "ch_" + key %>"
                  onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
                <td nowrap width="11%">
                  <%= accountPendingInd %>
                </td>
                <td nowrap width="11%">
                  <%= hEffectiveDate %>
                </td>
                <td nowrap width="11%">
                  <%= hProcessDate %>
                </td>
                <td nowrap width="11%">
                  <%= hTransactionType %>
                </td>
                <td nowrap width="11%">
                  <%= hSequence %>
                </td>
                <td nowrap width="11%">
                  <%= hStatus %>
                </td>
                <td nowrap width="11%">
                  <script>document.write(formatAsCurrency(<%= hGrossAmount %>))</script>
                </td>
                <td nowrap width="11%">
                  <script>document.write(formatAsCurrency(<%= hCheckAmount %>))</script>
                </td>
                <td nowrap width="11%">
                  <%= hOptionIdValue %>
                </td>
              </tr>
              <%
                    } // end if (ChangeHistory)
               } //ednf for
            } // end if
        %>
    </table>
  </span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="historyKey" value="">
 <input type="hidden" name="editTrxPK" value="<%= editTrxPK %>">

 <!-- recordPRASEEvents is set by the toolbar when saving the client -->
 <input type="hidden" name="recordPRASEEvents" value="false">

</form>
</body>
</html>
