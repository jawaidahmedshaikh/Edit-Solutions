<html>

<%
    String clientId = initValue((String) request.getAttribute("clientId"));
    String clientDetailPK = initValue((String) request.getAttribute("clientDetailPK"));
%>


<%!
    private String initValue(String value){

        if (value != null){

            return value;
        }
        else {

            return "";
        }
    }
%>
<head>
<title>Delete Client</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.theForm;
	}

    function deleteClient(){

        sendTransactionAction("ClientDetailTran", "deleteClientDetails", "contentIFrame");

        window.close();
    }

	function cancelDialog() {

		window.close();
	}

    function sendTransactionAction(transaction, action, target) {

        f.transaction.value= transaction;
        f.action.value= action;

        f.target = target;

        f.submit();
    }

</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="43%" border="0" cellspacing="0" cellpadding="8" bgcolor="#DDDDDD">

    <tr>
      <td colspan="3" nowrap>

        Delete Client Identification: <b><%= clientId %></b>?
      </td>
    </tr>
    <tr>
      <td>&nbsp;&nbsp;</td>
      <td colspan="2" align="right" nowrap>
        <input type="button" name="enter" value="OK?" onClick="deleteClient()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>

  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
  <input type="hidden" name="clientDetailPK" value="<%= clientDetailPK %>">

</form>
</body>
</html>
