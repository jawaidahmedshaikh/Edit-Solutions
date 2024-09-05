<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 fission.utility.*" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    EDITTrxVO[] editTrxVOs = (EDITTrxVO[]) session.getAttribute("editTrxVOs");
    ChangeHistoryVO[] changeHistoryVOs = (ChangeHistoryVO[]) session.getAttribute("changeHistoryVOs");

    String rowToMatchBase   = "";
    String selectedChangeHistoryPK = (String) request.getAttribute("selectedChangeHistoryPK");
    if (selectedChangeHistoryPK == null)
    {
        selectedChangeHistoryPK = "";
    }
    else
    {
        rowToMatchBase = selectedChangeHistoryPK;
    }

	String effectiveDate   = "";
	String afterValue      = "";
	String beforeValue     = "";
	String fieldName       = "";
	String dateTime        = "";
	String operator        = "";
	String processDate	   = "";
	String transactionType = "";
	String optionId        = "";
    String idName          = (String) request.getAttribute("idName");
    if (idName == null)
    {
        idName = "";
    }
    String optionIdValue   = "";
    long companyStructureId = 0;

    if (changeHistoryVOs != null && !selectedChangeHistoryPK.equals(""))
    {
        for (int c = 0; c < changeHistoryVOs.length; c++)
        {
            effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(changeHistoryVOs[c].getEffectiveDate());
            beforeValue = changeHistoryVOs[c].getBeforeValue();
            afterValue = changeHistoryVOs[c].getAfterValue();
            fieldName = changeHistoryVOs[c].getFieldName();
            dateTime = changeHistoryVOs[c].getMaintDateTime();
            operator = changeHistoryVOs[c].getOperator();
            processDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(changeHistoryVOs[c].getProcessDate());
            transactionType = "Non Financial";
            SegmentVO segmentVO = (SegmentVO) changeHistoryVOs[c].getParentVO(SegmentVO.class);
            optionId = segmentVO.getOptionCodeCT();
            companyStructureId = segmentVO.getProductStructureFK();
            optionIdValue = codeTableWrapper.getCodeDescByCodeTableNameAndCode("OPTIONCODE", optionId, companyStructureId);
        }
    }

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<%!
    private TreeMap sortHistoryByEffectiveDate(EDITTrxVO[] editTrxVOs,
                                                ChangeHistoryVO[] changeHistoryVOs)
    {
		TreeMap sortedHistories = new TreeMap();

        if (editTrxVOs != null)
        {
            for (int e = 0; e < editTrxVOs.length; e++)
            {
                sortedHistories.put(editTrxVOs[e].getEffectiveDate() +
                                     editTrxVOs[e].getEDITTrxPK() + "fh", editTrxVOs[e]);
            }
        }

        if (changeHistoryVOs != null)
        {
            for (int c = 0; c < changeHistoryVOs.length; c++)
            {
                sortedHistories.put(changeHistoryVOs[c].getEffectiveDate() +
                                     changeHistoryVOs[c].getChangeHistoryPK() + "ch", changeHistoryVOs[c]);
            }
        }

		return sortedHistories;
	}
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;
    var shouldShowLockAlert = true;

	function init()
    {
		f = document.newBusChangeistoryForm;
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
	}

    function showLockAlert()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Contract can not be edited.");

            return false;
        }
    }

	function selectRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.historyKey.value = key;

		sendTransactionAction("QuoteDetailTran", "showHistoryDetailSummary", "contentIFrame");
	}
