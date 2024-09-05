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
     String test = "GroupComponent.createDefaultBatchContractSetup";

     Group group = new GroupComponent();

     // Set up request document
     String requestXMLString =  "<SEGRequestVO>\n" +
                                "   <RequestParameters>\n" +
                                "       <GroupContractGroupPK>1182544086074</GroupContractGroupPK>\n" +
                                "       <Operator>Suzanne</Operator>\n" +
                                "   </RequestParameters>\n" +
                                "</SEGRequestVO>";

     Document requestDocument = XMLUtil.parse(requestXMLString);

     XMLUtil.printDocumentToSystemOut(requestDocument);

     // Call service
     Document responseDocument = group.createDefaultBatchContractSetup(requestDocument);
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
