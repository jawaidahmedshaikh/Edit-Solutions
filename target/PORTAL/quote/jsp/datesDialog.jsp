<%@ page import="fission.beans.PageBean,
                 edit.common.EDITDate,
                 fission.utility.Util,
                 fission.utility.DateTimeUtil"%>
<!--
 * (c) 2000 - 2008 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 -->

<html>
<%@ page import="edit.portal.common.session.UserSession" %>
                 
<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
	PageBean formBean = quoteMainSessionBean.getPageBean("formBean");

    String firstNotifyDate = formBean.getValue("firstNotifyDate");

    String previousNotifyDate = formBean.getValue("previousNotifyDate");

    String finalNotifyDate = formBean.getValue("finalNotifyDate");

    String advanceFinalNotify = formBean.getValue("advanceFinalNotify");
    
    UserSession userSession = (UserSession) session.getAttribute("userSession");

%>
<head>
<title>Date Values</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script language="Javascript1.2">

	var f = null;

    var advanceFinalNotify = "<%= advanceFinalNotify %>";
    
    var editableContractStatus = true;

	function init() {

		f = document.dateForm;
        if (advanceFinalNotify == "C")
        {
            f.advanceFinalNotifyCheckBox.disabled = true;
            f.advanceFinalNotifyCheckBox.checked = true;
        }
        else if (advanceFinalNotify == "Y")
        {
            f.advanceFinalNotifyCheckBox.checked = true;
        }
        else
        {
            f.advanceFinalNotifyCheckBox.unchecked = true;
        }
        
        // check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;
        
	}

    function saveDialog()
    {
		if (editableContractStatus == false) {
        	
        	alert("This Contract Cannot Be Edited Due to Terminated Status.");

            return;
            
        } else {
        	
	        if (f.advanceFinalNotifyCheckBox.checked == true)
	        {
	            f.advanceFinalNotify.value = "Y";
	        }
	
	        sendTransactionAction("QuoteDetailTran", "saveDatesDialog", "contentIFrame");
	        window.close();
        }
    }

	function closeDialog()
    {
		window.close();
	}

</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="dateForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="40%" border="0" cellspacing="5" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right" width="5%">First Notify Date:</td>
      <td>
        <input type="text" name="firstNotifyDate" size="10" maxlength="10" disabled value="<%= firstNotifyDate %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Previous Notify Date:</td>
      <td>
        <input type="text" name="previousNotifyDate" size="10" maxlength="10" disabled value="<%= previousNotifyDate %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Closure Date:</td>
      <td>
        <input type="text" name="finalNotifyDate" size="10" maxlength="10" disabled value="<%= finalNotifyDate %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Advance Final Notify:</td>
      <td>
        <input type="checkbox" name="advanceFinalNotifyCheckBox">
      </td>
    </tr>
  </table>

<table width="100%" border="0" cellspacing="0" cellpadding="5">
    <tr>
        <td align="right" colspan="2">
            <input type="button" value=" Save " onClick="saveDialog()">
            &nbsp;
            <input type="button" value=" Cancel  " onClick="closeDialog()">
        </td>
    </tr>
</table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">
 <input type="hidden" name="nextPage" value="">
 <input type="hidden" name="advanceFinalNotify" value="<%= advanceFinalNotify%>">

</form>
</body>
</html>