</script>
<head>
<title>New Business Change History Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "newBusChangeistoryForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="quoteInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:48%; top:0; left:0; z-index:0; overflow:visible">
  <table width="94%" border="0" cellspacing="8" cellpadding="0" height="48%">
    <tr>
      <td nowrap width="39%" height="5%">Effective Date:&nbsp;
        <input disabled type="text" name="effectiveDate" size="10" maxlength="10" value="<%= effectiveDate%>">
      </td>
      <td width="18%" nowrap height="5%">Process Date:&nbsp;
        <input disabled type="text" name="processDate" size="10" maxlength="10" value="<%= processDate%>">
      </td>
      <td width="43%" nowrap height="5%">Transaction Type:&nbsp;
        <input disabled type="text" name="transactionType" value="<%= transactionType%>">
      </td>
    </tr>
    <tr>
      <td width="39%" nowrap align="left" height="10%">Segment:&nbsp;
        <input disabled type="text" name="coverageRiderType" value="<%= optionIdValue%>">
      </td>
      <td nowrap height="35%" width="10%" align="left" colspan="2">Id:&nbsp;
        <input disabled type="text" name="idName" size="20" value="<%= idName%>">
      </td>
      </tr>
    <tr>
      <td nowrap>Field Name:&nbsp;
        <input disabled type="text" name="fieldName" size="20" value="<%= fieldName%>">
      </td>
      <td nowrap>Before Change Value:&nbsp;
        <input disabled type="text" name="beforeValue" size="20" value="<%= beforeValue%>">
      </td>
      <td nowrap>After Change Value:&nbsp;
        <input disabled type="text" name="afterValue" size="20" value="<%= afterValue%>">
      </td>
    </tr>
    <tr>
  	  <%--<td nowrap align="left">Reversal:&nbsp;
 	    <input disabled type="text" size="1" maxlength="1" name="reversal">
	  </td>--%>
      <td nowrap align="left">Operator:&nbsp;
        <input disabled type="text" name="operator" value="<%= operator%>">
      </td>
      <td nowrap align="left">Date/Time:&nbsp;
        <input disabled type="text" name="maintDate" value="<%= dateTime%>">
      </td>
    </tr>
  </table>
  <table class="summary" id="summaryTable" width="100%" height="50%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
	  <th align="left" width="11%">Acct Pend</th>
      <th align="left" width="11%">Eff Date</th>
      <th align="left" width="11%">Proc Date</th>
      <th align="left" width="11%">Tran Type</th>
	  <th align="left" width="11%">Seq</th>
	  <th align="left" width="11%">Status</th>
	  <th align="left" width="11%">Gross Amt</th>
	  <th align="left" width="11%">Check Amt</th>
	  <th align="left" width="11%">Coverage</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:50%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="quoteChangeHistSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
	  <%
            String rowToMatch = "";
            String trClass = "";
            boolean selected = false;

            Map sortedHistories = sortHistoryByEffectiveDate(editTrxVOs, changeHistoryVOs);

            Iterator it = sortedHistories.values().iterator();
            EDITTrxVO editTrxVO = null;
            ChangeHistoryVO changeHistoryVO = null;

            while (it.hasNext())
            {
                String accountPendingInd = "";
                String hEffectiveDate = "";
                String hProcessDate = "";
                String hTransactionType = "";
                String hSequence = "";
                String hStatus = "";
                String hGrossAmount = "";
                String hCheckAmount = "";
                String hOptionIdValue = "";
                String key = "";

                Object obj = it.next();
                if (obj instanceof EDITTrxVO)
                {
                    editTrxVO = (EDITTrxVO) obj;
                    changeHistoryVO = null;
                }
                else
                {
                    editTrxVO = null;
                    changeHistoryVO = (ChangeHistoryVO) obj;
                }

                if (editTrxVO != null)
                {
                    ClientSetupVO clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
                    ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
                    GroupSetupVO groupSetupVO = (GroupSetupVO) contractSetupVO.getParentVO(GroupSetupVO.class);

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

                            SegmentVO segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);
                            hOptionIdValue = segmentVO.getOptionCodeCT();

                            rowToMatch = key;
                            if (rowToMatch.equals(rowToMatchBase))
                            {
                                trClass = "highlighted";
                                selected = true;
                            }
                            else {

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
                else if (changeHistoryVO != null)
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
            } // end while (iterator)
      %>
    </table>
  </span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="historyKey" value="">

</form>
</body>
</html>
