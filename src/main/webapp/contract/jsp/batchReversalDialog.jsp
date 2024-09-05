<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
				 contract.HistoryFilterRow,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 java.text.DecimalFormat,
                 fission.utility.Util,
                 java.math.BigDecimal" %>

<%
    SessionBean contractMainSessionBean = (SessionBean) session.getAttribute("contractMainSessionBean");
	SessionBean quoteMainSessionBean = (SessionBean) session.getAttribute("quoteMainSessionBean");    
	SessionBean contractHistories = (SessionBean) session.getAttribute("contractHistories");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");
	if (companyStructureId == null || companyStructureId.equals("")) {
		companyStructureId = contractMainSessionBean.getValue("companyStructureId");
	}
    
	String contractNumber = contractMainSessionBean.getValue("contractId");
	String segmentStatus = contractMainSessionBean.getValue("statusCode");
	if (segmentStatus == null || segmentStatus.equals("")) {
		segmentStatus = quoteMainSessionBean.getValue("status");
	}
	
    CodeTableVO[] reversalReasonCodes = codeTableWrapper.getCodeTableEntries("REVERSALREASONCODE", Long.parseLong(companyStructureId));
    String reversalReasonCode = "";
  	String reverseDate = "";

 	String errorMessage = (String) request.getAttribute("errorMessage");
    errorMessage = Util.initString(errorMessage, "");

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
    var lockAlertStatus = false;
    var height = screen.height;
    var width  = screen.width;
    var errorMessage = "<%= errorMessage %>";
    var segmentStatus = "<%= segmentStatus %>";

	function init()
    {
		f = document.reversalTrxForm;

		if (errorMessage != "") {
            alert(errorMessage);
        }
        
        if (lockAlertStatus == "true") {
            shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;
        }

        if (segmentStatus == "SubmitPend") {
		    document.getElementById("btnReverseToSubmitPend").disabled = true;
        }

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if (shouldShowLockAlert == true)
            {
                if (f.elements[i].NOLOCK  == null)
                {
                    f.elements[i].onclick = showLockAlert;
                    f.elements[i].onkeydown = showLockAlert;
                }
                f.elements[i].onchange = showLockAlert;
            }
        }

        formatCurrency();

        // Initialize scroll tables
        initScrollTable(document.getElementById("BatchReversalTableModelScrollTable"));
	}

	function onTableRowSingleClick(tableId)
    {
	    //var selectedRowIds = getSelectedRowIds("BatchReversalTableModel");
	}

	function runReversals()
    {
	    var selectedRowIds = getSelectedRowIds("BatchReversalTableModel");

	    if (selectedRowIds == null || selectedRowIds == "") {
		    alert("You Have Not Selected Any Transactions to Reverse");

		} else {
			prepareToSendTransactionAction("ContractDetailTran", "reverseSelectedTrx", "_self");
		}
	}

	function reverseThroughDate()
	{
		if (f.reverseDate.value == null || f.reverseDate.value == "")
        {
            alert("Reverse Date Must Be Entered to Continue");
        }
        else
        {
			prepareToSendTransactionAction("ContractDetailTran", "reverseThroughDate", "_self");
        }
	}
	
	function reverseToSubmitted()
	{
		prepareToSendTransactionAction("ContractDetailTran", "reverseToSubmitted", "_self");
	}

	function reverseToSubmitPend()
	{
		prepareToSendTransactionAction("ContractDetailTran", "reverseToSubmitPend", "_self");
	}

	function cancelReversals()
	{
		window.close();
	}
	
	function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
    }
	
</script>
<head>
<title>Batch Reversal Transactions</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "reversalTrxForm" method="post" action="/PORTAL/servlet/RequestManager">
<input type="hidden" name="page" value="">
  
  </br>
  
  <table>
    <tr>
      <td> Contract Number: </td>
      <td> <%= contractNumber %> </td>
    </tr>
    <tr>
      <td> Contract Status: </td>
      <td> <%= segmentStatus %> </td>
    </tr>
    <tr>
	    <td nowrap align="left">Reversal Reason Code:</td>
	    <td>
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
    </tr>
  </table>
  
  <br/>
    
  <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="BatchReversalTableModel"/>
    <jsp:param name="tableHeight" value="55"/>
    <jsp:param name="multipleRowSelect" value="true"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
  </jsp:include>

  </br>

  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
  	  <td nowrap align="left">
  		<input type="button" value="Reverse Selected Trx" title="Reverse All Trx Highlighted in the Table" 
  			style="background-color:#DEDEDE; cursor: pointer; width: 250px;" onClick="runReversals()">
  	  </td>
  	  <td nowrap align="right">
  		   Reverse Date:&nbsp;
           <input type="text" name="reverseDate" value="<%= reverseDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.reverseDate', f.reverseDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
		   &nbsp;
		   <input type="button" value="Reverse Through Date" title="Reverse All Trx Up To And Including Selected Date" 
		   	style="background-color:#DEDEDE; cursor: pointer; width: 250px;" onClick="reverseThroughDate()">			
	  </td>
      
	</tr>
	
	<%-- <tr><td></br></td></tr>
	
	<tr>
	  <td></td>
  	  <td nowrap align="right">
  		<input type="button" value="Reverse to Submitted" title="Reverse All Trx Up to And Including IS" 
  			style="background-color:#DEDEDE; cursor: pointer; width: 250px;" onClick="reverseToSubmitted()">
	  </td>
	</tr> --%>
	
	<tr><td></br></td></tr>
	
	<tr>
	  <td></td>
  	  <td nowrap align="right">
  		<input type="button" id="btnReverseToSubmitPend" value="Reverse to SubmitPend" title="Reverse All Trx Up to And Including SB" 
  			style="background-color:#DEDEDE; cursor: pointer; width: 250px;" onClick="reverseToSubmitPend()">
	  </td>
	</tr>
	
	<tr><td></br></td></tr>
	
	<tr>
	  <td></td>
	  <td nowrap align="right">
  		<input type="button" value="Cancel" style="background-color:#DEDEDE; cursor: pointer; width: 150px;" onClick="cancelReversals()">
	  </td>
	</tr>
	
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action" value="">
 <input type="hidden" name="key" value="">

</form>
</body>
</html>