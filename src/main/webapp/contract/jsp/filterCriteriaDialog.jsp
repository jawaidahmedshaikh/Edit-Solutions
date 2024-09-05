<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*" %>

<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>
<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>


<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    
//    codeTableWrapper.reloadCodeTables();
    
    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");

    CodeTableVO[] trxTypes = null;

    if (companyStructureId != null && !companyStructureId.equalsIgnoreCase(""))
    {
        trxTypes = codeTableWrapper.getCodeTableEntries("TRXTYPE", Long.parseLong(companyStructureId));
    }
    else
    {
        trxTypes = codeTableWrapper.getCodeTableEntries("TRXTYPE");
    }

    String contractId = contractMainSessionBean.getValue("contractId");
%>

<html>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.filterCriteriaForm;
	}

    function cancelFilterCriteriaDialog()
    {
        sendTransactionAction("ContractDetailTran", "cancelFilterCriteriaDialog", "transactionDialog");
        window.close();
    }

	function showTransactionDefault() {

		f.transactionType.value = f.transactionTypeSelect.options[f.transactionTypeSelect.selectedIndex].text;

		var filterTypeChecked = false;

		for (var i = 0; i < 2; i++) {

			if (f.filterInd[i].checked) {

				filterTypeChecked = true;

				f.filterType.value = f.filterInd[i].value;
			}
		}


		if ((filterTypeChecked) && (f.filterValue.value != ""))  {

            sendTransactionAction("ContractDetailTran", "showTransactionDefault", "transactionDialog");
            window.close();
		}
		else {

			alert("A Filter Type / Filter Value must be set.")
		}
	}

    function sendTransactionAction(transaction, action, target) {

        f.transaction.value=transaction;
        f.action.value=action;

        f.target = target;

        f.submit();
    }

</script>

<head>

<title>Specify Transaction Type and Filter Criteria</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

</head>

<body class="dialog" onLoad="init()">
<form name="filterCriteriaForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" height="52%" bgcolor="#DCDCDC">
    <tr>
      <td align="left">
        Filter Type and Value:
        <table width="100%" height="100%" class="contentArea" bgcolor="#DCDCDC">
          <tr>
            <td nowrap align="left">
            <%
              List filterVector = new ArrayList();
              filterVector.add("Client ID");
              filterVector.add("Contract Number");

              for (int i = 0; i < 2; i++) {

                  if (filterVector.get(i).equals("Contract Number") &&
                      contractId != null && !contractId.equals(""))
                  {
                     out.println("<input checked type=\"radio\" name=\"filterInd\" value=\"" + filterVector.get(i) + "\">");

                  }
                  else
                  {
                    out.println("<input type=\"radio\" name=\"filterInd\" value=\"" + filterVector.get(i) + "\">");
                  }
                  out.println(filterVector.get(i));
                  out.println("<br>");
              }
            %>
            </td>
            <td nowraper align="right" valign="center">Filter Value:
              <input type="text" name="filterValue" size="15" maxlength="15" value="<%= contractId%>">
            </td>
          <tr>
        </table>
      </td>
    </tr>
  </table>
  <table width="100%" height="48%">
    <tr>
      <td nowrap width="1%">Transaction Type:</td>
      <td>
        <select name="transactionTypeSelect">
        <%

            for(int i = 0; i < trxTypes.length; i++) {

                String codeTablePK = trxTypes[i].getCodeTablePK() + "";
                String codeDesc    = trxTypes[i].getCodeDesc();

                if (!codeDesc.startsWith("Hedge"))
                {
                    out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                }
            }

       %>

        </select>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="right">
        <input type="button" name="enter" value="Enter" onClick="showTransactionDefault()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelFilterCriteriaDialog()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction"     value="">
  <input type="hidden" name="action"          value="">
  <input type="hidden" name="transactionType" value="">
  <input type="hidden" name="filterType"      value="">

</form>
</body>
</html>
