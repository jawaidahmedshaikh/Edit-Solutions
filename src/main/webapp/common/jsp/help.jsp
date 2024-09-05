<%@ page import="java.util.*,
                 edit.common.*,
                 edit.services.db.*"%>
<!--
 * User: sdorman
 * Date: Sep 26, 2006
 * Time: 9:43:37 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

 <%
     String version = System.getProperty("MajorVersion");
     String build = System.getProperty("Build");
     String builtFromBranch = System.getProperty("BuiltFromBranch");

     String versionInfo = "Version " + version + " Build " + build;

     EDITDate currentDate = new EDITDate();
 %>


 <html>

 <head>
 <title>Help</title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

 <script src="/PORTAL/common/javascript/commonJavascriptFunctions.js" type="text/javascript"></script>

 <script language="Javascript1.2">

 	var f = null;

 	function init()
    {
 		f = document.theForm;;
 	}
 </script>
 </head>

 <body class="dialog" onLoad="init()">
    <form name="theForm" method="post" action="/PORTAL/servlet/RequestManager" id="theForm">

        <table cellspacing="0" cellpadding="10">
            <tr>
                <td><B><I>EDITSolutions</I></B></td>
            </tr>
            <tr>
                <td>
                    <%= versionInfo %>
                </td>
                <td>
                    <%
                        if (builtFromBranch != null)
                        {
                    %>
                            Built From <%= builtFromBranch %>
                    <%
                        }
                    %>
                </td>
            </tr>
            <tr><td>&nbsp</td></tr>
        </table>

        <table cellspacing="0" cellpadding="10">
            <tr>
                <td><B><I>Databases</I></B></td>
            </tr>

            <tr>
                <th align="left">Name</th>
                <th align="left">Version</th>
                <th align="left">Last Update</th>
            </tr>

            <%
                DBDatabase[] dbDatabases = DBDatabase.getDBDatabases();

                for (int i = 0; i < dbDatabases.length; i++)
                {
                    DBDatabase dbDatabase = dbDatabases[i];

                    DatabaseVersion databaseVersion = dbDatabase.getDatabaseVersion();
            %>
                    <tr>
                        <td>
                            <%= dbDatabase.getDatabaseName()%>
                        </td>
                        <td>
                            <%= databaseVersion.getMajorVersion() %> <%= databaseVersion.getMinorVersion() %>
                        </td>
                        <td>
                            <%= databaseVersion.getUpdateDateTime()%>
                        </td>
                    </tr>
            <%
                }
            %>
        </table>

        <table width="100%" border="0" cellspacing="6" cellpadding="0">
            <tr><td>&nbsp</td></tr>
            <tr>
                <td align="right" valign="bottom" nowrap>
                    <input type="button" name="close" value="Close" onClick ="closeWindow()">
                </td>
            </tr>
        </table>

        <table>
            <tr>
                <td colspan="2" valign="bottom" align="center">
                    <font face="" color="#30548E">Copyright 2000-<%= currentDate.getFormattedYear()%>; Systems Engineering Group, LLC. All Rights Reserved.</font>
                </td>
            </tr>
        </table>

     </form>
 </body>
 </html>
