 <!--
 * 
 * User: cgleason
 * Date: Dec 19, 2005
 * Time: 3:31:59 PM
 * 
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 -->

<html>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
    String action = "showCaseMain";

    String mainFrameSrc = "/PORTAL/servlet/RequestManager?transaction=CaseDetailTran&action=" + action;
%>

<head>
<title>EDITSOLUTIONS - Case/Group Processing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<frameset rows="100,*" frameborder="No" border="0">

    <frame name="header"  src="/PORTAL/contract/jsp/caseMainToolBar.jsp" scrolling="No">
	<frame name="main"    src="<%= mainFrameSrc %>">

</frameset>
</html>
