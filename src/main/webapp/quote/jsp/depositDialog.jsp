<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 fission.utility.*,
                 edit.portal.common.session.UserSession,
                 event.Suspense,
                 fission.utility.*" %>
<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    PageBean formBean = quoteMainSessionBean.getPageBean("formBean");
    String companyStructureId = Util.initString(formBean.getValue("companyStructureId"), "0");

    String depositMessage = (String) request.getAttribute("depositMessage");
    if (depositMessage == null)
    {
        depositMessage = "";
    }

    String selectedSuspensePK = Util.initString((String) request.getAttribute("selectedSuspensePK"), "");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    CodeTableVO[] depositTypes = codeTableWrapper.getCodeTableEntries("DEPOSITTYPE", Long.parseLong(companyStructureId));

    DepositsVO[] depositsVOs = (DepositsVO[]) session.getAttribute("depositsVOs");

    String depositType       = "";
    //  OldCompany data displays in a field labeled ExchangeCompamy
    String oldCompany        = "";
    //  OldPolicyNumber displays in a field labeled ExchangePolicy
    String oldPolicyNumber   = "";
    String anticipatedAmount = "";
    String costBasis         = "";
    String dateReceivedDate  = "";
    String amountReceived    = "";
    String taxYear           = "";
    String suspenseFK        = "";
    String selectedDepositsPK = (String) request.getAttribute("selectedDepositsPK");
    String exchangePolicyEffectiveDate  = "";
    String exchangeIssueAge             = "";
    String exchangeDuration             = "";
    String preTEFRAGain = "";
    String preTEFRAAmount = "";
    String postTEFRAGain = "";
    String postTEFRAAmount = "";
    String exchangeLoanAmount = "";

    if (selectedDepositsPK == null) {

        selectedDepositsPK = "";
    }
    String cashBatchContractFK = "0";
    DepositsVO currentDepositVO = (DepositsVO) session.getAttribute("currentDepositVO");
    if (currentDepositVO  != null)
    {
        if (currentDepositVO.getDepositsPK() > 0)
        {
            selectedDepositsPK = currentDepositVO.getDepositsPK() + "";
            cashBatchContractFK = currentDepositVO.getCashBatchContractFK() + "";
        }
    }

    if (depositsVOs != null)
    {
        for (int e = 0; e < depositsVOs.length; e++)
        {
            if ((depositsVOs[e].getDepositsPK() + "").equals(selectedDepositsPK))
            {
                depositType = depositsVOs[e].getDepositTypeCT();
                oldCompany = Util.initString(depositsVOs[e].getOldCompany(), "");
                oldPolicyNumber = Util.initString(depositsVOs[e].getOldPolicyNumber(), "");
                anticipatedAmount = depositsVOs[e].getAnticipatedAmount().toString();
                costBasis = depositsVOs[e].getCostBasis().toString();

                dateReceivedDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(depositsVOs[e].getDateReceived());

                amountReceived = depositsVOs[e].getAmountReceived().toString();
                taxYear = depositsVOs[e].getTaxYear() + "";
                suspenseFK = depositsVOs[e].getSuspenseFK() + "";
                cashBatchContractFK = depositsVOs[e].getCashBatchContractFK() + "";

                exchangePolicyEffectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(depositsVOs[e].getExchangePolicyEffectiveDate());

                exchangeIssueAge             = depositsVOs[e].getExchangeIssueAge()+"";
                exchangeDuration             = depositsVOs[e].getExchangeDuration()+"";
                preTEFRAGain                 = new EDITBigDecimal(depositsVOs[e].getPreTEFRAGain()).toString();
                preTEFRAAmount               = new EDITBigDecimal(depositsVOs[e].getPreTEFRAAmount()).toString();
                postTEFRAGain                = new EDITBigDecimal(depositsVOs[e].getPostTEFRAGain()).toString();
                postTEFRAAmount              = new EDITBigDecimal(depositsVOs[e].getPostTEFRAAmount()).toString();
                exchangeLoanAmount           = new EDITBigDecimal(depositsVOs[e].getExchangeLoanAmount()).toString();
                break;
            }
        }
    }

    if (currentDepositVO != null)
    {
        depositType = currentDepositVO.getDepositTypeCT();
        oldCompany = Util.initString(currentDepositVO.getOldCompany(), "");
        oldPolicyNumber = Util.initString(currentDepositVO.getOldPolicyNumber(), "");

        exchangePolicyEffectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentDepositVO.getExchangePolicyEffectiveDate());
        
        exchangeIssueAge = currentDepositVO.getExchangeIssueAge()+"";
        exchangeDuration = currentDepositVO.getExchangeDuration()+"";         
        anticipatedAmount = currentDepositVO.getAnticipatedAmount().toString();
        costBasis = currentDepositVO.getCostBasis().toString();

        dateReceivedDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentDepositVO.getDateReceived());

        amountReceived = currentDepositVO.getAmountReceived().toString();
        taxYear = currentDepositVO.getTaxYear() + "";
        suspenseFK = currentDepositVO.getSuspenseFK() + "";
        cashBatchContractFK = currentDepositVO.getCashBatchContractFK() + "";

        preTEFRAGain = new EDITBigDecimal(currentDepositVO.getPreTEFRAGain()).toString();
        preTEFRAAmount = new EDITBigDecimal(currentDepositVO.getPreTEFRAAmount()).toString();
        postTEFRAGain = new EDITBigDecimal(currentDepositVO.getPostTEFRAGain()).toString();
        postTEFRAAmount = new EDITBigDecimal(currentDepositVO.getPostTEFRAAmount()).toString();
        exchangeLoanAmount = new EDITBigDecimal(currentDepositVO.getExchangeLoanAmount()).toString();
    }

	String rowToMatchBase     = selectedDepositsPK;

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>


