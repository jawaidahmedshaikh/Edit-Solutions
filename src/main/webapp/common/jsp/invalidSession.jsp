<html>
<head>
<meta http-equiv="REFRESH" content="5; URL=/PORTAL/common/jsp/portal.jsp">


<title>FISSION Error</title>
<link href="/ENGINE/ENGINE.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
<!--
    // Removes frames if target frame is not main or viewer
    if (top != self) {
        // Get main and viewer frame objects if they exist
        var main = null;
        var viewer = null;
        for (var i=0; i < top.frames.length; i++) {
            if (top.frames(i).name == "viewer") {
                main = top.frames(i);
            }
        }
        for (var i=0; i < parent.frames.length; i++) {
            if (parent.frames(i).name == "viewer") {
                viewer = parent.frames(i);
            }
        }

        // If the target frame is not main on viewer then
        // Change location to main
        if ((main != null) && (main.location != window.location)
                (viewer != null) && (viewer.location != window.location)) {

            top.frames("main").location = window.location;
        }
    }
//-->
</script>
</head>
<body class="withLogo" style="margin-top:5; margin-left:5; margin-right:75;">


<table width="100%" height="100%" border="0">
<tr>
    <td valign="middle" align="center">
        Your session has expired. You will be returned to the main page shortly.
    </td>
</tr>
</table>
</body>
</html>