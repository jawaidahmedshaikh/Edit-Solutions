<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.*,
                 edit.common.vo.*" %>


<jsp:useBean id="confirmsRunPageBean"
    class="fission.beans.PageBean" scope="request"/>

<%
	String[]  companyNames        = confirmsRunPageBean.getValues("companyNames");
    CodeTableVO[] correspondenceTypeVOs = CodeTableWrapper.getSingleton().getCodeTableEntries("CORRESPONDENCETYPE");
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.confirmsRunForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

	function processConfirms() {

		sendTransactionAction("DailyDetailTran", "runConfirms", "main");
	}

	function bCancel() {

		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}

</script>

<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="confirmsRunForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="accounting" align="center" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap>Company Key:</td>
      <td nowrap align="left">
          <select name="companyName">
          <option selected value="Please Select">
             Please Select
          </option>
        <option> All </option>

          <%
            for(int i = 0; i < companyNames.length; i++) {

                out.println("<option name=\"companyName\">" + companyNames[i] + "</option>");
            }
          %>
          </select>
      </td>
      <td nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Confirm Type:</td>
      <td align="left">
          <select name="correspondenceTypeCT">
          <option selected value="Please Select">
             Please Select
          </option>
        <%

            if (correspondenceTypeVOs != null) {

                for(int i = 0; i < correspondenceTypeVOs.length; i++) {

                    String correspondenceTypeCT = correspondenceTypeVOs[i].getCodeTablePK() + "";
                    String desc = correspondenceTypeVOs[i].getCodeDesc();

                    out.println("<option value=" + correspondenceTypeCT + ">" + desc);
                }
            }
        %>
        </select>
      </td>
    </tr>
    <tr>
      <td colspan="4" align="center">
        <br>
        <input type="button" name="enter" value=" Enter " onClick="processConfirms()">
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