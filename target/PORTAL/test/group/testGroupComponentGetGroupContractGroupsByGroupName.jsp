<%@ page import="engine.sp.*,
                 group.component.*,
                 org.dom4j.*,
                 fission.utility.*,
                 billing.component.*,
                 billing.business.*,
                 org.dom4j.tree.*,
                 group.business.*"%>
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
     String test = "GroupComponent.getGroupContractGroupsByGroupName()";

     Group group = new GroupComponent();

     // Set up request document
     String xmlString = "<SEGRequestVO>\n" +
                        "  <Service></Service>\n" +
                        "  <RequestParameters>\n" +
                        "    <GroupName>ABC Enterprises</GroupName>\n" +
                        "  </RequestParameters>\n" +
                        "</SEGRequestVO>";

     Document requestDocument = XMLUtil.parse(xmlString);

     // Call service
     Document responseDocument = group.getGroupContractGroupsByGroupName(requestDocument);
 %>

<html>
    <body>
        <p>
            <b>Test of <%=test %></b>
        </p>
        <p>
            <b><i>INPUT</i></b><br>
        </p>
        <%
            out.println(XMLUtil.parseForJSPDisplay(requestDocument));
        %>

        <p>
            <b><i>OUTPUT</i></b>
        </p>
        <%
            out.println(XMLUtil.parseForJSPDisplay(responseDocument));
        %>
    </body>
</html>