<html>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");
%>

<head>
<title>EDITSOLUTIONS - Event</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

</head>

<frameset rows="100,*" frameborder="No" border="0">
    <frame name="header" scrolling="No"   src="/PORTAL/event/jsp/eventToolBar.jsp" >
	<frame name="main" src="/PORTAL/servlet/RequestManager?transaction=EventAdminTran&action=showTransactionPrioritySummary&username=<%=username%>&password=<%=password%>">

</frameset>
</html>