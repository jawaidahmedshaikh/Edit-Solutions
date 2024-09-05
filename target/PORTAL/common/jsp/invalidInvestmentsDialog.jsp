<!-- editingExceptionDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.*,
                 edit.common.*,
                 edit.portal.exceptions.*" %>

<%

    PortalEditingException editingException = (PortalEditingException) request.getAttribute("portalEditingException");

    String transaction  = initValue(editingException.getTransaction());
    String action       = initValue(editingException.getAction());
%>
<%!
    private String initValue(String value){

        return (value == null ? "" : value);
    }
%>

<html>
<head>

<script language="Javascript1.2">

    var transaction = "<%= transaction %>";
    var action      = "<%= action %>";

    function closeWindow(){

        window.close();
    }

    function continueTransactionAction(){

        opener.continueTransactionAction(transaction, action);
        closeWindow();
    }

</script>

<title>Invalid Investments Exception</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

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
</head>

<body bgcolor="#DDDDDD">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:none">
  <table width="100%" border="0" cellspacing="0" cellpadding="4">
    <tr>
      <td colspan="7" align="left">
      Invalid Investment(s) selected for Transaction Date
      </td>
    </tr>
    <tr>
      <td colspan="7" align="right">
        <input type="button" value="Close" onClick="closeWindow()">
      </td>
    </tr>
  </table>
</span>

</form>
</body>
</html>