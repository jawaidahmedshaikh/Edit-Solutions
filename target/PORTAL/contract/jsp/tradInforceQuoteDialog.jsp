<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 contract.dm.dao.DAOFactory,
                 fission.utility.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.*" %>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");
    String editingExceptionExists = "false";
    if (editingException != null){

        editingExceptionExists = "true";
    }

    VOEditException voEditException = (VOEditException) session.getAttribute("VOEditException");
    String voEditExceptionExists = "false";
    if (voEditException != null){

        voEditExceptionExists = "true";
    }


    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    String productStr = contractMainSessionBean.getValue("companyStructureId"); //companyStructureId is product structure here

    CodeTableVO[] quoteTypeCTs = codeTableWrapper.getCodeTableEntries("QUOTETYPE",new Long(productStr).longValue());

    String inforceQuoteMessage = (String) request.getAttribute("inforceQuoteMessage");
    inforceQuoteMessage = Util.initString(inforceQuoteMessage, "");

    String errorMessage = (String) request.getAttribute("errorMessage");
    errorMessage = Util.initString(errorMessage, "");

    String analyzeTrx = (String) request.getAttribute("analyzeInforceQuote");

    QuoteVO quoteVO = (QuoteVO) session.getAttribute("quoteVO");

    String contractNumber = contractMainSessionBean.getValue("contractId");

    String quoteDate = Util.initString((String) request.getAttribute("quoteDate"), "");
    String ownerName = "";
    String insuredName = "";
    String premiumRefund = "";
    String surrenderValue = "";
    String cashSurrenderValue = "";
    String totalLTCBenefitPayments = "";
    String adjustedSurrenderValue = "";
    String loanPayoff = "";
    String tiLienBalance = "";
    String checkAmount = "";
    String effectiveDateAsMMDDYYYY = "";
    String freeLookEndDateAsMMDDYYYY = "";

    String quoteTypeCT = (String) request.getAttribute("quoteType");
    
    if (quoteVO != null && quoteVO.getSegmentVOCount() > 0)
    {
        quoteDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(quoteVO.getQuoteDate());

        SegmentVO segmentVO = quoteVO.getSegmentVO(0);
        ClientDetailVO[] clientDetailVO = quoteVO.getClientDetailVO();
        ContractClientVO[] contractClientVO = segmentVO.getContractClientVO();
        for (int i = 0; i < contractClientVO.length; i++)
        {
            ClientRoleVO clientRoleVO = (ClientRoleVO) contractClientVO[i].getParentVO(ClientRoleVO.class);

            if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase("OWN"))
            {
                for (int j = 0; j < clientDetailVO.length; j++)
                {
                    if (clientDetailVO[j].getClientDetailPK() == clientRoleVO.getClientDetailFK())
                    {
                        if (clientDetailVO[j].getLastName() == null || clientDetailVO[j].getLastName().equals(""))
                        {
                            ownerName = clientDetailVO[j].getCorporateName();
                        }
                        else
                        {
                            ownerName = clientDetailVO[j].getLastName() + ", " +
                                        Util.initString(clientDetailVO[j].getFirstName(), "") + " " +
                                        Util.initString(clientDetailVO[j].getMiddleName(), "");
                        }

                        break;
                    }
                }
            }
            else if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase("Insured") ||
                     clientRoleVO.getRoleTypeCT().equalsIgnoreCase("ANN"))
            {
                for (int j = 0; j < clientDetailVO.length; j++)
                {
                    if (clientDetailVO[j].getClientDetailPK() == clientRoleVO.getClientDetailFK())
                    {
                        if (clientDetailVO[j].getLastName() == null || clientDetailVO[j].getLastName().equals(""))
                        {
                            insuredName = clientDetailVO[j].getCorporateName();
                        }
                        else
                        {
                            insuredName = clientDetailVO[j].getLastName() + ", " +
                                        Util.initString(clientDetailVO[j].getFirstName(), "") + " " +
                                        Util.initString(clientDetailVO[j].getMiddleName(), "");
                        }
                    }

                    break;
                }
            }
        }

        if (quoteVO.getPremiumRefund() != null)
        {
            premiumRefund = quoteVO.getPremiumRefund().toString();
        }

        if (quoteVO.getSurrenderValue() != null)
        {
            surrenderValue = quoteVO.getSurrenderValue().toString();
        }

        if (quoteVO.getCashSurrenderValue() != null)
        {
            cashSurrenderValue = quoteVO.getCashSurrenderValue().toString();
        }

        if (quoteVO.getLongTermCareLienBalance() != null)
        {
            totalLTCBenefitPayments = quoteVO.getLongTermCareLienBalance().toString();
        }

        if (quoteVO.getAdjustedSurrenderValue() != null)
        {
            adjustedSurrenderValue = quoteVO.getAdjustedSurrenderValue().toString();
        }

        if (quoteVO.getLoanPayoff() != null)
        {
            loanPayoff = quoteVO.getLoanPayoff().toString();
        }

        if (quoteVO.getTILienBalance() != null)
        {
            tiLienBalance = quoteVO.getTILienBalance().toString();
        }

        if (quoteVO.getCheckAmount() != null)
        {
            checkAmount = quoteVO.getCheckAmount().toString();
        }

        effectiveDateAsMMDDYYYY = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(quoteVO.getQuoteDate());
        freeLookEndDateAsMMDDYYYY = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentVO.getFreeLookEndDate());
    }
    
    String notTakenOverrideInd = (String) request.getAttribute("notTakenOverrideInd");
