<!--
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 -->
<%@ taglib uri="/WEB-INF/SecurityTaglib.tld" prefix="security" %>

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 event.*,
                 fission.utility.*" %>

<%
    SessionBean quoteMainSessionBean = (SessionBean) session.getAttribute("quoteMainSessionBean");
    EDITTrx selectedTransaction = (EDITTrx) request.getAttribute("selectedTransaction");

    String message = Util.initString((String) request.getAttribute("message"), "");
    String responseMessage = Util.initString((String) request.getAttribute("responseMessage"), "");

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

	PageBean formBean = quoteMainSessionBean.getPageBean("formBean");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    String companyStructureKey = Util.initString(formBean.getValue("companyStructureId"), "0");
    String companyStructure = Util.initString(quoteMainSessionBean.getValue("companyStructure"), "");

    String editTrxPK        = formBean.getValue("editTrxPK");
    String segmentFK        = formBean.getValue("segmentFK");
	String contractId		= formBean.getValue("contractNumber");
	String optionId         = "";
    String transactionType	= "";
    String effectiveDate    = "";
    String sequenceNumber	= "";
    String amount			= "";
    String percent          = "";
    String taxYear			= "";
    String statusInd        = "";
    String pendingStatus    = "";
    String operator         = "";
    String maintDate        = "";
    String transactionId    = "";

    if (selectedTransaction != null)
    {
        transactionType = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", selectedTransaction.getTransactionTypeCT(), Long.parseLong(companyStructureKey));

        effectiveDate	= DateTimeUtil.formatYYYYMMDDToMMDDYYYY(selectedTransaction.getEffectiveDate().getFormattedDate());

        sequenceNumber = selectedTransaction.getSequenceNumber() + "";
        amount = selectedTransaction.getTrxAmount() + "";
        percent = selectedTransaction.getTrxPercent() + "";

        taxYear = selectedTransaction.getTaxYear() + "";
        statusInd = selectedTransaction.getStatus();
        pendingStatus = selectedTransaction.getPendingStatus();
        optionId = formBean.getValue("optionId");
 	    operator  = Util.initString(selectedTransaction.getOperator(), "");
	    maintDate = selectedTransaction.getMaintDateTime().getFormattedDateTime();
 	    transactionId = formBean.getValue("transactionId");
    }
    else
    {
        effectiveDate = Util.initString((String)request.getAttribute("effectiveDate"), "");

        transactionType = "Submit";
        sequenceNumber = Util.initString((String)request.getAttribute("sequenceNumber"), "");
        amount = Util.initString((String)request.getAttribute("amount"), "");
        percent = Util.initString((String)request.getAttribute("percent"), "");

        taxYear = Util.initString((String)request.getAttribute("taxYear"), "");
        statusInd = Util.initString((String)request.getAttribute("status"), "");
        pendingStatus = Util.initString((String)request.getAttribute("pendingStatus"), "");
        optionId = Util.initString((String)request.getAttribute("optionId"), "");
        operator = Util.initString((String)request.getAttribute("operator"), "");
        maintDate = Util.initString((String)request.getAttribute("dateTime"), "");
    }

	CodeTableVO[] options       = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureKey));
    CodeTableVO[] yesNo = codeTableWrapper.getCodeTableEntries("YESNO");

//    if (selectedTransaction == null)
//    {
//        transactionType = Util.initString(formBean.getValue("defaultTrxCode"), "Submit");
//    }
    String analyzeTrx = (String) request.getAttribute("analyzeTransaction");
	String rowToMatchBase = formBean.getValue("transactionId");


%>


<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

    var message = "<%= message %>";

    var editingExceptionExists = "<%= editingExceptionExists %>";
    var voEditExceptionExists = "<%= voEditExceptionExists %>";
    var analyzeTrx = "<%= analyzeTrx %>";
    var height = screen.height;
    var width  = screen.width;
    var transactionType = "<%= transactionType %>";
    var responseMessage = "<%= responseMessage %>";

	var f = null;


	function init()
    {
<%--        var previousRow = document.getElementById("<%= transactionId %>");--%>
<%----%>
<%--        if (previousRow != null) {--%>
<%----%>
<%--            previousRow.scrollIntoView(false);--%>
<%--        }--%>
		f = document.trxWithdrawalForm;

        if (analyzeTrx == "true")
        {
            showAnalyzer();
        }
        if (responseMessage != "")
        {
            alert(responseMessage);
        }

        if (message != "")
        {
            alert(message);
        }

        // Initialize scroll tables
        initScrollTable(document.getElementById("QuoteTransactionTableModelScrollTable"))



        formatCurrency();
	}

	function onTableRowSingleClick(tableId)
    {

		sendTransactionAction("QuoteDetailTran", "showTransactionDetail", "_self");
	}

