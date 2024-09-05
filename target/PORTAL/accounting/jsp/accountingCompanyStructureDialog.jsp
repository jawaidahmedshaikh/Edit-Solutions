<!-- ***** JAVA CODE ***** -->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.net.*, java.util.*, fission.global.*, fission.beans.*" %>

<jsp:useBean id="filteredCompanyStructures"
    class="fission.beans.SessionBean" scope="request"/>

<%
	String[] companyList = filteredCompanyStructures.getValues("companyStructures");
	if(companyList == null){companyList = new String[0];}

	String[] companyIdList = filteredCompanyStructures.getValues("companyStructureIds");
	if(companyIdList == null){companyIdList = new String[0];}

%>
<html>
<head>
<title>Select CompanyStructure.jsp</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<!-- ****** JAVASCRIPT ***** //-->

<script language="Javascript1.2">

	var companyName      = "";
	var companyStructureId        = "";
	var isCancelled 	= false;
	var f 				= null;

	function init() {

		f = document.companyForm;
		window.resizeTo(getPreferredWidth(), getPreferredHeight());
	}

	function getPreferredWidth() {

		return 1.1 * document.all.table1.offsetWidth;
	}

	function getPreferredHeight() {

		return 1.25 * document.all.table1.offsetHeight;
	}

	function setSelection() {

		companyName = f.companyStructureId.options[f.companyStructureId.selectedIndex].text;
	}

	function isCancelled() {

		return isCancelled;
	}

    function submitCompanyStructure() {

   		f.companyStructure.value =  f.companyStructureId.options[f.companyStructureId.selectedIndex].text;

		sendTransactionAction("AccountingDetailTran", "showChartSummaryByCSID", "contentIFrame");
		window.close();
	}

	function bCancel() {

		window.close();
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value = action;

		f.target = target;

		f.submit();
	}

</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="companyForm">
  <table id="table1" name="accounting" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
     <td width="26%" nowrap>Company Structure:</td>
     <td width="9%" nowrap>
	  <select name="companyStructureId" onChange="setSelection()">
         <option value="0" > Remove Filters</option>
	     <%
			for(int i=0; i<companyList.length; i++){
				out.println("<option name=\"companyStructureId\" value=\""+ companyIdList[i] + "\">"+companyList[i]+"</option>");
			}
		 %>
	  </select>
	 </td>
	</tr>
	<tr>
	<td width="26%">&nbsp;</td>
	<td width="9%">&nbsp;</td>
	<td align="right" nowrap>
        <input type="button" name="enter" value="Enter" onClick="submitCompanyStructure()">
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
    </td>
   </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="companyStructure" value="">

</form>
</body>
</html>