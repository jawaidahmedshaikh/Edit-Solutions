<html>

<%
    String jumpToTarget = (String) request.getAttribute("jumpToTarget");
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
<title>Jump To</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="Javascript1.2">

	var f = null;
    var jumpToTarget = "<%= jumpToTarget %>";

	function init() {

		f = document.theForm;
	}

    function jumpToComponent(){

        var action = null;

        if (jumpToTarget == "QuoteAndCommit"){

            action = "showQuote";
        }
        else if (jumpToTarget == "Client"){

            action="showIndividuals";
        }
        else if (jumpToTarget == "Contract"){

            action = "showContract";
        }
        else if (jumpToTarget == "Case"){

            action = "showCase";
        }
        else if (jumpToTarget == "PRASE"){

            action = "showProductProfessionals";
        }
        else if (jumpToTarget == "Roles"){

            action="showRoleAdmin";
        }
        else if (jumpToTarget == "Agent"){

            action = "showAgentDetail";
        }
        else if (jumpToTarget == "AgentHierarchy"){

            action = "showAgentHierarchy";
        }
        else if (jumpToTarget == "CommissionContract"){

            action = "showCommissionContract";
        }
        else if (jumpToTarget == "Main")
        {
            action = "goToMain";
        }
        else if (jumpToTarget == "Logout")
        {
            action = "doLogOut";
        }

       opener.top.location.href="/PORTAL/servlet/RequestManager?transaction=PortalLoginTran&action=" + action;

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
  <table id="theTable" width="43%" border="0" cellspacing="0" cellpadding="8" bgcolor="#DDDDDD">

    <tr>
      <td colspan="3" nowrap>

        Leave component and lose your edits?
      </td>
    </tr>
    <tr>
      <td>&nbsp;&nbsp;</td>
      <td colspan="2" align="right" nowrap>
        <input type="button" name="enter" value="OK?" onClick="jumpToComponent()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>

  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
</form>
</body>
</html>
