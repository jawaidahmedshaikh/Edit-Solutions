<!-- contractCheckIfNewHierarchyNeededDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 fission.utility.Util,
                 fission.utility.*,
                 edit.portal.common.session.UserSession,
                 engine.*,
                 group.*" %>
                 
<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>


<%
	//PageBean mainBean = contractMainSessionBean.getPageBean("formBean");
	String segmentAmount = formBean.getValue("segmentAmount");

%>

<html>
<head>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		// Resize for billing changes where loaded in full-size screen
        window.resizeTo(0.3 * screen.width, 0.2 * screen.height);
        //window.resizeTo(screen.width, screen.height);
		
		f = document.checkIfNewHierarchyNeededForm;
	}

	function showAgentHierarchyAllocationDialog() {
        var width = 0.90 * screen.width;

        var height= 0.65 * screen.height;

        window.close();
        
        openDialog("","contractAgentHierarchyAllocation","top=0,left=0,resizable=no,width=" + width + ",height=" + height,"ContractDetailTran","showAgentHierarchyAllocationDialog");
    }

	/**
     * Pops-open the specified dialog.
     */
	function openDialog(theURL,winName,features,transaction,action) {

        dialog = window.open(theURL,winName,features);

	    prepareToSendTransactionAction(transaction, action, winName);
	}

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
    }
	
	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function closeWindow() {
		window.close();
	}

</script>

<title>New Agent Hierarchy</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="checkIfNewHierarchyNeededForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="51%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td colspan="3" nowrap>Does this rider require a new Agent Hierarchy?
      </td>
    </tr>
    <tr>
      <td colspan="3" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3" align="right" valign="bottom" nowrap>
        <input type="button" name="yes" value="Yes" onClick="showAgentHierarchyAllocationDialog()">
        <input type="button" name="no" value="No" onClick="closeWindow()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="segmentAmount" value="<%= segmentAmount %>">
  
</form>
</body>
</html>
