<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 fission.utility.Util" %>

<jsp:useBean id="contractHistories"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");

    CodeTableVO[] options  = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));

	PageBean formBean      = contractHistories.getPageBean("ch");

 	String effectiveDate  = formBean.getValue("effectiveDate");
	String afterValue      = formBean.getValue("afterValue");
	String beforeValue     = formBean.getValue("beforeValue");
	String changeHistoryId = formBean.getValue("changeHistoryId");

//	String clientRelationshipId = formBean.getValue("clientRelationshipId");
	String fieldName            = formBean.getValue("fieldName");
//	String fundRelationshipId 	= formBean.getValue("fundRelationshipId");
	String dateTime 			= formBean.getValue("maintDate");
	String operator  			= formBean.getValue("operator");
//	String changeHistoryRiderId = formBean.getValue("changeHistoryRiderId");
	String processDate			= formBean.getValue("processDate");
	String transactionType	 	= formBean.getValue("transactionType");
	String optionId 		 	= formBean.getValue("optionId");
    String idName               = formBean.getValue("idName");
    String reversalReasonCode   = formBean.getValue("reversalReasonCode");

    String optionIdValue        = "";
    for (int i = 0; i < options.length; i++) {

        if ((options[i].getCode()).equals(optionId)) {

            optionIdValue = options[i].getCodeDesc();
            i = options.length;
        }
    }

	String lockAlertStatus = Util.initString((String)request.getAttribute("shouldShowLockAlert"), "false");

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
    var transactionType = "<%= transactionType %>";
    var lockAlertStatus = "<%= lockAlertStatus %>";
    var operator = "<%= operator %>";

	function init()
    {
		f = document.contractHistoryForm;
		top.frames["main"].setActiveTab("historyTab");

        if (lockAlertStatus == "true")
        {
            shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;
        }

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true) )
            {
                if (f.elements[i].NOLOCK  == null)
                {
                    f.elements[i].onclick = showLockAlert;
                    f.elements[i].onkeydown = showLockAlert;
                }
            }
        }

        formatCurrency();

        // Initialize scroll tables
        initScrollTable(document.getElementById("ContractHistorySummaryTableModelScrollTable"));
    }

    function showLockAlert()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Contract can not be edited.");

            return false;
        }
    }

	function showContractHistoryFilter()
    {
        var width = .55 * screen.width;
        var height = .35 * screen.height;

		openDialog("contractHistoryFilterDialog","left=0,top=0,resizable=yes", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showContractHistoryFilterDialog", "contractHistoryFilterDialog");
	}

	function selectHistoryRow()
    {
       alert("select");
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.key.value = key;

		prepareToSendTransactionAction("ContractDetailTran", "showHistoryDetailSummary", "contentIFrame");
	}

    function onTableRowSingleClick(tableId)
    {
<%--        f.key.value = key;--%>

		prepareToSendTransactionAction("ContractDetailTran", "showHistoryDetailSummary", "contentIFrame");
	}

	function restoreRealTimeBackup()
    {
		prepareToSendTransactionAction("ContractDetailTran", "restoreContractFromBackup", "contentIFrame");
	}

	function runReversal()
    {
		if (f.reversal.value == "")
        {
			alert("Reversal Needs To Be Entered");
		}
		else if (operator == "Conversion")
        {
        	alert("Reversal of Conversion Transaction Not Allowed");	
        }
		else
        {
			prepareToSendTransactionAction("ContractDetailTran", "runReversal", "contentIFrame")
		}
	}

	function cancelReversal()
    {
		f.reversal.value = "";
	}

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
    }

</script>
<head>
<title>Change History Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "contractHistoryForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="contractInfoHeader.jsp" flush="true"/>

  <table width="94%" border="0" cellspacing="8" cellpadding="0" height="48%">	
    <tr>
      <td nowrap width="39%" height="5%">Effective Date:&nbsp;
           <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>

      <td width="18%" nowrap height="5%">Process Date:&nbsp;
        <input type="text" name="processDate" value="<%= processDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.processDate', f.processDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
       </td>

      <td width="43%" nowrap height="5%">Transaction Type:&nbsp;
        <input type="text" name="transactionType" value="<%= transactionType%>">
      </td>
    </tr>
    <tr>
      <td width="39%" nowrap align="left" height="10%">Segment:&nbsp;
        <input type="text" name="coverageRiderType" value="<%= optionIdValue%>">
      </td>
      <td nowrap height="35%" width="10%" align="left" colspan="2">Id:&nbsp;
        <input type="text" name="idName" size="20" value="<%= idName%>">
      </td>
      </tr>
    <tr>
      <td nowrap>Field Name:&nbsp;
        <input type="text" name="fieldName" size="20" value="<%= fieldName%>">
      <td nowrap>Before Change Value:&nbsp;
        <input type="text" name="beforeValue" size="20" value="<%= beforeValue%>">
      </td>
      <td nowrap>After Change Value:&nbsp;
        <input type="text" name="afterValue" size="20" value="<%= afterValue%>">
      </td>
    </tr>
   <tr>
  	   <%--<td nowrap align="left">Reversal:&nbsp;
 	    <input disabled type="text" size="1" maxlength="1" name="reversal">
	  </td>--%>
      <td nowrap align="left">Operator:&nbsp;
        <input type="text" name="operator" value="<%= operator%>">
      </td>
      <td nowrap align="left">Date/Time:&nbsp;
        <input type="text" name="maintDate" value="<%= dateTime%>">
      </td>
      <td nowrap align="left">Reversal Reason Code:&nbsp;
	     <input type="text" name="dateTime" disabled value="<%= reversalReasonCode %>">
	  </td>
    </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
  	  <td nowrap align="left">
  		<%--<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="runReversal()">
  		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelReversal()">--%>
		<input type="button" value="  Filter " style="background-color:#DEDEDE" onClick="showContractHistoryFilter()" NOLOCK>
	  </td>
	</tr>
  </table>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="ContractHistorySummaryTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>


<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="optionId" value="<%= optionId %>">
 <input type="hidden" name="key" value="">
 <input type="hidden" name="filterTransaction" value="<%= request.getAttribute("filterTransaction") %>">
 <input type="hidden" name="statusRestriction" value="<%= request.getAttribute("statusRestriction") %>">
 <input type="hidden" name="fromDate" value="<%= request.getAttribute("fromDate") %>">
 <input type="hidden" name="toDate" value="<%= request.getAttribute("toDate") %>">
 <input type="hidden" name="filterPeriod" value="<%= request.getAttribute("filterPeriod") %>">

 <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">
</form>
</body>
</html>
