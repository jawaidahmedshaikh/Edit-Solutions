<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.ProductStructureVO,
                 fission.utility.Util" %>


<%
	ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("productStructureVOs");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.coiReplenishmentForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function runCoiReplenishment()
    {
        if (f.productStructure.value == "" ||
            f.productStructure.value ==  "Please Select")
        {
            alert("Please Select A Company Structure");
        }
        else if (f.processDate.value == "")
        {
            alert("Please Enter the Start RMD Annual Date");
        }
        else
        {
		    sendTransactionAction("ReportingDetailTran", "runCoiReplenishment", "main");
        }
	}

	function closeCoiReplenishmentParams()
    {
		sendTransactionAction("ReportingDetailTran", "showReportingMain", "main");
	}

</script>

<title>COI Replenishment</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="coiReplenishmentForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="rmd" name="coiParams" width="56%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td width="26%" nowrap>Company Key:</td>
      <td width="9%" nowrap>
        <select name="productStructure">
          <option selected value="Please Select">Please Select</option>
  	      <option value="All">All</option>
          <%
            for(int i = 0; i< productStructureVOs.length; i++)
            {
                String currentProductStructurePK = productStructureVOs[i].getProductStructurePK() + "";

                out.println("<option value=\""+ currentProductStructurePK + "\">" + Util.getProductStructure(productStructureVOs[i], ",") + "</option>");
            }
          %>
        </select>
      </td>
      <td width="42%">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap colspan="5">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap align="right">Contract Number:&nbsp;</td>
      <td nowrap align="left">
        <input type="text" name="contractNumber" maxlength="15" size="15" value="">
      </td>
    </tr>
    <tr>
      <td nowrap colspan="5">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap align="right">Run Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="processDate"
              attributesText="id='processDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.processDate', f.processDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td nowrap colspan="5">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap colspan="4">&nbsp;</td>
      <td nowrap align="right">
        <input type="button" name="enter" value="Enter" onClick="runCoiReplenishment()">
        <input type="button" name="cancel" value="Cancel" onClick="closeCoiReplenishmentParams()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>