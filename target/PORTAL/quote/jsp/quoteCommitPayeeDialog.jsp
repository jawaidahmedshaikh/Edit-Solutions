<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.Util" %>

<jsp:useBean id="quotePayeeOverrides"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    PageBean mainBean = quoteMainSessionBean.getPageBean("formBean");
    String companyStructureId = Util.initString(mainBean.getValue("companyStructureId"), "0");

	CodeTableVO[] bankAccounts = codeTableWrapper.getCodeTableEntries("BANKACCTTYPE", Long.parseLong(companyStructureId));

	String key = formBean.getValue("key");

	String federalWithholdingIndStatus = formBean.getValue("federalWithholdingIndStatus");
	String stateWithholdingIndStatus   = formBean.getValue("stateWithholdingIndStatus");
	String countyWithholdingIndStatus  = formBean.getValue("countyWithholdingIndStatus");
	String cityWithholdingIndStatus    = formBean.getValue("cityWithholdingIndStatus");

    String trxPayeeOverridePK = formBean.getValue("trxPayeeOverridePK");
    String trxBankOverridePK  = formBean.getValue("trxBankOverridePK");
    String trxWithholdingOverridePK = formBean.getValue("trxWithholdingOverridePK");
	String payeeClientId      = formBean.getValue("payeeClientId");
	String percent	          = formBean.getValue("payeePercent");
	String dollars            = formBean.getValue("payeeDollars");
	String state    	      = formBean.getValue("payeeStateWithholding");
    String statePct           = formBean.getValue("payeeStateWithholdingPct");
	String federal		      = formBean.getValue("payeeFederalWithholding");
    String federalPct         = formBean.getValue("payeeFederalWithholdingPct");
	String city	              = formBean.getValue("payeeCityWithholding");
	String county   		  = formBean.getValue("payeeCountyWithholding");
	String disburseSource     = formBean.getValue("payeeDisbursementSource");
    String bankAcctNbrOvrd    = formBean.getValue("payeeBankAcctNumberOvrd");
    String bankRoutingNbrOvrd = formBean.getValue("payeeBankRoutingNbrOvrd");
    String bankAcctTypeOvrd   = formBean.getValue("payeeBankAcctTypeOvrd");
    String payeePreNoteDate   = formBean.getValue("payeePreNoteDate");

	String rowToMatchBase = formBean.getValue("key");

	String transactionId = (String) request.getAttribute("transactionId");

    CodeTableVO[] disbursements = codeTableWrapper.getCodeTableEntries("DISBURSESOURCE", Long.parseLong(companyStructureId));

%>

<html>
<head>
<title>Payee Overrides</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<!-- ****** STYLE CODE ***** //-->

<style>

	.highLighted {

		background-color: #FFFFCC;
	}

	.dummy {
	}

	table {

		background-color: #DCDCDC;
	}
</style>

<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.payeeForm;

		window.resizeTo(getPreferredWidth(), getPreferredHeight());

		var payeeOverrideInd = document.all.table2.rows.length - 1;
	}

	function getPreferredWidth() {

		var tableWidth = document.all.table1.offsetWidth;

    	return tableWidth;
    }

    function getPreferredHeight() {

		var tableHeight = document.all.table1.offsetHeight;

    	return 450;
    }

    function closePayeeDialog() {

		sendTransactionAction("QuoteDetailTran", "closePayeeShowSchedEvent", "_main");
        window.close();
    }

	function selectRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.key.value = key;

		sendTransactionAction("QuoteDetailTran", "showPayeeDetailSummary", "_self");
	}

	function addNewPayeeOverride()	 {

		var transactionId = f.transactionId.value;

		clearForm();

		f.transactionId.value = transactionId;

	}

	function cancelPayeeOverride() {

		var key           = f.key.value;
		var transactionId = f.transactionId.value;

		clearForm();

		f.key.value = key;
		f.transactionId.value = transactionId;

		sendTransactionAction("QuoteDetailTran","cancelPayeeOverride","_self")
	}

	function updatePayeeOverride() {


		if (f.noFederalWithholdingInd.checked == true) {

			f.federalWithholdingIndStatus.value = "checked";
		}
        else {

            f.federalWithholdingIndStatus.value = "unchecked";
        }

		if (f.noStateWithholdingInd.checked == true) {

			f.stateWithholdingIndStatus.value = "checked";
		}
        else {

            f.stateWithholdingIndStatus.value = "unchecked";
        }

		if (f.noCountyWithholdingInd.checked == true) {

			f.countyWithholdingIndStatus.value = "checked";
		}
        else {

            f.countyWithholdingIndStatus.value = "unchecked";
        }

		if (f.noCityWithholdingInd.checked == true) {

			f.cityWithholdingIndStatus.value = "checked";
		}
        else {

            f.cityWithholdingIndStatus.value = "unchecked";
        }

        opener.f.payeeIndStatus = "checked";
        opener.f.payeeInd.checked = true;

		sendTransactionAction("QuoteDetailTran", "updatePayeeOverride", "_self");
	}

	function deletePayeeOverride() {

		sendTransactionAction("QuoteDetailTran","deletePayeeOverride","_self")
	}

	function clearForm() {

		f.noFederalWithholdingInd.checked = false;
		f.noStateWithholdingInd.checked   = false;
		f.noCountyWithholdingInd.checked  = false;
		f.noCityWithholdingInd.checked    = false;

		f.payeeClientId.value = "";
		f.percent.value  = "";
		f.dollars.value  = "";

		f.stateWithholding.value      = "";
        f.stateWithholdingPct.value   = "";
		f.federalWithholding.value    = "";
        f.federalWithholdingPct.value = "";
		f.cityWithholding.value       = "";
		f.countyWithholding.value     = "";
		f.key.value                   = "";

		f.payeeDisbursementSource.selectedIndex = 0;
	}

    function sendTransactionAction(transaction, action, target) {

    	f.transaction.value=transaction;
    	f.action.value=action;

    	f.target = target;

    	f.submit();
    }