%>


<html>

<!-- ***** JAVASCRIPT *****  -->

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

    var f = null;

    var inforceQuoteMessage = "<%= inforceQuoteMessage %>";
    var errorMessage = "<%= errorMessage %>";
    var analyzeTrx = "<%= analyzeTrx %>";
    var editingExceptionExists = "<%= editingExceptionExists %>";
    var voEditExceptionExists = "<%= voEditExceptionExists %>";

    function init()
    {
        f = document.inforceQuoteForm;

        f.quoteDate.focus();

        if (errorMessage != "")
        {
            alert(errorMessage);
            window.close();
        }
        else if (inforceQuoteMessage != "")
        {
            alert(inforceQuoteMessage);
        }

        formatCurrency();

        if (analyzeTrx == "true") {

            showAnalyzer();
        }
    }

    function performInforceQuote()
    {
        if (f.quoteType.selectedIndex == 0)
        {
            alert("Please Select Quote Type");
            return;
        }

        editDate();
        sendTransactionAction("ContractDetailTran","performInforceQuote","_self");
    }

    function analyzeInforceQuote()
    {
        editDate();
        sendTransactionAction("ContractDetailTran", "analyzeInforceQuote", "_self");
    }

    function editDate()
    {
        if (f.quoteDate.value == "")
        {
            alert("Quote Date Not Entered");
        }
    }

    function submitTradInforceQuoteDialog()
    {
        if (f.quoteType.value != "FullSurrender" && f.quoteType.value != "NotTaken")
        {
            alert("Invalid Selection For Submit");
            return;
        }

        editDate();
        sendTransactionAction("ContractDetailTran", "createTransactionFromQuote", "_self");
    }

    function clearInforceQuoteDialog()
    {
        sendTransactionAction("ContractDetailTran","clearInforceQuoteDialog","_self");
    }

    function closeInforceQuoteDialog()
    {
        sendTransactionAction("ContractDetailTran","closeInforceQuoteDialog","contentIFrame");
        window.close();
    }

    function showAnalyzer()
    {
        var width = screen.width;
        var height = screen.height;

        openDialog("analyzeInforceQuote", "left=0,top=0,resizable=yes", width, height);

        sendTransactionAction("ContractDetailTran", "showAnalyzer","analyzeInforceQuote");
    }

    function showNotTakenOverrideDialog()
    {
        var width = screen.width * .20;
        var height = screen.height * .10;

        openDialog("notTakenOverrideDialog", "left=0,top=0,resizable=yes", width, height);

        sendTransactionAction("ContractDetailTran", "showNotTakenOverrideDialog", "notTakenOverrideDialog");
    }

    function checkForEditingException()
    {
        if (editingExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("exceptionDialog", "resizable=no", width, height);

            sendTransactionAction("ContractDetailTran", "showEditingExceptionDialog", "exceptionDialog");
        }
    }

    function checkForVOEditException()
    {
        if (voEditExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("voEditExceptionDialog", "resizable=no", width, height);

            sendTransactionAction("ContractDetailTran", "showVOEditExceptionDialog", "voEditExceptionDialog");
        }
    }

    function continueTransactionAction(transaction, action)
    {
        f.ignoreEditWarnings.value = "true";
        sendTransactionAction(transaction, action, "contentIFrame");
    }

</script>

<head>
<title>Full Termination Quote Traditional</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()" style="border-style:solid; border-width:1">

