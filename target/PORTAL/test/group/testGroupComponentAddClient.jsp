<%@ page import="group.component.*,
                 org.dom4j.*,
                 fission.utility.*,
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
     String test = "GroupComponent.addClient";

     Group group = new GroupComponent();

     // Set up request document
     String requestXMLString =  "<SEGRequestVO>\n" +
                                "  <RequestParameters>\n" +
                                "    <ClientDetailVO>\n" +
                                "       <LastName>Rabbit</LastName>\n" +
                                "       <FirstName>Bunn</FirstName>\n" +
                                "       <MiddleName>E</MiddleName>\n" +
                                "       <TaxIdentification>5555555</TaxIdentification>\n" +
                                "       <BirthDate>01/01/2007</BirthDate>\n" +
                                "       <EmployeeIdentification>7777777</EmployeeIdentification>\n" +
                                "       <GenderCT>Female</GenderCT>\n" +
                                "       <Occupation>Reproducer</Occupation>\n" +
                                "       <Operator>Suzanne</Operator>\n" +
                                "    </ClientDetailVO>\n" +
                                "    <ClientAddressVO>\n" +
                                "       <AddressLine1>Main Street</AddressLine1>\n" +
                                "       <AddressLine2>Apt. 2</AddressLine2>\n" +
                                "       <AddressLine3></AddressLine3>\n" +
                                "       <AddressLine4></AddressLine4>\n" +
                                "       <City>Middletown</City>\n" +
                                "       <StateCT>CT</StateCT>\n" +
                                "       <ZipCode>06457</ZipCode>\n" +
                                "    </ClientAddressVO>\n" +
                                "  </RequestParameters>\n" +
                                "</SEGRequestVO>";

     Document requestDocument = XMLUtil.parse(requestXMLString);

     // Call service
     Document responseDocument = group.addClient(requestDocument);
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
