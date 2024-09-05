<html>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.net.*" %>

<head>
<title>ENGINE - Calculation Toolkit</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/ENGINE.css" rel="stylesheet" type="text/css">


<script language="JavaScript1.2">

	function getMode() {
		return "EnhancedApplet";
	}

	function setScript(transaction, action, scriptName, scriptId)  {
		document.applets[0].setScript(transaction, action, scriptName, scriptId);
	}

</script>


</head>

<body bgColor="#99BBBB" style="margin-top:0;margin-left:0">


<%
	//String transaction  = request.getParameter("transaction");
	//String action 	    = request.getParameter("action");

	String transaction  = "AppletScriptTran";
	String action       = "showEnhancedScriptNew";
	String scriptName 	= "";
	String scriptId     = "";
	String servletURL   = "PORTAL/servlet/RequestManager";
	String cancelPage   = "PORTAL/servlet/RequestManager?transaction=ScriptTran&action=showScriptNew";
	String port			= "9090";
	String host 		= InetAddress.getLocalHost().getHostName();
	//String host 		= "localhost";
%>

<br>
<center>
<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; top:0; left:0; z-index:0; overflow:visible">

<jsp:plugin
	type="applet"
	code="engine.ui.applet.EnhancedEditApplet"
      archive="/PORTAL/engine/ui/applet/ServletUtils.jar"
	codebase="/PORTAL"
	height="380"
	width="770"
>
	<jsp:params>
		<jsp:param name = "scriptName" value ="<%= scriptName %>" />
		<jsp:param name = "scriptId" value ="<%= scriptId %>" />
		<jsp:param name="servletURL" value ="<%= servletURL %>" />
		<jsp:param name="transaction" value ="<%= transaction %>" />
		<jsp:param name="cancelPage" value ="<%= cancelPage %>" />
		<jsp:param name="action" value ="<%= action %>" />
		<jsp:param name="port" value ="<%= port %>" />
		<jsp:param name="host" value ="<%= host %>" />
    	<jsp:param name="scriptable" value="true" />
	</jsp:params>
</jsp:plugin>

</span>

</center>
</body>
</html>