<%--	function selectRow()--%>
<%--    {--%>
<%--		var tdElement = window.event.srcElement;--%>
<%--		var trElement = tdElement.parentElement;--%>
<%----%>
<%--		var key = trElement.id;--%>
<%----%>
<%--		f.key.value = key;--%>
<%----%>
<%--		prepareToSendTransactionAction("QuoteDetailTran", "showTransactionDetail", "_self");--%>
<%--	}--%>

	function saveWithdrawalTransaction()
    {
        try
        {
            if (transactionType == "")
            {
                alert("Transaction Must Be Selected/Entered For Save");
            }
            else
            {
                disableActionButtons();

                sendTransactionAction("QuoteDetailTran", "saveWithdrawalTransaction", "_self");
            }
        }
        catch (e)
        {
            alert(e);
        }
	}

	function showAnalyzer()
    {
        var width = screen.width;
        var height = screen.height;

		openDialog("analyzeTransaction", "left=0,top=0,resizable=yes", width, height);

        sendTransactionAction("QuoteDetailTran", "showAnalyzer","analyzeTransaction");
	}

	function cancelWithdrawalTransaction()
    {
		sendTransactionAction("QuoteDetailTran", "cancelWithdrawalTransaction", "_self");
	}

	function deleteWithdrawalTransaction()
    {
		sendTransactionAction("QuoteDetailTran", "deleteWithdrawalTransaction", "_self");
	}

	function addWithdrawalTransaction()
    {
        sendTransactionAction("QuoteDetailTran", "addWithdrawalTransaction", "_self");
	}

	function analyzeTransaction()
    {
        sendTransactionAction("QuoteDetailTran", "analyzeTransaction", "_self");
	}


    function checkForEditingException()
    {
        if (editingExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("exceptionDialog", "resizable=no", width, height);

            sendTransactionAction("QuoteDetailTran", "showEditingExceptionDialog", "exceptionDialog");
        }
    }

    function checkForVOEditException()
    {
        if (voEditExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("voEditExceptionDialog", "resizable=no", width, height);

            sendTransactionAction("QutoeDetailTran", "showVOEditExceptionDialog", "voEditExceptionDialog");
        }
    }

    function continueTransactionAction(transaction, action)
    {
        f.ignoreEditWarnings.value = "true";

        disableActionButtons();

        sendTransactionAction(transaction, action, "_self");
    }

    function closeTrxDialog()
    {
        closeWindow();
    }

    /**
     * While a tranaction is being saved, we want to disable all buttons to prevent double-saves.
     */
    function disableActionButtons()
    {
        document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

        document.all.btnAdd.style.backgroundColor = "#99BBBB";
        document.all.btnSave.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";
        document.all.btnDelete.style.backgroundColor = "#99BBBB";
        document.all.btnAnalyze.style.backgroundColor = "#99BBBB";

        document.all.btnAdd.disabled = true;
        document.all.btnSave.disabled = true;
        document.all.btnCancel.disabled = true;
        document.all.btnDelete.disabled = true;
        document.all.btnAnalyze.disabled = true;
    }

</script>

