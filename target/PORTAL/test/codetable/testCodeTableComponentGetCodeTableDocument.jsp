<%@ page import="group.component.*,
                 org.dom4j.*,
                 org.dom4j.tree.*,
                 group.business.*,
                 codetable.business.*,
                 codetable.component.*,
                 fission.utility.*"%>
<!--
 * User: sdorman
 * Date: Jun 25, 2007
 * Time: 12:56:06 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%
     String test = "CodeTableComponent.getCodeTableDocument";

     CodeTable codeTable = new CodeTableComponent();

     // Call service
     Document responseDocument = codeTable.getCodeTableDocument();
 %>

<html>
    <body>
        <p>
            <b>Test of <%=test %></b>
        </p>
        <p>
            <b><i>INPUT</i></b><br>
            None
        </p>

        <p>
            <b><i>OUTPUT</i></b>
        </p>
        <%
            out.println(XMLUtil.parseForJSPDisplay(responseDocument));
        %>
    </body>
</html>