<script language="Javascript1.2">

	var f = null;

    var shouldShowLockAlert = true;
    var editableContractStatus = true;
    
    var depositMessage = "<%= depositMessage %>";
    var suspenseFK = "<%= suspenseFK %>";
    var selectedDepositsPK = "<%= selectedDepositsPK %>";
    var depositAmtReceived = "<%= amountReceived %>";
    var selectedSuspensePK = "<%= selectedSuspensePK %>";

	function init()
    {
		f = document.depositForm;

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

        // check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;
        
        if (depositMessage != "")
        {
            alert(depositMessage);
        }

        for (var i = 0; i < f.elements.length; i++) {

            var elementType = f.elements[i].type;

            if (f.elements[i].name != "close") {

                if ((elementType == "text" ||
                    elementType == "button") &&
                    (shouldShowLockAlert == true || editableContractStatus == false)) {

                    f.elements[i].onclick = showLockAlert;
                    f.elements[i].onkeydown = showLockAlert;
                }
            }
        }

        formatCurrency();

        // Initialize scroll tables
        initScrollTable(document.getElementById("NewBusDepositSuspenseTableModelScrollTable"));
	}

    function showLockAlert()
    {
    	if (shouldShowLockAlert == true)
        {
            alert("The Contract Cannot Be Edited.");

            return false;
            
        } else if (editableContractStatus == false) {
        	
        	alert("This Contract Cannot Be Edited Due to Terminated Status.");

            return false;
        }
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("QuoteDetailTran", "selectSuspenseForAdjustment", "_self");
    }

    function addNewDeposit()
    {
        <%
        if (Util.isANumber(amountReceived) &&
            new EDITBigDecimal(amountReceived).isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
        {
        %>
            alert("Suspense Created - Please Save Deposit");
        <%
        }
        else
        {
        %>
            sendTransactionAction("QuoteDetailTran", "clearDepositFormForAddOrCancel", "_self");
        <%
        }
        %>
    }

	function cancelDepositInfo()
    {
        <%
        if (Util.isANumber(amountReceived) &&
            new EDITBigDecimal(amountReceived).isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
        {
        %>
            alert("Suspense Created - Please Save Deposit");
        <%
        }
        else
        {
        %>
            sendTransactionAction("QuoteDetailTran", "clearDepositFormForAddOrCancel", "_self");
        <%
        }
        %>
	}

	function closeDepositDialog()
    {
        <%
        if (Util.isANumber(amountReceived) &&
            new EDITBigDecimal(amountReceived).isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR) &&
            userSession.getSegmentIsLocked())
        {
        %>
            alert("Suspense Created - Please Save Deposit");
        <%
        }
        else
        {
        %>
            sendTransactionAction("QuoteDetailTran", "showQuoteMainContent", "contentIFrame");
            window.close();
        <%
        }
        %>
	}

	function saveDepositToSummary()
    {
        if (selectElementIsEmpty(f.depositType))
        {
            alert("Please Select Deposit Type");
        }
        else
        {
            sendTransactionAction("QuoteDetailTran", "saveDepositToSummary", "_self");
        }
	}

	function deleteSelectedDeposit()
    {
        if (selectedDepositsPK == "")
        {
            alert("Deposit Record Not Selected - Nothing to Delete");
        }
        else
        {
            var width = .30 * screen.width;
            var height = .15 * screen.height;

            openDialog("depositDeletionDialog", "resizable=no", width, height);

            sendTransactionAction("QuoteDetailTran", "showDepositDeletionMessage", "depositDeletionDialog");
        }
	}

	function selectDepositRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var depositsPK = trElement.id;
		f.selectedDepositsPK.value = depositsPK;

		sendTransactionAction("QuoteDetailTran", "showDepositDetailSummary", "_self");
	}

    function showTEFRAInformation()
    {
        var height  = .30 * screen.height;
        var width   = .40 * screen.width;

        openDialog("tefraInformation", "top=0,left=0,scrollbars=yes,resizable=no", width, height);

        sendTransactionAction("QuoteDetailTran", "showTEFRAInformation", "tefraInformation");
    }

	function adjustTaxes()
    {
        if (selectedSuspensePK == "")
        {
            alert("Please Select Suspense For Adjustment");
        }
        else
        {
            var width = .30 * screen.width;
            var height = .40 * screen.height;

            openDialog("taxAdjustmentDialog", "left=0,top=0,resizable=no", width, height);

            sendTransactionAction("QuoteDetailTran", "showTaxAdjustmentDialog", "taxAdjustmentDialog");
        }
	}

