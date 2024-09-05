<%@ page import="engine.sp.*,
                 group.component.*,
                 org.dom4j.*,
                 fission.utility.*,
                 billing.component.*,
                 billing.business.*,
                 org.dom4j.tree.*"%>
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
     String test = "BillingComponent.updateBillGroup";

     Billing billing = new BillingComponent();

     // Set up request document
     String requestXMLString =  "<SEGRequestVO>\n" +
                                "  <RequestParameters>\n" +
                                "       <BillGroupVO>\n" +
                                "           <BillGroupPK>1187716849799</BillGroupPK>\n" +
                                "           <ExtractDate>08/03/2007</ExtractDate>\n" +
                                "           <DueDate>08/15/2007</DueDate>\n" +
                                "           <TotalBilledAmount>253.23</TotalBilledAmount>\n" +
                                "           <TotalPaidAmount>0.00</TotalPaidAmount>\n" +
                                "           <ReleaseDate></ReleaseDate>\n" +
                                "           <StopReasonCT></StopReasonCT>\n" +
                                "           <BillScheduleFK>1187699250490</BillScheduleFK>\n" +
                                "       </BillGroupVO>\n" +
                                "  </RequestParameters>\n" +
                                "</SEGRequestVO>";

     Document requestDocument = XMLUtil.parse(requestXMLString);

     // Call service
     billing.updateBillGroup(requestDocument);
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
        None
    </body>
</html>