</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="payeeForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="100%" height="10%" border="0" cellspacing="6" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="right" nowrap>Client Id: </td>
      <td width="10%">
        <input type="text" name="payeeClientId" size="11" maxlength="11" value="<%= payeeClientId %>">
      </td>
      <td align="right" nowrap>Percent: </td>
      <td>
        <input type="text" name="percent" size="9" maxlength="9" value="<%= percent %>">
      </td>
      <td align="right" nowrap>Dollars: </td>
      <td>
        <input type="text" name="dollars" size="9" maxlength="9" value="<%= dollars %>" >
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Bank Acct Number: </td>
      <td width="10%">
        <input type="text" name="payeeBankAcctNumberOvrd" size="17" maxlength="17" value="<%= bankAcctNbrOvrd %>">
      </td>
      <td align="right" nowrap>Bank Routing Number: </td>
      <td>
        <input type="text" name="payeeBankRoutingNbrOvrd" size="9" maxlength="9" value="<%= bankRoutingNbrOvrd %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Bank Account Type:</td>
      <td>
          <select name="payeeBankAcctTypeOvrd">
            <option selected> Please Select </option>
            <%

                for(int i = 0; i < bankAccounts.length; i++) {

                    String codeTablePK = bankAccounts[i].getCodeTablePK() + "";
                    String codeDesc    = bankAccounts[i].getCodeDesc();
                    String code        = bankAccounts[i].getCode();

                    if (bankAcctTypeOvrd.equalsIgnoreCase(code)) {

                        out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                    }
                    else  {

                        out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                    }
                }

            %>
          </select>
      </td>
      <td align="right" nowrap>Pre Note Effective Date:</td>
      <td colspan="3">
           <input type="text" name="payeePreNoteDate" value="<%= payeePreNoteDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.payeePreNoteDate', f.payeePreNoteDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Federal Withholding:</td>
      <td colspan="2">
        <input type="text" name="federalWithholding" size="9" maxlength="9" value="<%= federal %>">
      </td>
      <td align="right" nowrap>State Withholding:</td>
      <td colspan="2">
        <input type="text" name="stateWithholding" size="9" maxlength="9" value="<%= state %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Federal Withholding Pct:</td>
      <td colspan="2">
        <input type="text" name="federalWithholdingPct" size="9" maxlength="9" value="<%= federalPct %>">
      </td>
      <td align="right" nowrap>State Withholding Pct:</td>
      <td colspan="2">
        <input type="text" name="stateWithholdingPct" size="9" maxlength="9" value="<%= statePct %>">
      </td>
    </tr>
    <tr>
	  <td align="center" nowrap colspan="3">
        <input type="checkbox" name="noFederalWithholdingInd" <%= federalWithholdingIndStatus %>>
        No Federal Withholding</td>
      <td align="center" nowrap colspan="3">
        <input type="checkbox" name="noStateWithholdingInd" <%= stateWithholdingIndStatus %>>
        No State Withholding</td>
    </tr>
    <tr>
      <td align="right" nowrap>City Withholding: </td>
      <td colspan="2">
        <input type="text" name="cityWithholding" size="9" maxlength="9" value="<%= city %>" >
      </td>
      <td align="right" nowrap>County Withholding:</td>
      <td colspan="2">
        <input type="text" name="countyWithholding" size="9" maxlength="9" value="<%= county %>" >
      </td>
    </tr>
    <tr>
      <td align="center" nowrap colspan="3">
        <input type="checkbox" name="noCityWithholdingInd" <%= cityWithholdingIndStatus%> >
        No City Withholding</td>
      <td align="center" nowrap colspan="3">
        <input type="checkbox" name="noCountyWithholdingInd" <%=countyWithholdingIndStatus%>>
        No County Withholding</td>
    </tr>
    <tr>
      <td align="right" nowrap>Disbursement Source: </td>
      <td>

        <select name="payeeDisbursementSource" value="<%= disburseSource %>">

			<option> Please Select </option>
	        <%

                for(int i = 0; i < disbursements.length; i++) {

                    String codeTablePK = disbursements[i].getCodeTablePK() + "";
                    String code        = disbursements[i].getCode();
                    String codeDesc    = disbursements[i].getCodeDesc();

                   if (disburseSource.equalsIgnoreCase(code)) {

                       out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                   }
                   else  {

                       out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                   }
                }

	       %>
		</select>


      </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <tr>
      	<td nowrap align="left" colspan="4">
      			<input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNewPayeeOverride()">
      			<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="updatePayeeOverride()">
      			<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelPayeeOverride()">
      			<input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deletePayeeOverride()">
      	</td>
    </tr>
  </table>