</script>

<head>
<title>Deposits</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "depositForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="10%" border="0" cellspacing="0" cellpadding="3">
    <tr valign="top">
	  <td align="left" nowrap colspan="3">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="2">
          <tr>
            <td align="left" nowrap>Type:&nbsp;
            <select name="depositType" tabindex="1">
              <option> Please Select </option>
              <%
                for (int i = 0; i < depositTypes.length; i++) {

                    String codeTablePK = depositTypes[i].getCodeTablePK() + "";
                    String codeDesc    = depositTypes[i].getCodeDesc();
                    String code        = depositTypes[i].getCode();

                    if (depositType.equalsIgnoreCase(code)) {

                        out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                    }
                    else  {

                        out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                    }
                }
              %>
            </select>
            <td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td width="34%">
        <span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="3">
        <tr>
          <td align="right" nowrap>Anticipated Amount:&nbsp;</td>
          <td align="left" nowrap>
            <input type="text" name="anticipatedAmount" tabindex="2" maxlength="11" size="11" value="<%= anticipatedAmount %>" CURRENCY>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>Amount Received:&nbsp;</td>
          <td align="left" nowrap>
            <input disabled type="text" name="amountReceived" maxlength="11" size="11" value="<%= amountReceived %>" CURRENCY>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>Cost Basis $:&nbsp;</td>
          <td align="left" nowrap>
            <input type="text" name="costBasis" tabindex="3" maxlength="11" size="11" value="<%= costBasis %>" CURRENCY>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>Date Received:&nbsp;</td>
          <td align="left" nowrap>
          <input type="text" name="dateReceivedDate" value="<%= dateReceivedDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.dateReceivedDate', f.dateReceivedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>Tax Year:&nbsp;</td>
          <td align="left" nowrap>
            <input type="text" name="taxYear" tabindex="4" maxlength="4" size="4" value="<%= taxYear %>">
          </td>
        </tr>
        </table>
        </span>
      </td>
      <td width="33%">
        <span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="3">
          <tr>
            <td align="right" nowrap>Exchange Company:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="oldCompany" tabindex="5" maxlength="30" size="30" value="<%= oldCompany %>">
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Exchange Policy Number:&nbsp;</td>
            <td align="left" nowrap colspan="3">
              <input type="text" name="oldPolicyNumber" tabindex="6" maxlength="15" size="15" value="<%= oldPolicyNumber %>">
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Exchange Policy Effective Date:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="exchangePolicyEffectiveDate" value="<%= exchangePolicyEffectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.exchangePolicyEffectiveDate', f.exchangePolicyEffectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Exchange Issue Age:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="exchangeIssueAge" tabindex="10" maxlength="15" size="15" value="<%= exchangeIssueAge %>">
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Exchange Duration:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="exchangeDuration" tabindex="11" maxlength="15" size="15" value="<%= exchangeDuration %>">
            </td>
          </tr>
        </table>
        </span>
      </td>
      <td>
        <span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="3">
          <tr>
            <td align="right" nowrap>
                Pre TEFRA Gain:&nbsp;
            </td>
            <td align="left" nowrap>
                <input type="text" name="preTEFRAGain" tabindex="12" size="15" value="<%= preTEFRAGain %>" CURRENCY>
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>
                Pre TEFRA Amount:&nbsp;
            </td>
            <td align="left" nowrap>
                <input type="text" name="preTEFRAAmount" tabindex="13" maxlength="11" size="15" value="<%= preTEFRAAmount %>" CURRENCY>
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>
                Post TEFRA Gain:&nbsp;
            </td>
            <td align="left" nowrap>
                <input type="text" name="postTEFRAGain" tabindex="14" size="15" value="<%= postTEFRAGain %>" CURRENCY>
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>
                Post TEFRA Amount:&nbsp;
            </td>
            <td align="left" nowrap>
                <input type="text" name="postTEFRAAmount" tabindex="15" maxlength="11" size="15" value="<%= postTEFRAAmount %>" CURRENCY>
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>
                Exchange Loan Amount:&nbsp;
            </td>
            <td align="left" nowrap>
                <input type="text" name="exchangeLoanAmount" tabindex="16" maxlength="11" size="15" value="<%= exchangeLoanAmount %>" CURRENCY>
            </td>
          </tr>
        </table>
        </span>
      </td>
    </tr>
  </table>
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td nowrap align="left">
        <input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNewDeposit()">
		<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveDepositToSummary()">
		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelDepositInfo()">
		<input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deleteSelectedDeposit()">
	  </td>

	</tr>
  </table>
  <table class="summary" id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th width="20%" align="left">Exchange Company</th>
      <th width="20%" align="left">Exchange Policy #</th>
      <th width="20%" align="left">Anticipated Amt</th>
      <th width="20%" align="left">Amt Received</th>
      <th width="20%" align="left">Dt Rcvd</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:15%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="depositSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
          String rowToMatch = "";
          String trClass = "default";
          boolean selected = false;

          if (depositsVOs != null)
          {
              for (int i = 0; i < depositsVOs.length; i++)
              {
                  if (!depositsVOs[i].getVoShouldBeDeleted())
                  {
                      String sOldCompany = Util.initString(depositsVOs[i].getOldCompany(), "");
                      String sOldPolicyNumber = Util.initString(depositsVOs[i].getOldPolicyNumber(), "");
                      String sAnticipatedAmount = depositsVOs[i].getAnticipatedAmount().toString();
                      String sAmountReceived = depositsVOs[i].getAmountReceived().toString();
                      String sDateReceived = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(depositsVOs[i].getDateReceived());

                      String sDepositsPK = depositsVOs[i].getDepositsPK() + "";

                      rowToMatch = sDepositsPK;
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
        <tr class="<%= trClass %>" isSelected="<%= selected %>" id="<%= sDepositsPK %>"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectDepositRow()">
          <td width="20%" align="left" nowrap>
            <%= sOldCompany %>
          </td>
          <td width="20%" align="left" nowrap>
            <%= sOldPolicyNumber %>
          </td>
          <td width="20%" align="left" nowrap>
            <script>document.write(formatAsCurrency(<%= sAnticipatedAmount %>))</script>
          </td>
          <td width="20%" align="left" nowrap>
            <script>document.write(formatAsCurrency(<%= sAmountReceived %>))</script>
          </td>
          <td width="20%" align="left" nowrap>
            <%= sDateReceived %>
          </td>
        </tr>
        <%
                  } // end if
              } // end for
          } // end if
        %>
    </table>
  </span>

  <br>
  <h3 align="center">- Suspense -</h3>
  <br>
