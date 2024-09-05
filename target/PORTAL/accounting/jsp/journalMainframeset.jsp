<html>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<head>
<title>EDITSOLUTIONS - Journal Adjustment</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

</head>

<frameset rows="100,*" frameborder="No" border="0">
    <frame name="header" scrolling="No" src="/PORTAL/accounting/jsp/journalToolBar.jsp" >
	<frame name="main" src="/PORTAL/servlet/RequestManager?transaction=AccountingDetailTran&action=showJournalAdjustment">
</frameset>
</html>