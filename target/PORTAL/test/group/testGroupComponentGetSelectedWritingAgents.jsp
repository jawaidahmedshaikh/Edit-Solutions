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
     String test = "GroupComponent.getSelectedWritingAgents";

     Group group = new GroupComponent();

     // Set up request document
     String requestXMLString =  "<SEGRequestVO>\n" +
                                "  <RequestParameters>\n" +
                                "    <BatchContractSetupPK>1183738271423</BatchContractSetupPK>\n" +
                                "  </RequestParameters>\n" +
                                "</SEGRequestVO>";

     Document requestDocument = XMLUtil.parse(requestXMLString);

     // Call service
     Document responseDocument = group.getSelectedWritingAgents(requestDocument);
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