<form  name="inforceQuoteForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="span1" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="5%" border="0" cellspacing="0" cellpadding="4">
    <tr height="100%">
      <td align="right" nowrap>Contract Number:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="contractNumber" maxlength="15" size="15" value="<%= contractNumber %>">
      </td>
      <td align="right" nowrap>Quote Date:&nbsp;</td>
      <td align="left" nowrap>
           <input type="text" name="quoteDate" value="<%= quoteDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.quoteDate', f.quoteDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
       </td>
      <td align="right" nowrap>Quote Type:&nbsp;</td>
      <td align="left" nowrap>
        <select name="quoteType">
          <option value="null">Please Select</option>
          <%
              for (int i = 0; i < quoteTypeCTs.length; i++)
              {
                  String code = quoteTypeCTs[i].getCode();
                  String codeDesc = quoteTypeCTs[i].getCodeDesc();

                  if (code.equals(quoteTypeCT))
                  {
                    out.println("<option selected name=\"id\" value=\""  + code + "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
          %>
        </select>
      </td>
    </tr>
    <tr height="100%">
      <td align="right" nowrap>Owner Name:&nbsp;</td>
      <td align="left" colspan="5" nowrap>
        <input disabled type="text" name="ownerName" maxlength="63" size="63" value="<%= ownerName %>">
      </td>
    </tr>
    <tr height="100%">
      <td align="right" nowrap>Insured Name:&nbsp;</td>
      <td align="left" nowrap colspan="5">
        <input disabled type="text" name="insuredName" maxlength="63" size="63" value="<%= insuredName %>">
      </td>
    </tr>
	<tr>
      <td align="right" nowrap>Free Look End Date:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="freeLookEndDate" maxlength="10" size="10" value="<%= freeLookEndDateAsMMDDYYYY %>">
      </td>
	</tr>
    <tr>
      <td align="right" nowrap colspan="6">
        <input type="button" name="enter" value="Enter" onClick="performInforceQuote()">
        <input type="button" name="clear" value="Clear" onClick="clearInforceQuoteDialog()">
        <input type="button" name="analyze" value=" Analyze " onClick="analyzeInforceQuote()">
        </td>
    </tr>
  </table>
  <hr align="left" width="100%" noshade>
  <table width="100%" height="33%" border="0" cellspacing="0" cellpadding="4">
    <tr>
    <td width="50%" align="left">
    <table width="100%" border="0" cellspacing="0" cellpadding="4">
	<tr>
      <td align="right" nowrap>Effective Date:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="effectiveDate" maxlength="10" size="10" value="<%= effectiveDateAsMMDDYYYY %>">
      </td>
	</tr>
	<tr>
      <td align="right" nowrap>Premium Refund:&nbsp;</td>
	  <td align="left" nowrap>
	    <input disabled type="text" name="premiumRefund" size="15" value="<%= premiumRefund %>" CURRENCY>
	  </td>
    </tr>
    <tr>
      <td align="right" nowrap>Surrender Value:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="surrenderValue" size="15" value="<%= surrenderValue %>" CURRENCY>
      </td>
	</tr>
    <tr>
      <td align="right" nowrap>Total LTC Benefit Payments:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="totalLTCBenefitPayments" size="15" value="<%= totalLTCBenefitPayments %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>AdjustedSurrender Value:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="adjustedSurrenderValue" size="15" value="<%= adjustedSurrenderValue %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Loan Payoff:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="loanPayoff" size="15" value="<%= loanPayoff %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>TI Lien Balance:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="tiLienBalance" size="15" value="<%= tiLienBalance %>" CURRENCY>
      </td>
    <tr>
      <td align="right" nowrap>Cash Surrender Value:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="cashSurrenderValue" size="15" value="<%= cashSurrenderValue %>" CURRENCY>
      </td>
	</tr>
    <tr>
      <td align="right" nowrap>Check Amount:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="checkAmount" size="15" value="<%= checkAmount %>" CURRENCY>
      </td>
	</tr>
    </tr>
    </table>
    </td>
    <td width="50%" align="right">
    <table width="100%" height="60%" border="0" cellspacing="0" cellpadding="4">
    <tr>
      <td align="left" nowrap rowspan="3">Adjustments:&nbsp;
        <table class="summary" id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class="heading">
            <th width="50%" align="left">Adjustment Type</th>
            <th width="50%" align="left">Amount</th>
          </tr>
        </table>
        <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; background-color:#BBBBBB">
          <table class="summary" id="quoteChargesSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
              <%
                String cClassName = "default";
                String cSelected = "false";

                if (quoteVO != null)
                {
                    QuoteAdjustmentVO[] quoteAdjustmentVOs = quoteVO.getQuoteAdjustmentVO();
                    
                    for (int i = 0; i < quoteAdjustmentVOs.length; i++)
                    {
                        QuoteAdjustmentVO quoteAdjustmentVO = quoteAdjustmentVOs[i];
                        
                        String cChargeType = quoteAdjustmentVO.getChargeTypeCT();
                        String cChargeAmount = Util.roundDollars(quoteAdjustmentVO.getChargeAmount()).toString();
              %>
                      <tr class="<%= cClassName %>" isSelected="<%= cSelected %>"
                          onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
                        <td width="50%" nowrap>
                          <%= cChargeType %>
                        </td>
                        <td width="50%" nowrap>
                          <script>document.write(formatAsCurrency(<%= cChargeAmount %>))</script>
                        </td>
                      </tr>
              <%
                    }
                }
              %>          
          </table>
        </span>
      </td>
      </tr>
      </table>
    </td>
    </tr>
  </table>
  <table width="100%" height="2%">
    <tr>
      <td align="right">
        <a href="javascript:showNotTakenOverrideDialog()">Not Taken Override</a>
        <input type="button" name="Ssubmit" value="Submit" onClick="submitTradInforceQuoteDialog()">
        <input type="button" name="close" value="Close" onClick="closeInforceQuoteDialog()">
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

<input type="hidden" name="notTakenOverrideInd" value="<%= notTakenOverrideInd %>">

</form>
</body>
</html>
