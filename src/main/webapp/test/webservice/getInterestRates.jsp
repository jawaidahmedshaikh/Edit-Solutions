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
                 edit.services.config.*,
                 org.apache.axiom.om.*,
                 org.apache.axis2.util.*,
                 org.apache.axis2.client.*,
                 org.apache.axis2.*,
                 org.apache.axis2.addressing.*,
                 javax.xml.namespace.*,
                 webservice.*,
                 acord.model.*,
                 org.apache.axis2.Constants,
                 acord.model.search.*,
                 webservice.service.*,
                 org.dom4j.*,
                 javax.xml.namespace.QName"%>

 <!--                          This JSP tests the getInterestRates web service            -->
 <%
     //  Set host and port to run on (must match the host and port that EDITSolutions will be running on)
     String host = "localhost";
     String port = "9080";

     //  Set your input values  - NOTE: Dates must be in the ACORD format yyyy-mm-dd

     String companyName = "SEG1";
     String marketingPackageName = "CERTAINTY";
     String groupProductName = "*";
     String areaName = "*";
     String businessContractName = "SPDA";
     String fundNumber = "1000";                  
     String interestRateDate = "2004-02-15";      // <= originalDate on InterestRateParameters
     String lastValuationDate = "2003-11-01";     // start range for effectiveDate on InterestRate
     String trxEffectiveDate = "2005-12-31";      // end range for effectiveDate on InterestRate
     String optionCT = "Bonus";                   // Bonus or Current


     // Create the ACORD document for the input
     ACORDDocument acordDocument = createACORDDocument(companyName, marketingPackageName, groupProductName, areaName,
             businessContractName, fundNumber, interestRateDate, lastValuationDate, trxEffectiveDate, optionCT);

//     acordDocument.printDocument();


     // Set up the message, attach the document (input), and process the service
     org.dom4j.Document result = WebClientUtil.process(acordDocument.getDocument(), "GetInterestRates", host, port);

//     XMLUtil.printDocumentToSystemOut(result);

     //  Convert the results to prettyPrint format
     String xml = XMLUtil.prettyPrint(result);
 %>

 <%!
     private ACORDDocument createACORDDocument(String companyName, String marketingPackageName, String groupProductName,
                   String areaName, String businessContractName, String fundNumber, String interestRateDate,
                   String lastValuationDate, String trxEffectiveDate, String optionCT)
     {
        CriteriaExpression criteriaExpression = new CriteriaExpression(acord.model.search.Constants.CriteriaOperator.LOGICAL_OPERATOR_AND);

        Criteria companyNameCriteria = new Criteria(acord.model.Constants.ObjectType.OLI_APPLICATIONINFO, acord.model.Constants.Operation.OLI_OP_EQUAL , "companyName", companyName);
        Criteria marketingPackageNameCriteria = new Criteria(acord.model.Constants.ObjectType.OLI_APPLICATIONINFO, acord.model.Constants.Operation.OLI_OP_EQUAL , "marketingPackageName", marketingPackageName);
        Criteria groupProductNameCriteria = new Criteria(acord.model.Constants.ObjectType.OLI_APPLICATIONINFO, acord.model.Constants.Operation.OLI_OP_EQUAL , "groupProductName", groupProductName);
        Criteria areaNameCriteria = new Criteria(acord.model.Constants.ObjectType.OLI_APPLICATIONINFO, acord.model.Constants.Operation.OLI_OP_EQUAL , "areaName", areaName);
        Criteria businessContractNameCriteria = new Criteria(acord.model.Constants.ObjectType.OLI_APPLICATIONINFO, acord.model.Constants.Operation.OLI_OP_EQUAL , "businessContractName", businessContractName);
        Criteria fundNumberCriteria = new Criteria(acord.model.Constants.ObjectType.OLI_APPLICATIONINFO, acord.model.Constants.Operation.OLI_OP_EQUAL , "fundNumber", fundNumber);
        Criteria interestRateDateCriteria = new Criteria(acord.model.Constants.ObjectType.OLI_APPLICATIONINFO, acord.model.Constants.Operation.OLI_OP_EQUAL , "interestRateDate", interestRateDate);
        Criteria lastValuationDateCriteria = new Criteria(acord.model.Constants.ObjectType.OLI_APPLICATIONINFO, acord.model.Constants.Operation.OLI_OP_EQUAL , "lastValuationDate", lastValuationDate);
        Criteria trxEffectiveDateCriteria = new Criteria(acord.model.Constants.ObjectType.OLI_APPLICATIONINFO, acord.model.Constants.Operation.OLI_OP_EQUAL , "trxEffectiveDate", trxEffectiveDate);
        Criteria optionCTCriteria = new Criteria(acord.model.Constants.ObjectType.OLI_APPLICATIONINFO, acord.model.Constants.Operation.OLI_OP_EQUAL , "optionCT", optionCT);

//        optionCTCriteria.setComparedPropertyName("ComparedPropertyName");   // testing

        criteriaExpression.add(companyNameCriteria);
        criteriaExpression.add(marketingPackageNameCriteria);
        criteriaExpression.add(groupProductNameCriteria);
        criteriaExpression.add(areaNameCriteria);
        criteriaExpression.add(businessContractNameCriteria);
        criteriaExpression.add(fundNumberCriteria);
        criteriaExpression.add(interestRateDateCriteria);
        criteriaExpression.add(lastValuationDateCriteria);
        criteriaExpression.add(trxEffectiveDateCriteria);
        criteriaExpression.add(optionCTCriteria);

        ACORDDocument acordDocument = new ACORDDocument();

        acordDocument.add(criteriaExpression);

         return acordDocument;
     }
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

        <%= acordDocument.asXML() %>

        <BR><BR>
        <B> Output </B> <BR><BR>

        <%
            StringTokenizer st = new StringTokenizer(xml, "\n");
            
            while (st.hasMoreTokens())
            {
                String s = st.nextToken();
        %>
                <%=s %>   <BR>
        <%
            }
        %>

<%--                <%=xml %>   <BR>--%>

        <input type="hidden" name="transaction" value="">
        <input type="hidden" name="action"      value="">

    </form>

    </body>

</html>
