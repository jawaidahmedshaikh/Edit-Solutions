<!-- ****** JAVA CODE ***** //-->

<%@ page import="java.util.*" %>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<jsp:useBean id="pageBean"
    class="fission.beans.PageBean" scope="request"/>	
<%
    String message = (String) request.getAttribute("callChainMessage");
%>

<html>
<head>
<title>ENGINE - Call Chain Error Message</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

<script language="JavaScript">

	function init() {

		f = document.paramForm;
	}

	function closeWindow(){

		window.close();
	}

</script>

</head>

<!-- ****** HTML CODE ***** //-->

<body style="margin-top:5;margin-left:10" onLoad="init()">
<form name="paramForm" method="post" action="/PORTAL/servlet/RequestManager">
<p></p>
<table width="89%" border="0" rules="none" cellspacing="0" cellpadding="0" height="489">
  <tr>
	<td>
		Error Message:
	</td>
	<td>
		<%= message %>
	</td>
  </tr>
  <tr>
    <td colspan="2" align="right">
      <input type="button" name="close"  value="   Close   " onClick="closeWindow()">
</table>

</body>
</form>
</html>