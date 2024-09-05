<html>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<head>
<title>EDITSOLUTIONS - Contract</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

<script>
<% 
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage == null)
    {
        errorMessage = "";
    }
%>
    var errorMessage = "<%=errorMessage%>";
    if(errorMessage != "")
    {
        alert(errorMessage);
    }
</script>

</head>

<frameset name="mainframeset" rows="120,*" frameborder="No" border="0">

    <frame name="header"      scrolling="No"   src="/PORTAL/contract/jsp/contractToolBar.jsp" >
	<frame name="main" src="/PORTAL/contract/jsp/contractTabContent.jsp">
</frameset>
</html>
