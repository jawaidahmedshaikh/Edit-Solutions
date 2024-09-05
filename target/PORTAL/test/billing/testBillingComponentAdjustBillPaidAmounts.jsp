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
     String test = "BillingComponent.adjustBillPaidAmounts";

     Billing billing = new BillingComponent();

     // Set up request document
     String requestXMLString =  "<SEGRequestVO>\n" +
                                "  <RequestParameters>\n" +
                                "    <AdjustmentVO>\n" +
                                "      <BillPK>1</BillPK>\n" +
                                "      <PaidAmount>122.22</PaidAmount>\n" +
                                "    </AdjustmentVO>\n" +
                                "    <AdjustmentVO>\n" +
                                "      <BillPK>2</BillPK>\n" +
                                "      <PaidAmount>133.33</PaidAmount>\n" +
                                "    </AdjustmentVO>\n" +
                                "  </RequestParameters>\n" +
                                "</SEGRequestVO>";

     Document requestDocument = XMLUtil.parse(requestXMLString);

     // Call service
     billing.adjustBillPaidAmounts(requestDocument);
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