<head>
<title>EDITSOLUTIONS - Transactions</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()">
 <form name="trxWithdrawalForm" method="post" action="/PORTAL/servlet/RequestManager">

  <table width="100%" height="40%" border="0" style="border-style:solid; border-width:1; border-color:Black; background-color:#BBBBBB">
    <tr>
      <td nowrap align="right">CompanyKey:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="companyStructure" size="20" maxlength="25" value="<%= companyStructure%>">
      </td>
      <td nowrap align="right">Contract Number:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="contractId" size="20" maxlength="25" value="<%= contractId%>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Transaction Type:&nbsp;</td>
      <td nowrap align="left">
        <input disabled type="text" name="transactionType" size="15" maxlength="15" value="<%= transactionType%>">
      </td>
      <td nowrap align="right">Sequence:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="sequenceNumber" size="4" maxlength="4" value="<%= sequenceNumber%>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Effective Date:&nbsp;</td>
      <td nowrap align="left">
           <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td nowrap align="right">Tax Year:&nbsp</td>
      <td nowrap align="left">
        <input type="text" name="taxYear" size="4" maxlength="4" value="<%= taxYear %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Amount:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="amount" size="12" maxlength="12" value="<%= amount%>" CURRENCY>
      </td>
      <td nowrap align="right">Percent:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="percent" size="12" maxlength="12" value="<%= percent %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Status:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="status" size="15" maxlength="15" value="<%= statusInd%>">
      </td>
      <td nowrap align="right">Pending Status:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="pendingStatus" size="15" maxlength="15" value="<%= pendingStatus%>">
      </td>
    </tr>

    <tr>
      <td nowrap align="right">Coverage/Rider Type:&nbsp;</td>
      <td nowrap align="left">
      <select name="optionId">
          <%
            out.println("<option>Please Select</option>");

            for(int i = 0; i < options.length; i++) {

                String codeTablePK = options[i].getCodeTablePK() + "";
                String codeDesc    = options[i].getCodeDesc();
                String code        = options[i].getCode();

                if (optionId.equalsIgnoreCase(code)) {

                    out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                }
                else  {

                    out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                }
            }
          %>
        </select>
      </td>
    </tr>

    <tr>
      <td nowrap align="right">Operator:&nbsp;</td>
      <td nowrap align="left">
         <input disabled type="text" name="operator" size="15" maxlength="15" value="<%= operator%>">
      </td>
      <td nowrap align="right">Date/Time:&nbsp;</td>
      <td nowrap align="left">
         <input disabled type="text" name="dateTime"  size="30" maxlength="35" value="<%= maintDate %>">
      </td>
    </tr>
  </table>

  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0" style="border-style:solid; border-width:1; border-color:Black; background-color:#BBBBBB">
    <tr>
      <td nowrap align="left">
  		<input id="btnAdd" type="button" value="   Add   " onClick="addWithdrawalTransaction()">
  		<input id="btnSave" type="button" value="   Save  " onClick="saveWithdrawalTransaction()">
  		<input id="btnCancel" type="button" value="  Cancel " onClick="cancelWithdrawalTransaction()">
  		<input id="btnDelete" type="button" value="  Delete " onClick="deleteWithdrawalTransaction()">
        <input id="btnAnalyze" type="button" value=" Analyze " onClick="analyzeTransaction()">
  	  </td>
      <td id="trxMessage">
        &nbsp;
      </td>
    </tr>
  </table>

    <%-- ****************************** BEGIN Summary Area ****************************** --%>

    <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
        <jsp:param name="tableId" value="QuoteTransactionTableModel"/>
        <jsp:param name="tableHeight" value="40"/>
        <jsp:param name="multipleRowSelect" value="false"/>
        <jsp:param name="singleOrDoubleClick" value="single"/>
    </jsp:include>

    <%-- ****************************** END Summary Area ****************************** --%>


  <table id="closeTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	  <td colspan="8" nowrap align="right">
	  	<input type="button" value="   Close  " onClick="closeTrxDialog()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"   value="">
 <input type="hidden" name="action"        value="">
 <input type="hidden" name="key"           value="">
 <input type="hidden" name="ignoreEditWarnings" value="">
 <input type="hidden" name="contractId" value="<%= contractId %>">
 <input type="hidden" name="editTrxPK" value="<%= editTrxPK %>">
 <input type="hidden" name="noScrolling" value="">
 <input type="hidden" name="transactionId" value="<%= transactionId %>">
 <input type="hidden" name="trxEffDate" value="<%= effectiveDate %>">
 <input type="hidden" name="transactionType" value="<%= transactionType %>">
 <input type="hidden" name="optionId" value="<%= optionId %>">
 <input type="hidden" name="operator" value="<%= operator %>">
 <input type="hidden" name="dateTime" value="<%= maintDate %>">

</form>
</body>
</html>