<span id="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%;  top:0; left:0; z-index:0; overflow:scroll">
  <table id="table2" height="10%" width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">

	<tr bgcolor="#30548E">
	  <td align="left" nowrap><font color= "#FFFFFF">Client ID</font></td>
	  <td align="left" nowrap><font color= "#FFFFFF">Percent</font></td>
	  <td align="left" nowrap><font color= "#FFFFFF">Dollars</font></td>
	  <td align="left" nowrap><font color= "#FFFFFF">State W/H</font></td>
	  <td align="left" nowrap><font color= "#FFFFFF">Federal W/H</font></td>
	  <td align="left" nowrap><font color= "#FFFFFF">City W/H</font></td>
	  <td align="left" nowrap><font color= "#FFFFFF">County W/H</font></td>
	  <td align="left" nowrap><font color= "#FFFFFF">Disburse Source</font></td>
	</tr>
	<%
			int rowId   = 0;

			String rowToMatch = "";
			String trClass = "";
			String selected = "";

			Iterator it = quotePayeeOverrides.getPageBeans().values().iterator();

			while (it.hasNext())  {

				PageBean payeeBean = (PageBean) it.next();

				String iClientId 	   = payeeBean.getValue("payeeClientId");
				String iPercent	       = payeeBean.getValue("payeePercent");
				String iDollars        = payeeBean.getValue("payeeDollars");
				String iState	       = payeeBean.getValue("payeeStateWithholding");
				String iFederal		   = payeeBean.getValue("payeeFederalWithholding");
				String iCity	       = payeeBean.getValue("payeeCityWithholding");
				String iCounty		   = payeeBean.getValue("payeeCountyWithholding");
				String iDisburseSource = payeeBean.getValue("payeeDisbursementSource");
                for(int i = 0; i < disbursements.length; i++) {

                    String codeTablePK = disbursements[i].getCodeTablePK() + "";
                    String code        = disbursements[i].getCode();
                    String codeDesc    = disbursements[i].getCodeDesc();

                   if (iDisburseSource.equalsIgnoreCase(codeTablePK)) {

                       iDisburseSource = code;
                   }
                }

				// Store behind the scenes...
				String iKey = payeeBean.getValue("key");
				String iStatus = payeeBean.getValue("status");

				rowToMatch = iKey;

				if (rowToMatch.equals(rowToMatchBase)) {

						trClass = "highLighted";
				}
				else {

					trClass = "dummy";
				}

				if (iKey.startsWith(transactionId) && ! iStatus.equals("deleted"))  {
		%>
		<tr class="<%= trClass %>" id="<%= iKey %>" onClick="selectRow()">

			<td nowrap>
				<%= iClientId %>
			</td>
			<td nowrap>
				<%= iPercent %>
			</td>
			<td nowrap>
				<%= iDollars %>
			</td>
			<td nowrap>
				<%= iState %>
			</td>
			<td nowrap>
				<%= iFederal %>
			</td>
			<td nowrap>
				<%= iCity %>
			</td>
			<td nowrap>
				<%= iCounty %>
			</td>
			<td nowrap>
				<%= iDisburseSource %>
			</td>
		</tr>
	  	<%
			} // end if
		}// end while
		%>
</table>

</span>

<table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0" bgcolor="#DDDDDD">
	<tr>
			<td align="right" nowrap colspan="4">
				<input type="button" name="cancel" value="Close" onClick ="closePayeeDialog()">
			</td>
	</tr>
</table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="key" value="<%= key %>">
 <input type="hidden" name="transactionId" value="<%= transactionId %>">
 <input type="hidden" name="trxPayeeOverridePK" value="<%= trxPayeeOverridePK %>">
 <input type="hidden" name="trxBankOverridePK" value="<%= trxBankOverridePK %>">
 <input type="hidden" name="trxWithholdingOverridePK" value="<%= trxWithholdingOverridePK %>">

 <input type="hidden" name="federalWithholdingIndStatus" value="<%= federalWithholdingIndStatus %>">
 <input type="hidden" name="stateWithholdingIndStatus"  value="<%= stateWithholdingIndStatus %>">
 <input type="hidden" name="countyWithholdingIndStatus" value="<%= countyWithholdingIndStatus %>">
 <input type="hidden" name="cityWithholdingIndStatus"   value="<%= cityWithholdingIndStatus %>">

</form>
</body>
</html>
