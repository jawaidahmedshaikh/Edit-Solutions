<!--
 * User: sdorman
 * Date: Jul 20, 2006
 * Time: 9:34:12 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%@ page import="engine.business.*,
                 engine.component.*,
                 java.util.*,
                 fission.utility.*,
                 extension.component.*,
                 acord.services.*,
                 client.*,
                 org.dom4j.*,
                 org.dom4j.tree.*,
                 webservice.service.*,
                 edit.common.vo.*,
                 edit.services.config.*,
                 org.apache.axiom.om.*,
                 webservice.*,
                 org.apache.axis2.client.*,
                 org.apache.axis2.addressing.*,
                 org.apache.axiom.om.util.*,
                 org.apache.axis2.util.*,
                 org.apache.axis2.*,
                 javax.xml.namespace.*,
                 org.apache.axis2.Constants,
                 org.dom4j.*,
                 javax.xml.namespace.QName,
                 org.apache.axis2.util.UUIDGenerator"%>


 <%
     //  Set host and port to run on (must match the host and port that EDITSolutions will be running on)
     String host = "localhost";
     String port = "9080";

     //  Set the input values
     String taxID = "9999";
     String addressTypeCT = ClientAddress.CLIENT_PRIMARY_ADDRESS;
     String trustTypeCT = ClientDetail.TRUSTTYPECT_INDIVIDUAL;

     //  Build document which contains input variables
     Document document = new DefaultDocument();

     Element rootElement = new DefaultElement("Input");

     Element taxIDElement = new DefaultElement("TaxID");
     taxIDElement.add(new DefaultText(taxID));

     Element addressTypeCTElement = new DefaultElement("AddressTypeCT");
     addressTypeCTElement.add(new DefaultText(addressTypeCT));

     Element trustTypeCTElement = new DefaultElement("TrustTypeCT");
     trustTypeCTElement.add(new DefaultText(trustTypeCT));

     rootElement.add(taxIDElement);
     rootElement.add(addressTypeCTElement);
     rootElement.add(trustTypeCTElement);

     document.add(rootElement);

     // Set up the message, attach the document (input), and process the service
     org.dom4j.Document result = WebClientUtil.process(document, "GetCurrentClientAddress", host, port);

//     // Same thing but without using it as a service
//     GetCurrentClientAddress gc = new GetCurrentClientAddress();
//
//     ClientAddressVO clientAddressVO = gc.getCurrentClientAddress(document);

//     String xmlResults = Util.marshalVO(clientAddressVO);
//
//     System.out.println("clientAddressVO = " + Util.marshalVO(clientAddressVO));

     String xmlResults = XMLUtil.prettyPrint(result);

 %>
 


<html>
    <head>
        <title>EDITSOLUTIONS - Service</title>

        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <meta http-equiv="Cache-Control" content="no-store">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">

        <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

        <script language="javascript1.2">

            var f = null;

            function init()
            {
                f = document.theForm;
            }

            function sendTransactionAction(transaction, action, target)
            {
                f.transaction.value = transaction;
                f.action.value = action;

                f.target = target;

                f.submit();
            }

        </script>

    </head>

    <body  class="mainTheme" onLoad="init()">
    <form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
        <B> Input </B> <BR><BR>

        taxID = <%= taxID %>       <BR>
        addressTypeCT = <%= addressTypeCT %>   <BR>
        trustTypeCT = <%= trustTypeCT %>   <BR>

        <BR><BR>
        <B> Output </B> <BR><BR>

        <%
            String[] resultsArray = Util.fastTokenizer(xmlResults.toString(), "\n");

            for (int i = 0; i < resultsArray.length; i++)
            {
                String s = resultsArray[i];

        %>
                <%=s %>   <BR>
        <%
            }
        %>

        <input type="hidden" name="transaction" value="">
        <input type="hidden" name="action"      value="">

    </form>

    </body>

</html>
