<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
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
    SessionBean contractHistories = (SessionBean) session.getAttribute("contractHistories");
	PageBean formBean      = contractHistories.getPageBean("fh");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");
	if (companyStructureId.equals("")) {
		companyStructureId = contractMainSessionBean.getValue("companyStructureId");
	}
	
    CodeTableVO[] reversalReasonCodes = codeTableWrapper.getCodeTableEntries("REVERSALREASONCODE", Long.parseLong(companyStructureId));

 	String errorMessage = (String) request.getAttribute("errorMessage");
    errorMessage = Util.initString(errorMessage, "");
    
    EDITTrxVO[] trxToReverse = (EDITTrxVO[]) session.getAttribute("trxToReverse");

    Map<String, EDITTrxVO> reversalMap = (Map<String, EDITTrxVO>) session.getAttribute("reversalMap");
    
	String segmentStatus = contractMainSessionBean.getValue("statusCode");
	if (segmentStatus.equals("")) {
		segmentStatus = (String) session.getAttribute("status");
	}

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>

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
		    document.getElementById("btnConfirm").disabled = true;
            alert("This Processing Cannot Be Completed: " + errorMessage + " Please adjust and retry.");
        }
        
        if (lockAlertStatus == "true")
        {
            shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;
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
	}

	function runReversals()
    {
	    var message = "";

	    if (segmentStatus == "Death") {
			message = "This policy/contract has already had its death claim processed.  Are you sure you want to proceed?";
		} else if (segmentStatus == "Extension") {
			message = "This policy/contract is receiving benefits under the Extension of Benefits rider.  Any changes made may affect the ongoing claim.  Are you sure you want to proceed?";
		} else if (segmentStatus == "LTC") {
			message = "This policy/contract is receiving benefits under the Long Term Care rider.  Any changes made may affect the ongoing claim.  Are you sure you want to proceed?";
		} else if (segmentStatus == "PayorPremiumWaiver") {
			message = "This policy/contract is receiving benefits under the Payor Premium Waiver rider.  Any changes made may affect the ongoing claim.  Are you sure you want to proceed?";
		} else if (segmentStatus == "ReducedPaidUp") {
			message = "This policy/contract is currently paid up with a Reduced Paid Up benefit.  Any changes made may affect its Reduced Paid Up Death Benefit.  Are you sure you want to proceed?";
		} else if (segmentStatus == "RestoredBenefit") {
			message = "This policy/contract is currently paid up under the Restoration of Benefits rider.  Any changes made may affect its claim status.  Are you sure you want to proceed?";
		} else if (segmentStatus == "Surrendered") {
			message = "This policy/contract has already been surrendered.  Are you sure you want to proceed?";
		} else if (segmentStatus == "Waiver") {
			message = "This policy/contract is receiving Premium Waiver benefits.  Any changes made may affect the ongoing claim.  Are you sure you want to proceed?";
		} else if (segmentStatus == "WaiverMonthlyDed") {
			message = "This policy/contract is receiving Waiver of Monthly Deductions benefits.  Any changes made may affect the ongoing claim.  Are you sure you want to proceed?";
		}
		
		if (message != "") {
			if (confirm(message)) {
		        opener.top.location.href="/PORTAL/servlet/RequestManager?transaction=ContractDetailTran&action=runBatchReversals"; //&searchValue=" + segmentPK;
		    	window.close();
			}
		} else {
	        opener.top.location.href="/PORTAL/servlet/RequestManager?transaction=ContractDetailTran&action=runBatchReversals"; //&searchValue=" + segmentPK;
	    	window.close();
		}
	    
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
<title>Batch Reversal Confirmation</title>
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
  <div style="text-align:center;">
    
    <%
	    if (errorMessage != null && !errorMessage.equals("")) {
	    	%>
	    	 <h3><span style="color: red"> ** Processing Cannot Be Completed: <%= errorMessage %> ** </span></h3>
	    	<% 
	    } else {
	    	%>
	      	<h3>The following transactions will be affected by this processing.  Please confirm to proceed with processing.</h3>
	      	
	      	<h5>Please note that this is only an estimate of processing ... Final results may vary.</h5>
	      	
			<% 
	    }
     %>
     
    <%
        if (reversalMap != null) {
        	%>
        	 <h5> ** Reversals Are Highlighted ** </h5>
        	<% 
        }
    
     %>
  </div>
  
  </br>
  
  <table class="summary" border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <th width="20%">Expected Action</th>
        <th width="10%">Effective Date</th>
        <th width="10%">Transaction</th>
  		<th width="10%">Sequence</th>
  		<th width="10%">Linked Trx</th>
  		<th width="10%">Status</th>
  		<th width="10%">Pending Status</th>
  		<th width="10%">Amount</th>
  		<th width="10%">Operator</th>
    </tr>
  </table>
  
  <span style="border-style:solid; border-width:1;  position:relative; width:100%; height:49%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="contractTrxSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
        String rowToMatch = "";
        String className = "";
        boolean selected = false;

        if (reversalMap != null) {
        	
            Iterator it = reversalMap.keySet().iterator();

            while (it.hasNext()) {
            	
                String key = (String) it.next();

                String tAction = "";
                		
                String[] tokens = Util.fastTokenizer(key, "_");
				if (tokens != null && tokens.length > 1) {
					tAction = tokens[1];
				}
				
				EDITTrxVO editTrxVO = reversalMap.get(key);
				
                String tEffectiveDate = editTrxVO.getEffectiveDate();
                String tSequenceNumber = String.valueOf(editTrxVO.getSequenceNumber());
                String tTransactionStatus = editTrxVO.getStatus();
                String tTransactionType	= editTrxVO.getTransactionTypeCT();
                String tAmount = editTrxVO.getTrxAmount().toString();
                String tPendingStatus = editTrxVO.getPendingStatus();
                String tOperator = editTrxVO.getOperator();
                
                String tLinkedTrx = "";
				if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("PY") && editTrxVO.getOriginatingTrxFK() > 0) {
					tLinkedTrx = "Y";
				}
				
                if (tAction.equalsIgnoreCase("REVERSE")) {
                    className = "highlighted";

                } else {
                    className = "default";
                }
      %>
        <tr class="<%= className %>" id="<%= key %>">
            
            <td nowrap width="20%">
                <%= tAction %>
            </td>
            <td nowrap width="10%">
                <%= tEffectiveDate %>
            </td>
            <td nowrap width="10%">
                <%= tTransactionType %>
            </td>
            <td nowrap width="10%">
                <%= tSequenceNumber %>
            </td>
            <td nowrap width="10%">
                <%= tLinkedTrx %>
            </td>
            <td nowrap width="10%">
                <%= tTransactionStatus %>
            </td>
            <td nowrap width="10%">
                <%= tPendingStatus %>
            </td>
            <td nowrap width="10%">
                <script>document.write(formatAsCurrency(<%= tAmount %>))</script>
            </td>
            <td nowrap width="10%">
                <%= tOperator %>
            </td>
        </tr>
      <%
            }// end while

        }
      %>
      
    </table>
  </span>

  </br></br>

  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
  	  <td nowrap align="left">
  		<input type="button" value="   Confirm Processing   " id="btnConfirm" title="Reverse All Trx Highlighted in the Table" style="background-color:#DEDEDE" onClick="runReversals()">
  	  </td>
  	  <td nowrap align="right">
  		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelReversals()">
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