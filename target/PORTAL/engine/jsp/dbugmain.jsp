<!-- ****** JAVA CODE ***** //-->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<html>
<head>
<title>PRASE</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/ENGINE.css" rel="stylesheet" type="text/css">


<!-- ****** Java Script CODE ***** //-->
<script language="JavaScript">
<!--

	function getMode() {
		return "D";
	}

//-->
</script>
</head>


<!-- ****** HTML CODE ***** //-->
     <frameset rows="86%,14%"    frameborder="No" border="0">
       <frameset cols="20%,40%,40%"  framespacing="0">
       		<frame name="header"      src="/PORTAL/engine/jsp/dbughead.jsp">
         	<frame name="script"  src="/PORTAL/engine/jsp/dbugscrp.jsp"
            	frameborder="1"   border="1" scrolling="AUTO">
            <frame name="viewer"  src="/PORTAL/engine/jsp/dbugview.jsp"
             	frameborder="0" scrolling="AUTO">
        </frameset>
       <frame name="buttons"     src="/PORTAL/engine/jsp/dbugbtns.jsp">
    </frameset>
<noframes><body>
Frames support is required to run PRASE Product Professional
software.  Please upgrade your browser.
</body>
</noframes>
</html>