<%-- ********** BEGIN Suspense Summary Area ********** --%>

<table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
  <tr>
	<td nowrap align="left">
  	  <input type="button" value="Adjustment" style="background-color:#DEDEDE" onClick="adjustTaxes()">
    </td>
  </tr>
</table>
<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
  <jsp:param name="tableId" value="NewBusDepositSuspenseTableModel"/>
  <jsp:param name="tableHeight" value="20"/>
  <jsp:param name="multipleRowSelect" value="false"/>
  <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ********** END Suspense Summary Area ********** --%>

  <table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0">
	<tr>
	  <td align="right" nowrap colspan="2">
		<input type="button" name="close" value="Close" onClick ="closeDepositDialog()">
	  </td>
	</tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="selectedDepositsPK" value="<%= selectedDepositsPK %>">
 <input type="hidden" name="cashBatchContractFK" value="<%= cashBatchContractFK %>">

 <input type="hidden" name="suspenseFK" value="<%= suspenseFK %>">
 <input type="hidden" name="dateReceivedDate" value="<%= dateReceivedDate %>">
 <input type="hidden" name="exchangePolicyEffectiveDate" value="<%= exchangePolicyEffectiveDate %>">
 <input type="hidden" name="amountReceived" value="<%= amountReceived %>">

</form>
</body>
</html>
