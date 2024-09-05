<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
%>

<html>
<head>
<title>Conversion Selection</title>


<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript">

    var responseMessage = "<%= responseMessage %>";

   	var f = null;

	function init() {

		f = document.dailyform;

        checkForResponseMessage();
    }


    function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value = transaction;
    	f.action.value = action;

    	f.target = target;

    	f.submit();
    }



</script>
</head>

<body  bgColor="#99BBBB"  onLoad="init()">

        <h1>DATABASE CONVERSIONS</h1>

<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
<form name="dailyform" method="post" action="/PORTAL/servlet/RequestManager">
<table width="100%" height="100%" border="0" height="296">
  <tr align="center" valign="middle">
    <td height="294" width="94%" align="top" valign="middle" class="unnamed1">
      <table width="100%" height="100%" border="0" align="center"  bgcolor="#DDDDDD">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
            <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="JNLConversion/engineConversion.jsp">ENGINE Conversion</a>
	      </td>
        </tr>
        <tr>
            <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="JNLConversion/accountingConversion.jsp">ACCOUNTING Conversion</a>
	      </td>
        </tr>
        <tr>
            <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="JNLConversion/clientConversion.jsp">CLIENT Conversion</a>
	      </td>
        </tr>
        <tr>
            <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="JNLConversion/contractConversion.jsp">CONTRACT SEGMENT Conversion</a>
	      </td>
        </tr>
        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="JNLConversion/transactionConversion.jsp">CONTRACT TRANSACTION Conversion</a>
	      </td>
        </tr>
        <tr>
          <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
            <a href="JNLConversion/historyConversion.jsp">CONTRACT HISTORY Conversion</a>
	      </td>
        </tr>
        <tr>
            <td height="50%">
                &nbsp;
            </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<!-- ****** Hidden Values ***** //-->
	<input type="hidden" name="transaction">
	<input type="hidden" name="action">

</form>
</span>
</body>
</html>
