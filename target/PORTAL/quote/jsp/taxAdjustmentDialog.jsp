<!-- taxAdjustmentDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 event.Suspense,
                 fission.utility.Util,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO" %>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    PageBean formBean = quoteMainSessionBean.getPageBean("formBean");
    String companyStructureId = Util.initString(formBean.getValue("companyStructureId"), "0");

    Suspense suspense = (Suspense) request.getAttribute("suspense");
    String costBasis = suspense.getCostBasis().toString();
    String taxYear = suspense.getTaxYear() + "";
    String suspensePK = suspense.getSuspensePK().toString();
    String depositType = Util.initString(suspense.getDepositTypeCT(), "");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    CodeTableVO[] depositTypes = codeTableWrapper.getCodeTableEntries("DEPOSITTYPE", Long.parseLong(companyStructureId));
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.taxAdjustmentForm;

        formatCurrency();
	}

	function saveTaxes()
    {
        sendTransactionAction("QuoteDetailTran", "saveTaxAdjustment", "depositDialog");
		window.close();
	}
</script>

<title>Taxes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()" bgcolor="#DDDDDD">
<form name="taxAdjustmentForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="80%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
       <td nowrap align="left">Tax Year:&nbsp;&nbsp;
        <input type="text" name="taxYear" tabindex="4" value= "<%= taxYear %>">
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Cost Basis:&nbsp;
        <input type="text" name="costBasis" tabindex="4" value= "<%= costBasis %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Deposit Type:&nbsp;
            <select name="depositType" tabindex="1">
              <option> Please Select </option>
              <%
                for (int i = 0; i < depositTypes.length; i++)
                {
                    String codeTablePK = depositTypes[i].getCodeTablePK() + "";
                    String codeDesc    = depositTypes[i].getCodeDesc();
                    String code        = depositTypes[i].getCode();

                    if (depositType.equalsIgnoreCase(code))
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
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td width="57%" align="right" nowrap>
        <input type="button" name="enter" value="Enter" onClick="saveTaxes()">
        <input type="button" name="enter" value="Cancel" onClick="closeWindow()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="suspensePK" value="<%= suspensePK %>">

</form>
</body>
</html>
