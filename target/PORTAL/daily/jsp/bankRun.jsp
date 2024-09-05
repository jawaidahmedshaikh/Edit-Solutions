<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.*,
                 edit.common.*" %>


<jsp:useBean id="bankRunPageBean"
    class="fission.beans.PageBean" scope="request"/>

<%
	String[]  companyNames = bankRunPageBean.getValues("companyNames");

    CodeTableVO[] fileTypes = CodeTableWrapper.getSingleton().getCodeTableEntries("OUTPUTFILETYPE");
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.bankRunForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function createBankExtract() {

		sendTransactionAction("DailyDetailTran", "createBankExtracts", "main");
	}

	function bCancel() {

		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}

</script>

<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="bankRunForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="banking" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td width="26%" nowrap>Company Key:</td>
      <td width="9%" nowrap>
      <select name="companyName">
      <option selected value="Please Select">
         Please Select
      </option>
	<option> All </option>

      <%
      	for(int i = 0; i < companyNames.length; i++)
        {

			out.println("<option name=\"companyName\">" + companyNames[i] + "</option>");
      	}
      %>
      </select>
      </td>
      <td width="65%">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%" height="24" nowrap>Contract Id:</td>
      <td width="9%" height="24" nowrap>
        <input type="text" name="contractId" maxlength="15" size="15">
      </td>
      <td width="65%" height="24">&nbsp;</td>
    </tr>
    <br>
    <tr>
      <td width="26%" height="24" nowrap>Output File Type:</td>
      <td width="9%" nowrap>
            <select name="outputFileType">
                <option value="null">Please Select</option>
                <%
                    for(int i = 0; i < fileTypes.length; i++)
                    {
                        String codeDesc = fileTypes[i].getCodeDesc();
			            out.println("<option name=\"outputFileType\">" + codeDesc + "</option>");
                    }
                %>
            </select>
        </td>
      <td width="65%">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="65%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="createBankExtract()">
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>