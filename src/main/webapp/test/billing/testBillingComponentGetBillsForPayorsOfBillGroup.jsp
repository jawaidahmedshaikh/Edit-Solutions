<%@ page import="org.dom4j.*,
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
     String test = "BillingComponent.getBillsForPayorsOfBillGroup";

     Billing billing = new BillingComponent();

     // Set up request document
     String requestXMLString =  "<SEGRequestVO>\n" +
                                "  <RequestParameters>\n" +
                                "    <BillGroupPK>1187716849799</BillGroupPK>\n" +
                                "    <ClientDetailPK>1177333251234</ClientDetailPK>\n" +
                                "  </RequestParameters>\n" +
                                "</SEGRequestVO>";

     Document requestDocument = XMLUtil.parse(requestXMLString);

     // Call service
     // Document responseDocument = billing.getBillsForPayorsOfBillGroup(requestDocument);
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
            // out.println(XMLUtil.parseForJSPDisplay(responseDocument));
        %>
    </body>
</html>
