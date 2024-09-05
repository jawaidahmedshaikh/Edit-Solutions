<!-- quoteCommitContractNumberDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, 
				fission.beans.*, 
				engine.*, 
				fission.utility.*,
				contract.*" %>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
	PageBean formBean = quoteMainSessionBean.getPageBean("formBean");

	String segmentPK = formBean.getValue("segmentPK");
	Segment segment = new Segment(new Long(segmentPK));
	
	Company company = Company.findByProductStructurePK(segment.getProductStructureFK());
	String policyNumberPrefix = company.getPolicyNumberPrefix();
	String policyNumberSuffix = Util.initString(company.getPolicyNumberSuffix(), "");
	int policySequenceLength = company.getPolicySequenceLength();
	
	int contractMaxLength = policyNumberPrefix.length() + policySequenceLength + policyNumberSuffix.length();
	
	String contractNumber = quoteMainSessionBean.getValue("contractId");

	String contractTarget = (String) request.getAttribute("contractTarget");

	if ( contractNumber.equals("") ) {

		contractNumber = formBean.getValue("contractNumber");
	}

	String checkBoxStatus = formBean.getValue("checkBoxStatus");

	if (checkBoxStatus.equals("true")){

		checkBoxStatus = "checked";
	}
	else {

		checkBoxStatus = "";
	}
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;
	var contractTarget = "<%=contractTarget%>";

	function init() {

		f = document.contractNumberForm;

		window.resizeTo(getPreferredWidth(), getPreferredHeight());
	}

	function getPreferredWidth() {

		return 1.25 * document.all.table1.offsetWidth;
	}

	function getPreferredHeight() {

		return 1.50 * document.all.table1.offsetHeight;
	}

	function openDialog(theURL,winName,features,transaction,action) {

	  dialog = window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, winName);
	}

    function checkForEnter(){

        if (window.event.keyCode == 13){

            save();
        }
    }

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;
		f.target = target;

		f.submit();
	}

	function autoGenerateContractNumber() {

		if (f.autoAssignNumberBox.checked == true) {

			f.checkBoxStatus.value = "true";
			f.contractTarget.value = "<%=contractTarget%>";
			sendTransactionAction('QuoteDetailTran', 'autoGenerateContractNumber', '_self');
		}
		else {

			f.contractNumber.value        = "";
			f.autoAssignNumberBox.checked = false;
			f.checkBoxStatus.value = "false";
			f.contractTarget.value = "<%=contractTarget%>";
		}
	}

	function cancelDialog() {

		window.close();

	}

	function save() {
		
		var policyNumberPrefix = "<%=policyNumberPrefix%>";
		var policyNumberSuffix = "<%=policyNumberSuffix%>";
	
		var policySequenceLength = "<%=policySequenceLength%>";
		
		//check policy prefix and sequence have values
		if( policyNumberPrefix && policySequenceLength ) {
		
			var regEx = new RegExp( "^" + policyNumberPrefix + "[0-9]{" + policySequenceLength + "," + policySequenceLength + "}" + policyNumberSuffix + "$" );
			
			if (!regEx.test(f.contractNumber.value))			
				window.alert("Contract number doesn't meet requirements"); 
			else {
				if (contractTarget == "saveQuote") {
	
					sendTransactionAction("QuoteDetailTran", "saveQuote", "contentIFrame");
	 			}
	
				else {
	
					sendTransactionAction("QuoteDetailTran", "commitContract", "contentIFrame");
				}
			
				window.close();
			}
		}
	}

</script>

<title>Specify Application Number</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="contractNumberForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="51%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td colspan="3" nowrap>Application Number:
        <input type="text" name="contractNumber" maxlength="<%=contractMaxLength%>" size="15" value="<%= contractNumber%>" onKeyDown="checkForEnter()">
      </td>
    </tr>
    <tr>
      <td colspan="3" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3" align="center" nowrap>
	    <input type="checkbox" name="autoAssignNumberBox" <%= checkBoxStatus %> onClick="autoGenerateContractNumber()">
        Auto Assign Number</td>
    </tr>
    <tr>
      <td colspan="3" align="center">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3" align="right" valign="bottom" nowrap>
        <input type="button" name="enter" value="Enter" onClick="save()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="checkBoxStatus" value="">
  <input type="hidden" name="contractTarget" value="">

</form>
</body>
</html>
