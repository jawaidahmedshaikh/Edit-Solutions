<html>

<%
    String agentId = initValue((String) request.getAttribute("agentId"));
    String agentPK = initValue((String) request.getAttribute("agentPK"));
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

    function deleteAgent(){

        sendTransactionAction("AgentDetailTran", "deleteAgent", "contentIFrame");

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

        Delete Agent Identification: <b><%= agentId %></b>?
      </td>
    </tr>
    <tr>
      <td>&nbsp;&nbsp;</td>
      <td colspan="2" align="right" nowrap>
        <input type="button" name="enter" value="OK?" onClick="deleteAgent()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>

  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
  <input type="hidden" name="agentPK" value="<%= agentPK %>">

</form>
</body>
</html>
