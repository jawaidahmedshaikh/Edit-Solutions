<%@ page import="fission.utility.Util"%>
<!--
 * User: sprasad
 * Date: Mar 17, 2005
 * Time: 10:51:43 AM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<html>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
//    String pageToShow = (String) request.getAttribute("pageToShow");

//    String action = "show" + Util.convertFirstCharacterCase(pageToShow, Util.UPPER_CASE);
    String action = "showCasetrackingClient";

    String mainFrameSrc = "/PORTAL/servlet/RequestManager?transaction=CaseTrackingTran&action=" + action;
%>

<head>
<title>EDITSOLUTIONS - Case Tracking</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<frameset rows="100,*" frameborder="No" border="0">

    <frame name="header"  src="/PORTAL/casetracking/jsp/casetrackingToolBar.jsp" scrolling="No">
	<frame name="main"    src="<%= mainFrameSrc %>">

</frameset>
</html>
